package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.common.domain.StoredEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents Liabilities Summary by Rightsholder and Work Report record.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 10/15/2020
 *
 * @author Aliaksandr Liakh
 */
public class LiabilitiesSummaryByRhAndWorkReportDto extends StoredEntity<String> {

    private Long rhAccountNumber;
    private String rhName;
    private String wrWrkInst;
    private String workTitle;
    private BigDecimal grossAmount;
    private BigDecimal serviceFeeAmount;
    private BigDecimal netAmount;

    public Long getRhAccountNumber() {
        return rhAccountNumber;
    }

    public void setRhAccountNumber(Long rhAccountNumber) {
        this.rhAccountNumber = rhAccountNumber;
    }

    public String getRhName() {
        return rhName;
    }

    public void setRhName(String rhName) {
        this.rhName = rhName;
    }

    public String getWrWrkInst() {
        return wrWrkInst;
    }

    public void setWrWrkInst(String wrWrkInst) {
        this.wrWrkInst = wrWrkInst;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    public BigDecimal getServiceFeeAmount() {
        return serviceFeeAmount;
    }

    public void setServiceFeeAmount(BigDecimal serviceFeeAmount) {
        this.serviceFeeAmount = serviceFeeAmount;
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
        LiabilitiesSummaryByRhAndWorkReportDto that = (LiabilitiesSummaryByRhAndWorkReportDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(rhAccountNumber, that.rhAccountNumber)
            .append(rhName, that.rhName)
            .append(wrWrkInst, that.wrWrkInst)
            .append(workTitle, that.workTitle)
            .append(grossAmount, that.grossAmount)
            .append(serviceFeeAmount, that.serviceFeeAmount)
            .append(netAmount, that.netAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(rhAccountNumber)
            .append(rhName)
            .append(wrWrkInst)
            .append(workTitle)
            .append(grossAmount)
            .append(serviceFeeAmount)
            .append(netAmount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("rhAccountNumber", rhAccountNumber)
            .append("rhName", rhName)
            .append("wrWrkInst", wrWrkInst)
            .append("workTitle", workTitle)
            .append("grossAmount", grossAmount)
            .append("serviceFeeAmount", serviceFeeAmount)
            .append("netAmount", netAmount)
            .toString();
    }
}
