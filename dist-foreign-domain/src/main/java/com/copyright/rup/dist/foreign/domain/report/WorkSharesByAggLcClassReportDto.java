package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents Work Shares by Aggregate Licensee Class Summary Report record.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/22/20
 *
 * @author Uladzislau Shalamitski
 */
public class WorkSharesByAggLcClassReportDto extends StoredEntity<String> {

    private AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
    private BigDecimal totalShare;

    public AggregateLicenseeClass getAggregateLicenseeClass() {
        return aggregateLicenseeClass;
    }

    public void setAggregateLicenseeClass(AggregateLicenseeClass aggregateLicenseeClass) {
        this.aggregateLicenseeClass = aggregateLicenseeClass;
    }

    public BigDecimal getTotalShare() {
        return totalShare;
    }

    public void setTotalShare(BigDecimal totalShare) {
        this.totalShare = totalShare;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        WorkSharesByAggLcClassReportDto that =
            (WorkSharesByAggLcClassReportDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(aggregateLicenseeClass, that.aggregateLicenseeClass)
            .append(totalShare, that.totalShare)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(aggregateLicenseeClass)
            .append(totalShare)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("aggregateLicenseeClass", aggregateLicenseeClass)
            .append("totalShare", totalShare)
            .toString();
    }
}
