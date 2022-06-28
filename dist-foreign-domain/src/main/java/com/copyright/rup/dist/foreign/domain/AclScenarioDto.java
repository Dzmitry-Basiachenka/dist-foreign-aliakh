package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents ACL scenario DTO.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/27/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclScenarioDto extends AclScenario {

    private BigDecimal grossTotal = BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP);
    private BigDecimal grossTotalPrint = BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP);
    private BigDecimal grossTotalDigital = BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP);
    private BigDecimal serviceFeeTotal = BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP);
    private BigDecimal serviceFeeTotalPrint = BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP);
    private BigDecimal serviceFeeTotalDigital = BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP);
    private BigDecimal netTotal = BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP);
    private BigDecimal netTotalPrint = BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP);
    private BigDecimal netTotalDigital = BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP);
    private int numberOfRhsPrint;
    private int numberOfRhsDigital;
    private int numberOfWorksPrint;
    private int numberOfWorksDigital;
    private ScenarioAuditItem auditItem;

    public BigDecimal getGrossTotal() {
        return grossTotal;
    }

    public void setGrossTotal(BigDecimal grossTotal) {
        this.grossTotal = grossTotal;
    }

    public BigDecimal getGrossTotalPrint() {
        return grossTotalPrint;
    }

    public void setGrossTotalPrint(BigDecimal grossTotalPrint) {
        this.grossTotalPrint = grossTotalPrint;
    }

    public BigDecimal getGrossTotalDigital() {
        return grossTotalDigital;
    }

    public void setGrossTotalDigital(BigDecimal grossTotalDigital) {
        this.grossTotalDigital = grossTotalDigital;
    }

    public BigDecimal getServiceFeeTotal() {
        return serviceFeeTotal;
    }

    public void setServiceFeeTotal(BigDecimal serviceFeeTotal) {
        this.serviceFeeTotal = serviceFeeTotal;
    }

    public BigDecimal getServiceFeeTotalPrint() {
        return serviceFeeTotalPrint;
    }

    public void setServiceFeeTotalPrint(BigDecimal serviceFeeTotalPrint) {
        this.serviceFeeTotalPrint = serviceFeeTotalPrint;
    }

    public BigDecimal getServiceFeeTotalDigital() {
        return serviceFeeTotalDigital;
    }

    public void setServiceFeeTotalDigital(BigDecimal serviceFeeTotalDigital) {
        this.serviceFeeTotalDigital = serviceFeeTotalDigital;
    }

    public BigDecimal getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(BigDecimal netTotal) {
        this.netTotal = netTotal;
    }

    public BigDecimal getNetTotalPrint() {
        return netTotalPrint;
    }

    public void setNetTotalPrint(BigDecimal netTotalPrint) {
        this.netTotalPrint = netTotalPrint;
    }

    public BigDecimal getNetTotalDigital() {
        return netTotalDigital;
    }

    public void setNetTotalDigital(BigDecimal netTotalDigital) {
        this.netTotalDigital = netTotalDigital;
    }

    public int getNumberOfRhsPrint() {
        return numberOfRhsPrint;
    }

    public void setNumberOfRhsPrint(int numberOfRhsPrint) {
        this.numberOfRhsPrint = numberOfRhsPrint;
    }

    public int getNumberOfRhsDigital() {
        return numberOfRhsDigital;
    }

    public void setNumberOfRhsDigital(int numberOfRhsDigital) {
        this.numberOfRhsDigital = numberOfRhsDigital;
    }

    public int getNumberOfWorksPrint() {
        return numberOfWorksPrint;
    }

    public void setNumberOfWorksPrint(int numberOfWorksPrint) {
        this.numberOfWorksPrint = numberOfWorksPrint;
    }

    public int getNumberOfWorksDigital() {
        return numberOfWorksDigital;
    }

    public void setNumberOfWorksDigital(int numberOfWorksDigital) {
        this.numberOfWorksDigital = numberOfWorksDigital;
    }

    public ScenarioAuditItem getAuditItem() {
        return auditItem;
    }

    public void setAuditItem(ScenarioAuditItem auditItem) {
        this.auditItem = auditItem;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclScenarioDto that = (AclScenarioDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(grossTotal, that.grossTotal)
            .append(grossTotalPrint, that.grossTotalPrint)
            .append(grossTotalDigital, that.grossTotalDigital)
            .append(serviceFeeTotal, that.serviceFeeTotal)
            .append(serviceFeeTotalPrint, that.serviceFeeTotalPrint)
            .append(serviceFeeTotalDigital, that.serviceFeeTotalDigital)
            .append(netTotal, that.netTotal)
            .append(netTotalPrint, that.netTotalPrint)
            .append(netTotalDigital, that.netTotalDigital)
            .append(numberOfRhsPrint, that.numberOfRhsPrint)
            .append(numberOfRhsDigital, that.numberOfRhsDigital)
            .append(numberOfWorksPrint, that.numberOfWorksPrint)
            .append(numberOfWorksDigital, that.numberOfWorksDigital)
            .append(auditItem, that.auditItem)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(grossTotal)
            .append(grossTotalPrint)
            .append(grossTotalDigital)
            .append(serviceFeeTotal)
            .append(serviceFeeTotalPrint)
            .append(serviceFeeTotalDigital)
            .append(netTotal)
            .append(netTotalPrint)
            .append(netTotalDigital)
            .append(numberOfRhsPrint)
            .append(numberOfRhsDigital)
            .append(numberOfWorksPrint)
            .append(numberOfWorksDigital)
            .append(auditItem)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("grossTotal", grossTotal)
            .append("grossTotalPrint", grossTotalPrint)
            .append("grossTotalDigital", grossTotalDigital)
            .append("serviceFeeTotal", serviceFeeTotal)
            .append("serviceFeeTotalPrint", serviceFeeTotalPrint)
            .append("serviceFeeTotalDigital", serviceFeeTotalDigital)
            .append("netTotal", netTotal)
            .append("netTotalPrint", netTotalPrint)
            .append("netTotalDigital", netTotalDigital)
            .append("numberOfRhsPrint", numberOfRhsPrint)
            .append("numberOfRhsDigital", numberOfRhsDigital)
            .append("numberOfWorksPrint", numberOfWorksPrint)
            .append("numberOfWorksDigital", numberOfWorksDigital)
            .append("auditItem", auditItem)
            .toString();
    }
}
