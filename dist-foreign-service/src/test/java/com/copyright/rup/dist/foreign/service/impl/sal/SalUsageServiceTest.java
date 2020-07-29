package com.copyright.rup.dist.foreign.service.impl.sal;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.ISalUsageRepository;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link SalUsageService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/29/2020
 *
 * @author Aliaksandr Liakh
 */
public class SalUsageServiceTest {

    private final ISalUsageService salUsageService = new SalUsageService();

    private ISalUsageRepository salUsageRepository;

    @Before
    public void setUp() {
        salUsageRepository = createMock(ISalUsageRepository.class);
        Whitebox.setInternalState(salUsageService, salUsageRepository);
    }

    @Test
    public void testGetUsagesCount() {
        UsageFilter filter = new UsageFilter();
        filter.setFiscalYear(2020);
        expect(salUsageRepository.findCountByFilter(filter)).andReturn(1).once();
        replay(salUsageRepository);
        assertEquals(1, salUsageService.getUsagesCount(filter));
        verify(salUsageRepository);
    }

    @Test
    public void testGetUsageCountEmptyFilter() {
        assertEquals(0, salUsageService.getUsagesCount(new UsageFilter()));
    }

    @Test
    public void testGetUsageDtos() {
        UsageFilter filter = new UsageFilter();
        filter.setFiscalYear(2020);
        Pageable pageable = new Pageable(0, 1);
        Sort sort = new Sort("detailId", Sort.Direction.ASC);
        List<UsageDto> usages = Collections.singletonList(new UsageDto());
        expect(salUsageRepository.findDtosByFilter(filter, pageable, sort)).andReturn(usages).once();
        replay(salUsageRepository);
        assertEquals(usages, salUsageService.getUsageDtos(filter, pageable, sort));
        verify(salUsageRepository);
    }

    @Test
    public void testGetUsagesDtosEmptyFilter() {
        List<UsageDto> usages = salUsageService.getUsageDtos(new UsageFilter(), null, null);
        assertNotNull(usages);
        assertTrue(usages.isEmpty());
    }
}
