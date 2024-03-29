package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * Implementation of {@link AbstractUsageJobProcessor} for processing STM RH {@link Usage}s.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/10/2019
 *
 * @author Stanislau Rudak
 * @author Aliaksandr Liakh
 */
public class StmRhProcessor extends AbstractUsageJobProcessor {

    private static final long serialVersionUID = 4789312275831949638L;
    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    @Qualifier("df.service.stmRhProducer")
    private IProducer<List<Usage>> producer;

    @Override
    @Profiled(tag = "StmRhProcessor.process")
    public void process(List<Usage> usages) {
        LOGGER.trace("Usages STM RH processor. Started. UsageIds={}", LogUtils.ids(usages));
        producer.send(usages);
        LOGGER.trace("Usages STM RH processor. Finished. UsageIds={}", LogUtils.ids(usages));
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.STM_RH;
    }
}
