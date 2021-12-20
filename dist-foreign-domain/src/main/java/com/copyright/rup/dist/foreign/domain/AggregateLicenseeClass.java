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
    private String description;

    /**
     * Default constructor.
     */
    public AggregateLicenseeClass() {
    }

    /**
     * Constructor.
     *
     * @param aggregateLicenseeClass instance of {@link AggregateLicenseeClass} to copy
     */
    public AggregateLicenseeClass(AggregateLicenseeClass aggregateLicenseeClass) {
        this.setId(aggregateLicenseeClass.getId());
        enrollmentProfile = aggregateLicenseeClass.getEnrollmentProfile();
        discipline = aggregateLicenseeClass.getDiscipline();
        description = aggregateLicenseeClass.getDescription();
    }

    public String getIdAndDescription() {
        return String.format("%s - %s", getId(), getDescription());
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
            .append(description, that.description)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(enrollmentProfile)
            .append(discipline)
            .append(description)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("enrollmentProfile", enrollmentProfile)
            .append("discipline", discipline)
            .append("description", description)
            .toString();
    }
}
