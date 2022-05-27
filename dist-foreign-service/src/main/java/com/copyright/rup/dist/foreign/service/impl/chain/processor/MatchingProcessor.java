package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.apache.commons.collections4.CollectionUtils;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * Implementation of {@link AbstractUsageJobProcessor} for searching for works.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/06/2018
 *
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
 */
public class MatchingProcessor extends AbstractUsageJobProcessor {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    @Qualifier("df.service.matchingProducer")
    private IProducer<List<Usage>> matchingProducer;
    @Autowired
    @Qualifier("df.service.fasMatchingConsumer")
    private IConsumer<List<Usage>> fasMatchingConsumer;
    @Autowired
    @Qualifier("df.service.aaclMatchingConsumer")
    private IConsumer<List<Usage>> aaclMatchingConsumer;
    @Autowired
    @Qualifier("df.service.salMatchingConsumer")
    private IConsumer<List<Usage>> salMatchingConsumer;
    @Value("$RUP{dist.foreign.no_messaging}")
    private boolean noMessaging;

    @Override
    @Profiled(tag = "MatchingProcessor.process")
    public void process(List<Usage> usages) {
        LOGGER.trace("Usages Matching processor. Started. UsageIds={}", LogUtils.ids(usages));
        if (noMessaging) {
            if (CollectionUtils.isNotEmpty(usages)) {
                switch (usages.get(0).getProductFamily()) {
                    case FdaConstants.FAS_PRODUCT_FAMILY:
                    case FdaConstants.CLA_FAS_PRODUCT_FAMILY:
                        fasMatchingConsumer.consume(usages);
                        break;
                    case FdaConstants.AACL_PRODUCT_FAMILY:
                        aaclMatchingConsumer.consume(usages);
                        break;
                    case FdaConstants.SAL_PRODUCT_FAMILY:
                        salMatchingConsumer.consume(usages);
                        break;
                    default:
                        LOGGER.warn("Unknown product family: " + usages.get(0).getProductFamily());
                        break;
                }
            } else {
                LOGGER.warn("Usages Matching processor. There are no usages");
            }
        } else {
            matchingProducer.send(usages);
        }
        LOGGER.trace("Usages Matching processor. Finished. UsageIds={}", LogUtils.ids(usages));
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.MATCHING;
    }
}
