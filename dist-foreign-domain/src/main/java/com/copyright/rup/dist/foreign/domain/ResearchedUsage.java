package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

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
public class ResearchedUsage extends StoredEntity<String> {

    private static final long serialVersionUID = 4370998937952555300L;

    private String usageId;
    private String standardNumber;
    private String standardNumberType;
    private String systemTitle;
    private Long wrWrkInst;

    public String getUsageId() {
        return usageId;
    }

    public void setUsageId(String usageId) {
        this.usageId = usageId;
    }

    public String getStandardNumber() {
        return standardNumber;
    }

    public void setStandardNumber(String standardNumber) {
        this.standardNumber = standardNumber;
    }

    public String getStandardNumberType() {
        return standardNumberType;
    }

    public void setStandardNumberType(String standardNumberType) {
        this.standardNumberType = standardNumberType;
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
            .append(this.standardNumber, that.standardNumber)
            .append(this.standardNumberType, that.standardNumberType)
            .append(this.systemTitle, that.systemTitle)
            .append(this.wrWrkInst, that.wrWrkInst)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(usageId)
            .append(standardNumber)
            .append(standardNumberType)
            .append(systemTitle)
            .append(wrWrkInst)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("usageId", usageId)
            .append("standardNumber", standardNumber)
            .append("standardNumberType", standardNumberType)
            .append("systemTitle", systemTitle)
            .append("wrWrkInst", wrWrkInst)
            .toString();
    }
}
