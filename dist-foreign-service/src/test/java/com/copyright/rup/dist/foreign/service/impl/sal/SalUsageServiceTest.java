package com.copyright.rup.dist.foreign.service.impl.sal;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.SalUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.ISalUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
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
@RunWith(PowerMockRunner.class)
@PrepareForTest({RupContextUtils.class})
public class SalUsageServiceTest {

    private static final String USER_NAME = "user@copyright.com";
    private final ISalUsageService salUsageService = new SalUsageService();

    private ISalUsageRepository salUsageRepository;
    private IUsageAuditService usageAuditService;

    @Before
    public void setUp() {
        salUsageRepository = createMock(ISalUsageRepository.class);
        usageAuditService = createMock(IUsageAuditService.class);
        Whitebox.setInternalState(salUsageService, salUsageRepository);
        Whitebox.setInternalState(salUsageService, usageAuditService);
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

    @Test
    public void testInsertUsages() {
        mockStatic(RupContextUtils.class);
        Usage usage = new Usage();
        usage.setProductFamily("SAL");
        usage.setSalUsage(new SalUsage());
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        salUsageRepository.insert(usage);
        expectLastCall().once();
        usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.LOADED, "Uploaded in 'SAL Batch' Batch");
        expectLastCall().once();
        replay(salUsageRepository, usageAuditService, RupContextUtils.class);
        salUsageService.insertUsages(buildUsageBatch(), Collections.singletonList(usage));
        verify(salUsageRepository, usageAuditService, RupContextUtils.class);
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch batch = new UsageBatch();
        batch.setId("38ea9a39-0e20-4c3d-8054-0e88d403dd67");
        batch.setName("SAL Batch");
        batch.setPaymentDate(LocalDate.of(2019, 6, 30));
        return batch;
    }
}
