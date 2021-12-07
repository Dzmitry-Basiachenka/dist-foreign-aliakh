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
public class DetailLicenseeClass extends StoredEntity<Integer> {

    private String enrollmentProfile;
    private String discipline;
    private String description;
    private AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();

    /**
     * Default constructor.
     */
    public DetailLicenseeClass() {
    }

    /**
     * Constructor.
     *
     * @param detailLicenseeClass instance of {@link DetailLicenseeClass} to copy
     */
    public DetailLicenseeClass(DetailLicenseeClass detailLicenseeClass) {
        this.setId(detailLicenseeClass.getId());
        this.enrollmentProfile = detailLicenseeClass.getEnrollmentProfile();
        this.discipline = detailLicenseeClass.getDiscipline();
        this.description = detailLicenseeClass.getDescription();
        AggregateLicenseeClass aggregateLicenseeClassClone = detailLicenseeClass.getAggregateLicenseeClass();
        this.aggregateLicenseeClass = Objects.nonNull(aggregateLicenseeClassClone)
            ? new AggregateLicenseeClass(aggregateLicenseeClassClone)
            : null;
    }

    /**
     * Constructor.
     *
     * @param id          id
     * @param description description
     */
    public DetailLicenseeClass(Integer id, String description) {
        this.setId(id);
        this.description = description;
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

    public AggregateLicenseeClass getAggregateLicenseeClass() {
        return aggregateLicenseeClass;
    }

    public void setAggregateLicenseeClass(AggregateLicenseeClass aggregateLicenseeClass) {
        this.aggregateLicenseeClass = aggregateLicenseeClass;
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
        DetailLicenseeClass that = (DetailLicenseeClass) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(enrollmentProfile, that.enrollmentProfile)
            .append(discipline, that.discipline)
            .append(description, that.description)
            .append(aggregateLicenseeClass, that.aggregateLicenseeClass)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(enrollmentProfile)
            .append(discipline)
            .append(description)
            .append(aggregateLicenseeClass)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("enrollmentProfile", enrollmentProfile)
            .append("discipline", discipline)
            .append("description", description)
            .append("aggregateLicenseeClass", aggregateLicenseeClass)
            .toString();
    }
}
