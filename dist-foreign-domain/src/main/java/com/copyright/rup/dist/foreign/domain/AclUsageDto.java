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
    private DetailLicenseeClass detailLicenseeClass;
    private Integer aggregateLicenseeClassId;
    private String aggregateLicenseeClassName;
    private String surveyCountry;
    private PublicationType publicationType;
    private BigDecimal price;
    private Boolean priceFlag;
    private BigDecimal content;
    private Boolean contentFlag;
    private BigDecimal contentUnitPrice;
    private Boolean contentUnitPriceFlag;
    private String reportedTypeOfUse;
    private String typeOfUse;
    private BigDecimal annualizedCopies;
    private Long quantity;
    private boolean editable;

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

    public String getSurveyCountry() {
        return surveyCountry;
    }

    public void setSurveyCountry(String surveyCountry) {
        this.surveyCountry = surveyCountry;
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

    public BigDecimal getAnnualizedCopies() {
        return annualizedCopies;
    }

    public void setAnnualizedCopies(BigDecimal annualizedCopies) {
        this.annualizedCopies = annualizedCopies;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
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
            .append(detailLicenseeClass, that.detailLicenseeClass)
            .append(aggregateLicenseeClassId, that.aggregateLicenseeClassId)
            .append(aggregateLicenseeClassName, that.aggregateLicenseeClassName)
            .append(surveyCountry, that.surveyCountry)
            .append(publicationType, that.publicationType)
            .append(price, that.price)
            .append(priceFlag, that.priceFlag)
            .append(content, that.content)
            .append(contentFlag, that.contentFlag)
            .append(contentUnitPrice, that.contentUnitPrice)
            .append(contentUnitPriceFlag, that.contentUnitPriceFlag)
            .append(reportedTypeOfUse, that.reportedTypeOfUse)
            .append(typeOfUse, that.typeOfUse)
            .append(annualizedCopies, that.annualizedCopies)
            .append(quantity, that.quantity)
            .append(editable, that.editable)
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
            .append(detailLicenseeClass)
            .append(aggregateLicenseeClassId)
            .append(aggregateLicenseeClassName)
            .append(surveyCountry)
            .append(publicationType)
            .append(price)
            .append(priceFlag)
            .append(content)
            .append(contentFlag)
            .append(contentUnitPrice)
            .append(contentUnitPriceFlag)
            .append(reportedTypeOfUse)
            .append(typeOfUse)
            .append(annualizedCopies)
            .append(quantity)
            .append(editable)
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
            .append("detailLicenseeClass", detailLicenseeClass)
            .append("aggregateLicenseeClassId", aggregateLicenseeClassId)
            .append("aggregateLicenseeClassName", aggregateLicenseeClassName)
            .append("surveyCountry", surveyCountry)
            .append("publicationType", publicationType)
            .append("price", price)
            .append("priceFlag", priceFlag)
            .append("content", content)
            .append("contentFlag", contentFlag)
            .append("contentUnitPrice", contentUnitPrice)
            .append("contentUnitPriceFlag", contentUnitPriceFlag)
            .append("reportedTypeOfUse", reportedTypeOfUse)
            .append("typeOfUse", typeOfUse)
            .append("annualizedCopies", annualizedCopies)
            .append("quantity", quantity)
            .append("editable", editable)
            .toString();
    }
}
