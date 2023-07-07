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

import io.micrometer.core.annotation.Timed;

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
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
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
    @Autowired
    @Qualifier("df.service.aclciMatchingProcessor")
    private IChainProcessor<Usage> aclciProcessor;

    @Override
    Map<String, IChainProcessor<Usage>> getProductFamilyToProcessorMap() {
        return ImmutableMap.<String, IChainProcessor<Usage>>builder()
            .put(FdaConstants.FAS_PRODUCT_FAMILY, fasProcessor)
            .put(FdaConstants.CLA_FAS_PRODUCT_FAMILY, fasProcessor)
            .put(FdaConstants.NTS_PRODUCT_FAMILY, ntsProcessor)
            .put(FdaConstants.AACL_PRODUCT_FAMILY, aaclProcessor)
            .put(FdaConstants.SAL_PRODUCT_FAMILY, salProcessor)
            .put(FdaConstants.ACLCI_PRODUCT_FAMILY, aclciProcessor)
            .build();
    }

    @Override
    Function<Usage, String> getProductFamilyFunction() {
        return Usage::getProductFamily;
    }
}
