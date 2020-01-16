package com.copyright.rup.dist.foreign.service.impl.matching;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IWorkMatchingService;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import org.apache.commons.lang3.StringUtils;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Consumer to handle FAS/FAS2 usages for PI matching.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/13/2018
 *
 * @author Ihar Suvorau
 */
@Component("df.service.fasMatchingConsumer")
public class FasMatchingConsumer implements IConsumer<Usage> {

    private static final String MATCHING_BY_IDNO_FINISHED_LOG = "Consume FAS usage for matching processing. " +
        "Finished. UsageId={}, StandardNumber={}, WorkTitle={}, MatchBy=IDNO, WrWrkInst={}, UsageStatus={}";
    private static final String MATCHING_BY_TITLE_FINISHED_LOG = "Consume FAS usage for matching processing. " +
        "Finished. UsageId={}, WorkTitle={}, MatchBy=Title, WrWrkInst={}, UsageStatus={}";
    private static final String NOT_MATCHED_FINISHED_LOG = "Consume FAS usage for matching processing. Finished. " +
        "UsageId={}, UsageStatus={}, ProductFamily={}, WorkTitle={}, WrWrkInst={}";
    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IWorkMatchingService workMatchingService;
    @Autowired
    @Qualifier("df.service.fasMatchingProcessor")
    private IChainProcessor<Usage> matchingProcessor;

    @Override
    @Profiled(tag = "FasMatchingConsumer.consume")
    public void consume(Usage usage) {
        if (Objects.nonNull(usage)) {
            LOGGER.trace(
                "Consume FAS usage for matching processing. Started. UsageId={}, StandardNumber={}, WorkTitle={}",
                usage.getId(), usage.getStandardNumber(), usage.getWorkTitle());
            if (StringUtils.isNoneEmpty(usage.getStandardNumber())) {
                workMatchingService.matchByIdno(usage);
                LOGGER.trace(MATCHING_BY_IDNO_FINISHED_LOG, usage.getId(), usage.getStandardNumber(),
                    usage.getWorkTitle(), usage.getWrWrkInst(), usage.getStatus());
            } else if (StringUtils.isNoneEmpty(usage.getWorkTitle())) {
                workMatchingService.matchByTitle(usage);
                LOGGER.trace(MATCHING_BY_TITLE_FINISHED_LOG, usage.getId(), usage.getWorkTitle(), usage.getWrWrkInst(),
                    usage.getStatus());
            } else {
                workMatchingService.updateStatusForUsageWithoutStandardNumberAndTitle(usage);
                LOGGER.trace(NOT_MATCHED_FINISHED_LOG, usage.getId(), usage.getStatus(), usage.getProductFamily(),
                    usage.getWorkTitle(), usage.getWrWrkInst());
            }
            matchingProcessor.executeNextProcessor(usage,
                usageItem -> UsageStatusEnum.WORK_FOUND == usageItem.getStatus());
        }
    }
}
