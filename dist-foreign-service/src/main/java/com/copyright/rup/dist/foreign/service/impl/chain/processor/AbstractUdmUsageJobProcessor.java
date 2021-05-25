package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.service.api.processor.IUsageJobProcessor;

import com.google.common.collect.Iterables;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;
import java.util.List;

/**
 * Abstract implementation of {@link IUsageJobProcessor}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/21/2021
 *
 * @author Ihar Suvorau
 */
abstract class AbstractUdmUsageJobProcessor extends AbstractChainChunkProcessor<UdmUsage>
    implements IUsageJobProcessor {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUdmUsageService udmUsageService;
    @Value("$RUP{dist.foreign.usages.batch_size}")
    private Integer usagesBatchSize;

    private UsageStatusEnum usageStatus;

    @Override
    public JobInfo jobProcess(String productFamily) {
        JobInfo jobInfo;
        List<String> usageIds = udmUsageService.getUdmUsageIdsByStatus(usageStatus);
        if (CollectionUtils.isNotEmpty(usageIds)) {
            LogUtils.ILogWrapper usagesCount = LogUtils.size(usageIds);
            LOGGER.info("Send {} usages for processing. Started. ProductFamily=ACL (UDM), UsagesCount={}", usageStatus,
                usagesCount);
            Iterables.partition(usageIds, usagesBatchSize)
                .forEach(partition -> udmUsageService.getUdmUsagesByIds(partition)
                    .forEach(usage -> process(Collections.singletonList(usage))));
            String message = "ProductFamily=ACL (UDM), UsagesCount=" + usagesCount;
            LOGGER.info("Send {} usages for processing. Finished. {}", usageStatus, message);
            jobInfo = new JobInfo(JobStatusEnum.FINISHED, message);
        } else {
            String message = "ProductFamily=ACL (UDM), Reason=There are no usages";
            LOGGER.info("Send {} usages for processing. Skipped. {}", usageStatus, message);
            jobInfo = new JobInfo(JobStatusEnum.SKIPPED, message);
        }
        return jobInfo;
    }

    /**
     * Sets usage status for job processing.
     *
     * @param usageStatus instance of {@link UsageStatusEnum}
     */
    public void setUsageStatus(UsageStatusEnum usageStatus) {
        this.usageStatus = usageStatus;
    }
}
