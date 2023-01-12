package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.UdmUsage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * Verifies {@link SurveyDateValidator}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/03/21
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(Parameterized.class)
public class SurveyDateValidatorTest {

    private final LocalDate surveyStartDate;
    private final LocalDate surveyEndDate;
    private final boolean expectedResult;

    /**
     * Constructor.
     *
     * @param surveyStartDate survey start date
     * @param surveyEndDate   survey end date
     * @param expectedResult  expected result
     */
    public SurveyDateValidatorTest(LocalDate surveyStartDate, LocalDate surveyEndDate, boolean expectedResult) {
        this.surveyStartDate = surveyStartDate;
        this.surveyEndDate = surveyEndDate;
        this.expectedResult = expectedResult;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return List.of(new Object[][]{
            {LocalDate.of(2020, 10, 20), LocalDate.of(2020, 10, 20), true},
            {LocalDate.of(2020, 10, 20), LocalDate.of(2020, 10, 21), true},
            {LocalDate.of(2020, 10, 20), LocalDate.of(2020, 10, 19), false}
        });
    }

    @Test
    public void testIsValid() {
        SurveyDateValidator validator = new SurveyDateValidator();
        assertEquals(expectedResult, validator.isValid(buildUdmUsage(surveyStartDate, surveyEndDate)));
        assertEquals("Survey End Date should be greater than or equal to Survey Start Date",
            validator.getErrorMessage());
    }

    private UdmUsage buildUdmUsage(LocalDate startDate, LocalDate endDate) {
        UdmUsage udmUsage = new UdmUsage();
        udmUsage.setSurveyStartDate(startDate);
        udmUsage.setSurveyEndDate(endDate);
        return udmUsage;
    }
}
