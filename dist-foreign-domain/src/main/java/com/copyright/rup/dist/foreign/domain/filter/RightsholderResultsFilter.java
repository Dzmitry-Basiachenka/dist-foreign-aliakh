package com.copyright.rup.dist.foreign.domain.filter;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represents domain to hold results by rightsholder.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 08/25/2022
 *
 * @author Dzmitry Basiachenka
 */
public class RightsholderResultsFilter {

    private String scenarioId;
    private Long rhAccountNumber;
    private String rhName;
    private Long wrWrkInst;
    private String systemTitle;
    private Integer aggregateLicenseeClassId;
    private String aggregateLicenseeClassName;

    /**
     * Default constructor.
     */
    public RightsholderResultsFilter() {
    }

    /**
     * Constructor.
     *
     * @param filter {@link RightsholderResultsFilter}
     */
    public RightsholderResultsFilter(RightsholderResultsFilter filter) {
        setScenarioId(filter.getScenarioId());
        setRhAccountNumber(filter.getRhAccountNumber());
        setRhName(filter.getRhName());
        setWrWrkInst(filter.getWrWrkInst());
        setSystemTitle(filter.getSystemTitle());
        setAggregateLicenseeClassId(filter.getAggregateLicenseeClassId());
        setAggregateLicenseeClassName(filter.getAggregateLicenseeClassName());
    }

    public String getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(String scenarioId) {
        this.scenarioId = scenarioId;
    }

    public Long getRhAccountNumber() {
        return rhAccountNumber;
    }

    public void setRhAccountNumber(Long rhAccountNumber) {
        this.rhAccountNumber = rhAccountNumber;
    }

    public String getRhName() {
        return rhName;
    }

    public void setRhName(String rhName) {
        this.rhName = rhName;
    }

    public Long getWrWrkInst() {
        return wrWrkInst;
    }

    public void setWrWrkInst(Long wrWrkInst) {
        this.wrWrkInst = wrWrkInst;
    }

    public String getSystemTitle() {
        return systemTitle;
    }

    public void setSystemTitle(String systemTitle) {
        this.systemTitle = systemTitle;
    }

    public Integer getAggregateLicenseeClassId() {
        return aggregateLicenseeClassId;
    }

    public void setAggregateLicenseeClassId(Integer aggregateLicenseeClassId) {
        this.aggregateLicenseeClassId = aggregateLicenseeClassId;
    }

    public String getAggregateLicenseeClassName() {
        return aggregateLicenseeClassName;
    }

    public void setAggregateLicenseeClassName(String aggregateLicenseeClassName) {
        this.aggregateLicenseeClassName = aggregateLicenseeClassName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        RightsholderResultsFilter that = (RightsholderResultsFilter) obj;
        return new EqualsBuilder()
            .append(scenarioId, that.scenarioId)
            .append(rhAccountNumber, that.rhAccountNumber)
            .append(rhName, that.rhName)
            .append(wrWrkInst, that.wrWrkInst)
            .append(systemTitle, that.systemTitle)
            .append(aggregateLicenseeClassId, that.aggregateLicenseeClassId)
            .append(aggregateLicenseeClassName, that.aggregateLicenseeClassName)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(scenarioId)
            .append(rhAccountNumber)
            .append(rhName)
            .append(wrWrkInst)
            .append(systemTitle)
            .append(aggregateLicenseeClassId)
            .append(aggregateLicenseeClassName)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("scenarioId", scenarioId)
            .append("rhAccountNumber", rhAccountNumber)
            .append("rhName", rhName)
            .append("wrWrkInst", wrWrkInst)
            .append("systemTitle", systemTitle)
            .append("aggregateLicenseeClassId", aggregateLicenseeClassId)
            .append("aggregateLicenseeClassName", aggregateLicenseeClassName)
            .toString();
    }
}
