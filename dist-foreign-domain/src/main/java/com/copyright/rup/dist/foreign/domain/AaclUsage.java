package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

    private DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
    private PublicationType publicationType = new PublicationType();
    private UsageAge usageAge = new UsageAge();
    private String originalPublicationType;
    private String institution;
    private String usageSource;
    private String rightLimitation;
    private Integer numberOfPages;
    private LocalDate batchPeriodEndDate;
    private String baselineId;
    private BigDecimal volumeWeight;
    private BigDecimal valueWeight;
    private BigDecimal valueShare;
    private BigDecimal volumeShare;
    private BigDecimal totalShare;

    public String getBaselineId() {
        return baselineId;
    }

    public void setBaselineId(String baselineId) {
        this.baselineId = baselineId;
    }

    public DetailLicenseeClass getDetailLicenseeClass() {
        return detailLicenseeClass;
    }

    public void setDetailLicenseeClass(DetailLicenseeClass detailLicenseeClass) {
        this.detailLicenseeClass = detailLicenseeClass;
    }

    public AggregateLicenseeClass getAggregateLicenseeClass() {
        return detailLicenseeClass.getAggregateLicenseeClass();
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

    public UsageAge getUsageAge() {
        return usageAge;
    }

    public void setUsageAge(UsageAge usageAge) {
        this.usageAge = usageAge;
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

    public BigDecimal getVolumeWeight() {
        return volumeWeight;
    }

    public void setVolumeWeight(BigDecimal volumeWeight) {
        this.volumeWeight = volumeWeight;
    }

    public BigDecimal getValueWeight() {
        return valueWeight;
    }

    public void setValueWeight(BigDecimal valueWeight) {
        this.valueWeight = valueWeight;
    }

    public BigDecimal getValueShare() {
        return valueShare;
    }

    public void setValueShare(BigDecimal valueShare) {
        this.valueShare = valueShare;
    }

    public BigDecimal getVolumeShare() {
        return volumeShare;
    }

    public void setVolumeShare(BigDecimal volumeShare) {
        this.volumeShare = volumeShare;
    }

    public BigDecimal getTotalShare() {
        return totalShare;
    }

    public void setTotalShare(BigDecimal totalShare) {
        this.totalShare = totalShare;
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
            .append(detailLicenseeClass, that.detailLicenseeClass)
            .append(publicationType, that.publicationType)
            .append(originalPublicationType, that.originalPublicationType)
            .append(institution, that.institution)
            .append(usageAge, that.usageAge)
            .append(usageSource, that.usageSource)
            .append(rightLimitation, that.rightLimitation)
            .append(numberOfPages, that.numberOfPages)
            .append(batchPeriodEndDate, that.batchPeriodEndDate)
            .append(baselineId, that.baselineId)
            .append(volumeWeight, that.volumeWeight)
            .append(valueWeight, that.valueWeight)
            .append(volumeShare, that.volumeShare)
            .append(valueShare, that.valueShare)
            .append(totalShare, that.totalShare)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(detailLicenseeClass)
            .append(publicationType)
            .append(originalPublicationType)
            .append(institution)
            .append(usageAge)
            .append(usageSource)
            .append(rightLimitation)
            .append(numberOfPages)
            .append(batchPeriodEndDate)
            .append(baselineId)
            .append(volumeWeight)
            .append(valueWeight)
            .append(volumeShare)
            .append(valueShare)
            .append(totalShare)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("detailLicenseeClass", detailLicenseeClass)
            .append("publicationType", publicationType)
            .append("originalPublicationType", originalPublicationType)
            .append("institution", institution)
            .append("usageAge", usageAge)
            .append("usageSource", usageSource)
            .append("rightLimitation", rightLimitation)
            .append("numberOfPages", numberOfPages)
            .append("batchPeriodEndDate", batchPeriodEndDate)
            .append("baselineId", baselineId)
            .append("volumeWeight", volumeWeight)
            .append("valueWeight", valueWeight)
            .append("volumeShare", volumeShare)
            .append("valueShare", valueShare)
            .append("totalShare", totalShare)
            .toString();
    }
}
