package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.NtsFields;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IScenarioUsageFilterRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Verifies {@link ScenarioRepository}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/15/17
 *
 * @author Ihar Suvorau
 * @author Mikalai Bezmen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=scenario-repository-test-data-init.groovy"})
@Transactional
public class ScenarioRepositoryIntegrationTest {

    private static final String SCENARIO_NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String USAGE_BATCH_ID = "a5b64c3a-55d2-462e-b169-362dca6a4dd6";
    private static final String SCENARIO_ID = RupPersistUtils.generateUuid();
    private static final String SCENARIO_WITH_AUDIT_ID = "b1f0b236-3ae9-4a60-9fab-61db84199d6f";
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String NTS_PRODUCT_FAMILY = "NTS";
    private static final String USER = "user@copyright.com";

    @Autowired
    private ScenarioRepository scenarioRepository;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageBatchRepository batchRepository;
    @Autowired
    private IScenarioUsageFilterRepository filterRepository;

    @Test
    public void testFindCountByName() {
        assertEquals(0, scenarioRepository.findCountByName(SCENARIO_NAME));
        scenarioRepository.insert(buildScenario());
        assertEquals(1, scenarioRepository.findCountByName(SCENARIO_NAME));
    }

    @Test
    public void testFindByProductFamily() {
        List<Scenario> fasScenarios = scenarioRepository.findByProductFamily("FAS");
        assertEquals(9, fasScenarios.size());
        verifyScenario(fasScenarios.get(0), "095f3df4-c8a7-4dba-9a8f-7dce0b61c40a", "Scenario with excluded usages",
            "The description of scenario 6", FAS_PRODUCT_FAMILY, ScenarioStatusEnum.IN_PROGRESS);
        verifyScenario(fasScenarios.get(1), "e27551ed-3f69-4e08-9e4f-8ac03f67595f", "Scenario name 2",
            "The description of scenario 2", FAS_PRODUCT_FAMILY, ScenarioStatusEnum.IN_PROGRESS);
        verifyScenario(fasScenarios.get(2), "b1f0b236-3ae9-4a60-9fab-61db84199d6f", "Scenario name",
            "The description of scenario", FAS_PRODUCT_FAMILY, ScenarioStatusEnum.APPROVED);
        verifyScenario(fasScenarios.get(3), "1230b236-1239-4a60-9fab-123b84199123", "Scenario name 4",
            "The description of scenario 4", FAS_PRODUCT_FAMILY, ScenarioStatusEnum.IN_PROGRESS);
        verifyScenario(fasScenarios.get(4), "8a6a6b15-6922-4fda-b40c-5097fcbd256e", "Scenario name 5",
            "The description of scenario 5", FAS_PRODUCT_FAMILY, ScenarioStatusEnum.SENT_TO_LM);
        verifyScenario(fasScenarios.get(5), "3210b236-1239-4a60-9fab-888b84199321", "Scenario name 3",
            "The description of scenario 3", FAS_PRODUCT_FAMILY, ScenarioStatusEnum.IN_PROGRESS);
        verifyScenario(fasScenarios.get(6), "005a33fc-26c5-4e0d-afd3-1d581b62ec70", "Partially Paid Scenario",
            "Not all usages are paid", FAS_PRODUCT_FAMILY, ScenarioStatusEnum.SENT_TO_LM);
        verifyScenario(fasScenarios.get(7), "a9ee7491-d166-47cd-b36f-fe80ee7450f1", "Fully Paid Scenario",
            "All usages are paid and reported to CRM", FAS_PRODUCT_FAMILY, ScenarioStatusEnum.SENT_TO_LM);
        verifyScenario(fasScenarios.get(8), "a386bd74-c112-4b19-b9b7-c5e4f18c7fcd", "Archived Scenario",
            "Scenario already archived", FAS_PRODUCT_FAMILY, ScenarioStatusEnum.ARCHIVED);
        List<Scenario> ntsScenarios = scenarioRepository.findByProductFamily("NTS");
        assertEquals(3, ntsScenarios.size());
        verifyScenario(ntsScenarios.get(0), "8cb9092d-a0f7-474e-a13b-af1a134e4c86",
            "Sent to LM NTS scenario with audit", "The description of scenario 8", NTS_PRODUCT_FAMILY,
            ScenarioStatusEnum.SENT_TO_LM);
        assertNotNull(ntsScenarios.get(0).getNtsFields());
        verifyScenario(ntsScenarios.get(1), "2369313c-dd17-45ed-a6e9-9461b9232ffd", "Approved NTS scenario with audit",
            "The description of scenario 7", NTS_PRODUCT_FAMILY, ScenarioStatusEnum.APPROVED);
        Scenario ntsScenario = ntsScenarios.get(2);
        verifyScenario(ntsScenario, "1a5f3df4-c8a7-4dba-9a8f-7dce0b61c41b", "Test NTS scenario",
            "Description for test NTS scenario", NTS_PRODUCT_FAMILY, ScenarioStatusEnum.IN_PROGRESS);
        assertNotNull(ntsScenario.getNtsFields());
        assertEquals(new BigDecimal("300.00"), ntsScenario.getNtsFields().getRhMinimumAmount());
        assertNotNull(ntsScenarios.get(1).getNtsFields());
    }

    @Test
    public void testFindWithAmountsAndLastActionForFasScenario() {
        Scenario scenario = scenarioRepository.findWithAmountsAndLastAction(SCENARIO_WITH_AUDIT_ID);
        verifyScenario(scenario, "b1f0b236-3ae9-4a60-9fab-61db84199d6f", "Scenario name",
            "The description of scenario", FAS_PRODUCT_FAMILY, ScenarioStatusEnum.APPROVED);
        ScenarioAuditItem scenarioAuditItem = scenario.getAuditItem();
        assertEquals(ScenarioActionTypeEnum.APPROVED, scenarioAuditItem.getActionType());
        assertEquals("Scenario approved by manager", scenarioAuditItem.getActionReason());
        assertEquals(new BigDecimal("32874.8000000000"), scenario.getGrossTotal());
        assertEquals(new BigDecimal("22354.8000000000"), scenario.getNetTotal());
        assertEquals(new BigDecimal("10520.0000000000"), scenario.getServiceFeeTotal());
        assertEquals(new BigDecimal("19800.00"), scenario.getReportedTotal());
        assertNull(scenario.getNtsFields());
    }

    @Test
    public void testFindArchivedWithAmountsAndLastActionForFasScenario() {
        Scenario scenario =
            scenarioRepository.findArchivedWithAmountsAndLastAction("8a6a6b15-6922-4fda-b40c-5097fcbd256e");
        verifyScenario(scenario, "8a6a6b15-6922-4fda-b40c-5097fcbd256e", "Scenario name 5",
            "The description of scenario 5", FAS_PRODUCT_FAMILY, ScenarioStatusEnum.SENT_TO_LM);
        ScenarioAuditItem scenarioAuditItem = scenario.getAuditItem();
        assertEquals(ScenarioActionTypeEnum.SENT_TO_LM, scenarioAuditItem.getActionType());
        assertNull(scenarioAuditItem.getActionReason());
        assertEquals(new BigDecimal("1000.0000000000"), scenario.getGrossTotal());
        assertEquals(new BigDecimal("680.0000000000"), scenario.getNetTotal());
        assertEquals(new BigDecimal("320.0000000000"), scenario.getServiceFeeTotal());
        assertEquals(new BigDecimal("1000.00"), scenario.getReportedTotal());
        assertNull(scenario.getNtsFields());
    }

    @Test
    public void testFindWithAmountsAndLastActionForNtsScenario() {
        Scenario scenario = scenarioRepository.findWithAmountsAndLastAction("2369313c-dd17-45ed-a6e9-9461b9232ffd");
        verifyScenario(scenario, "2369313c-dd17-45ed-a6e9-9461b9232ffd", "Approved NTS scenario with audit",
            "The description of scenario 7", NTS_PRODUCT_FAMILY, ScenarioStatusEnum.APPROVED);
        ScenarioAuditItem scenarioAuditItem = scenario.getAuditItem();
        assertEquals(ScenarioActionTypeEnum.APPROVED, scenarioAuditItem.getActionType());
        assertEquals("Scenario approved by manager", scenarioAuditItem.getActionReason());
        assertEquals(new BigDecimal("1100.0000000000"), scenario.getGrossTotal());
        assertEquals(new BigDecimal("780.0000000000"), scenario.getNetTotal());
        assertEquals(new BigDecimal("320.0000000000"), scenario.getServiceFeeTotal());
        assertEquals(new BigDecimal("900.00"), scenario.getReportedTotal());
        NtsFields ntsFields = scenario.getNtsFields();
        assertEquals("7141290b-7042-4cc6-975f-10546370adce", ntsFields.getPreServiceFeeFundId());
        assertEquals("Pre-Service Fee Additional Fund 3", ntsFields.getPreServiceFeeFundName());
        assertEquals(new BigDecimal("300.00"), ntsFields.getRhMinimumAmount());
        assertEquals(new BigDecimal("100.00"), ntsFields.getPostServiceFeeAmount());
        assertEquals(new BigDecimal("50.00"), ntsFields.getPreServiceFeeAmount());
        assertEquals(USER, scenario.getCreateUser());
        assertEquals(Date.from(OffsetDateTime.parse("2017-02-14T12:00:00+00:00").toInstant()),
            scenario.getCreateDate());
        assertEquals(Date.from(OffsetDateTime.parse("2018-05-14T11:41:52.735531+03:00").toInstant()),
            scenario.getUpdateDate());
    }

    @Test
    public void testFindArchivedWithAmountsAndLastActionForNtsScenario() {
        Scenario scenario =
            scenarioRepository.findArchivedWithAmountsAndLastAction("8cb9092d-a0f7-474e-a13b-af1a134e4c86");
        verifyScenario(scenario, "8cb9092d-a0f7-474e-a13b-af1a134e4c86", "Sent to LM NTS scenario with audit",
            "The description of scenario 8", NTS_PRODUCT_FAMILY, ScenarioStatusEnum.SENT_TO_LM);
        ScenarioAuditItem scenarioAuditItem = scenario.getAuditItem();
        assertEquals(ScenarioActionTypeEnum.SENT_TO_LM, scenarioAuditItem.getActionType());
        assertNull(scenarioAuditItem.getActionReason());
        assertEquals(new BigDecimal("1100.0000000000"), scenario.getGrossTotal());
        assertEquals(new BigDecimal("780.0000000000"), scenario.getNetTotal());
        assertEquals(new BigDecimal("320.0000000000"), scenario.getServiceFeeTotal());
        assertEquals(new BigDecimal("900.00"), scenario.getReportedTotal());
        NtsFields ntsFields = scenario.getNtsFields();
        assertEquals("815d6736-a34e-4fc8-96c3-662a114fa7f2", ntsFields.getPreServiceFeeFundId());
        assertEquals("Pre-Service Fee Additional Fund 4", ntsFields.getPreServiceFeeFundName());
        assertEquals(new BigDecimal("300.00"), ntsFields.getRhMinimumAmount());
        assertEquals(new BigDecimal("100.00"), ntsFields.getPostServiceFeeAmount());
        assertEquals(new BigDecimal("50.00"), ntsFields.getPreServiceFeeAmount());
        assertEquals(USER, scenario.getCreateUser());
        assertEquals(Date.from(OffsetDateTime.parse("2017-02-14T12:00:00+00:00").toInstant()),
            scenario.getCreateDate());
        assertEquals(Date.from(OffsetDateTime.parse("2018-05-15T11:41:52.735531+03:00").toInstant()),
            scenario.getUpdateDate());
    }

    @Test
    public void testFindNamesByUsageBatchId() {
        List<String> scenariosNames = scenarioRepository.findNamesByUsageBatchId(USAGE_BATCH_ID);
        assertNotNull(scenariosNames);
        assertEquals(1, scenariosNames.size());
        Scenario scenario = buildScenario();
        scenarioRepository.insert(scenario);
        usageRepository.insert(buildUsage());
        scenariosNames = scenarioRepository.findNamesByUsageBatchId(USAGE_BATCH_ID);
        assertNotNull(scenariosNames);
        assertEquals(2, scenariosNames.size());
    }

    @Test
    public void testFindNamesByUsageBatchIdForArchiveUsage() {
        List<String> scenariosNames =
            scenarioRepository.findNamesByUsageBatchId("56282cac-2468-48d4-b346-93d3458a656a");
        assertNotNull(scenariosNames);
        assertEquals(1, scenariosNames.size());
        assertEquals("Scenario name 3", scenariosNames.get(0));
    }

    @Test
    public void testFindNamesByUsageBatchIdAssociatedWithFilterOnly() {
        List<String> scenariosNames =
            scenarioRepository.findNamesByUsageBatchId("4eff2685-4895-45a1-a886-c41a0f98204b");
        assertNotNull(scenariosNames);
        assertEquals(1, scenariosNames.size());
        assertEquals("Scenario with excluded usages", scenariosNames.get(0));
    }

    @Test
    public void testFindNameByPreServiceFeeFundId() {
        assertEquals("Sent to LM NTS scenario with audit",
            scenarioRepository.findNameByPreServiceFeeFundId("815d6736-a34e-4fc8-96c3-662a114fa7f2"));
        assertNull(scenarioRepository.findNameByPreServiceFeeFundId(RupPersistUtils.generateUuid()));
    }

    @Test
    public void testRemove() {
        scenarioRepository.insert(buildScenario());
        assertEquals(1, scenarioRepository.findCountByName(SCENARIO_NAME));
        scenarioRepository.remove(SCENARIO_ID);
        assertEquals(0, scenarioRepository.findCountByName(SCENARIO_NAME));
    }

    @Test
    public void testUpdateStatus() {
        Scenario scenario =
            scenarioRepository.findArchivedWithAmountsAndLastAction("3210b236-1239-4a60-9fab-888b84199321");
        scenario.setStatus(ScenarioStatusEnum.SUBMITTED);
        scenarioRepository.updateStatus(scenario);
        Scenario updatedScenario =
            scenarioRepository.findArchivedWithAmountsAndLastAction("3210b236-1239-4a60-9fab-888b84199321");
        assertNotNull(updatedScenario);
        assertEquals(ScenarioStatusEnum.SUBMITTED, updatedScenario.getStatus());
        assertEquals(scenario.getName(), updatedScenario.getName());
        assertEquals(scenario.getDescription(), updatedScenario.getDescription());
        assertEquals(scenario.getGrossTotal(), updatedScenario.getGrossTotal());
        assertEquals(scenario.getReportedTotal(), updatedScenario.getReportedTotal());
        assertEquals(scenario.getNetTotal(), updatedScenario.getNetTotal());
        assertEquals(scenario.getServiceFeeTotal(), updatedScenario.getServiceFeeTotal());
    }

    @Test
    public void testFindSourceRros() {
        scenarioRepository.insert(buildScenario());
        usageRepository.insert(buildUsage());
        UsageBatch batch = buildBatch();
        batchRepository.insert(batch);
        Usage usage = buildUsage();
        usage.setBatchId(batch.getId());
        usageRepository.insert(usage);
        // inserting different batch with the same RRO to verify that it will be returned only once
        batch = buildBatch();
        batchRepository.insert(batch);
        usage = buildUsage();
        usage.setBatchId(batch.getId());
        usageRepository.insert(usage);
        List<Rightsholder> sourceRros = scenarioRepository.findSourceRros(SCENARIO_ID);
        assertEquals(2, sourceRros.size());
        assertTrue(sourceRros.stream().map(Rightsholder::getAccountNumber).collect(Collectors.toSet()).containsAll(
            Sets.newHashSet(2000017010L, 2000017000L)));
    }

    @Test
    public void testFindRightsholdersByScenarioIdAndSourceRro() {
        scenarioRepository.insert(buildScenario());
        // build one usage with different pair of rh and payee
        Usage usage1 = buildUsage();
        usage1.setRightsholder(buildRightsholder(2000017004L,
            "Access Copyright, The Canadian Copyright Agency"));
        usage1.setPayee(buildRightsholder(2000017010L,
            "JAC, Japan Academic Association for Copyright Clearance, Inc."));
        // build 2 usages with the same rh and payee
        Usage usage2 = buildUsage();
        Usage usage3 = buildUsage();
        usageRepository.insert(usage1);
        usageRepository.insert(usage2);
        usageRepository.insert(usage3);
        usageRepository.addToScenario(Lists.newArrayList(usage1, usage2, usage3));
        List<RightsholderPayeePair> result =
            scenarioRepository.findRightsholdersByScenarioIdAndSourceRro(SCENARIO_ID, 2000017010L);
        assertEquals(2, result.size());
        result.sort(Comparator.comparing(RightsholderPayeePair::getRightsholder)
            .thenComparing(RightsholderPayeePair::getPayee));
        // one pair should be returned for usage 3
        RightsholderPayeePair pair1 = result.get(0);
        assertEquals(usage1.getRightsholder(), pair1.getRightsholder());
        assertEquals(usage1.getPayee(), pair1.getPayee());
        RightsholderPayeePair pair2 = result.get(1);
        // one pair should be returned for usages 1 and 2
        assertEquals(usage2.getRightsholder(), pair2.getRightsholder());
        assertEquals(usage2.getPayee(), pair2.getPayee());
        assertEquals(usage3.getRightsholder(), pair2.getRightsholder());
        assertEquals(usage3.getPayee(), pair2.getPayee());
    }

    @Test
    public void testFindIdsForArchiving() {
        List<String> scenariosIds = scenarioRepository.findIdsForArchiving();
        assertTrue(CollectionUtils.isNotEmpty(scenariosIds));
        assertEquals(1, scenariosIds.size());
        assertEquals("a9ee7491-d166-47cd-b36f-fe80ee7450f1", scenariosIds.get(0));
    }

    @Test
    public void testInsertNtsScenarioAndAddUsages() {
        Scenario scenario = buildScenario();
        NtsFields ntsFields = new NtsFields();
        ntsFields.setRhMinimumAmount(new BigDecimal("700.00"));
        ntsFields.setPreServiceFeeAmount(new BigDecimal("3250.00"));
        ntsFields.setPostServiceFeeAmount(new BigDecimal("5005.00"));
        ntsFields.setPreServiceFeeFundId("5210b859-1239-4a60-9fab-999b84199321");
        scenario.setNtsFields(ntsFields);
        UsageFilter usageFilter = buildUsageFilter();
        scenarioRepository.insertNtsScenarioAndAddUsages(scenario, usageFilter);
        ScenarioUsageFilter scenarioUsageFilter = new ScenarioUsageFilter(usageFilter);
        scenarioUsageFilter.setId(RupPersistUtils.generateUuid());
        scenarioUsageFilter.setScenarioId(scenario.getId());
        filterRepository.insert(scenarioUsageFilter);
        Scenario ntsScenario = scenarioRepository.findWithAmountsAndLastAction(SCENARIO_ID);
        assertNotNull(ntsScenario.getNtsFields());
        assertEquals(new BigDecimal("700.00"), ntsScenario.getNtsFields().getRhMinimumAmount());
        assertUsages(
            Arrays.asList(
                buildUsage("244de0db-b50c-45e8-937c-72e033e2a3a9", "921.8750000000"),
                buildUsage("3caf371f-f2e6-47cd-af6b-1e02b79f6195", "3950.8928571429"),
                buildUsage("87666035-2bdf-49ef-8c80-1d1b281fdc34", "9877.2321428571")),
            usageRepository.findByScenarioId(SCENARIO_ID));
        assertUsages(
            Arrays.asList(buildNtsExcludedUsage("0d200064-185a-4c48-bbc9-c67554e7db8e"),
                buildNtsExcludedUsage("9bc172f4-edbb-4a62-9ffc-254336e7a56d")),
            usageRepository.findByStatuses(UsageStatusEnum.NTS_EXCLUDED));
    }

    private void assertUsages(List<Usage> expected, List<Usage> actual) {
        actual.sort(Comparator.comparing(Usage::getId));
        assertEquals(CollectionUtils.size(expected), CollectionUtils.size(actual));
        IntStream.range(0, actual.size())
            .forEach(index -> verifyUsage(expected.get(index), actual.get(index)));
    }

    private UsageBatch buildBatch() {
        UsageBatch batch = new UsageBatch();
        batch.setId(RupPersistUtils.generateUuid());
        batch.setProductFamily(FAS_PRODUCT_FAMILY);
        batch.setGrossAmount(new BigDecimal("100.00"));
        batch.setPaymentDate(LocalDate.of(2017, 1, 1));
        batch.setFiscalYear(2017);
        batch.setName("Batch name");
        Rightsholder rro = new Rightsholder();
        rro.setAccountNumber(2000017000L);
        batch.setRro(rro);
        return batch;
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setBatchId(USAGE_BATCH_ID);
        usage.setScenarioId(SCENARIO_ID);
        usage.setWrWrkInst(123456783L);
        usage.setWorkTitle("WorkTitle");
        usage.setRightsholder(buildRightsholder(7000813806L,
            "CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil"));
        usage.setPayee(usage.getRightsholder());
        usage.setStatus(UsageStatusEnum.LOCKED);
        usage.setProductFamily(FAS_PRODUCT_FAMILY);
        usage.setArticle("Article");
        usage.setStandardNumber("StandardNumber");
        usage.setPublisher("Publisher");
        usage.setPublicationDate(LocalDate.of(2016, 11, 3));
        usage.setMarket("Market");
        usage.setMarketPeriodFrom(2015);
        usage.setMarketPeriodTo(2017);
        usage.setAuthor("Author");
        usage.setNumberOfCopies(1);
        usage.setReportedValue(new BigDecimal("11.25"));
        usage.setGrossAmount(new BigDecimal("54.4400000000"));
        return usage;
    }

    private Scenario buildScenario() {
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setName(SCENARIO_NAME);
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        scenario.setDescription(DESCRIPTION);
        scenario.setCreateUser(USER);
        scenario.setUpdateUser(USER);
        return scenario;
    }

    private void verifyScenario(Scenario scenario, String id, String name, String description, String productFamily,
                                ScenarioStatusEnum status) {
        assertEquals(id, scenario.getId());
        assertEquals(name, scenario.getName());
        assertEquals(description, scenario.getDescription());
        assertEquals(USER, scenario.getCreateUser());
        assertEquals(OffsetDateTime.parse("2017-02-14T12:00:00+00:00").toInstant(),
            scenario.getCreateDate().toInstant());
        assertEquals(productFamily, scenario.getProductFamily());
        assertEquals(status, scenario.getStatus());
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(name);
        return rightsholder;
    }

    private UsageFilter buildUsageFilter() {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageBatchesIds(Sets.newHashSet("7c8f48fe-3429-43fd-9389-d9b77fa9f3a0",
            "ac9bae73-9bd7-477c-bc95-e73daee455ee", "5769e9fe-1b4b-4399-841c-73108893f7d2",
            "d8baa8e6-10fd-4c3c-8851-b1e6883e8cde", "f8f23728-75ac-4114-b910-2d7abc061217"));
        usageFilter.setRhAccountNumbers(Collections.singleton(2000017001L));
        usageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        usageFilter.setProductFamily(NTS_PRODUCT_FAMILY);
        usageFilter.setPaymentDate(LocalDate.of(2020, 1, 1));
        usageFilter.setFiscalYear(2020);
        return usageFilter;
    }

    private Usage buildUsage(String usageId, String grossAmount) {
        Usage usage = new Usage();
        usage.setId(usageId);
        usage.setGrossAmount(new BigDecimal(grossAmount));
        usage.setScenarioId(SCENARIO_ID);
        usage.setStatus(UsageStatusEnum.LOCKED);
        return usage;
    }

    private Usage buildNtsExcludedUsage(String usageId) {
        Usage usage = new Usage();
        usage.setId(usageId);
        usage.setStatus(UsageStatusEnum.NTS_EXCLUDED);
        usage.setGrossAmount(new BigDecimal("0.0000000000"));
        return usage;
    }

    private void verifyUsage(Usage expectedUsage, Usage actualUsage) {
        assertEquals(expectedUsage.getScenarioId(), actualUsage.getScenarioId());
        assertEquals(expectedUsage.getId(), actualUsage.getId());
        assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
        assertEquals(expectedUsage.getGrossAmount(), actualUsage.getGrossAmount());
    }
}
