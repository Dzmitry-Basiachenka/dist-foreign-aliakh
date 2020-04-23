package com.copyright.rup.dist.foreign.service.impl.chain.processor.chunk;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.nts.IWorkClassificationService;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Implementation of {@link AbstractUsageChainChunkProcessor} to check {@link Usage}s' works classifications
 * are non Belletristic.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/22/19
 *
 * @author Pavel Liakh
 * @author Aliaksandr Liakh
 */
public class NonBelletristicChunkProcessor extends AbstractUsageChainChunkProcessor {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IWorkClassificationService workClassificationService;

    @Override
    @Profiled(tag = "NonBelletristicChunkProcessor.process")
    public void process(List<Usage> usages) {
        LOGGER.trace("Usage NonBelletristic processor. Started. UsageIds={}", LogUtils.ids(usages));
        executeNextChainProcessor(usages, usage -> {
            String classification = workClassificationService.getClassification(usage.getWrWrkInst());
            LOGGER.trace("Usage NonBelletristic processor. Classified. UsageId={}, Classification={}",
                usage.getId(), classification);
            return !FdaConstants.BELLETRISTIC_CLASSIFICATION.equals(classification);
        });
        LOGGER.trace("Usage NonBelletristic processor. Finished. UsageIds={}", LogUtils.ids(usages));
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.CLASSIFICATION;
    }
}
