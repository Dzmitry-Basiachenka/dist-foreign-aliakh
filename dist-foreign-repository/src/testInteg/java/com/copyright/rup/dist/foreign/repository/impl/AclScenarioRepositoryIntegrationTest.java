package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioRepository;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.OffsetDateTime;
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

    @Autowired
    private IAclScenarioRepository aclScenarioRepository;

    @Test
    @TestData(fileName = FOLDER_NAME + "find-all.groovy")
    public void testFindAll() {
        AclScenario scenario1 = buildAclScenario("c65e9c0a-006f-4b79-b828-87d2106330b7",
            "274ad62f-365e-41a6-a169-0e85e04d52d4", "2474c9ae-dfaf-404f-b4eb-17b7c88794d2",
            "7e89e5c4-7db6-44b6-9a82-43166ec8da63", "ACL Scenario 202212", "Description",
            ScenarioStatusEnum.IN_PROGRESS, true, 202212, "ACL", "user@copyright.com", "2022-02-14T12:00:00+00:00");
        AclScenario scenario2 = buildAclScenario("1995d50d-41c6-4e81-8c82-51a983bbecf8",
            "2a173b41-75e3-4478-80ef-157527b18996", "65b930f1-777d-4a51-b878-bea3c68624d8",
            "83e881cf-b258-42c1-849e-b2ec32b302b5", "ACL Scenario 202112", null, ScenarioStatusEnum.IN_PROGRESS,
            false, 202112, "ACL", "auser@copyright.com", "2021-02-14T12:00:00+00:00");
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
        assertEquals(expectedAclScenario.getCreateDate(), actualAclScenario.getCreateDate());
        assertEquals(expectedAclScenario.getUpdateDate(), actualAclScenario.getUpdateDate());
    }
}
