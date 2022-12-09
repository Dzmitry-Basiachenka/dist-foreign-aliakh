package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;

/**
 * Represents UDM Survey Dashboard Report record.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/08/2022
 *
 * @author Anton Azarenka
 */
public class UdmSurveyDashboardReportDto extends StoredEntity<String> {

    private Integer period;
    private String channel;
    private String usageOrigin;
    private LocalDate surveyStartDate;
    private Long companyId;
    private String companyName;
    private Integer detailLicenseeClassId;
    private String detailLicenseeClassName;
    private Integer unadjustedUniqueUsers;
    private Integer adjustedUniqueUsers;
    private Integer numberOfLoadedDetails;
    private Integer numberOfUsableLoadedDetails;
    private Integer numberOfSurveys;
    private Integer periodMonthOrder;

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

    public Integer getUnadjustedUniqueUsers() {
        return unadjustedUniqueUsers;
    }

    public void setUnadjustedUniqueUsers(Integer unadjustedUniqueUsers) {
        this.unadjustedUniqueUsers = unadjustedUniqueUsers;
    }

    public Integer getAdjustedUniqueUsers() {
        return adjustedUniqueUsers;
    }

    public void setAdjustedUniqueUsers(Integer adjustedUniqueUsers) {
        this.adjustedUniqueUsers = adjustedUniqueUsers;
    }

    public Integer getNumberOfLoadedDetails() {
        return numberOfLoadedDetails;
    }

    public void setNumberOfLoadedDetails(Integer numberOfLoadedDetails) {
        this.numberOfLoadedDetails = numberOfLoadedDetails;
    }

    public Integer getNumberOfUsableLoadedDetails() {
        return numberOfUsableLoadedDetails;
    }

    public void setNumberOfUsableLoadedDetails(Integer numberOfUsableLoadedDetails) {
        this.numberOfUsableLoadedDetails = numberOfUsableLoadedDetails;
    }

    public Integer getNumberOfSurveys() {
        return numberOfSurveys;
    }

    public void setNumberOfSurveys(Integer numberOfSurveys) {
        this.numberOfSurveys = numberOfSurveys;
    }

    public Integer getPeriodMonthOrder() {
        return periodMonthOrder;
    }

    public void setPeriodMonthOrder(Integer periodMonthOrder) {
        this.periodMonthOrder = periodMonthOrder;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        UdmSurveyDashboardReportDto that = (UdmSurveyDashboardReportDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(period, that.period)
            .append(channel, that.channel)
            .append(usageOrigin, that.usageOrigin)
            .append(surveyStartDate, that.surveyStartDate)
            .append(companyId, that.companyId)
            .append(companyName, that.companyName)
            .append(detailLicenseeClassId, that.detailLicenseeClassId)
            .append(detailLicenseeClassName, that.detailLicenseeClassName)
            .append(unadjustedUniqueUsers, that.unadjustedUniqueUsers)
            .append(adjustedUniqueUsers, that.adjustedUniqueUsers)
            .append(numberOfLoadedDetails, that.numberOfLoadedDetails)
            .append(numberOfUsableLoadedDetails, that.numberOfUsableLoadedDetails)
            .append(numberOfSurveys, that.numberOfSurveys)
            .append(periodMonthOrder, that.periodMonthOrder)
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
            .append(unadjustedUniqueUsers)
            .append(adjustedUniqueUsers)
            .append(numberOfLoadedDetails)
            .append(numberOfUsableLoadedDetails)
            .append(numberOfSurveys)
            .append(periodMonthOrder)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("period", period)
            .append("channel", channel)
            .append("usageOrigin", usageOrigin)
            .append("surveyStartDate", surveyStartDate)
            .append("companyId", companyId)
            .append("companyName", companyName)
            .append("detailLicenseeClassId", detailLicenseeClassId)
            .append("detailLicenseeClassName", detailLicenseeClassName)
            .append("unadjustedUniqueUsers", unadjustedUniqueUsers)
            .append("adjustedUniqueUsers", adjustedUniqueUsers)
            .append("numberOfLoadedDetails", numberOfLoadedDetails)
            .append("numberOfUsableLoadedDetails", numberOfUsableLoadedDetails)
            .append("numberOfSurveys", numberOfSurveys)
            .append("periodMonthOrder", periodMonthOrder)
            .toString();
    }
}
