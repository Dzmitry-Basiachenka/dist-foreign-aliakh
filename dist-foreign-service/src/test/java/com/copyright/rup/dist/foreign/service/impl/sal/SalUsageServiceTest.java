package com.copyright.rup.dist.foreign.service.impl.sal;

import static org.easymock.EasyMock.capture;
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
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.ISalUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.Arrays;
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
    private static final String USAGE_ID_1 = "d7d15c9f-39f5-4d51-b72b-48a80f7f5388";
    private static final String USAGE_ID_2 = "c72554d7-687e-4173-8406-dbddef74da98";
    private final ISalUsageService salUsageService = new SalUsageService();

    private ISalUsageRepository salUsageRepository;
    private IUsageAuditService usageAuditService;
    private IChainExecutor<Usage> chainExecutor;

    @Before
    public void setUp() {
        salUsageRepository = createMock(ISalUsageRepository.class);
        usageAuditService = createMock(IUsageAuditService.class);
        chainExecutor = createMock(IChainExecutor.class);
        Whitebox.setInternalState(salUsageService, salUsageRepository);
        Whitebox.setInternalState(salUsageService, usageAuditService);
        Whitebox.setInternalState(salUsageService, chainExecutor);
        Whitebox.setInternalState(salUsageService, "usagesBatchSize", 100);
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
    public void testWorkPortionIdExists() {
        expect(salUsageRepository.workPortionIdExists("1201064IB2199")).andReturn(true).once();
        replay(salUsageRepository);
        assertTrue(salUsageService.workPortionIdExists("1201064IB2199"));
        verify(salUsageRepository);
    }

    @Test
    public void testWorkPortionIdExistsInBatch() {
        expect(salUsageRepository.workPortionIdExists("2312175IB3200", "b72159b1-479d-4d73-8168-556a60800602"))
            .andReturn(true).once();
        replay(salUsageRepository);
        assertTrue(salUsageService.workPortionIdExists("2312175IB3200", "b72159b1-479d-4d73-8168-556a60800602"));
        verify(salUsageRepository);
    }

    @Test
    public void testInsertItemBankDetails() {
        mockStatic(RupContextUtils.class);
        Usage usage = new Usage();
        usage.setProductFamily("SAL");
        usage.setSalUsage(new SalUsage());
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        salUsageRepository.insertItemBankDetail(usage);
        expectLastCall().once();
        usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.LOADED, "Uploaded in 'SAL Batch' Batch");
        expectLastCall().once();
        replay(salUsageRepository, usageAuditService, RupContextUtils.class);
        salUsageService.insertItemBankDetails(buildUsageBatch(), Collections.singletonList(usage));
        verify(salUsageRepository, usageAuditService, RupContextUtils.class);
    }

    @Test
    public void testSendForMatching() {
        List<String> usageIds = Arrays.asList(USAGE_ID_1, USAGE_ID_2);
        Usage usage1 = new Usage();
        usage1.setStatus(UsageStatusEnum.NEW);
        Usage usage2 = new Usage();
        usage2.setStatus(UsageStatusEnum.NEW);
        List<Usage> usages = Arrays.asList(usage1, usage2);
        expect(salUsageRepository.findByIds(usageIds)).andReturn(usages).once();
        Capture<Runnable> captureRunnable = new Capture<>();
        chainExecutor.execute(capture(captureRunnable));
        expectLastCall().once();
        chainExecutor.execute(usages, ChainProcessorTypeEnum.MATCHING);
        expectLastCall().once();
        replay(chainExecutor, salUsageRepository);
        salUsageService.sendForMatching(usageIds, "Batch name");
        assertNotNull(captureRunnable);
        Runnable runnable = captureRunnable.getValue();
        assertNotNull(runnable);
        runnable.run();
        verify(chainExecutor, salUsageRepository);
    }

    @Test
    public void testGetUsagesByIds() {
        List<String> usageIds = Collections.singletonList(USAGE_ID_1);
        List<Usage> usages = Collections.singletonList(new Usage());
        expect(salUsageRepository.findByIds(usageIds)).andReturn(usages).once();
        replay(salUsageRepository);
        assertEquals(usages, salUsageService.getUsagesByIds(usageIds));
        verify(salUsageRepository);
    }

    @Test
    public void testGetItemBankDetailGradeByWorkPortionId() {
        expect(salUsageRepository.findItemBankDetailGradeByWorkPortionId("2401064IB2188")).andReturn("K").once();
        replay(salUsageRepository);
        assertEquals("K", salUsageService.getItemBankDetailGradeByWorkPortionId("2401064IB2188"));
        verify(salUsageRepository);
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch batch = new UsageBatch();
        batch.setId("38ea9a39-0e20-4c3d-8054-0e88d403dd67");
        batch.setName("SAL Batch");
        batch.setPaymentDate(LocalDate.of(2019, 6, 30));
        return batch;
    }
}
