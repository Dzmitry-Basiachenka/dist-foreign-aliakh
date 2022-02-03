package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UdmValueBaselineDto;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineValueFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmBaselineValueRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineValueService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collections;
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
    public void testGetValueDtos() {
        List<UdmValueBaselineDto> baselineValues = Collections.singletonList(new UdmValueBaselineDto());
        Pageable pageable = new Pageable(0, 1);
        Sort sort = new Sort("detailId", Sort.Direction.ASC);
        UdmBaselineValueFilter filter = new UdmBaselineValueFilter();
        filter.setCommentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, "Comment", null));
        expect(baselineValueRepository.findDtosByFilter(filter, pageable, sort)).andReturn(baselineValues).once();
        replay(baselineValueRepository);
        assertSame(baselineValues, udmBaselineValueService.getValueDtos(filter, pageable, sort));
        verify(baselineValueRepository);
    }

    @Test
    public void testGetValueDtosEmptyFilter() {
        List<UdmValueBaselineDto> result =
            udmBaselineValueService.getValueDtos(new UdmBaselineValueFilter(), null, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetBaselineValueCount() {
        UdmBaselineValueFilter filter = new UdmBaselineValueFilter();
        filter.setCommentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, "Comment", null));
        expect(baselineValueRepository.findCountByFilter(filter)).andReturn(1).once();
        replay(baselineValueRepository);
        assertEquals(1, udmBaselineValueService.getBaselineValueCount(filter));
        verify(baselineValueRepository);
    }

    @Test
    public void testGetBaselineValueCountEmptyFilter() {
        assertEquals(0, udmBaselineValueService.getBaselineValueCount(new UdmBaselineValueFilter()));
    }
}
