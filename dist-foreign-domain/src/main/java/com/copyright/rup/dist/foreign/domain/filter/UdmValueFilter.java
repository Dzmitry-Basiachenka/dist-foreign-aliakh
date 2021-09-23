package com.copyright.rup.dist.foreign.domain.filter;

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

    private Set<Integer> periods;
    private String currency;
    private UdmValueStatusEnum status;
    private Set<String> assignees = new HashSet<>();
    private Set<String> lastValuePeriods = new HashSet<>();
    private Long wrWrkInst;
    private FilterExpression<Number> systemTitleExpression = new FilterExpression<>();
    private FilterExpression<Number> systemStandardNumberExpression = new FilterExpression<>();
    private Long rhAccountNumber;
    private FilterExpression<Number> rhNameExpression = new FilterExpression<>();
    private FilterExpression<Number> priceExpression = new FilterExpression<>();
    private FilterExpression<Number> priceInUsdExpression = new FilterExpression<>();
    private String lastPriceFlag;
    private String lastPriceComment;
    private FilterExpression<Number> contentExpression = new FilterExpression<>();
    private String lastContentFlag;
    private String lastContentComment;
    private String pubType;
    private String lastPubType;
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
            setCurrency(filter.getCurrency());
            setStatus(filter.getStatus());
            setAssignees(filter.getAssignees());
            setLastValuePeriods(filter.getLastValuePeriods());
            setWrWrkInst(filter.getWrWrkInst());
            setSystemTitleExpression(filter.getSystemTitleExpression());
            setSystemStandardNumberExpression(filter.getSystemStandardNumberExpression());
            setRhAccountNumber(filter.getRhAccountNumber());
            setRhNameExpression(filter.getRhNameExpression());
            setPriceExpression(filter.getPriceExpression());
            setPriceInUsdExpression(filter.getPriceInUsdExpression());
            setLastPriceFlag(filter.getLastPriceFlag());
            setLastPriceComment(filter.getLastPriceComment());
            setContentExpression(filter.getContentExpression());
            setLastContentFlag(filter.getLastContentFlag());
            setLastContentComment(filter.getLastContentComment());
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public UdmValueStatusEnum getStatus() {
        return status;
    }

    public void setStatus(UdmValueStatusEnum status) {
        this.status = status;
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

    public Long getWrWrkInst() {
        return wrWrkInst;
    }

    public void setWrWrkInst(Long wrWrkInst) {
        this.wrWrkInst = wrWrkInst;
    }

    public FilterExpression<Number> getSystemTitleExpression() {
        return systemTitleExpression;
    }

    public void setSystemTitleExpression(FilterExpression<Number> systemTitleExpression) {
        this.systemTitleExpression = systemTitleExpression;
    }

    public FilterExpression<Number> getSystemStandardNumberExpression() {
        return systemStandardNumberExpression;
    }

    public void setSystemStandardNumberExpression(FilterExpression<Number> systemStandardNumberExpression) {
        this.systemStandardNumberExpression = systemStandardNumberExpression;
    }

    public Long getRhAccountNumber() {
        return rhAccountNumber;
    }

    public void setRhAccountNumber(Long rhAccountNumber) {
        this.rhAccountNumber = rhAccountNumber;
    }

    public FilterExpression<Number> getRhNameExpression() {
        return rhNameExpression;
    }

    public void setRhNameExpression(FilterExpression<Number> rhNameExpression) {
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

    public String getLastPriceFlag() {
        return lastPriceFlag;
    }

    public void setLastPriceFlag(String lastPriceFlag) {
        this.lastPriceFlag = lastPriceFlag;
    }

    public String getLastPriceComment() {
        return lastPriceComment;
    }

    public void setLastPriceComment(String lastPriceComment) {
        this.lastPriceComment = lastPriceComment;
    }

    public FilterExpression<Number> getContentExpression() {
        return contentExpression;
    }

    public void setContentExpression(FilterExpression<Number> contentExpression) {
        this.contentExpression = contentExpression;
    }

    public String getLastContentFlag() {
        return lastContentFlag;
    }

    public void setLastContentFlag(String lastContentFlag) {
        this.lastContentFlag = lastContentFlag;
    }

    public String getLastContentComment() {
        return lastContentComment;
    }

    public void setLastContentComment(String lastContentComment) {
        this.lastContentComment = lastContentComment;
    }

    public String getPubType() {
        return pubType;
    }

    public void setPubType(String pubType) {
        this.pubType = pubType;
    }

    public String getLastPubType() {
        return lastPubType;
    }

    public void setLastPubType(String lastPubType) {
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
            && null == currency
            && null == status
            && CollectionUtils.isEmpty(assignees)
            && CollectionUtils.isEmpty(lastValuePeriods)
            && null == wrWrkInst
            && systemTitleExpression.isEmpty()
            && systemStandardNumberExpression.isEmpty()
            && null == rhAccountNumber
            && rhNameExpression.isEmpty()
            && priceExpression.isEmpty()
            && priceInUsdExpression.isEmpty()
            && null == lastPriceFlag
            && null == lastPriceComment
            && contentExpression.isEmpty()
            && null == lastContentFlag
            && null == lastContentComment
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
            .append(currency, that.currency)
            .append(status, that.status)
            .append(assignees, that.assignees)
            .append(lastValuePeriods, that.lastValuePeriods)
            .append(wrWrkInst, that.wrWrkInst)
            .append(systemTitleExpression, that.systemTitleExpression)
            .append(systemStandardNumberExpression, that.systemStandardNumberExpression)
            .append(rhAccountNumber, that.rhAccountNumber)
            .append(rhNameExpression, that.rhNameExpression)
            .append(priceExpression, that.priceExpression)
            .append(priceInUsdExpression, that.priceInUsdExpression)
            .append(lastPriceFlag, that.lastPriceFlag)
            .append(lastPriceComment, that.lastPriceComment)
            .append(contentExpression, that.contentExpression)
            .append(lastContentFlag, that.lastContentFlag)
            .append(lastContentComment, that.lastContentComment)
            .append(pubType, that.pubType)
            .append(lastPubType, that.lastPubType)
            .append(comment, that.comment)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(periods)
            .append(currency)
            .append(status)
            .append(assignees)
            .append(lastValuePeriods)
            .append(wrWrkInst)
            .append(systemTitleExpression)
            .append(systemStandardNumberExpression)
            .append(rhAccountNumber)
            .append(rhNameExpression)
            .append(priceExpression)
            .append(priceInUsdExpression)
            .append(lastPriceFlag)
            .append(lastPriceComment)
            .append(contentExpression)
            .append(lastContentFlag)
            .append(lastContentComment)
            .append(pubType)
            .append(lastPubType)
            .append(comment)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("periods", periods)
            .append("currency", currency)
            .append("status", status)
            .append("assignees", assignees)
            .append("lastValuePeriods", lastValuePeriods)
            .append("wrWrkInst", wrWrkInst)
            .append("systemTitleExpression", systemTitleExpression)
            .append("systemStandardNumberExpression", systemStandardNumberExpression)
            .append("rhAccountNumber", rhAccountNumber)
            .append("rhNameExpression", rhNameExpression)
            .append("priceExpression", priceExpression)
            .append("priceInUsdExpression", priceInUsdExpression)
            .append("lastPriceFlag", lastPriceFlag)
            .append("lastPriceComment", lastPriceComment)
            .append("contentExpression", contentExpression)
            .append("lastContentFlag", lastContentFlag)
            .append("lastContentComment", lastContentComment)
            .append("pubType", pubType)
            .append("lastPubType", lastPubType)
            .append("comment", comment)
            .toString();
    }
}
