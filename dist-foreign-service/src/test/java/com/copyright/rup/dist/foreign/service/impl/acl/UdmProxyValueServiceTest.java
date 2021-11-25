package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.repository.api.IUdmProxyValueRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmProxyValueService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link UdmProxyValueService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/24/2021
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RupContextUtils.class, RupPersistUtils.class})
public class UdmProxyValueServiceTest {

    private static final Integer PERIOD = 202012;
    private static final String USER_NAME = "user@copyright.com";

    private IUdmProxyValueRepository udmProxyValueRepository;
    private IUdmProxyValueService udmProxyValueService;

    @Before
    public void setUp() {
        udmProxyValueRepository = createMock(IUdmProxyValueRepository.class);
        udmProxyValueService = new UdmProxyValueService();
        Whitebox.setInternalState(udmProxyValueService, udmProxyValueRepository);
    }

    @Test
    public void testCalculateProxyValues() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        udmProxyValueRepository.deleteProxyValues(PERIOD);
        expectLastCall().once();
        udmProxyValueRepository.insertProxyValues(PERIOD, USER_NAME);
        expectLastCall().once();
        expect(udmProxyValueRepository.applyProxyValues(PERIOD, USER_NAME)).andReturn(2).once();
        replay(udmProxyValueRepository, RupContextUtils.class);
        assertEquals(2, udmProxyValueService.calculateProxyValues(PERIOD));
        verify(udmProxyValueRepository, RupContextUtils.class);
    }
}
