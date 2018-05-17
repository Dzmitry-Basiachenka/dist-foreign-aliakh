package com.copyright.rup.dist.foreign.domain;

/**
 * Class that contains constants.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 05/17/18
 *
 * @author Uladzislau Shalamitski
 */
public final class FdaConstants {

    /**
     * FAS product family.
     */
    public static final String PRODUCT_FAMILY_FAS = "FAS";

    /**
     * Product family for rro with account number {@link FdaConstants#ACCOUNT_NUMBER_CLA_FAS}.
     */
    public static final String PRODUCT_FAMILY_CLA_FAS = "CLA_FAS";

    /**
     * Account number for {@link FdaConstants#PRODUCT_FAMILY_CLA_FAS} rro.
     */
    public static final Long ACCOUNT_NUMBER_CLA_FAS = 2000017000L;

    private FdaConstants() {
        throw new AssertionError("Constructor shouldn't be called directly");
    }
}
