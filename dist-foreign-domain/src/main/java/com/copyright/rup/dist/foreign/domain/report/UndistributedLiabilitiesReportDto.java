package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents undistributed liabilities reconciliation report record.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 05/14/18
 *
 * @author Uladzislau Shalamitski
 */
public class UndistributedLiabilitiesReportDto extends StoredEntity<String> {

    private static final BigDecimal DEFAULT_AMOUNT = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);

    private String rroName;
    private Long rroAccountNumber;
    private LocalDate paymentDate;
    private BigDecimal grossAmount = DEFAULT_AMOUNT;
    private BigDecimal estimatedServiceFeeAmount = DEFAULT_AMOUNT;
    private BigDecimal estimatedNetAmount = DEFAULT_AMOUNT;
    private BigDecimal estimatedServiceFee = DEFAULT_AMOUNT;

    public String getRroName() {
        return rroName;
    }

    public void setRroName(String rroName) {
        this.rroName = rroName;
    }

    public Long getRroAccountNumber() {
        return rroAccountNumber;
    }

    public void setRroAccountNumber(Long rroAccountNumber) {
        this.rroAccountNumber = rroAccountNumber;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
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

    public BigDecimal getEstimatedServiceFee() {
        return estimatedServiceFee;
    }

    public void setEstimatedServiceFee(BigDecimal estimatedServiceFee) {
        this.estimatedServiceFee = estimatedServiceFee;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UndistributedLiabilitiesReportDto that = (UndistributedLiabilitiesReportDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(rroName, that.rroName)
            .append(rroAccountNumber, that.rroAccountNumber)
            .append(paymentDate, that.paymentDate)
            .append(grossAmount, that.grossAmount)
            .append(estimatedServiceFeeAmount, that.estimatedServiceFeeAmount)
            .append(estimatedNetAmount, that.estimatedNetAmount)
            .append(estimatedServiceFee, that.estimatedServiceFee)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(rroName)
            .append(rroAccountNumber)
            .append(paymentDate)
            .append(grossAmount)
            .append(estimatedServiceFeeAmount)
            .append(estimatedNetAmount)
            .append(estimatedServiceFee)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("rroName", rroName)
            .append("rroAccountNumber", rroAccountNumber)
            .append("paymentDate", paymentDate)
            .append("grossAmount", grossAmount)
            .append("estimatedServiceFeeAmount", estimatedServiceFeeAmount)
            .append("estimatedNetAmount", estimatedNetAmount)
            .append("estimatedServiceFee", estimatedServiceFee)
            .toString();
    }
}
