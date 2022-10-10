package com.copyright.rup.dist.foreign.domain.report;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents total amounts for ACL reports.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/10/2022
 *
 * @author Anton Azarenka
 */
public class AclReportTotalAmountsDto {

    private BigDecimal grossAmount;
    private BigDecimal netAmount;

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclReportTotalAmountsDto that = (AclReportTotalAmountsDto) obj;
        return new EqualsBuilder()
            .append(grossAmount, that.grossAmount)
            .append(netAmount, that.netAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(grossAmount)
            .append(netAmount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("grossAmount", grossAmount)
            .append("netAmount", netAmount)
            .toString();
    }
}
