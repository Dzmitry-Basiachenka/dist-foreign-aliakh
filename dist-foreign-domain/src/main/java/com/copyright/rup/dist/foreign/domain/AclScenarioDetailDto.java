package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents ACL scenario detail dto.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/13/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclScenarioDetailDto extends StoredEntity<String> {

    private String originalDetailId;
    private String productFamily;
    private String usageBatchName;
    private Integer periodEndPeriod;
    private Long wrWrkInst;
    private String systemTitle;
    private Long rhAccountNumberPrint;
    private String rhNamePrint;
    private Long rhAccountNumberDigital;
    private String rhNameDigital;
    private Integer usagePeriod;
    private BigDecimal usageAgeWeight;
    private Integer detailLicenseeClassId;
    private String detailLicenseeClassName;
    private Integer aggregateLicenseeClassId;
    private String aggregateLicenseeClassName;
    private String surveyCountry;
    private String reportedTypeOfUse;
    private BigDecimal numberOfCopies;
    private BigDecimal weightedCopies;
    private PublicationType publicationType;
    private BigDecimal price;
    private boolean priceFlag;
    private BigDecimal content;
    private boolean contentFlag;
    private BigDecimal contentUnitPrice;
    private boolean contentUnitPriceFlag;
    private BigDecimal valueSharePrint;
    private BigDecimal volumeSharePrint;
    private BigDecimal detailSharePrint;
    private BigDecimal netAmountPrint;
    private BigDecimal valueShareDigital;
    private BigDecimal volumeShareDigital;
    private BigDecimal detailShareDigital;
    private BigDecimal netAmountDigital;
    private BigDecimal combinedNetAmount;

    public String getOriginalDetailId() {
        return originalDetailId;
    }

    public void setOriginalDetailId(String originalDetailId) {
        this.originalDetailId = originalDetailId;
    }

    public String getProductFamily() {
        return productFamily;
    }

    public void setProductFamily(String productFamily) {
        this.productFamily = productFamily;
    }

    public String getUsageBatchName() {
        return usageBatchName;
    }

    public void setUsageBatchName(String usageBatchName) {
        this.usageBatchName = usageBatchName;
    }

    public Integer getPeriodEndPeriod() {
        return periodEndPeriod;
    }

    public void setPeriodEndPeriod(Integer periodEndPeriod) {
        this.periodEndPeriod = periodEndPeriod;
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

    public Long getRhAccountNumberPrint() {
        return rhAccountNumberPrint;
    }

    public void setRhAccountNumberPrint(Long rhAccountNumberPrint) {
        this.rhAccountNumberPrint = rhAccountNumberPrint;
    }

    public String getRhNamePrint() {
        return rhNamePrint;
    }

    public void setRhNamePrint(String rhNamePrint) {
        this.rhNamePrint = rhNamePrint;
    }

    public Long getRhAccountNumberDigital() {
        return rhAccountNumberDigital;
    }

    public void setRhAccountNumberDigital(Long rhAccountNumberDigital) {
        this.rhAccountNumberDigital = rhAccountNumberDigital;
    }

    public String getRhNameDigital() {
        return rhNameDigital;
    }

    public void setRhNameDigital(String rhNameDigital) {
        this.rhNameDigital = rhNameDigital;
    }

    public Integer getUsagePeriod() {
        return usagePeriod;
    }

    public void setUsagePeriod(Integer usagePeriod) {
        this.usagePeriod = usagePeriod;
    }

    public BigDecimal getUsageAgeWeight() {
        return usageAgeWeight;
    }

    public void setUsageAgeWeight(BigDecimal usageAgeWeight) {
        this.usageAgeWeight = usageAgeWeight;
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

    public String getReportedTypeOfUse() {
        return reportedTypeOfUse;
    }

    public void setReportedTypeOfUse(String reportedTypeOfUse) {
        this.reportedTypeOfUse = reportedTypeOfUse;
    }

    public BigDecimal getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(BigDecimal numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public BigDecimal getWeightedCopies() {
        return weightedCopies;
    }

    public void setWeightedCopies(BigDecimal weightedCopies) {
        this.weightedCopies = weightedCopies;
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

    public boolean isPriceFlag() {
        return priceFlag;
    }

    public void setPriceFlag(boolean priceFlag) {
        this.priceFlag = priceFlag;
    }

    public BigDecimal getContent() {
        return content;
    }

    public void setContent(BigDecimal content) {
        this.content = content;
    }

    public boolean isContentFlag() {
        return contentFlag;
    }

    public void setContentFlag(boolean contentFlag) {
        this.contentFlag = contentFlag;
    }

    public BigDecimal getContentUnitPrice() {
        return contentUnitPrice;
    }

    public void setContentUnitPrice(BigDecimal contentUnitPrice) {
        this.contentUnitPrice = contentUnitPrice;
    }

    public boolean isContentUnitPriceFlag() {
        return contentUnitPriceFlag;
    }

    public void setContentUnitPriceFlag(boolean contentUnitPriceFlag) {
        this.contentUnitPriceFlag = contentUnitPriceFlag;
    }

    public BigDecimal getValueSharePrint() {
        return valueSharePrint;
    }

    public void setValueSharePrint(BigDecimal valueSharePrint) {
        this.valueSharePrint = valueSharePrint;
    }

    public BigDecimal getVolumeSharePrint() {
        return volumeSharePrint;
    }

    public void setVolumeSharePrint(BigDecimal volumeSharePrint) {
        this.volumeSharePrint = volumeSharePrint;
    }

    public BigDecimal getDetailSharePrint() {
        return detailSharePrint;
    }

    public void setDetailSharePrint(BigDecimal detailSharePrint) {
        this.detailSharePrint = detailSharePrint;
    }

    public BigDecimal getNetAmountPrint() {
        return netAmountPrint;
    }

    public void setNetAmountPrint(BigDecimal netAmountPrint) {
        this.netAmountPrint = netAmountPrint;
    }

    public BigDecimal getValueShareDigital() {
        return valueShareDigital;
    }

    public void setValueShareDigital(BigDecimal valueShareDigital) {
        this.valueShareDigital = valueShareDigital;
    }

    public BigDecimal getVolumeShareDigital() {
        return volumeShareDigital;
    }

    public void setVolumeShareDigital(BigDecimal volumeShareDigital) {
        this.volumeShareDigital = volumeShareDigital;
    }

    public BigDecimal getDetailShareDigital() {
        return detailShareDigital;
    }

    public void setDetailShareDigital(BigDecimal detailShareDigital) {
        this.detailShareDigital = detailShareDigital;
    }

    public BigDecimal getNetAmountDigital() {
        return netAmountDigital;
    }

    public void setNetAmountDigital(BigDecimal netAmountDigital) {
        this.netAmountDigital = netAmountDigital;
    }

    public BigDecimal getCombinedNetAmount() {
        return combinedNetAmount;
    }

    public void setCombinedNetAmount(BigDecimal combinedNetAmount) {
        this.combinedNetAmount = combinedNetAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (null == o || getClass() != o.getClass()) {
            return false;
        }
        AclScenarioDetailDto that = (AclScenarioDetailDto) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(priceFlag, that.priceFlag)
            .append(contentFlag, that.contentFlag)
            .append(contentUnitPriceFlag, that.contentUnitPriceFlag)
            .append(originalDetailId, that.originalDetailId)
            .append(productFamily, that.productFamily)
            .append(usageBatchName, that.usageBatchName)
            .append(periodEndPeriod, that.periodEndPeriod)
            .append(wrWrkInst, that.wrWrkInst)
            .append(systemTitle, that.systemTitle)
            .append(rhAccountNumberPrint, that.rhAccountNumberPrint)
            .append(rhNamePrint, that.rhNamePrint)
            .append(rhAccountNumberDigital, that.rhAccountNumberDigital)
            .append(rhNameDigital, that.rhNameDigital)
            .append(usagePeriod, that.usagePeriod)
            .append(usageAgeWeight, that.usageAgeWeight)
            .append(detailLicenseeClassId, that.detailLicenseeClassId)
            .append(detailLicenseeClassName, that.detailLicenseeClassName)
            .append(aggregateLicenseeClassId, that.aggregateLicenseeClassId)
            .append(aggregateLicenseeClassName, that.aggregateLicenseeClassName)
            .append(surveyCountry, that.surveyCountry)
            .append(reportedTypeOfUse, that.reportedTypeOfUse)
            .append(numberOfCopies, that.numberOfCopies)
            .append(weightedCopies, that.weightedCopies)
            .append(publicationType, that.publicationType)
            .append(price, that.price)
            .append(content, that.content)
            .append(contentUnitPrice, that.contentUnitPrice)
            .append(valueSharePrint, that.valueSharePrint)
            .append(volumeSharePrint, that.volumeSharePrint)
            .append(detailSharePrint, that.detailSharePrint)
            .append(netAmountPrint, that.netAmountPrint)
            .append(valueShareDigital, that.valueShareDigital)
            .append(volumeShareDigital, that.volumeShareDigital)
            .append(detailShareDigital, that.detailShareDigital)
            .append(netAmountDigital, that.netAmountDigital)
            .append(combinedNetAmount, that.combinedNetAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(originalDetailId)
            .append(productFamily)
            .append(usageBatchName)
            .append(periodEndPeriod)
            .append(wrWrkInst)
            .append(systemTitle)
            .append(rhAccountNumberPrint)
            .append(rhNamePrint)
            .append(rhAccountNumberDigital)
            .append(rhNameDigital)
            .append(usagePeriod)
            .append(usageAgeWeight)
            .append(detailLicenseeClassId)
            .append(detailLicenseeClassName)
            .append(aggregateLicenseeClassId)
            .append(aggregateLicenseeClassName)
            .append(surveyCountry)
            .append(reportedTypeOfUse)
            .append(numberOfCopies)
            .append(weightedCopies)
            .append(publicationType)
            .append(price)
            .append(priceFlag)
            .append(content)
            .append(contentFlag)
            .append(contentUnitPrice)
            .append(contentUnitPriceFlag)
            .append(valueSharePrint)
            .append(volumeSharePrint)
            .append(detailSharePrint)
            .append(netAmountPrint)
            .append(valueShareDigital)
            .append(volumeShareDigital)
            .append(detailShareDigital)
            .append(netAmountDigital)
            .append(combinedNetAmount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("originalDetailId", originalDetailId)
            .append("productFamily", productFamily)
            .append("usageBatchName", usageBatchName)
            .append("periodEndPeriod", periodEndPeriod)
            .append("wrWrkInst", wrWrkInst)
            .append("systemTitle", systemTitle)
            .append("rhAccountNumberPrint", rhAccountNumberPrint)
            .append("rhNamePrint", rhNamePrint)
            .append("rhAccountNumberDigital", rhAccountNumberDigital)
            .append("rhNameDigital", rhNameDigital)
            .append("usagePeriod", usagePeriod)
            .append("usageAgeWeight", usageAgeWeight)
            .append("detailLicenseeClassId", detailLicenseeClassId)
            .append("detailLicenseeClassName", detailLicenseeClassName)
            .append("aggregateLicenseeClassId", aggregateLicenseeClassId)
            .append("aggregateLicenseeClassName", aggregateLicenseeClassName)
            .append("surveyCountry", surveyCountry)
            .append("reportedTypeOfUse", reportedTypeOfUse)
            .append("numberOfCopies", numberOfCopies)
            .append("weightedCopies", weightedCopies)
            .append("publicationType", publicationType)
            .append("price", price)
            .append("priceFlag", priceFlag)
            .append("content", content)
            .append("contentFlag", contentFlag)
            .append("contentUnitPrice", contentUnitPrice)
            .append("contentUnitPriceFlag", contentUnitPriceFlag)
            .append("valueSharePrint", valueSharePrint)
            .append("volumeSharePrint", volumeSharePrint)
            .append("detailSharePrint", detailSharePrint)
            .append("netAmountPrint", netAmountPrint)
            .append("valueShareDigital", valueShareDigital)
            .append("volumeShareDigital", volumeShareDigital)
            .append("detailShareDigital", detailShareDigital)
            .append("netAmountDigital", netAmountDigital)
            .append("combinedNetAmount", combinedNetAmount)
            .toString();
    }
}
