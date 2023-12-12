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

    private static final long serialVersionUID = 2103850538146445915L;

    private String scenarioId;
    private Integer period;
    private String originalDetailId;
    private Long wrWrkInst;
    private String systemTitle;
    private DetailLicenseeClass detailLicenseeClass;
    private Integer aggregateLicenseeClassId;
    private String aggregateLicenseeClassName;
    private PublicationType publicationType;
    private BigDecimal price;
    private Boolean priceFlag;
    private BigDecimal content;
    private Boolean contentFlag;
    private BigDecimal contentUnitPrice;
    private Boolean contentUnitPriceFlag;
    private BigDecimal numberOfCopies;
    private BigDecimal usageAgeWeight;
    private BigDecimal weightedCopies;
    private String surveyCountry;
    private String reportedTypeOfUse;
    private String typeOfUse;
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

    public PublicationType getPublicationType() {
        return publicationType;
    }

    public void setPublicationType(PublicationType publicationType) {
        this.publicationType = publicationType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getPriceFlag() {
        return priceFlag;
    }

    public void setPriceFlag(Boolean priceFlag) {
        this.priceFlag = priceFlag;
    }

    public BigDecimal getContent() {
        return content;
    }

    public void setContent(BigDecimal content) {
        this.content = content;
    }

    public Boolean getContentFlag() {
        return contentFlag;
    }

    public void setContentFlag(Boolean contentFlag) {
        this.contentFlag = contentFlag;
    }

    public BigDecimal getContentUnitPrice() {
        return contentUnitPrice;
    }

    public void setContentUnitPrice(BigDecimal contentUnitPrice) {
        this.contentUnitPrice = contentUnitPrice;
    }

    public Boolean getContentUnitPriceFlag() {
        return contentUnitPriceFlag;
    }

    public void setContentUnitPriceFlag(Boolean contentUnitPriceFlag) {
        this.contentUnitPriceFlag = contentUnitPriceFlag;
    }

    public BigDecimal getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(BigDecimal numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
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

    public String getReportedTypeOfUse() {
        return reportedTypeOfUse;
    }

    public void setReportedTypeOfUse(String reportedTypeOfUse) {
        this.reportedTypeOfUse = reportedTypeOfUse;
    }

    public String getTypeOfUse() {
        return typeOfUse;
    }

    public void setTypeOfUse(String typeOfUse) {
        this.typeOfUse = typeOfUse;
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
            .append(price, that.price)
            .append(priceFlag, that.priceFlag)
            .append(content, that.content)
            .append(contentFlag, that.contentFlag)
            .append(contentUnitPrice, that.contentUnitPrice)
            .append(contentUnitPriceFlag, that.contentUnitPriceFlag)
            .append(numberOfCopies, that.numberOfCopies)
            .append(usageAgeWeight, that.usageAgeWeight)
            .append(weightedCopies, that.weightedCopies)
            .append(surveyCountry, that.surveyCountry)
            .append(reportedTypeOfUse, that.reportedTypeOfUse)
            .append(typeOfUse, that.typeOfUse)
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
            .append(price)
            .append(priceFlag)
            .append(content)
            .append(contentFlag)
            .append(contentUnitPrice)
            .append(contentUnitPriceFlag)
            .append(numberOfCopies)
            .append(usageAgeWeight)
            .append(weightedCopies)
            .append(surveyCountry)
            .append(reportedTypeOfUse)
            .append(typeOfUse)
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
            .append("price", price)
            .append("priceFlag", priceFlag)
            .append("content", content)
            .append("contentFlag", contentFlag)
            .append("contentUnitPrice", contentUnitPrice)
            .append("contentUnitPriceFlag", contentUnitPriceFlag)
            .append("numberOfCopies", numberOfCopies)
            .append("usageAgeWeight", usageAgeWeight)
            .append("weightedCopies", weightedCopies)
            .append("surveyCountry", surveyCountry)
            .append("reportedTypeOfUse", reportedTypeOfUse)
            .append("typeOfUse", typeOfUse)
            .append("scenarioShareDetails", scenarioShareDetails)
            .toString();
    }
}
