package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents fund pool.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 12/03/2018
 *
 * @author Aliaksandr Liakh
 */
// TODO {aliakh} to add as field to UsageBatch
public class FundPool {

    private Integer marketPeriodFrom;
    private Integer marketPeriodTo;
    private BigDecimal stmAmount;
    private BigDecimal nonStmAmount;
    private BigDecimal stmMinimumAmount;
    private BigDecimal nonStmMinimumAmount;
    private List<String> markets = new ArrayList<>();

    public Integer getMarketPeriodFrom() {
        return marketPeriodFrom;
    }

    public void setMarketPeriodFrom(Integer marketPeriodFrom) {
        this.marketPeriodFrom = marketPeriodFrom;
    }

    public Integer getMarketPeriodTo() {
        return marketPeriodTo;
    }

    public void setMarketPeriodTo(Integer marketPeriodTo) {
        this.marketPeriodTo = marketPeriodTo;
    }

    public BigDecimal getStmAmount() {
        return stmAmount;
    }

    public void setStmAmount(BigDecimal stmAmount) {
        this.stmAmount = stmAmount;
    }

    public BigDecimal getNonStmAmount() {
        return nonStmAmount;
    }

    public void setNonStmAmount(BigDecimal nonStmAmount) {
        this.nonStmAmount = nonStmAmount;
    }

    public BigDecimal getStmMinimumAmount() {
        return stmMinimumAmount;
    }

    public void setStmMinimumAmount(BigDecimal stmMinimumAmount) {
        this.stmMinimumAmount = stmMinimumAmount;
    }

    public BigDecimal getNonStmMinimumAmount() {
        return nonStmMinimumAmount;
    }

    public void setNonStmMinimumAmount(BigDecimal nonStmMinimumAmount) {
        this.nonStmMinimumAmount = nonStmMinimumAmount;
    }

    public List<String> getMarkets() {
        return markets;
    }

    public void setMarkets(List<String> markets) {
        this.markets = markets;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        FundPool that = (FundPool) obj;
        return new EqualsBuilder()
            .append(marketPeriodFrom, that.marketPeriodFrom)
            .append(marketPeriodTo, that.marketPeriodTo)
            .append(stmAmount, that.stmAmount)
            .append(nonStmAmount, that.nonStmAmount)
            .append(stmMinimumAmount, that.stmMinimumAmount)
            .append(nonStmMinimumAmount, that.nonStmMinimumAmount)
            .append(markets, that.markets)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(marketPeriodFrom)
            .append(marketPeriodTo)
            .append(stmAmount)
            .append(nonStmAmount)
            .append(stmMinimumAmount)
            .append(nonStmMinimumAmount)
            .append(markets)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("periodFrom", marketPeriodFrom)
            .append("periodTo", marketPeriodTo)
            .append("stmAmount", stmAmount)
            .append("nonStmAmount", nonStmAmount)
            .append("stmMinimumAmount", stmMinimumAmount)
            .append("nonStmMinimumAmount", nonStmMinimumAmount)
            .append("markets", markets)
            .toString();
    }
}
