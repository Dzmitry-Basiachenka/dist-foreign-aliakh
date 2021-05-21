package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.CompanyInformation;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.integration.telesales.api.ITelesalesService;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link CompanyIdValidator}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/21/21
 *
 * @author Darya Baraukova
 */
public class CompanyIdValidatorTest {

    private ITelesalesService telesalesService;
    private ILicenseeClassService licenseeClassService;
    private CompanyIdValidator validator;

    @Before
    public void setUp() {
        telesalesService = createMock(ITelesalesService.class);
        licenseeClassService = createMock(ILicenseeClassService.class);
        validator = new CompanyIdValidator(telesalesService, licenseeClassService);
    }

    @Test
    public void testIsValidInvalidDetailLicenseeClassId() {
        UdmUsage usage = new UdmUsage();
        usage.setCompanyId(1136L);
        CompanyInformation companyInformation = new CompanyInformation();
        companyInformation.setId(1136L);
        companyInformation.setName("Albany International Corp.");
        companyInformation.setDetailedLicenseeClassId(333);
        expect(telesalesService.getCompanyInformation(1136L)).andReturn(companyInformation).once();
        expect(licenseeClassService.detailLicenseeClassExists(333)).andReturn(false).once();
        replay(telesalesService, licenseeClassService);
        assertFalse(validator.isValid(usage));
        assertEquals("Detail Licensee Class ID for provided Company ID is invalid or empty",
            validator.getErrorMessage());
        verify(telesalesService, licenseeClassService);
    }

    @Test
    public void testIsValidNullResult() {
        UdmUsage usage = new UdmUsage();
        usage.setCompanyId(1136L);
        expect(telesalesService.getCompanyInformation(1136L)).andReturn(null).once();
        replay(telesalesService, licenseeClassService);
        assertFalse(validator.isValid(usage));
        assertEquals("Company ID is not found in Telesales", validator.getErrorMessage());
        verify(telesalesService, licenseeClassService);
    }

    @Test
    public void testIsValid() {
        UdmUsage usage = new UdmUsage();
        usage.setCompanyId(1136L);
        CompanyInformation companyInformation = new CompanyInformation();
        companyInformation.setId(1136L);
        companyInformation.setName("Albany International Corp.");
        companyInformation.setDetailedLicenseeClassId(1);
        expect(telesalesService.getCompanyInformation(1136L)).andReturn(companyInformation).once();
        expect(licenseeClassService.detailLicenseeClassExists(1)).andReturn(true).once();
        replay(telesalesService, licenseeClassService);
        assertTrue(validator.isValid(usage));
        assertTrue(StringUtils.isBlank(validator.getErrorMessage()));
        verify(telesalesService, licenseeClassService);
    }
}
