package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.domain.Country;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * The validator to check that survey country is International or corresponds to ISO format.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/16/21
 *
 * @author Anton Azarenka
 */
public class CountryValidator implements DistCsvProcessor.IValidator<UdmUsage> {

    private final Set<String> countryNames;

    /**
     * Constructor.
     *
     * @param prmIntegrationService instance of {@link IPrmIntegrationService}
     */
    public CountryValidator(IPrmIntegrationService prmIntegrationService) {
        countryNames = prmIntegrationService.getCountries()
            .values()
            .stream()
            .map(Country::getName)
            .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(UdmUsage usage) {
        return "International".equals(usage.getSurveyCountry()) || countryNames.contains(usage.getSurveyCountry());
    }

    @Override
    public String getErrorMessage() {
        return "Survey country is not found in PRM or does not match ISO Country Name standard";
    }
}
