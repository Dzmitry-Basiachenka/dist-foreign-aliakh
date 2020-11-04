package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents SAL Undistributed Liabilities Reconciliation Report.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 11/04/2020
 *
 * @author Dzmitry Basiachenka
 */
public class SalUndistributedLiabilitiesReportDto extends StoredEntity<String> {

    private static final BigDecimal DEFAULT_AMOUNT = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);

    private LocalDate dateReceived;
    private String fundPoolName;
    private BigDecimal grossAmount = DEFAULT_AMOUNT;
    private BigDecimal serviceFeeAmount = DEFAULT_AMOUNT;
    private BigDecimal netAmount = DEFAULT_AMOUNT;

    public LocalDate getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(LocalDate dateReceived) {
        this.dateReceived = dateReceived;
    }

    public String getFundPoolName() {
        return fundPoolName;
    }

    public void setFundPoolName(String fundPoolName) {
        this.fundPoolName = fundPoolName;
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
        SalUndistributedLiabilitiesReportDto that = (SalUndistributedLiabilitiesReportDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(dateReceived, that.dateReceived)
            .append(fundPoolName, that.fundPoolName)
            .append(grossAmount, that.grossAmount)
            .append(serviceFeeAmount, that.serviceFeeAmount)
            .append(netAmount, that.netAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(dateReceived)
            .append(fundPoolName)
            .append(grossAmount)
            .append(serviceFeeAmount)
            .append(netAmount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("dateReceived", dateReceived)
            .append("fundPoolName", fundPoolName)
            .append("grossAmount", grossAmount)
            .append("serviceFeeAmount", serviceFeeAmount)
            .append("netAmount", netAmount)
            .toString();
    }
}
