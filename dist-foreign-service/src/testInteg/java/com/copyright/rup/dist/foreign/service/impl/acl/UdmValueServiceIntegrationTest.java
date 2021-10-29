package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.Currency;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Verifies {@link UdmValueService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/15/2021
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UdmValueServiceIntegrationTest {

    private static final List<Currency> CURRENCIES = Arrays.asList(
        new Currency("USD", "US Dollar"),
        new Currency("AUD", "Australian Dollar"),
        new Currency("CAD", "Canadian Dollar"),
        new Currency("EUR", "Euro"),
        new Currency("GBP", "Pound Sterling"),
        new Currency("JPY", "Yen"),
        new Currency("BRL", "Brazilian Real"),
        new Currency("CNY", "Yuan Renminbi"),
        new Currency("CZK", "Czech Koruna"),
        new Currency("DKK", "Danish Krone"),
        new Currency("NZD", "New Zealand Dollar"),
        new Currency("NOK", "Norwegian Kron"),
        new Currency("ZAR", "Rand"),
        new Currency("CHF", "Swiss Franc"),
        new Currency("INR", "Indian Rupee"));

    @Autowired
    private IUdmValueService valueService;

    @Test
    public void testGetCurrencyCodesToCurrencyNames() {
        assertEquals(CURRENCIES, valueService.getAllCurrencies());
    }

    @Test
    public void testGetUdmRecordThreshold() {
        assertEquals(10000, valueService.getUdmRecordThreshold());
    }
}
