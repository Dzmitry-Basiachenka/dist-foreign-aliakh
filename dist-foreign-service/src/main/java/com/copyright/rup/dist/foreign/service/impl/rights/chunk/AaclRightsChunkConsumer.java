package com.copyright.rup.dist.foreign.service.impl.rights.chunk;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IRightsService;
import com.copyright.rup.dist.foreign.service.api.processor.chunk.IChainChunkProcessor;

import org.apache.commons.collections4.CollectionUtils;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IRightsService rightsService;
    @Autowired
    @Qualifier("df.service.aaclRightsChunkProcessor")
    private IChainChunkProcessor<List<Usage>, Usage> aaclRightsProcessor;

    @Override
    @Profiled(tag = "AaclRightsChunkConsumer.consume")
    public void consume(List<Usage> usages) {
        if (CollectionUtils.isNotEmpty(usages)) {
            LOGGER.trace("Consume AACL usages for rights processing. Started. UsageIds={}", LogUtils.ids(usages));
            Map<LocalDate, List<Usage>> groupedByPeriodEndDateUsages =
                usages.stream().collect(Collectors.groupingBy(usage -> usage.getAaclUsage().getBatchPeriodEndDate()));
            groupedByPeriodEndDateUsages.forEach(
                (periodEndDate, groupedUsages) -> rightsService.updateAaclRights(groupedUsages, periodEndDate));
            aaclRightsProcessor.executeNextChainProcessor(usages,
                usage -> UsageStatusEnum.RH_FOUND == usage.getStatus());
            LOGGER.trace("Consume AACL usages for rights processing. Finished. UsageIds={}", LogUtils.ids(usages));
        }
    }
}
