package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Provides data about action performed with {@link UdmValue}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/01/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmValueAuditItem extends StoredEntity<String> {

    private String valueId;
    private UdmValueActionTypeEnum actionType;
    private String actionReason;

    public String getValueId() {
        return valueId;
    }

    public void setValueId(String valueId) {
        this.valueId = valueId;
    }

    public UdmValueActionTypeEnum getActionType() {
        return actionType;
    }

    public void setActionType(UdmValueActionTypeEnum actionType) {
        this.actionType = actionType;
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
        UdmValueAuditItem that = (UdmValueAuditItem) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(valueId, that.valueId)
            .append(actionType, that.actionType)
            .append(actionReason, that.actionReason)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(valueId)
            .append(actionType)
            .append(actionReason)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("valueId", valueId)
            .append("actionType", actionType)
            .append("actionReason", actionReason)
            .toString();
    }
}
