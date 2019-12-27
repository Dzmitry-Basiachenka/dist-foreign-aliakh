package com.copyright.rup.dist.foreign.domain.filter;

import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import com.google.common.collect.Sets;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Stored usage filter to provide scenario refresh.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 03/05/2018
 *
 * @author Aliaksandr Liakh
 */
public class ScenarioUsageFilter extends StoredEntity<String> {

    private String scenarioId;
    private Set<Long> rhAccountNumbers = Sets.newHashSet();
    private Set<UsageBatch> usageBatches = Sets.newHashSet();
    private String productFamily;
    private UsageStatusEnum usageStatus;
    private LocalDate paymentDate;
    private Integer fiscalYear;

    /**
     * Default constructor.
     */
    public ScenarioUsageFilter() {
        // default constructor
    }

    /**
     * Constructor.
     *
     * @param usageFilter {@link UsageFilter}
     */
    public ScenarioUsageFilter(UsageFilter usageFilter) {
        Objects.requireNonNull(usageFilter);
        this.rhAccountNumbers = usageFilter.getRhAccountNumbers();
        setUsageBatchesIds(usageFilter.getUsageBatchesIds());
        this.productFamily = Objects.requireNonNull(usageFilter.getProductFamily());
        this.usageStatus = usageFilter.getUsageStatus();
        this.paymentDate = usageFilter.getPaymentDate();
        this.fiscalYear = usageFilter.getFiscalYear();
    }

    public String getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(String scenarioId) {
        this.scenarioId = scenarioId;
    }

    public Set<Long> getRhAccountNumbers() {
        return rhAccountNumbers;
    }

    public void setRhAccountNumbers(Set<Long> rhAccountNumbers) {
        this.rhAccountNumbers = rhAccountNumbers;
    }

    public Set<UsageBatch> getUsageBatches() {
        return usageBatches;
    }

    public void setUsageBatches(Set<UsageBatch> usageBatches) {
        this.usageBatches = usageBatches;
    }

    public Set<String> getUsageBatchesIds() {
        return usageBatches.stream().map(UsageBatch::getId).collect(Collectors.toSet());
    }

    public void setUsageBatchesIds(Set<String> usageBatchesIds) {
        this.usageBatches = usageBatchesIds.stream().map(batchId -> {
            UsageBatch usageBatch = new UsageBatch();
            usageBatch.setId(batchId);
            return usageBatch;
        }).collect(Collectors.toSet());
    }

    public UsageStatusEnum getUsageStatus() {
        return usageStatus;
    }

    public void setUsageStatus(UsageStatusEnum usageStatus) {
        this.usageStatus = usageStatus;
    }

    public String getProductFamily() {
        return productFamily;
    }

    public void setProductFamily(String productFamily) {
        this.productFamily = productFamily;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        ScenarioUsageFilter that = (ScenarioUsageFilter) obj;
        return new EqualsBuilder()
            .append(this.scenarioId, that.scenarioId)
            .append(this.rhAccountNumbers, that.rhAccountNumbers)
            .append(this.usageBatches, that.usageBatches)
            .append(this.productFamily, that.productFamily)
            .append(this.usageStatus, that.usageStatus)
            .append(this.paymentDate, that.paymentDate)
            .append(this.fiscalYear, that.fiscalYear)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(scenarioId)
            .append(rhAccountNumbers)
            .append(usageBatches)
            .append(productFamily)
            .append(usageStatus)
            .append(paymentDate)
            .append(fiscalYear)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("scenarioId", scenarioId)
            .append("rhAccountNumbers", rhAccountNumbers)
            .append("usageBatches", usageBatches)
            .append("productFamily", productFamily)
            .append("usageStatus", usageStatus)
            .append("paymentDate", paymentDate)
            .append("fiscalYear", fiscalYear)
            .toString();
    }
}
