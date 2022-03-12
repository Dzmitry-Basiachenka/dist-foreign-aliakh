package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents UDM Usable Details by Country Report.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/11/2022
 *
 * @author Mikita Maistrenka
 */
public class UdmUsableDetailsByCountryReportDto extends StoredEntity<String> {

    private Integer period;
    private String channel;
    private String usageOrigin;
    private String country;
    private Integer detailLicenseeClassId;
    private String detailLicenseeClassName;
    private Integer totalCount;

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

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

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        UdmUsableDetailsByCountryReportDto that = (UdmUsableDetailsByCountryReportDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(period, that.period)
            .append(channel, that.channel)
            .append(usageOrigin, that.usageOrigin)
            .append(country, that.country)
            .append(detailLicenseeClassId, that.detailLicenseeClassId)
            .append(detailLicenseeClassName, that.detailLicenseeClassName)
            .append(totalCount, that.totalCount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(period)
            .append(channel)
            .append(usageOrigin)
            .append(country)
            .append(detailLicenseeClassId)
            .append(detailLicenseeClassName)
            .append(totalCount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("period", period)
            .append("channel", channel)
            .append("usageOrigin", usageOrigin)
            .append("country", country)
            .append("detailLicenseeClassId", detailLicenseeClassId)
            .append("detailLicenseeClassName", detailLicenseeClassName)
            .append("totalCount", totalCount)
            .toString();
    }
}
