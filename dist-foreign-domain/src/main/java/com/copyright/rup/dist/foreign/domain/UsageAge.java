package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents usage age for AACL product family.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/11/2020
 *
 * @author Uladzislau Shalamitski
 */
public class UsageAge {

    private Integer period;
    private BigDecimal weight;

    /**
     * Constructor.
     */
    public UsageAge() {
    }

    /**
     * Constructor.
     *
     * @param usageAge instance of {@link UsageAge}
     */
    public UsageAge(UsageAge usageAge) {
        this.period = usageAge.getPeriod();
        this.weight = usageAge.getWeight();
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        UsageAge that = (UsageAge) obj;
        return new EqualsBuilder()
            .append(period, that.period)
            .append(weight, that.weight)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(period)
            .append(weight)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("period", period)
            .append("weight", weight)
            .toString();
    }
}
