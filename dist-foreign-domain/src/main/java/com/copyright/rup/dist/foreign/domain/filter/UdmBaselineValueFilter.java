package com.copyright.rup.dist.foreign.domain.filter;

import com.copyright.rup.dist.foreign.domain.PublicationType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class represents a set of filtering criteria for UDM Baseline Value.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Anton Azarenka
 */
public class UdmBaselineValueFilter {

    private Set<Integer> periods = new HashSet<>();
    private Set<PublicationType> pubTypes = new HashSet<>();
    private FilterExpression<Number> wrWrkInstExpression = new FilterExpression<>();
    private FilterExpression<String> systemTitleExpression = new FilterExpression<>();
    private FilterExpression<Boolean> priceFlagExpression = new FilterExpression<>();
    private FilterExpression<Boolean> contentFlagExpression = new FilterExpression<>();
    private FilterExpression<Boolean> contentUnitPriceFlagExpression = new FilterExpression<>();
    private FilterExpression<Number> priceExpression = new FilterExpression<>();
    private FilterExpression<Number> contentExpression = new FilterExpression<>();
    private FilterExpression<Number> contentUnitPriceExpression = new FilterExpression<>();
    private FilterExpression<String> commentExpression = new FilterExpression<>();

    /**
     * Default constructor.
     */
    public UdmBaselineValueFilter() {
    }

    /**
     * Constructs new filter based on existing.
     *
     * @param filter base filter
     */
    public UdmBaselineValueFilter(UdmBaselineValueFilter filter) {
        if (Objects.nonNull(filter)) {
            this.periods = filter.getPeriods();
            this.pubTypes = filter.getPubTypes();
            this.wrWrkInstExpression = new FilterExpression<>(filter.getWrWrkInstExpression());
            this.systemTitleExpression = new FilterExpression<>(filter.getSystemTitleExpression());
            this.priceFlagExpression = new FilterExpression<>(filter.getPriceFlagExpression());
            this.contentFlagExpression = new FilterExpression<>(filter.getContentFlagExpression());
            this.contentUnitPriceFlagExpression = new FilterExpression<>(filter.getContentUnitPriceFlagExpression());
            this.priceExpression = new FilterExpression<>(filter.getPriceExpression());
            this.contentExpression = new FilterExpression<>(filter.getContentExpression());
            this.contentUnitPriceExpression = new FilterExpression<>(filter.getContentUnitPriceExpression());
            this.commentExpression = new FilterExpression<>(filter.getCommentExpression());
        }
    }

    public Set<Integer> getPeriods() {
        return periods;
    }

    public void setPeriods(Set<Integer> periods) {
        this.periods = periods;
    }

    public Set<PublicationType> getPubTypes() {
        return pubTypes;
    }

    public void setPubTypes(Set<PublicationType> pubTypes) {
        this.pubTypes = pubTypes;
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

    public FilterExpression<Boolean> getPriceFlagExpression() {
        return priceFlagExpression;
    }

    public void setPriceFlagExpression(FilterExpression<Boolean> priceFlagExpression) {
        this.priceFlagExpression = priceFlagExpression;
    }

    public FilterExpression<Boolean> getContentFlagExpression() {
        return contentFlagExpression;
    }

    public void setContentFlagExpression(FilterExpression<Boolean> contentFlagExpression) {
        this.contentFlagExpression = contentFlagExpression;
    }

    public FilterExpression<Boolean> getContentUnitPriceFlagExpression() {
        return contentUnitPriceFlagExpression;
    }

    public void setContentUnitPriceFlagExpression(FilterExpression<Boolean> contentUnitPriceFlagExpression) {
        this.contentUnitPriceFlagExpression = contentUnitPriceFlagExpression;
    }

    public FilterExpression<Number> getPriceExpression() {
        return priceExpression;
    }

    public void setPriceExpression(FilterExpression<Number> priceExpression) {
        this.priceExpression = priceExpression;
    }

    public FilterExpression<Number> getContentExpression() {
        return contentExpression;
    }

    public void setContentExpression(FilterExpression<Number> contentExpression) {
        this.contentExpression = contentExpression;
    }

    public FilterExpression<Number> getContentUnitPriceExpression() {
        return contentUnitPriceExpression;
    }

    public void setContentUnitPriceExpression(FilterExpression<Number> contentUnitPriceExpression) {
        this.contentUnitPriceExpression = contentUnitPriceExpression;
    }

    public FilterExpression<String> getCommentExpression() {
        return commentExpression;
    }

    public void setCommentExpression(FilterExpression<String> commentExpression) {
        this.commentExpression = commentExpression;
    }

    /**
     * @return {@code true} if filter does not contain any criteria, otherwise {@code false}.
     */
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(periods)
            && CollectionUtils.isEmpty(pubTypes)
            && wrWrkInstExpression.isEmpty()
            && systemTitleExpression.isEmpty()
            && priceFlagExpression.isEmpty()
            && contentFlagExpression.isEmpty()
            && contentUnitPriceFlagExpression.isEmpty()
            && priceExpression.isEmpty()
            && contentExpression.isEmpty()
            && contentUnitPriceExpression.isEmpty()
            && commentExpression.isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }

        UdmBaselineValueFilter that = (UdmBaselineValueFilter) obj;

        return new EqualsBuilder()
            .append(periods, that.periods)
            .append(pubTypes, that.pubTypes)
            .append(wrWrkInstExpression, that.wrWrkInstExpression)
            .append(systemTitleExpression, that.systemTitleExpression)
            .append(priceFlagExpression, that.priceFlagExpression)
            .append(contentFlagExpression, that.contentFlagExpression)
            .append(contentUnitPriceFlagExpression, that.contentUnitPriceFlagExpression)
            .append(priceExpression, that.priceExpression)
            .append(contentExpression, that.contentExpression)
            .append(contentUnitPriceExpression, that.contentUnitPriceExpression)
            .append(commentExpression, that.commentExpression)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(periods)
            .append(pubTypes)
            .append(wrWrkInstExpression)
            .append(systemTitleExpression)
            .append(priceFlagExpression)
            .append(contentFlagExpression)
            .append(contentUnitPriceFlagExpression)
            .append(priceExpression)
            .append(contentExpression)
            .append(contentUnitPriceExpression)
            .append(commentExpression)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("periods", periods)
            .append("pubTypes", pubTypes)
            .append("wrWrkInstExpression", wrWrkInstExpression)
            .append("systemTitleExpression", systemTitleExpression)
            .append("priceFlagExpression", priceFlagExpression)
            .append("contentFlagExpression", contentFlagExpression)
            .append("contentUnitPriceFlagExpression", contentUnitPriceFlagExpression)
            .append("priceExpression", priceExpression)
            .append("contentExpression", contentExpression)
            .append("contentUnitPriceExpression", contentUnitPriceExpression)
            .append("commentExpression", commentExpression)
            .toString();
    }
}
