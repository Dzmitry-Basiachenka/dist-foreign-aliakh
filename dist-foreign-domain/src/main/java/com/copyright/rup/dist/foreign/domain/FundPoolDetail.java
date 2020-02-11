package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents {@link FundPool} detail.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 2/6/20
 *
 * @author Stanislau Rudak
 */
public class FundPoolDetail extends StoredEntity<String> {

    private AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
    private BigDecimal grossAmount = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);

    public AggregateLicenseeClass getAggregateLicenseeClass() {
        return aggregateLicenseeClass;
    }

    public void setAggregateLicenseeClass(AggregateLicenseeClass aggregateLicenseeClass) {
        this.aggregateLicenseeClass = aggregateLicenseeClass;
    }

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        FundPoolDetail that = (FundPoolDetail) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(aggregateLicenseeClass, that.aggregateLicenseeClass)
            .append(grossAmount, that.grossAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(aggregateLicenseeClass)
            .append(grossAmount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("aggregateLicenseeClass", aggregateLicenseeClass)
            .append("grossAmount", grossAmount)
            .toString();
    }
}
