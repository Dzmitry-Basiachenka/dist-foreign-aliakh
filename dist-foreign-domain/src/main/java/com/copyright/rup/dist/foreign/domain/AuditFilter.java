package com.copyright.rup.dist.foreign.domain;

import com.google.common.collect.Sets;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Set;

/**
 * Filter fo Audit.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/12/18
 *
 * @author Aliaksandr Radkevich
 */
public class AuditFilter {

    private Set<Long> rhAccountNumbers = Sets.newHashSet();
    private Set<String> batchesIds = Sets.newHashSet();
    private Set<UsageStatusEnum> statuses = Sets.newHashSet();
    private String searchValue;

    /**
     * Default constructor.
     */
    public AuditFilter() {
        // default constructor
    }

    /**
     * Constructor.
     *
     * @param filter {@link AuditFilter}
     */
    public AuditFilter(AuditFilter filter) {
        setStatuses(filter.getStatuses());
        setBatchesIds(filter.getBatchesIds());
        setRhAccountNumbers(filter.getRhAccountNumbers());
        setSearchValue(filter.getSearchValue());
    }

    public Set<Long> getRhAccountNumbers() {
        return rhAccountNumbers;
    }

    public void setRhAccountNumbers(Set<Long> rhAccountNumbers) {
        this.rhAccountNumbers = rhAccountNumbers;
    }

    public Set<String> getBatchesIds() {
        return batchesIds;
    }

    public void setBatchesIds(Set<String> batchesIds) {
        this.batchesIds = batchesIds;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public Set<UsageStatusEnum> getStatuses() {
        return statuses;
    }

    public void setStatuses(Set<UsageStatusEnum> statuses) {
        this.statuses = statuses;
    }

    public boolean isEmpty() {
        return rhAccountNumbers.isEmpty() && batchesIds.isEmpty() && statuses.isEmpty()
            && StringUtils.isBlank(searchValue);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        AuditFilter that = (AuditFilter) obj;
        return new EqualsBuilder()
            .append(this.rhAccountNumbers, that.rhAccountNumbers)
            .append(this.batchesIds, that.batchesIds)
            .append(this.statuses, that.statuses)
            .append(this.searchValue, that.searchValue)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(rhAccountNumbers)
            .append(batchesIds)
            .append(statuses)
            .append(searchValue)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("rhAccountNumbers", rhAccountNumbers)
            .append("batchesIds", batchesIds)
            .append("statuses", statuses)
            .append("searchValue", searchValue)
            .toString();
    }
}
