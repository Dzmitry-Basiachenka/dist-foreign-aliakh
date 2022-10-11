package com.copyright.rup.dist.foreign.domain.filter;

import com.copyright.rup.dist.foreign.domain.Currency;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UdmValueStatusEnum;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Class represents a set of filtering criteria for UDM Value.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/22/21
 *
 * @author Anton Azarenka
 */
public class UdmValueFilter {

    private Set<Integer> periods = new HashSet<>();
    private UdmValueStatusEnum status;
    private Currency currency;
    private Set<String> assignees = new HashSet<>();
    private Set<String> lastValuePeriods = new HashSet<>();
    private FilterExpression<Number> wrWrkInstExpression = new FilterExpression<>();
    private FilterExpression<String> systemTitleExpression = new FilterExpression<>();
    private FilterExpression<String> systemStandardNumberExpression = new FilterExpression<>();
    private FilterExpression<Number> rhAccountNumberExpression = new FilterExpression<>();
    private FilterExpression<String> rhNameExpression = new FilterExpression<>();
    private FilterExpression<Number> priceExpression = new FilterExpression<>();
    private FilterExpression<Number> priceInUsdExpression = new FilterExpression<>();
    private FilterExpression<Boolean> priceFlagExpression = new FilterExpression<>();
    private FilterExpression<String> priceCommentExpression = new FilterExpression<>();
    private FilterExpression<Boolean> lastPriceFlagExpression = new FilterExpression<>();
    private FilterExpression<String> lastPriceCommentExpression = new FilterExpression<>();
    private FilterExpression<Number> contentExpression = new FilterExpression<>();
    private FilterExpression<Boolean> contentFlagExpression = new FilterExpression<>();
    private FilterExpression<String> contentCommentExpression = new FilterExpression<>();
    private FilterExpression<Boolean> lastContentFlagExpression = new FilterExpression<>();
    private FilterExpression<String> lastContentCommentExpression = new FilterExpression<>();
    private FilterExpression<Number> contentUnitPriceExpression = new FilterExpression<>();
    private FilterExpression<Boolean> contentUnitPriceFlagExpression = new FilterExpression<>();
    private Set<PublicationType> pubTypes = new HashSet<>();
    private PublicationType lastPubType;
    private FilterExpression<String> commentExpression = new FilterExpression<>();
    private FilterExpression<String> lastCommentExpression = new FilterExpression<>();

    /**
     * Default constructor.
     */
    public UdmValueFilter() {
    }

    /**
     * Constructs new filter based on existing.
     *
     * @param filter base filter
     */
    public UdmValueFilter(UdmValueFilter filter) {
        if (null != filter) {
            setPeriods(filter.getPeriods());
            setStatus(filter.getStatus());
            setCurrency(filter.getCurrency());
            setAssignees(filter.getAssignees());
            setLastValuePeriods(filter.getLastValuePeriods());
            setWrWrkInstExpression(new FilterExpression<>(filter.getWrWrkInstExpression()));
            setSystemTitleExpression(new FilterExpression<>(filter.getSystemTitleExpression()));
            setSystemStandardNumberExpression(new FilterExpression<>(filter.getSystemStandardNumberExpression()));
            setRhAccountNumberExpression(new FilterExpression<>(filter.getRhAccountNumberExpression()));
            setRhNameExpression(new FilterExpression<>(filter.getRhNameExpression()));
            setPriceExpression(new FilterExpression<>(filter.getPriceExpression()));
            setPriceInUsdExpression(new FilterExpression<>(filter.getPriceInUsdExpression()));
            setPriceCommentExpression(new FilterExpression<>(filter.getPriceCommentExpression()));
            setPriceFlagExpression(new FilterExpression<>(filter.getPriceFlagExpression()));
            setLastPriceFlagExpression(new FilterExpression<>(filter.getLastPriceFlagExpression()));
            setLastPriceCommentExpression(new FilterExpression<>(filter.getLastPriceCommentExpression()));
            setContentExpression(new FilterExpression<>(filter.getContentExpression()));
            setContentFlagExpression(new FilterExpression<>(filter.getContentFlagExpression()));
            setLastContentFlagExpression(new FilterExpression<>(filter.getLastContentFlagExpression()));
            setContentCommentExpression(new FilterExpression<>(filter.getContentCommentExpression()));
            setLastContentCommentExpression(new FilterExpression<>(filter.getLastContentCommentExpression()));
            setContentUnitPriceExpression(new FilterExpression<>(filter.getContentUnitPriceExpression()));
            setContentUnitPriceFlagExpression(new FilterExpression<>(filter.getContentUnitPriceFlagExpression()));
            setPubTypes(filter.getPubTypes());
            setLastPubType(filter.getLastPubType());
            setCommentExpression(new FilterExpression<>(filter.getCommentExpression()));
            setLastCommentExpression(new FilterExpression<>(filter.getLastCommentExpression()));
        }
    }

    public Set<Integer> getPeriods() {
        return periods;
    }

    public void setPeriods(Set<Integer> periods) {
        this.periods = periods;
    }

    public UdmValueStatusEnum getStatus() {
        return status;
    }

    public void setStatus(UdmValueStatusEnum status) {
        this.status = status;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Set<String> getAssignees() {
        return assignees;
    }

    public void setAssignees(Set<String> assignees) {
        this.assignees = assignees;
    }

    public Set<String> getLastValuePeriods() {
        return lastValuePeriods;
    }

    public void setLastValuePeriods(Set<String> lastValuePeriods) {
        this.lastValuePeriods = lastValuePeriods;
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

    public FilterExpression<String> getSystemStandardNumberExpression() {
        return systemStandardNumberExpression;
    }

    public void setSystemStandardNumberExpression(FilterExpression<String> systemStandardNumberExpression) {
        this.systemStandardNumberExpression = systemStandardNumberExpression;
    }

    public FilterExpression<Number> getRhAccountNumberExpression() {
        return rhAccountNumberExpression;
    }

    public void setRhAccountNumberExpression(FilterExpression<Number> rhAccountNumberExpression) {
        this.rhAccountNumberExpression = rhAccountNumberExpression;
    }

    public FilterExpression<String> getRhNameExpression() {
        return rhNameExpression;
    }

    public void setRhNameExpression(FilterExpression<String> rhNameExpression) {
        this.rhNameExpression = rhNameExpression;
    }

    public FilterExpression<Number> getPriceExpression() {
        return priceExpression;
    }

    public void setPriceExpression(FilterExpression<Number> priceExpression) {
        this.priceExpression = priceExpression;
    }

    public FilterExpression<Number> getPriceInUsdExpression() {
        return priceInUsdExpression;
    }

    public void setPriceInUsdExpression(FilterExpression<Number> priceInUsdExpression) {
        this.priceInUsdExpression = priceInUsdExpression;
    }

    public FilterExpression<Boolean> getPriceFlagExpression() {
        return priceFlagExpression;
    }

    public void setPriceFlagExpression(FilterExpression<Boolean> priceFlagExpression) {
        this.priceFlagExpression = priceFlagExpression;
    }

    public FilterExpression<String> getPriceCommentExpression() {
        return priceCommentExpression;
    }

    public void setPriceCommentExpression(FilterExpression<String> priceCommentExpression) {
        this.priceCommentExpression = priceCommentExpression;
    }

    public FilterExpression<Boolean> getLastPriceFlagExpression() {
        return lastPriceFlagExpression;
    }

    public void setLastPriceFlagExpression(FilterExpression<Boolean> lastPriceFlagExpression) {
        this.lastPriceFlagExpression = lastPriceFlagExpression;
    }

    public FilterExpression<String> getLastPriceCommentExpression() {
        return lastPriceCommentExpression;
    }

    public void setLastPriceCommentExpression(FilterExpression<String> lastPriceCommentExpression) {
        this.lastPriceCommentExpression = lastPriceCommentExpression;
    }

    public FilterExpression<Number> getContentExpression() {
        return contentExpression;
    }

    public void setContentExpression(FilterExpression<Number> contentExpression) {
        this.contentExpression = contentExpression;
    }

    public FilterExpression<Boolean> getContentFlagExpression() {
        return contentFlagExpression;
    }

    public void setContentFlagExpression(FilterExpression<Boolean> contentFlagExpression) {
        this.contentFlagExpression = contentFlagExpression;
    }

    public FilterExpression<String> getContentCommentExpression() {
        return contentCommentExpression;
    }

    public void setContentCommentExpression(FilterExpression<String> contentCommentExpression) {
        this.contentCommentExpression = contentCommentExpression;
    }

    public FilterExpression<Boolean> getLastContentFlagExpression() {
        return lastContentFlagExpression;
    }

    public void setLastContentFlagExpression(FilterExpression<Boolean> lastContentFlagExpression) {
        this.lastContentFlagExpression = lastContentFlagExpression;
    }

    public FilterExpression<String> getLastContentCommentExpression() {
        return lastContentCommentExpression;
    }

    public void setLastContentCommentExpression(FilterExpression<String> lastContentCommentExpression) {
        this.lastContentCommentExpression = lastContentCommentExpression;
    }

    public FilterExpression<Number> getContentUnitPriceExpression() {
        return contentUnitPriceExpression;
    }

    public void setContentUnitPriceExpression(FilterExpression<Number> contentUnitPriceExpression) {
        this.contentUnitPriceExpression = contentUnitPriceExpression;
    }

    public FilterExpression<Boolean> getContentUnitPriceFlagExpression() {
        return contentUnitPriceFlagExpression;
    }

    public void setContentUnitPriceFlagExpression(FilterExpression<Boolean> contentUnitPriceFlagExpression) {
        this.contentUnitPriceFlagExpression = contentUnitPriceFlagExpression;
    }

    public Set<PublicationType> getPubTypes() {
        return pubTypes;
    }

    public void setPubTypes(Set<PublicationType> pubTypes) {
        this.pubTypes = pubTypes;
    }

    public PublicationType getLastPubType() {
        return lastPubType;
    }

    public void setLastPubType(PublicationType lastPubType) {
        this.lastPubType = lastPubType;
    }

    public FilterExpression<String> getCommentExpression() {
        return commentExpression;
    }

    public void setCommentExpression(FilterExpression<String> commentExpression) {
        this.commentExpression = commentExpression;
    }

    public FilterExpression<String> getLastCommentExpression() {
        return lastCommentExpression;
    }

    public void setLastCommentExpression(FilterExpression<String> lastCommentExpression) {
        this.lastCommentExpression = lastCommentExpression;
    }

    /**
     * @return {@code true} if filter does not contain any criteria, otherwise {@code false}.
     */
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(periods)
            && null == status
            && null == currency
            && CollectionUtils.isEmpty(assignees)
            && CollectionUtils.isEmpty(lastValuePeriods)
            && wrWrkInstExpression.isEmpty()
            && systemTitleExpression.isEmpty()
            && systemStandardNumberExpression.isEmpty()
            && rhAccountNumberExpression.isEmpty()
            && rhNameExpression.isEmpty()
            && priceExpression.isEmpty()
            && priceFlagExpression.isEmpty()
            && lastPriceFlagExpression.isEmpty()
            && priceInUsdExpression.isEmpty()
            && priceCommentExpression.isEmpty()
            && lastPriceCommentExpression.isEmpty()
            && contentExpression.isEmpty()
            && contentFlagExpression.isEmpty()
            && lastContentFlagExpression.isEmpty()
            && contentCommentExpression.isEmpty()
            && contentUnitPriceExpression.isEmpty()
            && contentUnitPriceFlagExpression.isEmpty()
            && lastContentCommentExpression.isEmpty()
            && CollectionUtils.isEmpty(pubTypes)
            && null == lastPubType
            && commentExpression.isEmpty()
            && lastCommentExpression.isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        UdmValueFilter that = (UdmValueFilter) obj;
        return new EqualsBuilder()
            .append(periods, that.periods)
            .append(status, that.status)
            .append(currency, that.currency)
            .append(assignees, that.assignees)
            .append(lastValuePeriods, that.lastValuePeriods)
            .append(wrWrkInstExpression, that.wrWrkInstExpression)
            .append(systemTitleExpression, that.systemTitleExpression)
            .append(systemStandardNumberExpression, that.systemStandardNumberExpression)
            .append(rhAccountNumberExpression, that.rhAccountNumberExpression)
            .append(rhNameExpression, that.rhNameExpression)
            .append(priceExpression, that.priceExpression)
            .append(priceFlagExpression, that.priceFlagExpression)
            .append(lastPriceFlagExpression, that.lastPriceFlagExpression)
            .append(priceInUsdExpression, that.priceInUsdExpression)
            .append(priceCommentExpression, that.priceCommentExpression)
            .append(lastPriceCommentExpression, that.lastPriceCommentExpression)
            .append(contentExpression, that.contentExpression)
            .append(contentFlagExpression, that.contentFlagExpression)
            .append(lastContentFlagExpression, that.lastContentFlagExpression)
            .append(contentCommentExpression, that.contentCommentExpression)
            .append(lastContentCommentExpression, that.lastContentCommentExpression)
            .append(contentUnitPriceExpression, that.contentUnitPriceExpression)
            .append(contentUnitPriceFlagExpression, that.contentUnitPriceFlagExpression)
            .append(pubTypes, that.pubTypes)
            .append(lastPubType, that.lastPubType)
            .append(commentExpression, that.commentExpression)
            .append(lastCommentExpression, that.lastCommentExpression)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(periods)
            .append(status)
            .append(currency)
            .append(assignees)
            .append(lastValuePeriods)
            .append(wrWrkInstExpression)
            .append(systemTitleExpression)
            .append(systemStandardNumberExpression)
            .append(rhAccountNumberExpression)
            .append(rhNameExpression)
            .append(priceExpression)
            .append(priceInUsdExpression)
            .append(priceFlagExpression)
            .append(lastPriceFlagExpression)
            .append(priceCommentExpression)
            .append(lastPriceCommentExpression)
            .append(contentExpression)
            .append(contentFlagExpression)
            .append(lastContentFlagExpression)
            .append(contentCommentExpression)
            .append(lastContentCommentExpression)
            .append(contentUnitPriceExpression)
            .append(contentUnitPriceFlagExpression)
            .append(pubTypes)
            .append(lastPubType)
            .append(commentExpression)
            .append(lastCommentExpression)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("periods", periods)
            .append("status", status)
            .append("currency", currency)
            .append("assignees", assignees)
            .append("lastValuePeriods", lastValuePeriods)
            .append("wrWrkInstExpression", wrWrkInstExpression)
            .append("systemTitleExpression", systemTitleExpression)
            .append("systemStandardNumberExpression", systemStandardNumberExpression)
            .append("rhAccountNumberExpression", rhAccountNumberExpression)
            .append("rhNameExpression", rhNameExpression)
            .append("priceExpression", priceExpression)
            .append("priceFlagExpression", priceFlagExpression)
            .append("lastPriceFlagExpression", lastPriceFlagExpression)
            .append("priceInUsdExpression", priceInUsdExpression)
            .append("priceCommentExpression", priceCommentExpression)
            .append("lastPriceCommentExpression", lastPriceCommentExpression)
            .append("contentExpression", contentExpression)
            .append("contentFlagExpression", contentFlagExpression)
            .append("lastContentFlagExpression", lastContentFlagExpression)
            .append("contentCommentExpression", contentCommentExpression)
            .append("lastContentCommentExpression", lastContentCommentExpression)
            .append("contentUnitPriceExpression", contentUnitPriceExpression)
            .append("contentUnitPriceFlagExpression", contentUnitPriceFlagExpression)
            .append("pubTypes", pubTypes)
            .append("lastPubType", lastPubType)
            .append("commentExpression", commentExpression)
            .append("lastCommentExpression", lastCommentExpression)
            .toString();
    }
}
