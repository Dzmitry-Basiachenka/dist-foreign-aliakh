package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.foreign.domain.AclScenario;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Represents domain object for filter and metadata ACL calculation reports.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/05/2022
 *
 * @author Mikita Maistrenka
 */
public class AclCalculationReportsInfoDto {

    private List<AclScenario> scenarios;
    private List<AclScenario> previousScenarios;
    private Integer period;
    private Integer previousPeriod;
    private String user;
    private OffsetDateTime reportDateTime;

    public List<AclScenario> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<AclScenario> scenarios) {
        this.scenarios = scenarios;
    }

    public List<AclScenario> getPreviousScenarios() {
        return previousScenarios;
    }

    public void setPreviousScenarios(List<AclScenario> previousScenarios) {
        this.previousScenarios = previousScenarios;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getPreviousPeriod() {
        return previousPeriod;
    }

    public void setPreviousPeriod(Integer previousPeriod) {
        this.previousPeriod = previousPeriod;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public OffsetDateTime getReportDateTime() {
        return reportDateTime;
    }

    public void setReportDateTime(OffsetDateTime reportDateTime) {
        this.reportDateTime = reportDateTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclCalculationReportsInfoDto that = (AclCalculationReportsInfoDto) obj;
        return new EqualsBuilder()
            .append(scenarios, that.scenarios)
            .append(previousScenarios, that.previousScenarios)
            .append(period, that.period)
            .append(previousPeriod, that.previousPeriod)
            .append(user, that.user)
            .append(reportDateTime, that.reportDateTime)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(scenarios)
            .append(previousScenarios)
            .append(period)
            .append(previousPeriod)
            .append(user)
            .append(reportDateTime)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("scenarios", scenarios)
            .append("previousScenarios", previousScenarios)
            .append("period", period)
            .append("previousPeriod", previousPeriod)
            .append("user", user)
            .append("reportDateTime", reportDateTime)
            .toString();
    }
}
