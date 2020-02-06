package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.AaclFundPoolDetail;
import com.copyright.rup.dist.foreign.repository.api.IAaclFundPoolRepository;

import java.util.Set;

/**
 * The validator for Aggregate Licensee Class Id.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 05/02/2020
 *
 * @author Aliaksandr Liakh
 */
public class AggregateLicenseeClassValidator implements DistCsvProcessor.IValidator<AaclFundPoolDetail> {

    private final Set<Integer> aggregateLicenseeClassIds;

    /**
     * Constructor.
     *
     * @param aaclFundPoolRepository instance of {@link IAaclFundPoolRepository}
     */
    public AggregateLicenseeClassValidator(IAaclFundPoolRepository aaclFundPoolRepository) {
        this.aggregateLicenseeClassIds = aaclFundPoolRepository.findAggregateLicenseeClassIds();
    }

    @Override
    public boolean isValid(AaclFundPoolDetail detail) {
        return aggregateLicenseeClassIds.contains(detail.getAggregateLicenseeClass().getId());
    }

    @Override
    public String getErrorMessage() {
        return "Aggregate Licensee Class with such ID doesn't exist in the system";
    }
}
