package com.copyright.rup.dist.foreign.service.impl.matching;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IWorkMatchingService;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Consumer to handle UDM usages for PI matching.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/20/2021
 *
 * @author Ihar Suvorau
 */
@Component("df.service.udmMatchingConsumer")
public class UdmMatchingConsumer implements IConsumer<List<UdmUsage>> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    @Qualifier("df.service.udmMatchingProcessor")
    private IChainProcessor<UdmUsage> matchingProcessor;
    @Autowired
    private IWorkMatchingService workMatchingService;

    @Override
    @Profiled(tag = "UdmMatchingConsumer.consume")
    public void consume(List<UdmUsage> usages) {
        if (Objects.nonNull(usages)) {
            LOGGER.trace("Consume UDM usages for matching processing. Started. UsageIds={}", LogUtils.ids(usages));
            workMatchingService.matchingUdmUsages(usages);
            matchingProcessor.executeNextChainProcessor(usages,
                usage -> UsageStatusEnum.WORK_FOUND == usage.getStatus());
            LOGGER.trace("Consume UDM usages for matching processing. Finished. UsageIds={}", LogUtils.ids(usages));
        }
    }
}
