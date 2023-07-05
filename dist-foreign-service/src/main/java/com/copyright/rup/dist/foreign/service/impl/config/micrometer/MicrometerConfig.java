package com.copyright.rup.dist.foreign.service.impl.config.micrometer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;

/**
 * Configuration class for Micrometer.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 07/05/2023
 *
 * @author Anton Azarenka
 */
@Configuration
@EnableAspectJAutoProxy
public class MicrometerConfig {

    private static final String APPLICATION_NAME_KEY = "application";
    private static final String APPLICATION_NAME_VALUE = "dist-foreign";

    /**
     * Create a composite register to collect metrics and push metrics to different monitoring systems at the same time.
     *
     * @return composite register
     */
    @Bean
    public CompositeMeterRegistry registry() {
        CompositeMeterRegistry compositeMeterRegistry = new CompositeMeterRegistry();
        compositeMeterRegistry.add(new FdaLoggingMeterRegistry(ILoggingRegistryConfig.DEFAULT, Clock.SYSTEM));
        compositeMeterRegistry.config().commonTags(APPLICATION_NAME_KEY, APPLICATION_NAME_VALUE);
        return compositeMeterRegistry;
    }

    /**
     * Creating an aspect to be able to use the annotation {@link io.micrometer.core.annotation.Timed}.
     *
     * @param registry registry
     * @return TimedAspect
     */
    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }

    /**
     * Creating an aspect to be able to use the annotation {@link io.micrometer.core.annotation.Counted}.
     *
     * @param registry registry
     * @return CountedAspect
     */
    @Bean
    public CountedAspect countedAspect(MeterRegistry registry) {
        return new CountedAspect(registry);
    }
}
