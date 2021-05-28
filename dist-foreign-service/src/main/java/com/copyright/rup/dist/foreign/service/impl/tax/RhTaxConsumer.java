package com.copyright.rup.dist.foreign.service.impl.tax;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IRhTaxService;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Consumer to handle usages to check RH tax country.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/13/2018
 *
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
 */
@Component("df.service.rhTaxConsumer")
public class RhTaxConsumer implements IConsumer<List<Usage>> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IRhTaxService rhTaxService;
    @Autowired
    @Qualifier("df.service.ntsRhTaxProcessor")
    private IChainProcessor<Usage> rhTaxProcessor;

    @Override
    @Profiled(tag = "RhTaxConsumer.consume")
    public void consume(List<Usage> usages) {
        if (Objects.nonNull(usages)) {
            LOGGER.trace("Consume usages for RH tax processing. Started. UsageIds={}", LogUtils.ids(usages));
            rhTaxService.processTaxCountryCode(usages);
            rhTaxProcessor.executeNextChainProcessor(usages,
                usage -> UsageStatusEnum.US_TAX_COUNTRY == usage.getStatus());
            LOGGER.trace("Consume usages for RH tax processing. Finished. UsageIds={}", LogUtils.ids(usages));
        }
    }
}
