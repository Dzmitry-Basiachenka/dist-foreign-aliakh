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

/**
 * Consumer to handle SAL usages for getting Rights.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 08/20/2020
 *
 * @author Stanislau Rudak
 */
@Component("df.service.salRightsConsumer")
public class SalRightsConsumer implements IConsumer<List<Usage>> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IRightsService rightsService;
    @Autowired
    @Qualifier("df.service.salRightsProcessor")
    private IChainProcessor<Usage> salRightsProcessor;

    @Override
    @Profiled(tag = "SalRightsConsumer.consume")
    public void consume(List<Usage> usages) {
        if (CollectionUtils.isNotEmpty(usages)) {
            LOGGER.trace("Consume SAL usages for rights processing. Started. UsageIds={}", LogUtils.ids(usages));
            rightsService.updateSalRights(usages);
            salRightsProcessor.executeNextChainProcessor(usages,
                usage -> UsageStatusEnum.RH_FOUND == usage.getStatus());
            LOGGER.trace("Consume SAL usages for rights processing. Finished. UsageIds={}", LogUtils.ids(usages));
        }
    }
}
