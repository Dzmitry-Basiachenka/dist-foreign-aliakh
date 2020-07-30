package com.copyright.rup.dist.foreign.integration.telesales.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.test.TestUtils;

import com.google.common.collect.ImmutableBiMap;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.springframework.web.client.RestTemplate;

/**
 * Verifies {@link TelesalesService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/30/2020
 *
 * @author Ihar Suvorau
 */
public class TelesalesServiceTest {

    private static final String TELESALES_URL =
        "http://localhost:8080/legacy-integration-rest/getSalesInfoByCompanyCode?companycode={licenseeAccountNumber}";
    private static final long ACCOUNT_NUMBER = 7001413934L;

    private TelesalesService telesalesService;
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = createMock(RestTemplate.class);
        telesalesService = new TelesalesService();
        Whitebox.setInternalState(telesalesService, restTemplate);
        Whitebox.setInternalState(telesalesService, "salesInfoUrl", TELESALES_URL);
    }

    @Test
    public void testGetLicenseeName() {
        expect(
            restTemplate.getForObject(
                TELESALES_URL, String.class, ImmutableBiMap.of("licenseeAccountNumber", ACCOUNT_NUMBER)))
            .andReturn(TestUtils.fileToString(this.getClass(), "sales_data_response.json"))
            .once();
        replay(restTemplate);
        assertEquals("Coors Brewing Company", telesalesService.getLicenseeName(ACCOUNT_NUMBER));
        verify(restTemplate);
    }
}
