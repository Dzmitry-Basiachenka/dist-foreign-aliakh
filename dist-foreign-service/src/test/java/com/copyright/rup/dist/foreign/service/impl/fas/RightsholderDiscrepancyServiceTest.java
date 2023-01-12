package com.copyright.rup.dist.foreign.service.impl.fas;

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
import com.copyright.rup.dist.foreign.service.api.fas.IRightsholderDiscrepancyService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

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
        List<RightsholderDiscrepancy> discrepancies = List.of(new RightsholderDiscrepancy());
        rightsholderDiscrepancyRepository.insertAll(discrepancies, SCENARIO_ID);
        expectLastCall().once();
        replay(rightsholderDiscrepancyRepository);
        rightsholderDiscrepancyService.insertDiscrepancies(discrepancies, SCENARIO_ID);
        verify(rightsholderDiscrepancyRepository);
    }

    @Test
    public void testGetCountByScenarioIdAndStatus() {
        expect(rightsholderDiscrepancyRepository.findCountByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.DRAFT)).andReturn(3).once();
        replay(rightsholderDiscrepancyRepository);
        assertEquals(3, rightsholderDiscrepancyService.getCountByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.DRAFT), 0);
        verify(rightsholderDiscrepancyRepository);
    }

    @Test
    public void testGetProhibitedAccountNumbers() {
        List<Long> accountNumbers = List.of(1000000001L, 1000000002L);
        expect(rightsholderDiscrepancyRepository.findProhibitedAccountNumbers(SCENARIO_ID))
            .andReturn(accountNumbers).once();
        replay(rightsholderDiscrepancyRepository);
        assertSame(accountNumbers, rightsholderDiscrepancyService.getProhibitedAccountNumbers(SCENARIO_ID));
        verify(rightsholderDiscrepancyRepository);
    }

    @Test
    public void testApproveByScenarioId() {
        rightsholderDiscrepancyRepository.approveByScenarioId(SCENARIO_ID);
        expectLastCall().once();
        replay(rightsholderDiscrepancyRepository);
        rightsholderDiscrepancyService.approveByScenarioId(SCENARIO_ID);
        verify(rightsholderDiscrepancyRepository);
    }

    @Test
    public void testGetByScenarioIdAndStatus() {
        List<RightsholderDiscrepancy> discrepancies = List.of(new RightsholderDiscrepancy());
        expect(rightsholderDiscrepancyRepository.findByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.DRAFT, null, null)).andReturn(discrepancies).once();
        replay(rightsholderDiscrepancyRepository);
        assertSame(discrepancies, rightsholderDiscrepancyService.getByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.DRAFT, null, null));
        verify(rightsholderDiscrepancyRepository);
    }

    @Test
    public void testDeleteByScenarioIdAndStatus() {
        rightsholderDiscrepancyRepository.deleteByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.DRAFT);
        expectLastCall().once();
        replay(rightsholderDiscrepancyRepository);
        rightsholderDiscrepancyService.deleteByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.DRAFT);
        verify(rightsholderDiscrepancyRepository);
    }

    @Test
    public void testDeleteByScenarioId() {
        rightsholderDiscrepancyRepository.deleteByScenarioId(SCENARIO_ID);
        expectLastCall().once();
        replay(rightsholderDiscrepancyRepository);
        rightsholderDiscrepancyService.deleteByScenarioId(SCENARIO_ID);
        verify(rightsholderDiscrepancyRepository);
    }
}
