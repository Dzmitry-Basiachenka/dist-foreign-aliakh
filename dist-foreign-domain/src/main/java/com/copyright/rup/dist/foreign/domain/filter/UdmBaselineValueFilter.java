package com.copyright.rup.dist.foreign.domain.filter;

import com.copyright.rup.dist.foreign.domain.PublicationType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
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
    private PublicationType pubType;
    private FilterExpression<Number> wrWrkInstExpression = new FilterExpression<>();
    private FilterExpression<String> systemTitleExpression = new FilterExpression<>();
    private FilterExpression<Boolean> priceFlagExpression = new FilterExpression<>();
    private FilterExpression<Boolean> contentFlagExpression = new FilterExpression<>();
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
        if (null != filter) {
            setPeriods(filter.getPeriods());
            setPubType(filter.getPubType());
            setWrWrkInstExpression(new FilterExpression<>(filter.getWrWrkInstExpression()));
            setSystemTitleExpression(new FilterExpression<>(filter.getSystemTitleExpression()));
            setPriceFlagExpression(new FilterExpression<>(filter.getPriceFlagExpression()));
            setContentFlagExpression(new FilterExpression<>(filter.getContentFlagExpression()));
            setPriceExpression(new FilterExpression<>(filter.getPriceExpression()));
            setContentExpression(new FilterExpression<>(filter.getContentExpression()));
            setContentUnitPriceExpression(new FilterExpression<>(filter.getContentUnitPriceExpression()));
            setCommentExpression(new FilterExpression<>(filter.getCommentExpression()));
        }
    }

    public Set<Integer> getPeriods() {
        return periods;
    }

    public void setPeriods(Set<Integer> periods) {
        this.periods = periods;
    }

    public PublicationType getPubType() {
        return pubType;
    }

    public void setPubType(PublicationType pubType) {
        this.pubType = pubType;
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
            && null == pubType
            && wrWrkInstExpression.isEmpty()
            && systemTitleExpression.isEmpty()
            && priceFlagExpression.isEmpty()
            && contentFlagExpression.isEmpty()
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
            .append(pubType, that.pubType)
            .append(wrWrkInstExpression, that.wrWrkInstExpression)
            .append(systemTitleExpression, that.systemTitleExpression)
            .append(priceFlagExpression, that.priceFlagExpression)
            .append(contentFlagExpression, that.contentFlagExpression)
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
            .append(pubType)
            .append(wrWrkInstExpression)
            .append(systemTitleExpression)
            .append(priceFlagExpression)
            .append(contentFlagExpression)
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
            .append("pubType", pubType)
            .append("wrWrkInstExpression", wrWrkInstExpression)
            .append("systemTitleExpression", systemTitleExpression)
            .append("priceFlagExpression", priceFlagExpression)
            .append("contentFlagExpression", contentFlagExpression)
            .append("priceExpression", priceExpression)
            .append("contentExpression", contentExpression)
            .append("contentUnitPriceExpression", contentUnitPriceExpression)
            .append("commentExpression", commentExpression)
            .toString();
    }
}
