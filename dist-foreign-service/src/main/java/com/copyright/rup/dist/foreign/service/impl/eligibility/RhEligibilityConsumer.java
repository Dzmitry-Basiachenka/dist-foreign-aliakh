package com.copyright.rup.dist.foreign.service.impl.eligibility;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Consumer to check whether RH is eligible for distribution or not.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/11/2019
 *
 * @author Uladzislau Shalamitski
 */
@Component("df.service.rhEligibilityConsumer")
public class RhEligibilityConsumer implements IConsumer<Usage> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IPrmIntegrationService prmIntegrationService;

    @Autowired
    @Qualifier("df.service.ntsRhEligibilityProcessor")
    private IChainProcessor<Usage> rhEligibilityProcessor;

    @Override
    @Transactional
    public void consume(Usage usage) {
        if (Objects.nonNull(usage)) {
            LOGGER.trace("Consume usage for RH eligibility processing. Started. UsageId={}", usage.getId());
            boolean isEligible =
                prmIntegrationService.isRightsholderEligibleForNtsDistribution(usage.getRightsholder().getId());
            rhEligibilityProcessor.executeNextProcessor(usage, (obj) -> isEligible);
            LOGGER.trace("Consume usage for RH eligibility processing. Finished. UsageId={}, IsEligibile={}",
                usage.getId(), isEligible);
        }
    }

    void setPrmIntegrationService(IPrmIntegrationService prmIntegrationService) {
        this.prmIntegrationService = prmIntegrationService;
    }

    void setRhEligibilityProcessor(IChainProcessor<Usage> rhEligibilityProcessor) {
        this.rhEligibilityProcessor = rhEligibilityProcessor;
    }
}
