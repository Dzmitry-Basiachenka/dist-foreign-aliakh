package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    private String detailLicenseeClassId;
    private String enrollmentProfile;
    private String discipline;
    private PublicationType publicationType = new PublicationType();
    private String originalPublicationType;
    private String institution;
    private int usagePeriod;
    private String usageSource;
    private String rightLimitation;
    private Integer numberOfPages;
    private LocalDate batchPeriodEndDate;
    private boolean baselineFlag;
    private BigDecimal publicationTypeWeight;

    public BigDecimal getPublicationTypeWeight() {
        return publicationTypeWeight;
    }

    public void setPublicationTypeWeight(BigDecimal publicationTypeWeight) {
        this.publicationTypeWeight = publicationTypeWeight;
    }

    public boolean isBaselineFlag() {
        return baselineFlag;
    }

    public void setBaselineFlag(boolean baselineFlag) {
        this.baselineFlag = baselineFlag;
    }

    public String getDetailLicenseeClassId() {
        return detailLicenseeClassId;
    }

    public void setDetailLicenseeClassId(String detailLicenseeClassId) {
        this.detailLicenseeClassId = detailLicenseeClassId;
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

    public PublicationType getPublicationType() {
        return publicationType;
    }

    public void setPublicationType(PublicationType publicationType) {
        this.publicationType = publicationType;
    }

    public String getOriginalPublicationType() {
        return originalPublicationType;
    }

    public void setOriginalPublicationType(String originalPublicationType) {
        this.originalPublicationType = originalPublicationType;
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

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public LocalDate getBatchPeriodEndDate() {
        return batchPeriodEndDate;
    }

    public void setBatchPeriodEndDate(LocalDate batchPeriodEndDate) {
        this.batchPeriodEndDate = batchPeriodEndDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AaclUsage that = (AaclUsage) obj;
        return new EqualsBuilder()
            .append(detailLicenseeClassId, that.detailLicenseeClassId)
            .append(enrollmentProfile, that.enrollmentProfile)
            .append(discipline, that.discipline)
            .append(publicationType, that.publicationType)
            .append(originalPublicationType, that.originalPublicationType)
            .append(institution, that.institution)
            .append(usagePeriod, that.usagePeriod)
            .append(usageSource, that.usageSource)
            .append(rightLimitation, that.rightLimitation)
            .append(numberOfPages, that.numberOfPages)
            .append(batchPeriodEndDate, that.batchPeriodEndDate)
            .append(publicationTypeWeight, that.publicationTypeWeight)
            .append(baselineFlag, that.baselineFlag)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(detailLicenseeClassId)
            .append(enrollmentProfile)
            .append(discipline)
            .append(publicationType)
            .append(originalPublicationType)
            .append(institution)
            .append(usagePeriod)
            .append(usageSource)
            .append(rightLimitation)
            .append(numberOfPages)
            .append(batchPeriodEndDate)
            .append(publicationTypeWeight)
            .append(baselineFlag)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("detailLicenseeClassId", detailLicenseeClassId)
            .append("enrollmentProfile", enrollmentProfile)
            .append("discipline", discipline)
            .append("publicationType", publicationType)
            .append("originalPublicationType", originalPublicationType)
            .append("institution", institution)
            .append("usagePeriod", usagePeriod)
            .append("usageSource", usageSource)
            .append("rightLimitation", rightLimitation)
            .append("numberOfPages", numberOfPages)
            .append("batchPeriodEndDate", batchPeriodEndDate)
            .append("publicationTypeWeight", publicationTypeWeight)
            .append("baselineFlag", baselineFlag)
            .toString();
    }
}
