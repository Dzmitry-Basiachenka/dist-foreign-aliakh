package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.foreign.domain.FdaConstants;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents research status report record.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 05/30/18
 *
 * @author Ihar Suvorau
 */
public class ResearchStatusReportDto extends StoredEntity<String> {

    private String batchName;
    private String rroName;
    private Long rroAccountNumber;
    private LocalDate paymentDate;
    private int workNotFoundDetailsCount;
    private int workResearchDetailsCount;
    private int sendForRaDetailsCount;
    private int rhNotFoundDetailsCount;
    private BigDecimal workNotFoundGrossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private BigDecimal workResearchGrossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private BigDecimal sendForRaGrossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private BigDecimal rhNotFoundGrossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getRroName() {
        return rroName;
    }

    public void setRroName(String rroName) {
        this.rroName = rroName;
    }

    public Long getRroAccountNumber() {
        return rroAccountNumber;
    }

    public void setRroAccountNumber(Long rroAccountNumber) {
        this.rroAccountNumber = rroAccountNumber;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public int getWorkNotFoundDetailsCount() {
        return workNotFoundDetailsCount;
    }

    public void setWorkNotFoundDetailsCount(int workNotFoundDetailsCount) {
        this.workNotFoundDetailsCount = workNotFoundDetailsCount;
    }

    public BigDecimal getWorkNotFoundGrossAmount() {
        return workNotFoundGrossAmount;
    }

    public void setWorkNotFoundGrossAmount(BigDecimal workNotFoundGrossAmount) {
        this.workNotFoundGrossAmount = workNotFoundGrossAmount;
    }

    public int getWorkResearchDetailsCount() {
        return workResearchDetailsCount;
    }

    public void setWorkResearchDetailsCount(int workResearchDetailsCount) {
        this.workResearchDetailsCount = workResearchDetailsCount;
    }

    public BigDecimal getWorkResearchGrossAmount() {
        return workResearchGrossAmount;
    }

    public void setWorkResearchGrossAmount(BigDecimal workResearchGrossAmount) {
        this.workResearchGrossAmount = workResearchGrossAmount;
    }

    public int getSendForRaDetailsCount() {
        return sendForRaDetailsCount;
    }

    public void setSendForRaDetailsCount(int sendForRaDetailsCount) {
        this.sendForRaDetailsCount = sendForRaDetailsCount;
    }

    public BigDecimal getSendForRaGrossAmount() {
        return sendForRaGrossAmount;
    }

    public void setSendForRaGrossAmount(BigDecimal sendForRaGrossAmount) {
        this.sendForRaGrossAmount = sendForRaGrossAmount;
    }

    public int getRhNotFoundDetailsCount() {
        return rhNotFoundDetailsCount;
    }

    public void setRhNotFoundDetailsCount(int rhNotFoundDetailsCount) {
        this.rhNotFoundDetailsCount = rhNotFoundDetailsCount;
    }

    public BigDecimal getRhNotFoundGrossAmount() {
        return rhNotFoundGrossAmount;
    }

    public void setRhNotFoundGrossAmount(BigDecimal rhNotFoundGrossAmount) {
        this.rhNotFoundGrossAmount = rhNotFoundGrossAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ResearchStatusReportDto that = (ResearchStatusReportDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(batchName, that.batchName)
            .append(rroName, that.rroName)
            .append(rroAccountNumber, that.rroAccountNumber)
            .append(paymentDate, that.paymentDate)
            .append(workNotFoundDetailsCount, that.workNotFoundDetailsCount)
            .append(workNotFoundGrossAmount, that.workNotFoundGrossAmount)
            .append(workResearchDetailsCount, that.workResearchDetailsCount)
            .append(workResearchGrossAmount, that.workResearchGrossAmount)
            .append(sendForRaDetailsCount, that.sendForRaDetailsCount)
            .append(sendForRaGrossAmount, that.sendForRaGrossAmount)
            .append(rhNotFoundDetailsCount, that.rhNotFoundDetailsCount)
            .append(rhNotFoundGrossAmount, that.rhNotFoundGrossAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(batchName)
            .append(rroName)
            .append(rroAccountNumber)
            .append(paymentDate)
            .append(workNotFoundDetailsCount)
            .append(workNotFoundGrossAmount)
            .append(workResearchDetailsCount)
            .append(workResearchGrossAmount)
            .append(sendForRaDetailsCount)
            .append(sendForRaGrossAmount)
            .append(rhNotFoundDetailsCount)
            .append(rhNotFoundGrossAmount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("batchName", batchName)
            .append("rroName", rroName)
            .append("rroAccountNumber", rroAccountNumber)
            .append("paymentDate", paymentDate)
            .append("workNotFoundDetailsCount", workNotFoundDetailsCount)
            .append("workNotFoundGrossAmount", workNotFoundGrossAmount)
            .append("workResearchDetailsCount", workResearchDetailsCount)
            .append("workResearchGrossAmount", workResearchGrossAmount)
            .append("sendForRaDetailsCount", sendForRaDetailsCount)
            .append("sendForRaGrossAmount", sendForRaGrossAmount)
            .append("rhNotFoundDetailsCount", rhNotFoundDetailsCount)
            .append("rhNotFoundGrossAmount", rhNotFoundGrossAmount)
            .toString();
    }
}
