package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents NTS service fee true-up report record.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 7/28/20
 *
 * @author Stanislau Rudak
 */
public class NtsServiceFeeTrueUpReportDto extends StoredEntity<String> {

    private static final BigDecimal DEFAULT_AMOUNT = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);

    private BigDecimal batchGrossAmount = DEFAULT_AMOUNT;
    private BigDecimal preServiceFeeAmount = DEFAULT_AMOUNT;
    private BigDecimal preServiceFeeFunds = DEFAULT_AMOUNT;
    private BigDecimal postServiceFeeAmount = DEFAULT_AMOUNT;
    private BigDecimal grossAmount = DEFAULT_AMOUNT;
    private BigDecimal estimatedServiceFee = DEFAULT_AMOUNT;
    private BigDecimal estimatedServiceFeeAmount = DEFAULT_AMOUNT;
    private BigDecimal estimatedNetAmount = DEFAULT_AMOUNT;
    private BigDecimal grossAmountSentToLm = DEFAULT_AMOUNT;
    private BigDecimal serviceFeeSentToLm = DEFAULT_AMOUNT;
    private BigDecimal serviceFeeAmountSentToLm = DEFAULT_AMOUNT;
    private BigDecimal netAmountSentToLm = DEFAULT_AMOUNT;
    private BigDecimal serviceFeeTrueUpAmount = DEFAULT_AMOUNT;

    public BigDecimal getBatchGrossAmount() {
        return batchGrossAmount;
    }

    public void setBatchGrossAmount(BigDecimal batchGrossAmount) {
        this.batchGrossAmount = batchGrossAmount;
    }

    public BigDecimal getPreServiceFeeAmount() {
        return preServiceFeeAmount;
    }

    public void setPreServiceFeeAmount(BigDecimal preServiceFeeAmount) {
        this.preServiceFeeAmount = preServiceFeeAmount;
    }

    public BigDecimal getPreServiceFeeFunds() {
        return preServiceFeeFunds;
    }

    public void setPreServiceFeeFunds(BigDecimal preServiceFeeFunds) {
        this.preServiceFeeFunds = preServiceFeeFunds;
    }

    public BigDecimal getPostServiceFeeAmount() {
        return postServiceFeeAmount;
    }

    public void setPostServiceFeeAmount(BigDecimal postServiceFeeAmount) {
        this.postServiceFeeAmount = postServiceFeeAmount;
    }

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    public BigDecimal getEstimatedServiceFee() {
        return estimatedServiceFee;
    }

    public void setEstimatedServiceFee(BigDecimal estimatedServiceFee) {
        this.estimatedServiceFee = estimatedServiceFee;
    }

    public BigDecimal getEstimatedServiceFeeAmount() {
        return estimatedServiceFeeAmount;
    }

    public void setEstimatedServiceFeeAmount(BigDecimal estimatedServiceFeeAmount) {
        this.estimatedServiceFeeAmount = estimatedServiceFeeAmount;
    }

    public BigDecimal getEstimatedNetAmount() {
        return estimatedNetAmount;
    }

    public void setEstimatedNetAmount(BigDecimal estimatedNetAmount) {
        this.estimatedNetAmount = estimatedNetAmount;
    }

    public BigDecimal getGrossAmountSentToLm() {
        return grossAmountSentToLm;
    }

    public void setGrossAmountSentToLm(BigDecimal grossAmountSentToLm) {
        this.grossAmountSentToLm = grossAmountSentToLm;
    }

    public BigDecimal getServiceFeeSentToLm() {
        return serviceFeeSentToLm;
    }

    public void setServiceFeeSentToLm(BigDecimal serviceFeeSentToLm) {
        this.serviceFeeSentToLm = serviceFeeSentToLm;
    }

    public BigDecimal getServiceFeeAmountSentToLm() {
        return serviceFeeAmountSentToLm;
    }

    public void setServiceFeeAmountSentToLm(BigDecimal serviceFeeAmountSentToLm) {
        this.serviceFeeAmountSentToLm = serviceFeeAmountSentToLm;
    }

    public BigDecimal getNetAmountSentToLm() {
        return netAmountSentToLm;
    }

    public void setNetAmountSentToLm(BigDecimal netAmountSentToLm) {
        this.netAmountSentToLm = netAmountSentToLm;
    }

    public BigDecimal getServiceFeeTrueUpAmount() {
        return serviceFeeTrueUpAmount;
    }

    public void setServiceFeeTrueUpAmount(BigDecimal serviceFeeTrueUpAmount) {
        this.serviceFeeTrueUpAmount = serviceFeeTrueUpAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (null == o || getClass() != o.getClass()) {
            return false;
        }
        NtsServiceFeeTrueUpReportDto that = (NtsServiceFeeTrueUpReportDto) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(batchGrossAmount, that.batchGrossAmount)
            .append(preServiceFeeAmount, that.preServiceFeeAmount)
            .append(preServiceFeeFunds, that.preServiceFeeFunds)
            .append(postServiceFeeAmount, that.postServiceFeeAmount)
            .append(grossAmount, that.grossAmount)
            .append(estimatedServiceFee, that.estimatedServiceFee)
            .append(estimatedServiceFeeAmount, that.estimatedServiceFeeAmount)
            .append(estimatedNetAmount, that.estimatedNetAmount)
            .append(grossAmountSentToLm, that.grossAmountSentToLm)
            .append(serviceFeeSentToLm, that.serviceFeeSentToLm)
            .append(serviceFeeAmountSentToLm, that.serviceFeeAmountSentToLm)
            .append(netAmountSentToLm, that.netAmountSentToLm)
            .append(serviceFeeTrueUpAmount, that.serviceFeeTrueUpAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(batchGrossAmount)
            .append(preServiceFeeAmount)
            .append(preServiceFeeFunds)
            .append(postServiceFeeAmount)
            .append(grossAmount)
            .append(estimatedServiceFee)
            .append(estimatedServiceFeeAmount)
            .append(estimatedNetAmount)
            .append(grossAmountSentToLm)
            .append(serviceFeeSentToLm)
            .append(serviceFeeAmountSentToLm)
            .append(netAmountSentToLm)
            .append(serviceFeeTrueUpAmount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("batchGrossAmount", batchGrossAmount)
            .append("preServiceFeeAmount", preServiceFeeAmount)
            .append("preServiceFeeFunds", preServiceFeeFunds)
            .append("postServiceFeeAmount", postServiceFeeAmount)
            .append("grossAmount", grossAmount)
            .append("estimatedServiceFee", estimatedServiceFee)
            .append("estimatedServiceFeeAmount", estimatedServiceFeeAmount)
            .append("estimatedNetAmount", estimatedNetAmount)
            .append("grossAmountSentToLm", grossAmountSentToLm)
            .append("serviceFeeSentToLm", serviceFeeSentToLm)
            .append("serviceFeeAmountSentToLm", serviceFeeAmountSentToLm)
            .append("netAmountSentToLm", netAmountSentToLm)
            .append("serviceFeeTrueUpAmount", serviceFeeTrueUpAmount)
            .toString();
    }
}
