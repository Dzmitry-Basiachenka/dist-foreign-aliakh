package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents action reason of UDM usage.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/30/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmActionReason extends StoredEntity<String> {

    private String actionReason;

    /**
     * Constructor.
     */
    public UdmActionReason() {
    }

    /**
     * Constructor.
     *
     * @param id           id
     * @param actionReason action reason.
     */
    public UdmActionReason(String id, String actionReason) {
        this.setId(id);
        this.actionReason = actionReason;
    }

    public String getActionReason() {
        return actionReason;
    }

    public void setActionReason(String actionReason) {
        this.actionReason = actionReason;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        UdmActionReason that = (UdmActionReason) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(actionReason, that.actionReason)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(actionReason)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("actionReason", actionReason)
            .toString();
    }
}
