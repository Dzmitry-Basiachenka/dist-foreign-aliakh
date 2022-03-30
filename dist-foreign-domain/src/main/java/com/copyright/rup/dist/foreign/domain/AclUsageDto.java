package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents ACL usage DTO.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/30/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclUsageDto extends StoredEntity<String> {

    private String usageBatchId;
    private UdmUsageOriginEnum usageOrigin;
    private UdmChannelEnum channel;
    private Integer period;
    private String originalDetailId;
    private Long wrWrkInst;
    private String systemTitle;
    private Integer detailLicenseeClassId;
    private String detailLicenseeClassName;
    private Integer aggregateLicenseeClassId;
    private String aggregateLicenseeClassName;
    private String surveyCountry;
    private String pubTypeName;
    private BigDecimal contentUnitPrice;
    private String typeOfUse;
    private BigDecimal annualizedCopies;

    public String getUsageBatchId() {
        return usageBatchId;
    }

    public void setUsageBatchId(String usageBatchId) {
        this.usageBatchId = usageBatchId;
    }

    public UdmUsageOriginEnum getUsageOrigin() {
        return usageOrigin;
    }

    public void setUsageOrigin(UdmUsageOriginEnum usageOrigin) {
        this.usageOrigin = usageOrigin;
    }

    public UdmChannelEnum getChannel() {
        return channel;
    }

    public void setChannel(UdmChannelEnum channel) {
        this.channel = channel;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getOriginalDetailId() {
        return originalDetailId;
    }

    public void setOriginalDetailId(String originalDetailId) {
        this.originalDetailId = originalDetailId;
    }

    public Long getWrWrkInst() {
        return wrWrkInst;
    }

    public void setWrWrkInst(Long wrWrkInst) {
        this.wrWrkInst = wrWrkInst;
    }

    public String getSystemTitle() {
        return systemTitle;
    }

    public void setSystemTitle(String systemTitle) {
        this.systemTitle = systemTitle;
    }

    public Integer getDetailLicenseeClassId() {
        return detailLicenseeClassId;
    }

    public void setDetailLicenseeClassId(Integer detailLicenseeClassId) {
        this.detailLicenseeClassId = detailLicenseeClassId;
    }

    public String getDetailLicenseeClassName() {
        return detailLicenseeClassName;
    }

    public void setDetailLicenseeClassName(String detailLicenseeClassName) {
        this.detailLicenseeClassName = detailLicenseeClassName;
    }

    public Integer getAggregateLicenseeClassId() {
        return aggregateLicenseeClassId;
    }

    public void setAggregateLicenseeClassId(Integer aggregateLicenseeClassId) {
        this.aggregateLicenseeClassId = aggregateLicenseeClassId;
    }

    public String getAggregateLicenseeClassName() {
        return aggregateLicenseeClassName;
    }

    public void setAggregateLicenseeClassName(String aggregateLicenseeClassName) {
        this.aggregateLicenseeClassName = aggregateLicenseeClassName;
    }

    public String getSurveyCountry() {
        return surveyCountry;
    }

    public void setSurveyCountry(String surveyCountry) {
        this.surveyCountry = surveyCountry;
    }

    public String getPubTypeName() {
        return pubTypeName;
    }

    public void setPubTypeName(String pubTypeName) {
        this.pubTypeName = pubTypeName;
    }

    public BigDecimal getContentUnitPrice() {
        return contentUnitPrice;
    }

    public void setContentUnitPrice(BigDecimal contentUnitPrice) {
        this.contentUnitPrice = contentUnitPrice;
    }

    public String getTypeOfUse() {
        return typeOfUse;
    }

    public void setTypeOfUse(String typeOfUse) {
        this.typeOfUse = typeOfUse;
    }

    public BigDecimal getAnnualizedCopies() {
        return annualizedCopies;
    }

    public void setAnnualizedCopies(BigDecimal annualizedCopies) {
        this.annualizedCopies = annualizedCopies;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclUsageDto that = (AclUsageDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(usageBatchId, that.usageBatchId)
            .append(usageOrigin, that.usageOrigin)
            .append(channel, that.channel)
            .append(period, that.period)
            .append(originalDetailId, that.originalDetailId)
            .append(wrWrkInst, that.wrWrkInst)
            .append(systemTitle, that.systemTitle)
            .append(detailLicenseeClassId, that.detailLicenseeClassId)
            .append(detailLicenseeClassName, that.detailLicenseeClassName)
            .append(aggregateLicenseeClassId, that.aggregateLicenseeClassId)
            .append(aggregateLicenseeClassName, that.aggregateLicenseeClassName)
            .append(surveyCountry, that.surveyCountry)
            .append(pubTypeName, that.pubTypeName)
            .append(contentUnitPrice, that.contentUnitPrice)
            .append(typeOfUse, that.typeOfUse)
            .append(annualizedCopies, that.annualizedCopies)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(usageBatchId)
            .append(usageOrigin)
            .append(channel)
            .append(period)
            .append(originalDetailId)
            .append(wrWrkInst)
            .append(systemTitle)
            .append(detailLicenseeClassId)
            .append(detailLicenseeClassName)
            .append(aggregateLicenseeClassId)
            .append(aggregateLicenseeClassName)
            .append(surveyCountry)
            .append(pubTypeName)
            .append(contentUnitPrice)
            .append(typeOfUse)
            .append(annualizedCopies)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("usageBatchId", usageBatchId)
            .append("usageOrigin", usageOrigin)
            .append("channel", channel)
            .append("period", period)
            .append("originalDetailId", originalDetailId)
            .append("wrWrkInst", wrWrkInst)
            .append("systemTitle", systemTitle)
            .append("detailLicenseeClassId", detailLicenseeClassId)
            .append("detailLicenseeClassName", detailLicenseeClassName)
            .append("aggregateLicenseeClassId", aggregateLicenseeClassId)
            .append("aggregateLicenseeClassName", aggregateLicenseeClassName)
            .append("surveyCountry", surveyCountry)
            .append("pubTypeName", pubTypeName)
            .append("contentUnitPrice", contentUnitPrice)
            .append("typeOfUse", typeOfUse)
            .append("annualizedCopies", annualizedCopies)
            .toString();
    }
}
