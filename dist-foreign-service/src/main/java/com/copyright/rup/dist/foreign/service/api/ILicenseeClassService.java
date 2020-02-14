package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;

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
     * Checks whether detail licensee class with provided enrollment profile and discipline exists in database.
     *
     * @param enrollmentProfile enrollment profile (case insensitive)
     * @param discipline        discipline (case insensitive)
     * @return {@code true} if detail licensee class is present, {@code false} otherwise
     */
    boolean detailLicenceClassExists(String enrollmentProfile, String discipline);

    /**
     * @return list of existing {@link AggregateLicenseeClass}es.
     */
    List<AggregateLicenseeClass> getAggregateLicenseeClasses();
}