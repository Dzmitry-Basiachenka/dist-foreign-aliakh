package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.UdmUsage;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

/**
 * Validates {@link UdmWorkInfoValidator}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 05/24/2021
 *
 * @author Ihar Suvorau
 */
@RunWith(Parameterized.class)
public class UdmWorkInfoValidatorTest {

    private static final long WR_WRK_INST = 185367895L;
    private static final String TITLE = "Delmar Learning, a division of Cengage Learning";
    private static final String STANDARD_NUMBER = "5212-4152";

    private final Long wrWrkInst;
    private final String title;
    private final String standardNumber;
    private final boolean expectedResult;

    public UdmWorkInfoValidatorTest(Long wrWrkInst, String title, String standardNumber,
                                    boolean expectedResult) {
        this.wrWrkInst = wrWrkInst;
        this.title = title;
        this.standardNumber = standardNumber;
        this.expectedResult = expectedResult;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {null, null, null, false},
            {WR_WRK_INST, TITLE, STANDARD_NUMBER, true},
            {WR_WRK_INST, TITLE, null, true},
            {WR_WRK_INST, TITLE, StringUtils.EMPTY, true},
            {WR_WRK_INST, null, STANDARD_NUMBER, true},
            {WR_WRK_INST, StringUtils.EMPTY, STANDARD_NUMBER, true},
            {WR_WRK_INST, null, null, true},
            {WR_WRK_INST, StringUtils.EMPTY, StringUtils.EMPTY, true},
            {null, TITLE, null, true},
            {null, TITLE, StringUtils.EMPTY, true},
            {null, null, STANDARD_NUMBER, true},
            {null, StringUtils.EMPTY, STANDARD_NUMBER, true}
        });
    }

    @Test
    public void testGetErrorMessage() {
        UdmWorkInfoValidator validator = new UdmWorkInfoValidator();
        assertEquals(expectedResult, validator.isValid(buildUsage()));
        assertEquals("No Work information found", validator.getErrorMessage());
    }

    private UdmUsage buildUsage() {
        UdmUsage usage = new UdmUsage();
        usage.setWrWrkInst(wrWrkInst);
        usage.setReportedTitle(title);
        usage.setReportedStandardNumber(standardNumber);
        return usage;
    }
}
