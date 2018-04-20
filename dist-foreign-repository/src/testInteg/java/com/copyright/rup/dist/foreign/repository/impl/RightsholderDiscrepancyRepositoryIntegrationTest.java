package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IRightsholderDiscrepancyRepository;

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
            rightsholderDiscrepancyRepository.findByScenarioId(SCENARIO_ID, null, null);
        assertEquals(2, discrepancies.size());
        rightsholderDiscrepancyRepository.insertAll(Collections.singletonList(buildRightsholderDiscrepancy()),
            SCENARIO_ID);
        discrepancies = rightsholderDiscrepancyRepository.findByScenarioId(SCENARIO_ID, null, null);
        assertEquals(3, discrepancies.size());
    }

    @Test
    public void testFindByScenarioId() {
        List<RightsholderDiscrepancy> discrepancies =
            rightsholderDiscrepancyRepository.findByScenarioId("3210b236-1239-4a60-9fab-888b84199321", null, null);
        assertEquals(1, discrepancies.size());
        RightsholderDiscrepancy discrepancy = discrepancies.get(0);
        assertEquals(243904752L, discrepancy.getWrWrkInst(), 0);
        assertEquals(1000002859L, discrepancy.getOldRightsholder().getAccountNumber(), 0);
        assertEquals(1000005413L, discrepancy.getNewRightsholder().getAccountNumber(), 0);
        assertEquals("100 ROAD MOVIES", discrepancy.getWorkTitle());
        assertEquals("FAS", discrepancy.getProductFamily());
    }

    @Test
    public void testDeleteByScenarioId() {
        List<RightsholderDiscrepancy> discrepancies =
            rightsholderDiscrepancyRepository.findByScenarioId(SCENARIO_ID, null, null);
        assertEquals(2, discrepancies.size());
        rightsholderDiscrepancyRepository.deleteByScenarioId(SCENARIO_ID);
        discrepancies = rightsholderDiscrepancyRepository.findByScenarioId(SCENARIO_ID, null, null);
        assertEquals(0, discrepancies.size());
    }

    private RightsholderDiscrepancy buildRightsholderDiscrepancy() {
        RightsholderDiscrepancy rightsholderDiscrepancy = new RightsholderDiscrepancy();
        rightsholderDiscrepancy.setNewRightsholder(buildRightsholder(1000009997L));
        rightsholderDiscrepancy.setOldRightsholder(buildRightsholder(1000002859L));
        rightsholderDiscrepancy.setProductFamily("FAS");
        rightsholderDiscrepancy.setWorkTitle("Work title");
        rightsholderDiscrepancy.setWrWrkInst(123456789L);
        rightsholderDiscrepancy.setStatus(RightsholderDiscrepancyStatusEnum.APPROVED);
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
