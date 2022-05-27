package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * Implementation of {@link AbstractUdmJobProcessor} for searching works.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/21/2021
 *
 * @author Ihar Suvorau
 */
public class UdmMatchingProcessor extends AbstractUdmJobProcessor {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    @Qualifier("df.service.udmMatchingProducer")
    private IProducer<List<UdmUsage>> matchingProducer;
    @Autowired
    @Qualifier("df.service.udmMatchingConsumer")
    private IConsumer<List<UdmUsage>> udmMatchingConsumer;
    @Value("$RUP{dist.foreign.no_messaging}")
    private boolean noMessaging;

    @Override
    @Profiled(tag = "UdmMatchingProcessor.process")
    public void process(List<UdmUsage> usages) {
        LOGGER.trace("UDM usages matching processor. Started. UsageIds={}", LogUtils.ids(usages));
        if (noMessaging) {
            udmMatchingConsumer.consume(usages);
        } else {
            matchingProducer.send(usages);
        }
        LOGGER.trace("UDM usages matching processor. Finished. UsageIds={}", LogUtils.ids(usages));
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.MATCHING;
    }
}
