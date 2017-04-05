package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * Represents scenario.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/15/17
 *
 * @author Ihar Suvorau
 */
public class Scenario extends StoredEntity<String> {

    private String name;
    private BigDecimal netTotal = BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP);
    private BigDecimal grossTotal = BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP);
    private BigDecimal reportedTotal = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
    private String description;
    private ScenarioStatusEnum status;

    /**
     * @return scenario name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets scenario name.
     *
     * @param name scenario name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return scenario net total.
     */
    public BigDecimal getNetTotal() {
        return netTotal;
    }

    /**
     * Sets scenario net total.
     *
     * @param netTotal net total
     */
    public void setNetTotal(BigDecimal netTotal) {
        this.netTotal = netTotal;
    }

    /**
     * @return scenario gross total.
     */
    public BigDecimal getGrossTotal() {
        return grossTotal;
    }

    /**
     * Sets scenario gross total.
     *
     * @param grossTotal gross total
     */
    public void setGrossTotal(BigDecimal grossTotal) {
        this.grossTotal = grossTotal;
    }

    /**
     * @return reported total.
     */
    public BigDecimal getReportedTotal() {
        return reportedTotal;
    }

    /**
     * Sets scenario reported total.
     *
     * @param reportedTotal reported total
     */
    public void setReportedTotal(BigDecimal reportedTotal) {
        this.reportedTotal = reportedTotal;
    }

    /**
     * @return description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets scenario description.
     *
     * @param description description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return status.
     */
    public ScenarioStatusEnum getStatus() {
        return status;
    }

    /**
     * Sets scenario status.
     *
     * @param status status
     */
    public void setStatus(ScenarioStatusEnum status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        Scenario that = (Scenario) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(this.name, that.name)
            .append(this.netTotal, that.netTotal)
            .append(this.grossTotal, that.grossTotal)
            .append(this.reportedTotal, that.reportedTotal)
            .append(this.description, that.description)
            .append(this.status, that.status)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(name)
            .append(netTotal)
            .append(grossTotal)
            .append(reportedTotal)
            .append(description)
            .append(status)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .appendSuper(super.toString())
            .append("name", name)
            .append("netTotal", netTotal)
            .append("grossTotal", grossTotal)
            .append("reportedTotal", reportedTotal)
            .append("description", description)
            .append("status", status)
            .toString();
    }
}
