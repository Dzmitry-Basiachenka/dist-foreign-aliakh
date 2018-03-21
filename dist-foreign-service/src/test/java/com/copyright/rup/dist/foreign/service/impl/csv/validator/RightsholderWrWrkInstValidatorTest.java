package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.Usage;

import org.junit.Test;

/**
 * Validates {@link RightsholderWrWrkInstValidator}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/24/18
 *
 * @author Uladzislau Shalamitski
 */
public class RightsholderWrWrkInstValidatorTest {

    private final RightsholderWrWrkInstValidator validator = new RightsholderWrWrkInstValidator();

    @Test
    public void testErrorMessage() {
        assertEquals("Wr Wrk Inst: Field value cannot be null or empty if RH Account # field value is presented",
            validator.getErrorMessage());
    }

    @Test
    public void testValidationWithRhAccountNumberAndWrWrkInst() {
        assertTrue(validator.isValid(buildUsage(185367895L, 1000009522L)));
    }

    @Test
    public void testValidationWithRhAccountNumberAndWithoutWrWrkInst() {
        assertFalse(validator.isValid(buildUsage(null, 1000009522L)));
    }

    @Test
    public void testValidationWithoutRhAccountNumberAndWithWrWrkInst() {
        assertTrue(validator.isValid(buildUsage(185367895L, null)));
    }

    @Test
    public void testValidationWithoutRhAccountNumberAndWrWrkInst() {
        assertTrue(validator.isValid(buildUsage(null, null)));
    }

    @Test(expected = NullPointerException.class)
    public void testValidationWithNullUsage() {
        assertTrue(validator.isValid(null));
    }

    private Usage buildUsage(Long wrWrkInst, Long rhAccountNumber) {
        Usage usage = new Usage();
        usage.setWrWrkInst(wrWrkInst);
        usage.getRightsholder().setAccountNumber(rhAccountNumber);
        return usage;
    }
}
