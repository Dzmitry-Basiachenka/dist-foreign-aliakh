package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;

import java.util.List;

/**
 * Represents interface of service for detail and aggregate licensee class business logic.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/27/2020
 *
 * @author Anton Azarenka
 */
public interface ILicenseeClassService {

    /**
     * Checks whether AACL detail licensee class with provided enrollment profile and discipline exists in database.
     *
     * @param enrollmentProfile enrollment profile (case insensitive)
     * @param discipline        discipline (case insensitive)
     * @return {@code true} if detail licensee class is present, {@code false} otherwise
     */
    boolean aaclDetailLicenseeClassExists(String enrollmentProfile, String discipline);

    /**
     * Gets list of {@link AggregateLicenseeClass}es by product family.
     *
     * @param productFamily product family
     * @return list of found {@link AggregateLicenseeClass}es by product family.
     */
    List<AggregateLicenseeClass> getAggregateLicenseeClasses(String productFamily);

    /**
     * Gets list of {@link DetailLicenseeClass}es by product family.
     *
     * @param productFamily product family
     * @return list of existing {@link DetailLicenseeClass}es.
     */
    List<DetailLicenseeClass> getDetailLicenseeClasses(String productFamily);

    /**
     * Gets list of {@link DetailLicenseeClass}es by scenario id.
     *
     * @param scenarioId scenario id
     * @return list of {@link DetailLicenseeClass}es
     */
    List<DetailLicenseeClass> getDetailLicenseeClassesByScenarioId(String scenarioId);
}
