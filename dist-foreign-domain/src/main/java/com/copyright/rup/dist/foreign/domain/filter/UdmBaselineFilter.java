package com.copyright.rup.dist.foreign.domain.filter;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Class represents a set of filtering criteria for UDM baseline.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/21
 *
 * @author Uladzislau Shalamitski
 */
public class UdmBaselineFilter {

    private Set<Integer> periods = new HashSet<>();
    private UdmChannelEnum channel;
    private UdmUsageOriginEnum udmUsageOrigin;
    private Set<DetailLicenseeClass> detailLicenseeClasses = new HashSet<>();
    private Set<AggregateLicenseeClass> aggregateLicenseeClasses = new HashSet<>();
    private Set<String> reportedTypeOfUses = new HashSet<>();
    private String typeOfUse;
    private FilterExpression<Number> annualizedCopiesExpression = new FilterExpression<>();
    private FilterExpression<Number> wrWrkInstExpression = new FilterExpression<>();
    private FilterExpression<String> systemTitleExpression = new FilterExpression<>();
    private FilterExpression<String> usageDetailIdExpression = new FilterExpression<>();
    private FilterExpression<String> surveyCountryExpression = new FilterExpression<>();

    /**
     * Default constructor.
     */
    public UdmBaselineFilter() {
    }

    /**
     * Constructs new filter based on existing.
     *
     * @param filter base filter
     */
    public UdmBaselineFilter(UdmBaselineFilter filter) {
        if (null != filter) {
            setPeriods(filter.getPeriods());
            setUdmUsageOrigin(filter.getUdmUsageOrigin());
            setChannel(filter.getChannel());
            setDetailLicenseeClasses(filter.getDetailLicenseeClasses());
            setAggregateLicenseeClasses(filter.getAggregateLicenseeClasses());
            setReportedTypeOfUses(filter.getReportedTypeOfUses());
            setTypeOfUse(filter.getTypeOfUse());
            setAnnualizedCopiesExpression(new FilterExpression<>(filter.getAnnualizedCopiesExpression()));
            setWrWrkInstExpression(new FilterExpression<>(filter.getWrWrkInstExpression()));
            setSystemTitleExpression(new FilterExpression<>(filter.getSystemTitleExpression()));
            setUsageDetailIdExpression(new FilterExpression<>(filter.getUsageDetailIdExpression()));
            setSurveyCountryExpression(new FilterExpression<>(filter.getSurveyCountryExpression()));
        }
    }

    public Set<Integer> getPeriods() {
        return periods;
    }

    public void setPeriods(Set<Integer> periods) {
        this.periods = periods;
    }

    public UdmChannelEnum getChannel() {
        return channel;
    }

    public void setChannel(UdmChannelEnum channel) {
        this.channel = channel;
    }

    public UdmUsageOriginEnum getUdmUsageOrigin() {
        return udmUsageOrigin;
    }

    public void setUdmUsageOrigin(UdmUsageOriginEnum udmUsageOrigin) {
        this.udmUsageOrigin = udmUsageOrigin;
    }

    public Set<DetailLicenseeClass> getDetailLicenseeClasses() {
        return detailLicenseeClasses;
    }

    public void setDetailLicenseeClasses(Set<DetailLicenseeClass> detailLicenseeClasses) {
        this.detailLicenseeClasses = detailLicenseeClasses;
    }

    public Set<AggregateLicenseeClass> getAggregateLicenseeClasses() {
        return aggregateLicenseeClasses;
    }

    public void setAggregateLicenseeClasses(Set<AggregateLicenseeClass> aggregateLicenseeClasses) {
        this.aggregateLicenseeClasses = aggregateLicenseeClasses;
    }

    public Set<String> getReportedTypeOfUses() {
        return reportedTypeOfUses;
    }

    public void setReportedTypeOfUses(Set<String> reportedTypeOfUses) {
        this.reportedTypeOfUses = reportedTypeOfUses;
    }

    public String getTypeOfUse() {
        return typeOfUse;
    }

    public void setTypeOfUse(String typeOfUse) {
        this.typeOfUse = typeOfUse;
    }

    public FilterExpression<Number> getAnnualizedCopiesExpression() {
        return annualizedCopiesExpression;
    }

    public void setAnnualizedCopiesExpression(FilterExpression<Number> annualizedCopiesExpression) {
        this.annualizedCopiesExpression = annualizedCopiesExpression;
    }

    public FilterExpression<Number> getWrWrkInstExpression() {
        return wrWrkInstExpression;
    }

    public void setWrWrkInstExpression(FilterExpression<Number> wrWrkInstExpression) {
        this.wrWrkInstExpression = wrWrkInstExpression;
    }

    public FilterExpression<String> getSystemTitleExpression() {
        return systemTitleExpression;
    }

    public void setSystemTitleExpression(FilterExpression<String> systemTitleExpression) {
        this.systemTitleExpression = systemTitleExpression;
    }

    public FilterExpression<String> getUsageDetailIdExpression() {
        return usageDetailIdExpression;
    }

    public void setUsageDetailIdExpression(FilterExpression<String> usageDetailIdExpression) {
        this.usageDetailIdExpression = usageDetailIdExpression;
    }

    public FilterExpression<String> getSurveyCountryExpression() {
        return surveyCountryExpression;
    }

    public void setSurveyCountryExpression(FilterExpression<String> surveyCountryExpression) {
        this.surveyCountryExpression = surveyCountryExpression;
    }

    /**
     * @return {@code true} if filter does not contain any criteria, otherwise {@code false}.
     */
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(periods)
            && null == udmUsageOrigin
            && null == channel
            && annualizedCopiesExpression.isEmpty()
            && wrWrkInstExpression.isEmpty()
            && systemTitleExpression.isEmpty()
            && usageDetailIdExpression.isEmpty()
            && surveyCountryExpression.isEmpty()
            && CollectionUtils.isEmpty(detailLicenseeClasses)
            && CollectionUtils.isEmpty(aggregateLicenseeClasses)
            && CollectionUtils.isEmpty(reportedTypeOfUses)
            && null == typeOfUse;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        UdmBaselineFilter that = (UdmBaselineFilter) obj;
        return new EqualsBuilder()
            .append(periods, that.periods)
            .append(channel, that.channel)
            .append(udmUsageOrigin, that.udmUsageOrigin)
            .append(detailLicenseeClasses, that.detailLicenseeClasses)
            .append(aggregateLicenseeClasses, that.aggregateLicenseeClasses)
            .append(reportedTypeOfUses, that.reportedTypeOfUses)
            .append(typeOfUse, that.typeOfUse)
            .append(annualizedCopiesExpression, that.annualizedCopiesExpression)
            .append(wrWrkInstExpression, that.wrWrkInstExpression)
            .append(systemTitleExpression, that.systemTitleExpression)
            .append(usageDetailIdExpression, that.usageDetailIdExpression)
            .append(surveyCountryExpression, that.surveyCountryExpression)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(periods)
            .append(channel)
            .append(udmUsageOrigin)
            .append(detailLicenseeClasses)
            .append(aggregateLicenseeClasses)
            .append(reportedTypeOfUses)
            .append(typeOfUse)
            .append(annualizedCopiesExpression)
            .append(wrWrkInstExpression)
            .append(systemTitleExpression)
            .append(usageDetailIdExpression)
            .append(surveyCountryExpression)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("periods", periods)
            .append("channel", channel)
            .append("udmUsageOrigin", udmUsageOrigin)
            .append("detailLicenseeClasses", detailLicenseeClasses)
            .append("aggregateLicenseeClasses", aggregateLicenseeClasses)
            .append("reportedTypeOfUses", reportedTypeOfUses)
            .append("typeOfUse", typeOfUse)
            .append("annualizedCopiesExpression", annualizedCopiesExpression)
            .append("wrWrkInstExpression", wrWrkInstExpression)
            .append("systemTitleExpression", systemTitleExpression)
            .append("usageDetailIdExpression", usageDetailIdExpression)
            .append("surveyCountryExpression", surveyCountryExpression)
            .toString();
    }
}
