package com.copyright.rup.dist.foreign.repository.api;

/**
 * Interface for Detail licensee class repository.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/27/2020
 *
 * @author Anton Azarenka
 */
public interface IDetailLicenseeClassRepository {

    /**
     * Checks whether detail licensee class id exist with provided enrollment profile and discipline ignoring case
     * in database.
     *
     * @param enrollmentProfile enrollment profile case insensitive
     * @param discipline        discipline (case insensitive)
     * @return {@code true} if detail licensee class is present, {@code false} otherwise
     */
    boolean isLicenseeClassIdExist(String enrollmentProfile, String discipline);
}
