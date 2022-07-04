package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents ACL scenario detail.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/01/2022
 *
 * @author Anton Azarenka
 */
public class AclScenarioDetail extends StoredEntity<String> {

    private String scenarioId;
    private Integer period;
    private String originalDetailId;
    private Long wrWrkInst;
    private String systemTitle;
    private DetailLicenseeClass detailLicenseeClass;
    private Integer aggregateLicenseeClassId;
    private String aggregateLicenseeClassName;
    private PublicationType publicationType;
    private BigDecimal contentUnitPrice;
    private Long quantity;
    private BigDecimal usageAgeWeight;
    private BigDecimal weightedCopies;
    private String surveyCountry;
    private List<AclScenarioShareDetail> scenarioShareDetails;

    public String getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(String scenarioId) {
        this.scenarioId = scenarioId;
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

    public DetailLicenseeClass getDetailLicenseeClass() {
        return detailLicenseeClass;
    }

    public void setDetailLicenseeClass(DetailLicenseeClass detailLicenseeClass) {
        this.detailLicenseeClass = detailLicenseeClass;
    }

    public PublicationType getPublicationType() {
        return publicationType;
    }

    public void setPublicationType(PublicationType publicationType) {
        this.publicationType = publicationType;
    }

    public BigDecimal getContentUnitPrice() {
        return contentUnitPrice;
    }

    public void setContentUnitPrice(BigDecimal contentUnitPrice) {
        this.contentUnitPrice = contentUnitPrice;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUsageAgeWeight() {
        return usageAgeWeight;
    }

    public void setUsageAgeWeight(BigDecimal usageAgeWeight) {
        this.usageAgeWeight = usageAgeWeight;
    }

    public BigDecimal getWeightedCopies() {
        return weightedCopies;
    }

    public void setWeightedCopies(BigDecimal weightedCopies) {
        this.weightedCopies = weightedCopies;
    }

    public String getSurveyCountry() {
        return surveyCountry;
    }

    public void setSurveyCountry(String surveyCountry) {
        this.surveyCountry = surveyCountry;
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

    public List<AclScenarioShareDetail> getScenarioShareDetails() {
        return scenarioShareDetails;
    }

    public void setScenarioShareDetails(List<AclScenarioShareDetail> scenarioShareDetails) {
        this.scenarioShareDetails = scenarioShareDetails;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclScenarioDetail that = (AclScenarioDetail) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(scenarioId, that.scenarioId)
            .append(period, that.period)
            .append(originalDetailId, that.originalDetailId)
            .append(wrWrkInst, that.wrWrkInst)
            .append(systemTitle, that.systemTitle)
            .append(detailLicenseeClass, that.detailLicenseeClass)
            .append(aggregateLicenseeClassId, that.aggregateLicenseeClassId)
            .append(aggregateLicenseeClassName, that.aggregateLicenseeClassName)
            .append(publicationType, that.publicationType)
            .append(contentUnitPrice, that.contentUnitPrice)
            .append(quantity, that.quantity)
            .append(usageAgeWeight, that.usageAgeWeight)
            .append(weightedCopies, that.weightedCopies)
            .append(surveyCountry, that.surveyCountry)
            .append(scenarioShareDetails, that.scenarioShareDetails)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(scenarioId)
            .append(period)
            .append(originalDetailId)
            .append(wrWrkInst)
            .append(systemTitle)
            .append(detailLicenseeClass)
            .append(aggregateLicenseeClassId)
            .append(aggregateLicenseeClassName)
            .append(publicationType)
            .append(contentUnitPrice)
            .append(quantity)
            .append(usageAgeWeight)
            .append(weightedCopies)
            .append(surveyCountry)
            .append(scenarioShareDetails)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("scenarioId", scenarioId)
            .append("period", period)
            .append("originalDetailId", originalDetailId)
            .append("wrWrkInst", wrWrkInst)
            .append("systemTitle", systemTitle)
            .append("detailLicenseeClass", detailLicenseeClass)
            .append("aggregateLicenseeClassId", aggregateLicenseeClassId)
            .append("aggregateLicenseeClassName", aggregateLicenseeClassName)
            .append("publicationType", publicationType)
            .append("contentUnitPrice", contentUnitPrice)
            .append("quantity", quantity)
            .append("usageAgeWeight", usageAgeWeight)
            .append("weightedCopies", weightedCopies)
            .append("surveyCountry", surveyCountry)
            .append("scenarioShareDetails", scenarioShareDetails)
            .toString();
    }
}
