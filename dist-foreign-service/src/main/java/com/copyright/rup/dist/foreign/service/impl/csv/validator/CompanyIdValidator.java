package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.CompanyInformation;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.integration.telesales.api.ITelesalesService;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * The validator to check that Company Id exists in Telesales and Detail Licnesee Class ID is presented in FDA database.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/21/21
 *
 * @author Darya Baraukova
 */
public class CompanyIdValidator implements DistCsvProcessor.IValidator<UdmUsage> {

    private final ITelesalesService telesalesService;
    private final ILicenseeClassService licenseeClassService;
    private String errorMessage = StringUtils.EMPTY;

    /**
     * Constructor.
     *
     * @param telesalesService     {@link ITelesalesService} instance
     * @param licenseeClassService {@link ILicenseeClassService} instance
     */
    public CompanyIdValidator(ITelesalesService telesalesService, ILicenseeClassService licenseeClassService) {
        this.telesalesService = telesalesService;
        this.licenseeClassService = licenseeClassService;
    }

    @Override
    public boolean isValid(UdmUsage udmUsage) {
        errorMessage = StringUtils.EMPTY;
        Long companyId = udmUsage.getCompanyId();
        boolean isValid = false;
        if (Objects.nonNull(companyId)) {
            CompanyInformation companyInformation = telesalesService.getCompanyInformation(companyId);
            if (Objects.isNull(companyInformation) || StringUtils.isBlank(companyInformation.getName())) {
                errorMessage = "Company ID is not found in Telesales";
            } else if (Objects.isNull(companyInformation.getDetailedLicenseeClassId())
                || !licenseeClassService.detailLicenseeClassExists(companyInformation.getDetailedLicenseeClassId())) {
                errorMessage = "Detail Licensee Class ID for provided Company ID is invalid or empty";
            } else {
                isValid = true;
            }
        }
        return isValid;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
