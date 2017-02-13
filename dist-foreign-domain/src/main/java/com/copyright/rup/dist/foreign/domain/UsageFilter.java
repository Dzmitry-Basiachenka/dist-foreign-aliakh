package com.copyright.rup.dist.foreign.domain;

import com.google.common.collect.Sets;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDate;
import java.util.Set;

/**
 * Class represents a set of filtering criteria.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 1/19/17
 *
 * @author Aliaksandr Radkevich
 */
public class UsageFilter {

    private Set<Long> rhAccountNumbers = Sets.newHashSet();
    private Set<String> usageBatchesIds = Sets.newHashSet();
    // Phase I only ELIGIBLE usages are displayed. Add usageStatus to isEmpty method after implementing filter by status
    private UsageStatusEnum usageStatus = UsageStatusEnum.ELIGIBLE;
    private LocalDate paymentDate;
    private Integer fiscalYear;

    /**
     * @return a set of rightsholders account numbers.
     */
    public Set<Long> getRhAccountNumbers() {
        return rhAccountNumbers;
    }

    /**
     * Sets rightsholders account numbers.
     *
     * @param rhAccountNumbers rightsholders account numbers
     */
    public void setRhAccountNumbers(Set<Long> rhAccountNumbers) {
        this.rhAccountNumbers = rhAccountNumbers;
    }

    /**
     * @return a set of usage batches ids.
     */
    public Set<String> getUsageBatchesIds() {
        return usageBatchesIds;
    }

    /**
     * Sets usage batches ids.
     *
     * @param usageBatchesIds usage batches ids
     */
    public void setUsageBatchesIds(Set<String> usageBatchesIds) {
        this.usageBatchesIds = usageBatchesIds;
    }

    /**
     * @return instance of {@link UsageStatusEnum}.
     */
    public UsageStatusEnum getUsageStatus() {
        return usageStatus;
    }

    /**
     * Sets usage status.
     *
     * @param usageStatus instance of {@link UsageStatusEnum}
     */
    public void setUsageStatus(UsageStatusEnum usageStatus) {
        this.usageStatus = usageStatus;
    }

    /**
     * @return payment date.
     */
    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    /**
     * Sets payment date.
     *
     * @param paymentDate payment date
     */
    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     * @return fiscal year.
     */
    public Integer getFiscalYear() {
        return fiscalYear;
    }

    /**
     * Sets fiscal year.
     *
     * @param fiscalYear fiscal year
     */
    public void setFiscalYear(Integer fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    /**
     * @return {@code true} if filter does not contain any criteria, otherwise {@code false}.
     */
    public boolean isEmpty() {
        return (null == rhAccountNumbers || rhAccountNumbers.isEmpty())
            && (null == usageBatchesIds || usageBatchesIds.isEmpty())
            && null == paymentDate
            && null == fiscalYear;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        UsageFilter that = (UsageFilter) obj;
        return new EqualsBuilder()
            .append(this.rhAccountNumbers, that.rhAccountNumbers)
            .append(this.usageBatchesIds, that.usageBatchesIds)
            .append(this.usageStatus, that.usageStatus)
            .append(this.paymentDate, that.paymentDate)
            .append(this.fiscalYear, that.fiscalYear)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(rhAccountNumbers)
            .append(usageBatchesIds)
            .append(usageStatus)
            .append(paymentDate)
            .append(fiscalYear)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("rhAccountNumbers", rhAccountNumbers)
            .append("usageBatchesIds", usageBatchesIds)
            .append("usageStatus", usageStatus)
            .append("paymentDate", paymentDate)
            .append("fiscalYear", fiscalYear)
            .toString();
    }
}
