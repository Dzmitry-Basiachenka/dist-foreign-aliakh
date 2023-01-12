package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Collection;
import java.util.List;

/**
 * Verifies {@link AclciLicenseTypeValidator}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/14/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(Parameterized.class)
public class AclciLicenseTypeValidatorTest {

    private final String value;
    private final boolean expectedResult;

    /**
     * Constructor.
     *
     * @param value          expected value
     * @param expectedResult expected result
     */
    public AclciLicenseTypeValidatorTest(String value, boolean expectedResult) {
        this.value = value;
        this.expectedResult = expectedResult;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return List.of(new Object[][]{
            {null, false},
            {StringUtils.EMPTY, false},
            {" ", false},
            {"CURR_REPUB_K12", true},
            {"CURR_REPUB_HE", true},
            {"CURR_REUSE_K12", true},
            {"CURR_SHARE_K12", true},
            {"curr_repub_k12", true},
            {"curr_repub_he", true},
            {"curr_reuse_k12", true},
            {"curr_share_k12", true},
            {"error", false}
        });
    }

    @Test
    public void testGetErrorMessage() {
        AclciLicenseTypeValidator validator = new AclciLicenseTypeValidator();
        assertEquals(expectedResult, validator.isValid(value));
        assertEquals("Field value should be any of: [CURR_REPUB_K12, CURR_REPUB_HE, CURR_REUSE_K12, CURR_SHARE_K12]",
            validator.getErrorMessage());
    }
}
