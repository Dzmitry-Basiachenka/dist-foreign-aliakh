package com.copyright.rup.dist.foreign.service.impl.chain;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;
import org.slf4j.Logger;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class to measure background usages processing performance.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/27/2022
 *
 * @author Aliaksandr Liakh
 */
public final class PerformanceMeter {

    private static final Map<ChainProcessorTypeEnum, Integer> COUNTS = new ConcurrentHashMap<>();
    private static final Map<ChainProcessorTypeEnum, Long> MILLIS = new ConcurrentHashMap<>();

    private static final int THRESHOLD = 10000;
    private static final Logger LOGGER = RupLogUtils.getLogger();

    private PerformanceMeter() {
    }

    /**
     * Calculates performance.
     *
     * @param type      instance of {@link ChainProcessorTypeEnum}
     * @param increment number of processed usages
     */
    public static void calculate(ChainProcessorTypeEnum type, int increment) {
        Integer count = COUNTS.get(type);
        if (null == count) {
            count = 0;
            MILLIS.put(type, new Date().getTime());
        }
        count += increment;
        if (count % THRESHOLD == 0) {
            long seconds = (new Date().getTime() - MILLIS.get(type)) / 1000;
            LOGGER.info("Background usages processing performance. Type={}, Usages={}, Seconds={}, UsagesPerSecond={}",
                type, count, seconds, count / seconds);
        }
        COUNTS.put(type, count);
    }


    /**
     * Cleans performance.
     */
    public static void clear() {
        COUNTS.clear();
        MILLIS.clear();
    }
}
