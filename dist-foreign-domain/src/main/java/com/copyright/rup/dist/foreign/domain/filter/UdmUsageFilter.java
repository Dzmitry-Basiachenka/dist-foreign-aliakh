package com.copyright.rup.dist.foreign.domain.filter;

import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Class represents a set of filtering criteria.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/04/21
 *
 * @author Dzmitry Basiachenka
 */
public class UdmUsageFilter {

    private Set<String> udmBatchesIds = new HashSet<>();
    private UsageStatusEnum usageStatus;
    private UdmUsageOriginEnum udmUsageOrigin;
    private Integer period;

    /**
     * Default constructor.
     */
    public UdmUsageFilter() {
    }

    /**
     * Constructs new filter based on existing.
     *
     * @param filter base filter
     */
    public UdmUsageFilter(UdmUsageFilter filter) {
        if (null != filter) {
            setUdmBatchesIds(filter.getUdmBatchesIds());
            setUsageStatus(filter.getUsageStatus());
            setUdmUsageOrigin(filter.getUdmUsageOrigin());
            setPeriod(filter.getPeriod());
        }
    }

    public Set<String> getUdmBatchesIds() {
        return udmBatchesIds;
    }

    public void setUdmBatchesIds(Set<String> udmBatchesIds) {
        this.udmBatchesIds = udmBatchesIds;
    }

    public UsageStatusEnum getUsageStatus() {
        return usageStatus;
    }

    public void setUsageStatus(UsageStatusEnum usageStatus) {
        this.usageStatus = usageStatus;
    }

    public UdmUsageOriginEnum getUdmUsageOrigin() {
        return udmUsageOrigin;
    }

    public void setUdmUsageOrigin(UdmUsageOriginEnum udmUsageOrigin) {
        this.udmUsageOrigin = udmUsageOrigin;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    /**
     * @return {@code true} if filter does not contain any criteria, otherwise {@code false}.
     */
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(udmBatchesIds)
            && null == usageStatus
            && null == udmUsageOrigin
            && null == period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (null == o || getClass() != o.getClass()) {
            return false;
        }
        UdmUsageFilter that = (UdmUsageFilter) o;
        return new EqualsBuilder()
            .append(udmBatchesIds, that.udmBatchesIds)
            .append(usageStatus, that.usageStatus)
            .append(udmUsageOrigin, that.udmUsageOrigin)
            .append(period, that.period)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(udmBatchesIds)
            .append(usageStatus)
            .append(udmUsageOrigin)
            .append(period)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("udmBatchesIds", udmBatchesIds)
            .append("usageStatus", usageStatus)
            .append("udmUsageOrigin", udmUsageOrigin)
            .append("period", period)
            .toString();
    }
}
