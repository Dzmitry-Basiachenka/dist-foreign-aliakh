package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents fund pool.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 12/03/2018
 *
 * @author Aliaksandr Liakh
 */
public class FundPool {

    private Integer fundPoolPeriodFrom;
    private Integer fundPoolPeriodTo;
    private BigDecimal stmAmount;
    private BigDecimal nonStmAmount;
    private BigDecimal stmMinimumAmount;
    private BigDecimal nonStmMinimumAmount;
    private Set<String> markets = new HashSet<>();
    private boolean excludingStm;

    public Integer getFundPoolPeriodFrom() {
        return fundPoolPeriodFrom;
    }

    public void setFundPoolPeriodFrom(Integer fundPoolPeriodFrom) {
        this.fundPoolPeriodFrom = fundPoolPeriodFrom;
    }

    public Integer getFundPoolPeriodTo() {
        return fundPoolPeriodTo;
    }

    public void setFundPoolPeriodTo(Integer fundPoolPeriodTo) {
        this.fundPoolPeriodTo = fundPoolPeriodTo;
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

    public Set<String> getMarkets() {
        return markets;
    }

    public void setMarkets(Set<String> markets) {
        this.markets = markets;
    }

    public boolean isExcludingStm() {
        return excludingStm;
    }

    public void setExcludingStm(boolean excludingStm) {
        this.excludingStm = excludingStm;
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
            .append(fundPoolPeriodFrom, that.fundPoolPeriodFrom)
            .append(fundPoolPeriodTo, that.fundPoolPeriodTo)
            .append(stmAmount, that.stmAmount)
            .append(nonStmAmount, that.nonStmAmount)
            .append(stmMinimumAmount, that.stmMinimumAmount)
            .append(nonStmMinimumAmount, that.nonStmMinimumAmount)
            .append(markets, that.markets)
            .append(excludingStm, that.excludingStm)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(fundPoolPeriodFrom)
            .append(fundPoolPeriodTo)
            .append(stmAmount)
            .append(nonStmAmount)
            .append(stmMinimumAmount)
            .append(nonStmMinimumAmount)
            .append(markets)
            .append(excludingStm)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("fundPoolPeriodFrom", fundPoolPeriodFrom)
            .append("fundPoolPeriodTo", fundPoolPeriodTo)
            .append("stmAmount", stmAmount)
            .append("nonStmAmount", nonStmAmount)
            .append("stmMinimumAmount", stmMinimumAmount)
            .append("nonStmMinimumAmount", nonStmMinimumAmount)
            .append("markets", markets)
            .append("excludingStm", excludingStm)
            .toString();
    }
}
