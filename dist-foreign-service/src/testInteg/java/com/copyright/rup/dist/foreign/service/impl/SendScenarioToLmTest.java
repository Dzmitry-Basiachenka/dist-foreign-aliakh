package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.common.test.mock.aws.SqsClientMock;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.repository.api.IAaclUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclScenarioService;
import com.copyright.rup.dist.foreign.service.api.fas.IFasScenarioService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsScenarioService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalScenarioService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;

import com.google.common.collect.ImmutableMap;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Verifies Send To LM functionality.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/11/18
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class SendScenarioToLmTest {

    private static final String FOLDER_NAME = "send-scenario-to-lm-integration-test/";
    private static final String FAS_SCENARIO_ID = "4c014547-06f3-4840-94ff-6249730d537d";
    private static final String NTS_SCENARIO_ID = "67027e15-17c6-4b9b-b7f0-12ec414ad344";
    private static final String AACL_SCENARIO_ID = "d92e3c8e-7ecc-4080-bf3f-b541f51c9a06";
    private static final String SAL_SCENARIO_ID = "b32a1abe-0de7-4889-99aa-fd5491c85a94";
    private static final String QUEUE_NAME = "fda-test-sf-detail.fifo";
    private static final Map<String, String> SOURCE_MAP = ImmutableMap.of("source", "FDA");

    @Autowired
    private IFasScenarioService fasScenarioService;
    @Autowired
    private INtsScenarioService ntsScenarioService;
    @Autowired
    private IAaclScenarioService aaclScenarioService;
    @Autowired
    private ISalScenarioService salScenarioService;
    @Autowired
    private ISalUsageService salUsageService;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;
    @Autowired
    private IAaclUsageRepository aaclUsageRepository;
    @Autowired
    private SqsClientMock sqsClientMock;
    @Autowired
    private ServiceTestHelper testHelper;

    @Before
    public void setUp() {
        sqsClientMock.reset();
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "test-sent-to-lm-sal.groovy")
    public void testSendToLmSal() throws IOException {
        Scenario scenario = new Scenario();
        scenario.setId(SAL_SCENARIO_ID);
        scenario.setProductFamily("SAL");
        salScenarioService.sendToLm(scenario);
        sqsClientMock.assertSendMessages(QUEUE_NAME,
            List.of(TestUtils.fileToString(this.getClass(), "details/details_to_lm_sal.json")),
            Collections.emptyList(), SOURCE_MAP);
        List<UsageDto> usageDtos = usageService.getRightsholderTotalsHoldersByScenario(scenario, null, null, null)
            .stream()
            .map(RightsholderTotalsHolder::getRightsholder)
            .map(Rightsholder::getAccountNumber)
            .flatMap(accountNumber ->
                salUsageService.getByScenarioAndRhAccountNumber(scenario, accountNumber, null, null, null).stream())
            .sorted(Comparator.comparing(UsageDto::getRhAccountNumber).thenComparing(UsageDto::getId))
            .collect(Collectors.toList());
        List<UsageDto> expectedUsageDtos = testHelper.loadExpectedUsageDtos("usage/sal/archived_usage_dtos_sal.json");
        assertEquals(CollectionUtils.size(expectedUsageDtos), CollectionUtils.size(usageDtos));
        IntStream.range(0, usageDtos.size())
            .forEach(index -> assertUsageDto(expectedUsageDtos.get(index), usageDtos.get(index)));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "test-sent-to-lm-fas.groovy")
    public void testSendToLmFas() throws IOException {
        Scenario scenario = new Scenario();
        scenario.setId(FAS_SCENARIO_ID);
        scenario.setProductFamily("FAS");
        fasScenarioService.sendToLm(scenario);
        sqsClientMock.assertSendMessages(QUEUE_NAME,
            List.of(TestUtils.fileToString(this.getClass(), "details/details_to_lm_fas.json")),
            Collections.emptyList(), SOURCE_MAP);
        List<UsageDto> usageDtos = findUsageDtos(scenario);
        List<UsageDto> expectedUsageDtos = testHelper.loadExpectedUsageDtos("usage/archived_usage_dtos_fas.json");
        assertEquals(CollectionUtils.size(expectedUsageDtos), CollectionUtils.size(usageDtos));
        IntStream.range(0, usageDtos.size())
            .forEach(index -> assertUsageDto(expectedUsageDtos.get(index), usageDtos.get(index)));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "test-sent-to-lm-nts.groovy")
    public void testSendToLmNts() throws IOException {
        assertNtsFundPoolUsages(UsageStatusEnum.TO_BE_DISTRIBUTED);
        Scenario scenario = new Scenario();
        scenario.setId(NTS_SCENARIO_ID);
        scenario.setProductFamily("NTS");
        ntsScenarioService.sendToLm(scenario);
        sqsClientMock.assertSendMessages(QUEUE_NAME,
            List.of(TestUtils.fileToString(this.getClass(), "details/details_to_lm_nts.json")),
            List.of("detail_id"), SOURCE_MAP);
        List<UsageDto> usageDtos = findUsageDtos(scenario);
        List<UsageDto> expectedUsageDtos = testHelper.loadExpectedUsageDtos("usage/archived_usage_dtos_nts.json");
        assertEquals(CollectionUtils.size(expectedUsageDtos), CollectionUtils.size(usageDtos));
        IntStream.range(0, usageDtos.size())
            .forEach(index -> assertUsageDtoIgnoringId(expectedUsageDtos.get(index), usageDtos.get(index)));
        assertNtsFundPoolUsages(UsageStatusEnum.ARCHIVED);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "test-sent-to-lm-aacl.groovy")
    public void testSendToLmAacl() throws IOException {
        Scenario scenario = new Scenario();
        scenario.setId(AACL_SCENARIO_ID);
        scenario.setProductFamily("AACL");
        aaclScenarioService.sendToLm(scenario);
        sqsClientMock.assertSendMessages(QUEUE_NAME,
            List.of(TestUtils.fileToString(this.getClass(), "details/details_to_lm_aacl.json")),
            Collections.emptyList(), SOURCE_MAP);
        List<UsageDto> actualUsageDtos = usageArchiveRepository.findAaclDtosByScenarioId(AACL_SCENARIO_ID);
        List<UsageDto> expectedUsageDtos = testHelper.loadExpectedUsageDtos("usage/aacl/aacl_archived_usage_dtos.json");
        assertEquals(CollectionUtils.size(expectedUsageDtos), CollectionUtils.size(actualUsageDtos));
        IntStream.range(0, actualUsageDtos.size())
            .forEach(index -> assertUsageDto(expectedUsageDtos.get(index), actualUsageDtos.get(index)));
        List<Usage> actualBaselineUsages = aaclUsageRepository.findBaselineUsages().stream()
            .sorted(Comparator.comparing(Usage::getComment))
            .collect(Collectors.toList());
        List<Usage> expectedBaselineUsages = testHelper.loadExpectedUsages("usage/aacl/aacl_baseline_usages.json");
        assertEquals(CollectionUtils.size(actualBaselineUsages), CollectionUtils.size(expectedBaselineUsages));
        IntStream.range(0, actualUsageDtos.size())
            .forEach(index -> assertBaselineUsage(expectedBaselineUsages.get(index), actualBaselineUsages.get(index)));

    }

    private List<UsageDto> findUsageDtos(Scenario scenario) {
        return usageService.getRightsholderTotalsHoldersByScenario(scenario, null, null, null).stream()
            .map(RightsholderTotalsHolder::getRightsholder)
            .map(Rightsholder::getAccountNumber)
            .flatMap(accountNumber ->
                usageService.getByScenarioAndRhAccountNumber(accountNumber, scenario, null, null, null).stream())
            .sorted(Comparator.comparing(UsageDto::getRhAccountNumber).thenComparing(UsageDto::getId))
            .collect(Collectors.toList());
    }

    private void assertNtsFundPoolUsages(UsageStatusEnum status) {
        AuditFilter filter = new AuditFilter();
        filter.setBatchesIds(Set.of("a3af8396-acf3-432b-9f23-7554e3d8f50d"));
        List<UsageDto> usages = usageService.getForAudit(filter, null, null);
        assertEquals(2, usages.size());
        usages.forEach(usage -> assertEquals(status, usage.getStatus()));
    }

    private void assertUsageDto(UsageDto expected, UsageDto actual) {
        assertEquals(expected.getId(), actual.getId());
        assertUsageDtoIgnoringId(expected, actual);
    }

    private void assertUsageDtoIgnoringId(UsageDto expected, UsageDto actual) {
        assertEquals(expected.getBatchName(), actual.getBatchName());
        assertEquals(expected.getFiscalYear(), actual.getFiscalYear());
        assertEquals(expected.getRroAccountNumber(), actual.getRroAccountNumber());
        assertEquals(expected.getRroName(), actual.getRroName());
        assertEquals(expected.getWrWrkInst(), actual.getWrWrkInst());
        assertEquals(expected.getSystemTitle(), actual.getSystemTitle());
        assertEquals(expected.getWorkTitle(), actual.getWorkTitle());
        assertEquals(expected.getArticle(), actual.getArticle());
        assertEquals(expected.getRhAccountNumber(), actual.getRhAccountNumber());
        assertEquals(expected.getRhName(), actual.getRhName());
        assertEquals(expected.getStandardNumber(), actual.getStandardNumber());
        assertEquals(expected.getStandardNumberType(), actual.getStandardNumberType());
        assertEquals(expected.getPublisher(), actual.getPublisher());
        assertEquals(expected.getNumberOfCopies(), actual.getNumberOfCopies());
        assertEquals(expected.getMarket(), actual.getMarket());
        assertEquals(expected.getArticle(), actual.getArticle());
        assertEquals(expected.getPayeeAccountNumber(), actual.getPayeeAccountNumber());
        assertEquals(expected.getPayeeName(), actual.getPayeeName());
        assertEquals(expected.getGrossAmount(), actual.getGrossAmount());
        assertEquals(expected.getReportedValue(), actual.getReportedValue());
        assertEquals(expected.getNetAmount(), actual.getNetAmount());
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getServiceFee(), actual.getServiceFee());
        assertEquals(expected.getComment(), actual.getComment());
        if (Objects.nonNull(expected.getAaclUsage())) {
            testHelper.assertAaclUsage(expected.getAaclUsage(), actual.getAaclUsage());
        } else {
            assertNull(actual.getAaclUsage());
        }
        if (Objects.nonNull(expected.getSalUsage())) {
            testHelper.assertSalUsage(expected.getSalUsage(), actual.getSalUsage());
        } else {
            assertNull(actual.getSalUsage());
        }
    }

    private void assertBaselineUsage(Usage expectedUsage, Usage actualUsage) {
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(expectedUsage.getNumberOfCopies(), actualUsage.getNumberOfCopies());
        assertEquals(expectedUsage.getComment(), actualUsage.getComment());
        testHelper.assertAaclUsage(expectedUsage.getAaclUsage(), actualUsage.getAaclUsage());
    }
}
