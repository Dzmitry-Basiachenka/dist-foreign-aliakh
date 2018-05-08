package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.Usage;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * Validates {@link WorkTitleValidator}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 05/04/2018
 *
 * @author Aliaksandr Liakh
 */
public class WorkTitleValidatorTest {

    private static final long WR_WRK_INST = 185367895L;
    private static final String WORK_TITLE = "Work title";

    private final WorkTitleValidator validator = new WorkTitleValidator();

    @Test
    public void testErrorMessage() {
        assertEquals("Work Title: Field value cannot be null or empty when Wr Wrk Inst is not null",
            validator.getErrorMessage());
    }

    @Test
    public void testValidationWrWrkInstAndWorkTitle() {
        assertTrue(validator.isValid(buildUsage(WR_WRK_INST, WORK_TITLE)));
    }

    @Test
    public void testValidationNullWrWrkInstAndWorkTitle() {
        assertTrue(validator.isValid(buildUsage(null, WORK_TITLE)));
    }

    @Test
    public void testValidationWrWrkInstAndNullWorkTitle() {
        assertFalse(validator.isValid(buildUsage(WR_WRK_INST, null)));
    }

    @Test
    public void testValidationWrWrkInstAndEmptyWorkTitle() {
        assertFalse(validator.isValid(buildUsage(WR_WRK_INST, StringUtils.EMPTY)));
    }

    @Test
    public void testValidationNullWrWrkInstAndNullWorkTitle() {
        assertTrue(validator.isValid(buildUsage(null, null)));
    }

    @Test
    public void testValidationNullWrWrkInstAndEmptyWorkTitle() {
        assertTrue(validator.isValid(buildUsage(null, StringUtils.EMPTY)));
    }

    @Test(expected = NullPointerException.class)
    public void testValidationNullUsage() {
        assertTrue(validator.isValid(null));
    }

    private Usage buildUsage(Long wrWrkInst, String workTitle) {
        Usage usage = new Usage();
        usage.setWrWrkInst(wrWrkInst);
        usage.setWorkTitle(workTitle);
        return usage;
    }
}
