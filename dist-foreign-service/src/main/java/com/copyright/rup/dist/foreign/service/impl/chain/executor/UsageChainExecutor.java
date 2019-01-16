package com.copyright.rup.dist.foreign.service.impl.chain.executor;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;
import com.copyright.rup.dist.foreign.service.api.processor.IUsageJobProcessor;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

/**
 * Implementation of {@link IChainExecutor}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/06/18
 *
 * @author Uladzislau Shalamitski
 */
@Component
public class UsageChainExecutor implements IChainExecutor<Usage> {

    @Autowired
    @Qualifier("df.service.fasMatchingProcessor")
    private IChainProcessor<Usage> fasProcessor;
    @Autowired
    @Qualifier("df.service.ntsRightsProcessor")
    private IChainProcessor<Usage> ntsProcessor;

    private Map<String, IChainProcessor<Usage>> productFamilyToProcessorMap;

    /**
     * Initialization.
     */
    @PostConstruct
    public void init() {
        productFamilyToProcessorMap = ImmutableMap.of(
            FdaConstants.FAS_PRODUCT_FAMILY, fasProcessor,
            FdaConstants.CLA_FAS_PRODUCT_FAMILY, fasProcessor,
            FdaConstants.NTS_PRODUCT_FAMILY, ntsProcessor);
    }

    @Override
    public void execute(ChainProcessorTypeEnum type) {
        productFamilyToProcessorMap.forEach((productFamily, processor) -> execute(productFamily, processor, type));
    }

    @Override
    public void execute(List<Usage> usages, ChainProcessorTypeEnum type) {
        Map<String, List<Usage>> productFamilyToUsagesMap = usages.stream()
            .collect(Collectors.groupingBy(Usage::getProductFamily));
        productFamilyToUsagesMap.forEach((productFamily, usagesList) -> {
            IChainProcessor<Usage> processor = productFamilyToProcessorMap.get(productFamily);
            if (Objects.nonNull(processor)) {
                execute(usages, processor, type);
            } else {
                throw new UnsupportedOperationException(
                    String.format("Product family %s is not supported", productFamily));
            }
        });
    }

    private void execute(String productFamily, IChainProcessor<Usage> processor, ChainProcessorTypeEnum type) {
        if (Objects.nonNull(processor)) {
            if (processor instanceof IUsageJobProcessor && type == processor.getChainProcessorType()) {
                ((IUsageJobProcessor) processor).jobProcess(productFamily);
            } else {
                execute(productFamily, processor.getSuccessProcessor(), type);
                execute(productFamily, processor.getFailureProcessor(), type);
            }
        }
    }

    private void execute(List<Usage> usages, IChainProcessor<Usage> processor, ChainProcessorTypeEnum type) {
        if (Objects.nonNull(processor)) {
            if (type == processor.getChainProcessorType()) {
                usages.forEach(processor::process);
            } else {
                execute(usages, processor.getSuccessProcessor(), type);
                execute(usages, processor.getFailureProcessor(), type);
            }
        }
    }
}
