package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.foreign.domain.FdaConstants;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents NTS Withdrawn Batch Summary Report record.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 10/30/19
 *
 * @author Anton Azarenka
 */
public class NtsWithDrawnBatchSummaryReportDto extends StoredEntity<String> {

    private String batchName;
    private Long rroAccountNumber;
    private String rroName;
    private LocalDate paymentDate;
    private BigDecimal batchGrossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private int ntsDetailsCount;
    private BigDecimal ntsDetailsGrossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private int toBeDistributedDetailsCount;
    private BigDecimal toBeDistributedDetailsGrossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public Long getRroAccountNumber() {
        return rroAccountNumber;
    }

    public void setRroAccountNumber(Long rroAccountNumber) {
        this.rroAccountNumber = rroAccountNumber;
    }

    public String getRroName() {
        return rroName;
    }

    public void setRroName(String rroName) {
        this.rroName = rroName;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getBatchGrossAmount() {
        return batchGrossAmount;
    }

    public void setBatchGrossAmount(BigDecimal batchGrossAmount) {
        this.batchGrossAmount = batchGrossAmount;
    }

    public int getNtsDetailsCount() {
        return ntsDetailsCount;
    }

    public void setNtsDetailsCount(int ntsDetailsCount) {
        this.ntsDetailsCount = ntsDetailsCount;
    }

    public BigDecimal getNtsDetailsGrossAmount() {
        return ntsDetailsGrossAmount;
    }

    public void setNtsDetailsGrossAmount(BigDecimal ntsDetailsGrossAmount) {
        this.ntsDetailsGrossAmount = ntsDetailsGrossAmount;
    }

    public int getToBeDistributedDetailsCount() {
        return toBeDistributedDetailsCount;
    }

    public void setToBeDistributedDetailsCount(int toBeDistributedDetailsCount) {
        this.toBeDistributedDetailsCount = toBeDistributedDetailsCount;
    }

    public BigDecimal getToBeDistributedDetailsGrossAmount() {
        return toBeDistributedDetailsGrossAmount;
    }

    public void setToBeDistributedDetailsGrossAmount(BigDecimal toBeDistributedDetailsGrossAmount) {
        this.toBeDistributedDetailsGrossAmount = toBeDistributedDetailsGrossAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        NtsWithDrawnBatchSummaryReportDto that = (NtsWithDrawnBatchSummaryReportDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(batchName, that.batchName)
            .append(rroAccountNumber, that.rroAccountNumber)
            .append(rroName, that.rroName)
            .append(paymentDate, that.paymentDate)
            .append(batchGrossAmount, that.batchGrossAmount)
            .append(ntsDetailsCount, that.ntsDetailsCount)
            .append(ntsDetailsGrossAmount, that.ntsDetailsGrossAmount)
            .append(toBeDistributedDetailsCount, that.toBeDistributedDetailsCount)
            .append(toBeDistributedDetailsGrossAmount, that.toBeDistributedDetailsGrossAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(batchName)
            .append(rroAccountNumber)
            .append(rroName)
            .append(paymentDate)
            .append(batchGrossAmount)
            .append(ntsDetailsCount)
            .append(ntsDetailsGrossAmount)
            .append(toBeDistributedDetailsCount)
            .append(toBeDistributedDetailsGrossAmount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("batchName", batchName)
            .append("rroAccountNumber", rroAccountNumber)
            .append("rroName", rroName)
            .append("paymentDate", paymentDate)
            .append("batchGrossAmount", batchGrossAmount)
            .append("ntsDetailsCount", ntsDetailsCount)
            .append("ntsDetailsGrossAmount", ntsDetailsGrossAmount)
            .append("toBeDistributedDetailsCount", toBeDistributedDetailsCount)
            .append("toBeDistributedDetailsGrossAmount", toBeDistributedDetailsGrossAmount)
            .toString();
    }
}
