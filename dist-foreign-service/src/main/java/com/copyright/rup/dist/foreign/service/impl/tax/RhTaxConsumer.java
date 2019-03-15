package com.copyright.rup.dist.foreign.service.impl.tax;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IRhTaxService;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Consumer to handle usages to check RH tax country.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/13/2018
 *
 * @author Uladzislau Shalamitski
 */
@Component("df.service.rhTaxConsumer")
public class RhTaxConsumer implements IConsumer<Usage> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IRhTaxService rhTaxService;

    @Autowired
    @Qualifier("df.service.ntsRhTaxProcessor")
    private IChainProcessor<Usage> rhTaxProcessor;

    @Override
    @Profiled(tag = "RhTaxConsumer.consume")
    public void consume(Usage usage) {
        if (Objects.nonNull(usage)) {
            LOGGER.trace("Consume usage for RH tax processing. Started. UsageId={}", usage.getId());
            rhTaxService.processTaxCountryCode(usage);
            rhTaxProcessor.executeNextProcessor(usage, (obj) -> UsageStatusEnum.US_TAX_COUNTRY == obj.getStatus());
            LOGGER.trace("Consume usage for RH tax processing. Finished. UsageId={}, UsageStatus={}", usage.getId(),
                usage.getStatus());
        }
    }

    void setRhTaxService(IRhTaxService rhTaxService) {
        this.rhTaxService = rhTaxService;
    }

    void setRhTaxProcessor(IChainProcessor<Usage> rhTaxProcessor) {
        this.rhTaxProcessor = rhTaxProcessor;
    }
}
