package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents information about usage batch status.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 02/10/2021
 *
 * @author Ihar Suvorau
 */
public class UsageBatchStatus {

    private String batchName;
    private int totalCount;
    private int newCount;
    private int workNotFoundCount;
    private int workFoundCount;
    private int ntsWithdrawnCount;
    private int workResearchCount;
    private int rhNotFoundCount;
    private int rhFoundCount;
    private int workNotGrantedCount;
    private int sentForRaCount;
    private int unclassifiedCount;
    private int eligibleCount;
    private int excludedCount;
    private boolean completed;

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

    public int getNewCount() {
        return newCount;
    }

    public void setNewCount(int newCount) {
        this.newCount = newCount;
    }

    public int getWorkNotFoundCount() {
        return workNotFoundCount;
    }

    public void setWorkNotFoundCount(int workNotFoundCount) {
        this.workNotFoundCount = workNotFoundCount;
    }

    public int getWorkFoundCount() {
        return workFoundCount;
    }

    public void setWorkFoundCount(int workFoundCount) {
        this.workFoundCount = workFoundCount;
    }

    public int getNtsWithdrawnCount() {
        return ntsWithdrawnCount;
    }

    public void setNtsWithdrawnCount(int ntsWithdrawnCount) {
        this.ntsWithdrawnCount = ntsWithdrawnCount;
    }

    public int getWorkResearchCount() {
        return workResearchCount;
    }

    public void setWorkResearchCount(int workResearchCount) {
        this.workResearchCount = workResearchCount;
    }

    public int getRhNotFoundCount() {
        return rhNotFoundCount;
    }

    public void setRhNotFoundCount(int rhNotFoundCount) {
        this.rhNotFoundCount = rhNotFoundCount;
    }

    public int getRhFoundCount() {
        return rhFoundCount;
    }

    public void setRhFoundCount(int rhFoundCount) {
        this.rhFoundCount = rhFoundCount;
    }

    public int getWorkNotGrantedCount() {
        return workNotGrantedCount;
    }

    public void setWorkNotGrantedCount(int workNotGrantedCount) {
        this.workNotGrantedCount = workNotGrantedCount;
    }

    public int getSentForRaCount() {
        return sentForRaCount;
    }

    public void setSentForRaCount(int sentForRaCount) {
        this.sentForRaCount = sentForRaCount;
    }

    public int getUnclassifiedCount() {
        return unclassifiedCount;
    }

    public void setUnclassifiedCount(int unclassifiedCount) {
        this.unclassifiedCount = unclassifiedCount;
    }

    public int getEligibleCount() {
        return eligibleCount;
    }

    public void setEligibleCount(int eligibleCount) {
        this.eligibleCount = eligibleCount;
    }

    public int getExcludedCount() {
        return excludedCount;
    }

    public void setExcludedCount(int excludedCount) {
        this.excludedCount = excludedCount;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        UsageBatchStatus that = (UsageBatchStatus) obj;
        return new EqualsBuilder()
            .append(batchName, that.batchName)
            .append(totalCount, that.totalCount)
            .append(newCount, that.newCount)
            .append(workNotFoundCount, that.workNotFoundCount)
            .append(workFoundCount, that.workFoundCount)
            .append(ntsWithdrawnCount, that.ntsWithdrawnCount)
            .append(workResearchCount, that.workResearchCount)
            .append(rhNotFoundCount, that.rhNotFoundCount)
            .append(rhFoundCount, that.rhFoundCount)
            .append(workNotGrantedCount, that.workNotGrantedCount)
            .append(sentForRaCount, that.sentForRaCount)
            .append(unclassifiedCount, that.unclassifiedCount)
            .append(eligibleCount, that.eligibleCount)
            .append(excludedCount, that.excludedCount)
            .append(completed, that.completed)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(batchName)
            .append(totalCount)
            .append(newCount)
            .append(workNotFoundCount)
            .append(workFoundCount)
            .append(ntsWithdrawnCount)
            .append(workResearchCount)
            .append(rhNotFoundCount)
            .append(rhFoundCount)
            .append(workNotGrantedCount)
            .append(sentForRaCount)
            .append(unclassifiedCount)
            .append(eligibleCount)
            .append(excludedCount)
            .append(completed)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("batchName", batchName)
            .append("totalCount", totalCount)
            .append("newCount", newCount)
            .append("workNotFoundCount", workNotFoundCount)
            .append("workFoundCount", workFoundCount)
            .append("ntsWithdrawnCount", ntsWithdrawnCount)
            .append("workResearchCount", workResearchCount)
            .append("rhNotFoundCount", rhNotFoundCount)
            .append("rhFoundCount", rhFoundCount)
            .append("workNotGrantedCount", workNotGrantedCount)
            .append("sentForRaCount", sentForRaCount)
            .append("unclassifiedCount", unclassifiedCount)
            .append("eligibleCount", eligibleCount)
            .append("excludedCount", excludedCount)
            .append("completed", completed)
            .toString();
    }
}
