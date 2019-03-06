package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.processor.IUsageJobProcessor;

import com.google.common.collect.Iterables;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * Abstract implementation of {@link IUsageJobProcessor}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/16/19
 *
 * @author Uladzislau Shalamitski
 */
public abstract class AbstractUsageJobProcessor extends AbstractUsageChainProcessor implements IUsageJobProcessor {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUsageService usageService;
    @Value("$RUP{dist.foreign.usages.batch_size}")
    private Integer usagesBatchSize;

    private UsageStatusEnum usageStatus;

    @Override
    public void jobProcess(String productFamily) {
        List<String> usageIds = usageService.getUsageIdsByStatusAndProductFamily(usageStatus, productFamily);
        if (CollectionUtils.isNotEmpty(usageIds)) {
            LogUtils.ILogWrapper usagesCount = LogUtils.size(usageIds);
            LOGGER.info("Send {} usages for processing. Started. ProductFamily={}, UsagesCount={}", usageStatus,
                productFamily, usagesCount);
            Iterables.partition(usageIds, usagesBatchSize)
                .forEach(partition -> usageService.getUsagesByIds(partition).forEach(this::process));
            LOGGER.info("Send {} usages for processing. Finished. ProductFamily={}, UsagesCount={}", usageStatus,
                productFamily, usagesCount);
        } else {
            LOGGER.info("Send {} usages for processing. Skipped. Reason=There are no usages. ProductFamily={}",
                usageStatus, productFamily);
        }
    }

    public void setUsageStatus(UsageStatusEnum usageStatus) {
        this.usageStatus = usageStatus;
    }

    void setUsageService(IUsageService usageService) {
        this.usageService = usageService;
    }

    void setUsagesBatchSize(Integer usagesBatchSize) {
        this.usagesBatchSize = usagesBatchSize;
    }
}
