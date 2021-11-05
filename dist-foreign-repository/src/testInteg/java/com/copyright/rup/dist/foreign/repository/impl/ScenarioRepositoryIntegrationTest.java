package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;
import com.copyright.rup.dist.foreign.domain.Scenario.NtsFields;
import com.copyright.rup.dist.foreign.domain.Scenario.SalFields;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IFasUsageRepository;
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
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EnumSet;
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
//TODO: split test data into separate files for each test method
@TestData(fileName = "scenario-repository-test-data-init.groovy")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
@Transactional
public class ScenarioRepositoryIntegrationTest {

    private static final String SCENARIO_NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String USAGE_BATCH_ID = "a5b64c3a-55d2-462e-b169-362dca6a4dd6";
    private static final String SCENARIO_ID_1 = "5fb817fd-c1cb-4b47-a3c9-3dbb16c88b65";
    private static final String SCENARIO_ID_2 = "b1f0b236-3ae9-4a60-9fab-61db84199d6f";
    private static final String SCENARIO_ID_3 = "2369313c-dd17-45ed-a6e9-9461b9232ffd";
    private static final String SCENARIO_ID_4 = "8cb9092d-a0f7-474e-a13b-af1a134e4c86";
    private static final String SCENARIO_ID_5 = "8a6a6b15-6922-4fda-b40c-5097fcbd256e";
    private static final String SCENARIO_ID_6 = "3210b236-1239-4a60-9fab-888b84199321";
    private static final String SENT_TO_LM_AUDIT = "Sent to LM NTS scenario with audit";
    private static final String AACL_PRODUCT_FAMILY = "AACL";
    private static final String SAL_PRODUCT_FAMILY = "SAL";
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String NTS_PRODUCT_FAMILY = "NTS";
    private static final String USER = "user@copyright.com";
    private static final String ONE = "1.00";

    @Autowired
    private ScenarioRepository scenarioRepository;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IFasUsageRepository fasUsageRepository;
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
        verifyScenario(fasScenarios.get(2), "b1f0b236-3ae9-4a60-9fab-61db84199d6f", "Scenario name 9",
            "The description of scenario 9", FAS_PRODUCT_FAMILY, ScenarioStatusEnum.IN_PROGRESS);
        verifyScenario(fasScenarios.get(3), "1230b236-1239-4a60-9fab-123b84199123", "Scenario name 4",
            "The description of scenario 4", FAS_PRODUCT_FAMILY, ScenarioStatusEnum.IN_PROGRESS);
        verifyScenario(fasScenarios.get(4), SCENARIO_ID_5, "Scenario name 5", "The description of scenario 5",
            FAS_PRODUCT_FAMILY, ScenarioStatusEnum.SENT_TO_LM);
        verifyScenario(fasScenarios.get(5), "3210b236-1239-4a60-9fab-888b84199321", "Scenario name 3",
            "The description of scenario 3", FAS_PRODUCT_FAMILY, ScenarioStatusEnum.IN_PROGRESS);
        verifyScenario(fasScenarios.get(6), "005a33fc-26c5-4e0d-afd3-1d581b62ec70", "Partially Paid Scenario",
            "Not all usages are paid", FAS_PRODUCT_FAMILY, ScenarioStatusEnum.SENT_TO_LM);
        verifyScenario(fasScenarios.get(7), "a9ee7491-d166-47cd-b36f-fe80ee7450f1", "Fully Paid Scenario",
            "All usages are paid and reported to CRM", FAS_PRODUCT_FAMILY, ScenarioStatusEnum.SENT_TO_LM);
        verifyScenario(fasScenarios.get(8), "a386bd74-c112-4b19-b9b7-c5e4f18c7fcd", "Archived Scenario",
            "Scenario already archived", FAS_PRODUCT_FAMILY, ScenarioStatusEnum.ARCHIVED);
        List<Scenario> ntsScenarios = scenarioRepository.findByProductFamily(NTS_PRODUCT_FAMILY);
        assertEquals(3, ntsScenarios.size());
        verifyScenario(ntsScenarios.get(0), SCENARIO_ID_4, SENT_TO_LM_AUDIT, "The description of scenario 8",
            NTS_PRODUCT_FAMILY, ScenarioStatusEnum.SENT_TO_LM);
        assertNotNull(ntsScenarios.get(0).getNtsFields());
        verifyScenario(ntsScenarios.get(1), SCENARIO_ID_3, "Rejected NTS scenario with audit",
            "The description of scenario 7", NTS_PRODUCT_FAMILY, ScenarioStatusEnum.IN_PROGRESS);
        Scenario ntsScenario = ntsScenarios.get(2);
        verifyScenario(ntsScenario, "1a5f3df4-c8a7-4dba-9a8f-7dce0b61c41b", "Test NTS scenario",
            "Description for test NTS scenario", NTS_PRODUCT_FAMILY, ScenarioStatusEnum.IN_PROGRESS);
        assertNotNull(ntsScenario.getNtsFields());
        assertEquals(new BigDecimal("300.00"), ntsScenario.getNtsFields().getRhMinimumAmount());
        assertNotNull(ntsScenarios.get(1).getNtsFields());
    }

    @Test
    public void testFindByProductFamiliesAndStatuses() {
        List<Scenario> scenarios =
            scenarioRepository.findByProductFamiliesAndStatuses(Sets.newHashSet(NTS_PRODUCT_FAMILY, FAS_PRODUCT_FAMILY),
                EnumSet.of(ScenarioStatusEnum.SENT_TO_LM));
        assertEquals(4, scenarios.size());
        verifyScenario(scenarios.get(0), SCENARIO_ID_4, SENT_TO_LM_AUDIT, "The description of scenario 8",
            NTS_PRODUCT_FAMILY, ScenarioStatusEnum.SENT_TO_LM);
        verifyScenario(scenarios.get(1), SCENARIO_ID_5, "Scenario name 5",
            "The description of scenario 5", FAS_PRODUCT_FAMILY, ScenarioStatusEnum.SENT_TO_LM);
        verifyScenario(scenarios.get(2), "005a33fc-26c5-4e0d-afd3-1d581b62ec70", "Partially Paid Scenario",
            "Not all usages are paid", FAS_PRODUCT_FAMILY, ScenarioStatusEnum.SENT_TO_LM);
        verifyScenario(scenarios.get(3), "a9ee7491-d166-47cd-b36f-fe80ee7450f1", "Fully Paid Scenario",
            "All usages are paid and reported to CRM", FAS_PRODUCT_FAMILY, ScenarioStatusEnum.SENT_TO_LM);
    }

    @Test
    public void testFindWithAmountsAndLastActionForFasScenario() {
        Scenario scenario = scenarioRepository.findWithAmountsAndLastAction(SCENARIO_ID_2);
        verifyScenario(scenario, SCENARIO_ID_2, "Scenario name 9",
            "The description of scenario 9", FAS_PRODUCT_FAMILY, ScenarioStatusEnum.IN_PROGRESS);
        ScenarioAuditItem scenarioAuditItem = scenario.getAuditItem();
        assertEquals(ScenarioActionTypeEnum.REJECTED, scenarioAuditItem.getActionType());
        assertEquals("Scenario rejected by manager", scenarioAuditItem.getActionReason());
        assertEquals(new BigDecimal("32874.8000000000"), scenario.getGrossTotal());
        assertEquals(new BigDecimal("22354.8000000000"), scenario.getNetTotal());
        assertEquals(new BigDecimal("10520.0000000000"), scenario.getServiceFeeTotal());
        assertNull(scenario.getNtsFields());
        List<Usage> usages = usageRepository.findByScenarioId(SCENARIO_ID_2);
        usageRepository.deleteFromScenario(usages.stream().map(Usage::getId).collect(Collectors.toList()), USER);
        scenario = scenarioRepository.findWithAmountsAndLastAction(SCENARIO_ID_2);
        assertNotNull(scenario);
        verifyScenario(scenario, SCENARIO_ID_2, "Scenario name 9",
            "The description of scenario 9", FAS_PRODUCT_FAMILY, ScenarioStatusEnum.IN_PROGRESS);
        assertEquals(BigDecimal.ZERO, scenario.getGrossTotal());
        assertEquals(BigDecimal.ZERO, scenario.getNetTotal());
        assertEquals(BigDecimal.ZERO, scenario.getServiceFeeTotal());
    }

    @Test
    public void testFindArchivedWithAmountsAndLastActionForFasScenario() {
        Scenario scenario =
            scenarioRepository.findArchivedWithAmountsAndLastAction(SCENARIO_ID_5);
        verifyScenario(scenario, SCENARIO_ID_5, "Scenario name 5",
            "The description of scenario 5", FAS_PRODUCT_FAMILY, ScenarioStatusEnum.SENT_TO_LM);
        ScenarioAuditItem scenarioAuditItem = scenario.getAuditItem();
        assertEquals(ScenarioActionTypeEnum.SENT_TO_LM, scenarioAuditItem.getActionType());
        assertNull(scenarioAuditItem.getActionReason());
        assertEquals(new BigDecimal("1000.0000000000"), scenario.getGrossTotal());
        assertEquals(new BigDecimal("680.0000000000"), scenario.getNetTotal());
        assertEquals(new BigDecimal("320.0000000000"), scenario.getServiceFeeTotal());
        assertNull(scenario.getNtsFields());
    }

    @Test
    public void testFindWithAmountsAndLastActionForNtsScenario() {
        Scenario scenario = scenarioRepository.findWithAmountsAndLastAction(SCENARIO_ID_3);
        verifyScenario(scenario, SCENARIO_ID_3, "Rejected NTS scenario with audit",
            "The description of scenario 7", NTS_PRODUCT_FAMILY, ScenarioStatusEnum.IN_PROGRESS);
        ScenarioAuditItem scenarioAuditItem = scenario.getAuditItem();
        assertEquals(ScenarioActionTypeEnum.REJECTED, scenarioAuditItem.getActionType());
        assertEquals("Scenario rejected by manager", scenarioAuditItem.getActionReason());
        assertEquals(new BigDecimal("1100.0000000000"), scenario.getGrossTotal());
        assertEquals(new BigDecimal("780.0000000000"), scenario.getNetTotal());
        assertEquals(new BigDecimal("320.0000000000"), scenario.getServiceFeeTotal());
        NtsFields ntsFields = scenario.getNtsFields();
        assertEquals("7141290b-7042-4cc6-975f-10546370adce", ntsFields.getPreServiceFeeFundId());
        assertEquals("NTS Fund Pool 3", ntsFields.getPreServiceFeeFundName());
        assertEquals(new BigDecimal("300.00"), ntsFields.getRhMinimumAmount());
        assertEquals(new BigDecimal("100.00"), ntsFields.getPostServiceFeeAmount());
        assertEquals(new BigDecimal("50.00"), ntsFields.getPreServiceFeeAmount());
        assertEquals(USER, scenario.getCreateUser());
        assertEquals(Date.from(OffsetDateTime.parse("2017-02-14T12:00:00+00:00").toInstant()),
            scenario.getCreateDate());
        assertEquals(Date.from(OffsetDateTime.parse("2018-05-14T11:41:52.735531+03:00").toInstant()),
            scenario.getUpdateDate());
        List<Usage> usages = usageRepository.findByScenarioId(SCENARIO_ID_3);
        usageRepository.deleteFromScenario(usages.stream().map(Usage::getId).collect(Collectors.toList()), USER);
        scenario = scenarioRepository.findWithAmountsAndLastAction(SCENARIO_ID_3);
        assertNotNull(scenario);
        verifyScenario(scenario, SCENARIO_ID_3, "Rejected NTS scenario with audit",
            "The description of scenario 7", NTS_PRODUCT_FAMILY, ScenarioStatusEnum.IN_PROGRESS);
        assertEquals(BigDecimal.ZERO, scenario.getGrossTotal());
        assertEquals(BigDecimal.ZERO, scenario.getNetTotal());
        assertEquals(BigDecimal.ZERO, scenario.getServiceFeeTotal());
    }

    @Test
    public void testFindArchivedWithAmountsAndLastActionForNtsScenario() {
        Scenario scenario =
            scenarioRepository.findArchivedWithAmountsAndLastAction(SCENARIO_ID_4);
        verifyScenario(scenario, SCENARIO_ID_4, SENT_TO_LM_AUDIT, "The description of scenario 8", NTS_PRODUCT_FAMILY,
            ScenarioStatusEnum.SENT_TO_LM);
        ScenarioAuditItem scenarioAuditItem = scenario.getAuditItem();
        assertEquals(ScenarioActionTypeEnum.SENT_TO_LM, scenarioAuditItem.getActionType());
        assertNull(scenarioAuditItem.getActionReason());
        assertEquals(new BigDecimal("1100.0000000000"), scenario.getGrossTotal());
        assertEquals(new BigDecimal("780.0000000000"), scenario.getNetTotal());
        assertEquals(new BigDecimal("320.0000000000"), scenario.getServiceFeeTotal());
        NtsFields ntsFields = scenario.getNtsFields();
        assertEquals("815d6736-a34e-4fc8-96c3-662a114fa7f2", ntsFields.getPreServiceFeeFundId());
        assertEquals("NTS Fund Pool 4", ntsFields.getPreServiceFeeFundName());
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
        fasUsageRepository.insert(buildUsage());
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
    public void testFindNameByNtsFundPoolId() {
        assertEquals(SENT_TO_LM_AUDIT,
            scenarioRepository.findNameByNtsFundPoolId("815d6736-a34e-4fc8-96c3-662a114fa7f2"));
        assertNull(scenarioRepository.findNameByNtsFundPoolId("59353faf-2e8f-4bf7-a160-4c6eb28e8177"));
    }

    @Test
    public void testFindNameByAaclFundPoolId() {
        assertEquals("AACL Scenario 1",
            scenarioRepository.findNameByAaclFundPoolId("39548ee4-7929-477e-b9d2-bcb1e76f8037"));
        assertNull(scenarioRepository.findNameByAaclFundPoolId("859c35e5-abda-407f-92df-02f3a51f24cf"));
    }

    @Test
    public void testFindNameBySalFundPoolId() {
        assertEquals("SAL Scenario 1",
            scenarioRepository.findNameBySalFundPoolId("462111b6-5d30-4a43-a35b-14796d34d847"));
        assertNull(scenarioRepository.findNameBySalFundPoolId("c9bc2013-4c77-42f7-8071-77fc115c55a7"));
    }

    @Test
    public void testRemove() {
        scenarioRepository.insert(buildScenario());
        assertEquals(1, scenarioRepository.findCountByName(SCENARIO_NAME));
        scenarioRepository.remove(SCENARIO_ID_1);
        assertEquals(0, scenarioRepository.findCountByName(SCENARIO_NAME));
    }

    @Test
    public void testUpdateStatus() {
        Scenario scenario = scenarioRepository.findArchivedWithAmountsAndLastAction(SCENARIO_ID_6);
        scenario.setStatus(ScenarioStatusEnum.SUBMITTED);
        scenarioRepository.updateStatus(scenario);
        Scenario updatedScenario = scenarioRepository.findArchivedWithAmountsAndLastAction(SCENARIO_ID_6);
        assertNotNull(updatedScenario);
        assertEquals(ScenarioStatusEnum.SUBMITTED, updatedScenario.getStatus());
        assertEquals(scenario.getName(), updatedScenario.getName());
        assertEquals(scenario.getDescription(), updatedScenario.getDescription());
        assertEquals(scenario.getGrossTotal(), updatedScenario.getGrossTotal());
        assertEquals(scenario.getNetTotal(), updatedScenario.getNetTotal());
        assertEquals(scenario.getServiceFeeTotal(), updatedScenario.getServiceFeeTotal());
    }

    @Test
    public void testUpdateNameById() {
        Scenario scenario = scenarioRepository.findArchivedWithAmountsAndLastAction(SCENARIO_ID_6);
        assertEquals("Scenario name 3", scenario.getName());
        scenarioRepository.updateNameById(SCENARIO_ID_6, "New scenario name", USER);
        Scenario updatedScenario = scenarioRepository.findArchivedWithAmountsAndLastAction(SCENARIO_ID_6);
        assertEquals("New scenario name", updatedScenario.getName());
        assertEquals(scenario.getStatus(), updatedScenario.getStatus());
        assertEquals(scenario.getDescription(), updatedScenario.getDescription());
        assertEquals(scenario.getGrossTotal(), updatedScenario.getGrossTotal());
        assertEquals(scenario.getNetTotal(), updatedScenario.getNetTotal());
        assertEquals(scenario.getServiceFeeTotal(), updatedScenario.getServiceFeeTotal());
    }

    @Test
    public void testFindSourceRros() {
        scenarioRepository.insert(buildScenario());
        fasUsageRepository.insert(buildUsage());
        UsageBatch batch = buildBatch("FAS batch 1");
        batchRepository.insert(batch);
        Usage usage = buildUsage();
        usage.setBatchId(batch.getId());
        fasUsageRepository.insert(usage);
        // inserting different batch with the same RRO to verify that it will be returned only once
        batch = buildBatch("FAS batch 2");
        batchRepository.insert(batch);
        usage = buildUsage();
        usage.setBatchId(batch.getId());
        fasUsageRepository.insert(usage);
        List<Rightsholder> sourceRros = scenarioRepository.findSourceRros(SCENARIO_ID_1);
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
        fasUsageRepository.insert(usage1);
        fasUsageRepository.insert(usage2);
        fasUsageRepository.insert(usage3);
        usageRepository.addToScenario(Lists.newArrayList(usage1, usage2, usage3));
        List<RightsholderPayeePair> result =
            scenarioRepository.findRightsholdersByScenarioIdAndSourceRro(SCENARIO_ID_1, 2000017010L);
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
    public void testInsertAaclScenario() {
        Scenario scenario = buildScenario();
        scenario.setProductFamily(AACL_PRODUCT_FAMILY);
        AaclFields aaclFields = new AaclFields();
        scenario.setAaclFields(aaclFields);
        aaclFields.setFundPoolId("95faddb9-27b6-422e-9de8-01f3aaa9c64d");
        List<PublicationType> pubTypes = new ArrayList<>();
        aaclFields.setPublicationTypes(pubTypes);
        pubTypes.add(buildPublicationType("2fe9c0a0-7672-4b56-bc64-9d4125fecf6e", ONE));
        pubTypes.add(buildPublicationType("68fd94c0-a8c0-4a59-bfe3-6674c4b12199", "1.50"));
        pubTypes.add(buildPublicationType("46634907-882e-4f91-b1ad-f57db945aff7", ONE));
        pubTypes.add(buildPublicationType("a3dff475-fc06-4d8c-b7cc-f093073ada6f", "4.00"));
        pubTypes.add(buildPublicationType("1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e", "1.10"));
        scenarioRepository.insert(scenario);
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        usageFilter.setProductFamily(AACL_PRODUCT_FAMILY);
        ScenarioUsageFilter scenarioUsageFilter = new ScenarioUsageFilter(usageFilter);
        scenarioUsageFilter.setId("c96cfa30-92a3-409b-adef-a76fc9e7ec1f");
        scenarioUsageFilter.setScenarioId(scenario.getId());
        filterRepository.insert(scenarioUsageFilter);
        Scenario actualScenario = scenarioRepository.findWithAmountsAndLastAction(SCENARIO_ID_1);
        AaclFields actualAaclFields = actualScenario.getAaclFields();
        assertNotNull(actualAaclFields);
        assertEquals("95faddb9-27b6-422e-9de8-01f3aaa9c64d", actualAaclFields.getFundPoolId());
        assertEquals(pubTypes, actualAaclFields.getPublicationTypes());
    }

    @Test
    public void testInsertSalScenario() {
        Scenario scenario = buildScenario();
        scenario.setProductFamily(SAL_PRODUCT_FAMILY);
        SalFields salFields = new SalFields();
        scenario.setSalFields(salFields);
        salFields.setFundPoolId("9888ce49-c550-4444-a8de-a05affa4927c");
        scenarioRepository.insert(scenario);
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        usageFilter.setProductFamily(SAL_PRODUCT_FAMILY);
        ScenarioUsageFilter scenarioUsageFilter = new ScenarioUsageFilter(usageFilter);
        scenarioUsageFilter.setId("2395bfa7-de54-4946-acd2-a7526806dca1");
        scenarioUsageFilter.setScenarioId(scenario.getId());
        filterRepository.insert(scenarioUsageFilter);
        Scenario actualScenario = scenarioRepository.findWithAmountsAndLastAction(SCENARIO_ID_1);
        SalFields actualSalFields = actualScenario.getSalFields();
        assertNotNull(actualSalFields);
        assertEquals("9888ce49-c550-4444-a8de-a05affa4927c", actualSalFields.getFundPoolId());
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
        scenarioUsageFilter.setId("4ea7c95a-c98a-4552-a566-bd4bcec37652");
        scenarioUsageFilter.setScenarioId(scenario.getId());
        filterRepository.insert(scenarioUsageFilter);
        Scenario ntsScenario = scenarioRepository.findWithAmountsAndLastAction(SCENARIO_ID_1);
        assertNotNull(ntsScenario.getNtsFields());
        assertEquals(new BigDecimal("700.00"), ntsScenario.getNtsFields().getRhMinimumAmount());
        assertUsages(
            Arrays.asList(
                buildUsage("244de0db-b50c-45e8-937c-72e033e2a3a9", "921.8750000000"),
                buildUsage("3caf371f-f2e6-47cd-af6b-1e02b79f6195", "3950.8928571429"),
                buildUsage("87666035-2bdf-49ef-8c80-1d1b281fdc34", "9877.2321428571")),
            usageRepository.findByScenarioId(SCENARIO_ID_1));
        assertUsages(
            Arrays.asList(buildScenarioExcludedUsage("0d200064-185a-4c48-bbc9-c67554e7db8e"),
                buildScenarioExcludedUsage("9bc172f4-edbb-4a62-9ffc-254336e7a56d")),
            usageRepository.findByStatuses(UsageStatusEnum.SCENARIO_EXCLUDED));
    }

    private void assertUsages(List<Usage> expected, List<Usage> actual) {
        actual.sort(Comparator.comparing(Usage::getId));
        assertEquals(CollectionUtils.size(expected), CollectionUtils.size(actual));
        IntStream.range(0, actual.size())
            .forEach(index -> verifyUsage(expected.get(index), actual.get(index)));
    }

    private UsageBatch buildBatch(String batchName) {
        UsageBatch batch = new UsageBatch();
        batch.setId(RupPersistUtils.generateUuid());
        batch.setProductFamily(FAS_PRODUCT_FAMILY);
        batch.setGrossAmount(new BigDecimal("100.00"));
        batch.setPaymentDate(LocalDate.of(2017, 1, 1));
        batch.setFiscalYear(2017);
        batch.setName(batchName);
        Rightsholder rro = new Rightsholder();
        rro.setAccountNumber(2000017000L);
        batch.setRro(rro);
        return batch;
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setBatchId(USAGE_BATCH_ID);
        usage.setScenarioId(SCENARIO_ID_1);
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
        scenario.setId(SCENARIO_ID_1);
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
        usage.setScenarioId(SCENARIO_ID_1);
        usage.setStatus(UsageStatusEnum.LOCKED);
        return usage;
    }

    private Usage buildScenarioExcludedUsage(String usageId) {
        Usage usage = new Usage();
        usage.setId(usageId);
        usage.setStatus(UsageStatusEnum.SCENARIO_EXCLUDED);
        usage.setGrossAmount(new BigDecimal("0.0000000000"));
        return usage;
    }

    private void verifyUsage(Usage expectedUsage, Usage actualUsage) {
        assertEquals(expectedUsage.getScenarioId(), actualUsage.getScenarioId());
        assertEquals(expectedUsage.getId(), actualUsage.getId());
        assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
        assertEquals(expectedUsage.getGrossAmount(), actualUsage.getGrossAmount());
    }

    private PublicationType buildPublicationType(String id, String weight) {
        PublicationType pubType = new PublicationType();
        pubType.setId(id);
        pubType.setWeight(new BigDecimal(weight));
        return pubType;
    }
}
