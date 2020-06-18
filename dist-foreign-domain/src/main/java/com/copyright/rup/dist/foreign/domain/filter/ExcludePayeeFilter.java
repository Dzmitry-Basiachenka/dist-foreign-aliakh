package com.copyright.rup.dist.foreign.domain.filter;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Filter fo exclude payees.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/19
 *
 * @author Uladzislau Shalamitski
 */
public class ExcludePayeeFilter {

    private Boolean payeeParticipating;
    private BigDecimal netAmountMinThreshold;
    private Set<String> scenarioIds = new HashSet<>();
    private String searchValue;

    /**
     * Default constructor.
     */
    public ExcludePayeeFilter() {
        // default constructor
    }

    /**
     * Constructor.
     *
     * @param filter instance of {@link ExcludePayeeFilter}
     */
    public ExcludePayeeFilter(ExcludePayeeFilter filter) {
        this.payeeParticipating = filter.getPayeeParticipating();
        this.netAmountMinThreshold = filter.getNetAmountMinThreshold();
        this.scenarioIds = filter.getScenarioIds();
        this.searchValue = filter.getSearchValue();
    }

    public Boolean getPayeeParticipating() {
        return payeeParticipating;
    }

    public void setPayeeParticipating(Boolean payeeParticipating) {
        this.payeeParticipating = payeeParticipating;
    }

    public BigDecimal getNetAmountMinThreshold() {
        return netAmountMinThreshold;
    }

    public void setNetAmountMinThreshold(BigDecimal netAmountMinThreshold) {
        this.netAmountMinThreshold = netAmountMinThreshold;
    }

    public Set<String> getScenarioIds() {
        return scenarioIds;
    }

    public void setScenarioIds(Set<String> scenarios) {
        this.scenarioIds = scenarios;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public boolean isEmpty() {
        return Objects.isNull(payeeParticipating)
            && Objects.isNull(netAmountMinThreshold)
            && CollectionUtils.isEmpty(scenarioIds)
            && StringUtils.isBlank(searchValue);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        ExcludePayeeFilter that = (ExcludePayeeFilter) obj;
        return new EqualsBuilder()
            .append(payeeParticipating, that.payeeParticipating)
            .append(netAmountMinThreshold, that.netAmountMinThreshold)
            .append(scenarioIds, that.scenarioIds)
            .append(searchValue, that.searchValue)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(payeeParticipating)
            .append(netAmountMinThreshold)
            .append(scenarioIds)
            .append(searchValue)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("payeeParticipating", payeeParticipating)
            .append("netAmountMinThreshold", netAmountMinThreshold)
            .append("scenarioIds", scenarioIds)
            .append("searchValue", searchValue)
            .toString();
    }
}
