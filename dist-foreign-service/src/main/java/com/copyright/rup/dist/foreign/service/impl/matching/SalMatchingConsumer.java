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

import io.micrometer.core.annotation.Timed;

/**
 * Consumer to handle SAL usages for PI matching.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 08/19/20
 *
 * @author Anton Azarenka
 */
@Component("df.service.salMatchingConsumer")
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class SalMatchingConsumer implements IConsumer<List<Usage>> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IWorkMatchingService workMatchingService;
    @Autowired
    @Qualifier("df.service.salMatchingProcessor")
    private IChainProcessor<Usage> matchingProcessor;

    @Override
    @Profiled(tag = "SalMatchingConsumer.consume")
    public void consume(List<Usage> usages) {
        if (Objects.nonNull(usages)) {
            LOGGER.trace("Consume SAL usages for matching processing. Started. UsageIds={}", LogUtils.ids(usages));
            workMatchingService.matchingSalUsages(usages);
            matchingProcessor.executeNextChainProcessor(usages,
                usage -> UsageStatusEnum.WORK_FOUND == usage.getStatus());
            LOGGER.trace("Consume SAL usages for matching processing. Finished. UsageIds={}", LogUtils.ids(usages));
        }
    }
}
