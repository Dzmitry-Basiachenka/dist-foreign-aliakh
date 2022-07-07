package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioRepository;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Date;
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
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class AclScenarioRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "acl-scenario-repository-integration-test/";
    private static final String LICENSE_TYPE_ACL = "ACL";
    private static final String SCENARIO_UID_1 = "d18d7cab-8a69-4b60-af5a-0a0c99b8a4d3";
    private static final String SCENARIO_UID_2 = "53a1c4e8-f1fe-4b17-877e-2d721b2059b5";
    private static final String DESCRIPTION = "Description";
    private static final String USER_NAME = "user@copyright.com";
    private static final String DATE = "2022-02-14T12:00:00+00:00";

    @Autowired
    private IAclScenarioRepository aclScenarioRepository;

    @Test
    @TestData(fileName = FOLDER_NAME + "find-all.groovy")
    public void testFindAll() {
        AclScenario scenario1 = buildAclScenario("c65e9c0a-006f-4b79-b828-87d2106330b7",
            "274ad62f-365e-41a6-a169-0e85e04d52d4", "2474c9ae-dfaf-404f-b4eb-17b7c88794d2",
            "7e89e5c4-7db6-44b6-9a82-43166ec8da63", "ACL Scenario 202212", DESCRIPTION,
            ScenarioStatusEnum.IN_PROGRESS, true, 202212, LICENSE_TYPE_ACL, USER_NAME, DATE);
        AclScenario scenario2 = buildAclScenario("1995d50d-41c6-4e81-8c82-51a983bbecf8",
            "2a173b41-75e3-4478-80ef-157527b18996", "65b930f1-777d-4a51-b878-bea3c68624d8",
            "83e881cf-b258-42c1-849e-b2ec32b302b5", "ACL Scenario 202112", null, ScenarioStatusEnum.IN_PROGRESS,
            false, 202112, LICENSE_TYPE_ACL, "auser@copyright.com", "2021-02-14T12:00:00+00:00");
        List<AclScenario> scenarios = aclScenarioRepository.findAll();
        assertEquals(2, CollectionUtils.size(scenarios));
        verifyAclScenario(scenario1, scenarios.get(0));
        verifyAclScenario(scenario2, scenarios.get(1));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-all.groovy")
    public void testFindCountByName() {
        assertEquals(1, aclScenarioRepository.findCountByName("ACL Scenario 202212"));
        assertEquals(0, aclScenarioRepository.findCountByName("ACL Scenario"));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-with-amounts-and-last-action.groovy")
    public void testFindWithAmountsAndLastAction() {
        AclScenarioDto scenario = aclScenarioRepository.findWithAmountsAndLastAction(SCENARIO_UID_1);
        assertNotNull(scenario);
        assertEquals(SCENARIO_UID_1, scenario.getId());
        assertEquals("1b48301c-e953-4af1-8ccb-8b3f9ed31544", scenario.getFundPoolId());
        assertEquals("30d8a41f-9b01-42cd-8041-ce840512a040", scenario.getUsageBatchId());
        assertEquals("b175a252-2fb9-47da-8d40-8ad82107f546", scenario.getGrantSetId());
        assertEquals("ACL Scenario 202012", scenario.getName());
        assertEquals("some description", scenario.getDescription());
        assertEquals(ScenarioStatusEnum.SUBMITTED, scenario.getStatus());
        assertTrue(scenario.isEditableFlag());
        assertEquals(202012, scenario.getPeriodEndDate().intValue());
        assertEquals(LICENSE_TYPE_ACL, scenario.getLicenseType());
        ScenarioAuditItem auditItem = scenario.getAuditItem();
        assertNotNull(auditItem);
        assertEquals(ScenarioActionTypeEnum.SUBMITTED, auditItem.getActionType());
        assertEquals("Scenario submitted for approval", auditItem.getActionReason());
        assertEquals(new BigDecimal("300.0000000000"), scenario.getGrossTotal());
        assertEquals(new BigDecimal("100.0000000000"), scenario.getGrossTotalPrint());
        assertEquals(new BigDecimal("200.0000000000"), scenario.getGrossTotalDigital());
        assertEquals(new BigDecimal("48.0000000000"), scenario.getServiceFeeTotal());
        assertEquals(new BigDecimal("16.0000000000"), scenario.getServiceFeeTotalPrint());
        assertEquals(new BigDecimal("32.0000000000"), scenario.getServiceFeeTotalDigital());
        assertEquals(new BigDecimal("252.0000000000"), scenario.getNetTotal());
        assertEquals(new BigDecimal("84.0000000000"), scenario.getNetTotalPrint());
        assertEquals(new BigDecimal("168.0000000000"), scenario.getNetTotalDigital());
        assertEquals(1, scenario.getNumberOfRhsPrint());
        assertEquals(1, scenario.getNumberOfRhsDigital());
        assertEquals(1, scenario.getNumberOfWorksPrint());
        assertEquals(1, scenario.getNumberOfWorksDigital());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-with-amounts-and-last-action.groovy")
    public void testFindWithAmountsAndLastActionEmpty() {
        AclScenarioDto scenario = aclScenarioRepository.findWithAmountsAndLastAction(SCENARIO_UID_2);
        assertNotNull(scenario);
        assertEquals(SCENARIO_UID_2, scenario.getId());
        assertEquals("e8a591d8-2803-4f9e-8cf5-4cd6257917e8", scenario.getFundPoolId());
        assertEquals("794481d7-41e5-44b5-929b-87f379b28ffa", scenario.getUsageBatchId());
        assertEquals("fb637adf-04a6-4bee-b195-8cbde93bf672", scenario.getGrantSetId());
        assertEquals("ACL Scenario 202112", scenario.getName());
        assertEquals("another description", scenario.getDescription());
        assertEquals(ScenarioStatusEnum.IN_PROGRESS, scenario.getStatus());
        assertFalse(scenario.isEditableFlag());
        assertEquals(202112, scenario.getPeriodEndDate().intValue());
        assertEquals(LICENSE_TYPE_ACL, scenario.getLicenseType());
        ScenarioAuditItem auditItem = scenario.getAuditItem();
        assertNotNull(auditItem);
        assertNull(auditItem.getActionType());
        assertNull(auditItem.getActionReason());
        assertEquals(BigDecimal.ZERO, scenario.getGrossTotal());
        assertEquals(BigDecimal.ZERO, scenario.getGrossTotalPrint());
        assertEquals(BigDecimal.ZERO, scenario.getGrossTotalDigital());
        assertEquals(BigDecimal.ZERO, scenario.getServiceFeeTotal());
        assertEquals(BigDecimal.ZERO, scenario.getServiceFeeTotalPrint());
        assertEquals(BigDecimal.ZERO, scenario.getServiceFeeTotalDigital());
        assertEquals(BigDecimal.ZERO, scenario.getNetTotal());
        assertEquals(BigDecimal.ZERO, scenario.getNetTotalPrint());
        assertEquals(BigDecimal.ZERO, scenario.getNetTotalDigital());
        assertEquals(0, scenario.getNumberOfRhsPrint());
        assertEquals(0, scenario.getNumberOfRhsDigital());
        assertEquals(0, scenario.getNumberOfWorksPrint());
        assertEquals(0, scenario.getNumberOfWorksDigital());
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
        List<AclScenario> aclScenarios = aclScenarioRepository.findAll();
        assertEquals(1, aclScenarios.size());
        verifyAclScenario(expectedScenario, aclScenarios.get(0));
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
        verifyAclScenario(expectedScenario, aclScenarioRepository.findById(expectedScenario.getId()));
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
        verifyAclScenario(expectedScenario, aclScenarioRepository.findById(expectedScenario.getId()));
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
        verifyAclScenario(expectedScenario, aclScenarioRepository.findById(expectedScenario.getId()));
    }

    private DetailLicenseeClass buildDetailLicenseeClass(Integer detailLicenseeClassId,
                                                         Integer aggregateLicenseeClassId) {
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        detailLicenseeClass.setId(detailLicenseeClassId);
        AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
        aggregateLicenseeClass.setId(aggregateLicenseeClassId);
        detailLicenseeClass.setAggregateLicenseeClass(aggregateLicenseeClass);
        return detailLicenseeClass;
    }

    private UsageAge buildUsageAge(Integer period, BigDecimal weight) {
        UsageAge usageAge = new UsageAge();
        usageAge.setPeriod(period);
        usageAge.setWeight(weight);
        return usageAge;
    }

    private PublicationType buildPublicationType(String publicationTypeId, BigDecimal weight) {
        PublicationType publicationType = new PublicationType();
        publicationType.setId(publicationTypeId);
        publicationType.setWeight(weight);
        return publicationType;
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
        scenario.setDetailLicenseeClasses(Collections.singletonList(buildDetailLicenseeClass(43, 1)));
        scenario.setPublicationTypes(Collections.singletonList(
            buildPublicationType("73876e58-2e87-485e-b6f3-7e23792dd214", new BigDecimal("1.00"))));
        scenario.setUsageAges(Collections.singletonList(buildUsageAge(0, new BigDecimal("1.00000"))));
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
        assertEquals(expectedAclScenario.getCreateUser(), actualAclScenario.getCreateUser());
        assertEquals(expectedAclScenario.getUpdateUser(), actualAclScenario.getUpdateUser());
        verifyAclScenarioUsageAge(expectedAclScenario.getUsageAges().get(0),
            actualAclScenario.getUsageAges().get(0));
        verifyAclScenarioLicenseeClasses(expectedAclScenario.getDetailLicenseeClasses().get(0),
            actualAclScenario.getDetailLicenseeClasses().get(0));
        verifyAclScenarioPublicationType(expectedAclScenario.getPublicationTypes().get(0),
            actualAclScenario.getPublicationTypes().get(0));
    }

    private void verifyAclScenarioLicenseeClasses(DetailLicenseeClass expectedDetailLicenseeClass,
                                                  DetailLicenseeClass actualDetailLicenseeClass) {
        assertEquals(expectedDetailLicenseeClass.getId(), actualDetailLicenseeClass.getId());
        assertEquals(expectedDetailLicenseeClass.getAggregateLicenseeClass().getId(),
            actualDetailLicenseeClass.getAggregateLicenseeClass().getId());
    }

    private void verifyAclScenarioPublicationType(PublicationType expectedPublicationType,
                                                  PublicationType actualPublicationType) {
        assertEquals(expectedPublicationType.getId(), actualPublicationType.getId());
        assertEquals(expectedPublicationType.getWeight(), actualPublicationType.getWeight());
    }

    private void verifyAclScenarioUsageAge(UsageAge expectedUsageAge, UsageAge actualUsageAge) {
        assertEquals(expectedUsageAge.getPeriod(), actualUsageAge.getPeriod());
        assertEquals(expectedUsageAge.getWeight(), actualUsageAge.getWeight());
    }
}
