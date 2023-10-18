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
 * Verifies {@link AclciGradeValidator}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/19/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(Parameterized.class)
public class AclciGradeValidatorTest {

    private final String value;
    private final boolean expectedResult;

    /**
     * Constructor.
     *
     * @param value          expected value
     * @param expectedResult expected result
     */
    public AclciGradeValidatorTest(String value, boolean expectedResult) {
        this.value = value;
        this.expectedResult = expectedResult;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return List.of(new Object[][]{
            {null, false},
            {StringUtils.EMPTY, false},
            {" ", false},
            {"K", true},
            {"0", false},
            {"1", true},
            {"2", true},
            {"3", true},
            {"4", true},
            {"5", true},
            {"6", true},
            {"7", true},
            {"8", true},
            {"10", true},
            {"11", true},
            {"12", true},
            {"13", false},
            {"HE", true},
            {"HS", true},
            {"E", true},
            {"M", true},
            {"ME", false},
            {"error", false}
        });
    }

    @Test
    public void testGetErrorMessage() {
        AclciGradeValidator validator = new AclciGradeValidator();
        assertEquals(expectedResult, validator.isValid(value));
        assertEquals("Field value should be any of: K, 1-12, HE, HS, M, E", validator.getErrorMessage());
    }
}
