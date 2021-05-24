package com.copyright.rup.dist.foreign.service.impl.rights;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IRightsService;
import com.copyright.rup.dist.foreign.service.api.processor.IChainChunkProcessor;

import org.apache.commons.collections4.CollectionUtils;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Consumer to handle UDM usages for getting rights.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/21/2021
 *
 * @author Uladzislau Shalamitski
 */
@Component("df.service.udmRightsConsumer")
public class UdmRightsConsumer implements IConsumer<List<UdmUsage>> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IRightsService rightsService;
    @Autowired
    @Qualifier("df.service.udmRightsProcessor")
    private IChainChunkProcessor<List<UdmUsage>, UdmUsage> udmRightsProcessor;

    @Override
    @Profiled(tag = "UdmRightsConsumer.consume")
    public void consume(List<UdmUsage> udmUsages) {
        LOGGER.trace("Consume UDM usages for rights processing. Started. UsageIds={}", LogUtils.ids(udmUsages));
        if (CollectionUtils.isNotEmpty(udmUsages)) {
            rightsService.updateUdmRights(udmUsages);
            udmRightsProcessor.executeNextChainProcessor(udmUsages,
                usage -> UsageStatusEnum.RH_FOUND == usage.getStatus());
            LOGGER.trace("Consume UDM usages for rights processing. Finished. UsageIds={}", LogUtils.ids(udmUsages));
        } else {
            LOGGER.trace("Consume UDM usages for rights processing. Skipped. There are no usages to process");
        }
    }
}
