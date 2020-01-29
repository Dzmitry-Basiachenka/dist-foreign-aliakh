package com.copyright.rup.dist.foreign.service.impl.nts;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.repository.api.INtsUsageRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link NtsUsageService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/28/2020
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RupContextUtils.class})
public class NtsUsageServiceTest {

    private static final String USER_NAME = "user@copyright.com";

    private final NtsUsageService ntsUsageService = new NtsUsageService();

    private INtsUsageRepository ntsUsageRepository;

    @Before
    public void setUp() {
        ntsUsageRepository = createMock(INtsUsageRepository.class);
        Whitebox.setInternalState(ntsUsageService, ntsUsageRepository);
    }

    @Test
    public void testInsertUsages() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        UsageBatch usageBatch = new UsageBatch();
        List<String> usageIds = Collections.singletonList("92acae56-e328-4cb9-b2ab-ad696a01d71c");
        expect(ntsUsageRepository.insertUsages(usageBatch, USER_NAME)).andReturn(usageIds).once();
        replay(RupContextUtils.class, ntsUsageRepository);
        assertEquals(usageIds, ntsUsageService.insertUsages(usageBatch));
        verify(RupContextUtils.class, ntsUsageRepository);
    }

    @Test
    public void testDeleteFromPreServiceFeeFund() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        String fundPoolId = "d40dc124-83dd-463b-9102-4ff383cc94b1";
        ntsUsageRepository.deleteFromPreServiceFeeFund(fundPoolId, USER_NAME);
        expectLastCall().once();
        replay(RupContextUtils.class, ntsUsageRepository);
        ntsUsageService.deleteFromPreServiceFeeFund(fundPoolId);
        verify(RupContextUtils.class, ntsUsageRepository);
    }

    @Test
    public void testDeleteBelletristicByScenarioId() {
        String scenarioId = "3b387481-b643-47df-acc0-728cd6878d17";
        ntsUsageRepository.deleteBelletristicByScenarioId(scenarioId);
        expectLastCall().once();
        replay(ntsUsageRepository);
        ntsUsageService.deleteBelletristicByScenarioId(scenarioId);
        verify(ntsUsageRepository);
    }

    @Test
    public void testDeleteUsagesFromNtsScenario() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        String scenarioId = "fcfb154b-52b4-4c17-bde9-19677e94fa87";
        ntsUsageRepository.deleteFromScenario(scenarioId, USER_NAME);
        expectLastCall().once();
        replay(RupContextUtils.class, ntsUsageRepository);
        ntsUsageService.deleteFromScenario(scenarioId);
        verify(RupContextUtils.class, ntsUsageRepository);
    }
}
