package com.copyright.rup.dist.foreign.domain;

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
    private BigDecimal grossAmountSentToLm = DEFAULT_AMOUNT;
    private BigDecimal serviceFeeAmountSentToLm = DEFAULT_AMOUNT;
    private BigDecimal netAmountSentToLm = DEFAULT_AMOUNT;
    private BigDecimal serviceFee = DEFAULT_AMOUNT;
    private BigDecimal serviceFeeTrueUp = DEFAULT_AMOUNT;
    private BigDecimal serviceFeeTrueUpReturnToCla = DEFAULT_AMOUNT;
    private BigDecimal grossAmountReturnToCla = DEFAULT_AMOUNT;
    private BigDecimal serviceFeeAmountReturnToCla = DEFAULT_AMOUNT;
    private BigDecimal netAmountReturnToCla = DEFAULT_AMOUNT;

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

    public BigDecimal getGrossAmountSentToLm() {
        return grossAmountSentToLm;
    }

    public void setGrossAmountSentToLm(BigDecimal grossAmountSentToLm) {
        this.grossAmountSentToLm = grossAmountSentToLm;
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

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public BigDecimal getServiceFeeTrueUp() {
        return serviceFeeTrueUp;
    }

    public void setServiceFeeTrueUp(BigDecimal serviceFeeTrueUp) {
        this.serviceFeeTrueUp = serviceFeeTrueUp;
    }

    public BigDecimal getServiceFeeTrueUpReturnToCla() {
        return serviceFeeTrueUpReturnToCla;
    }

    public void setServiceFeeTrueUpReturnToCla(BigDecimal serviceFeeTrueUpReturnToCla) {
        this.serviceFeeTrueUpReturnToCla = serviceFeeTrueUpReturnToCla;
    }

    public BigDecimal getGrossAmountReturnToCla() {
        return grossAmountReturnToCla;
    }

    public void setGrossAmountReturnToCla(BigDecimal grossAmountReturnToCla) {
        this.grossAmountReturnToCla = grossAmountReturnToCla;
    }

    public BigDecimal getServiceFeeAmountReturnToCla() {
        return serviceFeeAmountReturnToCla;
    }

    public void setServiceFeeAmountReturnToCla(BigDecimal serviceFeeAmountReturnToCla) {
        this.serviceFeeAmountReturnToCla = serviceFeeAmountReturnToCla;
    }

    public BigDecimal getNetAmountReturnToCla() {
        return netAmountReturnToCla;
    }

    public void setNetAmountReturnToCla(BigDecimal netAmountReturnToCla) {
        this.netAmountReturnToCla = netAmountReturnToCla;
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
            .append(grossAmountSentToLm, that.grossAmountSentToLm)
            .append(serviceFeeAmountSentToLm, that.serviceFeeAmountSentToLm)
            .append(netAmountSentToLm, that.netAmountSentToLm)
            .append(serviceFee, that.serviceFee)
            .append(serviceFeeTrueUp, that.serviceFeeTrueUp)
            .append(serviceFeeTrueUpReturnToCla, that.serviceFeeTrueUpReturnToCla)
            .append(grossAmountReturnToCla, that.grossAmountReturnToCla)
            .append(serviceFeeAmountReturnToCla, that.serviceFeeAmountReturnToCla)
            .append(netAmountReturnToCla, that.netAmountReturnToCla)
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
            .append(grossAmountSentToLm)
            .append(serviceFeeAmountSentToLm)
            .append(netAmountSentToLm)
            .append(serviceFee)
            .append(serviceFeeTrueUp)
            .append(serviceFeeTrueUpReturnToCla)
            .append(grossAmountReturnToCla)
            .append(serviceFeeAmountReturnToCla)
            .append(netAmountReturnToCla)
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
            .append("grossAmountSentToLm", grossAmountSentToLm)
            .append("serviceFeeAmountSentToLm", serviceFeeAmountSentToLm)
            .append("netAmountSentToLm", netAmountSentToLm)
            .append("serviceFee", serviceFee)
            .append("serviceFeeTrueUp", serviceFeeTrueUp)
            .append("serviceFeeTrueUpReturnToCla", serviceFeeTrueUpReturnToCla)
            .append("grossAmountReturnToCla", grossAmountReturnToCla)
            .append("serviceFeeAmountReturnToCla", serviceFeeAmountReturnToCla)
            .append("netAmountReturnToCla", netAmountReturnToCla)
            .toString();
    }
}
