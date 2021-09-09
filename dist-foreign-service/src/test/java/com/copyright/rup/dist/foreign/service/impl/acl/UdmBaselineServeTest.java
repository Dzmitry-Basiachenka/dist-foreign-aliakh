package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.UdmBaselineDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmBaselineRepository;

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

    @Test
    public void testGetBaselineUsageDtos() {
        UdmBaselineFilter filter = new UdmBaselineFilter();
        filter.setPeriod(202012);
        List<UdmBaselineDto> udmBaselineDtos = Collections.singletonList(new UdmBaselineDto());
        expect(baselineRepository.findDtosByFilter(filter, null, null)).andReturn(udmBaselineDtos).once();
        replay(baselineRepository);
        assertEquals(udmBaselineDtos, udmBaselineService.getBaselineUsageDtos(filter, null, null));
        verify(baselineRepository);
    }

    @Test
    public void testGetBaselineUsageDtosEmptyFilter() {
        replay(baselineRepository);
        assertEquals(Collections.EMPTY_LIST,
            udmBaselineService.getBaselineUsageDtos(new UdmBaselineFilter(), null, null));
        verify(baselineRepository);
    }

    @Test
    public void testGetBaselineUsagesCount() {
        UdmBaselineFilter filter = new UdmBaselineFilter();
        filter.setPeriod(202012);
        expect(baselineRepository.findCountByFilter(filter)).andReturn(10).once();
        replay(baselineRepository);
        assertEquals(10, udmBaselineService.getBaselineUsagesCount(filter));
        verify(baselineRepository);
    }

    @Test
    public void testGetBaselineUsagesCountEmptyFilter() {
        replay(baselineRepository);
        assertEquals(0, udmBaselineService.getBaselineUsagesCount(new UdmBaselineFilter()));
        verify(baselineRepository);
    }

    @Test
    public void testGetPeriods() {
        List<Integer> periods = Arrays.asList(202012, 201906);
        expect(baselineRepository.findPeriods()).andReturn(periods).once();
        replay(baselineRepository);
        assertEquals(periods, udmBaselineService.getPeriods());
        verify(baselineRepository);
    }
}
