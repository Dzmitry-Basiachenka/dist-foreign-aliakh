package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IRightsholderDiscrepancyRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderDiscrepancyService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link RightsholderDiscrepancyService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 04/19/18
 *
 * @author Ihar Suvorau
 */
public class RightsholderDiscrepancyServiceTest {

    private static final String SCENARIO_ID = RupPersistUtils.generateUuid();
    private IRightsholderDiscrepancyRepository rightsholderDiscrepancyRepository;
    private IRightsholderDiscrepancyService rightsholderDiscrepancyService;

    @Before
    public void setUp() {
        rightsholderDiscrepancyRepository = createMock(IRightsholderDiscrepancyRepository.class);
        rightsholderDiscrepancyService = new RightsholderDiscrepancyService();
        Whitebox.setInternalState(rightsholderDiscrepancyService, rightsholderDiscrepancyRepository);
    }

    @Test
    public void testInsertDiscrepancies() {
        List<RightsholderDiscrepancy> discrepancies = Collections.singletonList(new RightsholderDiscrepancy());
        rightsholderDiscrepancyRepository.insertAll(discrepancies, SCENARIO_ID);
        expectLastCall().once();
        replay(rightsholderDiscrepancyRepository);
        rightsholderDiscrepancyService.insertDiscrepancies(discrepancies, SCENARIO_ID);
        verify(rightsholderDiscrepancyRepository);
    }

    @Test
    public void testGetInProgressDiscrepanciesCountByScenarioId() {
        expect(rightsholderDiscrepancyRepository.findInProgressCountByScenarioId(SCENARIO_ID)).andReturn(3).once();
        replay(rightsholderDiscrepancyRepository);
        assertEquals(3, rightsholderDiscrepancyService.getInProgressDiscrepanciesCountByScenarioId(SCENARIO_ID), 0);
        verify(rightsholderDiscrepancyRepository);
    }

    @Test
    public void testGetProhibitedAccountNumbers() {
        List<Long> accountNumbers = Arrays.asList(1000000001L, 1000000002L);
        expect(rightsholderDiscrepancyRepository.findProhibitedAccountNumbers(SCENARIO_ID))
            .andReturn(accountNumbers).once();
        replay(rightsholderDiscrepancyRepository);
        assertSame(accountNumbers, rightsholderDiscrepancyService.getProhibitedAccountNumbers(SCENARIO_ID));
        verify(rightsholderDiscrepancyRepository);
    }

    @Test
    public void testUpdateDiscrepanciesStatusByScenarioId() {
        rightsholderDiscrepancyRepository.approveByScenarioId(SCENARIO_ID);
        expectLastCall().once();
        replay(rightsholderDiscrepancyRepository);
        rightsholderDiscrepancyService.approveDiscrepanciesByScenarioId(SCENARIO_ID);
        verify(rightsholderDiscrepancyRepository);
    }

    @Test
    public void testGetDiscrepanciesByScenarioId() {
        List<RightsholderDiscrepancy> discrepancies = Collections.singletonList(new RightsholderDiscrepancy());
        expect(rightsholderDiscrepancyRepository.findByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.IN_PROGRESS, null, null)).andReturn(discrepancies).once();
        replay(rightsholderDiscrepancyRepository);
        assertSame(discrepancies, rightsholderDiscrepancyService.getDiscrepanciesByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.IN_PROGRESS, null, null));
        verify(rightsholderDiscrepancyRepository);
    }

    @Test
    public void testDeleteDiscrepanciesByScenarioIdAndStatus() {
        rightsholderDiscrepancyRepository.deleteByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.IN_PROGRESS);
        expectLastCall().once();
        replay(rightsholderDiscrepancyRepository);
        rightsholderDiscrepancyService.deleteDiscrepanciesByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.IN_PROGRESS);
        verify(rightsholderDiscrepancyRepository);
    }
}
