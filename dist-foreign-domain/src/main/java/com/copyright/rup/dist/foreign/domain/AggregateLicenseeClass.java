package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Domain object for aggregate licensee class.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 2/5/20
 *
 * @author Stanislau Rudak
 */
public class AggregateLicenseeClass extends StoredEntity<Integer> {

    private String enrollmentProfile;
    private String discipline;

    /**
     * Default constructor.
     */
    public AggregateLicenseeClass() {
    }

    /**
     * Copy constructor.
     *
     * @param clone instance of {@link DetailLicenseeClass} to copy
     */
    public AggregateLicenseeClass(AggregateLicenseeClass clone) {
        this.setId(clone.getId());
        enrollmentProfile = clone.getEnrollmentProfile();
        discipline = clone.getDiscipline();
    }

    public String getEnrollmentProfile() {
        return enrollmentProfile;
    }

    public void setEnrollmentProfile(String enrollmentProfile) {
        this.enrollmentProfile = enrollmentProfile;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AggregateLicenseeClass that = (AggregateLicenseeClass) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(enrollmentProfile, that.enrollmentProfile)
            .append(discipline, that.discipline)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(enrollmentProfile)
            .append(discipline)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("enrollmentProfile", enrollmentProfile)
            .append("discipline", discipline)
            .toString();
    }
}
