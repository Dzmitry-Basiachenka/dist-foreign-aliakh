package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.common.domain.StoredEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents service fee true-up report record.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 7/11/18
 *
 * @author Uladzislau Shalamitski
 */
public class ServiceFeeTrueUpReportDto extends StoredEntity<String> {

    private static final BigDecimal DEFAULT_AMOUNT = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);

    private Long rroAccountNumber;
    private String rroName;
    private LocalDate paymentDate;
    private BigDecimal grossAmountSentToLm = DEFAULT_AMOUNT;
    private BigDecimal serviceFeeAmountSentToLm = DEFAULT_AMOUNT;
    private BigDecimal serviceFeeSentToLm = DEFAULT_AMOUNT;
    private BigDecimal netAmountSentToLm = DEFAULT_AMOUNT;
    private BigDecimal estimatedServiceFeeAmountSentToLm = DEFAULT_AMOUNT;
    private BigDecimal estimatedServiceFee = DEFAULT_AMOUNT;
    private BigDecimal serviceFeeTrueUp = DEFAULT_AMOUNT;
    private BigDecimal grossAmountReturnToCla = DEFAULT_AMOUNT;
    private BigDecimal estimatedServiceFeeAmountReturnToCla = DEFAULT_AMOUNT;
    private BigDecimal serviceFeeAmountReturnToCla = DEFAULT_AMOUNT;
    private BigDecimal netAmountReturnToCla = DEFAULT_AMOUNT;
    private BigDecimal serviceFeeTrueUpReturnToCla = DEFAULT_AMOUNT;

    public Long getRroAccountNumber() {
        return rroAccountNumber;
    }

    public void setRroAccountNumber(Long rroAccountNumber) {
        this.rroAccountNumber = rroAccountNumber;
    }

    public String getRroName() {
        return rroName;
    }

    public void setRroName(String rroName) {
        this.rroName = rroName;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
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

    public BigDecimal getServiceFeeSentToLm() {
        return serviceFeeSentToLm;
    }

    public void setServiceFeeSentToLm(BigDecimal serviceFeeSentToLm) {
        this.serviceFeeSentToLm = serviceFeeSentToLm;
    }

    public BigDecimal getNetAmountSentToLm() {
        return netAmountSentToLm;
    }

    public void setNetAmountSentToLm(BigDecimal netAmountSentToLm) {
        this.netAmountSentToLm = netAmountSentToLm;
    }

    public BigDecimal getEstimatedServiceFeeAmountSentToLm() {
        return estimatedServiceFeeAmountSentToLm;
    }

    public void setEstimatedServiceFeeAmountSentToLm(BigDecimal estimatedServiceFeeAmountSentToLm) {
        this.estimatedServiceFeeAmountSentToLm = estimatedServiceFeeAmountSentToLm;
    }

    public BigDecimal getEstimatedServiceFee() {
        return estimatedServiceFee;
    }

    public void setEstimatedServiceFee(BigDecimal estimatedServiceFee) {
        this.estimatedServiceFee = estimatedServiceFee;
    }

    public BigDecimal getServiceFeeTrueUp() {
        return serviceFeeTrueUp;
    }

    public void setServiceFeeTrueUp(BigDecimal serviceFeeTrueUp) {
        this.serviceFeeTrueUp = serviceFeeTrueUp;
    }

    public BigDecimal getGrossAmountReturnToCla() {
        return grossAmountReturnToCla;
    }

    public void setGrossAmountReturnToCla(BigDecimal grossAmountReturnToCla) {
        this.grossAmountReturnToCla = grossAmountReturnToCla;
    }

    public BigDecimal getEstimatedServiceFeeAmountReturnToCla() {
        return estimatedServiceFeeAmountReturnToCla;
    }

    public void setEstimatedServiceFeeAmountReturnToCla(BigDecimal estimatedServiceFeeAmountReturnToCla) {
        this.estimatedServiceFeeAmountReturnToCla = estimatedServiceFeeAmountReturnToCla;
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

    public BigDecimal getServiceFeeTrueUpReturnToCla() {
        return serviceFeeTrueUpReturnToCla;
    }

    public void setServiceFeeTrueUpReturnToCla(BigDecimal serviceFeeTrueUpReturnToCla) {
        this.serviceFeeTrueUpReturnToCla = serviceFeeTrueUpReturnToCla;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ServiceFeeTrueUpReportDto that = (ServiceFeeTrueUpReportDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(rroAccountNumber, that.rroAccountNumber)
            .append(rroName, that.rroName)
            .append(paymentDate, that.paymentDate)
            .append(grossAmountSentToLm, that.grossAmountSentToLm)
            .append(serviceFeeAmountSentToLm, that.serviceFeeAmountSentToLm)
            .append(serviceFeeSentToLm, that.serviceFeeSentToLm)
            .append(netAmountSentToLm, that.netAmountSentToLm)
            .append(estimatedServiceFeeAmountSentToLm, that.estimatedServiceFeeAmountSentToLm)
            .append(estimatedServiceFee, that.estimatedServiceFee)
            .append(serviceFeeTrueUp, that.serviceFeeTrueUp)
            .append(grossAmountReturnToCla, that.grossAmountReturnToCla)
            .append(estimatedServiceFeeAmountReturnToCla, that.estimatedServiceFeeAmountReturnToCla)
            .append(serviceFeeAmountReturnToCla, that.serviceFeeAmountReturnToCla)
            .append(netAmountReturnToCla, that.netAmountReturnToCla)
            .append(serviceFeeTrueUpReturnToCla, that.serviceFeeTrueUpReturnToCla)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(rroAccountNumber)
            .append(rroName)
            .append(paymentDate)
            .append(grossAmountSentToLm)
            .append(serviceFeeAmountSentToLm)
            .append(serviceFeeSentToLm)
            .append(netAmountSentToLm)
            .append(estimatedServiceFeeAmountSentToLm)
            .append(estimatedServiceFee)
            .append(serviceFeeTrueUp)
            .append(grossAmountReturnToCla)
            .append(estimatedServiceFeeAmountReturnToCla)
            .append(serviceFeeAmountReturnToCla)
            .append(netAmountReturnToCla)
            .append(serviceFeeTrueUpReturnToCla)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("rroAccountNumber", rroAccountNumber)
            .append("rroName", rroName)
            .append("paymentDate", paymentDate)
            .append("grossAmountSentToLm", grossAmountSentToLm)
            .append("serviceFeeAmountSentToLm", serviceFeeAmountSentToLm)
            .append("serviceFeeSentToLm", serviceFeeSentToLm)
            .append("netAmountSentToLm", netAmountSentToLm)
            .append("estimatedServiceFeeAmountSentToLm", estimatedServiceFeeAmountSentToLm)
            .append("estimatedServiceFee", estimatedServiceFee)
            .append("serviceFeeTrueUp", serviceFeeTrueUp)
            .append("grossAmountReturnToCla", grossAmountReturnToCla)
            .append("estimatedServiceFeeAmountReturnToCla", estimatedServiceFeeAmountReturnToCla)
            .append("serviceFeeAmountReturnToCla", serviceFeeAmountReturnToCla)
            .append("netAmountReturnToCla", netAmountReturnToCla)
            .append("serviceFeeTrueUpReturnToCla", serviceFeeTrueUpReturnToCla)
            .toString();
    }
}
