package com.copyright.rup.dist.foreign.service.impl.aacl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclScenarioService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;

import com.copyright.rup.dist.foreign.service.impl.ServiceTestHelper;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
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
    private IScenarioRepository scenarioRepository;
    @Autowired
    private IAaclScenarioService aaclScenarioService;
    @Autowired
    private IAaclUsageService aaclUsageService;
    @Autowired
    private IScenarioUsageFilterService scenarioUsageFilterService;
    @Autowired
    private ServiceTestHelper testHelper;

    private String scenarioName;
    private String scenarioDescription;
    private String expectedRollupsJson;
    private List<String> expectedRollupsRightholderIds;
    private AaclFields scenarioAaclFields;
    private UsageFilter scenarioUsageFilter;
    private Scenario expectedScenario;
    private String expectedUsagesJsonFile;
    private ScenarioUsageFilter expectedScenarioFilter;
    private List<Pair<ScenarioActionTypeEnum, String>> expectedScenarioAudit;

    CreateAaclScenarioIntegrationTestBuilder withFilter(UsageFilter usageFilter) {
        scenarioUsageFilter = usageFilter;
        return this;
    }

    CreateAaclScenarioIntegrationTestBuilder expectRollups(String rollupsJson, String... rollupsRightsholdersIds) {
        this.expectedRollupsJson = rollupsJson;
        this.expectedRollupsRightholderIds = Arrays.asList(rollupsRightsholdersIds);
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

    CreateAaclScenarioIntegrationTestBuilder expectScenarioAudit(
        List<Pair<ScenarioActionTypeEnum, String>> expectedAudit) {
        this.expectedScenarioAudit = expectedAudit;
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
        expectedRollupsJson = null;
        expectedRollupsRightholderIds = null;
        scenarioName = null;
        scenarioDescription = null;
        scenarioAaclFields = null;
        scenarioUsageFilter = null;
        expectedScenario = null;
        expectedUsagesJsonFile = null;
        expectedScenarioFilter = null;
        expectedScenarioAudit = null;
    }

    /**
     * Test runner class.
     */
    class Runner {

        private String scenarioId;

        void run() throws IOException {
            testHelper.createRestServer();
            if (Objects.nonNull(expectedRollupsRightholderIds)) {
                testHelper.expectGetRollups(expectedRollupsJson, expectedRollupsRightholderIds);
            }
            scenarioId = aaclScenarioService
                .createScenario(scenarioName, scenarioAaclFields, scenarioDescription, scenarioUsageFilter).getId();
            testHelper.verifyRestServer();
            assertScenario();
            assertUsages();
            testHelper.assertScenarioAudit(scenarioId, expectedScenarioAudit);
            assertScenarioUsageFilter();
        }

        private void assertScenario() {
            Scenario actualScenario = scenarioRepository.findById(scenarioId);
            assertNotNull(actualScenario);
            assertEquals(expectedScenario.getName(), actualScenario.getName());
            assertEquals(expectedScenario.getNetTotal(), actualScenario.getNetTotal());
            assertEquals(expectedScenario.getGrossTotal(), actualScenario.getGrossTotal());
            assertEquals(expectedScenario.getServiceFeeTotal(), actualScenario.getServiceFeeTotal());
            assertEquals(expectedScenario.getStatus(), actualScenario.getStatus());
            assertEquals(expectedScenario.getDescription(), actualScenario.getDescription());
            assertAaclFields(expectedScenario.getAaclFields(), actualScenario.getAaclFields());
        }

        private void assertUsages() throws IOException {
            List<Usage> expectedUsages = testHelper.loadExpectedUsages(expectedUsagesJsonFile);
            expectedUsages.forEach(expectedUsage -> {
                List<Usage> actualUsages = aaclUsageService.getUsagesByIds(List.of(expectedUsage.getId()));
                assertEquals(1, actualUsages.size());
                assertUsage(expectedUsage, actualUsages.get(0));
            });
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

        private void assertUsage(Usage expectedUsage, Usage actualUsage) {
            assertNotNull(actualUsage);
            assertEquals(expectedUsage.getBatchId(), actualUsage.getBatchId());
            assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
            if (UsageStatusEnum.LOCKED == expectedUsage.getStatus()) {
                assertEquals(scenarioId, actualUsage.getScenarioId());
            } else {
                assertNull(actualUsage.getScenarioId());
            }
            assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
            assertEquals(expectedUsage.getRightsholder().getAccountNumber(),
                actualUsage.getRightsholder().getAccountNumber());
            assertEquals(expectedUsage.getProductFamily(), actualUsage.getProductFamily());
            assertEquals(expectedUsage.getWorkTitle(), actualUsage.getWorkTitle());
            assertEquals(expectedUsage.getSystemTitle(), actualUsage.getSystemTitle());
            assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
            assertEquals(expectedUsage.getStandardNumberType(), actualUsage.getStandardNumberType());
            assertEquals(expectedUsage.getNumberOfCopies(), actualUsage.getNumberOfCopies());
            assertEquals(expectedUsage.getGrossAmount(), actualUsage.getGrossAmount());
            assertEquals(expectedUsage.getNetAmount(), actualUsage.getNetAmount());
            assertEquals(expectedUsage.getServiceFeeAmount(), actualUsage.getServiceFeeAmount());
            assertEquals(expectedUsage.getServiceFee(), actualUsage.getServiceFee());
            assertEquals(expectedUsage.getComment(), actualUsage.getComment());
            testHelper.assertAaclUsage(expectedUsage.getAaclUsage(), actualUsage.getAaclUsage());
        }

        private void assertPublicationType(PublicationType expectedPublicationType,
                                           PublicationType actualPublicationType) {
            assertEquals(expectedPublicationType.getName(), actualPublicationType.getName());
            assertEquals(expectedPublicationType.getWeight(), actualPublicationType.getWeight());
        }
    }
}
