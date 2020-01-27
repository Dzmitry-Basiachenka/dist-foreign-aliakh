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
     * Checks if detail licensee class exist in database with enrollment profile and discipline.
     *
     * @param enrollmentProfile enrollment profile
     * @param discipline        dicipline
     * @return {@code true} if detail licensee class is present, {@code false} otherwise
     */
    boolean isDetailLicenceClassExist(String enrollmentProfile, String discipline);
}
