package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IRightsholderDiscrepancyRepository;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link RightsholderDiscrepancyRepository}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 04/18/2018
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class RightsholderDiscrepancyRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "rightsholder-discrepancy-repository-integration-test/";
    private static final String SCENARIO_ID = "b1f0b236-3ae9-4a60-9fab-61db84199d6f";

    @Autowired
    private IRightsholderDiscrepancyRepository rightsholderDiscrepancyRepository;

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-scenario-id-and-status.groovy")
    public void testInsertAll() {
        List<RightsholderDiscrepancy> discrepancies =
            rightsholderDiscrepancyRepository.findByScenarioIdAndStatus(SCENARIO_ID,
                RightsholderDiscrepancyStatusEnum.DRAFT, null, null);
        assertNotNull(discrepancies);
        assertEquals(3, discrepancies.size());
        rightsholderDiscrepancyRepository.insertAll(Collections.singletonList(buildRightsholderDiscrepancy()),
            SCENARIO_ID);
        discrepancies = rightsholderDiscrepancyRepository.findByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.DRAFT, null, null);
        assertEquals(4, discrepancies.size());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-scenario-id-and-status.groovy")
    public void testFindCountByScenarioIdAndStatus() {
        assertEquals(3, rightsholderDiscrepancyRepository.findCountByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.DRAFT));
        assertEquals(0, rightsholderDiscrepancyRepository.findCountByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.APPROVED));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-scenario-id-and-status.groovy")
    public void testFindByScenarioIdAndStatus() {
        List<RightsholderDiscrepancy> discrepancies =
            rightsholderDiscrepancyRepository.findByScenarioIdAndStatus("3210b236-1239-4a60-9fab-888b84199321",
                RightsholderDiscrepancyStatusEnum.APPROVED, null, null);
        assertNotNull(discrepancies);
        assertEquals(1, discrepancies.size());
        RightsholderDiscrepancy discrepancy = discrepancies.get(0);
        assertEquals(243904752L, discrepancy.getWrWrkInst(), 0);
        assertEquals(1000002859L, discrepancy.getOldRightsholder().getAccountNumber(), 0);
        assertEquals(1000005413L, discrepancy.getNewRightsholder().getAccountNumber(), 0);
        assertEquals("100 ROAD MOVIES", discrepancy.getWorkTitle());
        assertEquals("FAS", discrepancy.getProductFamily());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete-by-scenario-id.groovy")
    public void testDeleteByScenarioIdAndStatus() {
        List<RightsholderDiscrepancy> discrepancies =
            rightsholderDiscrepancyRepository.findByScenarioIdAndStatus(SCENARIO_ID,
                RightsholderDiscrepancyStatusEnum.DRAFT, null, null);
        assertNotNull(discrepancies);
        assertEquals(3, discrepancies.size());
        rightsholderDiscrepancyRepository.deleteByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.DRAFT);
        discrepancies = rightsholderDiscrepancyRepository.findByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.DRAFT, null, null);
        assertEquals(0, discrepancies.size());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete-by-scenario-id.groovy")
    public void testDeleteByScenarioId() {
        List<RightsholderDiscrepancy> discrepancies =
            rightsholderDiscrepancyRepository.findByScenarioIdAndStatus(SCENARIO_ID,
                RightsholderDiscrepancyStatusEnum.DRAFT, null, null);
        assertNotNull(discrepancies);
        assertEquals(3, discrepancies.size());
        rightsholderDiscrepancyRepository.deleteByScenarioId(SCENARIO_ID);
        discrepancies = rightsholderDiscrepancyRepository.findByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.DRAFT, null, null);
        assertEquals(0, discrepancies.size());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "approve-by-scenario-id.groovy")
    public void testApproveByScenarioId() {
        assertTrue(CollectionUtils.isEmpty(rightsholderDiscrepancyRepository.findByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.APPROVED, null, null)));
        rightsholderDiscrepancyRepository.approveByScenarioId(SCENARIO_ID);
        assertEquals(3, CollectionUtils.size(rightsholderDiscrepancyRepository.findByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.APPROVED, null, null)));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-prohibited-account-numbers.groovy")
    public void testFindProhibitedAccountNumbers() {
        List<Long> accountNumbers = rightsholderDiscrepancyRepository.findProhibitedAccountNumbers(SCENARIO_ID);
        assertNotNull(accountNumbers);
        assertEquals(1, accountNumbers.size());
        assertEquals(1000009997L, accountNumbers.get(0), 0);
    }

    private RightsholderDiscrepancy buildRightsholderDiscrepancy() {
        RightsholderDiscrepancy rightsholderDiscrepancy = new RightsholderDiscrepancy();
        rightsholderDiscrepancy.setNewRightsholder(buildRightsholder(1000009997L));
        rightsholderDiscrepancy.setOldRightsholder(buildRightsholder(1000002859L));
        rightsholderDiscrepancy.setProductFamily("FAS");
        rightsholderDiscrepancy.setWorkTitle("Work title");
        rightsholderDiscrepancy.setWrWrkInst(123456789L);
        rightsholderDiscrepancy.setStatus(RightsholderDiscrepancyStatusEnum.DRAFT);
        rightsholderDiscrepancy.setId(RupPersistUtils.generateUuid());
        rightsholderDiscrepancy.setCreateUser("SYSTEM");
        rightsholderDiscrepancy.setUpdateUser("SYSTEM");
        return rightsholderDiscrepancy;
    }

    private Rightsholder buildRightsholder(Long accountNumber) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setId(RupPersistUtils.generateUuid());
        return rightsholder;
    }
}
