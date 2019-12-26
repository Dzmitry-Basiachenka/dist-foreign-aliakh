package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents AACL specific usage details.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/26/2019
 *
 * @author Ihar Suvorau
 */
public class AaclUsage {

    private String detailLicenseeClass;
    private String enrollmentProfile;
    private String discipline;
    private String publicationType;
    private String institution;
    private int usagePeriod;
    private String usageSource;
    private String rightLimitation;
    private int numberOfPages;

    public String getDetailLicenseeClass() {
        return detailLicenseeClass;
    }

    public void setDetailLicenseeClass(String detailLicenseeClass) {
        this.detailLicenseeClass = detailLicenseeClass;
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

    public String getPublicationType() {
        return publicationType;
    }

    public void setPublicationType(String publicationType) {
        this.publicationType = publicationType;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public int getUsagePeriod() {
        return usagePeriod;
    }

    public void setUsagePeriod(int usagePeriod) {
        this.usagePeriod = usagePeriod;
    }

    public String getUsageSource() {
        return usageSource;
    }

    public void setUsageSource(String usageSource) {
        this.usageSource = usageSource;
    }

    public String getRightLimitation() {
        return rightLimitation;
    }

    public void setRightLimitation(String rightLimitation) {
        this.rightLimitation = rightLimitation;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AaclUsage that = (AaclUsage) obj;
        return new EqualsBuilder()
            .append(detailLicenseeClass, that.detailLicenseeClass)
            .append(enrollmentProfile, that.enrollmentProfile)
            .append(discipline, that.discipline)
            .append(publicationType, that.publicationType)
            .append(institution, that.institution)
            .append(usagePeriod, that.usagePeriod)
            .append(usageSource, that.usageSource)
            .append(rightLimitation, that.rightLimitation)
            .append(numberOfPages, that.numberOfPages)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(detailLicenseeClass)
            .append(enrollmentProfile)
            .append(discipline)
            .append(publicationType)
            .append(institution)
            .append(usagePeriod)
            .append(usageSource)
            .append(rightLimitation)
            .append(numberOfPages)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("detailLicenseeClass", detailLicenseeClass)
            .append("enrollmentProfile", enrollmentProfile)
            .append("discipline", discipline)
            .append("publicationType", publicationType)
            .append("institution", institution)
            .append("usagePeriod", usagePeriod)
            .append("usageSource", usageSource)
            .append("rightLimitation", rightLimitation)
            .append("numberOfPages", numberOfPages)
            .toString();
    }
}
