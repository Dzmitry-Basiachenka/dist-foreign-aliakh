package com.copyright.rup.dist.foreign.integration.rfex.impl;

import com.copyright.rup.common.caching.impl.AbstractCacheService;
import com.copyright.rup.dist.foreign.domain.ExchangeRate;
import com.copyright.rup.dist.foreign.integration.rfex.api.IRfexIntegrationService;
import com.copyright.rup.dist.foreign.integration.rfex.impl.RfexIntegrationCacheService.ExchangeRateRequest;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import io.micrometer.core.annotation.Timed;

/**
 * Implementation of service for retrieving information about {@link ExchangeRate}s from PRM.
 * Uses {@link com.google.common.cache.Cache} for caching data and REST calls for retrieving data.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/14/21
 *
 * @author Anton Azarenka
 */
@Service("df.integration.rfexIntegrationCacheService")
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class RfexIntegrationCacheService extends AbstractCacheService<ExchangeRateRequest, ExchangeRate>
    implements IRfexIntegrationService {

    private final IRfexIntegrationService exchangeRateService;

    /**
     * Constructor.
     *
     * @param exchangeRateService instance of {@link IRfexIntegrationService}
     */
    @Autowired
    public RfexIntegrationCacheService(IRfexIntegrationService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
        super.setExpirationTime(TimeUnit.HOURS.toSeconds(24));
    }

    @Override
    protected ExchangeRate loadData(ExchangeRateRequest rateRequest) {
        return exchangeRateService.getExchangeRate(rateRequest.foreignCurrencyCode, rateRequest.exchangeRateDate);
    }

    @Override
    public ExchangeRate getExchangeRate(String foreignCurrencyCode, LocalDate date) {
        return getFromCache(new ExchangeRateRequest(foreignCurrencyCode, date));
    }

    /**
     * Represents request domain for exchange rate requests.
     */
    public static class ExchangeRateRequest {

        private final String foreignCurrencyCode;
        private final LocalDate exchangeRateDate;

        /**
         * Constructor.
         *
         * @param foreignCurrencyCode foreign currency code.
         * @param exchangeRateDate    exchange rate date.
         */
        ExchangeRateRequest(String foreignCurrencyCode, LocalDate exchangeRateDate) {
            this.foreignCurrencyCode = foreignCurrencyCode;
            this.exchangeRateDate = exchangeRateDate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (null == o || getClass() != o.getClass()) {
                return false;
            }

            ExchangeRateRequest that = (ExchangeRateRequest) o;
            return new EqualsBuilder()
                .append(foreignCurrencyCode, that.foreignCurrencyCode)
                .append(exchangeRateDate, that.exchangeRateDate)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                .append(foreignCurrencyCode)
                .append(exchangeRateDate)
                .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                .append("foreignCurrencyCode", foreignCurrencyCode)
                .append("exchangeRateDate", exchangeRateDate)
                .toString();
        }
    }
}
