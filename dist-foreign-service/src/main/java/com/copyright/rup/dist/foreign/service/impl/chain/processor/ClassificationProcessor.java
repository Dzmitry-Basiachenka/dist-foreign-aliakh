package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IWorkClassificationService;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Objects;

/**
 * Implementation of {@link AbstractUsageChainProcessor} for processing usage based on work classification.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/06/19
 *
 * @author Pavel Liakh
 */
public class ClassificationProcessor extends AbstractUsageChainProcessor {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUsageRepository usageRepository;

    @Autowired
    private IWorkClassificationService workClassificationService;

    @Override
    @Profiled(tag = "ClassificationProcessor.process")
    public void process(Usage usage) {
        LOGGER.trace("Usage Classification processor. Started. UsageId={}", usage.getId());
        String classification = workClassificationService.getClassification(usage.getWrWrkInst());
        if (Objects.nonNull(classification)) {
            executeNextProcessor(usage, obj -> FdaConstants.BELLETRISTIC_CLASSIFICATION.equals(classification));
        } else {
            usageRepository.updateStatus(Collections.singleton(usage.getId()), UsageStatusEnum.UNCLASSIFIED);
        }
        LOGGER.trace("Usage Classification processor. Finished. UsageId={}, Classification={}", usage.getId(),
            classification);
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.CLASSIFICATION;
    }

    public void setUsageRepository(IUsageRepository usageRepository) {
        this.usageRepository = usageRepository;
    }

    public void setWorkClassificationService(IWorkClassificationService workClassificationService) {
        this.workClassificationService = workClassificationService;
    }
}
