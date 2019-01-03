package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.api.IUsageJobProcessor;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * Implementation of {@link AbstractUsageChainProcessor} to process RH Tax.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/02/19
 *
 * @author Pavel Liakh
 */
public class RhTaxProcessor extends AbstractUsageChainProcessor implements IUsageJobProcessor {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    @Qualifier("df.service.rhTaxProducer")
    private IProducer<Usage> rhTaxProducer;

    @Autowired
    private IUsageService usageService;

    @Override
    public void process(Usage item) {
        rhTaxProducer.send(item);
    }

    @Override
    public void process(String productFamily) {
        List<Usage> rhFoundUsages =
            usageService.getUsagesByStatusAndProductFamily(UsageStatusEnum.RH_FOUND, productFamily);
        LOGGER.info("Send RH_FOUND usages to RH Tax queue. Started. RhFoundUsagesCount={}",
            LogUtils.size(rhFoundUsages));
        rhFoundUsages.forEach(rhTaxProducer::send);
        LOGGER.info("Send RH_FOUND usages to RH Tax queue. Finished. RhFoundUsagesCount={}",
            LogUtils.size(rhFoundUsages));
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.RH_TAX;
    }

    void setRhTaxProducer(IProducer<Usage> rhTaxProducer) {
        this.rhTaxProducer = rhTaxProducer;
    }

    void setUsageService(IUsageService usageService) {
        this.usageService = usageService;
    }
}
