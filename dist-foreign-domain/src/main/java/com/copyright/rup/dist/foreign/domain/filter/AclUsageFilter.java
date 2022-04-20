package com.copyright.rup.dist.foreign.domain.filter;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class represents a set of filtering criteria for ACL usages.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/31/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclUsageFilter {

    private String usageBatchName;
    private UdmUsageOriginEnum usageOrigin;
    private UdmChannelEnum channel;
    private Set<Integer> periods = new HashSet<>();
    private Set<DetailLicenseeClass> detailLicenseeClasses = new HashSet<>();
    private Set<AggregateLicenseeClass> aggregateLicenseeClasses = new HashSet<>();
    private Set<PublicationType> pubTypes = new HashSet<>();
    private Set<String> typeOfUses = new HashSet<>();
    private FilterExpression<String> usageDetailIdExpression = new FilterExpression<>();
    private FilterExpression<Number> wrWrkInstExpression = new FilterExpression<>();
    private FilterExpression<String> systemTitleExpression = new FilterExpression<>();
    private FilterExpression<String> surveyCountryExpression = new FilterExpression<>();
    private FilterExpression<Number> contentUnitPriceExpression = new FilterExpression<>();
    private FilterExpression<Number> annualizedCopiesExpression = new FilterExpression<>();

    /**
     * Default constructor.
     */
    public AclUsageFilter() {
    }

    /**
     * Constructs new filter based on existing.
     *
     * @param filter base filter
     */
    public AclUsageFilter(AclUsageFilter filter) {
        if (Objects.nonNull(filter)) {
            setUsageBatchName(filter.getUsageBatchName());
            setUsageOrigin(filter.getUsageOrigin());
            setChannel(filter.getChannel());
            setPeriods(filter.getPeriods());
            setDetailLicenseeClasses(filter.getDetailLicenseeClasses());
            setAggregateLicenseeClasses(filter.getAggregateLicenseeClasses());
            setPubTypes(filter.getPubTypes());
            setTypeOfUses(filter.getTypeOfUses());
            setUsageDetailIdExpression(new FilterExpression<>(filter.getUsageDetailIdExpression()));
            setWrWrkInstExpression(new FilterExpression<>(filter.getWrWrkInstExpression()));
            setSystemTitleExpression(new FilterExpression<>(filter.getSystemTitleExpression()));
            setSurveyCountryExpression(new FilterExpression<>(filter.getSurveyCountryExpression()));
            setContentUnitPriceExpression(new FilterExpression<>(filter.getContentUnitPriceExpression()));
            setAnnualizedCopiesExpression(new FilterExpression<>(filter.getAnnualizedCopiesExpression()));
        }
    }

    public String getUsageBatchName() {
        return usageBatchName;
    }

    public void setUsageBatchName(String usageBatchName) {
        this.usageBatchName = usageBatchName;
    }

    public UdmUsageOriginEnum getUsageOrigin() {
        return usageOrigin;
    }

    public void setUsageOrigin(UdmUsageOriginEnum usageOrigin) {
        this.usageOrigin = usageOrigin;
    }

    public UdmChannelEnum getChannel() {
        return channel;
    }

    public void setChannel(UdmChannelEnum channel) {
        this.channel = channel;
    }

    public Set<Integer> getPeriods() {
        return periods;
    }

    public void setPeriods(Set<Integer> periods) {
        this.periods = periods;
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

    public Set<PublicationType> getPubTypes() {
        return pubTypes;
    }

    public void setPubTypes(Set<PublicationType> pubTypes) {
        this.pubTypes = pubTypes;
    }

    public Set<String> getTypeOfUses() {
        return typeOfUses;
    }

    public void setTypeOfUses(Set<String> typeOfUses) {
        this.typeOfUses = typeOfUses;
    }

    public FilterExpression<String> getUsageDetailIdExpression() {
        return usageDetailIdExpression;
    }

    public void setUsageDetailIdExpression(FilterExpression<String> usageDetailIdExpression) {
        this.usageDetailIdExpression = usageDetailIdExpression;
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

    public FilterExpression<String> getSurveyCountryExpression() {
        return surveyCountryExpression;
    }

    public void setSurveyCountryExpression(FilterExpression<String> surveyCountryExpression) {
        this.surveyCountryExpression = surveyCountryExpression;
    }

    public FilterExpression<Number> getContentUnitPriceExpression() {
        return contentUnitPriceExpression;
    }

    public void setContentUnitPriceExpression(FilterExpression<Number> contentUnitPriceExpression) {
        this.contentUnitPriceExpression = contentUnitPriceExpression;
    }

    public FilterExpression<Number> getAnnualizedCopiesExpression() {
        return annualizedCopiesExpression;
    }

    public void setAnnualizedCopiesExpression(FilterExpression<Number> annualizedCopiesExpression) {
        this.annualizedCopiesExpression = annualizedCopiesExpression;
    }

    /**
     * @return {@code true} if filter does not contain any criteria, otherwise {@code false}.
     */
    public boolean isEmpty() {
        return Objects.isNull(usageBatchName)
            && Objects.isNull(usageOrigin)
            && Objects.isNull(channel)
            && CollectionUtils.isEmpty(periods)
            && CollectionUtils.isEmpty(detailLicenseeClasses)
            && CollectionUtils.isEmpty(aggregateLicenseeClasses)
            && CollectionUtils.isEmpty(pubTypes)
            && CollectionUtils.isEmpty(typeOfUses)
            && usageDetailIdExpression.isEmpty()
            && wrWrkInstExpression.isEmpty()
            && systemTitleExpression.isEmpty()
            && surveyCountryExpression.isEmpty()
            && contentUnitPriceExpression.isEmpty()
            && annualizedCopiesExpression.isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclUsageFilter that = (AclUsageFilter) obj;
        return new EqualsBuilder()
            .append(usageBatchName, that.usageBatchName)
            .append(usageOrigin, that.usageOrigin)
            .append(channel, that.channel)
            .append(periods, that.periods)
            .append(detailLicenseeClasses, that.detailLicenseeClasses)
            .append(aggregateLicenseeClasses, that.aggregateLicenseeClasses)
            .append(pubTypes, that.pubTypes)
            .append(typeOfUses, that.typeOfUses)
            .append(usageDetailIdExpression, that.usageDetailIdExpression)
            .append(wrWrkInstExpression, that.wrWrkInstExpression)
            .append(systemTitleExpression, that.systemTitleExpression)
            .append(surveyCountryExpression, that.surveyCountryExpression)
            .append(contentUnitPriceExpression, that.contentUnitPriceExpression)
            .append(annualizedCopiesExpression, that.annualizedCopiesExpression)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(usageBatchName)
            .append(usageOrigin)
            .append(channel)
            .append(periods)
            .append(detailLicenseeClasses)
            .append(aggregateLicenseeClasses)
            .append(pubTypes)
            .append(typeOfUses)
            .append(usageDetailIdExpression)
            .append(wrWrkInstExpression)
            .append(systemTitleExpression)
            .append(surveyCountryExpression)
            .append(contentUnitPriceExpression)
            .append(annualizedCopiesExpression)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("usageBatchName", usageBatchName)
            .append("usageOrigin", usageOrigin)
            .append("channel", channel)
            .append("periods", periods)
            .append("detailLicenseeClasses", detailLicenseeClasses)
            .append("aggregateLicenseeClasses", aggregateLicenseeClasses)
            .append("pubTypes", pubTypes)
            .append("typeOfUses", typeOfUses)
            .append("usageDetailIdExpression", usageDetailIdExpression)
            .append("wrWrkInstExpression", wrWrkInstExpression)
            .append("systemTitleExpression", systemTitleExpression)
            .append("surveyCountryExpression", surveyCountryExpression)
            .append("contentUnitPriceExpression", contentUnitPriceExpression)
            .append("annualizedCopiesExpression", annualizedCopiesExpression)
            .toString();
    }
}
