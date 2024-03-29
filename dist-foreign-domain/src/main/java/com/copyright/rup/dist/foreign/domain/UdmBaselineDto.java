package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents UDM baseline dto.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/21
 *
 * @author Dzmitry Basiachenka
 */
public class UdmBaselineDto extends StoredEntity<String> {

    private static final long serialVersionUID = 1614533382656108796L;

    private Integer period;
    private UdmUsageOriginEnum usageOrigin;
    private String originalDetailId;
    private Long wrWrkInst;
    private String systemTitle;
    private Integer detailLicenseeClassId;
    private String detailLicenseeClassName;
    private Integer aggregateLicenseeClassId;
    private String aggregateLicenseeClassName;
    private String surveyCountry;
    private UdmChannelEnum channel;
    private String typeOfUse;
    private String reportedTypeOfUse;
    private BigDecimal annualizedCopies;
    private String valueId;

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public UdmUsageOriginEnum getUsageOrigin() {
        return usageOrigin;
    }

    public void setUsageOrigin(UdmUsageOriginEnum usageOrigin) {
        this.usageOrigin = usageOrigin;
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

    public UdmChannelEnum getChannel() {
        return channel;
    }

    public void setChannel(UdmChannelEnum channel) {
        this.channel = channel;
    }

    public String getTypeOfUse() {
        return typeOfUse;
    }

    public void setTypeOfUse(String typeOfUse) {
        this.typeOfUse = typeOfUse;
    }

    public String getReportedTypeOfUse() {
        return reportedTypeOfUse;
    }

    public void setReportedTypeOfUse(String reportedTypeOfUse) {
        this.reportedTypeOfUse = reportedTypeOfUse;
    }

    public BigDecimal getAnnualizedCopies() {
        return annualizedCopies;
    }

    public void setAnnualizedCopies(BigDecimal annualizedCopies) {
        this.annualizedCopies = annualizedCopies;
    }

    public String getValueId() {
        return valueId;
    }

    public void setValueId(String valueId) {
        this.valueId = valueId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        UdmBaselineDto that = (UdmBaselineDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(period, that.period)
            .append(usageOrigin, that.usageOrigin)
            .append(originalDetailId, that.originalDetailId)
            .append(wrWrkInst, that.wrWrkInst)
            .append(systemTitle, that.systemTitle)
            .append(detailLicenseeClassId, that.detailLicenseeClassId)
            .append(detailLicenseeClassName, that.detailLicenseeClassName)
            .append(aggregateLicenseeClassId, that.aggregateLicenseeClassId)
            .append(aggregateLicenseeClassName, that.aggregateLicenseeClassName)
            .append(surveyCountry, that.surveyCountry)
            .append(channel, that.channel)
            .append(typeOfUse, that.typeOfUse)
            .append(reportedTypeOfUse, that.reportedTypeOfUse)
            .append(annualizedCopies, that.annualizedCopies)
            .append(valueId, that.valueId)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(period)
            .append(usageOrigin)
            .append(originalDetailId)
            .append(wrWrkInst)
            .append(systemTitle)
            .append(detailLicenseeClassId)
            .append(detailLicenseeClassName)
            .append(aggregateLicenseeClassId)
            .append(aggregateLicenseeClassName)
            .append(surveyCountry)
            .append(channel)
            .append(typeOfUse)
            .append(reportedTypeOfUse)
            .append(annualizedCopies)
            .append(valueId)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("period", period)
            .append("usageOrigin", usageOrigin)
            .append("originalDetailId", originalDetailId)
            .append("wrWrkInst", wrWrkInst)
            .append("systemTitle", systemTitle)
            .append("detailLicenseeClassId", detailLicenseeClassId)
            .append("detailLicenseeClassName", detailLicenseeClassName)
            .append("aggregateLicenseeClassId", aggregateLicenseeClassId)
            .append("aggregateLicenseeClassName", aggregateLicenseeClassName)
            .append("surveyCountry", surveyCountry)
            .append("channel", channel)
            .append("typeOfUse", typeOfUse)
            .append("reportedTypeOfUse", reportedTypeOfUse)
            .append("annualizedCopies", annualizedCopies)
            .append("valueId", valueId)
            .toString();
    }
}
