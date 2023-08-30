package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents ACL publication type domain object.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/13/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclPublicationType extends PublicationType {

    private Integer period;

    /**
     * Constructor.
     */
    public AclPublicationType() {
    }

    /**
     * Constructor.
     *
     * @param pubType instance of {@link AclPublicationType}
     */
    public AclPublicationType(AclPublicationType pubType) {
        super(pubType);
        setPeriod(pubType.getPeriod());
    }

    public Integer getPeriod() {
        return period;
    }

    public final void setPeriod(Integer period) {
        this.period = period;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclPublicationType that = (AclPublicationType) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(period, that.period)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(period)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("period", period)
            .toString();
    }
}
