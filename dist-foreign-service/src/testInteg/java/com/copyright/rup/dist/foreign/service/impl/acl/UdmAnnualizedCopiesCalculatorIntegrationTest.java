package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.junit.Assert.assertEquals;

import com.google.common.collect.Sets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.reflect.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 * Verifies {@link UdmAnnualizedCopiesCalculator}.
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
public class UdmAnnualizedCopiesCalculatorIntegrationTest {

    private static final String EMAIL_COPY = "EMAIL_COPY";
    private static final BigDecimal STATISTICAL_MULTIPLIER = BigDecimal.ONE.setScale(5, BigDecimal.ROUND_HALF_UP);

    @Autowired
    private UdmAnnualizedCopiesCalculator udmAnnualizedCopiesCalculator;

    @Test
    public void testTypesOfUseEmail() {
        assertEquals(Whitebox.getInternalState(UdmAnnualizedCopiesCalculator.class, "EMAIL_TOU"),
            Sets.newHashSet(EMAIL_COPY, "SHARE_SINGLE_ELECTRONIC_COPY", "SUBMIT_ELECTRONIC_COPY"));
    }

    @Test
    public void testTypesOfUseIntranet() {
        assertEquals(Whitebox.getInternalState(UdmAnnualizedCopiesCalculator.class, "INTRANET_TOU"),
            Sets.newHashSet("DISPLAY_IN_POWERPOINT", "DISTRIBUTE_IN_POWERPOINT", "STORE_COPY",
                "DIGITAL_SHARING_OTHER"));
    }

    @Test
    public void testCalculateTypeOfUseEmailApplyEmailCappedQuantity() {
        assertEquals(new BigDecimal("1505.00000"), udmAnnualizedCopiesCalculator.calculate(
            EMAIL_COPY, 301L, 5, STATISTICAL_MULTIPLIER));
    }

    @Test
    public void testCalculateTypeOfUseEmail() {
        assertEquals(new BigDecimal("1495.00000"), udmAnnualizedCopiesCalculator.calculate(
            EMAIL_COPY, 299L, 5, STATISTICAL_MULTIPLIER));
    }

    @Test
    public void testCalculateTypeOfUseEmailApplyMaxEmailAnnualizedQuantity() {
        assertEquals(new BigDecimal("1500.00000"), udmAnnualizedCopiesCalculator.calculate(
            EMAIL_COPY, 61L, 25, STATISTICAL_MULTIPLIER));
    }

    @Test
    public void testCalculateTypeOfUseIntranet() {
        assertEquals(new BigDecimal("225.00000"), udmAnnualizedCopiesCalculator.calculate(
            "DISPLAY_IN_POWERPOINT", 9L, 25, STATISTICAL_MULTIPLIER));
    }

    @Test
    public void testCalculateTypeOfUseIntranetApplyCappedQuantity() {
        assertEquals(new BigDecimal("250.00000"), udmAnnualizedCopiesCalculator.calculate(
            "DISPLAY_IN_POWERPOINT", 11L, 25, STATISTICAL_MULTIPLIER));
    }

    @Test
    public void testCalculateTypeOfUseOther() {
        assertEquals(new BigDecimal("300.00000"), udmAnnualizedCopiesCalculator.calculate(
            "COPY_FOR_MYSELF", 12L, 25, STATISTICAL_MULTIPLIER));
    }

    @Test
    public void testCalculateWithLargeNumbers() {
        assertEquals(new BigDecimal("24999999975.00000"), udmAnnualizedCopiesCalculator.calculate(
            "COPY_FOR_MYSELF", 25L, 999999999, STATISTICAL_MULTIPLIER));
    }
}
