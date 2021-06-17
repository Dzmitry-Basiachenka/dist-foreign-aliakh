package com.copyright.rup.dist.foreign.service.impl.mock;

import com.copyright.rup.dist.common.domain.Country;
import com.copyright.rup.dist.foreign.integration.prm.impl.PrmIntegrationService;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Mock for {@link PrmIntegrationService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/16/21
 *
 * @author Anton Azarenka
 */
public class PrmIntegrationServiceMock extends PrmIntegrationService {

    private final Map<String, Country> countryMap = ImmutableMap.of(
        "BY", createCountry("Belarus", "BLR"),
        "CA", createCountry("Canada", "CND"),
        "US", createCountry("United States", "USA"),
        "BG", createCountry("Bulgaria", "BGR"));

    @Override
    public Map<String, Country> getCountries() {
        return countryMap;
    }

    private Country createCountry(String name, String isoCode) {
        Country country = new Country();
        country.setName(name);
        country.setIsoCode(isoCode);
        return country;
    }
}
