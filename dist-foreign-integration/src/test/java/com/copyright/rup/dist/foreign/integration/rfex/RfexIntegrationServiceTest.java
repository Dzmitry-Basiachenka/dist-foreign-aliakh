package com.copyright.rup.dist.foreign.integration.rfex;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.foreign.domain.ExchangeRate;
import com.copyright.rup.dist.foreign.integration.rfex.impl.RfexIntegrationService;
import com.copyright.rup.dist.foreign.integration.rfex.impl.RfexIntegrationService.CommonRfexRestHandler;

import com.google.common.collect.ImmutableMap;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Verifies {@link RfexIntegrationService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/15/21
 *
 * @author Anton Azarenka
 */
public class RfexIntegrationServiceTest {

    private static final String FORMATTED_DATE = "2015-01-01";
    private static final String INVALID_FORMATTED_DATE = "9999-01-01";
    private static final String AUD_CURRENCY_CODE = "AUD";
    private static final String USD_CODE = "USD";
    private static final String EXCHANGE_RATE_URL =
        "http://foreign-exchange-rest.aws-sh-prd.copyright.com/foreign-exchange-rest/rest-api/rate?date={updateDate}" +
            "&baseCurrency={baseCurrency}&foreignCurrency={foreignCurrency}&fmt=json";
    private static final LocalDate DATE = LocalDate.parse(FORMATTED_DATE);
    private static final LocalDate INVALID_DATE = LocalDate.parse(INVALID_FORMATTED_DATE);
    private static final BigDecimal EXCHANGE_RATE_VALUE = new BigDecimal("1.2243049009");
    private static final BigDecimal INVERSE_EXCHANGE_RATE_VALUE = new BigDecimal("0.81679");
    private RfexIntegrationService integrationService;
    private CommonRfexRestHandler restHandler;

    @Before
    public void setUp() {
        RestTemplate restTemplate = createMock(RestTemplate.class);
        integrationService = new RfexIntegrationService(restTemplate);
        restHandler = createMock(CommonRfexRestHandler.class);
        Whitebox.setInternalState(integrationService, "exchangeRateUrl", EXCHANGE_RATE_URL);
        Whitebox.setInternalState(integrationService, "restHandler", restHandler);
    }

    @Test
    public void testGetExchangeRate() {
        expect(restHandler.handleResponse(EXCHANGE_RATE_URL,
            ImmutableMap.of("updateDate", FORMATTED_DATE, "baseCurrency", USD_CODE, "foreignCurrency",
                AUD_CURRENCY_CODE))).andReturn(buildExchangeRate()).once();
        replay(restHandler);
        verifyExchangeRate(integrationService.getExchangeRate(AUD_CURRENCY_CODE, DATE));
        verify(restHandler);
    }

    @Test
    public void testGetCurrencyExchangeRateInvalidDate() {
        expect(restHandler.handleResponse(EXCHANGE_RATE_URL,
            ImmutableMap.of("updateDate", INVALID_FORMATTED_DATE, "baseCurrency", USD_CODE, "foreignCurrency",
                AUD_CURRENCY_CODE))).andReturn(null).once();
        replay(restHandler);
        assertNull(integrationService.getExchangeRate(AUD_CURRENCY_CODE, INVALID_DATE));
        verify(restHandler);
    }

    private void verifyExchangeRate(ExchangeRate exchangeRate) {
        assertNotNull(exchangeRate);
        assertEquals(USD_CODE, exchangeRate.getBaseCurrencyCode());
        assertEquals(AUD_CURRENCY_CODE, exchangeRate.getForeignCurrencyCode());
        assertEquals(EXCHANGE_RATE_VALUE, exchangeRate.getExchangeRateValue());
        assertEquals(INVERSE_EXCHANGE_RATE_VALUE, exchangeRate.getInverseExchangeRateValue());
        assertEquals(DATE, exchangeRate.getExchangeRateUpdateDate());
    }

    private ExchangeRate buildExchangeRate() {
        ExchangeRate result = new ExchangeRate();
        result.setBaseCurrencyCode("USD");
        result.setForeignCurrencyCode("AUD");
        result.setExchangeRateValue(EXCHANGE_RATE_VALUE);
        result.setInverseExchangeRateValue(INVERSE_EXCHANGE_RATE_VALUE);
        result.setExchangeRateUpdateDate(DATE);
        return result;
    }
}
