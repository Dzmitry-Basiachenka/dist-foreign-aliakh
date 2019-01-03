package com.copyright.rup.dist.foreign.service.impl.tax;

import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.IChainProcessor;
import com.copyright.rup.dist.foreign.service.api.IRhTaxService;

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

    @Autowired
    private IRhTaxService rhTaxService;

    @Autowired
    @Qualifier("df.service.rhTaxProcessor")
    private IChainProcessor<Usage> rhTaxProcessor;

    @Override
    public void consume(Usage usage) {
        if (Objects.nonNull(usage)) {
            boolean isUsCountryCode = rhTaxService.isUsCountryCodeUsage(usage);
            rhTaxProcessor.processResult(usage, (obj) -> isUsCountryCode);
        }
    }

    void setRhTaxService(IRhTaxService rhTaxService) {
        this.rhTaxService = rhTaxService;
    }

    void setRhTaxProcessor(IChainProcessor<Usage> rhTaxProcessor) {
        this.rhTaxProcessor = rhTaxProcessor;
    }
}
