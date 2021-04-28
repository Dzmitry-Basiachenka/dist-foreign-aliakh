package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents UDM usage batch.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 04/26/21
 *
 * @author Anton Azarenka
 */
public class UdmBatch extends StoredEntity<String> {

    private String period;
    private String udmBatchName;
    private UdmUsageOriginEnum usageOrigin;
    private UdmChannelEnum channel;

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getUdmBatchName() {
        return udmBatchName;
    }

    public void setUdmBatchName(String udmBatchName) {
        this.udmBatchName = udmBatchName;
    }

    public UdmUsageOriginEnum getUsageOrigin() {
        return usageOrigin;
    }

    public void setUsageOrigin(UdmUsageOriginEnum usageOrigin) {
        this.usageOrigin = usageOrigin;
    }

    public UdmChannelEnum getChannel() {
        return channel;
    }

    public void setChannel(UdmChannelEnum channel) {
        this.channel = channel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (null == o || getClass() != o.getClass()) {
            return false;
        }
        UdmBatch udmBatch = (UdmBatch) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(period, udmBatch.period)
            .append(udmBatchName, udmBatch.udmBatchName)
            .append(usageOrigin, udmBatch.usageOrigin)
            .append(channel, udmBatch.channel)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(period)
            .append(udmBatchName)
            .append(usageOrigin)
            .append(channel)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("period", period)
            .append("udmBatchName", udmBatchName)
            .append("usageOrigin", usageOrigin)
            .append("channel", channel)
            .toString();
    }
}
