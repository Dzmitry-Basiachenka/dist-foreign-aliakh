package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents ACL Liabilities by Rightsholder Report record.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/13/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclLiabilitiesByRhReportDto extends StoredEntity<String> {

    private Long rhAccountNumber;
    private String rhName;
    private Long printPayeeAccountNumber;
    private String printPayeeName;
    private Long digitalPayeeAccountNumber;
    private String digitalPayeeName;
    private BigDecimal grossAmount;
    private BigDecimal netAmount;
    private BigDecimal printNetAmount;
    private BigDecimal digitalNetAmount;
    private BigDecimal aclNetAmount;
    private BigDecimal maclNetAmount;
    private BigDecimal vgwNetAmount;
    private BigDecimal jacdclNetAmount;

    public Long getRhAccountNumber() {
        return rhAccountNumber;
    }

    public void setRhAccountNumber(Long rhAccountNumber) {
        this.rhAccountNumber = rhAccountNumber;
    }

    public String getRhName() {
        return rhName;
    }

    public void setRhName(String rhName) {
        this.rhName = rhName;
    }

    public Long getPrintPayeeAccountNumber() {
        return printPayeeAccountNumber;
    }

    public void setPrintPayeeAccountNumber(Long printPayeeAccountNumber) {
        this.printPayeeAccountNumber = printPayeeAccountNumber;
    }

    public String getPrintPayeeName() {
        return printPayeeName;
    }

    public void setPrintPayeeName(String printPayeeName) {
        this.printPayeeName = printPayeeName;
    }

    public Long getDigitalPayeeAccountNumber() {
        return digitalPayeeAccountNumber;
    }

    public void setDigitalPayeeAccountNumber(Long digitalPayeeAccountNumber) {
        this.digitalPayeeAccountNumber = digitalPayeeAccountNumber;
    }

    public String getDigitalPayeeName() {
        return digitalPayeeName;
    }

    public void setDigitalPayeeName(String digitalPayeeName) {
        this.digitalPayeeName = digitalPayeeName;
    }

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
        AclLiabilitiesByRhReportDto that = (AclLiabilitiesByRhReportDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(rhAccountNumber, that.rhAccountNumber)
            .append(rhName, that.rhName)
            .append(printPayeeAccountNumber, that.printPayeeAccountNumber)
            .append(printPayeeName, that.printPayeeName)
            .append(digitalPayeeAccountNumber, that.digitalPayeeAccountNumber)
            .append(digitalPayeeName, that.digitalPayeeName)
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
            .appendSuper(super.hashCode())
            .append(rhAccountNumber)
            .append(rhName)
            .append(printPayeeAccountNumber)
            .append(printPayeeName)
            .append(digitalPayeeAccountNumber)
            .append(digitalPayeeName)
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
            .appendSuper(super.toString())
            .append("rhAccountNumber", rhAccountNumber)
            .append("rhName", rhName)
            .append("printPayeeAccountNumber", printPayeeAccountNumber)
            .append("printPayeeName", printPayeeName)
            .append("digitalPayeeAccountNumber", digitalPayeeAccountNumber)
            .append("digitalPayeeName", digitalPayeeName)
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
