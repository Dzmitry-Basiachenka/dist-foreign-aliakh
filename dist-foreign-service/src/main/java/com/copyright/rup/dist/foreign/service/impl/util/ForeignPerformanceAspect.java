package com.copyright.rup.dist.foreign.service.impl.util;

import com.copyright.rup.dist.common.service.impl.util.PerformanceAspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * Aspect to measure performance of methods.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/04/2019
 *
 * @author Pavel Liakh
 * @see PerformanceAspect
 */
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ForeignPerformanceAspect {

    private final PerformanceAspect performanceAspect;

    /**
     * Contructor.
     *
     * @param performanceThresholdMillis threshold in milliseconds
     * @param loggingEnabled             true to enable logging, false - otherwise
     */
    public ForeignPerformanceAspect(Integer performanceThresholdMillis, Boolean loggingEnabled) {
        performanceAspect = new PerformanceAspect(performanceThresholdMillis, loggingEnabled);
    }

    /**
     * Performs profiling of methods.
     * Methods to profile annotated with {@link Around} in this method.
     *
     * @param joinPoint joint point
     * @return method result
     * @throws Throwable if any
     */
    @Around("bean(*Service) || bean(*Repository) || bean(*Processor) || bean(*Consumer)")
    public Object doPerfLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        return performanceAspect.doPerfLogging(joinPoint);
    }
}
