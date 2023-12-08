package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.domain.StoredEntity;

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
public class PayeeTotalHolder extends StoredEntity<String> {

    private static final long serialVersionUID = 8324932040358394428L;

    private Rightsholder payee = new Rightsholder();
    private BigDecimal grossTotal;
    private BigDecimal serviceFeeTotal;
    private BigDecimal netTotal;
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
        PayeeTotalHolder that = (PayeeTotalHolder) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(payee, that.payee)
            .append(grossTotal, that.grossTotal)
            .append(serviceFeeTotal, that.serviceFeeTotal)
            .append(netTotal, that.netTotal)
            .append(payeeParticipating, that.payeeParticipating)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(payee)
            .append(grossTotal)
            .append(serviceFeeTotal)
            .append(netTotal)
            .append(payeeParticipating)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("payee", payee)
            .append("grossTotal", grossTotal)
            .append("serviceFeeTotal", serviceFeeTotal)
            .append("netTotal", netTotal)
            .append("payeeParticipating", payeeParticipating)
            .toString();
    }
}
