package com.copyright.rup.dist.foreign.domain;

import java.util.EnumSet;

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
    public static final String FAS_PRODUCT_FAMILY = "FAS";

    /**
     * Product family for RRO with account number {@link FdaConstants#CLA_ACCOUNT_NUMBER}.
     */
    public static final String CLA_FAS_PRODUCT_FAMILY = "FAS2";

    /**
     * NTS product family.
     */
    public static final String NTS_PRODUCT_FAMILY = "NTS";

    /**
     * Account number for {@link FdaConstants#CLA_FAS_PRODUCT_FAMILY} RRO.
     */
    public static final Long CLA_ACCOUNT_NUMBER = 2000017000L;

    /**
     * Rightsholder preference code for the rightsholder FDA participation flag from PRM.
     */
    public static final String IS_RH_FDA_PARTICIPATING_PREFERENCE_CODE = "IS-RH-FDA-PARTICIPATING";

    /**
     * Key for all products for reading rightsholder preferences from PRM.
     */
    public static final String ALL_PRODUCTS_KEY = "*";

    /**
     * Archived scenario statuses.
     */
    public static final EnumSet<ScenarioStatusEnum> ARCHIVED_SCENARIO_STATUSES =
        EnumSet.of(ScenarioStatusEnum.SENT_TO_LM, ScenarioStatusEnum.ARCHIVED);

    private FdaConstants() {
        throw new AssertionError("Constructor shouldn't be called directly");
    }
}
