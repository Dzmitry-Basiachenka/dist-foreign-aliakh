package com.copyright.rup.dist.foreign.domain;

import com.google.common.collect.ImmutableSet;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
     * AACL product family.
     */
    public static final String AACL_PRODUCT_FAMILY = "AACL";

    /**
     * ACL product family.
     */
    public static final String ACL_PRODUCT_FAMILY = "ACL";

    /**
     * ACLCI product family.
     */
    public static final String ACLCI_PRODUCT_FAMILY = "ACLCI";

    /**
     * ACL_UDM_USAGE product family.
     */
    public static final String ACL_UDM_USAGE_PRODUCT_FAMILY = "ACL_UDM_USAGE";

    /**
     * ACL_UDM_VALUE product family.
     */
    public static final String ACL_UDM_VALUE_PRODUCT_FAMILY = "ACL_UDM_VALUE";

    /**
     * SAL product family.
     */
    public static final String SAL_PRODUCT_FAMILY = "SAL";

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
     * Rightsholder preference code for TBO flag in PRM.
     */
    public static final String TAX_BENEFICIAL_OWNER_CODE = "TAXBENEFICIALOWNER";

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
     * Right status GRANT.
     */
    public static final String RIGHT_STATUS_GRANT = "GRANT";

    /**
     * Right status DENY.
     */
    public static final String RIGHT_STATUS_DENY = "DENY";

    /**
     * Set of FAS and FAS2 product families.
     */
    public static final Set<String> FAS_FAS2_PRODUCT_FAMILY_SET =
        ImmutableSet.of(FAS_PRODUCT_FAMILY, CLA_FAS_PRODUCT_FAMILY);

    /**
     * Set of ACL License Types.
     */
    public static final Set<String> ACL_LICENSE_TYPES = ImmutableSet.of("ACL", "MACL", "VGW", "JACDCL");

    /**
     * Set of available months for ACL periods.
     */
    public static final Set<String> ACL_PERIOD_MONTHS = ImmutableSet.of("06", "12");

    /**
     * Archived scenario statuses.
     */
    public static final EnumSet<ScenarioStatusEnum> ARCHIVED_SCENARIO_STATUSES =
        EnumSet.of(ScenarioStatusEnum.SENT_TO_LM, ScenarioStatusEnum.ARCHIVED);

    /**
     * Default amount with scale 2.
     */
    public static final BigDecimal DEFAULT_AMOUNT_SCALE_2 = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    /**
     * Default amount with scale 10.
     */
    public static final BigDecimal DEFAULT_AMOUNT_SCALE_10 = BigDecimal.ZERO.setScale(10, RoundingMode.HALF_UP);

    private FdaConstants() {
        throw new AssertionError("Constructor shouldn't be called directly");
    }
}
