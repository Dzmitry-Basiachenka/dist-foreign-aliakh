package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents domain to hold information about rightsholder, print and digital totals for ACL scenario.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/28/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclRightsholderTotalsHolder extends StoredEntity<String> {

    private Rightsholder rightsholder = new Rightsholder();
    private Long printPayeeAccountNumber;
    private String printPayeeName;
    private Long digitalPayeeAccountNumber;
    private String digitalPayeeName;
    private BigDecimal grossTotalPrint = BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP);
    private BigDecimal grossTotalDigital = BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP);
    private BigDecimal serviceFeeTotalPrint = BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP);
    private BigDecimal serviceFeeTotalDigital = BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP);
    private BigDecimal netTotalPrint = BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP);
    private BigDecimal netTotalDigital = BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP);
    private int numberOfTitles;
    private int numberOfAggLcClasses;
    private String licenseType;

    public Rightsholder getRightsholder() {
        return rightsholder;
    }

    public void setRightsholder(Rightsholder rightsholder) {
        this.rightsholder = rightsholder;
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

    public int getNumberOfTitles() {
        return numberOfTitles;
    }

    public void setNumberOfTitles(int numberOfTitles) {
        this.numberOfTitles = numberOfTitles;
    }

    public int getNumberOfAggLcClasses() {
        return numberOfAggLcClasses;
    }

    public void setNumberOfAggLcClasses(int numberOfAggLcClasses) {
        this.numberOfAggLcClasses = numberOfAggLcClasses;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclRightsholderTotalsHolder that = (AclRightsholderTotalsHolder) obj;
        return new EqualsBuilder()
            .append(rightsholder, that.rightsholder)
            .append(printPayeeAccountNumber, that.printPayeeAccountNumber)
            .append(printPayeeName, that.printPayeeName)
            .append(digitalPayeeAccountNumber, that.digitalPayeeAccountNumber)
            .append(digitalPayeeName, that.digitalPayeeName)
            .append(grossTotalPrint, that.grossTotalPrint)
            .append(grossTotalDigital, that.grossTotalDigital)
            .append(serviceFeeTotalPrint, that.serviceFeeTotalPrint)
            .append(serviceFeeTotalDigital, that.serviceFeeTotalDigital)
            .append(netTotalPrint, that.netTotalPrint)
            .append(netTotalDigital, that.netTotalDigital)
            .append(numberOfTitles, that.numberOfTitles)
            .append(numberOfAggLcClasses, that.numberOfAggLcClasses)
            .append(licenseType, that.licenseType)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(rightsholder)
            .append(printPayeeAccountNumber)
            .append(printPayeeName)
            .append(digitalPayeeAccountNumber)
            .append(digitalPayeeName)
            .append(grossTotalPrint)
            .append(grossTotalDigital)
            .append(serviceFeeTotalPrint)
            .append(serviceFeeTotalDigital)
            .append(netTotalPrint)
            .append(netTotalDigital)
            .append(numberOfTitles)
            .append(numberOfAggLcClasses)
            .append(licenseType)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("rightsholder", rightsholder)
            .append("printPayeeAccountNumber", printPayeeAccountNumber)
            .append("printPayeeName", printPayeeName)
            .append("digitalPayeeAccountNumber", digitalPayeeAccountNumber)
            .append("digitalPayeeName", digitalPayeeName)
            .append("grossTotalPrint", grossTotalPrint)
            .append("grossTotalDigital", grossTotalDigital)
            .append("serviceFeeTotalPrint", serviceFeeTotalPrint)
            .append("serviceFeeTotalDigital", serviceFeeTotalDigital)
            .append("netTotalPrint", netTotalPrint)
            .append("netTotalDigital", netTotalDigital)
            .append("numberOfTitles", numberOfTitles)
            .append("numberOfAggLcClasses", numberOfAggLcClasses)
            .append("licenseType", licenseType)
            .toString();
    }
}
