package com.copyright.rup.dist.foreign.service.impl.rights;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IRightsService;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import org.apache.commons.collections4.CollectionUtils;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

import io.micrometer.core.annotation.Timed;

/**
 * Consumer to handle NTS usages for getting Rights.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/12/2019
 *
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
 */
@Component("df.service.ntsRightsConsumer")
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class NtsRightsConsumer implements IConsumer<List<Usage>> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IRightsService rightsService;
    @Autowired
    @Qualifier("df.service.ntsRightsProcessor")
    private IChainProcessor<Usage> ntsRightsProcessor;

    @Override
    @Profiled(tag = "NtsRightsConsumer.consume")
    public void consume(List<Usage> usages) {
        if (CollectionUtils.isNotEmpty(usages)) {
            LOGGER.trace("Consume NTS usages for rights processing. Started. UsageIds={}", LogUtils.ids(usages));
            rightsService.updateRights(usages, false);
            ntsRightsProcessor.executeNextChainProcessor(usages,
                usage -> UsageStatusEnum.RH_FOUND == usage.getStatus());
            LOGGER.trace("Consume NTS usages for rights processing. Finished. UsageIds={}", LogUtils.ids(usages));
        }
    }
}
