package com.copyright.rup.dist.foreign.service.impl.chain.executor;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

/**
 * Implementation of {@link AbstractUsageChainExecutor} for {@link Usage}s.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/06/2018
 *
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
 */
@Component("usageChainExecutor")
public class UsageChainExecutor extends AbstractUsageChainExecutor<Usage> {

    @Autowired
    @Qualifier("df.service.fasMatchingProcessor")
    private IChainProcessor<Usage> fasProcessor;
    @Autowired
    @Qualifier("df.service.ntsRightsProcessor")
    private IChainProcessor<Usage> ntsProcessor;
    @Autowired
    @Qualifier("df.service.aaclMatchingProcessor")
    private IChainProcessor<Usage> aaclProcessor;
    @Autowired
    @Qualifier("df.service.salMatchingProcessor")
    private IChainProcessor<Usage> salProcessor;

    @Override
    Map<String, IChainProcessor<Usage>> getProductFamilyToProcessorMap() {
        return ImmutableMap.of(
            FdaConstants.FAS_PRODUCT_FAMILY, fasProcessor,
            FdaConstants.CLA_FAS_PRODUCT_FAMILY, fasProcessor,
            FdaConstants.NTS_PRODUCT_FAMILY, ntsProcessor,
            FdaConstants.AACL_PRODUCT_FAMILY, aaclProcessor,
            FdaConstants.SAL_PRODUCT_FAMILY, salProcessor);
    }

    @Override
    Function<Usage, String> getProductFamilyFunction() {
        return Usage::getProductFamily;
    }
}
