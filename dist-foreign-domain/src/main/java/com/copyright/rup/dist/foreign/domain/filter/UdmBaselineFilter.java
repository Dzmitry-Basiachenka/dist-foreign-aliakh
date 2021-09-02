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

    private Integer period;
    private UdmChannelEnum channel;
    private UdmUsageOriginEnum udmUsageOrigin;
    private Set<DetailLicenseeClass> detailLicenseeClasses = new HashSet<>();
    private Set<AggregateLicenseeClass> aggregateLicenseeClasses = new HashSet<>();
    private Set<String> reportedTypeOfUses = new HashSet<>();
    private FilterExpression<Number> annualizedCopiesExpression = new FilterExpression<>();
    private Long wrWrkInst;
    private String systemTitle;
    private String usageDetailId;
    private String surveyCountry;

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
            setPeriod(filter.getPeriod());
            setUdmUsageOrigin(filter.getUdmUsageOrigin());
            setChannel(filter.getChannel());
            setDetailLicenseeClasses(filter.getDetailLicenseeClasses());
            setAggregateLicenseeClasses(filter.getAggregateLicenseeClasses());
            setReportedTypeOfUses(filter.getReportedTypeOfUses());
            setAnnualizedCopiesExpression(filter.getAnnualizedCopiesExpression());
            setWrWrkInst(filter.getWrWrkInst());
            setSystemTitle(filter.getSystemTitle());
            setSurveyCountry(filter.getSurveyCountry());
            setUsageDetailId(filter.getUsageDetailId());
        }
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
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

    public FilterExpression<Number> getAnnualizedCopiesExpression() {
        return annualizedCopiesExpression;
    }

    public void setAnnualizedCopiesExpression(FilterExpression<Number> annualizedCopiesExpression) {
        this.annualizedCopiesExpression = annualizedCopiesExpression;
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

    public String getUsageDetailId() {
        return usageDetailId;
    }

    public void setUsageDetailId(String usageDetailId) {
        this.usageDetailId = usageDetailId;
    }

    public String getSurveyCountry() {
        return surveyCountry;
    }

    public void setSurveyCountry(String surveyCountry) {
        this.surveyCountry = surveyCountry;
    }

    /**
     * @return {@code true} if filter does not contain any criteria, otherwise {@code false}.
     */
    public boolean isEmpty() {
        return null == udmUsageOrigin
            && null == period
            && null == channel
            && null == wrWrkInst
            && null == systemTitle
            && null == usageDetailId
            && null == surveyCountry
            && annualizedCopiesExpression.isEmpty()
            && CollectionUtils.isEmpty(detailLicenseeClasses)
            && CollectionUtils.isEmpty(aggregateLicenseeClasses)
            && CollectionUtils.isEmpty(reportedTypeOfUses);
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
            .append(period, that.period)
            .append(channel, that.channel)
            .append(udmUsageOrigin, that.udmUsageOrigin)
            .append(detailLicenseeClasses, that.detailLicenseeClasses)
            .append(aggregateLicenseeClasses, that.aggregateLicenseeClasses)
            .append(reportedTypeOfUses, that.reportedTypeOfUses)
            .append(annualizedCopiesExpression, that.annualizedCopiesExpression)
            .append(wrWrkInst, that.wrWrkInst)
            .append(systemTitle, that.systemTitle)
            .append(usageDetailId, that.usageDetailId)
            .append(surveyCountry, that.surveyCountry)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(period)
            .append(channel)
            .append(udmUsageOrigin)
            .append(detailLicenseeClasses)
            .append(aggregateLicenseeClasses)
            .append(reportedTypeOfUses)
            .append(annualizedCopiesExpression)
            .append(wrWrkInst)
            .append(systemTitle)
            .append(usageDetailId)
            .append(surveyCountry)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("period", period)
            .append("channel", channel)
            .append("udmUsageOrigin", udmUsageOrigin)
            .append("detailLicenseeClasses", detailLicenseeClasses)
            .append("aggregateLicenseeClasses", aggregateLicenseeClasses)
            .append("reportedTypeOfUses", reportedTypeOfUses)
            .append("annualizedCopiesExpression", annualizedCopiesExpression)
            .append("wrWrkInst", wrWrkInst)
            .append("systemTitle", systemTitle)
            .append("usageDetailId", usageDetailId)
            .append("surveyCountry", surveyCountry)
            .toString();
    }
}
