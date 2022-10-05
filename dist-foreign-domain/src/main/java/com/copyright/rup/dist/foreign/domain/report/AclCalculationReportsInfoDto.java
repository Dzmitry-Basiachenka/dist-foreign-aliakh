package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.foreign.domain.AclScenario;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;
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

    private String reportName;
    private List<AclScenario> scenarios;
    private Integer period;
    private String user;
    private LocalDateTime reportDateTime;

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public List<AclScenario> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<AclScenario> scenarios) {
        this.scenarios = scenarios;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDateTime getReportDateTime() {
        return reportDateTime;
    }

    public void setReportDateTime(LocalDateTime reportDateTime) {
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
            .append(reportName, that.reportName)
            .append(scenarios, that.scenarios)
            .append(period, that.period)
            .append(user, that.user)
            .append(reportDateTime, that.reportDateTime)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(reportName)
            .append(scenarios)
            .append(period)
            .append(user)
            .append(reportDateTime)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("reportName", reportName)
            .append("scenarios", scenarios)
            .append("period", period)
            .append("user", user)
            .append("reportDateTime", reportDateTime)
            .toString();
    }
}
