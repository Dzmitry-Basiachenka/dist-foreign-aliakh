package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.Usage;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Collection;
import java.util.List;

/**
 * Validates {@link WorkTitleStandardNumberValidator}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 05/16/2019
 *
 * @author Ihar Suvorau
 */
@RunWith(Parameterized.class)
public class WorkTitleStandardNumberValidatorTest {

    private static final long WR_WRK_INST = 185367895L;
    private static final String WORK_TITLE = "Work title";
    private static final String STANDARD_NUMBER = "5212-4152";

    private final Long wrWrkInst;
    private final String workTitle;
    private final String standardNumber;
    private final boolean expectedResult;

    /**
     * Constructor.
     *
     * @param wrWrkInst      market period from
     * @param workTitle      market period to
     * @param standardNumber expected result
     */
    public WorkTitleStandardNumberValidatorTest(Long wrWrkInst, String workTitle, String standardNumber,
                                                boolean expectedResult) {
        this.wrWrkInst = wrWrkInst;
        this.workTitle = workTitle;
        this.standardNumber = standardNumber;
        this.expectedResult = expectedResult;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return List.of(new Object[][]{
            {null, null, null, true},
            {WR_WRK_INST, WORK_TITLE, STANDARD_NUMBER, true},
            {WR_WRK_INST, WORK_TITLE, null, true},
            {WR_WRK_INST, WORK_TITLE, StringUtils.EMPTY, true},
            {WR_WRK_INST, null, STANDARD_NUMBER, true},
            {WR_WRK_INST, StringUtils.EMPTY, STANDARD_NUMBER, true},
            {WR_WRK_INST, null, null, false},
            {WR_WRK_INST, StringUtils.EMPTY, StringUtils.EMPTY, false},
            {null, WORK_TITLE, null, true},
            {null, WORK_TITLE, StringUtils.EMPTY, true},
            {null, null, STANDARD_NUMBER, true},
            {null, StringUtils.EMPTY, STANDARD_NUMBER, true}
        });
    }

    @Test
    public void testGetErrorMessage() {
        WorkTitleStandardNumberValidator validator = new WorkTitleStandardNumberValidator();
        assertEquals(expectedResult, validator.isValid(buildUsage()));
        assertEquals(
            "Work Title or Standard Number: Both fields values cannot be null or empty when Wr Wrk Inst is not null",
            validator.getErrorMessage());
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setWrWrkInst(wrWrkInst);
        usage.setWorkTitle(workTitle);
        usage.setStandardNumber(standardNumber);
        return usage;
    }
}
