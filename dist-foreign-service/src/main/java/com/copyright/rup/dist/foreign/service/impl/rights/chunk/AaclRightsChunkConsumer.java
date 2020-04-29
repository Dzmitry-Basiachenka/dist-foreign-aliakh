package com.copyright.rup.dist.foreign.service.impl.rights.chunk;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IRightsService;
import com.copyright.rup.dist.foreign.service.api.processor.chunk.IChainChunkProcessor;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Consumer to handle AACL usages for getting Rights.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/16/2020
 *
 * @author Ihar Suvorau
 * @author Aliaksandr Liakh
 */
@Component("df.service.aaclRightsChunkConsumer")
public class AaclRightsChunkConsumer implements IConsumer<List<Usage>> {

    private static final String RIGHTS_PROCESSING_STARTED_LOG = "Consume AACL usages for rights processing. " +
        "Started. UsageId={}, ProductFamily={}, WrWrkInst={}";
    private static final String RIGHTS_PROCESSING_FINISHED_LOG = "Consume AACL usages for rights processing. " +
        "Finished. UsageId={}, ProductFamily={}, WrWrkInst={}, UsageStatus={}, RhAcc#={}";
    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IRightsService rightsService;
    @Autowired
    @Qualifier("df.service.aaclRightsChunkProcessor")
    private IChainChunkProcessor<List<Usage>, Usage> aaclRightsProcessor;

    @Override
    @Profiled(tag = "AaclRightsChunkConsumer.consume")
    public void consume(List<Usage> usages) {
        if (Objects.nonNull(usages)) {
            LOGGER.trace("Consume AACL usages for rights processing. Started. UsageIds={}", LogUtils.ids(usages));
            usages.forEach(usage -> {
                LOGGER.trace(RIGHTS_PROCESSING_STARTED_LOG, usage.getId(), usage.getProductFamily(),
                    usage.getWrWrkInst());
                rightsService.updateAaclRight(usage);
                LOGGER.trace(RIGHTS_PROCESSING_FINISHED_LOG, usage.getId(), usage.getProductFamily(),
                    usage.getWrWrkInst(), usage.getStatus(), usage.getRightsholder().getAccountNumber());
            });
            aaclRightsProcessor.executeNextChainProcessor(usages,
                usage -> UsageStatusEnum.RH_FOUND == usage.getStatus());
            LOGGER.trace("Consume AACL usages for rights processing. Finished. UsageIds={}", LogUtils.ids(usages));
        }
    }
}
