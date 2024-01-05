package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents ACL Fund Pools by Agg LC Report record.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/24/2023
 *
 * @author Ihar Suvorau
 */
public class AclFundPoolByAggLcReportDto extends StoredEntity<String> {

    private static final long serialVersionUID = 3785362500136424184L;

    private String fundPoolName;
    private String licenseType;
    private int period;
    private Integer aggregateLicenseeClassId;
    private String aggregateLicenseeClassName;
    private BigDecimal netAmount;

    public String getFundPoolName() {
        return fundPoolName;
    }

    public void setFundPoolName(String fundPoolName) {
        this.fundPoolName = fundPoolName;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

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

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclFundPoolByAggLcReportDto that = (AclFundPoolByAggLcReportDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(fundPoolName, that.fundPoolName)
            .append(licenseType, that.licenseType)
            .append(period, that.period)
            .append(aggregateLicenseeClassId, that.aggregateLicenseeClassId)
            .append(aggregateLicenseeClassName, that.aggregateLicenseeClassName)
            .append(netAmount, that.netAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(fundPoolName)
            .append(licenseType)
            .append(period)
            .append(aggregateLicenseeClassId)
            .append(aggregateLicenseeClassName)
            .append(netAmount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("fundPoolName", fundPoolName)
            .append("licenseType", licenseType)
            .append("period", period)
            .append("aggregateLicenseeClassId", aggregateLicenseeClassId)
            .append("aggregateLicenseeClassName", aggregateLicenseeClassName)
            .append("netAmount", netAmount)
            .toString();
    }
}
