package com.copyright.rup.dist.foreign.service.impl.quartz;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.IWorkMatchingService;

import org.apache.commons.collections4.CollectionUtils;
import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    private IWorkMatchingService matchingService;
    @Value("$RUP{dist.foreign.integration.works.pi.batch_size}")
    private int batchSize;

    /**
     * Finds works and updates WrWrkInsts and statuses of {@link Usage}s.
     */
    @Override
    public void executeInternal(JobExecutionContext context) {
        int offset = 0;
        StopWatch stopWatch = new Slf4JStopWatch();
        int standardNumbersCount = usageService.getStandardNumbersCount();
        int titlesCount = usageService.getTitlesCount();
        LOGGER.info("Search works. Started. IDNOsCount={}, TitlesCount={}", standardNumbersCount, titlesCount);
        for (int i = 0; i < standardNumbersCount; i += batchSize) {
            List<Usage> usagesByStandardNumber = usageService.getUsagesWithStandardNumber(batchSize, offset);
            if (CollectionUtils.isNotEmpty(usagesByStandardNumber)) {
                try {
                    matchingService.matchByIdno(usagesByStandardNumber);
                } catch (RupRuntimeException e) {
                    offset += batchSize;
                    LOGGER.warn("Search works. Skipped. INDOsCount={}", usagesByStandardNumber.size());
                }
            }
        }
        offset = 0;
        for (int i = 0; i < titlesCount; i += batchSize) {
            List<Usage> usagesByTitle = usageService.getUsagesWithTitle(batchSize, offset);
            if (CollectionUtils.isNotEmpty(usagesByTitle)) {
                try {
                    matchingService.matchByTitle(usagesByTitle);
                } catch (RupRuntimeException e) {
                    offset += batchSize;
                    LOGGER.warn("Search works. Skipped. TitlesCount={}", usagesByTitle.size());
                }
            }
        }
        List<Usage> usagesWithoutStandardNumberAndTitle = usageService.getUsagesWithoutStandardNumberAndTitle();
        if (CollectionUtils.isNotEmpty(usagesWithoutStandardNumberAndTitle)) {
            matchingService.updateStatusForUsagesWithNoStandardNumberAndTitle(usagesWithoutStandardNumberAndTitle);
        }
        LOGGER.info("Search works. Finished. IDNOsCount={}, TitlesCount={}", standardNumbersCount, titlesCount);
        stopWatch.stop("usage.matchWorks_findWorksAndUpdateStatuses");
    }
}
