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
    private UsageStatusEnum usageStatus;
    private UdmUsageOriginEnum udmUsageOrigin;
    private Integer period;
    private Set<String> assignees = new HashSet<>();
    private Set<String> reportedPubTypes = new HashSet<>();
    private Set<String> pubFormats = new HashSet<>();
    private Set<DetailLicenseeClass> detailLicenseeClasses = new HashSet<>();
    private Set<String> reportedTypeOfUses = new HashSet<>();
    private UdmChannelEnum channel;
    private LocalDate usageDateFrom;
    private LocalDate usageDateTo;
    private LocalDate surveyStartDateFrom;
    private LocalDate surveyStartDateTo;
    private FilterExpression<Number> annualMultiplierExpression = new FilterExpression<>();
    private FilterExpression<Number> annualizedCopiesExpression = new FilterExpression<>();
    private FilterExpression<Number> statisticalMultiplierExpression = new FilterExpression<>();
    private FilterExpression<Number> quantityExpression = new FilterExpression<>();
    private String surveyCountry;
    private String language;
    private String companyName;
    private Long companyId;
    private Long wrWrkInst;
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
            setUsageStatus(filter.getUsageStatus());
            setUdmUsageOrigin(filter.getUdmUsageOrigin());
            setPeriod(filter.getPeriod());
            setAssignees(filter.getAssignees());
            setReportedPubTypes(filter.getReportedPubTypes());
            setPubFormats(filter.getPubFormats());
            setDetailLicenseeClasses(filter.getDetailLicenseeClasses());
            setChannel(filter.getChannel());
            setUsageDateFrom(filter.getUsageDateFrom());
            setUsageDateTo(filter.getUsageDateTo());
            setSurveyStartDateFrom(filter.getSurveyStartDateFrom());
            setSurveyStartDateTo(filter.getSurveyStartDateTo());
            setAnnualMultiplierExpression(filter.getAnnualMultiplierExpression());
            setAnnualizedCopiesExpression(filter.getAnnualizedCopiesExpression());
            setStatisticalMultiplierExpression(filter.getStatisticalMultiplierExpression());
            setReportedTypeOfUses(filter.getReportedTypeOfUses());
            setQuantityExpression(filter.getQuantityExpression());
            setSurveyCountry(filter.getSurveyCountry());
            setLanguage(filter.getLanguage());
            setCompanyId(filter.getCompanyId());
            setCompanyName(filter.getCompanyName());
            setWrWrkInst(filter.getWrWrkInst());
            setSearchValue(filter.getSearchValue());
        }
    }

    public Set<String> getUdmBatchesIds() {
        return udmBatchesIds;
    }

    public void setUdmBatchesIds(Set<String> udmBatchesIds) {
        this.udmBatchesIds = udmBatchesIds;
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

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Set<String> getAssignees() {
        return assignees;
    }

    public void setAssignees(Set<String> assignees) {
        this.assignees = assignees;
    }

    public Set<String> getReportedPubTypes() {
        return reportedPubTypes;
    }

    public void setReportedPubTypes(Set<String> reportedPubTypes) {
        this.reportedPubTypes = reportedPubTypes;
    }

    public Set<String> getPubFormats() {
        return pubFormats;
    }

    public void setPubFormats(Set<String> pubFormats) {
        this.pubFormats = pubFormats;
    }

    public Set<DetailLicenseeClass> getDetailLicenseeClasses() {
        return detailLicenseeClasses;
    }

    public void setDetailLicenseeClasses(Set<DetailLicenseeClass> detailLicenseeClasses) {
        this.detailLicenseeClasses = detailLicenseeClasses;
    }

    public UdmChannelEnum getChannel() {
        return channel;
    }

    public void setChannel(UdmChannelEnum channel) {
        this.channel = channel;
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

    public Set<String> getReportedTypeOfUses() {
        return reportedTypeOfUses;
    }

    public void setReportedTypeOfUses(Set<String> reportedTypeOfUses) {
        this.reportedTypeOfUses = reportedTypeOfUses;
    }

    public FilterExpression<Number> getQuantityExpression() {
        return quantityExpression;
    }

    public void setQuantityExpression(FilterExpression<Number> quantityExpression) {
        this.quantityExpression = quantityExpression;
    }

    public String getSurveyCountry() {
        return surveyCountry;
    }

    public void setSurveyCountry(String surveyCountry) {
        this.surveyCountry = surveyCountry;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getWrWrkInst() {
        return wrWrkInst;
    }

    public void setWrWrkInst(Long wrWrkInst) {
        this.wrWrkInst = wrWrkInst;
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
            && null == usageStatus
            && null == udmUsageOrigin
            && null == period
            && null == channel
            && null == usageDateFrom
            && null == usageDateTo
            && null == surveyStartDateFrom
            && null == surveyStartDateTo
            && null == surveyCountry
            && null == language
            && null == companyName
            && null == companyId
            && null == wrWrkInst
            && annualMultiplierExpression.isEmpty()
            && annualizedCopiesExpression.isEmpty()
            && statisticalMultiplierExpression.isEmpty()
            && quantityExpression.isEmpty()
            && CollectionUtils.isEmpty(assignees)
            && CollectionUtils.isEmpty(reportedPubTypes)
            && CollectionUtils.isEmpty(pubFormats)
            && CollectionUtils.isEmpty(detailLicenseeClasses)
            && CollectionUtils.isEmpty(reportedTypeOfUses)
            && StringUtils.isBlank(searchValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (null == o || getClass() != o.getClass()) {
            return false;
        }
        UdmUsageFilter that = (UdmUsageFilter) o;
        return new EqualsBuilder()
            .append(udmBatchesIds, that.udmBatchesIds)
            .append(usageStatus, that.usageStatus)
            .append(udmUsageOrigin, that.udmUsageOrigin)
            .append(period, that.period)
            .append(assignees, that.assignees)
            .append(reportedPubTypes, that.reportedPubTypes)
            .append(pubFormats, that.pubFormats)
            .append(detailLicenseeClasses, that.detailLicenseeClasses)
            .append(channel, that.channel)
            .append(usageDateFrom, that.usageDateFrom)
            .append(usageDateTo, that.usageDateTo)
            .append(surveyStartDateFrom, that.surveyStartDateFrom)
            .append(surveyStartDateTo, that.surveyStartDateTo)
            .append(annualMultiplierExpression, that.annualMultiplierExpression)
            .append(annualizedCopiesExpression, that.annualizedCopiesExpression)
            .append(statisticalMultiplierExpression, that.statisticalMultiplierExpression)
            .append(reportedTypeOfUses, that.reportedTypeOfUses)
            .append(quantityExpression, that.quantityExpression)
            .append(surveyCountry, that.surveyCountry)
            .append(language, that.language)
            .append(companyName, that.companyName)
            .append(companyId, that.companyId)
            .append(wrWrkInst, that.wrWrkInst)
            .append(searchValue, that.searchValue)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(udmBatchesIds)
            .append(usageStatus)
            .append(udmUsageOrigin)
            .append(period)
            .append(assignees)
            .append(reportedPubTypes)
            .append(pubFormats)
            .append(detailLicenseeClasses)
            .append(channel)
            .append(usageDateFrom)
            .append(usageDateTo)
            .append(surveyStartDateFrom)
            .append(surveyStartDateTo)
            .append(annualMultiplierExpression)
            .append(annualizedCopiesExpression)
            .append(statisticalMultiplierExpression)
            .append(reportedTypeOfUses)
            .append(quantityExpression)
            .append(surveyCountry)
            .append(language)
            .append(companyName)
            .append(companyId)
            .append(wrWrkInst)
            .append(searchValue)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("udmBatchesIds", udmBatchesIds)
            .append("usageStatus", usageStatus)
            .append("udmUsageOrigin", udmUsageOrigin)
            .append("period", period)
            .append("assignees", assignees)
            .append("reportedPubTypes", reportedPubTypes)
            .append("pubFormats", pubFormats)
            .append("detailLicenseeClasses", detailLicenseeClasses)
            .append("channel", channel)
            .append("usageDateFrom", usageDateFrom)
            .append("usageDateTo", usageDateTo)
            .append("surveyStartDateFrom", surveyStartDateFrom)
            .append("surveyStartDateTo", surveyStartDateTo)
            .append("annualMultiplierExpression", annualMultiplierExpression)
            .append("annualizedCopiesExpression", annualizedCopiesExpression)
            .append("statisticalMultiplierExpression", statisticalMultiplierExpression)
            .append("reportedTypeOfUses", reportedTypeOfUses)
            .append("quantityExpression", quantityExpression)
            .append("surveyCountry", surveyCountry)
            .append("language", language)
            .append("companyName", companyName)
            .append("companyId", companyId)
            .append("wrWrkInst", wrWrkInst)
            .append("searchValue", searchValue)
            .toString();
    }
}
