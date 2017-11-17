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
    private UsageStatusEnum usageStatus;
    private LocalDate paymentDate;
    private Integer fiscalYear;

    /**
     * Default constructor.
     */
    public UsageFilter() {
        // Default constructor
    }

    /**
     * Constructs new filter based on existing.
     *
     * @param filter base filter
     */
    public UsageFilter(UsageFilter filter) {
        if (null != filter) {
            setRhAccountNumbers(filter.getRhAccountNumbers());
            setUsageBatchesIds(filter.getUsageBatchesIds());
            setUsageStatus(filter.getUsageStatus());
            setPaymentDate(filter.getPaymentDate());
            setFiscalYear(filter.getFiscalYear());
        }
    }

    public Set<Long> getRhAccountNumbers() {
        return rhAccountNumbers;
    }

    public void setRhAccountNumbers(Set<Long> rhAccountNumbers) {
        this.rhAccountNumbers = rhAccountNumbers;
    }

    public Set<String> getUsageBatchesIds() {
        return usageBatchesIds;
    }

    public void setUsageBatchesIds(Set<String> usageBatchesIds) {
        this.usageBatchesIds = usageBatchesIds;
    }

    public UsageStatusEnum getUsageStatus() {
        return usageStatus;
    }

    public void setUsageStatus(UsageStatusEnum usageStatus) {
        this.usageStatus = usageStatus;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Integer getFiscalYear() {
        return fiscalYear;
    }

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
            && null == fiscalYear
            && null == usageStatus;
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
