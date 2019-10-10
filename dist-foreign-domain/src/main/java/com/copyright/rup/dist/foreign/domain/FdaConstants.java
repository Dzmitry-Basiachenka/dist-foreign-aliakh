package com.copyright.rup.dist.foreign.domain;

import com.google.common.collect.ImmutableSet;

import java.util.EnumSet;
import java.util.Set;

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
     * Product family for RRO with CLA account number.
     */
    public static final String CLA_FAS_PRODUCT_FAMILY = "FAS2";

    /**
     * NTS product family.
     */
    public static final String NTS_PRODUCT_FAMILY = "NTS";

    /**
     * Rightsholder preference code for the rightsholder FDA participation flag from PRM.
     */
    public static final String IS_RH_FDA_PARTICIPATING_PREFERENCE_CODE = "IS-RH-FDA-PARTICIPATING";

    /**
     * Rightsholder preference code for the ineligible rightsholder flag in PRM.
     */
    public static final String IS_RH_DIST_INELIGIBLE_CODE = "ISRHDISTINELIGIBLE";

    /**
     * Rightsholder preference code for STM flag in PRM.
     */
    public static final String IS_RH_STM_IPRO_CODE = "IS-RH-STM-IPRO";

    /**
     * Key for all products for reading rightsholder preferences from PRM.
     */
    public static final String ALL_PRODUCTS_KEY = "*";

    /**
     * Belletristic work classification.
     */
    public static final String BELLETRISTIC_CLASSIFICATION = "BELLETRISTIC";

    /**
     * STM work classification.
     */
    public static final String STM_CLASSIFICATION = "STM";

    /**
     * NON-STM work classification.
     */
    public static final String NON_STM_CLASSIFICATION = "NON-STM";

    /**
     * Set of FAS and FAS2 product families.
     */
    public static final Set<String> FAS_FAS2_PRODUCT_FAMILY_SET =
        ImmutableSet.of(FAS_PRODUCT_FAMILY, CLA_FAS_PRODUCT_FAMILY);

    /**
     * Archived scenario statuses.
     */
    public static final EnumSet<ScenarioStatusEnum> ARCHIVED_SCENARIO_STATUSES =
        EnumSet.of(ScenarioStatusEnum.SENT_TO_LM, ScenarioStatusEnum.ARCHIVED);

    private FdaConstants() {
        throw new AssertionError("Constructor shouldn't be called directly");
    }
}
