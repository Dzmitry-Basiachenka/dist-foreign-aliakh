package com.copyright.rup.dist.foreign.service.impl.chain.executor;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import com.google.common.collect.ImmutableMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

import io.micrometer.core.annotation.Timed;

/**
 * Implementation of {@link AbstractUsageChainExecutor} for {@link UdmUsage}s.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/06/2021
 *
 * @author Uladzislau Shalamitski
 */
@Component("udmUsageChainExecutor")
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class UdmUsageChainExecutor extends AbstractUsageChainExecutor<UdmUsage> {

    @Autowired
    @Qualifier("df.service.udmMatchingProcessor")
    private IChainProcessor<UdmUsage> udmProcessor;

    @Override
    Map<String, IChainProcessor<UdmUsage>> getProductFamilyToProcessorMap() {
        return ImmutableMap.of(FdaConstants.ACL_PRODUCT_FAMILY, udmProcessor);
    }

    @Override
    Function<UdmUsage, String> getProductFamilyFunction() {
        return (udmUsage) -> FdaConstants.ACL_PRODUCT_FAMILY;
    }
}
