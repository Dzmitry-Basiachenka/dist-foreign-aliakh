package com.copyright.rup.dist.foreign.domain.common.util;

import com.copyright.rup.dist.common.util.LogUtils.ILogWrapper;
import com.copyright.rup.dist.foreign.domain.Scenario;

/**
 * Provides methods to log information for FDA related objects.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/08/18
 *
 * @author Ihar Suvorau
 */
public final class ForeignLogUtils {

    private ForeignLogUtils() {
        throw new AssertionError("Constructor shouldn't be called directly");
    }

    /**
     * Wraps the {@link Scenario} object and provides name and status for log message.
     *
     * @param scenario instance of {@link Scenario}
     * @return instance of {@link ILogWrapper}
     */
    public static ILogWrapper scenario(Scenario scenario) {
        return new ILogWrapper() {
            @Override
            public String toString() {
                return null == scenario
                    ? "Scenario={NULL}"
                    : String.format("ScenarioName='%s', Status='%s'", scenario.getName(), scenario.getStatus());
            }
        };
    }
}
