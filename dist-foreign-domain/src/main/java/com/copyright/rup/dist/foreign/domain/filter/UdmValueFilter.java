package com.copyright.rup.dist.foreign.domain.filter;

import com.copyright.rup.dist.foreign.domain.ValueStatusEnum;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Set;

/**
 * Class represents a set of filtering criteria for UDM Value.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/22/21
 *
 * @author Anton Azarenka
 */
public class UdmValueFilter {

    private Set<Integer> periods;
    private String currency;
    private ValueStatusEnum valueStatus;
    private Long wrWrkInst;
    private String systemTitle;
    private String standardNumber;
    private String rhName;
    private Long rhAccountNumber;
    private String pubType;
    private FilterExpression<String> lastPubType = new FilterExpression<>();

    /**
     * Default constructor.
     */
    public UdmValueFilter() {
    }

    /**
     * Constructs new filter based on existing.
     *
     * @param filter base filter
     */
    public UdmValueFilter(UdmValueFilter filter) {
        if (null != filter) {
            setPeriods(filter.getPeriods());
            setCurrency(filter.getCurrency());
            setValueStatus(filter.getValueStatus());
            setWrWrkInst(filter.getWrWrkInst());
            setSystemTitle(filter.getSystemTitle());
            setStandardNumber(filter.getStandardNumber());
            setRhName(filter.getRhName());
            setRhAccountNumber(filter.getRhAccountNumber());
            setPubType(filter.getPubType());
            setLastPubType(filter.getLastPubType());
        }
    }

    public Set<Integer> getPeriods() {
        return periods;
    }

    public void setPeriods(Set<Integer> periods) {
        this.periods = periods;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public ValueStatusEnum getValueStatus() {
        return valueStatus;
    }

    public void setValueStatus(ValueStatusEnum valueStatus) {
        this.valueStatus = valueStatus;
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

    public String getRhName() {
        return rhName;
    }

    public void setRhName(String rhName) {
        this.rhName = rhName;
    }

    public Long getRhAccountNumber() {
        return rhAccountNumber;
    }

    public void setRhAccountNumber(Long rhAccountNumber) {
        this.rhAccountNumber = rhAccountNumber;
    }

    public String getPubType() {
        return pubType;
    }

    public void setPubType(String pubType) {
        this.pubType = pubType;
    }

    public FilterExpression<String> getLastPubType() {
        return lastPubType;
    }

    public void setLastPubType(FilterExpression<String> lastPubType) {
        this.lastPubType = lastPubType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (null == o || getClass() != o.getClass()) {
            return false;
        }

        UdmValueFilter that = (UdmValueFilter) o;

        return new EqualsBuilder()
            .append(periods, that.periods)
            .append(currency, that.currency)
            .append(valueStatus, that.valueStatus)
            .append(wrWrkInst, that.wrWrkInst)
            .append(systemTitle, that.systemTitle)
            .append(standardNumber, that.standardNumber)
            .append(rhName, that.rhName)
            .append(rhAccountNumber, that.rhAccountNumber)
            .append(pubType, that.pubType)
            .append(lastPubType, that.lastPubType)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(periods)
            .append(currency)
            .append(valueStatus)
            .append(wrWrkInst)
            .append(systemTitle)
            .append(standardNumber)
            .append(rhName)
            .append(rhAccountNumber)
            .append(pubType)
            .append(lastPubType)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("periods", periods)
            .append("currency", currency)
            .append("valueStatus", valueStatus)
            .append("wrWrkInst", wrWrkInst)
            .append("systemTitle", systemTitle)
            .append("standardNumber", standardNumber)
            .append("rhName", rhName)
            .append("rhAccountNumber", rhAccountNumber)
            .append("pubType", pubType)
            .append("lastPubType", lastPubType)
            .toString();
    }
}
