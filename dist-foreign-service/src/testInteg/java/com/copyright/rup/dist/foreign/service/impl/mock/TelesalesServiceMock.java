package com.copyright.rup.dist.foreign.service.impl.mock;

import com.copyright.rup.dist.foreign.domain.CompanyInformation;
import com.copyright.rup.dist.foreign.integration.telesales.api.ITelesalesService;

/**
 * Mock service for {@link com.copyright.rup.dist.foreign.integration.telesales.impl.TelesalesService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/24/21
 *
 * @author Darya Baraukova
 */
public class TelesalesServiceMock implements ITelesalesService {

    private static final Long COMPANY_ID_1136 = 1136L;
    private static final Long COMPANY_ID_1139 = 1139L;
    private static final Long COMPANY_ID_1142 = 1142L;

    @Override
    public String getLicenseeName(Long licenseeAccountNumber) {
        return null;
    }

    @Override
    public CompanyInformation getCompanyInformation(Long companyId) {
        CompanyInformation companyInformation = null;
        if (COMPANY_ID_1136.equals(companyId)) {
            companyInformation = new CompanyInformation();
            companyInformation.setId(COMPANY_ID_1136);
            companyInformation.setName("Albany International Corp.");
            companyInformation.setDetailedLicenseeClassId(2);
        } else if (COMPANY_ID_1139.equals(companyId)) {
            companyInformation = new CompanyInformation();
            companyInformation.setId(COMPANY_ID_1139);
        } else if (COMPANY_ID_1142.equals(companyId)) {
            companyInformation = new CompanyInformation();
            companyInformation.setId(COMPANY_ID_1142);
            companyInformation.setName("Educational Testing Service");
            companyInformation.setDetailedLicenseeClassId(1000);
        }
        return companyInformation;
    }
}
