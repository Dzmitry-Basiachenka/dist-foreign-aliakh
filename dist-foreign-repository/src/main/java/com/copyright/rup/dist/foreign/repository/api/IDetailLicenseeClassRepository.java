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
     * Finds detail licensee class id by enrollment profile and discipline ignoring case.
     *
     * @param enrollmentProfile enrollment profile in any case
     * @param discipline        discipline in any case
     * @return detail licensee class id
     */
    String findLicenseeClassIdByDisciplineAndEnrollmentProfile(String enrollmentProfile, String discipline);
}
