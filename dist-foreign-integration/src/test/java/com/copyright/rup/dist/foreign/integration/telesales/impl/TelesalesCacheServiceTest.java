package com.copyright.rup.dist.foreign.integration.telesales.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.CompanyInformation;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link TelesalesCacheService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/26/2021
 *
 * @author Dzmitry Basiachenka
 */
public class TelesalesCacheServiceTest {

    private static final Long COMPANY_ID_1 = 1136L;
    private static final Long COMPANY_ID_2 = 1137L;
    private static final Long COMPANY_ID_3 = 1138L;
    private static final String COMPANY_NAME_1 = "Albany International Corp.";
    private static final String COMPANY_NAME_2 = "Alberto-Culver Company";
    private static final String COMPANY_NAME_3 = "Alcon Laboratories, Inc.";
    private TelesalesCacheService telesalesCacheService;
    private TelesalesService telesalesService;

    @Before
    public void setUp() {
        telesalesService = createMock(TelesalesService.class);
        telesalesCacheService = new TelesalesCacheService(telesalesService, 1);
        telesalesCacheService.createCache();
    }

    @Test
    public void testGetLicenseeName() {
        CompanyInformation companyInformation1 = buildCompanyInformation(COMPANY_NAME_1);
        CompanyInformation companyInformation2 = buildCompanyInformation(COMPANY_NAME_2);
        CompanyInformation companyInformation3 = buildCompanyInformation(COMPANY_NAME_3);
        expect(telesalesService.getCompanyInformation(COMPANY_ID_1)).andReturn(companyInformation1).once();
        expect(telesalesService.getCompanyInformation(COMPANY_ID_2)).andReturn(companyInformation2).once();
        expect(telesalesService.getCompanyInformation(COMPANY_ID_3)).andReturn(companyInformation3).once();
        replay(telesalesService);
        assertEquals(COMPANY_NAME_1, telesalesCacheService.getLicenseeName(COMPANY_ID_1));
        assertEquals(COMPANY_NAME_2, telesalesCacheService.getLicenseeName(COMPANY_ID_2));
        assertEquals(COMPANY_NAME_3, telesalesCacheService.getLicenseeName(COMPANY_ID_3));
        verify(telesalesService);
    }

    @Test
    public void testGetCompanyInformation() {
        CompanyInformation companyInformation1 = buildCompanyInformation(COMPANY_NAME_1);
        CompanyInformation companyInformation2 = buildCompanyInformation(COMPANY_NAME_2);
        CompanyInformation companyInformation3 = buildCompanyInformation(COMPANY_NAME_3);
        expect(telesalesService.getCompanyInformation(COMPANY_ID_1)).andReturn(companyInformation1).once();
        expect(telesalesService.getCompanyInformation(COMPANY_ID_2)).andReturn(companyInformation2).once();
        expect(telesalesService.getCompanyInformation(COMPANY_ID_3)).andReturn(companyInformation3).once();
        replay(telesalesService);
        assertEquals(companyInformation1, telesalesCacheService.getCompanyInformation(COMPANY_ID_1));
        assertEquals(companyInformation2, telesalesCacheService.getCompanyInformation(COMPANY_ID_2));
        assertEquals(companyInformation3, telesalesCacheService.getCompanyInformation(COMPANY_ID_3));
        verify(telesalesService);
    }

    private CompanyInformation buildCompanyInformation(String name) {
        CompanyInformation companyInformation = new CompanyInformation();
        companyInformation.setName(name);
        return companyInformation;
    }
}
