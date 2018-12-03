package com.copyright.rup.dist.foreign.integration.oracle.impl.handler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Verifies {@link OracleRhTaxInformationRequest}.
 * <p/>
 * Copyright (C) 2015 copyright.com
 * <p/>
 * Date: 12/03/18
 *
 * @author Pavel Liakh
 */
public class OracleRhTaxInformationRequestTest {

    private static final Long PAYEE_ACCOUNT_NUMBER = 123456789L;

    @Test
    public void testConstructorWithParameters() {
        OracleRhTaxInformationRequest oracleRhTaxInformationRequest =
            new OracleRhTaxInformationRequest(PAYEE_ACCOUNT_NUMBER);
        assertEquals(PAYEE_ACCOUNT_NUMBER, oracleRhTaxInformationRequest.getPayeeAccountNumber(), 0);
        assertEquals(PAYEE_ACCOUNT_NUMBER, oracleRhTaxInformationRequest.getTboAccountNumber(), 0);
    }
}
