package com.copyright.rup.dist.foreign.integration.prm.api;

import com.copyright.rup.dist.common.domain.Rightsholder;

import com.google.common.collect.Table;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
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
     * Gets the table of roll ups for the rightsholders with specified ids.
     * Returned table has the following structure:
     * <br/>row key    - related rightsholder id (child organization)
     * <br/>column key - product id (license product id)
     * <br/>value key  - primary rightsholder (parent organization)
     *
     * @param rightsholdersIds collection of related rightsholders (child organizations) ids
     * @return roll ups for the rightsholders with specified ids
     */
    Table<String, String, Rightsholder> getRollUps(Collection<String> rightsholdersIds);

    /**
     * Checks whether {@link com.copyright.rup.dist.common.domain.Rightsholder rightsholder} is participating or not.
     * Gets participating flag from {@link com.copyright.rup.dist.foreign.domain.FdaConstants preferences}
     * by specified rightsholder id and product family.
     * </br>
     * If preferences for specified product family were not found System finds preferences for the same rightsholder
     * id and <b>{@code '*'}</b> ('*' is passed as a product family).
     *
     * @param rightsholderId rightsholder id
     * @param productFamily  product family
     * @return the found rightsholder participating flag or
     * {@code false} if there is neither no preferences for rightsholder
     */
    boolean isRightsholderParticipating(String rightsholderId, String productFamily);

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
     * @param rhParticipatingFlag RH participating flag
     * @return RH participating service fee
     */
    BigDecimal getRhParticipatingServiceFee(boolean rhParticipatingFlag);

    /**
     * Checks whether {@link com.copyright.rup.dist.common.domain.Rightsholder} is STM rightsholder.
     * Gets STM flag {@link com.copyright.rup.dist.foreign.domain.FdaConstants#IS_RH_STM_IPRO_CODE} by specified
     * rightsholder id and product family.
     * </br>
     * If preferences for NTS product family were not found System finds preferences for the same rightsholder id
     * and <b>{@code '*'}</b> ('*' is passed as a product family).
     *
     * @param rightsholderId rightsholder id
     * @param productFamily  product family
     * @return {@code true} if RH is STM RH, otherwise {@code false}
     */
    boolean isStmRightsholder(String rightsholderId, String productFamily);
}
