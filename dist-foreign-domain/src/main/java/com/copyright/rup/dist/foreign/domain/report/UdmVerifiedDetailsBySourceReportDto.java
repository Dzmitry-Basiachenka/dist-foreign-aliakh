package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents UDM Verified Details By Source Report.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/05/2022
 *
 * @author Mikita Maistrenka
 */
public class UdmVerifiedDetailsBySourceReportDto extends StoredEntity<String> {

    private Integer detailLicenseeClassId;
    private String detailLicenseeClassName;
    private Integer aggregateLicenseeClassId;
    private String aggregateLicenseeClassName;
    private String channel;
    private String usageOrigin;
    private Integer totalCountOfDetailsInEligibleStatus;

    public Integer getDetailLicenseeClassId() {
        return detailLicenseeClassId;
    }

    public void setDetailLicenseeClassId(Integer detailLicenseeClassId) {
        this.detailLicenseeClassId = detailLicenseeClassId;
    }

    public String getDetailLicenseeClassName() {
        return detailLicenseeClassName;
    }

    public void setDetailLicenseeClassName(String detailLicenseeClassName) {
        this.detailLicenseeClassName = detailLicenseeClassName;
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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getUsageOrigin() {
        return usageOrigin;
    }

    public void setUsageOrigin(String usageOrigin) {
        this.usageOrigin = usageOrigin;
    }

    public Integer getTotalCountOfDetailsInEligibleStatus() {
        return totalCountOfDetailsInEligibleStatus;
    }

    public void setTotalCountOfDetailsInEligibleStatus(Integer totalCountOfDetailsInEligibleStatus) {
        this.totalCountOfDetailsInEligibleStatus = totalCountOfDetailsInEligibleStatus;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        UdmVerifiedDetailsBySourceReportDto that = (UdmVerifiedDetailsBySourceReportDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(channel, that.channel)
            .append(usageOrigin, that.usageOrigin)
            .append(detailLicenseeClassId, that.detailLicenseeClassId)
            .append(detailLicenseeClassName, that.detailLicenseeClassName)
            .append(aggregateLicenseeClassId, that.aggregateLicenseeClassId)
            .append(aggregateLicenseeClassName, that.aggregateLicenseeClassName)
            .append(totalCountOfDetailsInEligibleStatus, that.totalCountOfDetailsInEligibleStatus)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(channel)
            .append(usageOrigin)
            .append(detailLicenseeClassId)
            .append(detailLicenseeClassName)
            .append(aggregateLicenseeClassId)
            .append(aggregateLicenseeClassName)
            .append(totalCountOfDetailsInEligibleStatus)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("channel", channel)
            .append("usageOrigin", usageOrigin)
            .append("detailLicenseeClassId", detailLicenseeClassId)
            .append("detailLicenseeClassName", detailLicenseeClassName)
            .append("aggregateLicenseeClassId", aggregateLicenseeClassId)
            .append("aggregateLicenseeClassName", aggregateLicenseeClassName)
            .append("totalCountOfDetailsInEligibleStatus", totalCountOfDetailsInEligibleStatus)
            .toString();
    }
}
