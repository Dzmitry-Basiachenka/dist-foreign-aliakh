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
    private int matching;
    private int rights;
    private int eligibility;

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

    public int getMatching() {
        return matching;
    }

    public void setMatching(int matching) {
        this.matching = matching;
    }

    public int getRights() {
        return rights;
    }

    public void setRights(int rights) {
        this.rights = rights;
    }

    public int getEligibility() {
        return eligibility;
    }

    public void setEligibility(int eligibility) {
        this.eligibility = eligibility;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UsageStatistic)) {
            return false;
        }
        UsageStatistic that = (UsageStatistic) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(usageId, that.usageId)
            .append(status, that.status)
            .append(matching, that.matching)
            .append(rights, that.rights)
            .append(eligibility, that.eligibility)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(usageId)
            .append(status)
            .append(matching)
            .append(rights)
            .append(eligibility)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("usageId", usageId)
            .append("status", status)
            .append("matching", matching)
            .append("rights", rights)
            .append("eligibility", eligibility)
            .toString();
    }
}
