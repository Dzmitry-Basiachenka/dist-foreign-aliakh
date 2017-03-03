package com.copyright.rup.dist.foreign.ui.component.validator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

/**
 * Verifies {@link GrossAmountValidator}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/24/17
 *
 * @author Darya Baraukova
 */
@RunWith(Parameterized.class)
public class GrossAmountValidatorTest {

    private String amount;
    private boolean expectedResult;
    private GrossAmountValidator validator = new GrossAmountValidator();

    /**
     * Constructor.
     *
     * @param amount         amount
     * @param expectedResult expected test result
     */
    public GrossAmountValidatorTest(String amount, boolean expectedResult) {
        this.amount = amount;
        this.expectedResult = expectedResult;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {"  125", false},
            {"  125.2", false},
            {"125.23  ", true},
            {"  106.44", true},
            {"  1024656.56   ", true},
            {"0.11", true},
            {"0.10", true},
            {"0.01", true},
            {"0.09", true},
            {"1.00", true},
            {"10.00", true},
            {"9999999999.99", true},
            {"999999999999", false},
            {"000125.52", false},
            {"01.44", false},
            {"10000000000.00", false},
            {"555.9", false},
            {"105.8 9", false},
            {"84.44415", false},
            {"99.451", false},
            {"33..5", false},
            {"86.0.5", false},
            {"amount", false},
            {"-15.55", false},
            {".55", false},
            {"874.", false},
            {" ", false},
            {"15,687", false},
            {"0", false},
            {"0.0", false},
            {"0.1", false},
            {"0.00", false},
            {"+0.00", false},
            {"-0.00", false}
        });
    }

    @Test
    public void testValidate() {
        assertEquals(expectedResult, validator.isValid(amount));
    }
}
