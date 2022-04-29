package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Verifies {@link AclFundPoolAmountValidator}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/29/2022
 *
 * @author Anton Azarenka
 */
public class AclFundPoolAmountValidatorTest {

    private AclFundPoolAmountValidator validator;

    @Before
    public void setUp() {
        validator = new AclFundPoolAmountValidator();
    }

    @Test
    public void testIsValid() {
        assertTrue(validator.isValid(buildFundPoolDetail(new BigDecimal("2.5"), new BigDecimal("20.48"))));
        assertTrue(validator.isValid(buildFundPoolDetail(new BigDecimal("3.5"), new BigDecimal("3.5"))));
        assertFalse(validator.isValid(buildFundPoolDetail(new BigDecimal("19.85"), new BigDecimal("19.25"))));
        assertTrue(validator.isValid(buildFundPoolDetail(new BigDecimal("1.02"), new BigDecimal("1.02"))));
        assertFalse(validator.isValid(buildFundPoolDetail(new BigDecimal("1.23"), new BigDecimal("0.9"))));
    }

    @Test
    public void testGetErrorMessage() {
        assertEquals("Gross Amount should be greater or equal to the Net Amount", validator.getErrorMessage());
    }

    private AclFundPoolDetail buildFundPoolDetail(BigDecimal netAmount, BigDecimal grossAmount) {
        AclFundPoolDetail fundPoolDetail = new AclFundPoolDetail();
        fundPoolDetail.setGrossAmount(grossAmount);
        fundPoolDetail.setNetAmount(netAmount);
        return fundPoolDetail;
    }
}
