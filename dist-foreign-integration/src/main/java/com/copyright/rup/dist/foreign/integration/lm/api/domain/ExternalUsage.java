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

    @JsonProperty(value = "rh_account_number")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long rhAccountNumber;

    @JsonProperty(value = "product_family")
    private String productFamily;

    @JsonProperty(value = "detail_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long detailId;

    @JsonProperty(value = "royalty_amount")
    private BigDecimal royaltyAmount = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);

    @JsonProperty(value = "wr_wrk_inst")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long wrWrkInst;

    @JsonProperty(value = "work_title")
    private String workTitle;

    /**
     * Constructs new external usage based on {@link Usage}.
     *
     * @param usage base usage
     */
    public ExternalUsage(Usage usage) {
        this.rhAccountNumber = usage.getRightsholder().getAccountNumber();
        this.productFamily = usage.getProductFamily();
        this.detailId = usage.getDetailId();
        this.workTitle = usage.getWorkTitle();
        this.royaltyAmount = usage.getNetAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
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

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public BigDecimal getRoyaltyAmount() {
        return royaltyAmount;
    }

    public void setRoyaltyAmount(BigDecimal royaltyAmount) {
        this.royaltyAmount = royaltyAmount;
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
            .append(detailId, that.detailId)
            .append(royaltyAmount, that.royaltyAmount)
            .append(wrWrkInst, that.wrWrkInst)
            .append(workTitle, that.workTitle)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(rhAccountNumber)
            .append(productFamily)
            .append(detailId)
            .append(royaltyAmount)
            .append(wrWrkInst)
            .append(workTitle)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("rhAccountNumber", rhAccountNumber)
            .append("productFamily", productFamily)
            .append("detailId", detailId)
            .append("royaltyAmount", royaltyAmount)
            .append("wrWrkInst", wrWrkInst)
            .append("workTitle", workTitle)
            .toString();
    }
}
