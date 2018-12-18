package com.copyright.rup.dist.foreign.service.impl.quartz;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    /**
     * Sends {@link Usage}s in {@link UsageStatusEnum#NEW} status on matching queue.
     */
    @Override
    public void executeInternal(JobExecutionContext context) {
        LOGGER.info("Send usages for works matching. Started.");
        List<Usage> usages = usageService.getUsagesByStatus(UsageStatusEnum.NEW);
        usages.forEach(matchingProducer::send);
        LOGGER.info("Send usages for works matching. Finished. SendUsagesCount={}", LogUtils.size(usages));
    }
}
