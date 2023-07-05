package com.copyright.rup.dist.foreign.service.impl.config.micrometer;

import com.copyright.rup.common.logging.RupLogUtils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.util.concurrent.TimeUnit;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.step.StepMeterRegistry;
import io.micrometer.core.instrument.util.NamedThreadFactory;

/**
 * Implementation of {@link StepMeterRegistry}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 07/05/2023
 *
 * @author Anton Azarenka
 */
public class FdaLoggingMeterRegistry extends StepMeterRegistry {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    /**
     * Default constructor.
     *
     * @param config config
     * @param clock  clock
     */
    public FdaLoggingMeterRegistry(ILoggingRegistryConfig config, Clock clock) {
        super(config, clock);
        start(new NamedThreadFactory("dist-foreign-metrics-publisher"));
    }

    @Override
    protected void publish() {
        forEachMeter(item -> item.measure().forEach(measurement -> {
            if (measurement.getValue() > 0) {
                Meter.Id id = item.getId();
                LOGGER.info(id.withName(generateIdName(id)) + StringUtils.SPACE + measurement);
            }
        }));
    }

    @Override
    protected TimeUnit getBaseTimeUnit() {
        return TimeUnit.MILLISECONDS;
    }

    private String generateIdName(Meter.Id id) {
        String separator = ".";
        String className = StringUtils.substringAfterLast(id.getTag("class"), separator);
        String methodName = id.getTag("method");
        String idName = StringUtils.join(className, separator, methodName);
        if (StringUtils.endsWith(id.getName(), ".percentile")) {
            idName = idName + ".percentile";
        } else if (StringUtils.endsWith(id.getName(), ".counted")) {
            idName = idName + ".counted";
        }
        return idName;
    }
}
