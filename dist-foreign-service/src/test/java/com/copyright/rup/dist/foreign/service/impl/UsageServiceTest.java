package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.io.PipedOutputStream;
import java.util.List;

/**
 * Verifies {@link UsageService}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/03/17
 *
 * @author Aliaksei Pchelnikau
 */
public class UsageServiceTest {

    private IUsageRepository usageRepository;
    private IUsageService usageService;

    @Before
    public void setUp() {
        usageRepository = createMock(IUsageRepository.class);
        usageService = new UsageService();
        Whitebox.setInternalState(usageService, "usageRepository", usageRepository);
    }

    @Test
    public void testGetUsagesCount() {
        UsageFilter filter = new UsageFilter();
        filter.setFiscalYear(2017);
        expect(usageRepository.getUsagesCount(filter)).andReturn(1).once();
        replay(usageRepository);
        usageService.getUsagesCount(filter);
        verify(usageRepository);
    }

    @Test
    public void testGetUsageCountsEmptyFilter() {
        assertEquals(0, usageService.getUsagesCount(new UsageFilter()));
    }

    @Test
    public void testGetUsages() {
        List<UsageDto> usagesWithBatch = Lists.newArrayList(new UsageDto());
        Pageable pageable = new Pageable(0, 1);
        Sort sort = new Sort("detailId", Sort.Direction.ASC);
        UsageFilter filter = new UsageFilter();
        filter.setFiscalYear(2017);
        expect(usageRepository.findByFilter(filter, pageable, sort)).andReturn(usagesWithBatch).once();
        replay(usageRepository);
        List<UsageDto> result = usageService.getUsages(filter, pageable, sort);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(usageRepository);
    }

    @Test
    public void testGetUsagesEmptyFilter() {
        List<UsageDto> result = usageService.getUsages(new UsageFilter(), null, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testWriteUsageCsvReport() {
        PipedOutputStream outputStream = new PipedOutputStream();
        UsageFilter usageFilter = new UsageFilter();
        usageRepository.writeUsagesCsvReport(usageFilter, outputStream);
        expectLastCall().once();
        replay(usageRepository);
        usageService.writeUsageCsvReport(usageFilter, outputStream);
        verify(usageRepository);
    }

    @Test
    public void testInsertUsages() {
        List<Usage> usages = Lists.newArrayList(new Usage(), new Usage());
        usageRepository.insertUsage(usages.get(0));
        expectLastCall().once();
        usageRepository.insertUsage(usages.get(1));
        expectLastCall().once();
        replay(usageRepository);
        assertEquals(2, usageService.insertUsages(new UsageBatch(), usages, "User Name"));
        verify(usageRepository);
    }

    @Test
    public void testDeleteUsageBatchDetails() {
        String batchId = RupPersistUtils.generateUuid();
        usageRepository.deleteUsageBatchDetails(batchId);
        expectLastCall().once();
        replay(usageRepository);
        usageService.deleteUsageBatchDetails(batchId);
        verify(usageRepository);
    }
}
