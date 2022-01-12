package com.copyright.rup.dist.foreign.domain.filter;

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Class represents a set of filtering criteria.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/04/21
 *
 * @author Dzmitry Basiachenka
 */
public class UdmUsageFilter {

    private Set<String> udmBatchesIds = new HashSet<>();
    private Set<Integer> periods = new HashSet<>();
    private UsageStatusEnum usageStatus;
    private UdmUsageOriginEnum udmUsageOrigin;
    private Set<String> assignees = new HashSet<>();
    private Set<DetailLicenseeClass> detailLicenseeClasses = new HashSet<>();
    private Set<String> reportedPubTypes = new HashSet<>();
    private Set<String> reportedTypeOfUses = new HashSet<>();
    private Set<String> pubFormats = new HashSet<>();
    private LocalDate usageDateFrom;
    private LocalDate usageDateTo;
    private LocalDate surveyStartDateFrom;
    private LocalDate surveyStartDateTo;
    private UdmChannelEnum channel;
    private FilterExpression<Number> wrWrkInstExpression = new FilterExpression<>();
    private FilterExpression<String> usageDetailIdExpression = new FilterExpression<>();
    private FilterExpression<Number> companyIdExpression = new FilterExpression<>();
    private FilterExpression<String> companyNameExpression = new FilterExpression<>();
    private FilterExpression<String> surveyCountryExpression = new FilterExpression<>();
    private FilterExpression<String> languageExpression = new FilterExpression<>();
    private FilterExpression<Number> annualMultiplierExpression = new FilterExpression<>();
    private FilterExpression<Number> annualizedCopiesExpression = new FilterExpression<>();
    private FilterExpression<Number> statisticalMultiplierExpression = new FilterExpression<>();
    private FilterExpression<Number> quantityExpression = new FilterExpression<>();
    private String searchValue;

    /**
     * Default constructor.
     */
    public UdmUsageFilter() {
    }

    /**
     * Constructs new filter based on existing.
     *
     * @param filter base filter
     */
    public UdmUsageFilter(UdmUsageFilter filter) {
        if (null != filter) {
            setUdmBatchesIds(filter.getUdmBatchesIds());
            setPeriods(filter.getPeriods());
            setUsageStatus(filter.getUsageStatus());
            setUdmUsageOrigin(filter.getUdmUsageOrigin());
            setAssignees(filter.getAssignees());
            setDetailLicenseeClasses(filter.getDetailLicenseeClasses());
            setReportedPubTypes(filter.getReportedPubTypes());
            setReportedTypeOfUses(filter.getReportedTypeOfUses());
            setPubFormats(filter.getPubFormats());
            setUsageDateFrom(filter.getUsageDateFrom());
            setUsageDateTo(filter.getUsageDateTo());
            setSurveyStartDateFrom(filter.getSurveyStartDateFrom());
            setSurveyStartDateTo(filter.getSurveyStartDateTo());
            setChannel(filter.getChannel());
            setWrWrkInstExpression(filter.getWrWrkInstExpression());
            setUsageDetailIdExpression(filter.getUsageDetailIdExpression());
            setCompanyIdExpression(filter.getCompanyIdExpression());
            setCompanyNameExpression(filter.getCompanyNameExpression());
            setSurveyCountryExpression(filter.getSurveyCountryExpression());
            setLanguageExpression(filter.getLanguageExpression());
            setAnnualMultiplierExpression(filter.getAnnualMultiplierExpression());
            setAnnualizedCopiesExpression(filter.getAnnualizedCopiesExpression());
            setStatisticalMultiplierExpression(filter.getStatisticalMultiplierExpression());
            setQuantityExpression(filter.getQuantityExpression());
            setSearchValue(filter.getSearchValue());
        }
    }

    public Set<String> getUdmBatchesIds() {
        return udmBatchesIds;
    }

    public void setUdmBatchesIds(Set<String> udmBatchesIds) {
        this.udmBatchesIds = udmBatchesIds;
    }

    public Set<Integer> getPeriods() {
        return periods;
    }

    public void setPeriods(Set<Integer> periods) {
        this.periods = periods;
    }

    public UsageStatusEnum getUsageStatus() {
        return usageStatus;
    }

    public void setUsageStatus(UsageStatusEnum usageStatus) {
        this.usageStatus = usageStatus;
    }

    public UdmUsageOriginEnum getUdmUsageOrigin() {
        return udmUsageOrigin;
    }

    public void setUdmUsageOrigin(UdmUsageOriginEnum udmUsageOrigin) {
        this.udmUsageOrigin = udmUsageOrigin;
    }

    public Set<String> getAssignees() {
        return assignees;
    }

    public void setAssignees(Set<String> assignees) {
        this.assignees = assignees;
    }

    public Set<DetailLicenseeClass> getDetailLicenseeClasses() {
        return detailLicenseeClasses;
    }

    public void setDetailLicenseeClasses(Set<DetailLicenseeClass> detailLicenseeClasses) {
        this.detailLicenseeClasses = detailLicenseeClasses;
    }

    public Set<String> getReportedPubTypes() {
        return reportedPubTypes;
    }

    public void setReportedPubTypes(Set<String> reportedPubTypes) {
        this.reportedPubTypes = reportedPubTypes;
    }

    public Set<String> getReportedTypeOfUses() {
        return reportedTypeOfUses;
    }

    public void setReportedTypeOfUses(Set<String> reportedTypeOfUses) {
        this.reportedTypeOfUses = reportedTypeOfUses;
    }

    public Set<String> getPubFormats() {
        return pubFormats;
    }

    public void setPubFormats(Set<String> pubFormats) {
        this.pubFormats = pubFormats;
    }

    public LocalDate getUsageDateFrom() {
        return usageDateFrom;
    }

    public void setUsageDateFrom(LocalDate usageDateFrom) {
        this.usageDateFrom = usageDateFrom;
    }

    public LocalDate getUsageDateTo() {
        return usageDateTo;
    }

    public void setUsageDateTo(LocalDate usageDateTo) {
        this.usageDateTo = usageDateTo;
    }

    public LocalDate getSurveyStartDateFrom() {
        return surveyStartDateFrom;
    }

    public void setSurveyStartDateFrom(LocalDate surveyStartDateFrom) {
        this.surveyStartDateFrom = surveyStartDateFrom;
    }

    public LocalDate getSurveyStartDateTo() {
        return surveyStartDateTo;
    }

    public void setSurveyStartDateTo(LocalDate surveyStartDateTo) {
        this.surveyStartDateTo = surveyStartDateTo;
    }

    public UdmChannelEnum getChannel() {
        return channel;
    }

    public void setChannel(UdmChannelEnum channel) {
        this.channel = channel;
    }

    public FilterExpression<Number> getWrWrkInstExpression() {
        return wrWrkInstExpression;
    }

    public void setWrWrkInstExpression(FilterExpression<Number> wrWrkInstExpression) {
        this.wrWrkInstExpression = wrWrkInstExpression;
    }

    public FilterExpression<String> getUsageDetailIdExpression() {
        return usageDetailIdExpression;
    }

    public void setUsageDetailIdExpression(FilterExpression<String> usageDetailIdExpression) {
        this.usageDetailIdExpression = usageDetailIdExpression;
    }

    public FilterExpression<Number> getCompanyIdExpression() {
        return companyIdExpression;
    }

    public void setCompanyIdExpression(FilterExpression<Number> companyIdExpression) {
        this.companyIdExpression = companyIdExpression;
    }

    public FilterExpression<String> getCompanyNameExpression() {
        return companyNameExpression;
    }

    public void setCompanyNameExpression(FilterExpression<String> companyNameExpression) {
        this.companyNameExpression = companyNameExpression;
    }

    public FilterExpression<String> getSurveyCountryExpression() {
        return surveyCountryExpression;
    }

    public void setSurveyCountryExpression(FilterExpression<String> surveyCountryExpression) {
        this.surveyCountryExpression = surveyCountryExpression;
    }

    public FilterExpression<String> getLanguageExpression() {
        return languageExpression;
    }

    public void setLanguageExpression(FilterExpression<String> languageExpression) {
        this.languageExpression = languageExpression;
    }

    public FilterExpression<Number> getAnnualMultiplierExpression() {
        return annualMultiplierExpression;
    }

    public void setAnnualMultiplierExpression(FilterExpression<Number> annualMultiplierExpression) {
        this.annualMultiplierExpression = annualMultiplierExpression;
    }

    public FilterExpression<Number> getAnnualizedCopiesExpression() {
        return annualizedCopiesExpression;
    }

    public void setAnnualizedCopiesExpression(FilterExpression<Number> annualizedCopiesExpression) {
        this.annualizedCopiesExpression = annualizedCopiesExpression;
    }

    public FilterExpression<Number> getStatisticalMultiplierExpression() {
        return statisticalMultiplierExpression;
    }

    public void setStatisticalMultiplierExpression(FilterExpression<Number> statisticalMultiplierExpression) {
        this.statisticalMultiplierExpression = statisticalMultiplierExpression;
    }

    public FilterExpression<Number> getQuantityExpression() {
        return quantityExpression;
    }

    public void setQuantityExpression(FilterExpression<Number> quantityExpression) {
        this.quantityExpression = quantityExpression;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    /**
     * @return {@code true} if filter does not contain any criteria, otherwise {@code false}.
     */
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(udmBatchesIds)
            && CollectionUtils.isEmpty(periods)
            && null == usageStatus
            && null == udmUsageOrigin
            && CollectionUtils.isEmpty(assignees)
            && CollectionUtils.isEmpty(detailLicenseeClasses)
            && CollectionUtils.isEmpty(reportedPubTypes)
            && CollectionUtils.isEmpty(reportedTypeOfUses)
            && CollectionUtils.isEmpty(pubFormats)
            && null == usageDateFrom
            && null == usageDateTo
            && null == surveyStartDateFrom
            && null == surveyStartDateTo
            && null == channel
            && wrWrkInstExpression.isEmpty()
            && usageDetailIdExpression.isEmpty()
            && companyIdExpression.isEmpty()
            && companyNameExpression.isEmpty()
            && surveyCountryExpression.isEmpty()
            && languageExpression.isEmpty()
            && annualMultiplierExpression.isEmpty()
            && annualizedCopiesExpression.isEmpty()
            && statisticalMultiplierExpression.isEmpty()
            && quantityExpression.isEmpty()
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
        UdmUsageFilter that = (UdmUsageFilter) obj;
        return new EqualsBuilder()
            .append(udmBatchesIds, that.udmBatchesIds)
            .append(periods, that.periods)
            .append(usageStatus, that.usageStatus)
            .append(udmUsageOrigin, that.udmUsageOrigin)
            .append(assignees, that.assignees)
            .append(detailLicenseeClasses, that.detailLicenseeClasses)
            .append(reportedPubTypes, that.reportedPubTypes)
            .append(reportedTypeOfUses, that.reportedTypeOfUses)
            .append(pubFormats, that.pubFormats)
            .append(usageDateFrom, that.usageDateFrom)
            .append(usageDateTo, that.usageDateTo)
            .append(surveyStartDateFrom, that.surveyStartDateFrom)
            .append(surveyStartDateTo, that.surveyStartDateTo)
            .append(channel, that.channel)
            .append(wrWrkInstExpression, that.wrWrkInstExpression)
            .append(usageDetailIdExpression, that.usageDetailIdExpression)
            .append(companyIdExpression, that.companyIdExpression)
            .append(companyNameExpression, that.companyNameExpression)
            .append(surveyCountryExpression, that.surveyCountryExpression)
            .append(languageExpression, that.languageExpression)
            .append(annualMultiplierExpression, that.annualMultiplierExpression)
            .append(annualizedCopiesExpression, that.annualizedCopiesExpression)
            .append(statisticalMultiplierExpression, that.statisticalMultiplierExpression)
            .append(quantityExpression, that.quantityExpression)
            .append(searchValue, that.searchValue)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(udmBatchesIds)
            .append(periods)
            .append(usageStatus)
            .append(udmUsageOrigin)
            .append(assignees)
            .append(detailLicenseeClasses)
            .append(reportedPubTypes)
            .append(reportedTypeOfUses)
            .append(pubFormats)
            .append(usageDateFrom)
            .append(usageDateTo)
            .append(surveyStartDateFrom)
            .append(surveyStartDateTo)
            .append(channel)
            .append(wrWrkInstExpression)
            .append(usageDetailIdExpression)
            .append(companyIdExpression)
            .append(companyNameExpression)
            .append(surveyCountryExpression)
            .append(languageExpression)
            .append(annualMultiplierExpression)
            .append(annualizedCopiesExpression)
            .append(statisticalMultiplierExpression)
            .append(quantityExpression)
            .append(searchValue)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("udmBatchesIds", udmBatchesIds)
            .append("periods", periods)
            .append("usageStatus", usageStatus)
            .append("udmUsageOrigin", udmUsageOrigin)
            .append("assignees", assignees)
            .append("detailLicenseeClasses", detailLicenseeClasses)
            .append("reportedPubTypes", reportedPubTypes)
            .append("reportedTypeOfUses", reportedTypeOfUses)
            .append("pubFormats", pubFormats)
            .append("usageDateFrom", usageDateFrom)
            .append("usageDateTo", usageDateTo)
            .append("surveyStartDateFrom", surveyStartDateFrom)
            .append("surveyStartDateTo", surveyStartDateTo)
            .append("channel", channel)
            .append("wrWrkInstExpression", wrWrkInstExpression)
            .append("usageDetailIdExpression", usageDetailIdExpression)
            .append("companyIdExpression", companyIdExpression)
            .append("companyNameExpression", companyNameExpression)
            .append("surveyCountryExpression", surveyCountryExpression)
            .append("languageExpression", languageExpression)
            .append("annualMultiplierExpression", annualMultiplierExpression)
            .append("annualizedCopiesExpression", annualizedCopiesExpression)
            .append("statisticalMultiplierExpression", statisticalMultiplierExpression)
            .append("quantityExpression", quantityExpression)
            .append("searchValue", searchValue)
            .toString();
    }
}
