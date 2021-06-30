package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents ineligible reason of UDM usage.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/30/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmIneligibleReason extends StoredEntity<String> {

    private String ineligibleReason;

    /**
     * Constructor.
     */
    public UdmIneligibleReason() {
    }

    /**
     * Constructor.
     *
     * @param id               id
     * @param ineligibleReason ineligible reason
     */
    public UdmIneligibleReason(String id, String ineligibleReason) {
        this.setId(id);
        this.ineligibleReason = ineligibleReason;
    }

    public String getIneligibleReason() {
        return ineligibleReason;
    }

    public void setIneligibleReason(String ineligibleReason) {
        this.ineligibleReason = ineligibleReason;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        UdmIneligibleReason that = (UdmIneligibleReason) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(ineligibleReason, that.ineligibleReason)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(ineligibleReason)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("ineligibleReason", ineligibleReason)
            .toString();
    }
}
