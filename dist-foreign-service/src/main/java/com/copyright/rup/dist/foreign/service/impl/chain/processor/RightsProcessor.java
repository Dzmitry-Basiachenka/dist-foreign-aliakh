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
 * Implementation of {@link AbstractUsageJobProcessor} for getting rights.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/06/2018
 *
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
 */
public class RightsProcessor extends AbstractUsageJobProcessor {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    @Qualifier("df.service.rightsProducer")
    private IProducer<List<Usage>> producer;
    @Autowired
    @Qualifier("df.service.fasRightsConsumer")
    private IConsumer<List<Usage>> fasRightsConsumer;
    @Autowired
    @Qualifier("df.service.ntsRightsConsumer")
    private IConsumer<List<Usage>> ntsRightsConsumer;
    @Autowired
    @Qualifier("df.service.aaclRightsConsumer")
    private IConsumer<List<Usage>> aaclRightsConsumer;
    @Autowired
    @Qualifier("df.service.salRightsConsumer")
    private IConsumer<List<Usage>> salRightsConsumer;
    @Value("$RUP{dist.foreign.no_messaging}")
    private boolean noMessaging;
    
    @Override
    @Profiled(tag = "RightsProcessor.process")
    public void process(List<Usage> usages) {
        LOGGER.trace("Usages Rights processor. Started. UsageIds={}", LogUtils.ids(usages));
        if (noMessaging) {
            if (CollectionUtils.isNotEmpty(usages)) {
                switch (usages.get(0).getProductFamily()) {
                    case FdaConstants.FAS_PRODUCT_FAMILY:
                    case FdaConstants.CLA_FAS_PRODUCT_FAMILY:
                        fasRightsConsumer.consume(usages);
                        break;
                    case FdaConstants.NTS_PRODUCT_FAMILY:
                        ntsRightsConsumer.consume(usages);
                        break;
                    case FdaConstants.AACL_PRODUCT_FAMILY:
                        aaclRightsConsumer.consume(usages);
                        break;
                    case FdaConstants.SAL_PRODUCT_FAMILY:
                        salRightsConsumer.consume(usages);
                        break;
                    default:
                        LOGGER.warn("Unknown product family: " + usages.get(0).getProductFamily());
                        break;
                }
            } else {
                LOGGER.warn("Usages Rights processor. There are no usages");
            }
        } else {
            producer.send(usages);
        }
        LOGGER.trace("Usages Rights processor. Finished. UsageIds={}", LogUtils.ids(usages));
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.RIGHTS;
    }
}
