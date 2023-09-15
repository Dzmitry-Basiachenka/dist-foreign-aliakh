package com.copyright.rup.dist.foreign.service.impl.matching;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
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
 * Consumer to handle FAS/FAS2 usages for PI matching.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/13/2018
 *
 * @author Ihar Suvorau
 * @author Aliaksandr Liakh
 */
@Component("df.service.fasMatchingConsumer")
public class FasMatchingConsumer implements IConsumer<List<Usage>> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IWorkMatchingService workMatchingService;
    @Autowired
    @Qualifier("df.service.fasMatchingProcessor")
    private IChainProcessor<Usage> matchingProcessor;

    @Override
    @Profiled(tag = "FasMatchingConsumer.consume")
    public void consume(List<Usage> usages) {
        if (Objects.nonNull(usages)) {
            LOGGER.trace("Consume FAS usages for matching processing. Started. UsageIds={}", LogUtils.ids(usages));
            workMatchingService.matchingFasUsages(usages);
            matchingProcessor.executeNextChainProcessor(usages,
                usage -> UsageStatusEnum.WORK_FOUND == usage.getStatus());
            LOGGER.trace("Consume FAS usages for matching processing. Finished. UsageIds={}", LogUtils.ids(usages));
        }
    }
}
