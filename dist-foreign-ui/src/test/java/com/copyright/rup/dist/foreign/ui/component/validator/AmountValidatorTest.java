package com.copyright.rup.dist.foreign.ui.component.validator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

/**
 * Verifies {@link AmountValidator}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/24/17
 *
 * @author Darya Baraukova
 */
@RunWith(Parameterized.class)
public class AmountValidatorTest {

    private String amount;
    private boolean expectedResult;
    private AmountValidator validator = new AmountValidator();

    /**
     * Constructor.
     *
     * @param amount         amount
     * @param expectedResult expected test result
     */
    public AmountValidatorTest(String amount, boolean expectedResult) {
        this.amount = amount;
        this.expectedResult = expectedResult;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {"  125", true},
            {"  125.2", true},
            {"125.23  ", true},
            {"152.44", true},
            {"555.9", true},
            {"84.44415", false},
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
            {"0.00", false},
            {"+0.00", false}
        });
    }

    @Test
    public void testValidate() {
        assertEquals(expectedResult, validator.isValid(amount));
    }
}
