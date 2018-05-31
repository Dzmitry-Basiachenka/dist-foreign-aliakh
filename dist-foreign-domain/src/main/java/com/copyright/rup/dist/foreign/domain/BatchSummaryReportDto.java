package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents batch summary report record.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 05/31/18
 *
 * @author Uladzislau Shalamitski
 */
public class BatchSummaryReportDto extends StoredEntity<String> {

    private static final BigDecimal DEFAULT_AMOUNT = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);

    private String batchName;
    private String rroName;
    private Long rroAccountNumber;
    private LocalDate paymentDate;
    private BigDecimal grossAmount = DEFAULT_AMOUNT;
    private int nonEligibleDetailsCount;
    private BigDecimal nonEligibleDetailsGrossAmount = DEFAULT_AMOUNT;
    private int ntsDetailsCount;
    private BigDecimal ntsDetailsGrossAmount = DEFAULT_AMOUNT;
    private int fasAndClaFasEligibleDetailsCount;
    private BigDecimal fasAndClaFasEligibleDetailsGrossAmount = DEFAULT_AMOUNT;
    private int scenariosDetailsCount;
    private BigDecimal scenariosDetailsGrossAmount = DEFAULT_AMOUNT;
    private BigDecimal scenariosDetailsNetAmount = DEFAULT_AMOUNT;
    private int returnToClaDetailsCount;
    private BigDecimal returnToClaDetailsGrossAmount = DEFAULT_AMOUNT;

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

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    public int getNonEligibleDetailsCount() {
        return nonEligibleDetailsCount;
    }

    public void setNonEligibleDetailsCount(int nonEligibleDetailsCount) {
        this.nonEligibleDetailsCount = nonEligibleDetailsCount;
    }

    public BigDecimal getNonEligibleDetailsGrossAmount() {
        return nonEligibleDetailsGrossAmount;
    }

    public void setNonEligibleDetailsGrossAmount(BigDecimal nonEligibleDetailsGrossAmount) {
        this.nonEligibleDetailsGrossAmount = nonEligibleDetailsGrossAmount;
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

    public int getFasAndClaFasEligibleDetailsCount() {
        return fasAndClaFasEligibleDetailsCount;
    }

    public void setFasAndClaFasEligibleDetailsCount(int fasAndClaFasEligibleDetailsCount) {
        this.fasAndClaFasEligibleDetailsCount = fasAndClaFasEligibleDetailsCount;
    }

    public BigDecimal getFasAndClaFasEligibleDetailsGrossAmount() {
        return fasAndClaFasEligibleDetailsGrossAmount;
    }

    public void setFasAndClaFasEligibleDetailsGrossAmount(BigDecimal fasAndClaFasEligibleDetailsGrossAmount) {
        this.fasAndClaFasEligibleDetailsGrossAmount = fasAndClaFasEligibleDetailsGrossAmount;
    }

    public int getScenariosDetailsCount() {
        return scenariosDetailsCount;
    }

    public void setScenariosDetailsCount(int scenariosDetailsCount) {
        this.scenariosDetailsCount = scenariosDetailsCount;
    }

    public BigDecimal getScenariosDetailsGrossAmount() {
        return scenariosDetailsGrossAmount;
    }

    public void setScenariosDetailsGrossAmount(BigDecimal scenariosDetailsGrossAmount) {
        this.scenariosDetailsGrossAmount = scenariosDetailsGrossAmount;
    }

    public BigDecimal getScenariosDetailsNetAmount() {
        return scenariosDetailsNetAmount;
    }

    public void setScenariosDetailsNetAmount(BigDecimal scenariosDetailsNetAmount) {
        this.scenariosDetailsNetAmount = scenariosDetailsNetAmount;
    }

    public int getReturnToClaDetailsCount() {
        return returnToClaDetailsCount;
    }

    public void setReturnToClaDetailsCount(int returnToClaDetailsCount) {
        this.returnToClaDetailsCount = returnToClaDetailsCount;
    }

    public BigDecimal getReturnToClaDetailsGrossAmount() {
        return returnToClaDetailsGrossAmount;
    }

    public void setReturnToClaDetailsGrossAmount(BigDecimal returnToClaDetailsGrossAmount) {
        this.returnToClaDetailsGrossAmount = returnToClaDetailsGrossAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BatchSummaryReportDto)) {
            return false;
        }
        BatchSummaryReportDto that = (BatchSummaryReportDto) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(nonEligibleDetailsCount, that.nonEligibleDetailsCount)
            .append(ntsDetailsCount, that.ntsDetailsCount)
            .append(fasAndClaFasEligibleDetailsCount, that.fasAndClaFasEligibleDetailsCount)
            .append(scenariosDetailsCount, that.scenariosDetailsCount)
            .append(returnToClaDetailsCount, that.returnToClaDetailsCount)
            .append(batchName, that.batchName)
            .append(rroName, that.rroName)
            .append(rroAccountNumber, that.rroAccountNumber)
            .append(paymentDate, that.paymentDate)
            .append(grossAmount, that.grossAmount)
            .append(nonEligibleDetailsGrossAmount, that.nonEligibleDetailsGrossAmount)
            .append(ntsDetailsGrossAmount, that.ntsDetailsGrossAmount)
            .append(fasAndClaFasEligibleDetailsGrossAmount, that.fasAndClaFasEligibleDetailsGrossAmount)
            .append(scenariosDetailsGrossAmount, that.scenariosDetailsGrossAmount)
            .append(scenariosDetailsNetAmount, that.scenariosDetailsNetAmount)
            .append(returnToClaDetailsGrossAmount, that.returnToClaDetailsGrossAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(batchName)
            .append(rroName)
            .append(rroAccountNumber)
            .append(paymentDate)
            .append(grossAmount)
            .append(nonEligibleDetailsCount)
            .append(nonEligibleDetailsGrossAmount)
            .append(ntsDetailsCount)
            .append(ntsDetailsGrossAmount)
            .append(fasAndClaFasEligibleDetailsCount)
            .append(fasAndClaFasEligibleDetailsGrossAmount)
            .append(scenariosDetailsCount)
            .append(scenariosDetailsGrossAmount)
            .append(scenariosDetailsNetAmount)
            .append(returnToClaDetailsCount)
            .append(returnToClaDetailsGrossAmount)
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
            .append("grossAmount", grossAmount)
            .append("nonEligibleDetailsCount", nonEligibleDetailsCount)
            .append("nonEligibleDetailsGrossAmount", nonEligibleDetailsGrossAmount)
            .append("ntsDetailsCount", ntsDetailsCount)
            .append("ntsDetailsGrossAmount", ntsDetailsGrossAmount)
            .append("fasAndClaFasEligibleDetailsCount", fasAndClaFasEligibleDetailsCount)
            .append("fasAndClaFasEligibleDetailsGrossAmount", fasAndClaFasEligibleDetailsGrossAmount)
            .append("scenariosDetailsCount", scenariosDetailsCount)
            .append("scenariosDetailsGrossAmount", scenariosDetailsGrossAmount)
            .append("scenariosDetailsNetAmount", scenariosDetailsNetAmount)
            .append("returnToClaDetailsCount", returnToClaDetailsCount)
            .append("returnToClaDetailsGrossAmount", returnToClaDetailsGrossAmount)
            .toString();
    }
}
