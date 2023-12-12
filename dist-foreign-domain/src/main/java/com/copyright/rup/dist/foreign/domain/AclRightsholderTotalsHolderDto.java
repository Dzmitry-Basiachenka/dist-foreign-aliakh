package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents ACL rightsholder totals holder DTO.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 08/24/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclRightsholderTotalsHolderDto extends AclRightsholderTotalsHolder {

    private static final long serialVersionUID = -7097106332467646693L;

    private Long wrWrkInst;
    private String systemTitle;
    private BigDecimal grossTotal = FdaConstants.DEFAULT_AMOUNT_SCALE_10;
    private BigDecimal netTotal = FdaConstants.DEFAULT_AMOUNT_SCALE_10;
    private AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();

    public Long getWrWrkInst() {
        return wrWrkInst;
    }

    public void setWrWrkInst(Long wrWrkInst) {
        this.wrWrkInst = wrWrkInst;
    }

    public String getSystemTitle() {
        return systemTitle;
    }

    public void setSystemTitle(String systemTitle) {
        this.systemTitle = systemTitle;
    }

    public BigDecimal getGrossTotal() {
        return grossTotal;
    }

    public void setGrossTotal(BigDecimal grossTotal) {
        this.grossTotal = grossTotal;
    }

    public BigDecimal getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(BigDecimal netTotal) {
        this.netTotal = netTotal;
    }

    public AggregateLicenseeClass getAggregateLicenseeClass() {
        return aggregateLicenseeClass;
    }

    public void setAggregateLicenseeClass(AggregateLicenseeClass aggregateLicenseeClass) {
        this.aggregateLicenseeClass = aggregateLicenseeClass;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclRightsholderTotalsHolderDto that = (AclRightsholderTotalsHolderDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(wrWrkInst, that.wrWrkInst)
            .append(systemTitle, that.systemTitle)
            .append(grossTotal, that.grossTotal)
            .append(netTotal, that.netTotal)
            .append(aggregateLicenseeClass, that.aggregateLicenseeClass)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(wrWrkInst)
            .append(systemTitle)
            .append(grossTotal)
            .append(netTotal)
            .append(aggregateLicenseeClass)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("wrWrkInst", wrWrkInst)
            .append("systemTitle", systemTitle)
            .append("grossTotal", grossTotal)
            .append("netTotal", netTotal)
            .append("aggregateLicenseeClass", aggregateLicenseeClass)
            .toString();
    }
}
