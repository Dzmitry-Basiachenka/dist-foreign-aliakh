package com.copyright.rup.dist.foreign.service.impl.nts;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
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
        UsageBatch usageBatch = new UsageBatch();
        List<String> usageIds = Collections.singletonList("92acae56-e328-4cb9-b2ab-ad696a01d71c");
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        expect(ntsUsageRepository.insertUsages(usageBatch, USER_NAME)).andReturn(usageIds).once();
        replay(RupContextUtils.class, ntsUsageRepository);
        assertEquals(usageIds, ntsUsageService.insertUsages(usageBatch));
        verify(RupContextUtils.class, ntsUsageRepository);
    }
}
