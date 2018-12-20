package com.copyright.rup.dist.foreign.service.impl.matching;

import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IChainProcessor;
import com.copyright.rup.dist.foreign.service.api.IWorkMatchingService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Consumer to handle usages for PI matching.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/13/2018
 *
 * @author Ihar Suvorau
 */
@Component("df.service.matchingConsumer")
public class MatchingConsumer implements IConsumer<Usage> {

    @Autowired
    private IWorkMatchingService workMatchingService;
    @Autowired
    @Qualifier("df.service.fasMatchingProcessor")
    private IChainProcessor<Usage> matchingProcessor;

    @Override
    public void consume(Usage usage) {
        if (StringUtils.isNoneEmpty(usage.getStandardNumber())) {
            workMatchingService.matchByIdno(usage);
        } else if (StringUtils.isNoneEmpty(usage.getWorkTitle())) {
            workMatchingService.matchByTitle(usage);
        } else {
            workMatchingService.updateStatusForUsageWithoutStandardNumberAndTitle(usage);
        }
        matchingProcessor.processResult(usage, usageItem -> UsageStatusEnum.WORK_FOUND == usageItem.getStatus());
    }
}
