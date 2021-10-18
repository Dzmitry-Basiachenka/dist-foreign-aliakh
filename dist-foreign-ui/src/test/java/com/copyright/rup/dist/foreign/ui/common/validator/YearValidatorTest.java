package com.copyright.rup.dist.foreign.ui.common.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import com.vaadin.server.SerializablePredicate;
import org.junit.Test;

/**
 * Verifies {@link YearValidator}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/18/2021
 *
 * @author Aliaksandr Liakh
 */
public class YearValidatorTest {

    @Test
    public void testConstructor() {
        TestUtils.validatePrivateConstructor(YearValidator.class);
    }

    @Test
    public void testGetValidator() {
        SerializablePredicate<String> validator = YearValidator.getValidator();
        assertFalse(validator.test("1949"));
        assertTrue(validator.test("1950"));
        assertTrue(validator.test(" 1950 "));
        assertTrue(validator.test("2099"));
        assertTrue(validator.test(" 2099 "));
        assertFalse(validator.test("2100"));
    }
}
