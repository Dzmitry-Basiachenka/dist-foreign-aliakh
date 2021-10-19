package com.copyright.rup.dist.foreign.ui.common.validator;

import com.vaadin.server.SerializablePredicate;
import org.apache.commons.lang3.StringUtils;

/**
 * Class to validate years.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/18/2021
 *
 * @author Aliaksandr Liakh
 */
public final class YearValidator {

    /**
     * Minimum year.
     */
    public static final int MIN_YEAR = 1950;

    /**
     * Maximum year.
     */
    public static final int MAX_YEAR = 2099;

    private YearValidator() {
        throw new AssertionError("Constructor shouldn't be called directly");
    }

    /**
     * Gets validator.
     *
     * @return instance of {@link SerializablePredicate}
     */
    public static SerializablePredicate<String> getValidator() {
        return value -> Integer.parseInt(StringUtils.trim(value)) >= MIN_YEAR
            && Integer.parseInt(StringUtils.trim(value)) <= MAX_YEAR;
    }
}
