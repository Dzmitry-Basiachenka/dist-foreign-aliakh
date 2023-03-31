package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.foreign.domain.FdaConstants;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents domain object for usage batch statistic.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/07/2018
 *
 * @author Ihar Suvorau
 */
public class BatchStatistic {

    private String batchName;
    private int totalCount;
    private BigDecimal totalAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private int matchedCount;
    private BigDecimal matchedAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private BigDecimal matchedPercent = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private int worksNotFoundCount;
    private BigDecimal worksNotFoundAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private BigDecimal worksNotFoundPercent = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private int multipleMatchingCount;
    private BigDecimal multipleMatchingAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private BigDecimal multipleMatchingPercent = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private int ntsWithdrawnCount;
    private BigDecimal ntsWithdrawnAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private BigDecimal ntsWithdrawnPercent = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private int rhNotFoundCount;
    private BigDecimal rhNotFoundAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private BigDecimal rhNotFoundPercent = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private int rhFoundCount;
    private BigDecimal rhFoundAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private BigDecimal rhFoundPercent = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private int eligibleCount;
    private BigDecimal eligibleAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private BigDecimal eligiblePercent = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private int sendForRaCount;
    private BigDecimal sendForRaAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private BigDecimal sendForRaPercent = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private int paidCount;
    private BigDecimal paidAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private BigDecimal paidPercent = FdaConstants.DEFAULT_AMOUNT_SCALE_2;

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getMatchedCount() {
        return matchedCount;
    }

    public void setMatchedCount(int matchedCount) {
        this.matchedCount = matchedCount;
    }

    public BigDecimal getMatchedAmount() {
        return matchedAmount;
    }

    public void setMatchedAmount(BigDecimal matchedAmount) {
        this.matchedAmount = matchedAmount;
    }

    public BigDecimal getMatchedPercent() {
        return matchedPercent;
    }

    public void setMatchedPercent(BigDecimal matchedPercent) {
        this.matchedPercent = matchedPercent;
    }

    public int getWorksNotFoundCount() {
        return worksNotFoundCount;
    }

    public void setWorksNotFoundCount(int worksNotFoundCount) {
        this.worksNotFoundCount = worksNotFoundCount;
    }

    public BigDecimal getWorksNotFoundAmount() {
        return worksNotFoundAmount;
    }

    public void setWorksNotFoundAmount(BigDecimal worksNotFoundAmount) {
        this.worksNotFoundAmount = worksNotFoundAmount;
    }

    public BigDecimal getWorksNotFoundPercent() {
        return worksNotFoundPercent;
    }

    public void setWorksNotFoundPercent(BigDecimal worksNotFoundPercent) {
        this.worksNotFoundPercent = worksNotFoundPercent;
    }

    public int getMultipleMatchingCount() {
        return multipleMatchingCount;
    }

    public void setMultipleMatchingCount(int multipleMatchingCount) {
        this.multipleMatchingCount = multipleMatchingCount;
    }

    public BigDecimal getMultipleMatchingAmount() {
        return multipleMatchingAmount;
    }

    public void setMultipleMatchingAmount(BigDecimal multipleMatchingAmount) {
        this.multipleMatchingAmount = multipleMatchingAmount;
    }

    public BigDecimal getMultipleMatchingPercent() {
        return multipleMatchingPercent;
    }

    public void setMultipleMatchingPercent(BigDecimal multipleMatchingPercent) {
        this.multipleMatchingPercent = multipleMatchingPercent;
    }

    public int getNtsWithdrawnCount() {
        return ntsWithdrawnCount;
    }

    public void setNtsWithdrawnCount(int ntsWithdrawnCount) {
        this.ntsWithdrawnCount = ntsWithdrawnCount;
    }

    public BigDecimal getNtsWithdrawnAmount() {
        return ntsWithdrawnAmount;
    }

    public void setNtsWithdrawnAmount(BigDecimal ntsWithdrawnAmount) {
        this.ntsWithdrawnAmount = ntsWithdrawnAmount;
    }

    public BigDecimal getNtsWithdrawnPercent() {
        return ntsWithdrawnPercent;
    }

    public void setNtsWithdrawnPercent(BigDecimal ntsWithdrawnPercent) {
        this.ntsWithdrawnPercent = ntsWithdrawnPercent;
    }

    public int getRhNotFoundCount() {
        return rhNotFoundCount;
    }

    public void setRhNotFoundCount(int rhNotFoundCount) {
        this.rhNotFoundCount = rhNotFoundCount;
    }

    public BigDecimal getRhNotFoundAmount() {
        return rhNotFoundAmount;
    }

    public void setRhNotFoundAmount(BigDecimal rhNotFoundAmount) {
        this.rhNotFoundAmount = rhNotFoundAmount;
    }

    public BigDecimal getRhNotFoundPercent() {
        return rhNotFoundPercent;
    }

    public void setRhNotFoundPercent(BigDecimal rhNotFoundPercent) {
        this.rhNotFoundPercent = rhNotFoundPercent;
    }

    public int getRhFoundCount() {
        return rhFoundCount;
    }

    public void setRhFoundCount(int rhFoundCount) {
        this.rhFoundCount = rhFoundCount;
    }

    public BigDecimal getRhFoundAmount() {
        return rhFoundAmount;
    }

    public void setRhFoundAmount(BigDecimal rhFoundAmount) {
        this.rhFoundAmount = rhFoundAmount;
    }

    public BigDecimal getRhFoundPercent() {
        return rhFoundPercent;
    }

    public void setRhFoundPercent(BigDecimal rhFoundPercent) {
        this.rhFoundPercent = rhFoundPercent;
    }

    public int getEligibleCount() {
        return eligibleCount;
    }

    public void setEligibleCount(int eligibleCount) {
        this.eligibleCount = eligibleCount;
    }

    public BigDecimal getEligibleAmount() {
        return eligibleAmount;
    }

    public void setEligibleAmount(BigDecimal eligibleAmount) {
        this.eligibleAmount = eligibleAmount;
    }

    public BigDecimal getEligiblePercent() {
        return eligiblePercent;
    }

    public void setEligiblePercent(BigDecimal eligiblePercent) {
        this.eligiblePercent = eligiblePercent;
    }

    public int getSendForRaCount() {
        return sendForRaCount;
    }

    public void setSendForRaCount(int sendForRaCount) {
        this.sendForRaCount = sendForRaCount;
    }

    public BigDecimal getSendForRaAmount() {
        return sendForRaAmount;
    }

    public void setSendForRaAmount(BigDecimal sendForRaAmount) {
        this.sendForRaAmount = sendForRaAmount;
    }

    public BigDecimal getSendForRaPercent() {
        return sendForRaPercent;
    }

    public void setSendForRaPercent(BigDecimal sendForRaPercent) {
        this.sendForRaPercent = sendForRaPercent;
    }

    public int getPaidCount() {
        return paidCount;
    }

    public void setPaidCount(int paidCount) {
        this.paidCount = paidCount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public BigDecimal getPaidPercent() {
        return paidPercent;
    }

    public void setPaidPercent(BigDecimal paidPercent) {
        this.paidPercent = paidPercent;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        BatchStatistic that = (BatchStatistic) obj;
        return new EqualsBuilder()
            .append(batchName, that.batchName)
            .append(totalCount, that.totalCount)
            .append(totalAmount, that.totalAmount)
            .append(matchedCount, that.matchedCount)
            .append(matchedAmount, that.matchedAmount)
            .append(matchedPercent, that.matchedPercent)
            .append(ntsWithdrawnCount, that.ntsWithdrawnCount)
            .append(ntsWithdrawnAmount, that.ntsWithdrawnAmount)
            .append(ntsWithdrawnPercent, that.ntsWithdrawnPercent)
            .append(worksNotFoundCount, that.worksNotFoundCount)
            .append(worksNotFoundAmount, that.worksNotFoundAmount)
            .append(worksNotFoundPercent, that.worksNotFoundPercent)
            .append(multipleMatchingCount, that.multipleMatchingCount)
            .append(multipleMatchingAmount, that.multipleMatchingAmount)
            .append(multipleMatchingPercent, that.multipleMatchingPercent)
            .append(rhNotFoundCount, that.rhNotFoundCount)
            .append(rhNotFoundAmount, that.rhNotFoundAmount)
            .append(rhNotFoundPercent, that.rhNotFoundPercent)
            .append(rhFoundCount, that.rhFoundCount)
            .append(rhFoundAmount, that.rhFoundAmount)
            .append(rhFoundPercent, that.rhFoundPercent)
            .append(eligibleCount, that.eligibleCount)
            .append(eligibleAmount, that.eligibleAmount)
            .append(eligiblePercent, that.eligiblePercent)
            .append(sendForRaCount, that.sendForRaCount)
            .append(sendForRaAmount, that.sendForRaAmount)
            .append(sendForRaPercent, that.sendForRaPercent)
            .append(paidCount, that.paidCount)
            .append(paidAmount, that.paidAmount)
            .append(paidPercent, that.paidPercent)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(batchName)
            .append(totalCount)
            .append(totalAmount)
            .append(matchedCount)
            .append(matchedAmount)
            .append(matchedPercent)
            .append(ntsWithdrawnCount)
            .append(ntsWithdrawnAmount)
            .append(ntsWithdrawnPercent)
            .append(worksNotFoundCount)
            .append(worksNotFoundAmount)
            .append(worksNotFoundPercent)
            .append(multipleMatchingCount)
            .append(multipleMatchingAmount)
            .append(multipleMatchingPercent)
            .append(rhNotFoundCount)
            .append(rhNotFoundAmount)
            .append(rhNotFoundPercent)
            .append(rhFoundCount)
            .append(rhFoundAmount)
            .append(rhFoundPercent)
            .append(eligibleCount)
            .append(eligibleAmount)
            .append(eligiblePercent)
            .append(sendForRaCount)
            .append(sendForRaAmount)
            .append(sendForRaPercent)
            .append(paidCount)
            .append(paidAmount)
            .append(paidPercent)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("batchName", batchName)
            .append("totalCount", totalCount)
            .append("totalAmount", totalAmount)
            .append("matchedCount", matchedCount)
            .append("matchedAmount", matchedAmount)
            .append("matchedPercent", matchedPercent)
            .append("ntsWithdrawnCount", ntsWithdrawnCount)
            .append("ntsWithdrawnAmount", ntsWithdrawnAmount)
            .append("ntsWithdrawnPercent", ntsWithdrawnPercent)
            .append("worksNotFoundCount", worksNotFoundCount)
            .append("worksNotFoundAmount", worksNotFoundAmount)
            .append("worksNotFoundPercent", worksNotFoundPercent)
            .append("multipleMatchingCount", multipleMatchingCount)
            .append("multipleMatchingAmount", multipleMatchingAmount)
            .append("multipleMatchingPercent", multipleMatchingPercent)
            .append("rhNotFoundCount", rhNotFoundCount)
            .append("rhNotFoundAmount", rhNotFoundAmount)
            .append("rhNotFoundPercent", rhNotFoundPercent)
            .append("rhFoundCount", rhFoundCount)
            .append("rhFoundAmount", rhFoundAmount)
            .append("rhFoundPercent", rhFoundPercent)
            .append("eligibleCount", eligibleCount)
            .append("eligibleAmount", eligibleAmount)
            .append("eligiblePercent", eligiblePercent)
            .append("sendForRaCount", sendForRaCount)
            .append("sendForRaAmount", sendForRaAmount)
            .append("sendForRaPercent", sendForRaPercent)
            .append("paidCount", paidCount)
            .append("paidAmount", paidAmount)
            .append("paidPercent", paidPercent)
            .toString();
    }
}
