package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents domain to hold on group of usages.
 * Contains information about rightsholder, payee and totals.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/31/17
 *
 * @author Ihar Suvorau
 */
public class RightsholderTotalsHolder {

    private String rightsholderName;
    private Long rightsholderAccountNumber;
    private String payeeName;
    private Long payeeAccountNumber;
    private BigDecimal grossTotal = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    private BigDecimal serviceFeeTotal;
    private BigDecimal netTotal;
    private BigDecimal serviceFee;

    /**
     * @return rightsholder name.
     */
    public String getRightsholderName() {
        return rightsholderName;
    }

    /**
     * Sets rightsholder name.
     *
     * @param rightsholderName rightsholder name
     */
    public void setRightsholderName(String rightsholderName) {
        this.rightsholderName = rightsholderName;
    }

    /**
     * @return rightsholder account number.
     */
    public Long getRightsholderAccountNumber() {
        return rightsholderAccountNumber;
    }

    /**
     * Sets rightsholder account number.
     *
     * @param rightsholderAccountNumber rightsholder account number
     */
    public void setRightsholderAccountNumber(Long rightsholderAccountNumber) {
        this.rightsholderAccountNumber = rightsholderAccountNumber;
    }

    /**
     * @return payee name.
     */
    public String getPayeeName() {
        return payeeName;
    }

    /**
     * Sets payee name.
     *
     * @param payeeName payee name
     */
    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    /**
     * @return payee account number.
     */
    public Long getPayeeAccountNumber() {
        return payeeAccountNumber;
    }

    /**
     * Sets payee account number.
     *
     * @param payeeAccountNumber payee account number
     */
    public void setPayeeAccountNumber(Long payeeAccountNumber) {
        this.payeeAccountNumber = payeeAccountNumber;
    }

    /**
     * @return scenario gross total.
     */
    public BigDecimal getGrossTotal() {
        return grossTotal;
    }

    /**
     * Sets scenario gross total.
     *
     * @param grossTotal gross total
     */
    public void setGrossTotal(BigDecimal grossTotal) {
        this.grossTotal = grossTotal;
    }

    /**
     * @return service fee total.
     */
    public BigDecimal getServiceFeeTotal() {
        return serviceFeeTotal;
    }

    /**
     * Sets service fee total.
     *
     * @param serviceFeeTotal service fee total
     */
    public void setServiceFeeTotal(BigDecimal serviceFeeTotal) {
        this.serviceFeeTotal = serviceFeeTotal;
    }

    /**
     * @return net total.
     */
    public BigDecimal getNetTotal() {
        return netTotal;
    }

    /**
     * Sets net total.
     *
     * @param netTotal net total
     */
    public void setNetTotal(BigDecimal netTotal) {
        this.netTotal = netTotal;
    }

    /**
     * @return service fee.
     */
    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    /**
     * Sets service fee.
     *
     * @param serviceFee service fee
     */
    public void setServiceFee(BigDecimal serviceFee) {
        if (null != serviceFee) {
            this.serviceFee = serviceFee;
        }
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
            .append(this.rightsholderName, that.rightsholderName)
            .append(this.rightsholderAccountNumber, that.rightsholderAccountNumber)
            .append(this.payeeName, that.payeeName)
            .append(this.payeeAccountNumber, that.payeeAccountNumber)
            .append(this.grossTotal, that.grossTotal)
            .append(this.serviceFeeTotal, that.serviceFeeTotal)
            .append(this.netTotal, that.netTotal)
            .append(this.serviceFee, that.serviceFee)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(rightsholderName)
            .append(rightsholderAccountNumber)
            .append(payeeName)
            .append(payeeAccountNumber)
            .append(grossTotal)
            .append(serviceFeeTotal)
            .append(netTotal)
            .append(serviceFee)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("rightsholderName", rightsholderName)
            .append("rightsholderAccountNumber", rightsholderAccountNumber)
            .append("payeeName", payeeName)
            .append("payeeAccountNumber", payeeAccountNumber)
            .append("grossTotal", grossTotal)
            .append("serviceFeeTotal", serviceFeeTotal)
            .append("netTotal", netTotal)
            .append("serviceFee", serviceFee)
            .toString();
    }
}
