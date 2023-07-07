package com.copyright.rup.dist.foreign.integration.rfex.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.rest.CommonRestHandler;
import com.copyright.rup.dist.common.integration.util.JsonUtils;
import com.copyright.rup.dist.foreign.domain.ExchangeRate;
import com.copyright.rup.dist.foreign.integration.rfex.api.IRfexIntegrationService;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import io.micrometer.core.annotation.Timed;

/**
 * Implementation of {@link IRfexIntegrationService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/14/21
 *
 * @author Anton Azarenka
 */
@Service("df.integration.rfexIntegrationService")
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class RfexIntegrationService implements IRfexIntegrationService {

    private static final String BASE_CURRENCY = "USD";
    private static final Logger LOGGER = RupLogUtils.getLogger();
    private final CommonRestHandler<ExchangeRate> restHandler;
    @Value("$RUP{dist.foreign.rest.rfex.url}")
    private String exchangeRateUrl;

    /**
     * Constructor.
     *
     * @param restTemplate instance of {@link RestTemplate}
     */
    @Autowired
    public RfexIntegrationService(RestTemplate restTemplate) {
        restHandler = new CommonRfexRestHandler(restTemplate);
    }

    @Override
    public ExchangeRate getExchangeRate(String foreignCurrencyCode, LocalDate date) {
        LOGGER.debug("Get exchange rate. Started. ForeignCurrencyCode={} ExchangeRateDate={}", foreignCurrencyCode,
            date);
        checkArgument(StringUtils.isNotBlank(foreignCurrencyCode), "Currency code shouldn't be null or empty");
        checkNotNull(date, "Exchange date shouldn't be null");
        ExchangeRate exchangeRate = doGetExchangeRate(restHandler, foreignCurrencyCode, date);
        LOGGER.debug("Get exchange rate. Finished. ForeignCurrencyCode={} ExchangeRateDate={}", foreignCurrencyCode,
            date);
        return exchangeRate;
    }

    private ExchangeRate doGetExchangeRate(CommonRestHandler<ExchangeRate> handler, String foreignCurrencyCode,
                                           LocalDate date) {
        return handler.handleResponse(exchangeRateUrl, buildParamsMap(foreignCurrencyCode, date));
    }

    private ImmutableMap<String, ? extends Serializable> buildParamsMap(String foreignCurrencyCode, LocalDate date) {
        return ImmutableMap.of("updateDate", date.format(DateTimeFormatter.ofPattern(RupDateUtils.DATE_FORMAT_PATTERN)),
            "baseCurrency", BASE_CURRENCY, "foreignCurrency", foreignCurrencyCode);
    }

    private ExchangeRate buildExchangeRate(JsonNode response) {
        JsonNode exchangeRateNode = response.get("rate");
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setBaseCurrencyCode(JsonUtils.getStringValue(exchangeRateNode.get("baseCurrency")));
        exchangeRate.setForeignCurrencyCode(JsonUtils.getStringValue(exchangeRateNode.get("foreignCurrency")));
        exchangeRate.setExchangeRateValue(JsonUtils.getBigDecimalValue(exchangeRateNode.get("exchangeRateValue")));
        exchangeRate.setInverseExchangeRateValue(
            JsonUtils.getBigDecimalValue(exchangeRateNode.get("inverseExchangeRateValue")));
        exchangeRate.setExchangeRateUpdateDate(
            getLocalDateValue(JsonUtils.getDateValue(exchangeRateNode.get("exchangeRateUpdateDate"))));
        return exchangeRate;
    }

    private LocalDate getLocalDateValue(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Handler for getting exchange rate.
     */
    public class CommonRfexRestHandler extends CommonRestHandler<ExchangeRate> {

        private static final String SYSTEM_NAME = "RFEX";

        /**
         * Constructor.
         *
         * @param restTemplate instance of {@link RestTemplate}
         */
        CommonRfexRestHandler(RestTemplate restTemplate) {
            super(restTemplate);
        }

        @Override
        protected ExchangeRate doHandleResponse(JsonNode response) {
            return buildExchangeRate(response);
        }

        @Override
        protected String getSystemName() {
            return SYSTEM_NAME;
        }

        @Override
        protected ExchangeRate getDefaultValue() {
            return null;
        }
    }
}
