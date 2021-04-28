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

    private Integer period;
    private String name;
    private UdmUsageOriginEnum usageOrigin;
    private UdmChannelEnum channel;

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
            .append(name, udmBatch.name)
            .append(usageOrigin, udmBatch.usageOrigin)
            .append(channel, udmBatch.channel)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(period)
            .append(name)
            .append(usageOrigin)
            .append(channel)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("period", period)
            .append("name", name)
            .append("usageOrigin", usageOrigin)
            .append("channel", channel)
            .toString();
    }
}
