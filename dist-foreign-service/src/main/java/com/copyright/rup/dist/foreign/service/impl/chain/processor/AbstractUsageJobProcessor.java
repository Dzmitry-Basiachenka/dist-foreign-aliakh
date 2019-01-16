package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.processor.IUsageJobProcessor;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

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

    private UsageStatusEnum usageStatus;

    @Override
    public void jobProcess(String productFamily) {
        List<Usage> usages = usageService.getUsagesByStatusAndProductFamily(usageStatus, productFamily);
        if (CollectionUtils.isNotEmpty(usages)) {
            LogUtils.ILogWrapper usagesCount = LogUtils.size(usages);
            LOGGER.info("Send {} usages for processing. Started. ProductFamily={}, UsagesCount={}", usageStatus,
                productFamily, usagesCount);
            usages.forEach(this::process);
            LOGGER.info("Send {} usages for processing. Finished. ProductFamily={}, UsagesCount={}", usageStatus,
                productFamily, usagesCount);
        } else {
            LOGGER.info("Send {} usages for processing. Skipped. Reason=There are no usages. ProductFamily={}",
                usageStatus, productFamily);
        }
    }

    void setUsageStatus(UsageStatusEnum usageStatus) {
        this.usageStatus = usageStatus;
    }

    void setUsageService(IUsageService usageService) {
        this.usageService = usageService;
    }
}
