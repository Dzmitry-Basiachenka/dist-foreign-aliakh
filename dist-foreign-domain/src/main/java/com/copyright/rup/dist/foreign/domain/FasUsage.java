package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represents FAS specific usage details.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/07/2023
 *
 * @author Dzmitry Basiachenka
 */
public class FasUsage {

    private String reportedStandardNumber;

    public String getReportedStandardNumber() {
        return reportedStandardNumber;
    }

    public void setReportedStandardNumber(String reportedStandardNumber) {
        this.reportedStandardNumber = reportedStandardNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        FasUsage that = (FasUsage) obj;
        return new EqualsBuilder()
            .append(this.reportedStandardNumber, that.reportedStandardNumber)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(reportedStandardNumber)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("reportedStandardNumber", reportedStandardNumber)
            .toString();
    }
}
