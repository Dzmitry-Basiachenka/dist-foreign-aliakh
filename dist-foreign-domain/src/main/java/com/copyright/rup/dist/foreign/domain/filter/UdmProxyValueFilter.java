package com.copyright.rup.dist.foreign.domain.filter;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class represents a set of filtering criteria for UDM proxy values.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Uladzislau Shalamitski
 */
public class UdmProxyValueFilter {

    private Set<Integer> periods = new HashSet<>();
    private Set<String> pubTypeNames = new HashSet<>();

    /**
     * Default constructor.
     */
    public UdmProxyValueFilter() {
    }

    /**
     * Constructs new filter based on existing.
     *
     * @param filter base filter
     */
    public UdmProxyValueFilter(UdmProxyValueFilter filter) {
        if (Objects.nonNull(filter)) {
            this.periods = filter.getPeriods();
            this.pubTypeNames = filter.getPubTypeNames();
        }
    }

    public Set<Integer> getPeriods() {
        return periods;
    }

    public void setPeriods(Set<Integer> periods) {
        this.periods = periods;
    }

    public Set<String> getPubTypeNames() {
        return pubTypeNames;
    }

    public void setPubTypeNames(Set<String> pubTypeNames) {
        this.pubTypeNames = pubTypeNames;
    }

    /**
     * @return {@code true} if filter does not contain any criteria, otherwise {@code false}.
     */
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(periods) && CollectionUtils.isEmpty(pubTypeNames);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        UdmProxyValueFilter that = (UdmProxyValueFilter) obj;
        return new EqualsBuilder()
            .append(periods, that.periods)
            .append(pubTypeNames, that.pubTypeNames)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(periods)
            .append(pubTypeNames)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("periods", periods)
            .append("pubTypeNames", pubTypeNames)
            .toString();
    }
}
