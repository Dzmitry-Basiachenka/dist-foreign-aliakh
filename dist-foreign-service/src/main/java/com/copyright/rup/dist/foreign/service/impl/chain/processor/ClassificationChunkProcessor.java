package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.nts.IWorkClassificationService;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link AbstractChainChunkProcessor} to check whether {@link Usage}s' works
 * have classification.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/06/2019
 *
 * @author Pavel Liakh
 * @author Aliaksandr Liakh
 */
public class ClassificationChunkProcessor extends AbstractChainChunkProcessor<Usage> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IWorkClassificationService workClassificationService;

    @Override
    @Profiled(tag = "ClassificationChunkProcessor.process")
    public void process(List<Usage> usages) {
        LOGGER.trace("Usages Classification processor. Started. UsageIds={}", LogUtils.ids(usages));
        executeNextChainProcessor(usages, usage -> {
            String classification = workClassificationService.getClassification(usage.getWrWrkInst());
            LOGGER.trace("Usages Classification processor. Classified. UsageId={}, Classification={}",
                usage.getId(), classification);
            return Objects.nonNull(classification);
        });
        LOGGER.trace("Usages Classification processor. Finished. UsageIds={}", LogUtils.ids(usages));
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.CLASSIFICATION;
    }
}
