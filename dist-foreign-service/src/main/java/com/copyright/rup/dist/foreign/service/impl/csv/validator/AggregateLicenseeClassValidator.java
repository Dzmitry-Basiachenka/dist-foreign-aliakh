package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * The validator for Aggregate Licensee Class Id.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 05/02/2020
 *
 * @author Aliaksandr Liakh
 */
public class AggregateLicenseeClassValidator implements DistCsvProcessor.IValidator<FundPoolDetail> {

    private final Set<Integer> aggregateLicenseeClassIds;

    /**
     * Constructor.
     *
     * @param licenseeClassService instance of {@link ILicenseeClassService}
     * @param productFamily        product family
     */
    public AggregateLicenseeClassValidator(ILicenseeClassService licenseeClassService, String productFamily) {
        this.aggregateLicenseeClassIds = licenseeClassService.getAggregateLicenseeClasses(productFamily).stream()
            .map(AggregateLicenseeClass::getId)
            .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(FundPoolDetail detail) {
        return aggregateLicenseeClassIds.contains(detail.getAggregateLicenseeClass().getId());
    }

    @Override
    public String getErrorMessage() {
        return "Aggregate Licensee Class with such ID doesn't exist in the system";
    }
}
