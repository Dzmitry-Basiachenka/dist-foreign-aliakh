package com.copyright.rup.dist.foreign.service.impl.quartz;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.matching.MatchingProducer;
import org.apache.camel.CamelExecutionException;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

/**
 * Verifies {@link WorksMatchingJob}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/20/17
 *
 * @author Darya Baraukova
 */
public class WorksMatchingJobTest {

    private IUsageService usageService;
    private MatchingProducer matchingProducer;
    private UsageFilter usageFilter;
    private WorksMatchingJob job;

    @Before
    public void setUp() {
        usageService = createMock(IUsageService.class);
        matchingProducer = createMock(MatchingProducer.class);
        job = new WorksMatchingJob();
        Whitebox.setInternalState(job, usageService);
        Whitebox.setInternalState(job, matchingProducer);
        Whitebox.setInternalState(job, "batchSize", 2);
        usageFilter = new UsageFilter();
        usageFilter.setUsageStatus(UsageStatusEnum.NEW);
    }

    @Test
    public void testExecuteInternal() {
        Usage usage = buildUsage();
        expect(usageService.getUsagesCount(usageFilter)).andReturn(1).once();
        expect(usageService.getUsagesByStatus(UsageStatusEnum.NEW, 2, 0))
            .andReturn(Collections.singletonList(usage)).once();
        matchingProducer.send(usage);
        expectLastCall().once();
        replay(usageService, matchingProducer);
        job.executeInternal(null);
        verify(usageService, matchingProducer);
    }

    @Test
    public void testExecuteInternalProcessingByBatches() {
        Usage usage1 = buildUsage();
        Usage usage2 = buildUsage();
        Usage usage3 = buildUsage();
        expect(usageService.getUsagesCount(usageFilter)).andReturn(3).once();
        expect(usageService.getUsagesByStatus(UsageStatusEnum.NEW, 2, 0))
            .andReturn(Arrays.asList(usage1, usage2)).once();
        expect(usageService.getUsagesByStatus(UsageStatusEnum.NEW, 2, 0))
            .andReturn(Collections.singletonList(usage3)).once();
        matchingProducer.send(usage1);
        expectLastCall().once();
        matchingProducer.send(usage2);
        expectLastCall().once();
        matchingProducer.send(usage3);
        expectLastCall().once();
        replay(usageService, matchingProducer);
        job.executeInternal(null);
        verify(usageService, matchingProducer);
    }

    @Test
    public void testExecuteInternalSendingFailure() {
        Usage usage1 = buildUsage();
        Usage usage2 = buildUsage();
        Usage usage3 = buildUsage();
        expect(usageService.getUsagesCount(usageFilter)).andReturn(3).once();
        expect(usageService.getUsagesByStatus(UsageStatusEnum.NEW, 2, 0))
            .andReturn(Arrays.asList(usage1, usage2)).once();
        matchingProducer.send(usage1);
        expectLastCall().once();
        matchingProducer.send(usage2);
        expectLastCall().andThrow(
            new CamelExecutionException("Exception occurred during execution on the exchange", null)).once();
        expect(usageService.getUsagesByStatus(UsageStatusEnum.NEW, 2, 2))
            .andReturn(Collections.singletonList(usage3)).once();
        matchingProducer.send(usage3);
        expectLastCall().once();
        replay(usageService, matchingProducer);
        job.executeInternal(null);
        verify(usageService, matchingProducer);
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setStatus(UsageStatusEnum.NEW);
        usage.setProductFamily("FAS");
        usage.setArticle("DIN EN 779:2012");
        usage.setPublisher("IEEE");
        usage.setMarket("Doc Del");
        usage.setMarketPeriodFrom(2013);
        usage.setMarketPeriodTo(2018);
        usage.setNumberOfCopies(100);
        usage.setReportedValue(new BigDecimal("100.00"));
        usage.setGrossAmount(new BigDecimal("100.00"));
        return usage;
    }
}
