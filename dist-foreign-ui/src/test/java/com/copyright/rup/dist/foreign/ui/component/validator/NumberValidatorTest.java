package com.copyright.rup.dist.foreign.ui.component.validator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

/**
 * Verifies {@link NumberValidator}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/20/2017
 *
 * @author Mikita Hladkikh
 */
@RunWith(Parameterized.class)
public class NumberValidatorTest {

    private final String number;
    private final boolean expectedResult;
    private final NumberValidator validator = new NumberValidator();

    /**
     * Constructor.
     *
     * @param number         number
     * @param expectedResult expected test result
     */
    public NumberValidatorTest(String number, boolean expectedResult) {
        this.number = number;
        this.expectedResult = expectedResult;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {"0", true},
            {"01458784", true},
            {"998784", true},
            {"  998784   ", true},
            {"a", false},
            {"4a", false},
            {" ", false},
            {"-1", false},
            {"100.", false},
            {"null", false},
            {"4.47e+8", false},
            {"b3a5e", false}
        });
    }

    @Test
    public void testValidate() {
        assertEquals(expectedResult, validator.isValid(number));
    }
}
