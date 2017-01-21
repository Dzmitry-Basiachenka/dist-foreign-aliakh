package com.copyright.rup.dist.foreign;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Set;

/**
 * Class represents a set of filtering criteria.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 1/19/17
 *
 * @author Aliaksandr Radkevich
 */
public class UsageFilter {

    private Set<Long> rightsholdersAccountNumbers;
    private Set<String> usageBatchesIds;

    /**
     * @return a set of rightsholders account numbers.
     */
    public Set<Long> getRightsholdersAccountNumbers() {
        return rightsholdersAccountNumbers;
    }

    /**
     * Sets rightsholders account numbers.
     *
     * @param rightsholdersAccountNumbers rightsholders account numbers
     */
    public void setRightsholdersAccountNumbers(Set<Long> rightsholdersAccountNumbers) {
        this.rightsholdersAccountNumbers = rightsholdersAccountNumbers;
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
        return (null == rightsholdersAccountNumbers || rightsholdersAccountNumbers.isEmpty())
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
            .append(this.rightsholdersAccountNumbers, that.rightsholdersAccountNumbers)
            .append(this.usageBatchesIds, that.usageBatchesIds)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(rightsholdersAccountNumbers)
            .append(usageBatchesIds)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("rightsholdersAccountNumbers", rightsholdersAccountNumbers)
            .append("usageBatchesIds", usageBatchesIds)
            .toString();
    }
}
