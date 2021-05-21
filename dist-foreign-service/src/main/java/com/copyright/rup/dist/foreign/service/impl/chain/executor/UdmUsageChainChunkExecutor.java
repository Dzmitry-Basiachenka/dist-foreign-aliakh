package com.copyright.rup.dist.foreign.service.impl.chain.executor;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import com.google.common.collect.ImmutableMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Implementation of {@link AbstractUsageChainChunkExecutor} for {@link UdmUsage}s.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/06/2021
 *
 * @author Uladzislau Shalamitski
 */
@Component("udmUsageChainChunkExecutor")
public class UdmUsageChainChunkExecutor extends AbstractUsageChainChunkExecutor<UdmUsage> {

    @Autowired
    @Qualifier("df.service.udmMatchingProcessor")
    private IChainProcessor<List<UdmUsage>> udmProcessor;

    @Override
    Map<String, IChainProcessor<List<UdmUsage>>> getProductFamilyToProcessorMap() {
        return ImmutableMap.of(FdaConstants.ACL_PRODUCT_FAMILY, udmProcessor);
    }

    @Override
    Function<UdmUsage, String> getProductFamilyFunction() {
        return (udmUsage) -> FdaConstants.ACL_PRODUCT_FAMILY;
    }
}
