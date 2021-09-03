package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.expect;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.repository.api.IUdmBaselineRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Collections;

/**
 * Verifies {@link UdmBaselineService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/03/21
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(RupContextUtils.class)
public class UdmBaselineServeTest {

    private static final String USER_NAME = "user@copyright.com";
    private final UdmBaselineService udmBaselineService = new UdmBaselineService();
    private IUdmBaselineRepository baselineRepository;

    @Before
    public void setUp() {
        baselineRepository = createMock(IUdmBaselineRepository.class);
        Whitebox.setInternalState(udmBaselineService, baselineRepository);
    }

    @Test
    public void testRemoveFromBaseline() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        expect(baselineRepository.removeUmdUsagesFromBaseline(202106, USER_NAME)).andReturn(Collections.EMPTY_SET)
            .once();
        replay(baselineRepository, RupContextUtils.class);
        udmBaselineService.removeFromBaseline(202106);
        verify(baselineRepository, RupContextUtils.class);
    }
}
