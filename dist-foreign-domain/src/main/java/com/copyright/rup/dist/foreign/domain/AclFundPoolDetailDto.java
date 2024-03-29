package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Domain object to represent ACL fund pool detail DTO.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/11/2022
 *
 * @author Ihar Suvorau
 */
public class AclFundPoolDetailDto extends AclFundPoolDetail {

    private static final long serialVersionUID = 1567078515284594954L;

    private String fundPoolName;
    private int period;
    private AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();

    public String getFundPoolName() {
        return fundPoolName;
    }

    public void setFundPoolName(String fundPoolName) {
        this.fundPoolName = fundPoolName;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
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
        AclFundPoolDetailDto that = (AclFundPoolDetailDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(fundPoolName, that.fundPoolName)
            .append(period, that.period)
            .append(aggregateLicenseeClass, that.aggregateLicenseeClass)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(fundPoolName)
            .append(period)
            .append(aggregateLicenseeClass)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("fundPoolName", fundPoolName)
            .append("period", period)
            .append("aggregateLicenseeClass", aggregateLicenseeClass)
            .toString();
    }
}
