package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.Rightsholder;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents domain to hold group of usages.
 * Contains information about rightsholder, payee and totals.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/31/17
 *
 * @author Ihar Suvorau
 */
public class RightsholderTotalsHolder {

    private Rightsholder rightsholder = new Rightsholder();
    private Rightsholder payee = new Rightsholder();
    private BigDecimal grossTotal = BigDecimal.ZERO.setScale(10, RoundingMode.HALF_UP);
    private BigDecimal serviceFeeTotal = BigDecimal.ZERO.setScale(10, RoundingMode.HALF_UP);
    private BigDecimal netTotal = BigDecimal.ZERO.setScale(10, RoundingMode.HALF_UP);
    private BigDecimal serviceFee;

    public Rightsholder getRightsholder() {
        return rightsholder;
    }

    public void setRightsholder(Rightsholder rightsholder) {
        this.rightsholder = rightsholder;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        RightsholderTotalsHolder that = (RightsholderTotalsHolder) obj;
        return new EqualsBuilder()
            .append(this.rightsholder, that.rightsholder)
            .append(this.payee, that.payee)
            .append(this.grossTotal, that.grossTotal)
            .append(this.serviceFeeTotal, that.serviceFeeTotal)
            .append(this.netTotal, that.netTotal)
            .append(this.serviceFee, that.serviceFee)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(rightsholder)
            .append(payee)
            .append(grossTotal)
            .append(serviceFeeTotal)
            .append(netTotal)
            .append(serviceFee)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("rightsholder", rightsholder)
            .append("payee", payee)
            .append("grossTotal", grossTotal)
            .append("serviceFeeTotal", serviceFeeTotal)
            .append("netTotal", netTotal)
            .append("serviceFee", serviceFee)
            .toString();
    }
}
