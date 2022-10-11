package com.copyright.rup.dist.foreign.domain.report;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents total amounts for ACL reports.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/10/2022
 *
 * @author Anton Azarenka
 */
public class AclReportTotalAmountsDto {

    private BigDecimal grossAmount;
    private BigDecimal netAmount;
    private BigDecimal printNetAmount;
    private BigDecimal digitalNetAmount;
    private BigDecimal aclNetAmount;
    private BigDecimal maclNetAmount;
    private BigDecimal vgwNetAmount;
    private BigDecimal jacdclNetAmount;

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public BigDecimal getPrintNetAmount() {
        return printNetAmount;
    }

    public void setPrintNetAmount(BigDecimal printNetAmount) {
        this.printNetAmount = printNetAmount;
    }

    public BigDecimal getDigitalNetAmount() {
        return digitalNetAmount;
    }

    public void setDigitalNetAmount(BigDecimal digitalNetAmount) {
        this.digitalNetAmount = digitalNetAmount;
    }

    public BigDecimal getAclNetAmount() {
        return aclNetAmount;
    }

    public void setAclNetAmount(BigDecimal aclNetAmount) {
        this.aclNetAmount = aclNetAmount;
    }

    public BigDecimal getMaclNetAmount() {
        return maclNetAmount;
    }

    public void setMaclNetAmount(BigDecimal maclNetAmount) {
        this.maclNetAmount = maclNetAmount;
    }

    public BigDecimal getVgwNetAmount() {
        return vgwNetAmount;
    }

    public void setVgwNetAmount(BigDecimal vgwNetAmount) {
        this.vgwNetAmount = vgwNetAmount;
    }

    public BigDecimal getJacdclNetAmount() {
        return jacdclNetAmount;
    }

    public void setJacdclNetAmount(BigDecimal jacdclNetAmount) {
        this.jacdclNetAmount = jacdclNetAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclReportTotalAmountsDto that = (AclReportTotalAmountsDto) obj;
        return new EqualsBuilder()
            .append(grossAmount, that.grossAmount)
            .append(netAmount, that.netAmount)
            .append(printNetAmount, that.printNetAmount)
            .append(digitalNetAmount, that.digitalNetAmount)
            .append(aclNetAmount, that.aclNetAmount)
            .append(maclNetAmount, that.maclNetAmount)
            .append(vgwNetAmount, that.vgwNetAmount)
            .append(jacdclNetAmount, that.jacdclNetAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(grossAmount)
            .append(netAmount)
            .append(printNetAmount)
            .append(digitalNetAmount)
            .append(aclNetAmount)
            .append(maclNetAmount)
            .append(vgwNetAmount)
            .append(jacdclNetAmount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("grossAmount", grossAmount)
            .append("netAmount", netAmount)
            .append("printNetAmount", printNetAmount)
            .append("digitalNetAmount", digitalNetAmount)
            .append("aclNetAmount", aclNetAmount)
            .append("maclNetAmount", maclNetAmount)
            .append("vgwNetAmount", vgwNetAmount)
            .append("jacdclNetAmount", jacdclNetAmount)
            .toString();
    }
}
