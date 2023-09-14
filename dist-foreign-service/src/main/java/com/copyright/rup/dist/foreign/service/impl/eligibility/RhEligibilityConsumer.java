package com.copyright.rup.dist.foreign.service.impl.eligibility;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Consumer to check whether RH is eligible for distribution or not.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/11/2019
 *
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
 */
@Component("df.service.rhEligibilityConsumer")
public class RhEligibilityConsumer implements IConsumer<List<Usage>> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IPrmIntegrationService prmIntegrationService;
    @Autowired
    @Qualifier("df.service.ntsRhEligibilityProcessor")
    private IChainProcessor<Usage> rhEligibilityProcessor;

    @Override
    @Profiled(tag = "RhEligibilityConsumer.consume")
    public void consume(List<Usage> usages) {
        if (Objects.nonNull(usages)) {
            LOGGER.trace("Consume usages for RH eligibility processing. Started. UsageIds={}", LogUtils.ids(usages));
            rhEligibilityProcessor.executeNextChainProcessor(usages, usage -> {
                boolean isEligible = prmIntegrationService.isRightsholderEligibleForNtsDistribution(
                    usage.getRightsholder().getId());
                LOGGER.trace("Consume usages for RH eligibility processing. Processed. UsageId={}, IsEligibile={}",
                    usage.getId(), isEligible);
                return isEligible;
            });
            LOGGER.trace("Consume usages for RH eligibility processing. Finished. UsageIds={}", LogUtils.ids(usages));
        }
    }
}
