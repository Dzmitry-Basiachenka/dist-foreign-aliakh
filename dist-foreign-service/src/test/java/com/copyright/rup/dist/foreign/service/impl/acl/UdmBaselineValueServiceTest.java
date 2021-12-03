package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.UdmValueBaselineDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineValueFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmBaselineValueRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineValueService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.List;

/**
 * Verifies {@link UdmBaselineValueService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/29/2021
 *
 * @author Anton Azarenka
 */
public class UdmBaselineValueServiceTest {

    private final IUdmBaselineValueService udmBaselineValueService = new UdmBaselineValueService();
    private IUdmBaselineValueRepository baselineValueRepository;

    @Before
    public void setUp() {
        baselineValueRepository = createMock(IUdmBaselineValueRepository.class);
        Whitebox.setInternalState(udmBaselineValueService, baselineValueRepository);
    }

    @Test
    public void testGetPeriods() {
        List<Integer> periods = Arrays.asList(202006, 202112);
        expect(baselineValueRepository.findPeriods()).andReturn(periods).once();
        replay(baselineValueRepository);
        assertEquals(periods, udmBaselineValueService.getPeriods());
        verify(baselineValueRepository);
    }

    @Test
    public void testGetValuesDtosEmptyFilter() {
        List<UdmValueBaselineDto> result =
            udmBaselineValueService.getValueDtos(new UdmBaselineValueFilter(), null, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetValuesCount() {
        UdmBaselineValueFilter filter = new UdmBaselineValueFilter();
        filter.setComment("Comment");
        expect(baselineValueRepository.findCountByFilter(filter)).andReturn(1).once();
        replay(baselineValueRepository);
        assertEquals(1, udmBaselineValueService.getBaselineValueCount(filter));
        verify(baselineValueRepository);
    }
}
