package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

/**
 * Verifies {@link UsageStatusEnumValidator}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 03/27/2018
 *
 * @author Aliaksandr Liakh
 */
@RunWith(Parameterized.class)
public class UsageStatusEnumValidatorTest {

    private final String value;
    private final boolean expectedResult;

    /**
     * Constructor.
     *
     * @param value          expected value
     * @param expectedResult expected result
     */
    public UsageStatusEnumValidatorTest(String value, boolean expectedResult) {
        this.value = value;
        this.expectedResult = expectedResult;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {null, false},
            {StringUtils.EMPTY, false},
            {"TEST", false},
            {"WORK_RESEARCH", true}
        });
    }

    @Test
    public void testIsValid() {
        UsageStatusEnumValidator validator = new UsageStatusEnumValidator(UsageStatusEnum.WORK_RESEARCH);
        assertEquals(expectedResult, validator.isValid(value));
        assertEquals("Usage status should be 'WORK_RESEARCH'", validator.getErrorMessage());
    }
}
