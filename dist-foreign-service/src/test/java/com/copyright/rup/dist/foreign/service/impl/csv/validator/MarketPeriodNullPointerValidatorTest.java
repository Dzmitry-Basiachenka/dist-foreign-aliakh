package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import org.junit.Test;

/**
 * Verifies {@link YearValidator} in case of invalid Usage.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/28/17
 *
 * @author Mikalai Bezmen
 */
public class MarketPeriodNullPointerValidatorTest {

    @Test(expected = NullPointerException.class)
    public void testIsValidNullUsage() {
        MarketPeriodValidator validator = new MarketPeriodValidator();
        validator.isValid(null);
    }
}
