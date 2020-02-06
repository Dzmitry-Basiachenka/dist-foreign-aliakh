package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents AACL Fund Pool detail.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 2/6/20
 *
 * @author Stanislau Rudak
 */
public class AaclFundPoolDetail extends StoredEntity<String> {

    private String fundPoolId;
    private AggregateLicenseeClass aggregateLicenseeClass;
    private BigDecimal grossAmount;

    public String getFundPoolId() {
        return fundPoolId;
    }

    public void setFundPoolId(String fundPoolId) {
        this.fundPoolId = fundPoolId;
    }

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
        AaclFundPoolDetail that = (AaclFundPoolDetail) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(fundPoolId, that.fundPoolId)
            .append(aggregateLicenseeClass, that.aggregateLicenseeClass)
            .append(grossAmount, that.grossAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(fundPoolId)
            .append(aggregateLicenseeClass)
            .append(grossAmount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("fundPoolId", fundPoolId)
            .append("aggregateLicenseeClass", aggregateLicenseeClass)
            .append("grossAmount", grossAmount)
            .toString();
    }
}
