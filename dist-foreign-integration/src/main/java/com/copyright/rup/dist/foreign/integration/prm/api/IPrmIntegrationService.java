package com.copyright.rup.dist.foreign.integration.prm.api;

import com.copyright.rup.dist.common.domain.Rightsholder;

import com.google.common.collect.Table;

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
     * <br/>value key  - primary rightsholder account number (parent organization)
     *
     * @param rightsholdersIds collection of related rightsholders (child organizations) ids
     * @return roll ups for the rightsholders with specified ids
     */
    Table<String, String, Long> getRollUps(Collection<String> rightsholdersIds);

    /**
     * Checks whether {@link com.copyright.rup.dist.common.domain.Rightsholder rightsholder} is participating or not.
     * Gets participating flag from {@link com.copyright.rup.dist.common.domain.RightsholderPreferences preferences}
     * by specified {@code rightsholder account number} and 'FAS' product family.
     * </br>
     * If preferences for specified product family were not found System finds preferences for the same rightsholder
     * account number and <b>{@code '*'}</b> ('*' is passed as a product family).
     *
     * @param accountNumber rightsholder account number to search
     * @return the found rightsholder participating flag or
     * {@code false} if there is neither no preferences for rightsholder or
     * {@code accountNumber} is a null or
     * {@code productFamily} is a blank string.
     */
    boolean isRightsholderParticipating(Long accountNumber);
}
