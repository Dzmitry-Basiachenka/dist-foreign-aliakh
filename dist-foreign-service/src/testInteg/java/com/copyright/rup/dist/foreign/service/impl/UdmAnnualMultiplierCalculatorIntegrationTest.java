package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.foreign.service.impl.acl.UdmAnnualMultiplierCalculator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

/**
 * Verifies {@link UdmAnnualMultiplierCalculator}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/19/2021
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UdmAnnualMultiplierCalculatorIntegrationTest {

    @Autowired
    private UdmAnnualMultiplierCalculator udmAnnualMultiplierCalculator;

    @Test
    public void testCalculate() {
        LocalDate surveyStartDate = LocalDate.of(2021, 1, 1);
        assertCalculate(25, surveyStartDate, surveyStartDate.plusDays(0));
        assertCalculate(25, surveyStartDate, surveyStartDate.plusDays(19));
        assertCalculate(12, surveyStartDate, surveyStartDate.plusDays(20));
        assertCalculate(12, surveyStartDate, surveyStartDate.plusDays(39));
        assertCalculate(6, surveyStartDate, surveyStartDate.plusDays(40));
        assertCalculate(6, surveyStartDate, surveyStartDate.plusDays(69));
        assertCalculate(4, surveyStartDate, surveyStartDate.plusDays(70));
        assertCalculate(4, surveyStartDate, surveyStartDate.plusDays(99));
        assertCalculate(3, surveyStartDate, surveyStartDate.plusDays(100));
        assertCalculate(3, surveyStartDate, surveyStartDate.plusDays(149));
        assertCalculate(2, surveyStartDate, surveyStartDate.plusDays(150));
        assertCalculate(2, surveyStartDate, surveyStartDate.plusDays(239));
        assertCalculate(1, surveyStartDate, surveyStartDate.plusDays(240));
        assertCalculate(1, surveyStartDate, surveyStartDate.plusDays(Integer.MAX_VALUE - 1));
    }

    @Test(expected = RupRuntimeException.class)
    public void testCalculateException() {
        LocalDate surveyStartDate = LocalDate.of(2021, 1, 1);
        udmAnnualMultiplierCalculator.calculate(surveyStartDate, surveyStartDate.minusDays(1));
    }

    private void assertCalculate(int expectedAnnualMultiplier, LocalDate surveyStartDate, LocalDate surveyEndDate) {
        assertEquals(expectedAnnualMultiplier, udmAnnualMultiplierCalculator.calculate(surveyStartDate, surveyEndDate));
    }
}
