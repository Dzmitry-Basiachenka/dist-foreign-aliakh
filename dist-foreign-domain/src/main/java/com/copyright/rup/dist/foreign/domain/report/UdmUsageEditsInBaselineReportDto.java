package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents UDM Usage Edits in Baseline Report record.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/10/2022
 *
 * @author Aliaksandr Liakh
 */
public class UdmUsageEditsInBaselineReportDto extends StoredEntity<String> {

    private static final long serialVersionUID = -1019377321993347108L;

    private String usageId;
    private UsageActionTypeEnum actionType;
    private String actionReason;
    private Long wrWrkInst;
    private String systemTitle;
    private Integer period;

    public String getUsageId() {
        return usageId;
    }

    public void setUsageId(String usageId) {
        this.usageId = usageId;
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

    public Long getWrWrkInst() {
        return wrWrkInst;
    }

    public void setWrWrkInst(Long wrWrkInst) {
        this.wrWrkInst = wrWrkInst;
    }

    public String getSystemTitle() {
        return systemTitle;
    }

    public void setSystemTitle(String systemTitle) {
        this.systemTitle = systemTitle;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
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
        UdmUsageEditsInBaselineReportDto that = (UdmUsageEditsInBaselineReportDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(usageId, that.usageId)
            .append(actionType, that.actionType)
            .append(actionReason, that.actionReason)
            .append(wrWrkInst, that.wrWrkInst)
            .append(systemTitle, that.systemTitle)
            .append(period, that.period)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(usageId)
            .append(actionType)
            .append(actionReason)
            .append(wrWrkInst)
            .append(systemTitle)
            .append(period)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("usageId", usageId)
            .append("actionType", actionType)
            .append("actionReason", actionReason)
            .append("wrWrkInst", wrWrkInst)
            .append("systemTitle", systemTitle)
            .append("period", period)
            .toString();
    }
}
