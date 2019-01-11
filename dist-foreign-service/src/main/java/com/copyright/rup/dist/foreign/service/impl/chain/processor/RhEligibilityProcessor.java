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
 * Implementation of {@link AbstractUsageChainProcessor} to check whether RH is eligible for distribution or not.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/02/19
 *
 * @author Uladzislau Shalamitski
 */
public class RhEligibilityProcessor extends AbstractUsageChainProcessor implements IUsageJobProcessor {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    @Qualifier("df.service.rhEligibilityProducer")
    private IProducer<Usage> rhEligibilityProducer;

    @Autowired
    private IUsageService usageService;

    @Override
    public void process(Usage item) {
        rhEligibilityProducer.send(item);
    }

    @Override
    public void process(String productFamily) {
        List<Usage> usTaxUsages =
            usageService.getUsagesByStatusAndProductFamily(UsageStatusEnum.US_TAX_COUNTRY, productFamily);
        LogUtils.ILogWrapper usagesCount = LogUtils.size(usTaxUsages);
        LOGGER.info("Send US_TAX_COUNTRY usages to RH Eligibility queue. Started. UsTaxUsagesCount={}", usagesCount);
        usTaxUsages.forEach(this::process);
        LOGGER.info("Send US_TAX_COUNTRY usages to RH Eligibility queue. Finished. UsTaxUsagesCount={}", usagesCount);
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.RH_ELIGIBILITY;
    }

    void setRhEligibilityProducer(IProducer<Usage> rhEligibilityProducer) {
        this.rhEligibilityProducer = rhEligibilityProducer;
    }

    void setUsageService(IUsageService usageService) {
        this.usageService = usageService;
    }
}
