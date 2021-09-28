package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.UdmValueStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmValueRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link UdmValueService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/23/21
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(RupContextUtils.class)
public class UdmValueServiceTest {

    private static final String USER_NAME = "user@copyright.com";

    private IUdmValueRepository udmValueRepository;
    private IUdmValueService udmValueService;

    @Before
    public void setUp() {
        udmValueRepository = createMock(IUdmValueRepository.class);
        udmValueService = new UdmValueService();
        Whitebox.setInternalState(udmValueService, udmValueRepository);
    }

    @Test
    public void testGetPeriods() {
        List<Integer> periods = Arrays.asList(202006, 202112);
        expect(udmValueRepository.findPeriods()).andReturn(periods).once();
        replay(udmValueRepository);
        assertEquals(periods, udmValueService.getPeriods());
        verify(udmValueRepository);
    }

    @Test
    public void testGetValuesDtosEmptyFilter() {
        List<UdmValueDto> result = udmValueService.getValueDtos(new UdmValueFilter(), null, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetValuesCount() {
        UdmValueFilter filter = new UdmValueFilter();
        filter.setStatus(UdmValueStatusEnum.NEW);
        expect(udmValueRepository.findCountByFilter(filter)).andReturn(1).once();
        replay(udmValueRepository);
        assertEquals(1, udmValueService.getValueCount(filter));
        verify(udmValueRepository);
    }

    @Test
    public void testAssignValues() {
        mockStatic(RupContextUtils.class);
        Set<String> valueIds = Collections.singleton("49efb6c9-acb8-43e5-912e-607597581713");
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        udmValueRepository.updateAssignee(valueIds, USER_NAME, USER_NAME);
        expectLastCall().once();
        replay(udmValueRepository, RupContextUtils.class);
        udmValueService.assignValues(valueIds);
        verify(udmValueRepository, RupContextUtils.class);
    }

    @Test
    public void testUnAssignValues() {
        mockStatic(RupContextUtils.class);
        Set<String> valueIds = Collections.singleton("49efb6c9-acb8-43e5-912e-607597581713");
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        udmValueRepository.updateAssignee(valueIds, null, USER_NAME);
        expectLastCall().once();
        replay(udmValueRepository, RupContextUtils.class);
        udmValueService.unassignValues(valueIds);
        verify(udmValueRepository, RupContextUtils.class);
    }

    @Test
    public void testGetAssignees() {
        List<String> assignees = Collections.singletonList("wjohn@copyright.com");
        expect(udmValueRepository.findAssignees()).andReturn(assignees).once();
        replay(udmValueRepository);
        assertEquals(assignees, udmValueService.getAssignees());
        verify(udmValueRepository);
    }
}
