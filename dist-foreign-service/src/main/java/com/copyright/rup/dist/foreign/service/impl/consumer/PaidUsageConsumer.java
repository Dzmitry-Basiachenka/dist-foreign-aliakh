package com.copyright.rup.dist.foreign.service.impl.consumer;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Consumer to handle list of {@link PaidUsage}s from LM.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 02/21/18
 *
 * @author Darya Baraukova
 */
@Component("df.service.paidUsageConsumer")
public class PaidUsageConsumer implements IConsumer<List<PaidUsage>> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUsageService usageService;

    @Override
    public void consume(List<PaidUsage> usages) {
        LOGGER.info("Consume paid information from LM. Started. UsagesCount={}", LogUtils.size(usages));
        if (CollectionUtils.isNotEmpty(usages)) {
            usageService.updatePaidInfo(usages);
            LOGGER.info("Consume paid information from LM. Finished. UsagesCount={}", LogUtils.size(usages));
        } else {
            LOGGER.warn("Consume paid information from LM. Failed. Reason='Usages list is empty'");
        }
    }
}
