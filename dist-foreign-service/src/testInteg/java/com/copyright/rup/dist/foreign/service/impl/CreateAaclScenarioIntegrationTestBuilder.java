package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.AaclUsage;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * Builder for {@link CreateAaclScenarioIntegrationTest}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 3/18/20
 *
 * @author Stanislau Rudak
 */
@Component
public class CreateAaclScenarioIntegrationTestBuilder {

    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IAaclUsageService aaclUsageService;
    @Autowired
    private IScenarioAuditService scenarioAuditService;
    @Autowired
    private IScenarioUsageFilterService scenarioUsageFilterService;

    private String scenarioName;
    private String scenarioDescription;
    private AaclFields scenarioAaclFields;
    private UsageFilter scenarioUsageFilter;
    private Scenario expectedScenario;
    private String expectedUsagesJsonFile;
    private ScenarioUsageFilter expectedScenarioFilter;

    CreateAaclScenarioIntegrationTestBuilder withFilter(UsageFilter usageFilter) {
        scenarioUsageFilter = usageFilter;
        return this;
    }

    CreateAaclScenarioIntegrationTestBuilder withScenario(String name, String description, AaclFields aaclFields) {
        scenarioName = name;
        scenarioDescription = description;
        scenarioAaclFields = aaclFields;
        return this;
    }

    CreateAaclScenarioIntegrationTestBuilder expectScenario(Scenario scenario) {
        expectedScenario = scenario;
        return this;
    }

    CreateAaclScenarioIntegrationTestBuilder expectUsages(String usageJsonFile) {
        expectedUsagesJsonFile = usageJsonFile;
        return this;
    }

    CreateAaclScenarioIntegrationTestBuilder expectScenarioFilter(ScenarioUsageFilter scenarioFilter) {
        expectedScenarioFilter = scenarioFilter;
        return this;
    }

    Runner build() {
        return new Runner();
    }

    void reset() {
        scenarioName = null;
        scenarioDescription = null;
        scenarioAaclFields = null;
        scenarioUsageFilter = null;
        expectedScenario = null;
        expectedUsagesJsonFile = null;
        expectedScenarioFilter = null;
    }

    /**
     * Test runner class.
     */
    class Runner {

        private final String productFamily = scenarioUsageFilter.getProductFamily();
        private String scenarioId;

        void run() throws IOException {
            scenarioId = scenarioService
                .createAaclScenario(scenarioName, scenarioAaclFields, scenarioDescription, scenarioUsageFilter).getId();
            assertScenario();
            assertUsages();
            assertScenarioActions();
            assertScenarioUsageFilter();
        }

        private void assertScenario() {
            Scenario actualScenario = getScenarioById(scenarioId);
            assertEquals(expectedScenario.getName(), actualScenario.getName());
            assertEquals(expectedScenario.getNetTotal(), actualScenario.getNetTotal());
            assertEquals(expectedScenario.getGrossTotal(), actualScenario.getGrossTotal());
            assertEquals(expectedScenario.getServiceFeeTotal(), actualScenario.getServiceFeeTotal());
            assertEquals(expectedScenario.getReportedTotal(), actualScenario.getReportedTotal());
            assertEquals(expectedScenario.getStatus(), actualScenario.getStatus());
            assertEquals(expectedScenario.getDescription(), actualScenario.getDescription());
            assertAaclFields(expectedScenario.getAaclFields(), actualScenario.getAaclFields());
        }

        private void assertUsages() throws IOException {
            List<Usage> expectedUsages = loadExpectedUsages(expectedUsagesJsonFile);
            expectedUsages.forEach(expectedUsage -> {
                List<Usage> actualUsages =
                    aaclUsageService.getUsagesByIds(Collections.singletonList(expectedUsage.getId()));
                assertEquals(1, actualUsages.size());
                assertUsage(expectedUsage, actualUsages.get(0));
            });
        }

        private void assertScenarioActions() {
            List<ScenarioAuditItem> actions = scenarioAuditService.getActions(scenarioId);
            assertTrue(CollectionUtils.isNotEmpty(actions));
            assertEquals(1, CollectionUtils.size(actions));
            assertEquals(ScenarioActionTypeEnum.ADDED_USAGES, actions.get(0).getActionType());
            assertEquals(StringUtils.EMPTY, actions.get(0).getActionReason());
        }

        private void assertScenarioUsageFilter() {
            ScenarioUsageFilter actual = scenarioUsageFilterService.getByScenarioId(scenarioId);
            assertNotNull(actual);
            assertEquals(expectedScenarioFilter.getRhAccountNumbers(), actual.getRhAccountNumbers());
            assertEquals(expectedScenarioFilter.getUsageBatchesIds(), actual.getUsageBatchesIds());
            assertEquals(expectedScenarioFilter.getProductFamily(), actual.getProductFamily());
            assertEquals(expectedScenarioFilter.getUsageStatus(), actual.getUsageStatus());
            assertEquals(expectedScenarioFilter.getPaymentDate(), actual.getPaymentDate());
            assertEquals(expectedScenarioFilter.getFiscalYear(), actual.getFiscalYear());
            assertEquals(expectedScenarioFilter.getUsagePeriod(), actual.getUsagePeriod());
        }

        private void assertAaclFields(AaclFields expected, AaclFields actual) {
            assertEquals(expected.getTitleCutoffAmount(), actual.getTitleCutoffAmount());
            assertEquals(expected.getFundPoolId(), actual.getFundPoolId());
            assertUsageAges(expected.getUsageAges(), actual.getUsageAges());
            assertPublicationTypes(expected.getPublicationTypes(), actual.getPublicationTypes());
            assertDetailLicenseeClasses(expected.getDetailLicenseeClasses(), actual.getDetailLicenseeClasses());
        }

        private void assertUsageAges(List<UsageAge> expected, List<UsageAge> actual) {
            assertEquals(expected.size(), actual.size());
            IntStream.range(0, expected.size()).forEach(i -> assertUsageAge(expected.get(i), actual.get(i)));
        }

        private void assertUsageAge(UsageAge expected, UsageAge actual) {
            assertEquals(expected.getPeriod(), actual.getPeriod());
            assertEquals(expected.getWeight(), actual.getWeight());
        }

        private void assertPublicationTypes(List<PublicationType> expected, List<PublicationType> actual) {
            assertEquals(expected.size(), actual.size());
            IntStream.range(0, expected.size()).forEach(i -> assertPublicationType(expected.get(i), actual.get(i)));
        }

        private void assertDetailLicenseeClasses(List<DetailLicenseeClass> expected, List<DetailLicenseeClass> actual) {
            assertEquals(expected.size(), actual.size());
            IntStream.range(0, expected.size()).forEach(i -> assertDetailLicenseeClass(expected.get(i), actual.get(i)));
        }

        private void assertDetailLicenseeClass(DetailLicenseeClass expected, DetailLicenseeClass actual) {
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getDiscipline(), actual.getDiscipline());
            assertEquals(expected.getEnrollmentProfile(), actual.getEnrollmentProfile());
            assertAggregateLicenseeClass(expected.getAggregateLicenseeClass(), actual.getAggregateLicenseeClass());
        }

        private void assertAggregateLicenseeClass(AggregateLicenseeClass expected, AggregateLicenseeClass actual) {
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getDiscipline(), actual.getDiscipline());
            assertEquals(expected.getEnrollmentProfile(), actual.getEnrollmentProfile());
        }

        private List<Usage> loadExpectedUsages(String fileName) throws IOException {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
            return mapper.readValue(content, new TypeReference<List<Usage>>() {
            });
        }

        private void assertUsage(Usage expectedUsage, Usage actualUsage) {
            assertNotNull(actualUsage);
            assertEquals(expectedUsage.getBatchId(), actualUsage.getBatchId());
            assertEquals(scenarioId, actualUsage.getScenarioId());
            assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
            assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
            assertEquals(expectedUsage.getRightsholder().getAccountNumber(),
                actualUsage.getRightsholder().getAccountNumber());
            assertEquals(expectedUsage.getProductFamily(), actualUsage.getProductFamily());
            assertEquals(expectedUsage.getWorkTitle(), actualUsage.getWorkTitle());
            assertEquals(expectedUsage.getSystemTitle(), actualUsage.getSystemTitle());
            assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
            assertEquals(expectedUsage.getStandardNumberType(), actualUsage.getStandardNumberType());
            assertEquals(expectedUsage.getNumberOfCopies(), actualUsage.getNumberOfCopies());
            assertEquals(expectedUsage.getComment(), actualUsage.getComment());
            assertAaclUsage(expectedUsage.getAaclUsage(), actualUsage.getAaclUsage());
        }

        private void assertAaclUsage(AaclUsage expectedAaclUsage, AaclUsage actualAaclUsage) {
            assertEquals(expectedAaclUsage.getInstitution(), actualAaclUsage.getInstitution());
            assertEquals(expectedAaclUsage.getUsageSource(), actualAaclUsage.getUsageSource());
            assertEquals(expectedAaclUsage.getNumberOfPages(), actualAaclUsage.getNumberOfPages());
            assertEquals(expectedAaclUsage.getUsagePeriod(), actualAaclUsage.getUsagePeriod());
            assertPublicationType(expectedAaclUsage.getPublicationType(), actualAaclUsage.getPublicationType());
            assertEquals(expectedAaclUsage.getOriginalPublicationType(), actualAaclUsage.getOriginalPublicationType());
            assertEquals(expectedAaclUsage.getPublicationTypeWeight(), actualAaclUsage.getPublicationTypeWeight());
            assertEquals(expectedAaclUsage.getDetailLicenseeClassId(), actualAaclUsage.getDetailLicenseeClassId());
            assertEquals(expectedAaclUsage.getRightLimitation(), actualAaclUsage.getRightLimitation());
            assertEquals(expectedAaclUsage.getBaselineId(), actualAaclUsage.getBaselineId());
        }

        private void assertPublicationType(PublicationType expectedPublicationType,
                                           PublicationType actualPublicationType) {
            assertEquals(expectedPublicationType.getName(), actualPublicationType.getName());
            assertEquals(expectedPublicationType.getWeight(), actualPublicationType.getWeight());
        }

        private Scenario getScenarioById(String id) {
            List<Scenario> scenarios = scenarioService.getScenarios(productFamily);
            return scenarios.stream()
                .filter(scenario -> Objects.equals(scenarioId, scenario.getId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError(
                    String.format("Scenario doesn't exist. ScenarioId=%s, Scenarios=%s", id, scenarios)));
        }
    }
}
