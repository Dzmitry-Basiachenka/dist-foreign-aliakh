package com.copyright.rup.dist.foreign.service.impl.rights;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IRightsService;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import com.google.common.collect.ImmutableSet;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Consumer to handle usages for getting Rights.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/13/2018
 *
 * @author Ihar Suvorau
 */
@Component("df.service.rightsConsumer")
public class RightsConsumer implements IConsumer<Usage> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    private static final ImmutableSet<String> FAS_PRODUCT_FAMILIES =
        ImmutableSet.of(FdaConstants.FAS_PRODUCT_FAMILY, FdaConstants.CLA_FAS_PRODUCT_FAMILY);

    @Autowired
    private IRightsService rightsService;
    @Autowired
    @Qualifier("df.service.ntsRightsProcessor")
    private IChainProcessor<Usage> ntsRightsProcessor;
    @Autowired
    @Qualifier("df.service.fasRightsProcessor")
    private IChainProcessor<Usage> fasRightsProcessor;

    @Override
    @Transactional
    public void consume(Usage usage) {
        LOGGER.trace("Consume usage for rights processing. Usage={}", usage);
        if (Objects.nonNull(usage)) {
            rightsService.updateRight(usage);
            Predicate<Usage> successPredicate = updatedUsage -> UsageStatusEnum.RH_FOUND == updatedUsage.getStatus();
            if (FdaConstants.NTS_PRODUCT_FAMILY.equals(usage.getProductFamily())) {
                ntsRightsProcessor.executeNextProcessor(usage, successPredicate);
            } else if (FAS_PRODUCT_FAMILIES.contains(usage.getProductFamily())) {
                fasRightsProcessor.executeNextProcessor(usage, successPredicate);
            }
        }
    }

    void setRightsService(IRightsService rightsService) {
        this.rightsService = rightsService;
    }

    void setNtsRightsProcessor(IChainProcessor<Usage> ntsRightsProcessor) {
        this.ntsRightsProcessor = ntsRightsProcessor;
    }

    void setFasRightsProcessor(IChainProcessor<Usage> fasRightsProcessor) {
        this.fasRightsProcessor = fasRightsProcessor;
    }
}
