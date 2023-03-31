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
    private int ntsDetailsCount;
    private BigDecimal batchGrossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private BigDecimal ntsDetailsGrossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;

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

    public int getNtsDetailsCount() {
        return ntsDetailsCount;
    }

    public void setNtsDetailsCount(int ntsDetailsCount) {
        this.ntsDetailsCount = ntsDetailsCount;
    }

    public BigDecimal getBatchGrossAmount() {
        return batchGrossAmount;
    }

    public void setBatchGrossAmount(BigDecimal batchGrossAmount) {
        this.batchGrossAmount = batchGrossAmount;
    }

    public BigDecimal getNtsDetailsGrossAmount() {
        return ntsDetailsGrossAmount;
    }

    public void setNtsDetailsGrossAmount(BigDecimal ntsDetailsGrossAmount) {
        this.ntsDetailsGrossAmount = ntsDetailsGrossAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (null == o || getClass() != o.getClass()) {
            return false;
        }
        NtsWithDrawnBatchSummaryReportDto that = (NtsWithDrawnBatchSummaryReportDto) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(ntsDetailsCount, that.ntsDetailsCount)
            .append(batchName, that.batchName)
            .append(rroAccountNumber, that.rroAccountNumber)
            .append(rroName, that.rroName)
            .append(paymentDate, that.paymentDate)
            .append(batchGrossAmount, that.batchGrossAmount)
            .append(ntsDetailsGrossAmount, that.ntsDetailsGrossAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(batchName)
            .append(rroAccountNumber)
            .append(rroName)
            .append(paymentDate)
            .append(ntsDetailsCount)
            .append(batchGrossAmount)
            .append(ntsDetailsGrossAmount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("batchName", batchName)
            .append("rroAccountNumber", rroAccountNumber)
            .append("rroName", rroName)
            .append("paymentDate", paymentDate)
            .append("ntsDetailsCount", ntsDetailsCount)
            .append("batchGrossAmount", batchGrossAmount)
            .append("ntsDetailsGrossAmount", ntsDetailsGrossAmount)
            .toString();
    }
}
