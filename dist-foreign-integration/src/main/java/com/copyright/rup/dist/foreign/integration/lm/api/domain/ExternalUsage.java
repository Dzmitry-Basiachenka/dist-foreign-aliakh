package com.copyright.rup.dist.foreign.integration.lm.api.domain;

import com.copyright.rup.dist.foreign.domain.Usage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

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

    @JsonProperty("rh_account_number")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long rhAccountNumber;

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

    /**
     * Constructs new external usage based on {@link Usage}.
     *
     * @param usage base usage
     */
    public ExternalUsage(Usage usage) {
        this.rhAccountNumber = usage.getRightsholder().getAccountNumber();
        this.productFamily = usage.getProductFamily();
        this.usageId = usage.getId();
        this.workTitle = usage.getSystemTitle();
        this.royaltyAmount = usage.getNetAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
        this.serviceFeeAmount = usage.getServiceFeeAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
        this.collectedAmount = usage.getGrossAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
        this.wrWrkInst = usage.getWrWrkInst();
    }

    public Long getRhAccountNumber() {
        return rhAccountNumber;
    }

    public void setRhAccountNumber(Long rhAccountNumber) {
        this.rhAccountNumber = rhAccountNumber;
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
            .append(rhAccountNumber, that.rhAccountNumber)
            .append(productFamily, that.productFamily)
            .append(usageId, that.usageId)
            .append(royaltyAmount, that.royaltyAmount)
            .append(serviceFeeAmount, that.serviceFeeAmount)
            .append(collectedAmount, that.collectedAmount)
            .append(wrWrkInst, that.wrWrkInst)
            .append(workTitle, that.workTitle)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(rhAccountNumber)
            .append(productFamily)
            .append(usageId)
            .append(royaltyAmount)
            .append(serviceFeeAmount)
            .append(collectedAmount)
            .append(wrWrkInst)
            .append(workTitle)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("rhAccountNumber", rhAccountNumber)
            .append("productFamily", productFamily)
            .append("usageId", usageId)
            .append("royaltyAmount", royaltyAmount)
            .append("serviceFeeAmount", serviceFeeAmount)
            .append("collectedAmount", collectedAmount)
            .append("wrWrkInst", wrWrkInst)
            .append("workTitle", workTitle)
            .toString();
    }
}
