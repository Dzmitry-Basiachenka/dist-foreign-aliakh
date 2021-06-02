package com.copyright.rup.dist.foreign.integration.telesales.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.test.TestUtils;

import com.copyright.rup.dist.foreign.domain.CompanyInformation;
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
    private static final String COMPANY_NAME = "Albany International Corp.";
    private static final long COMPANY_ID = 1136L;

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
                TELESALES_URL, String.class, ImmutableBiMap.of("companyCode", COMPANY_ID)))
            .andReturn(TestUtils.fileToString(this.getClass(), "sales_data_response.json"))
            .once();
        replay(restTemplate);
        assertEquals(COMPANY_NAME, telesalesService.getLicenseeName(COMPANY_ID));
        verify(restTemplate);
    }

    @Test
    public void testGetCompanyInformation() {
        CompanyInformation companyInformation = new CompanyInformation();
        companyInformation.setId(COMPANY_ID);
        companyInformation.setName(COMPANY_NAME);
        companyInformation.setDetailLicenseeClassId(2);
        expect(restTemplate.getForObject(TELESALES_URL, String.class, ImmutableBiMap.of("companyCode", COMPANY_ID)))
            .andReturn(TestUtils.fileToString(this.getClass(), "sales_data_response.json")).once();
        replay(restTemplate);
        CompanyInformation actualCompanyInformation = telesalesService.getCompanyInformation(COMPANY_ID);
        assertEquals(Long.valueOf(1136), actualCompanyInformation.getId());
        assertEquals(COMPANY_NAME, actualCompanyInformation.getName());
        assertEquals(Integer.valueOf(2), actualCompanyInformation.getDetailLicenseeClassId());
        verify(restTemplate);
    }
}
