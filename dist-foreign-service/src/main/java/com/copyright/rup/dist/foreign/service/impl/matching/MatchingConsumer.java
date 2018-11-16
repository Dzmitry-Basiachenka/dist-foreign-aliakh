package com.copyright.rup.dist.foreign.service.impl.matching;

import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.IWorkMatchingService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
// TODO {isuvorau} cover by integration test
public class MatchingConsumer implements IConsumer<Usage> {

    @Autowired
    private IWorkMatchingService workMatchingService;

    @Override
    public void consume(Usage usage) {
        if (StringUtils.isNoneEmpty(usage.getStandardNumber())) {
            workMatchingService.matchByIdno(usage);
        } else if (StringUtils.isNoneEmpty(usage.getWorkTitle())) {
            workMatchingService.matchByTitle(usage);
        } else {
            workMatchingService.updateStatusForUsageWithoutStandardNumberAndTitle(usage);
        }
    }
}
