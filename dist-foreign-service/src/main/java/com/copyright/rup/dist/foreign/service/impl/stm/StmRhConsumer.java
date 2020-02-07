package com.copyright.rup.dist.foreign.service.impl.stm;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.foreign.domain.NtsFundPool;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IStmRhService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Consumer that handles STM RH usage exclusion.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/10/19
 *
 * @author Stanislau Rudak
 */
@Component("df.service.stmRhConsumer")
public class StmRhConsumer implements IConsumer<Usage> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUsageBatchService batchService;
    @Autowired
    private IStmRhService stmRhService;
    @Autowired
    @Qualifier("df.service.ntsStmRhProcessor")
    private IChainProcessor<Usage> stmRhProcessor;

    @Override
    @Profiled(tag = "StmRhConsumer.consume")
    public void consume(Usage usage) {
        if (Objects.nonNull(usage)) {
            LOGGER.trace("Consume usage for processing STM RH. Started. UsageId={}", usage.getId());
            NtsFundPool ntsFundPool =
                Objects.requireNonNull(batchService.getUsageBatchById(usage.getBatchId()).getNtsFundPool());
            if (ntsFundPool.isExcludingStm()) {
                stmRhService.processStmRh(usage);
                stmRhProcessor.executeNextProcessor(usage, (obj) -> UsageStatusEnum.NON_STM_RH == obj.getStatus());
                LOGGER.trace("Consume usage for processing STM RH. Finished. UsageId={}, UsageStatus={}", usage.getId(),
                    usage.getStatus());
            } else {
                stmRhProcessor.executeNextProcessor(usage, (obj) -> true);
                LOGGER.trace(
                    "Consume usage for processing STM RH. Skipped. UsageId={}, Reason=STM RH exclusion is not needed",
                    usage.getId());
            }
        }
    }
}
