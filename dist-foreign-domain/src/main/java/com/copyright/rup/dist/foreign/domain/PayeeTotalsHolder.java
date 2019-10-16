package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.Rightsholder;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Contains information about payee and totals.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/16/19
 *
 * @author Uladzislau Shalamitski
 */
public class PayeeTotalsHolder {

    private Rightsholder payee = new Rightsholder();
    private BigDecimal grossTotal;
    private BigDecimal serviceFeeTotal;
    private BigDecimal netTotal;
    private BigDecimal serviceFee;
    private boolean payeeParticipating;

    public Rightsholder getPayee() {
        return payee;
    }

    public void setPayee(Rightsholder payee) {
        this.payee = payee;
    }

    public BigDecimal getGrossTotal() {
        return grossTotal;
    }

    public void setGrossTotal(BigDecimal grossTotal) {
        this.grossTotal = grossTotal;
    }

    public BigDecimal getServiceFeeTotal() {
        return serviceFeeTotal;
    }

    public void setServiceFeeTotal(BigDecimal serviceFeeTotal) {
        this.serviceFeeTotal = serviceFeeTotal;
    }

    public BigDecimal getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(BigDecimal netTotal) {
        this.netTotal = netTotal;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public boolean isPayeeParticipating() {
        return payeeParticipating;
    }

    public void setPayeeParticipating(boolean payeeParticipating) {
        this.payeeParticipating = payeeParticipating;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        PayeeTotalsHolder that = (PayeeTotalsHolder) obj;
        return new EqualsBuilder()
            .append(payee, that.payee)
            .append(grossTotal, that.grossTotal)
            .append(serviceFeeTotal, that.serviceFeeTotal)
            .append(netTotal, that.netTotal)
            .append(serviceFee, that.serviceFee)
            .append(payeeParticipating, that.payeeParticipating)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(payee)
            .append(grossTotal)
            .append(serviceFeeTotal)
            .append(netTotal)
            .append(serviceFee)
            .append(payeeParticipating)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("payee", payee)
            .append("grossTotal", grossTotal)
            .append("serviceFeeTotal", serviceFeeTotal)
            .append("netTotal", netTotal)
            .append("serviceFee", serviceFee)
            .append("payeeParticipating", payeeParticipating)
            .toString();
    }
}
