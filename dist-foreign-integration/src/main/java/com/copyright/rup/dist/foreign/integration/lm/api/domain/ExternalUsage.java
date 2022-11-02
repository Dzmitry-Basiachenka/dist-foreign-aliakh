package com.copyright.rup.dist.foreign.integration.lm.api.domain;

import com.copyright.rup.dist.foreign.domain.AclScenarioLiabilityDetail;
import com.copyright.rup.dist.foreign.domain.Usage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents usage data object for external systems.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/09/18
 *
 * @author Ihar Suvorau
 */
@JsonInclude(Include.NON_NULL)
public class ExternalUsage {

    @JsonProperty("rh_uid")
    private String rhId;

    @JsonProperty("product_family")
    private String productFamily;

    @JsonProperty("detail_id")
    private String usageId;

    @JsonProperty("royalty_amount")
    private BigDecimal royaltyAmount;

    @JsonProperty("service_fee_amount")
    private BigDecimal serviceFeeAmount;

    @JsonProperty("collected_amount")
    private BigDecimal collectedAmount;

    @JsonProperty("wr_wrk_inst")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long wrWrkInst;

    @JsonProperty("work_title")
    private String workTitle;

    @JsonProperty("aggregate_licensee_class_description")
    private String aggregateLicenseeClassName;

    @JsonProperty("license_type")
    private String licenseType;

    @JsonProperty("type_of_use")
    private String typeOfUse;

    /**
     * Constructs new external usage based on {@link Usage}.
     *
     * @param usage base usage
     */
    public ExternalUsage(Usage usage) {
        this.rhId = usage.getRightsholder().getId();
        this.productFamily = usage.getProductFamily();
        this.usageId = usage.getId();
        this.workTitle = usage.getSystemTitle();
        this.royaltyAmount = usage.getNetAmount().setScale(2, RoundingMode.HALF_UP);
        this.serviceFeeAmount = usage.getServiceFeeAmount().setScale(2, RoundingMode.HALF_UP);
        this.collectedAmount = usage.getGrossAmount().setScale(2, RoundingMode.HALF_UP);
        this.wrWrkInst = usage.getWrWrkInst();
    }

    /**
     * Constructs new external usage based on {@link AclScenarioLiabilityDetail}.
     *
     * @param liabilityDetail base scenario share detail
     */
    public ExternalUsage(AclScenarioLiabilityDetail liabilityDetail) {
        this.rhId = liabilityDetail.getRightsholderId();
        this.productFamily = liabilityDetail.getProductFamily();
        this.usageId = liabilityDetail.getLiabilityDetailId();
        this.wrWrkInst = liabilityDetail.getWrWrkInst();
        this.workTitle = liabilityDetail.getSystemTitle();
        this.royaltyAmount = liabilityDetail.getNetAmount().setScale(2, RoundingMode.HALF_UP);
        this.serviceFeeAmount = liabilityDetail.getServiceFeeAmount().setScale(2, RoundingMode.HALF_UP);
        this.collectedAmount = liabilityDetail.getGrossAmount().setScale(2, RoundingMode.HALF_UP);
        this.aggregateLicenseeClassName = liabilityDetail.getAggregateLicenseeClassName();
        this.licenseType = liabilityDetail.getLicenseType();
        this.typeOfUse = liabilityDetail.getTypeOfUse();
    }

    public String getRhId() {
        return rhId;
    }

    public void setRhId(String rhId) {
        this.rhId = rhId;
    }

    public String getProductFamily() {
        return productFamily;
    }

    public void setProductFamily(String productFamily) {
        this.productFamily = productFamily;
    }

    public String getUsageId() {
        return usageId;
    }

    public void setUsageId(String usageId) {
        this.usageId = usageId;
    }

    public BigDecimal getRoyaltyAmount() {
        return royaltyAmount;
    }

    public void setRoyaltyAmount(BigDecimal royaltyAmount) {
        this.royaltyAmount = royaltyAmount;
    }

    public BigDecimal getServiceFeeAmount() {
        return serviceFeeAmount;
    }

    public void setServiceFeeAmount(BigDecimal serviceFeeAmount) {
        this.serviceFeeAmount = serviceFeeAmount;
    }

    public BigDecimal getCollectedAmount() {
        return collectedAmount;
    }

    public void setCollectedAmount(BigDecimal collectedAmount) {
        this.collectedAmount = collectedAmount;
    }

    public Long getWrWrkInst() {
        return wrWrkInst;
    }

    public void setWrWrkInst(Long wrWrkInst) {
        this.wrWrkInst = wrWrkInst;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }

    public String getAggregateLicenseeClassName() {
        return aggregateLicenseeClassName;
    }

    public void setAggregateLicenseeClassName(String aggregateLicenseeClassName) {
        this.aggregateLicenseeClassName = aggregateLicenseeClassName;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getTypeOfUse() {
        return typeOfUse;
    }

    public void setTypeOfUse(String typeOfUse) {
        this.typeOfUse = typeOfUse;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        ExternalUsage that = (ExternalUsage) obj;
        return new EqualsBuilder()
            .append(rhId, that.rhId)
            .append(productFamily, that.productFamily)
            .append(usageId, that.usageId)
            .append(royaltyAmount, that.royaltyAmount)
            .append(serviceFeeAmount, that.serviceFeeAmount)
            .append(collectedAmount, that.collectedAmount)
            .append(wrWrkInst, that.wrWrkInst)
            .append(workTitle, that.workTitle)
            .append(aggregateLicenseeClassName, that.aggregateLicenseeClassName)
            .append(licenseType, that.licenseType)
            .append(typeOfUse, that.typeOfUse)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(rhId)
            .append(productFamily)
            .append(usageId)
            .append(royaltyAmount)
            .append(serviceFeeAmount)
            .append(collectedAmount)
            .append(wrWrkInst)
            .append(workTitle)
            .append(aggregateLicenseeClassName)
            .append(licenseType)
            .append(typeOfUse)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("rhId", rhId)
            .append("productFamily", productFamily)
            .append("usageId", usageId)
            .append("royaltyAmount", royaltyAmount)
            .append("serviceFeeAmount", serviceFeeAmount)
            .append("collectedAmount", collectedAmount)
            .append("wrWrkInst", wrWrkInst)
            .append("workTitle", workTitle)
            .append("aggregateLicenseeClassName", aggregateLicenseeClassName)
            .append("licenseType", licenseType)
            .append("typeOfUse", typeOfUse)
            .toString();
    }
}
