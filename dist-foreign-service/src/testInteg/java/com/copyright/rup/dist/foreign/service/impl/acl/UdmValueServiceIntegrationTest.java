package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueService;

import com.google.common.collect.ImmutableMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

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

    private static final Map<String, String> CURRENCY_CODES_TO_CURRENCY_NAMES_MAP =
        ImmutableMap.<String, String>builder()
            .put("USD", "US Dollar")
            .put("AUD", "Australian Dollar")
            .put("CAD", "Canadian Dollar")
            .put("EUR", "Euro")
            .put("GBP", "Pound Sterling")
            .put("JPY", "Yen")
            .put("BRL", "Brazilian Real")
            .put("CNY", "Yuan Renminbi")
            .put("CZK", "Czech Koruna")
            .put("DKK", "Danish Krone")
            .put("NZD", "New Zealand Dollar")
            .put("NOK", "Norwegian Kron")
            .put("ZAR", "Rand")
            .put("CHF", "Swiss Franc")
            .put("INR", "Indian Rupee")
            .build();

    @Autowired
    private IUdmValueService valueService;

    @Test
    public void testGetCurrencyCodesToCurrencyNames() {
        assertEquals(CURRENCY_CODES_TO_CURRENCY_NAMES_MAP, valueService.getCurrencyCodesToCurrencyNamesMap());
    }
}
