package com.copyright.rup.dist.foreign.service.impl.config.micrometer;

import java.time.Duration;

import io.micrometer.core.instrument.config.validate.Validated;
import io.micrometer.core.instrument.step.StepRegistryConfig;

/**
 * Represents interface for Micrometer step registry configuration.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 07/05/2023
 *
 * @author Anton Azarenka
 */
public interface ILoggingRegistryConfig extends StepRegistryConfig {

    /**
     * Default config.
     */
    ILoggingRegistryConfig DEFAULT = k -> null;

    @Override
    default String prefix() {
        return "dist-foreign-registry";
    }

    @Override
    default Duration step() {
        return StepRegistryConfig.super.step();
    }

    @Override
    default boolean enabled() {
        return StepRegistryConfig.super.enabled();
    }

    @Override
    default int batchSize() {
        return StepRegistryConfig.super.batchSize();
    }

    @Override
    default Validated<?> validate() {
        return StepRegistryConfig.super.validate();
    }

    @Override
    String get(String key);

    @Override
    default void requireValid() {
        StepRegistryConfig.super.requireValid();
    }
}

