package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents class storing exchange rate information which are got from RFEX service.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/14/21
 *
 * @author Anton Azarenka
 */
public class ExchangeRate {

    private String baseCurrencyCode;
    private String foreignCurrencyCode;
    private BigDecimal exchangeRateValue;
    private BigDecimal inverseExchangeRateValue;
    private LocalDate exchangeRateUpdateDate;

    public String getBaseCurrencyCode() {
        return baseCurrencyCode;
    }

    public void setBaseCurrencyCode(String baseCurrencyCode) {
        this.baseCurrencyCode = baseCurrencyCode;
    }

    public String getForeignCurrencyCode() {
        return foreignCurrencyCode;
    }

    public void setForeignCurrencyCode(String foreignCurrencyCode) {
        this.foreignCurrencyCode = foreignCurrencyCode;
    }

    public BigDecimal getExchangeRateValue() {
        return exchangeRateValue;
    }

    public void setExchangeRateValue(BigDecimal exchangeRateValue) {
        this.exchangeRateValue = exchangeRateValue;
    }

    public BigDecimal getInverseExchangeRateValue() {
        return inverseExchangeRateValue;
    }

    public void setInverseExchangeRateValue(BigDecimal inverseExchangeRateValue) {
        this.inverseExchangeRateValue = inverseExchangeRateValue;
    }

    public LocalDate getExchangeRateUpdateDate() {
        return exchangeRateUpdateDate;
    }

    public void setExchangeRateUpdateDate(LocalDate exchangeRateUpdateDate) {
        this.exchangeRateUpdateDate = exchangeRateUpdateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (null == o || getClass() != o.getClass()) {
            return false;
        }

        ExchangeRate that = (ExchangeRate) o;

        return new EqualsBuilder().append(baseCurrencyCode, that.baseCurrencyCode)
            .append(foreignCurrencyCode, that.foreignCurrencyCode)
            .append(exchangeRateValue, that.exchangeRateValue)
            .append(inverseExchangeRateValue, that.inverseExchangeRateValue)
            .append(exchangeRateUpdateDate, that.exchangeRateUpdateDate)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(baseCurrencyCode)
            .append(foreignCurrencyCode)
            .append(exchangeRateValue)
            .append(inverseExchangeRateValue)
            .append(exchangeRateUpdateDate)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("baseCurrencyCode", baseCurrencyCode)
            .append("foreignCurrencyCode", foreignCurrencyCode)
            .append("exchangeRateValue", exchangeRateValue)
            .append("inverseExchangeRateValue", inverseExchangeRateValue)
            .append("exchangeRateUpdateDate", exchangeRateUpdateDate)
            .toString();
    }
}
