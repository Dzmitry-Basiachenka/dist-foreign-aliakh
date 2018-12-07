package com.copyright.rup.dist.foreign.service.impl.quartz;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import org.apache.commons.collections4.CollectionUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Quartz job to get works and setting statuses for {@link Usage}s.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 3/6/18
 *
 * @author Aliaksandr Radkevich
 */
@DisallowConcurrentExecution
@Component
public class WorksMatchingJob extends QuartzJobBean {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUsageService usageService;
    @Autowired
    @Qualifier("df.service.matchingProducer")
    private IProducer<Usage> matchingProducer;
    @Value("$RUP{dist.foreign.pi.batch_size}")
    private int batchSize;

    /**
     * Finds works and updates WrWrkInsts and statuses of {@link Usage}s.
     */
    @Override
    // TODO {pliakh} remove redundant RE catching
    // TODO {pliakh} check that job will work correctly in case of NEW usages will be processed in parallel
    public void executeInternal(JobExecutionContext context) {
        int offset = 0;
        int processedUsagesCount = 0;
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageStatus(UsageStatusEnum.NEW);
        int newUsagesCount = usageService.getUsagesCount(usageFilter);
        LOGGER.info("Send usages for works matching. Started. NewUsagesCount={}", newUsagesCount);
        for (int i = 0; i < newUsagesCount; i += batchSize) {
            List<Usage> newUsages = usageService.getUsagesByStatus(UsageStatusEnum.NEW, batchSize, offset);
            if (CollectionUtils.isNotEmpty(newUsages)) {
                processedUsagesCount += newUsages.size();
                try {
                    newUsages.forEach(matchingProducer::send);
                } catch (RuntimeException e) {
                    offset += batchSize;
                    LOGGER.warn(String.format("Send usages for works matching. Skipped. NewUsagesCount=%s. Reason=%s",
                        LogUtils.size(newUsages), e.getMessage()), e);
                }
            }
        }
        LOGGER.info("Send usages for works matching. Finished. NewUsagesCount={}, ProcessedUsagesCount={}",
            newUsagesCount, processedUsagesCount);
    }
}
