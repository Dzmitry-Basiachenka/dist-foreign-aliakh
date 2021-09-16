package com.copyright.rup.dist.foreign.integration.rfex.api;

import com.copyright.rup.dist.foreign.domain.ExchangeRate;

import java.time.LocalDate;

/**
 * Represents interface of service for loading exchange rates information.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/14/21
 *
 * @author Anton Azarenka
 */
public interface IRfexIntegrationService {

    /**
     * Gets exchange rate of foreign currency to USD.
     *
     * @param foreignCurrencyCode foreign currency code to load exchange rate
     * @param date                date when the currency data was updated from the service provider
     * @return instance of {@link ExchangeRate}
     */
    ExchangeRate getExchangeRate(String foreignCurrencyCode, LocalDate date);
}
