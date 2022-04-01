package com.copyright.rup.dist.foreign.domain.filter;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Class represents a set of filtering criteria for ACL usages.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/31/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclUsageFilter {

    private String usageBatchName;

    /**
     * Default constructor.
     */
    public AclUsageFilter() {
    }

    /**
     * Constructs new filter based on existing.
     *
     * @param filter base filter
     */
    public AclUsageFilter(AclUsageFilter filter) {
        if (null != filter) {
            setUsageBatchName(filter.getUsageBatchName());
        }
    }

    public String getUsageBatchName() {
        return usageBatchName;
    }

    public void setUsageBatchName(String usageBatchName) {
        this.usageBatchName = usageBatchName;
    }

    /**
     * @return {@code true} if filter does not contain any criteria, otherwise {@code false}.
     */
    public boolean isEmpty() {
        return null == usageBatchName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclUsageFilter aclUsageFilter = (AclUsageFilter) obj;
        return new EqualsBuilder()
            .append(usageBatchName, aclUsageFilter.usageBatchName)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(usageBatchName)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("usageBatchName", usageBatchName)
            .toString();
    }
}
