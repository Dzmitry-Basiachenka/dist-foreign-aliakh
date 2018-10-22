package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IRightsholderDiscrepancyRepository;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

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
@TestPropertySource(properties = {"test.liquibase.changelog=rightsholder-discrepancy-repository-test-data-init.groovy"})
@TransactionConfiguration
@Transactional
public class RightsholderDiscrepancyRepositoryIntegrationTest {

    private static final String SCENARIO_ID = "b1f0b236-3ae9-4a60-9fab-61db84199d6f";

    @Autowired
    private IRightsholderDiscrepancyRepository rightsholderDiscrepancyRepository;

    @Test
    public void testInsertAll() {
        List<RightsholderDiscrepancy> discrepancies =
            rightsholderDiscrepancyRepository.findByScenarioIdAndStatus(SCENARIO_ID,
                RightsholderDiscrepancyStatusEnum.IN_PROGRESS, null, null);
        assertNotNull(discrepancies);
        assertEquals(3, discrepancies.size());
        rightsholderDiscrepancyRepository.insertAll(Collections.singletonList(buildRightsholderDiscrepancy()),
            SCENARIO_ID);
        discrepancies = rightsholderDiscrepancyRepository.findByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.IN_PROGRESS, null, null);
        assertEquals(4, discrepancies.size());
    }

    @Test
    public void testFindCountByScenarioIdAndStatus() {
        assertEquals(3, rightsholderDiscrepancyRepository.findCountByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.IN_PROGRESS));
        assertEquals(0, rightsholderDiscrepancyRepository.findCountByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.APPROVED));
    }

    @Test
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
    public void testDeleteByScenarioIdAndStatus() {
        List<RightsholderDiscrepancy> discrepancies =
            rightsholderDiscrepancyRepository.findByScenarioIdAndStatus(SCENARIO_ID,
                RightsholderDiscrepancyStatusEnum.IN_PROGRESS, null, null);
        assertNotNull(discrepancies);
        assertEquals(3, discrepancies.size());
        rightsholderDiscrepancyRepository.deleteByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.IN_PROGRESS);
        discrepancies = rightsholderDiscrepancyRepository.findByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.IN_PROGRESS, null, null);
        assertEquals(0, discrepancies.size());
    }

    @Test
    public void testDeleteByScenarioId() {
        List<RightsholderDiscrepancy> discrepancies =
            rightsholderDiscrepancyRepository.findByScenarioIdAndStatus(SCENARIO_ID,
                RightsholderDiscrepancyStatusEnum.IN_PROGRESS, null, null);
        assertNotNull(discrepancies);
        assertEquals(3, discrepancies.size());
        rightsholderDiscrepancyRepository.deleteByScenarioId(SCENARIO_ID);
        discrepancies = rightsholderDiscrepancyRepository.findByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.IN_PROGRESS, null, null);
        assertEquals(0, discrepancies.size());
    }

    @Test
    public void testApproveByScenarioId() {
        assertTrue(CollectionUtils.isEmpty(rightsholderDiscrepancyRepository.findByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.APPROVED, null, null)));
        rightsholderDiscrepancyRepository.approveByScenarioId(SCENARIO_ID);
        assertEquals(3, CollectionUtils.size(rightsholderDiscrepancyRepository.findByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.APPROVED, null, null)));
    }

    @Test
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
        rightsholderDiscrepancy.setStatus(RightsholderDiscrepancyStatusEnum.IN_PROGRESS);
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
