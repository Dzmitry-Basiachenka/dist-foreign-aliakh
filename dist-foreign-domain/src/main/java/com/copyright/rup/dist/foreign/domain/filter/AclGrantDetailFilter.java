package com.copyright.rup.dist.foreign.domain.filter;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Class represents a set of filtering criteria for ACL grant details.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/28/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclGrantDetailFilter {

    private Set<String> grantSetNames = new HashSet<>();

    /**
     * Default constructor.
     */
    public AclGrantDetailFilter() {
    }

    /**
     * Constructs new filter based on existing.
     *
     * @param filter base filter
     */
    public AclGrantDetailFilter(AclGrantDetailFilter filter) {
        if (null != filter) {
            setGrantSetNames(filter.getGrantSetNames());
        }
    }

    public Set<String> getGrantSetNames() {
        return grantSetNames;
    }

    public void setGrantSetNames(Set<String> grantSetNames) {
        this.grantSetNames = grantSetNames;
    }

    /**
     * @return {@code true} if filter does not contain any criteria, otherwise {@code false}.
     */
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(grantSetNames);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclGrantDetailFilter that = (AclGrantDetailFilter) obj;
        return new EqualsBuilder()
            .append(grantSetNames, that.grantSetNames)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(grantSetNames)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("grantSetNames", grantSetNames)
            .toString();
    }
}
