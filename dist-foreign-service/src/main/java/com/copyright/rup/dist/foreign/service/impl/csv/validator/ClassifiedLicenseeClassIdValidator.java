package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static com.google.common.base.Preconditions.checkNotNull;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.service.api.IDetailLicenseeClassService;

/**
 * The validator for Classified AACL usages to check if licensee class id exists in
 * df_detail_licensee_class table based on fields of enrolment profile and discipline.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/25/2020
 *
 * @author Anton Azarenka
 */
public class ClassifiedLicenseeClassIdValidator implements DistCsvProcessor.IValidator<AaclClassifiedUsage> {

    private final IDetailLicenseeClassService detailLicenseeClassService;

    /**
     * Constructor.
     *
     * @param detailLicenseeClassService instance of {@link IDetailLicenseeClassService}
     */
    public ClassifiedLicenseeClassIdValidator(IDetailLicenseeClassService detailLicenseeClassService) {
        this.detailLicenseeClassService = detailLicenseeClassService;
    }

    @Override
    public boolean isValid(AaclClassifiedUsage aaclClassifiedUsage) {
        checkNotNull(aaclClassifiedUsage);
        return isUsageDisqualified(aaclClassifiedUsage)
            || detailLicenseeClassService.isDetailLicenceClassExist(aaclClassifiedUsage.getEnrollmentProfile(),
            aaclClassifiedUsage.getDiscipline());
    }

    @Override
    public String getErrorMessage() {
        return "Detail License Class Id with such enrolment profile and discipline doesn't exist in the system";
    }

    private boolean isUsageDisqualified(AaclClassifiedUsage aaclClassifiedUsage) {
        return "disqualified".equals(aaclClassifiedUsage.getEnrollmentProfile())
            || "disqualified".equals(aaclClassifiedUsage.getDiscipline());
    }
}
