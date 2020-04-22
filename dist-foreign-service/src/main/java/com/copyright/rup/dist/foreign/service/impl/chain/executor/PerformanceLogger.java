package com.copyright.rup.dist.foreign.service.impl.chain.executor;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Implementation {@link IPerformanceLogger}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 20/04/2020
 *
 * @author Aliaksandr Liakh
 */
@Component
public class PerformanceLogger implements IPerformanceLogger {

    private static final Logger LOGGER = RupLogUtils.getLogger();
    private static final int PERIOD_OF_INACTIVITY_MINUTES = 10;
    private static final int PERIOD_OF_LOGGING_MINUTES = 5;

    private final Map<ChainProcessorTypeEnum, Integer> currentTypeToUsagesCount = new LinkedHashMap<>();
    private final Map<ChainProcessorTypeEnum, Integer> lastTypeToUsagesCount = new LinkedHashMap<>();

    private LocalDateTime resetDateTime;
    private LocalDateTime lastlogDateTime;

    @Override
    public void log(ChainProcessorTypeEnum chainProcessorTypeEnum, int increment) {
        synchronized (this) {
            if (lastlogDateTime == null
                || Duration.between(lastlogDateTime, LocalDateTime.now()).toMinutes() > PERIOD_OF_INACTIVITY_MINUTES) {
                reset();
            } else {
                count(chainProcessorTypeEnum, increment);
                log();
            }
        }
    }

    Map<ChainProcessorTypeEnum, Integer> getCurrentTypeToUsagesCount() {
        return currentTypeToUsagesCount;
    }

    /**
     * Resets the logger.
     */
    void reset() {
        LOGGER.info("Performance logger. Reset.");
        currentTypeToUsagesCount.clear();
        lastTypeToUsagesCount.clear();
        resetDateTime = LocalDateTime.now();
        lastlogDateTime = LocalDateTime.now();
    }

    /**
     * Counts an event.
     *
     * @param chainProcessorTypeEnum instance of {@link ChainProcessorTypeEnum}
     * @param increment              value to increment performance counter
     */
    void count(ChainProcessorTypeEnum chainProcessorTypeEnum, int increment) {
        Integer size = currentTypeToUsagesCount.get(chainProcessorTypeEnum);
        if (size == null) {
            size = 0;
        }
        currentTypeToUsagesCount.put(chainProcessorTypeEnum, size + increment);
    }

    /**
     * Logs the performance metrics.
     */
    void log() {
        LocalDateTime now = LocalDateTime.now();
        long secondsFromLastLog = Duration.between(lastlogDateTime, now).getSeconds();
        if (secondsFromLastLog > PERIOD_OF_LOGGING_MINUTES * 60) {
            LOGGER.info("Performance logger. Duration={} min., Metrics={}",
                Duration.between(resetDateTime, now).toMinutes(), getMetrics(secondsFromLastLog));
            lastTypeToUsagesCount.clear();
            lastTypeToUsagesCount.putAll(currentTypeToUsagesCount);
            lastlogDateTime = now;
        }
    }

    /**
     * Gets performance metrics.
     *
     * @param secondsFromLastLog seconds from last log
     * @return performance metrics
     */
    Map<ChainProcessorTypeEnum, Map<String, String>> getMetrics(long secondsFromLastLog) {
        return currentTypeToUsagesCount
            .entrySet()
            .stream()
            .collect(Collectors.toMap(
                Entry::getKey,
                entry -> {
                    Map<String, String> metrics = new LinkedHashMap<>();
                    int currentCount = entry.getValue();
                    int lastCount = lastTypeToUsagesCount.getOrDefault(entry.getKey(), 0);
                    int delta = currentCount - lastCount;
                    metrics.put("usages", String.format("%d(+%d)", currentCount, delta));
                    if (secondsFromLastLog != 0) {
                        metrics.put("usages/min.", String.valueOf(delta * 60 / secondsFromLastLog));
                    }
                    return metrics;
                })
            );
    }
}
