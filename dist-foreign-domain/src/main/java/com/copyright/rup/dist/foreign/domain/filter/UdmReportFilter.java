package com.copyright.rup.dist.foreign.domain.filter;

import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Class represents a set of filtering criteria for UDM report.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/03/2022
 *
 * @author Anton Azarenka
 */
public class UdmReportFilter {

    private Set<Integer> periods = new HashSet<>();
    private Set<String> userNames = new HashSet<>();
    private UdmChannelEnum channel;
    private UdmUsageOriginEnum usageOrigin;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public UdmChannelEnum getChannel() {
        return channel;
    }

    public void setChannel(UdmChannelEnum channel) {
        this.channel = channel;
    }

    public UdmUsageOriginEnum getUsageOrigin() {
        return usageOrigin;
    }

    public void setUsageOrigin(UdmUsageOriginEnum usageOrigin) {
        this.usageOrigin = usageOrigin;
    }

    public Set<Integer> getPeriods() {
        return periods;
    }

    public void setPeriods(Set<Integer> periods) {
        this.periods = periods;
    }

    public Set<String> getUserNames() {
        return userNames;
    }

    public void setUserNames(Set<String> userNames) {
        this.userNames = userNames;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (null == o || getClass() != o.getClass()) {
            return false;
        }
        UdmReportFilter that = (UdmReportFilter) o;
        return new EqualsBuilder()
            .append(periods, that.periods)
            .append(userNames, that.userNames)
            .append(channel, that.channel)
            .append(usageOrigin, that.usageOrigin)
            .append(dateFrom, that.dateFrom)
            .append(dateTo, that.dateTo)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(periods)
            .append(userNames)
            .append(channel)
            .append(usageOrigin)
            .append(dateFrom)
            .append(dateTo)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("periods", periods)
            .append("userNames", userNames)
            .append("channel", channel)
            .append("usageOrigin", usageOrigin)
            .append("dateFrom", dateFrom)
            .append("dateTo", dateTo)
            .toString();
    }
}
