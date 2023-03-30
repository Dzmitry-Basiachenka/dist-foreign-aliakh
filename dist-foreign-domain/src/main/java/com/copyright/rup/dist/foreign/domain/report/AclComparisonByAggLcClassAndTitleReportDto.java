package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents ACL Comparison by Aggregate Licensee Class and Title Report record.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/27/2023
 *
 * @author Mikita Maistrenka
 */
public class AclComparisonByAggLcClassAndTitleReportDto extends StoredEntity<String> {

    private Integer aggregateLicenseeClassId;
    private String aggregateLicenseeClassName;
    private String wrWrkInst;
    private String systemTitle;
    private String pubTypeName;
    private Long printRhAccountNumberPreviousPeriod;
    private String printRhNamePreviousPeriod;
    private Long digitalRhAccountNumberPreviousPeriod;
    private String digitalRhNamePreviousPeriod;
    private BigDecimal printNetAmountPreviousPeriod;
    private BigDecimal digitalNetAmountPreviousPeriod;
    private BigDecimal totalNetAmountPreviousPeriod;
    private Long printRhAccountNumberCurrentPeriod;
    private String printRhNameCurrentPeriod;
    private Long digitalRhAccountNumberCurrentPeriod;
    private String digitalRhNameCurrentPeriod;
    private BigDecimal printNetAmountCurrentPeriod;
    private BigDecimal digitalNetAmountCurrentPeriod;
    private BigDecimal totalNetAmountCurrentPeriod;
    private BigDecimal delta;
    private Integer changePercent;

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

    public String getWrWrkInst() {
        return wrWrkInst;
    }

    public void setWrWrkInst(String wrWrkInst) {
        this.wrWrkInst = wrWrkInst;
    }

    public String getSystemTitle() {
        return systemTitle;
    }

    public void setSystemTitle(String systemTitle) {
        this.systemTitle = systemTitle;
    }

    public String getPubTypeName() {
        return pubTypeName;
    }

    public void setPubTypeName(String pubTypeName) {
        this.pubTypeName = pubTypeName;
    }

    public Long getPrintRhAccountNumberPreviousPeriod() {
        return printRhAccountNumberPreviousPeriod;
    }

    public void setPrintRhAccountNumberPreviousPeriod(Long printRhAccountNumberPreviousPeriod) {
        this.printRhAccountNumberPreviousPeriod = printRhAccountNumberPreviousPeriod;
    }

    public String getPrintRhNamePreviousPeriod() {
        return printRhNamePreviousPeriod;
    }

    public void setPrintRhNamePreviousPeriod(String printRhNamePreviousPeriod)  {
        this.printRhNamePreviousPeriod = printRhNamePreviousPeriod;
    }

    public Long getDigitalRhAccountNumberPreviousPeriod() {
        return digitalRhAccountNumberPreviousPeriod;
    }

    public void setDigitalRhAccountNumberPreviousPeriod(Long digitalRhAccountNumberPreviousPeriod) {
        this.digitalRhAccountNumberPreviousPeriod = digitalRhAccountNumberPreviousPeriod;
    }

    public String getDigitalRhNamePreviousPeriod() {
        return digitalRhNamePreviousPeriod;
    }

    public void setDigitalRhNamePreviousPeriod(String digitalRhNamePreviousPeriod) {
        this.digitalRhNamePreviousPeriod = digitalRhNamePreviousPeriod;
    }

    public BigDecimal getPrintNetAmountPreviousPeriod() {
        return printNetAmountPreviousPeriod;
    }

    public void setPrintNetAmountPreviousPeriod(BigDecimal printNetAmountPreviousPeriod) {
        this.printNetAmountPreviousPeriod = printNetAmountPreviousPeriod;
    }

    public BigDecimal getDigitalNetAmountPreviousPeriod() {
        return digitalNetAmountPreviousPeriod;
    }

    public void setDigitalNetAmountPreviousPeriod(BigDecimal digitalNetAmountPreviousPeriod) {
        this.digitalNetAmountPreviousPeriod = digitalNetAmountPreviousPeriod;
    }

    public BigDecimal getTotalNetAmountPreviousPeriod() {
        return totalNetAmountPreviousPeriod;
    }

    public void setTotalNetAmountPreviousPeriod(BigDecimal totalNetAmountPreviousPeriod) {
        this.totalNetAmountPreviousPeriod = totalNetAmountPreviousPeriod;
    }

    public Long getPrintRhAccountNumberCurrentPeriod() {
        return printRhAccountNumberCurrentPeriod;
    }

    public void setPrintRhAccountNumberCurrentPeriod(Long printRhAccountNumberCurrentPeriod) {
        this.printRhAccountNumberCurrentPeriod = printRhAccountNumberCurrentPeriod;
    }

    public String getPrintRhNameCurrentPeriod() {
        return printRhNameCurrentPeriod;
    }

    public void setPrintRhNameCurrentPeriod(String printRhNameCurrentPeriod) {
        this.printRhNameCurrentPeriod = printRhNameCurrentPeriod;
    }

    public Long getDigitalRhAccountNumberCurrentPeriod() {
        return digitalRhAccountNumberCurrentPeriod;
    }

    public void setDigitalRhAccountNumberCurrentPeriod(Long digitalRhAccountNumberCurrentPeriod) {
        this.digitalRhAccountNumberCurrentPeriod = digitalRhAccountNumberCurrentPeriod;
    }

    public String getDigitalRhNameCurrentPeriod() {
        return digitalRhNameCurrentPeriod;
    }

    public void setDigitalRhNameCurrentPeriod(String digitalRhNameCurrentPeriod) {
        this.digitalRhNameCurrentPeriod = digitalRhNameCurrentPeriod;
    }

    public BigDecimal getPrintNetAmountCurrentPeriod() {
        return printNetAmountCurrentPeriod;
    }

    public void setPrintNetAmountCurrentPeriod(BigDecimal printNetAmountCurrentPeriod) {
        this.printNetAmountCurrentPeriod = printNetAmountCurrentPeriod;
    }

    public BigDecimal getDigitalNetAmountCurrentPeriod() {
        return digitalNetAmountCurrentPeriod;
    }

    public void setDigitalNetAmountCurrentPeriod(BigDecimal digitalNetAmountCurrentPeriod) {
        this.digitalNetAmountCurrentPeriod = digitalNetAmountCurrentPeriod;
    }

    public BigDecimal getTotalNetAmountCurrentPeriod() {
        return totalNetAmountCurrentPeriod;
    }

    public void setTotalNetAmountCurrentPeriod(BigDecimal totalNetAmountCurrentPeriod) {
        this.totalNetAmountCurrentPeriod = totalNetAmountCurrentPeriod;
    }

    public BigDecimal getDelta() {
        return delta;
    }

    public void setDelta(BigDecimal delta) {
        this.delta = delta;
    }

    public Integer getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(Integer changePercent) {
        this.changePercent = changePercent;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclComparisonByAggLcClassAndTitleReportDto that = (AclComparisonByAggLcClassAndTitleReportDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(aggregateLicenseeClassId, that.aggregateLicenseeClassId)
            .append(aggregateLicenseeClassName, that.aggregateLicenseeClassName)
            .append(wrWrkInst, that.wrWrkInst)
            .append(systemTitle, that.systemTitle)
            .append(pubTypeName, that.pubTypeName)
            .append(printRhAccountNumberPreviousPeriod, that.printRhAccountNumberPreviousPeriod)
            .append(printRhNamePreviousPeriod, that.printRhNamePreviousPeriod)
            .append(digitalRhAccountNumberPreviousPeriod, that.digitalRhAccountNumberPreviousPeriod)
            .append(digitalRhNamePreviousPeriod, that.digitalRhNamePreviousPeriod)
            .append(printNetAmountPreviousPeriod, that.printNetAmountPreviousPeriod)
            .append(digitalNetAmountPreviousPeriod, that.digitalNetAmountPreviousPeriod)
            .append(totalNetAmountPreviousPeriod, that.totalNetAmountPreviousPeriod)
            .append(printRhAccountNumberCurrentPeriod, that.printRhAccountNumberCurrentPeriod)
            .append(printRhNameCurrentPeriod, that.printRhNameCurrentPeriod)
            .append(digitalRhAccountNumberCurrentPeriod, that.digitalRhAccountNumberCurrentPeriod)
            .append(digitalRhNameCurrentPeriod, that.digitalRhNameCurrentPeriod)
            .append(printNetAmountCurrentPeriod, that.printNetAmountCurrentPeriod)
            .append(digitalNetAmountCurrentPeriod, that.digitalNetAmountCurrentPeriod)
            .append(totalNetAmountCurrentPeriod, that.totalNetAmountCurrentPeriod)
            .append(delta, that.delta)
            .append(changePercent, that.changePercent)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(aggregateLicenseeClassId)
            .append(aggregateLicenseeClassName)
            .append(wrWrkInst)
            .append(systemTitle)
            .append(pubTypeName)
            .append(printRhAccountNumberPreviousPeriod)
            .append(printRhNamePreviousPeriod)
            .append(digitalRhAccountNumberPreviousPeriod)
            .append(digitalRhNamePreviousPeriod)
            .append(printNetAmountPreviousPeriod)
            .append(digitalNetAmountPreviousPeriod)
            .append(totalNetAmountPreviousPeriod)
            .append(printRhAccountNumberCurrentPeriod)
            .append(printRhNameCurrentPeriod)
            .append(digitalRhAccountNumberCurrentPeriod)
            .append(digitalRhNameCurrentPeriod)
            .append(printNetAmountCurrentPeriod)
            .append(digitalNetAmountCurrentPeriod)
            .append(totalNetAmountCurrentPeriod)
            .append(delta)
            .append(changePercent)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("aggregateLicenseeClassId", aggregateLicenseeClassId)
            .append("aggregateLicenseeClassName", aggregateLicenseeClassName)
            .append("wrWrkInst", wrWrkInst)
            .append("systemTitle", systemTitle)
            .append("pubTypeName", pubTypeName)
            .append("printRhAccountNumberPreviousPeriod", printRhAccountNumberPreviousPeriod)
            .append("printRhNamePreviousPeriod", printRhNamePreviousPeriod)
            .append("digitalRhAccountNumberPreviousPeriod", digitalRhAccountNumberPreviousPeriod)
            .append("digitalRhNamePreviousPeriod", digitalRhNamePreviousPeriod)
            .append("printNetAmountPreviousPeriod", printNetAmountPreviousPeriod)
            .append("digitalNetAmountPreviousPeriod", digitalNetAmountPreviousPeriod)
            .append("totalNetAmountPreviousPeriod", totalNetAmountPreviousPeriod)
            .append("printRhAccountNumberCurrentPeriod", printRhAccountNumberCurrentPeriod)
            .append("printRhNameCurrentPeriod", printRhNameCurrentPeriod)
            .append("digitalRhAccountNumberCurrentPeriod", digitalRhAccountNumberCurrentPeriod)
            .append("digitalRhNameCurrentPeriod", digitalRhNameCurrentPeriod)
            .append("printNetAmountCurrentPeriod", printNetAmountCurrentPeriod)
            .append("digitalNetAmountCurrentPeriod", digitalNetAmountCurrentPeriod)
            .append("totalNetAmountCurrentPeriod", totalNetAmountCurrentPeriod)
            .append("delta", delta)
            .append("changePercent", changePercent)
            .toString();
    }
}
