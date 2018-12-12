package com.copyright.rup.dist.foreign.domain.report;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents domain object for usage batch statistic.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/07/2018
 *
 * @author Ihar Suvorau
 */
public class UsageBatchStatistic {

    private static final BigDecimal DEFAULT_AMOUNT = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);

    private String batchName;
    private LocalDate date;
    private int loadedCount;
    private BigDecimal loadedAmount = DEFAULT_AMOUNT;
    private int matchedCount;
    private BigDecimal matchedAmount = DEFAULT_AMOUNT;
    private BigDecimal matchedPercent = DEFAULT_AMOUNT;
    private int worksNotFoundCount;
    private BigDecimal worksNotFoundAmount = DEFAULT_AMOUNT;
    private BigDecimal worksNotFoundPercent = DEFAULT_AMOUNT;
    private int multipleMatchingCount;
    private BigDecimal multipleMatchingAmount = DEFAULT_AMOUNT;
    private BigDecimal multipleMatchingPercent = DEFAULT_AMOUNT;
    private int ntsWithDrawnCount;
    private BigDecimal ntsWithDrawnAmount = DEFAULT_AMOUNT;
    private BigDecimal ntsWithDrawnPercent = DEFAULT_AMOUNT;
    private int rhNotFoundCount;
    private BigDecimal rhNotFoundAmount = DEFAULT_AMOUNT;
    private BigDecimal rhNotFoundPercent = DEFAULT_AMOUNT;
    private int eligibleCount;
    private BigDecimal eligibleAmount = DEFAULT_AMOUNT;
    private BigDecimal eligiblePercent = DEFAULT_AMOUNT;
    private int sendForRaCount;
    private BigDecimal sendForRaAmount = DEFAULT_AMOUNT;
    private BigDecimal sendForRaPercent = DEFAULT_AMOUNT;
    private int paidCount;
    private BigDecimal paidAmount = DEFAULT_AMOUNT;
    private BigDecimal paidPercent = DEFAULT_AMOUNT;

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getLoadedCount() {
        return loadedCount;
    }

    public void setLoadedCount(int loadedCount) {
        this.loadedCount = loadedCount;
    }

    public BigDecimal getLoadedAmount() {
        return loadedAmount;
    }

    public void setLoadedAmount(BigDecimal loadedAmount) {
        this.loadedAmount = loadedAmount;
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

    public int getNtsWithDrawnCount() {
        return ntsWithDrawnCount;
    }

    public void setNtsWithDrawnCount(int ntsWithDrawnCount) {
        this.ntsWithDrawnCount = ntsWithDrawnCount;
    }

    public BigDecimal getNtsWithDrawnAmount() {
        return ntsWithDrawnAmount;
    }

    public void setNtsWithDrawnAmount(BigDecimal ntsWithDrawnAmount) {
        this.ntsWithDrawnAmount = ntsWithDrawnAmount;
    }

    public BigDecimal getNtsWithDrawnPercent() {
        return ntsWithDrawnPercent;
    }

    public void setNtsWithDrawnPercent(BigDecimal ntsWithDrawnPercent) {
        this.ntsWithDrawnPercent = ntsWithDrawnPercent;
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
        UsageBatchStatistic that = (UsageBatchStatistic) obj;
        return new EqualsBuilder()
            .append(batchName, that.batchName)
            .append(date, that.date)
            .append(loadedCount, that.loadedCount)
            .append(loadedAmount, that.loadedAmount)
            .append(matchedCount, that.matchedCount)
            .append(matchedAmount, that.matchedAmount)
            .append(matchedPercent, that.matchedPercent)
            .append(ntsWithDrawnCount, that.ntsWithDrawnCount)
            .append(ntsWithDrawnAmount, that.ntsWithDrawnAmount)
            .append(ntsWithDrawnPercent, that.ntsWithDrawnPercent)
            .append(worksNotFoundCount, that.worksNotFoundCount)
            .append(worksNotFoundAmount, that.worksNotFoundAmount)
            .append(worksNotFoundPercent, that.worksNotFoundPercent)
            .append(multipleMatchingCount, that.multipleMatchingCount)
            .append(multipleMatchingAmount, that.multipleMatchingAmount)
            .append(multipleMatchingPercent, that.multipleMatchingPercent)
            .append(eligibleCount, that.eligibleCount)
            .append(eligibleAmount, that.eligibleAmount)
            .append(eligiblePercent, that.eligiblePercent)
            .append(rhNotFoundCount, that.rhNotFoundCount)
            .append(rhNotFoundAmount, that.rhNotFoundAmount)
            .append(rhNotFoundPercent, that.rhNotFoundPercent)
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
            .append(date)
            .append(loadedCount)
            .append(loadedAmount)
            .append(matchedCount)
            .append(matchedAmount)
            .append(matchedPercent)
            .append(ntsWithDrawnCount)
            .append(ntsWithDrawnAmount)
            .append(ntsWithDrawnPercent)
            .append(worksNotFoundCount)
            .append(worksNotFoundAmount)
            .append(worksNotFoundPercent)
            .append(multipleMatchingCount)
            .append(multipleMatchingAmount)
            .append(multipleMatchingPercent)
            .append(eligibleCount)
            .append(eligibleAmount)
            .append(eligiblePercent)
            .append(rhNotFoundCount)
            .append(rhNotFoundAmount)
            .append(rhNotFoundPercent)
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
            .append("date", date)
            .append("loadedCount", loadedCount)
            .append("loadedAmount", loadedAmount)
            .append("matchedCount", matchedCount)
            .append("matchedAmount", matchedAmount)
            .append("matchedPercent", matchedPercent)
            .append("ntsWithDrawnCount", ntsWithDrawnCount)
            .append("ntsWithDrawnAmount", ntsWithDrawnAmount)
            .append("ntsWithDrawnPercent", ntsWithDrawnPercent)
            .append("worksNotFoundCount", worksNotFoundCount)
            .append("worksNotFoundAmount", worksNotFoundAmount)
            .append("worksNotFoundPercent", worksNotFoundPercent)
            .append("multipleMatchingCount", multipleMatchingCount)
            .append("multipleMatchingAmount", multipleMatchingAmount)
            .append("multipleMatchingPercent", multipleMatchingPercent)
            .append("eligibleCount", eligibleCount)
            .append("eligibleAmount", eligibleAmount)
            .append("eligiblePercent", eligiblePercent)
            .append("rhNotFoundCount", rhNotFoundCount)
            .append("rhNotFoundAmount", rhNotFoundAmount)
            .append("rhNotFoundPercent", rhNotFoundPercent)
            .append("sendForRaCount", sendForRaCount)
            .append("sendForRaAmount", sendForRaAmount)
            .append("sendForRaPercent", sendForRaPercent)
            .append("paidCount", paidCount)
            .append("paidAmount", paidAmount)
            .append("paidPercent", paidPercent)
            .toString();
    }
}
