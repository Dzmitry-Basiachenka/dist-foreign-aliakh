package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents ACL Summary of Work Shares by Aggregate Licensee Class report record.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/03/22
 *
 * @author Mikita Maistrenka
 */
public class AclSummaryOfWorkSharesByAggLcReportDto extends StoredEntity<String> {

    private static final long serialVersionUID = -5817861763661797782L;

    private Integer aggregateLicenseeClassId;
    private String aggregateLicenseeClassName;
    private BigDecimal totalDetailSharePrint;
    private BigDecimal totalDetailShareDigital;

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

    public BigDecimal getTotalDetailSharePrint() {
        return totalDetailSharePrint;
    }

    public void setTotalDetailSharePrint(BigDecimal totalDetailSharePrint) {
        this.totalDetailSharePrint = totalDetailSharePrint;
    }

    public BigDecimal getTotalDetailShareDigital() {
        return totalDetailShareDigital;
    }

    public void setTotalDetailShareDigital(BigDecimal totalDetailShareDigital) {
        this.totalDetailShareDigital = totalDetailShareDigital;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclSummaryOfWorkSharesByAggLcReportDto that = (AclSummaryOfWorkSharesByAggLcReportDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(aggregateLicenseeClassId, that.aggregateLicenseeClassId)
            .append(aggregateLicenseeClassName, that.aggregateLicenseeClassName)
            .append(totalDetailSharePrint, that.totalDetailSharePrint)
            .append(totalDetailShareDigital, that.totalDetailShareDigital)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(aggregateLicenseeClassId)
            .append(aggregateLicenseeClassName)
            .append(totalDetailSharePrint)
            .append(totalDetailShareDigital)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("aggregateLicenseeClassId", aggregateLicenseeClassId)
            .append("aggregateLicenseeClassName", aggregateLicenseeClassName)
            .append("totalDetailSharePrint", totalDetailSharePrint)
            .append("totalDetailShareDigital", totalDetailShareDigital)
            .toString();
    }
}
