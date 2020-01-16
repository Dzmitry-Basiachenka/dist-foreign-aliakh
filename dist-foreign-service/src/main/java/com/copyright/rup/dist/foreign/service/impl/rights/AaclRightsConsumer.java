package com.copyright.rup.dist.foreign.service.impl.rights;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IRightsService;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Consumer to handle AACL usages for getting Rights.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/16/2020
 *
 * @author Ihar Suvorau
 */
@Component("df.service.aaclRightsConsumer")
public class AaclRightsConsumer implements IConsumer<Usage> {

    private static final String RIGHTS_PROCESSING_FINISHED_LOG = "Consume usage for rights processing. Finished. " +
        "UsageId={}, ProductFamily={}, WrWrkInst={}, UsageStatus={}, RhAcc#={}";
    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IRightsService rightsService;
    @Autowired
    @Qualifier("df.service.aaclRightsProcessor")
    private IChainProcessor<Usage> aaclRightsProcessor;

    @Override
    @Profiled(tag = "AaclRightsConsumer.consume")
    public void consume(Usage usage) {
        if (Objects.nonNull(usage)) {
            LOGGER.trace("Consume usage for rights processing. Started. UsageId={}, ProductFamily={}, WrWrkInst={}",
                usage.getId(), usage.getProductFamily(), usage.getWrWrkInst());
            rightsService.updateAaclRight(usage);
            aaclRightsProcessor.executeNextProcessor(usage, item -> UsageStatusEnum.RH_FOUND == item.getStatus());
            LOGGER.trace(RIGHTS_PROCESSING_FINISHED_LOG, usage.getId(), usage.getProductFamily(), usage.getWrWrkInst(),
                usage.getStatus(), usage.getRightsholder().getAccountNumber());
        }
    }

    void setRightsService(IRightsService rightsService) {
        this.rightsService = rightsService;
    }

    void setAaclRightsProcessor(IChainProcessor<Usage> aaclRightsProcessor) {
        this.aaclRightsProcessor = aaclRightsProcessor;
    }
}
