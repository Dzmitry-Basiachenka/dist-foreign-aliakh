package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertSame;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.repository.api.IRightsholderDiscrepancyRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderDiscrepancyService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

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
    public void testGetDiscrepanciesByScenarioId() {
        List<RightsholderDiscrepancy> discrepancies = Collections.singletonList(new RightsholderDiscrepancy());
        expect(rightsholderDiscrepancyRepository.findByScenarioId(SCENARIO_ID, null, null))
            .andReturn(discrepancies).once();
        replay(rightsholderDiscrepancyRepository);
        assertSame(discrepancies, rightsholderDiscrepancyService.getDiscrepanciesByScenarioId(SCENARIO_ID, null, null));
        verify(rightsholderDiscrepancyRepository);
    }

    @Test
    public void testDeleteDiscrepanciesByScenarioId() {
        rightsholderDiscrepancyRepository.deleteByScenarioId(SCENARIO_ID);
        expectLastCall().once();
        replay(rightsholderDiscrepancyRepository);
        rightsholderDiscrepancyService.deleteDiscrepanciesByScenarioId(SCENARIO_ID);
        verify(rightsholderDiscrepancyRepository);
    }
}
