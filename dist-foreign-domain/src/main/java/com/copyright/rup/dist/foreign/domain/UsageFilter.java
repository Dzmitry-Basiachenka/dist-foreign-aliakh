package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Set;

/**
 * Class represents a set of filtering criteria.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 1/19/17
 *
 * @author Aliaksandr Radkevich
 */
public class UsageFilter {

    private Set<Long> rhAccountNumbers;
    private Set<String> usageBatchesIds;

    /**
     * @return a set of rightsholders account numbers.
     */
    public Set<Long> getRhAccountNumbers() {
        return rhAccountNumbers;
    }

    /**
     * Sets rightsholders account numbers.
     *
     * @param rhAccountNumbers rightsholders account numbers
     */
    public void setRhAccountNumbers(Set<Long> rhAccountNumbers) {
        this.rhAccountNumbers = rhAccountNumbers;
    }

    /**
     * @return a set of usage batches ids.
     */
    public Set<String> getUsageBatchesIds() {
        return usageBatchesIds;
    }

    /**
     * Sets usage batches ids.
     *
     * @param usageBatchesIds usage batches ids
     */
    public void setUsageBatchesIds(Set<String> usageBatchesIds) {
        this.usageBatchesIds = usageBatchesIds;
    }

    /**
     * @return {@code true} if filter does not contain any criteria, otherwise {@code false}.
     */
    public boolean isEmpty() {
        return (null == rhAccountNumbers || rhAccountNumbers.isEmpty())
            && (null == usageBatchesIds || usageBatchesIds.isEmpty());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        UsageFilter that = (UsageFilter) obj;
        return new EqualsBuilder()
            .append(this.rhAccountNumbers, that.rhAccountNumbers)
            .append(this.usageBatchesIds, that.usageBatchesIds)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(rhAccountNumbers)
            .append(usageBatchesIds)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("rhAccountNumbers", rhAccountNumbers)
            .append("usageBatchesIds", usageBatchesIds)
            .toString();
    }
}
