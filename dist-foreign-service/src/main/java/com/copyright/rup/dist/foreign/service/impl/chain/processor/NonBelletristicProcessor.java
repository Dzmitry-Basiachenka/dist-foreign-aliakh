package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.nts.IWorkClassificationService;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implementation of {@link AbstractUsageChainProcessor} to check usage work classification is non Belletristic.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/22/19
 *
 * @author Pavel Liakh
 */
public class NonBelletristicProcessor extends AbstractUsageChainProcessor {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IWorkClassificationService workClassificationService;

    @Override
    @Profiled(tag = "NonBelletristicProcessor.process")
    public void process(Usage usage) {
        LOGGER.trace("Usage NonBelletristic processor. Started. UsageId={}", usage.getId());
        String classification = workClassificationService.getClassification(usage.getWrWrkInst());
        executeNextProcessor(usage, obj -> !FdaConstants.BELLETRISTIC_CLASSIFICATION.equals(classification));
        LOGGER.trace("Usage NonBelletristic processor. Finished. UsageId={}, Classification={}", usage.getId(),
            classification);
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.CLASSIFICATION;
    }

    void setWorkClassificationService(IWorkClassificationService workClassificationService) {
        this.workClassificationService = workClassificationService;
    }
}
