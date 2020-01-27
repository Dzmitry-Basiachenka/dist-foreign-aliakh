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
     * Finds detail licensee class id by enrollment profile and discipline.
     *
     * @param enrollmentProfile enrollment profile
     * @param discipline        discipline
     * @return detail licensee class id
     */
    String findLicenseeClassIdByDisciplineAndEnrollmentProfile(String enrollmentProfile, String discipline);
}
