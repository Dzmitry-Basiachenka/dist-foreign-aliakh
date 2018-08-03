package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represents researched usage detail.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 03/27/2018
 *
 * @author Aliaksandr Liakh
 */
public class ResearchedUsage {

    private String usageId;
    private String systemTitle;
    private Long wrWrkInst;

    public String getUsageId() {
        return usageId;
    }

    public void setUsageId(String usageId) {
        this.usageId = usageId;
    }

    public String getSystemTitle() {
        return systemTitle;
    }

    public void setSystemTitle(String systemTitle) {
        this.systemTitle = systemTitle;
    }

    public Long getWrWrkInst() {
        return wrWrkInst;
    }

    public void setWrWrkInst(Long wrWrkInst) {
        this.wrWrkInst = wrWrkInst;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        ResearchedUsage that = (ResearchedUsage) obj;
        return new EqualsBuilder()
            .append(this.usageId, that.usageId)
            .append(this.systemTitle, that.systemTitle)
            .append(this.wrWrkInst, that.wrWrkInst)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(usageId)
            .append(systemTitle)
            .append(wrWrkInst)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("usageId", usageId)
            .append("systemTitle", systemTitle)
            .append("wrWrkInst", wrWrkInst)
            .toString();
    }
}
