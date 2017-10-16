package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Provides data about action performed with {@link Usage}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/16/2017
 *
 * @author Uladzislau_Shalamitski
 */
public class UsageAuditItem extends StoredEntity<String> {

    private String usageId;
    private String scenarioId;
    private String scenarioName;
    private UsageActionTypeEnum actionType;
    private String actionReason;

    public String getUsageId() {
        return usageId;
    }

    public void setUsageId(String usageId) {
        this.usageId = usageId;
    }

    public String getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(String scenarioId) {
        this.scenarioId = scenarioId;
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public UsageActionTypeEnum getActionType() {
        return actionType;
    }

    public void setActionType(UsageActionTypeEnum actionType) {
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
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UsageAuditItem that = (UsageAuditItem) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(usageId, that.usageId)
            .append(scenarioId, that.scenarioId)
            .append(scenarioName, that.scenarioName)
            .append(actionType, that.actionType)
            .append(actionReason, that.actionReason)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(usageId)
            .append(scenarioId)
            .append(scenarioName)
            .append(actionType)
            .append(actionReason)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("usageId", usageId)
            .append("scenarioId", scenarioId)
            .append("scenarioName", scenarioName)
            .append("actionType", actionType)
            .append("actionReason", actionReason)
            .toString();
    }
}
