package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents usage statistic.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 03/06/2019
 *
 * @author Aliaksandr Liakh
 */
public class UsageStatistic {

    private String usageId;
    private UsageStatusEnum status;
    private int matchingMs;
    private int rightsMs;
    private int eligibilityMs;

    public String getUsageId() {
        return usageId;
    }

    public void setUsageId(String usageId) {
        this.usageId = usageId;
    }

    public UsageStatusEnum getStatus() {
        return status;
    }

    public void setStatus(UsageStatusEnum status) {
        this.status = status;
    }

    public int getMatchingMs() {
        return matchingMs;
    }

    public void setMatchingMs(int matchingMs) {
        this.matchingMs = matchingMs;
    }

    public int getRightsMs() {
        return rightsMs;
    }

    public void setRightsMs(int rightsMs) {
        this.rightsMs = rightsMs;
    }

    public int getEligibilityMs() {
        return eligibilityMs;
    }

    public void setEligibilityMs(int eligibilityMs) {
        this.eligibilityMs = eligibilityMs;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        UsageStatistic that = (UsageStatistic) obj;
        return new EqualsBuilder()
            .append(matchingMs, that.matchingMs)
            .append(rightsMs, that.rightsMs)
            .append(eligibilityMs, that.eligibilityMs)
            .append(usageId, that.usageId)
            .append(status, that.status)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(usageId)
            .append(status)
            .append(matchingMs)
            .append(rightsMs)
            .append(eligibilityMs)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("usageId", usageId)
            .append("status", status)
            .append("matchingMs", matchingMs)
            .append("rightsMs", rightsMs)
            .append("eligibilityMs", eligibilityMs)
            .toString();
    }
}
