package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents UDM value.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/24/2021
 *
 * @author Uladzislau Shalamitski
 */
public class UdmValue extends StoredEntity<String> {

    private Integer period;
    private UdmValueStatusEnum status;
    private Long rhAccountNumber;
    private Long wrWrkInst;
    private String systemTitle;
    private String standardNumber;
    private String standardNumberType;

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public UdmValueStatusEnum getStatus() {
        return status;
    }

    public void setStatus(UdmValueStatusEnum status) {
        this.status = status;
    }

    public Long getRhAccountNumber() {
        return rhAccountNumber;
    }

    public void setRhAccountNumber(Long rhAccountNumber) {
        this.rhAccountNumber = rhAccountNumber;
    }

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

    public String getStandardNumber() {
        return standardNumber;
    }

    public void setStandardNumber(String standardNumber) {
        this.standardNumber = standardNumber;
    }

    public String getStandardNumberType() {
        return standardNumberType;
    }

    public void setStandardNumberType(String standardNumberType) {
        this.standardNumberType = standardNumberType;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        UdmValue that = (UdmValue) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(period, that.period)
            .append(status, that.status)
            .append(rhAccountNumber, that.rhAccountNumber)
            .append(wrWrkInst, that.wrWrkInst)
            .append(systemTitle, that.systemTitle)
            .append(standardNumber, that.standardNumber)
            .append(standardNumberType, that.standardNumberType)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(period)
            .append(status)
            .append(rhAccountNumber)
            .append(wrWrkInst)
            .append(systemTitle)
            .append(standardNumber)
            .append(standardNumberType)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("period", period)
            .append("status", status)
            .append("rhAccountNumber", rhAccountNumber)
            .append("wrWrkInst", wrWrkInst)
            .append("systemTitle", systemTitle)
            .append("systemStandardNumber", standardNumber)
            .append("standardNumberType", standardNumberType)
            .toString();
    }
}
