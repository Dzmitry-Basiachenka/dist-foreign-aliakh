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

    private static final long serialVersionUID = -6062845774739288570L;

    private String reason;

    /**
     * Constructor.
     */
    public UdmIneligibleReason() {
    }

    /**
     * Constructor.
     *
     * @param id     ineligible reason id
     * @param reason ineligible reason
     */
    public UdmIneligibleReason(String id, String reason) {
        super.setId(id);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
            .append(reason, that.reason)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(reason)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("reason", reason)
            .toString();
    }
}
