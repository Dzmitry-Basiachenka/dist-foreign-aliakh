package com.copyright.rup.dist.foreign.service.impl.stm.chunk;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.processor.chunk.IChainChunkProcessor;
import com.copyright.rup.dist.foreign.service.api.stm.chunk.IStmRhChunkService;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Consumer that handles STM RH usage exclusion.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/10/2019
 *
 * @author Stanislau Rudak
 * @author Aliaksandr Liakh
 */
@Component("df.service.stmRhChunkConsumer")
public class StmRhChunkConsumer implements IConsumer<List<Usage>> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUsageBatchService batchService;
    @Autowired
    private IStmRhChunkService stmRhService;
    @Autowired
    @Qualifier("df.service.ntsStmRhChunkProcessor")
    private IChainChunkProcessor<List<Usage>, Usage> stmRhProcessor;

    @Override
    @Profiled(tag = "StmRhChunkConsumer.consume")
    public void consume(List<Usage> usages) {
        if (Objects.nonNull(usages)) {
            LOGGER.trace("Consume usages for processing STM RH. Started. UsageIds={}", LogUtils.ids(usages));
            Predicate<Usage> successPredicate = usage -> {
                boolean excludingStm = Objects.requireNonNull(batchService.getUsageBatchById(usage.getBatchId())
                    .getNtsFields())
                    .isExcludingStm();
                LOGGER.trace("Consume usages for processing STM RH. Processed. UsageId={}, BatchId={}, ExcludingStm={}",
                    usage.getId(), usage.getBatchId(), excludingStm);
                return excludingStm;
            };
            Map<Boolean, List<Usage>> usagesByExcludingStm = usages
                .stream()
                .collect(Collectors.partitioningBy(successPredicate));
            List<Usage> usagesExcludingStm = usagesByExcludingStm.get(true);
            if (!usagesExcludingStm.isEmpty()) {
                String productFamily = usagesExcludingStm.get(0).getProductFamily();
                stmRhService.processStmRhs(usagesExcludingStm, productFamily);
                stmRhProcessor.executeNextChainProcessor(usagesExcludingStm,
                    usage -> UsageStatusEnum.NON_STM_RH == usage.getStatus());
            }
            List<Usage> usagesNonExcludingStm = usagesByExcludingStm.get(false);
            if (!usagesNonExcludingStm.isEmpty()) {
                usagesNonExcludingStm.forEach(usage -> {
                    LOGGER.trace("Consume usages for processing STM RH. Skipped. UsageId={}, " +
                        "Reason=STM RH exclusion is not needed", usage.getId());
                });
                stmRhProcessor.executeNextChainProcessor(usagesNonExcludingStm, usage -> true);
            }
            LOGGER.trace("Consume usages for processing STM RH. Finished. UsageIds={}", LogUtils.ids(usages));
        }
    }
}
