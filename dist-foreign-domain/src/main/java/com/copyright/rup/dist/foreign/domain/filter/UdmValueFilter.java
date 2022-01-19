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
    private Boolean priceFlag;
    private FilterExpression<String> priceCommentExpression = new FilterExpression<>();
    private Boolean lastPriceFlag;
    private FilterExpression<String> lastPriceCommentExpression = new FilterExpression<>();
    private FilterExpression<Number> contentExpression = new FilterExpression<>();
    private Boolean contentFlag;
    private FilterExpression<String> contentCommentExpression = new FilterExpression<>();
    private Boolean lastContentFlag;
    private FilterExpression<String> lastContentCommentExpression = new FilterExpression<>();
    private PublicationType pubType;
    private PublicationType lastPubType;
    private String comment;

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
            setWrWrkInstExpression(filter.getWrWrkInstExpression());
            setSystemTitleExpression(filter.getSystemTitleExpression());
            setSystemStandardNumberExpression(filter.getSystemStandardNumberExpression());
            setRhAccountNumberExpression(filter.getRhAccountNumberExpression());
            setRhNameExpression(filter.getRhNameExpression());
            setPriceExpression(filter.getPriceExpression());
            setPriceInUsdExpression(filter.getPriceInUsdExpression());
            setPriceFlag(filter.getPriceFlag());
            setPriceCommentExpression(filter.getPriceCommentExpression());
            setLastPriceFlag(filter.getLastPriceFlag());
            setLastPriceCommentExpression(filter.getLastPriceCommentExpression());
            setContentExpression(filter.getContentExpression());
            setContentFlag(filter.getContentFlag());
            setContentCommentExpression(filter.getContentCommentExpression());
            setLastContentFlag(filter.getLastContentFlag());
            setLastContentCommentExpression(filter.getLastContentCommentExpression());
            setPubType(filter.getPubType());
            setLastPubType(filter.getLastPubType());
            setComment(filter.getComment());
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

    public Boolean getPriceFlag() {
        return priceFlag;
    }

    public void setPriceFlag(Boolean priceFlag) {
        this.priceFlag = priceFlag;
    }

    public FilterExpression<String> getPriceCommentExpression() {
        return priceCommentExpression;
    }

    public void setPriceCommentExpression(FilterExpression<String> priceCommentExpression) {
        this.priceCommentExpression = priceCommentExpression;
    }

    public Boolean getLastPriceFlag() {
        return lastPriceFlag;
    }

    public void setLastPriceFlag(Boolean lastPriceFlag) {
        this.lastPriceFlag = lastPriceFlag;
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

    public Boolean getContentFlag() {
        return contentFlag;
    }

    public void setContentFlag(Boolean contentFlag) {
        this.contentFlag = contentFlag;
    }

    public FilterExpression<String> getContentCommentExpression() {
        return contentCommentExpression;
    }

    public void setContentCommentExpression(FilterExpression<String> contentCommentExpression) {
        this.contentCommentExpression = contentCommentExpression;
    }

    public Boolean getLastContentFlag() {
        return lastContentFlag;
    }

    public void setLastContentFlag(Boolean lastContentFlag) {
        this.lastContentFlag = lastContentFlag;
    }

    public FilterExpression<String> getLastContentCommentExpression() {
        return lastContentCommentExpression;
    }

    public void setLastContentCommentExpression(FilterExpression<String> lastContentCommentExpression) {
        this.lastContentCommentExpression = lastContentCommentExpression;
    }

    public PublicationType getPubType() {
        return pubType;
    }

    public void setPubType(PublicationType pubType) {
        this.pubType = pubType;
    }

    public PublicationType getLastPubType() {
        return lastPubType;
    }

    public void setLastPubType(PublicationType lastPubType) {
        this.lastPubType = lastPubType;
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
            && priceInUsdExpression.isEmpty()
            && null == priceFlag
            && priceCommentExpression.isEmpty()
            && null == lastPriceFlag
            && lastPriceCommentExpression.isEmpty()
            && contentExpression.isEmpty()
            && null == contentFlag
            && contentCommentExpression.isEmpty()
            && null == lastContentFlag
            && lastContentCommentExpression.isEmpty()
            && null == pubType
            && null == lastPubType
            && null == comment;
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
            .append(priceInUsdExpression, that.priceInUsdExpression)
            .append(priceFlag, that.priceFlag)
            .append(priceCommentExpression, that.priceCommentExpression)
            .append(lastPriceFlag, that.lastPriceFlag)
            .append(lastPriceCommentExpression, that.lastPriceCommentExpression)
            .append(contentExpression, that.contentExpression)
            .append(contentFlag, that.contentFlag)
            .append(contentCommentExpression, that.contentCommentExpression)
            .append(lastContentFlag, that.lastContentFlag)
            .append(lastContentCommentExpression, that.lastContentCommentExpression)
            .append(pubType, that.pubType)
            .append(lastPubType, that.lastPubType)
            .append(comment, that.comment)
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
            .append(priceFlag)
            .append(priceCommentExpression)
            .append(lastPriceFlag)
            .append(lastPriceCommentExpression)
            .append(contentExpression)
            .append(contentFlag)
            .append(contentCommentExpression)
            .append(lastContentFlag)
            .append(lastContentCommentExpression)
            .append(pubType)
            .append(lastPubType)
            .append(comment)
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
            .append("priceInUsdExpression", priceInUsdExpression)
            .append("priceFlag", priceFlag)
            .append("priceCommentExpression", priceCommentExpression)
            .append("lastPriceFlag", lastPriceFlag)
            .append("lastPriceCommentExpression", lastPriceCommentExpression)
            .append("contentExpression", contentExpression)
            .append("contentFlag", contentFlag)
            .append("contentCommentExpression", contentCommentExpression)
            .append("lastContentFlag", lastContentFlag)
            .append("lastContentCommentExpression", lastContentCommentExpression)
            .append("pubType", pubType)
            .append("lastPubType", lastPubType)
            .append("comment", comment)
            .toString();
    }
}
