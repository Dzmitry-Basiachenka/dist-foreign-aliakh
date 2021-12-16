package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.UdmProxyValueDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmProxyValueFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmProxyValueRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmProxyValueService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        udmProxyValueRepository.clearProxyValues(PERIOD, USER_NAME);
        expectLastCall().once();
        expect(udmProxyValueRepository.applyProxyValues(PERIOD, USER_NAME)).andReturn(2).once();
        replay(udmProxyValueRepository, RupContextUtils.class);
        assertEquals(2, udmProxyValueService.calculateProxyValues(PERIOD));
        verify(udmProxyValueRepository, RupContextUtils.class);
    }

    @Test
    public void testFindPeriods() {
        List<Integer> periods = Arrays.asList(202012, 202112);
        expect(udmProxyValueRepository.findPeriods()).andReturn(periods).once();
        replay(udmProxyValueRepository);
        assertEquals(periods, udmProxyValueService.findPeriods());
        verify(udmProxyValueRepository);
    }

    @Test
    public void testGetDtosByFilter() {
        UdmProxyValueFilter filter = new UdmProxyValueFilter();
        filter.setPeriods(Collections.singleton(202012));
        List<UdmProxyValueDto> valueDtos = Arrays.asList(new UdmProxyValueDto(), new UdmProxyValueDto());
        expect(udmProxyValueRepository.findDtosByFilter(filter)).andReturn(valueDtos).once();
        replay(udmProxyValueRepository);
        assertEquals(valueDtos, udmProxyValueService.getDtosByFilter(filter));
        verify(udmProxyValueRepository);
    }

    @Test
    public void testGetDtosByEmptyFilter() {
        replay(udmProxyValueRepository);
        assertTrue(udmProxyValueService.getDtosByFilter(new UdmProxyValueFilter()).isEmpty());
        verify(udmProxyValueRepository);
    }
}
