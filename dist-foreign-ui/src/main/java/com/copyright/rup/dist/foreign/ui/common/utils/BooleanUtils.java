package com.copyright.rup.dist.foreign.ui.common.utils;

import java.util.Objects;

/**
 * Class for conversions between {@link Boolean} and {@link String}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/14/2021
 *
 * @author Aliaksandr Liakh
 */
public final class BooleanUtils {

    private BooleanUtils() {
        throw new AssertionError("Constructor shouldn't be called directly");
    }

    /**
     * Converts instance of {@link Boolean} to "Y", "N" or {@code null} instance of {@link String}.
     *
     * @param value instance of {@link Boolean}
     * @return "Y", "N" or {@code null} instance of {@link String}
     */
    public static String toYNString(Boolean value) {
        if (Objects.isNull(value)) {
            return null;
        } else {
            return value ? "Y" : "N";
        }
    }
}
