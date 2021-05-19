package com.copyright.rup.dist.foreign.service.impl.chain.executor;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Implementation of {@link AbstractUsageChainChunkExecutor} for {@link Usage}s.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/06/2018
 *
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
 */
@Component("usageChainChunkExecutor")
public class UsageChainChunkExecutor extends AbstractUsageChainChunkExecutor<Usage> {

    @Autowired
    @Qualifier("df.service.fasMatchingChunkProcessor")
    private IChainProcessor<List<Usage>> fasProcessor;
    @Autowired
    @Qualifier("df.service.ntsRightsChunkProcessor")
    private IChainProcessor<List<Usage>> ntsProcessor;
    @Autowired
    @Qualifier("df.service.aaclMatchingChunkProcessor")
    private IChainProcessor<List<Usage>> aaclProcessor;
    @Autowired
    @Qualifier("df.service.salMatchingChunkProcessor")
    private IChainProcessor<List<Usage>> salProcessor;

    @Override
    Map<String, IChainProcessor<List<Usage>>> getProductFamilyToProcessorMap() {
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
