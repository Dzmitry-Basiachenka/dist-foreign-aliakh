package com.copyright.rup.dist.foreign.service.impl.chain.executor;

import static junit.framework.TestCase.assertEquals;

import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.junit.Test;

import java.util.Map;

/**
 * Verifies {@link PerformanceLogger}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 21/04/2020
 *
 * @author Aliaksandr Liakh
 */
public class PerformanceLoggerTest {

    private static final String USAGES = "usages";
    private static final String USAGES_PER_MINUTE = "usages/min.";

    @Test
    public void testClean() {
        PerformanceLogger logger = new PerformanceLogger();
        ChainProcessorTypeEnum chainProcessorTypeEnum = ChainProcessorTypeEnum.MATCHING;
        logger.count(chainProcessorTypeEnum);
        assertEquals(1, logger.getCurrentTypeToUsagesCount().get(chainProcessorTypeEnum).intValue());
        logger.reset();
        assertNull(logger.getCurrentTypeToUsagesCount().get(chainProcessorTypeEnum));
    }

    @Test
    public void testCount() {
        PerformanceLogger logger = new PerformanceLogger();
        ChainProcessorTypeEnum chainProcessorTypeEnum = ChainProcessorTypeEnum.MATCHING;
        assertNull(logger.getCurrentTypeToUsagesCount().get(chainProcessorTypeEnum));
        logger.count(chainProcessorTypeEnum);
        assertEquals(1, logger.getCurrentTypeToUsagesCount().get(chainProcessorTypeEnum).intValue());
        logger.count(chainProcessorTypeEnum);
        assertEquals(2, logger.getCurrentTypeToUsagesCount().get(chainProcessorTypeEnum).intValue());
    }

    @Test
    public void testGetMetrics() {
        PerformanceLogger logger = new PerformanceLogger();
        logger.count(ChainProcessorTypeEnum.MATCHING);
        logger.count(ChainProcessorTypeEnum.RIGHTS);
        logger.count(ChainProcessorTypeEnum.RIGHTS);
        Map<ChainProcessorTypeEnum, Map<String, String>> metrics = logger.getMetrics(60);
        Map<String, String> metric1 = metrics.get(ChainProcessorTypeEnum.MATCHING);
        assertEquals("1(+1)", metric1.get(USAGES));
        assertEquals("1", metric1.get(USAGES_PER_MINUTE));
        Map<String, String> metric2 = metrics.get(ChainProcessorTypeEnum.RIGHTS);
        assertEquals("2(+2)", metric2.get(USAGES));
        assertEquals("2", metric2.get(USAGES_PER_MINUTE));
    }
}
