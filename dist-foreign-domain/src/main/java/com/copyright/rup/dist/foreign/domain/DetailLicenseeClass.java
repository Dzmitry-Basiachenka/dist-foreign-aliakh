package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

/**
 * Domain object for detail licensee class.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/11/20
 *
 * @author Ihar Suvorau
 */
//TODO {isuvorau} apply in AaclUsage
public class DetailLicenseeClass extends StoredEntity<Integer> {

    private String enrollmentProfile;
    private String discipline;
    private AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();

    /**
     * Default constructor.
     */
    public DetailLicenseeClass() {
    }

    /**
     * Copy constructor.
     *
     * @param clone instance of {@link DetailLicenseeClass} to copy
     */
    public DetailLicenseeClass(DetailLicenseeClass clone) {
        setId(clone.getId());
        enrollmentProfile = clone.getEnrollmentProfile();
        discipline = clone.getDiscipline();
        AggregateLicenseeClass aggregateLicenseeClassClone = clone.getAggregateLicenseeClass();
        aggregateLicenseeClass = Objects.nonNull(aggregateLicenseeClassClone)
            ? new AggregateLicenseeClass(aggregateLicenseeClassClone)
            : null;
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

    public AggregateLicenseeClass getAggregateLicenseeClass() {
        return aggregateLicenseeClass;
    }

    public void setAggregateLicenseeClass(AggregateLicenseeClass aggregateLicenseeClass) {
        this.aggregateLicenseeClass = aggregateLicenseeClass;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        DetailLicenseeClass that = (DetailLicenseeClass) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(enrollmentProfile, that.enrollmentProfile)
            .append(discipline, that.discipline)
            .append(aggregateLicenseeClass, that.aggregateLicenseeClass)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(enrollmentProfile)
            .append(discipline)
            .append(aggregateLicenseeClass)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("enrollmentProfile", enrollmentProfile)
            .append("discipline", discipline)
            .append("aggregateLicenseeClass", aggregateLicenseeClass)
            .toString();
    }
}
