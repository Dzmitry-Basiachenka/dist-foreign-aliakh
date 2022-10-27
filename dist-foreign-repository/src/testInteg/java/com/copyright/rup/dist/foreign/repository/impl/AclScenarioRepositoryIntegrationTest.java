package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioRepository;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioUsageRepository;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

/**
 * Integration test for {@link AclScenarioRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/24/2022
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class AclScenarioRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "acl-scenario-repository-integration-test/";
    private static final String FIND_ALL_FILE = "find-all.groovy";
    private static final String SCENARIO_UID = "cf1b6b34-0a67-4177-a456-4429f20fe2c5";
    private static final String SCENARIO_NAME = "ACL Scenario 201812";
    private static final String LICENSE_TYPE_ACL = "ACL";
    private static final String DESCRIPTION = "Description";
    private static final String USER_NAME = "user@copyright.com";
    private static final String DATE = "2022-02-14T12:00:00+00:00";
    private final AclScenario scenario1 = buildAclScenario("c65e9c0a-006f-4b79-b828-87d2106330b7",
        "274ad62f-365e-41a6-a169-0e85e04d52d4", "2474c9ae-dfaf-404f-b4eb-17b7c88794d2",
        "7e89e5c4-7db6-44b6-9a82-43166ec8da63", "ACL Scenario 202212", DESCRIPTION,
        ScenarioStatusEnum.IN_PROGRESS, true, 202212, LICENSE_TYPE_ACL, USER_NAME, DATE);
    private final AclScenario scenario2 = buildAclScenario("1995d50d-41c6-4e81-8c82-51a983bbecf8",
        "2a173b41-75e3-4478-80ef-157527b18996", "65b930f1-777d-4a51-b878-bea3c68624d8",
        "83e881cf-b258-42c1-849e-b2ec32b302b5", "ACL Scenario 202112", null, ScenarioStatusEnum.IN_PROGRESS,
        false, 202112, LICENSE_TYPE_ACL, "auser@copyright.com", "2021-02-14T12:00:00+00:00");

    @Autowired
    private IAclScenarioRepository aclScenarioRepository;
    @Autowired
    private IAclScenarioUsageRepository aclScenarioUsageRepository;

    @Test
    @TestData(fileName = FOLDER_NAME + FIND_ALL_FILE)
    public void testFindAll() {
        List<AclScenario> scenarios = aclScenarioRepository.findAll();
        assertEquals(2, CollectionUtils.size(scenarios));
        verifyAclScenario(scenario1, scenarios.get(0));
        verifyAclScenario(scenario2, scenarios.get(1));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + FIND_ALL_FILE)
    public void testFindCountByName() {
        assertEquals(1, aclScenarioRepository.findCountByName("ACL Scenario 202212"));
        assertEquals(0, aclScenarioRepository.findCountByName("ACL Scenario"));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + FIND_ALL_FILE)
    public void testFindByPeriod() {
        List<AclScenario> scenarios = aclScenarioRepository.findByPeriod(202212);
        assertEquals(1, scenarios.size());
        verifyAclScenario(scenario1, scenarios.get(0));
        scenarios = aclScenarioRepository.findByPeriod(202112);
        assertEquals(1, scenarios.size());
        verifyAclScenario(scenario2, scenarios.get(0));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + FIND_ALL_FILE)
    public void testPeriods() {
        assertEquals(Arrays.asList(202212, 202112), aclScenarioRepository.findPeriods());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "insert-scenario.groovy")
    public void testInsertScenario() {
        AclScenario expectedScenario = buildAclScenario("d582352f-11c3-47f2-b0e1-1e3a57aaa1af",
            "4426b58d-9df9-4c5e-842f-b179ba87989f", "61be2512-cbf7-4d39-94d7-acf419238dbd",
            "924aab3c-b2e0-4115-b46a-cb6cfe70fb61", "ACL Scenario 202206", DESCRIPTION,
            ScenarioStatusEnum.IN_PROGRESS, true, 202212, LICENSE_TYPE_ACL, USER_NAME, DATE);
        aclScenarioRepository.insertAclScenario(expectedScenario);
        aclScenarioRepository.insertAclScenarioUsageAgeWeight(expectedScenario.getUsageAges().get(0),
            expectedScenario.getId(), USER_NAME);
        aclScenarioRepository.insertAclScenarioPubTypeWeight(expectedScenario.getPublicationTypes().get(0),
            expectedScenario.getId(), USER_NAME);
        aclScenarioRepository.insertAclScenarioLicenseeClass(expectedScenario.getDetailLicenseeClasses().get(0),
            expectedScenario.getId(), USER_NAME);
        AclScenario aclScenario = aclScenarioRepository.findById("d582352f-11c3-47f2-b0e1-1e3a57aaa1af");
        assertNotNull(aclScenario);
        verifyFullAclScenario(expectedScenario, aclScenario);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + FIND_ALL_FILE)
    public void testFindScenarioById() {
        verifyAclScenario(scenario2, aclScenarioRepository.findById("1995d50d-41c6-4e81-8c82-51a983bbecf8"));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "insert-scenario-usage-age.groovy")
    public void testInsertAclScenarioUsageAgeWeight() {
        AclScenario expectedScenario = buildAclScenario("7318b083-278b-44f3-8d3c-7fd22083443a",
            "9695d1a0-407b-485d-ad0e-7471f4cb5607", "fb1d0635-b52d-4d4a-9412-d370481657d8",
            "4448e5d8-a008-4481-abb8-e75af1329637", "ACL Scenario 201806", DESCRIPTION,
            ScenarioStatusEnum.IN_PROGRESS, true, 202012, LICENSE_TYPE_ACL, USER_NAME, DATE);
        aclScenarioRepository.insertAclScenarioUsageAgeWeight(expectedScenario.getUsageAges().get(0),
            expectedScenario.getId(), USER_NAME);
        verifyFullAclScenario(expectedScenario, aclScenarioRepository.findById(expectedScenario.getId()));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "insert-scenario-publication-type.groovy")
    public void testInsertAclScenarioPublicationTypeWeight() {
        AclScenario expectedScenario = buildAclScenario("798964c8-e267-46e2-8685-29e84a5ab8c0",
            "3ef4e970-7a40-4909-b0a3-7a59c8a28e82", "4a716c5e-ef92-4507-9283-c2be0f622494",
            "d6209e69-527b-469e-8ba8-2f83ef4f99aa", "ACL Scenario 201712", DESCRIPTION,
            ScenarioStatusEnum.IN_PROGRESS, true, 202012, LICENSE_TYPE_ACL, USER_NAME, DATE);
        aclScenarioRepository.insertAclScenarioPubTypeWeight(expectedScenario.getPublicationTypes().get(0),
            expectedScenario.getId(), USER_NAME);
        verifyFullAclScenario(expectedScenario, aclScenarioRepository.findById(expectedScenario.getId()));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "insert-scenario-licensee-class.groovy")
    public void testInsertAclScenarioLicenseeClass() {
        AclScenario expectedScenario = buildAclScenario("d18b4933-98c7-45b0-9775-1a86f3525636",
            "881e39b4-b8dc-4df5-97c1-e6dad801a3b0", "3b960493-95f6-44b6-bc85-30021a18c65c",
            "92362a0f-75a0-4746-8ccf-082041023016", "ACL Scenario 201512", DESCRIPTION,
            ScenarioStatusEnum.IN_PROGRESS, true, 202012, LICENSE_TYPE_ACL, USER_NAME, DATE);
        aclScenarioRepository.insertAclScenarioLicenseeClass(expectedScenario.getDetailLicenseeClasses().get(0),
            expectedScenario.getId(), USER_NAME);
        verifyFullAclScenario(expectedScenario, aclScenarioRepository.findById(expectedScenario.getId()));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-scenario-names-by-usage-batch-id.groovy")
    public void testFindScenarioNamesByUsageBatchId() {
        assertEquals(2,
            aclScenarioRepository.findScenarioNamesByUsageBatchId("b52b5b2d-77b3-42f1-89a1-85a0c083fd0a").size());
        assertTrue(CollectionUtils.isEmpty(
            aclScenarioRepository.findScenarioNamesByUsageBatchId("818d564a-2db7-431f-8ea1-fe74dfdd0973")));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-scenario-names-by-fund-pool-id.groovy")
    public void testFindScenarioNamesByFundPoolId() {
        assertEquals(2,
            aclScenarioRepository.findScenarioNamesByFundPoolId("f8e623b0-7e18-4a06-a754-0a81decff96f").size());
        assertTrue(CollectionUtils.isEmpty(
            aclScenarioRepository.findScenarioNamesByFundPoolId("7df8b1ec-c464-42a8-aa28-52bb5bc7cb7b")));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-scenario-names-by-grant-set-id.groovy")
    public void testFindScenarioNamesByGrantSetId() {
        assertEquals(2,
            aclScenarioRepository.findScenarioNamesByGrantSetId("60e31e39-bac9-4e51-8d5d-009f1ec334fa").size());
        assertTrue(CollectionUtils.isEmpty(
            aclScenarioRepository.findScenarioNamesByGrantSetId("f71caf7b-410d-4a8e-b933-e93922067269")));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-scenario-id.groovy")
    public void testFindUsageAgeWeightsByScenarioId() {
        List<UsageAge> usageAges = aclScenarioRepository.findUsageAgeWeightsByScenarioId(SCENARIO_UID);
        assertEquals(2, usageAges.size());
        verifyUsageAge(usageAges.get(0), buildUsageAge(0, "1.00000"));
        verifyUsageAge(usageAges.get(1), buildUsageAge(1, "2.00000"));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-scenario-id.groovy")
    public void testFindAclPublicationTypesByScenarioId() {
        List<AclPublicationType> publicationTypes =
            aclScenarioRepository.findAclPublicationTypesByScenarioId(SCENARIO_UID);
        assertEquals(2, publicationTypes.size());
        verifyAclPublicationType(publicationTypes.get(0),
            buildAclPublicationType(null, "BK", "Book", "1.00", 201506));
        verifyAclPublicationType(publicationTypes.get(1),
            buildAclPublicationType(null, "NL", "Newsletter", "2.00", 201506));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-scenario-id.groovy")
    public void testFindDetailLicenseeClassesByScenarioId() {
        List<DetailLicenseeClass> detailLicenseeClasses =
            aclScenarioRepository.findDetailLicenseeClassesByScenarioId(SCENARIO_UID);
        assertEquals(2, detailLicenseeClasses.size());
        verifyDetailLicenseeClass(detailLicenseeClasses.get(0),
            buildDetailLicenseeClass(1, "Food and Tobacco", 51, "Materials"));
        verifyDetailLicenseeClass(detailLicenseeClasses.get(1),
            buildDetailLicenseeClass(2, "Textiles, Apparel, etc.", 52, "Medical"));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "remove-scenario.groovy")
    public void testRemove() {
        aclScenarioRepository.insertAclScenario(buildAclScenario(SCENARIO_UID,
            "4426b58d-9df9-4c5e-842f-b179ba87989f", "61be2512-cbf7-4d39-94d7-acf419238dbd",
            "924aab3c-b2e0-4115-b46a-cb6cfe70fb61", SCENARIO_NAME, DESCRIPTION,
            ScenarioStatusEnum.IN_PROGRESS, true, 202012, LICENSE_TYPE_ACL, USER_NAME, DATE));
        assertEquals(1, aclScenarioRepository.findCountByName(SCENARIO_NAME));
        aclScenarioRepository.deleteScenario(SCENARIO_UID);
        assertEquals(0, aclScenarioRepository.findCountByName(SCENARIO_NAME));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "remove-scenario-data.groovy")
    public void testRemoveScenarioData() {
        String scenarioId = "8e4f30ca-48c8-4349-8d5a-ffc393a74a30";
        AclScenario scenario = aclScenarioRepository.findById(scenarioId);
        assertEquals(2, scenario.getDetailLicenseeClasses().size());
        assertEquals(2, scenario.getPublicationTypes().size());
        assertEquals(2, scenario.getUsageAges().size());
        assertNotNull(aclScenarioUsageRepository.findScenarioDetailsByScenarioId(scenarioId));
        aclScenarioRepository.deleteScenarioData(scenarioId);
        aclScenarioRepository.findAclPublicationTypesByScenarioId(scenarioId);
        assertEquals(0, aclScenarioRepository.findAclPublicationTypesByScenarioId(scenarioId).size());
        assertEquals(0, aclScenarioRepository.findDetailLicenseeClassesByScenarioId(scenarioId).size());
        assertEquals(0, aclScenarioRepository.findAclPublicationTypesByScenarioId(scenarioId).size());
        assertEquals(0, aclScenarioUsageRepository.findScenarioDetailsByScenarioId(scenarioId).size());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-statuses.groovy")
    public void testFindAclScenariosByStatuses() {
        List<Scenario> scenarios =
            aclScenarioRepository.findAclScenariosByStatuses(EnumSet.of(ScenarioStatusEnum.IN_PROGRESS));
        assertEquals(1, scenarios.size());
        Scenario scenario = scenarios.get(0);
        assertEquals("d4525438-49a1-486a-a651-40b9099ece3a", scenario.getId());
        assertEquals("ACL Scenario 202112", scenario.getName());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-submitted-scenario.groovy")
    public void testSubmittedScenarioExistWithLicenseTypeAndPeriod() {
        assertTrue(aclScenarioRepository.submittedScenarioExistWithLicenseTypeAndPeriod("ACL", 202012));
        assertFalse(aclScenarioRepository.submittedScenarioExistWithLicenseTypeAndPeriod("MACL", 202206));
        assertFalse(aclScenarioRepository.submittedScenarioExistWithLicenseTypeAndPeriod("ACL", 201506));
        assertFalse(aclScenarioRepository.submittedScenarioExistWithLicenseTypeAndPeriod("MACL", 201506));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "update-status.groovy")
    public void testUpdateStatus() {
        AclScenario scenario = aclScenarioRepository.findById("3beec0c7-6783-4481-9648-c02f4ec1da5a");
        assertNotNull(scenario);
        scenario.setStatus(ScenarioStatusEnum.SUBMITTED);
        aclScenarioRepository.updateStatus(scenario);
        AclScenario updatedScenario = aclScenarioRepository.findById("3beec0c7-6783-4481-9648-c02f4ec1da5a");
        assertNotNull(updatedScenario);
        assertEquals(ScenarioStatusEnum.SUBMITTED, updatedScenario.getStatus());
        assertEquals(scenario.getName(), updatedScenario.getName());
        assertEquals(scenario.getDescription(), updatedScenario.getDescription());
        assertEquals("SYSTEM", updatedScenario.getUpdateUser());
    }

    private UsageAge buildUsageAge(Integer period, String weight) {
        UsageAge usageAge = new UsageAge();
        usageAge.setPeriod(period);
        usageAge.setWeight(new BigDecimal(weight));
        return usageAge;
    }

    private AclPublicationType buildAclPublicationType(String publicationTypeId, String name, String description,
                                                       String weight, int period) {
        AclPublicationType publicationType = new AclPublicationType();
        publicationType.setId(publicationTypeId);
        publicationType.setName(name);
        publicationType.setDescription(description);
        publicationType.setWeight(new BigDecimal(weight));
        publicationType.setPeriod(period);
        return publicationType;
    }

    private DetailLicenseeClass buildDetailLicenseeClass(Integer detailLicenseeClassId,
                                                         String detailLicClassDescription,
                                                         Integer aggregateLicenseeClassId,
                                                         String aggregateLicClassDescription) {
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        detailLicenseeClass.setId(detailLicenseeClassId);
        detailLicenseeClass.setDescription(detailLicClassDescription);
        AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
        aggregateLicenseeClass.setId(aggregateLicenseeClassId);
        aggregateLicenseeClass.setDescription(aggregateLicClassDescription);
        detailLicenseeClass.setAggregateLicenseeClass(aggregateLicenseeClass);
        return detailLicenseeClass;
    }

    private AclScenario buildAclScenario(String id, String fundPoolId, String usageBatchId, String grantSetId,
                                         String name, String description, ScenarioStatusEnum status, boolean editable,
                                         Integer periodEndDate, String licenseType, String user, String date) {
        AclScenario scenario = new AclScenario();
        scenario.setId(id);
        scenario.setFundPoolId(fundPoolId);
        scenario.setUsageBatchId(usageBatchId);
        scenario.setGrantSetId(grantSetId);
        scenario.setName(name);
        scenario.setDescription(description);
        scenario.setStatus(status);
        scenario.setEditableFlag(editable);
        scenario.setPeriodEndDate(periodEndDate);
        scenario.setLicenseType(licenseType);
        scenario.setCreateUser(user);
        scenario.setUpdateUser(user);
        scenario.setCreateDate(Date.from(OffsetDateTime.parse(date).toInstant()));
        scenario.setUpdateDate(Date.from(OffsetDateTime.parse(date).toInstant()));
        scenario.setDetailLicenseeClasses(Collections.singletonList(
            buildDetailLicenseeClass(43, "Other - Govt", 1, "Food and Tobacco")));
        scenario.setPublicationTypes(Collections.singletonList(
            buildAclPublicationType("73876e58-2e87-485e-b6f3-7e23792dd214", "BK", "Book", "1.00", 201512)));
        scenario.setUsageAges(Collections.singletonList(buildUsageAge(0, "1.00000")));
        scenario.setCopiedFrom("Copied Scenario Name");
        return scenario;
    }

    private void verifyAclScenario(AclScenario expectedAclScenario, AclScenario actualAclScenario) {
        assertEquals(expectedAclScenario.getId(), actualAclScenario.getId());
        assertEquals(expectedAclScenario.getFundPoolId(), actualAclScenario.getFundPoolId());
        assertEquals(expectedAclScenario.getUsageBatchId(), actualAclScenario.getUsageBatchId());
        assertEquals(expectedAclScenario.getGrantSetId(), actualAclScenario.getGrantSetId());
        assertEquals(expectedAclScenario.getName(), actualAclScenario.getName());
        assertEquals(expectedAclScenario.getDescription(), actualAclScenario.getDescription());
        assertEquals(expectedAclScenario.getStatus(), actualAclScenario.getStatus());
        assertEquals(expectedAclScenario.isEditableFlag(), actualAclScenario.isEditableFlag());
        assertEquals(expectedAclScenario.getPeriodEndDate(), actualAclScenario.getPeriodEndDate());
        assertEquals(expectedAclScenario.getLicenseType(), actualAclScenario.getLicenseType());
        assertEquals(expectedAclScenario.getCopiedFrom(), actualAclScenario.getCopiedFrom());
        assertEquals(expectedAclScenario.getCreateUser(), actualAclScenario.getCreateUser());
        assertEquals(expectedAclScenario.getUpdateUser(), actualAclScenario.getUpdateUser());
    }

    private void verifyFullAclScenario(AclScenario expectedAclScenario, AclScenario actualAclScenario) {
        assertEquals(expectedAclScenario.getId(), actualAclScenario.getId());
        assertEquals(expectedAclScenario.getFundPoolId(), actualAclScenario.getFundPoolId());
        assertEquals(expectedAclScenario.getUsageBatchId(), actualAclScenario.getUsageBatchId());
        assertEquals(expectedAclScenario.getGrantSetId(), actualAclScenario.getGrantSetId());
        assertEquals(expectedAclScenario.getName(), actualAclScenario.getName());
        assertEquals(expectedAclScenario.getDescription(), actualAclScenario.getDescription());
        assertEquals(expectedAclScenario.getStatus(), actualAclScenario.getStatus());
        assertEquals(expectedAclScenario.isEditableFlag(), actualAclScenario.isEditableFlag());
        assertEquals(expectedAclScenario.getPeriodEndDate(), actualAclScenario.getPeriodEndDate());
        assertEquals(expectedAclScenario.getLicenseType(), actualAclScenario.getLicenseType());
        assertEquals(expectedAclScenario.getCopiedFrom(), actualAclScenario.getCopiedFrom());
        assertEquals(expectedAclScenario.getCreateUser(), actualAclScenario.getCreateUser());
        assertEquals(expectedAclScenario.getUpdateUser(), actualAclScenario.getUpdateUser());
        verifyUsageAge(expectedAclScenario.getUsageAges().get(0), actualAclScenario.getUsageAges().get(0));
        verifyDetailLicenseeClass(expectedAclScenario.getDetailLicenseeClasses().get(0),
            actualAclScenario.getDetailLicenseeClasses().get(0));
        verifyAclPublicationType(expectedAclScenario.getPublicationTypes().get(0),
            actualAclScenario.getPublicationTypes().get(0));
    }

    private void verifyUsageAge(UsageAge expectedUsageAge, UsageAge actualUsageAge) {
        assertEquals(expectedUsageAge.getPeriod(), actualUsageAge.getPeriod());
        assertEquals(expectedUsageAge.getWeight(), actualUsageAge.getWeight());
    }

    private void verifyAclPublicationType(AclPublicationType expectedPublicationType,
                                          AclPublicationType actualPublicationType) {
        assertEquals(expectedPublicationType.getId(), actualPublicationType.getId());
        assertEquals(expectedPublicationType.getName(), actualPublicationType.getName());
        assertEquals(expectedPublicationType.getWeight(), actualPublicationType.getWeight());
        assertEquals(expectedPublicationType.getPeriod(), actualPublicationType.getPeriod());
        assertEquals(expectedPublicationType.getDescription(), actualPublicationType.getDescription());
    }

    private void verifyDetailLicenseeClass(DetailLicenseeClass expectedDetailLicenseeClass,
                                           DetailLicenseeClass actualDetailLicenseeClass) {
        assertEquals(expectedDetailLicenseeClass.getId(), actualDetailLicenseeClass.getId());
        assertEquals(expectedDetailLicenseeClass.getDescription(), actualDetailLicenseeClass.getDescription());
        assertEquals(expectedDetailLicenseeClass.getAggregateLicenseeClass().getId(),
            actualDetailLicenseeClass.getAggregateLicenseeClass().getId());
        assertEquals(expectedDetailLicenseeClass.getAggregateLicenseeClass().getDescription(),
            actualDetailLicenseeClass.getAggregateLicenseeClass().getDescription());
    }
}
