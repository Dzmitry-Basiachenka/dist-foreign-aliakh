package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Provides data about action performed with {@link Scenario}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 12/13/2017
 *
 * @author Uladzislau_Shalamitski
 */
public class ScenarioAuditItem extends StoredEntity<String> {

    private static final long serialVersionUID = -7130115473665334300L;

    private String scenarioId;
    private ScenarioActionTypeEnum actionType;
    private String actionReason;

    public String getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(String scenarioId) {
        this.scenarioId = scenarioId;
    }

    public ScenarioActionTypeEnum getActionType() {
        return actionType;
    }

    public void setActionType(ScenarioActionTypeEnum actionType) {
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
        ScenarioAuditItem that = (ScenarioAuditItem) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(scenarioId, that.scenarioId)
            .append(actionType, that.actionType)
            .append(actionReason, that.actionReason)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(scenarioId)
            .append(actionType)
            .append(actionReason)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("scenarioId", scenarioId)
            .append("actionType", actionType)
            .append("actionReason", actionReason)
            .toString();
    }
}
