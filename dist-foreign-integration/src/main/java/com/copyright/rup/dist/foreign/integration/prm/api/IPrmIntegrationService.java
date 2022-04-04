package com.copyright.rup.dist.foreign.integration.prm.api;

import com.copyright.rup.dist.common.domain.Country;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.AclIneligibleRightsholder;

import com.google.common.collect.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Interface for service to encapsulate logic for PRM system.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/21/2017
 *
 * @author Mikalai Bezmen
 */
public interface IPrmIntegrationService {

    /**
     * Retrieves list of {@link Rightsholder} by specified set of account numbers from PRM.
     *
     * @param accountNumbers the set of account numbers
     * @return the list of {@link Rightsholder}
     */
    List<Rightsholder> getRightsholders(Set<Long> accountNumbers);

    /**
     * Gets {@link Rightsholder} from PRM by account number.
     *
     * @param accountNumber rightsholder account number
     * @return instance of {@link Rightsholder}
     */
    Rightsholder getRightsholder(Long accountNumber);

    /**
     * Gets the map of roll ups for the rightsholders with specified ids.
     * Returned map has the following structure:
     * <br/>first key - related rightsholder id (child organization)
     * <br/>second key - product id (license product id)
     * <br/>value - primary rightsholder (parent organization)
     *
     * @param rightsholdersIds set of related rightsholders (child organizations) ids
     * @return roll ups for the rightsholders with specified ids
     */
    Map<String, Map<String, Rightsholder>> getRollUps(Set<String> rightsholdersIds);

    /**
     * Gets countries from PRM.
     *
     * @return map of country id to {@link Country}
     */
    Map<String, Country> getCountries();

    /**
     * Checks whether {@link com.copyright.rup.dist.common.domain.Rightsholder rightsholder} is participating or not.
     * Gets participating flag from {@link com.copyright.rup.dist.foreign.domain.FdaConstants preferences}
     * by specified rightsholder id and product family.
     * </br>
     * If preferences for specified product family were not found System finds preferences for the same rightsholder
     * id and <b>{@code '*'}</b> ('*' is passed as a product family).
     *
     * @param preferences    map with preferences
     * @param rightsholderId rightsholder identifier
     * @param productFamily  product family
     * @return the found rightsholder participating flag or
     * {@code false} if there is neither no preferences for rightsholder
     */
    boolean isRightsholderParticipating(Map<String, Table<String, String, Object>> preferences, String rightsholderId,
                                        String productFamily);

    /**
     * Gets rightsholder to preferences table map, key is rightsholderId, value - preferences table.
     * Table has the following structure:
     * <br/>row key    - product family
     * <br/>column key - preference code
     * <br/>value key  - preference value
     *
     * @param rightsholderIds set of rightsholder ids
     * @return rightsholder to preferences table map
     */
    Map<String, Table<String, String, Object>> getPreferences(Set<String> rightsholderIds);

    /**
     * Checks whether {@link com.copyright.rup.dist.common.domain.Rightsholder} is eligible for NTS distribution or not.
     * Gets participating flag {@link com.copyright.rup.dist.foreign.domain.FdaConstants#IS_RH_DIST_INELIGIBLE_CODE}
     * by specified rightsholder id and {@link com.copyright.rup.dist.foreign.domain.FdaConstants#NTS_PRODUCT_FAMILY}.
     * </br>
     * If preferences for NTS product family were not found System finds preferences for the same rightsholder id
     * and <b>{@code '*'}</b> ('*' is passed as a product family).
     *
     * @param rightsholderId rightsholder id
     * @return {@code true} if RH is eligible for NTS distribution, otherwise {@code false}
     */
    boolean isRightsholderEligibleForNtsDistribution(String rightsholderId);

    /**
     * Gets service fee configuration value based on RH participating flag.
     *
     * @param rhParticipating RH participating flag
     * @return RH participating service fee
     */
    BigDecimal getRhParticipatingServiceFee(boolean rhParticipating);

    /**
     * Checks whether {@link com.copyright.rup.dist.common.domain.Rightsholder} is STM rightsholder.
     * Gets STM flag {@link com.copyright.rup.dist.foreign.domain.FdaConstants#IS_RH_STM_IPRO_CODE} by specified
     * rightsholder id and product family.
     * </br>
     * If preferences for specified product family were not found System finds preferences for the same rightsholder id
     * and <b>{@code '*'}</b> ('*' is passed as a product family).
     *
     * @param rightsholderId rightsholder id
     * @param productFamily  product family
     * @return {@code true} if RH is STM RH, otherwise {@code false}
     */
    boolean isStmRightsholder(String rightsholderId, String productFamily);

    /**
     * Checks whether {@link com.copyright.rup.dist.common.domain.Rightsholder} is tax beneficial owner.
     * Gets TBO flag {@link com.copyright.rup.dist.foreign.domain.FdaConstants#TAX_BENEFICIAL_OWNER_CODE} by specified
     * rightsholder id and product family.
     * </br>
     * If preferences for specified product family were not found System finds preferences for the same rightsholder id
     * and <b>{@code '*'}</b> ('*' is passed as a product family).
     *
     * @param rightsholderId rightsholder id
     * @param productFamily  product family
     * @return {@code true} if RH is TBO, otherwise {@code false}
     */
    boolean isRightsholderTaxBeneficialOwner(String rightsholderId, String productFamily);

    /**
     * Gets map from rightsholder ids to STM rightsholder flag.
     * Gets STM flag {@link com.copyright.rup.dist.foreign.domain.FdaConstants#IS_RH_STM_IPRO_CODE} by specified
     * rightsholder id and product family.
     * </br>
     * If preferences for NTS product family were not found System finds preferences for the same rightsholder id
     * and <b>{@code '*'}</b> ('*' is passed as a product family).
     *
     * @param rightsholdersIds list of rightsholder ids
     * @param productFamily    product family
     * @return map from rightsholder ids to STM rightsholders flag
     */
    Map<String, Boolean> getStmRightsholderPreferenceMap(Set<String> rightsholdersIds, String productFamily);

    /**
     * Gets set of {@link AclIneligibleRightsholder}s.
     *
     * @param periodEndDate period end date
     * @param licenseType   licenseType
     * @return set of {@link AclIneligibleRightsholder}s
     */
    Set<AclIneligibleRightsholder> getIneligibleRightsholders(LocalDate periodEndDate, String licenseType);
}
