package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents ACL Work Shares by Aggregate Licensee Class report record.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/09/2022
 *
 * @author Ihar Suvorau
 */
public class AclWorkSharesByAggLcReportDto extends StoredEntity<String> {

    private Integer aggregateLicenseeClassId;
    private String aggregateLicenseeClassName;
    private Long wrWrkInst;
    private String workTitle;
    private Long printRhAccountNumber;
    private String printRhName;
    private Long digitalRhAccountNumber;
    private String digitalRhName;
    private BigDecimal printTotalShare;
    private BigDecimal printValueShare;
    private BigDecimal printVolumeShare;
    private BigDecimal printNetAmount;
    private BigDecimal digitalTotalShare;
    private BigDecimal digitalValueShare;
    private BigDecimal digitalVolumeShare;
    private BigDecimal digitalNetAmount;
    private BigDecimal totalNetAmount;

    public Integer getAggregateLicenseeClassId() {
        return aggregateLicenseeClassId;
    }

    public void setAggregateLicenseeClassId(Integer aggregateLicenseeClassId) {
        this.aggregateLicenseeClassId = aggregateLicenseeClassId;
    }

    public String getAggregateLicenseeClassName() {
        return aggregateLicenseeClassName;
    }

    public void setAggregateLicenseeClassName(String aggregateLicenseeClassName) {
        this.aggregateLicenseeClassName = aggregateLicenseeClassName;
    }

    public Long getWrWrkInst() {
        return wrWrkInst;
    }

    public void setWrWrkInst(Long wrWrkInst) {
        this.wrWrkInst = wrWrkInst;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }

    public Long getPrintRhAccountNumber() {
        return printRhAccountNumber;
    }

    public void setPrintRhAccountNumber(Long printRhAccountNumber) {
        this.printRhAccountNumber = printRhAccountNumber;
    }

    public String getPrintRhName() {
        return printRhName;
    }

    public void setPrintRhName(String printRhName) {
        this.printRhName = printRhName;
    }

    public Long getDigitalRhAccountNumber() {
        return digitalRhAccountNumber;
    }

    public void setDigitalRhAccountNumber(Long digitalRhAccountNumber) {
        this.digitalRhAccountNumber = digitalRhAccountNumber;
    }

    public String getDigitalRhName() {
        return digitalRhName;
    }

    public void setDigitalRhName(String digitalRhName) {
        this.digitalRhName = digitalRhName;
    }

    public BigDecimal getPrintTotalShare() {
        return printTotalShare;
    }

    public void setPrintTotalShare(BigDecimal printTotalShare) {
        this.printTotalShare = printTotalShare;
    }

    public BigDecimal getPrintValueShare() {
        return printValueShare;
    }

    public void setPrintValueShare(BigDecimal printValueShare) {
        this.printValueShare = printValueShare;
    }

    public BigDecimal getPrintVolumeShare() {
        return printVolumeShare;
    }

    public void setPrintVolumeShare(BigDecimal printVolumeShare) {
        this.printVolumeShare = printVolumeShare;
    }

    public BigDecimal getPrintNetAmount() {
        return printNetAmount;
    }

    public void setPrintNetAmount(BigDecimal printNetAmount) {
        this.printNetAmount = printNetAmount;
    }

    public BigDecimal getDigitalTotalShare() {
        return digitalTotalShare;
    }

    public void setDigitalTotalShare(BigDecimal digitalTotalShare) {
        this.digitalTotalShare = digitalTotalShare;
    }

    public BigDecimal getDigitalValueShare() {
        return digitalValueShare;
    }

    public void setDigitalValueShare(BigDecimal digitalValueShare) {
        this.digitalValueShare = digitalValueShare;
    }

    public BigDecimal getDigitalVolumeShare() {
        return digitalVolumeShare;
    }

    public void setDigitalVolumeShare(BigDecimal digitalVolumeShare) {
        this.digitalVolumeShare = digitalVolumeShare;
    }

    public BigDecimal getDigitalNetAmount() {
        return digitalNetAmount;
    }

    public void setDigitalNetAmount(BigDecimal digitalNetAmount) {
        this.digitalNetAmount = digitalNetAmount;
    }

    public BigDecimal getTotalNetAmount() {
        return totalNetAmount;
    }

    public void setTotalNetAmount(BigDecimal totalNetAmount) {
        this.totalNetAmount = totalNetAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclWorkSharesByAggLcReportDto that = (AclWorkSharesByAggLcReportDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(aggregateLicenseeClassId, that.aggregateLicenseeClassId)
            .append(aggregateLicenseeClassName, that.aggregateLicenseeClassName)
            .append(wrWrkInst, that.wrWrkInst)
            .append(workTitle, that.workTitle)
            .append(printRhAccountNumber, that.printRhAccountNumber)
            .append(printRhName, that.printRhName)
            .append(digitalRhAccountNumber, that.digitalRhAccountNumber)
            .append(digitalRhName, that.digitalRhName)
            .append(printTotalShare, that.printTotalShare)
            .append(printValueShare, that.printValueShare)
            .append(printVolumeShare, that.printVolumeShare)
            .append(printNetAmount, that.printNetAmount)
            .append(digitalTotalShare, that.digitalTotalShare)
            .append(digitalValueShare, that.digitalValueShare)
            .append(digitalVolumeShare, that.digitalVolumeShare)
            .append(digitalNetAmount, that.digitalNetAmount)
            .append(totalNetAmount, that.totalNetAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(aggregateLicenseeClassId)
            .append(aggregateLicenseeClassName)
            .append(wrWrkInst)
            .append(workTitle)
            .append(printRhAccountNumber)
            .append(printRhName)
            .append(digitalRhAccountNumber)
            .append(digitalRhName)
            .append(printTotalShare)
            .append(printValueShare)
            .append(printVolumeShare)
            .append(printNetAmount)
            .append(digitalTotalShare)
            .append(digitalValueShare)
            .append(digitalVolumeShare)
            .append(digitalNetAmount)
            .append(totalNetAmount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("aggregateLicenseeClassId", aggregateLicenseeClassId)
            .append("aggregateLicenseeClassName", aggregateLicenseeClassName)
            .append("wrWrkInst", wrWrkInst)
            .append("workTitle", workTitle)
            .append("printRhAccountNumber", printRhAccountNumber)
            .append("printRhName", printRhName)
            .append("digitalRhAccountNumber", digitalRhAccountNumber)
            .append("digitalRhName", digitalRhName)
            .append("printTotalShare", printTotalShare)
            .append("printValueShare", printValueShare)
            .append("printVolumeShare", printVolumeShare)
            .append("printNetAmount", printNetAmount)
            .append("digitalTotalShare", digitalTotalShare)
            .append("digitalValueShare", digitalValueShare)
            .append("digitalVolumeShare", digitalVolumeShare)
            .append("digitalNetAmount", digitalNetAmount)
            .append("totalNetAmount", totalNetAmount)
            .toString();
    }
}
