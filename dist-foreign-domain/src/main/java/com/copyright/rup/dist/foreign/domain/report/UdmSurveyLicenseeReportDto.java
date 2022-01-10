package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;

/**
 * Represents UDM Survey Licensee Report record.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/05/2022
 *
 * @author Anton Azarenka
 */
public class UdmSurveyLicenseeReportDto extends StoredEntity<String> {

    private Integer period;
    private String channel;
    private String usageOrigin;
    private LocalDate surveyStartDate;
    private Long companyId;
    private String companyName;
    private Integer detailLicenseeClassId;
    private String detailLicenseeClassName;
    private Integer aggregateClassId;
    private String aggregateClassName;
    private Integer numberOfTotalRows;
    private Integer numberOfUsableRows;
    private Integer percentUsable;
    private Integer numberOfRegisteredUsers;
    private Integer numberOfRowsByRegisteredUsers;
    private Integer numberOfUsableRowsByRegisteredUsers;
    private Integer percentUsableFromRegisteredUsers;
    private Integer numberOfRowsByUnregisteredUsers;
    private Integer numberOfUsableRowsByUnregisteredUsers;
    private Integer percentUsableFromUnregisteredUsers;

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getUsageOrigin() {
        return usageOrigin;
    }

    public void setUsageOrigin(String usageOrigin) {
        this.usageOrigin = usageOrigin;
    }

    public LocalDate getSurveyStartDate() {
        return surveyStartDate;
    }

    public void setSurveyStartDate(LocalDate surveyStartDate) {
        this.surveyStartDate = surveyStartDate;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getDetailLicenseeClassId() {
        return detailLicenseeClassId;
    }

    public void setDetailLicenseeClassId(Integer detailLicenseeClassId) {
        this.detailLicenseeClassId = detailLicenseeClassId;
    }

    public String getDetailLicenseeClassName() {
        return detailLicenseeClassName;
    }

    public void setDetailLicenseeClassName(String detailLicenseeClassName) {
        this.detailLicenseeClassName = detailLicenseeClassName;
    }

    public Integer getAggregateClassId() {
        return aggregateClassId;
    }

    public void setAggregateClassId(Integer aggregateClassId) {
        this.aggregateClassId = aggregateClassId;
    }

    public String getAggregateClassName() {
        return aggregateClassName;
    }

    public void setAggregateClassName(String aggregateClassName) {
        this.aggregateClassName = aggregateClassName;
    }

    public Integer getNumberOfTotalRows() {
        return numberOfTotalRows;
    }

    public void setNumberOfTotalRows(Integer numberOfTotalRows) {
        this.numberOfTotalRows = numberOfTotalRows;
    }

    public Integer getNumberOfUsableRows() {
        return numberOfUsableRows;
    }

    public void setNumberOfUsableRows(Integer numberOfUsableRows) {
        this.numberOfUsableRows = numberOfUsableRows;
    }

    public Integer getPercentUsable() {
        return percentUsable;
    }

    public void setPercentUsable(Integer percentUsable) {
        this.percentUsable = percentUsable;
    }

    public Integer getNumberOfRegisteredUsers() {
        return numberOfRegisteredUsers;
    }

    public void setNumberOfRegisteredUsers(Integer numberOfRegisteredUsers) {
        this.numberOfRegisteredUsers = numberOfRegisteredUsers;
    }

    public Integer getNumberOfRowsByRegisteredUsers() {
        return numberOfRowsByRegisteredUsers;
    }

    public void setNumberOfRowsByRegisteredUsers(Integer numberOfRowsByRegisteredUsers) {
        this.numberOfRowsByRegisteredUsers = numberOfRowsByRegisteredUsers;
    }

    public Integer getNumberOfUsableRowsByRegisteredUsers() {
        return numberOfUsableRowsByRegisteredUsers;
    }

    public void setNumberOfUsableRowsByRegisteredUsers(Integer numberOfUsableRowsByRegisteredUsers) {
        this.numberOfUsableRowsByRegisteredUsers = numberOfUsableRowsByRegisteredUsers;
    }

    public Integer getPercentUsableFromRegisteredUsers() {
        return percentUsableFromRegisteredUsers;
    }

    public void setPercentUsableFromRegisteredUsers(Integer percentUsableFromRegisteredUsers) {
        this.percentUsableFromRegisteredUsers = percentUsableFromRegisteredUsers;
    }

    public Integer getNumberOfRowsByUnregisteredUsers() {
        return numberOfRowsByUnregisteredUsers;
    }

    public void setNumberOfRowsByUnregisteredUsers(Integer numberOfRowsByUnregisteredUsers) {
        this.numberOfRowsByUnregisteredUsers = numberOfRowsByUnregisteredUsers;
    }

    public Integer getNumberOfUsableRowsByUnregisteredUsers() {
        return numberOfUsableRowsByUnregisteredUsers;
    }

    public void setNumberOfUsableRowsByUnregisteredUsers(Integer numberOfUsableRowsByUnregisteredUsers) {
        this.numberOfUsableRowsByUnregisteredUsers = numberOfUsableRowsByUnregisteredUsers;
    }

    public Integer getPercentUsableFromUnregisteredUsers() {
        return percentUsableFromUnregisteredUsers;
    }

    public void setPercentUsableFromUnregisteredUsers(Integer percentUsableFromUnregisteredUsers) {
        this.percentUsableFromUnregisteredUsers = percentUsableFromUnregisteredUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (null == o || getClass() != o.getClass()) {
            return false;
        }
        UdmSurveyLicenseeReportDto that = (UdmSurveyLicenseeReportDto) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(period, that.period)
            .append(channel, that.channel)
            .append(usageOrigin, that.usageOrigin)
            .append(surveyStartDate, that.surveyStartDate)
            .append(companyId, that.companyId)
            .append(companyName, that.companyName)
            .append(detailLicenseeClassId, that.detailLicenseeClassId)
            .append(detailLicenseeClassName, that.detailLicenseeClassName)
            .append(aggregateClassId, that.aggregateClassId)
            .append(aggregateClassName, that.aggregateClassName)
            .append(numberOfTotalRows, that.numberOfTotalRows)
            .append(numberOfUsableRows, that.numberOfUsableRows)
            .append(percentUsable, that.percentUsable)
            .append(numberOfRegisteredUsers, that.numberOfRegisteredUsers)
            .append(numberOfRowsByRegisteredUsers, that.numberOfRowsByRegisteredUsers)
            .append(numberOfUsableRowsByRegisteredUsers, that.numberOfUsableRowsByRegisteredUsers)
            .append(percentUsableFromRegisteredUsers, that.percentUsableFromRegisteredUsers)
            .append(numberOfRowsByUnregisteredUsers, that.numberOfRowsByUnregisteredUsers)
            .append(numberOfUsableRowsByUnregisteredUsers, that.numberOfUsableRowsByUnregisteredUsers)
            .append(percentUsableFromUnregisteredUsers, that.percentUsableFromUnregisteredUsers)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(period)
            .append(channel)
            .append(usageOrigin)
            .append(surveyStartDate)
            .append(companyId)
            .append(companyName)
            .append(detailLicenseeClassId)
            .append(detailLicenseeClassName)
            .append(aggregateClassId)
            .append(aggregateClassName)
            .append(numberOfTotalRows)
            .append(numberOfUsableRows)
            .append(percentUsable)
            .append(numberOfRegisteredUsers)
            .append(numberOfRowsByRegisteredUsers)
            .append(numberOfUsableRowsByRegisteredUsers)
            .append(percentUsableFromRegisteredUsers)
            .append(numberOfRowsByUnregisteredUsers)
            .append(numberOfUsableRowsByUnregisteredUsers)
            .append(percentUsableFromUnregisteredUsers)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("period", period)
            .append("channel", channel)
            .append("usageOrigin", usageOrigin)
            .append("surveyStartDate", surveyStartDate)
            .append("companyId", companyId)
            .append("companyName", companyName)
            .append("detailLicenseeClassId", detailLicenseeClassId)
            .append("detailLicenseeClassName", detailLicenseeClassName)
            .append("aggregateClassId", aggregateClassId)
            .append("aggregateClassName", aggregateClassName)
            .append("numberOfTotalRows", numberOfTotalRows)
            .append("numberOfUsableRows", numberOfUsableRows)
            .append("percentUsable", percentUsable)
            .append("numberOfRegisteredUsers", numberOfRegisteredUsers)
            .append("numberOfRowsByRegisteredUsers", numberOfRowsByRegisteredUsers)
            .append("numberOfUsableRowsByRegisteredUsers", numberOfUsableRowsByRegisteredUsers)
            .append("percentUsableFromRegisteredUsers", percentUsableFromRegisteredUsers)
            .append("numberOfRowsByUnregisteredUsers", numberOfRowsByUnregisteredUsers)
            .append("numberOfUsableRowsByUnregisteredUsers", numberOfUsableRowsByUnregisteredUsers)
            .append("percentUsableFromUnregisteredUsers", percentUsableFromUnregisteredUsers)
            .toString();
    }
}
