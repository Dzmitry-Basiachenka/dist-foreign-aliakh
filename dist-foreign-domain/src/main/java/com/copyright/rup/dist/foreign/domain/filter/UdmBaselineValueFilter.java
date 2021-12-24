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
    private Long wrWrkInst;
    private FilterExpression<String> systemTitleExpression = new FilterExpression<>();
    private PublicationType pubType;
    private FilterExpression<Number> priceExpression = new FilterExpression<>();
    private Boolean priceFlag;
    private FilterExpression<Number> contentExpression = new FilterExpression<>();
    private Boolean contentFlag;
    private FilterExpression<Number> contentUnitPriceExpression = new FilterExpression<>();
    private String comment;

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
            setWrWrkInst(filter.getWrWrkInst());
            setSystemTitleExpression(new FilterExpression<>(filter.getSystemTitleExpression()));
            setPubType(filter.getPubType());
            setPriceExpression(new FilterExpression<>(filter.getPriceExpression()));
            setPriceFlag(filter.getPriceFlag());
            setContentExpression(new FilterExpression<>(filter.getContentExpression()));
            setContentFlag(filter.getContentFlag());
            setContentUnitPriceExpression(new FilterExpression<>(filter.getContentUnitPriceExpression()));
            setComment(filter.getComment());
        }
    }

    public Set<Integer> getPeriods() {
        return periods;
    }

    public void setPeriods(Set<Integer> periods) {
        this.periods = periods;
    }

    public Long getWrWrkInst() {
        return wrWrkInst;
    }

    public void setWrWrkInst(Long wrWrkInst) {
        this.wrWrkInst = wrWrkInst;
    }

    public FilterExpression<String> getSystemTitleExpression() {
        return systemTitleExpression;
    }

    public void setSystemTitleExpression(FilterExpression<String> systemTitleExpression) {
        this.systemTitleExpression = systemTitleExpression;
    }

    public PublicationType getPubType() {
        return pubType;
    }

    public void setPubType(PublicationType pubType) {
        this.pubType = pubType;
    }

    public FilterExpression<Number> getPriceExpression() {
        return priceExpression;
    }

    public void setPriceExpression(
        FilterExpression<Number> priceExpression) {
        this.priceExpression = priceExpression;
    }

    public Boolean getPriceFlag() {
        return priceFlag;
    }

    public void setPriceFlag(Boolean priceFlag) {
        this.priceFlag = priceFlag;
    }

    public FilterExpression<Number> getContentExpression() {
        return contentExpression;
    }

    public void setContentExpression(FilterExpression<Number> contentExpression) {
        this.contentExpression = contentExpression;
    }

    public Boolean getContentFlag() {
        return contentFlag;
    }

    public void setContentFlag(Boolean contentFlag) {
        this.contentFlag = contentFlag;
    }

    public FilterExpression<Number> getContentUnitPriceExpression() {
        return contentUnitPriceExpression;
    }

    public void setContentUnitPriceExpression(
        FilterExpression<Number> contentUnitPriceExpression) {
        this.contentUnitPriceExpression = contentUnitPriceExpression;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return {@code true} if filter does not contain any criteria, otherwise {@code false}.
     */
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(periods)
            && null == wrWrkInst
            && systemTitleExpression.isEmpty()
            && null == pubType
            && priceExpression.isEmpty()
            && null == priceFlag
            && contentExpression.isEmpty()
            && null == contentFlag
            && contentUnitPriceExpression.isEmpty()
            && null == comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (null == o || getClass() != o.getClass()) {
            return false;
        }

        UdmBaselineValueFilter that = (UdmBaselineValueFilter) o;

        return new EqualsBuilder()
            .append(periods, that.periods)
            .append(wrWrkInst, that.wrWrkInst)
            .append(systemTitleExpression, that.systemTitleExpression)
            .append(pubType, that.pubType)
            .append(priceExpression, that.priceExpression)
            .append(priceFlag, that.priceFlag)
            .append(contentExpression, that.contentExpression)
            .append(contentFlag, that.contentFlag)
            .append(contentUnitPriceExpression, that.contentUnitPriceExpression)
            .append(comment, that.comment)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(periods)
            .append(wrWrkInst)
            .append(systemTitleExpression)
            .append(pubType)
            .append(priceExpression)
            .append(priceFlag)
            .append(contentExpression)
            .append(contentFlag)
            .append(contentUnitPriceExpression)
            .append(comment)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("periods", periods)
            .append("wrWrkInst", wrWrkInst)
            .append("systemTitleExpression", systemTitleExpression)
            .append("pubType", pubType)
            .append("priceExpression", priceExpression)
            .append("priceFlag", priceFlag)
            .append("contentExpression", contentExpression)
            .append("contentFlag", contentFlag)
            .append("contentUnitPriceExpression", contentUnitPriceExpression)
            .append("comment", comment)
            .toString();
    }
}
