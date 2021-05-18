package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;

import java.util.List;

/**
 * Interface for detail and aggregate licensee class repository.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/27/2020
 *
 * @author Anton Azarenka
 */
public interface ILicenseeClassRepository {

    /**
     * Checks whether detail licensee class with provided enrollment profile and discipline exists in database.
     *
     * @param enrollmentProfile enrollment profile (case insensitive)
     * @param discipline        discipline (case insensitive)
     * @return {@code true} if detail licensee class is present, {@code false} otherwise
     */
    boolean detailLicenseeClassExists(String enrollmentProfile, String discipline);

    /**
     * Gets list of {@link AggregateLicenseeClass}es by product family.
     *
     * @param productFamily product family
     * @return list of existing {@link AggregateLicenseeClass}es.
     */
    List<AggregateLicenseeClass> findAggregateLicenseeClassesByProductFamily(String productFamily);

    /**
     * Gets list of {@link DetailLicenseeClass}es by product family.
     *
     * @param productFamily product family
     * @return list of existing {@link DetailLicenseeClass}es.
     */
    List<DetailLicenseeClass> findDetailLicenseeClassesByProductFamily(String productFamily);

    /**
     * Finds list of {@link DetailLicenseeClass}es by scenario id.
     *
     * @param scenarioId scenario id
     * @return list of {@link DetailLicenseeClass}es
     */
    List<DetailLicenseeClass> findDetailLicenseeClassesByScenarioId(String scenarioId);
}
