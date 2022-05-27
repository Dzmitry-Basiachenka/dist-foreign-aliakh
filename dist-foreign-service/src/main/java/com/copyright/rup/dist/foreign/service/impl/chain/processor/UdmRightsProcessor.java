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
 * Implementation of {@link AbstractUdmJobProcessor} for getting rights.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/21/2021
 *
 * @author Uladzislau Shalamitski
 */
public class UdmRightsProcessor extends AbstractUdmJobProcessor {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    @Qualifier("df.service.udmRightsProducer")
    private IProducer<List<UdmUsage>> producer;
    @Autowired
    @Qualifier("df.service.udmRightsConsumer")
    private IConsumer<List<UdmUsage>> udmRightsConsumer;
    @Value("$RUP{dist.foreign.no_messaging}")
    private boolean noMessaging;

    @Override
    @Profiled(tag = "UdmRightsProcessor.process")
    public void process(List<UdmUsage> usages) {
        LOGGER.trace("UDM usages rights processor. Started. UsageIds={}", LogUtils.ids(usages));
        if (noMessaging) {
            udmRightsConsumer.consume(usages);
        } else {
            producer.send(usages);
        }
        LOGGER.trace("UDM usages rights processor. Finished. UsageIds={}", LogUtils.ids(usages));
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.RIGHTS;
    }
}
