package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.UdmValueStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmValueRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.List;

/**
 * Verifies {@link UdmValueService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/23/21
 *
 * @author Anton Azarenka
 */
public class UdmValueServiceTest {

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
}
