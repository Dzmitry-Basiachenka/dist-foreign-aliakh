package com.copyright.rup.dist.foreign.service.api;

/**
 * Represents interface of service for detail licensee class business logic.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/27/2020
 *
 * @author Anton Azarenka
 */
public interface IDetailLicenseeClassService {

    /**
     * Checks whether detail licensee class exist id with provided enrollment profile and discipline ignoring case
     * in database.
     *
     * @param enrollmentProfile enrollment profile
     * @param discipline        discipline
     * @return {@code true} if detail licensee class is present, {@code false} otherwise
     */
    boolean detailLicenceClassIdExist(String enrollmentProfile, String discipline);
}
