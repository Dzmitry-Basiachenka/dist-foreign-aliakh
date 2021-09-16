package com.copyright.rup.dist.foreign.integration.rfex;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.domain.ExchangeRate;
import com.copyright.rup.dist.foreign.integration.rfex.api.IRfexIntegrationService;
import com.copyright.rup.dist.foreign.integration.rfex.impl.RfexIntegrationCacheService;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Verifies {@link RfexIntegrationCacheService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/16/21
 *
 * @author Anton Azarenka
 */
public class RfexIntegrationCacheServiceTest {

    private static final String EUR_CURRENCY = "EUR";
    private static final String BYN_CURRENCY = "BYN";
    private static final String USD_CURRENCY = "USD";
    private static final LocalDate EXCHANGE_DATE = LocalDate.now();
    private static final BigDecimal EXCHANGE_RATE_VALUE = BigDecimal.ONE;
    private IRfexIntegrationService rfexIntegrationService;
    private RfexIntegrationCacheService cacheService;

    @Before
    public void setUp() {
        rfexIntegrationService = createMock(IRfexIntegrationService.class);
        cacheService = new RfexIntegrationCacheService(rfexIntegrationService);
    }

    @Test
    public void testGetCountriesWithEmptyCache() {
        expect(rfexIntegrationService.getExchangeRate(EUR_CURRENCY, EXCHANGE_DATE)).andReturn(buildExchangeRate())
            .once();
        replay(rfexIntegrationService);
        cacheService.createCache();
        verifyResult(EUR_CURRENCY, cacheService.getExchangeRate(EUR_CURRENCY, EXCHANGE_DATE));
        verify(rfexIntegrationService);
    }

    @Test
    public void testGetCountriesWithPopulatedCache() {
        expect(rfexIntegrationService.getExchangeRate(EUR_CURRENCY, EXCHANGE_DATE)).andReturn(buildExchangeRate())
            .once();
        ExchangeRate exchangeRate = buildExchangeRate();
        exchangeRate.setForeignCurrencyCode(BYN_CURRENCY);
        expect(rfexIntegrationService.getExchangeRate(BYN_CURRENCY, EXCHANGE_DATE)).andReturn(exchangeRate).once();
        replay(rfexIntegrationService);
        cacheService.createCache();
        ExchangeRate result = cacheService.getExchangeRate(EUR_CURRENCY, EXCHANGE_DATE);
        verifyResult(EUR_CURRENCY, result);
        ExchangeRate cachedResult = cacheService.getExchangeRate(EUR_CURRENCY, EXCHANGE_DATE);
        verifyResult(EUR_CURRENCY, cachedResult);
        assertSame(result, cachedResult);
        result = cacheService.getExchangeRate(BYN_CURRENCY, EXCHANGE_DATE);
        verifyResult(BYN_CURRENCY, result);
        verify(rfexIntegrationService);
    }

    private void verifyResult(String expectedForignCurrencyCode, ExchangeRate result) {
        assertNotNull(result);
        assertEquals(USD_CURRENCY, result.getBaseCurrencyCode());
        assertEquals(expectedForignCurrencyCode, result.getForeignCurrencyCode());
        assertEquals(EXCHANGE_RATE_VALUE, result.getExchangeRateValue());
        assertEquals(EXCHANGE_RATE_VALUE, result.getInverseExchangeRateValue());
        assertEquals(EXCHANGE_DATE, result.getExchangeRateUpdateDate());
    }

    private ExchangeRate buildExchangeRate() {
        ExchangeRate result = new ExchangeRate();
        result.setBaseCurrencyCode(USD_CURRENCY);
        result.setForeignCurrencyCode(EUR_CURRENCY);
        result.setExchangeRateValue(EXCHANGE_RATE_VALUE);
        result.setInverseExchangeRateValue(EXCHANGE_RATE_VALUE);
        result.setExchangeRateUpdateDate(EXCHANGE_DATE);
        return result;
    }
}
