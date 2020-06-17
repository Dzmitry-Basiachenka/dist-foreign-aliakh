package com.copyright.rup.dist.foreign.integration.oracle.impl;

import com.copyright.rup.dist.common.integration.IntegrationConnectionException;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleRhTaxCountryService;
import com.copyright.rup.dist.foreign.integration.oracle.impl.handler.OracleRhTaxCountryRestHandler;

import com.google.common.collect.ImmutableMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation of {@link IOracleRhTaxCountryService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/27/2018
 *
 * @author Aliaksandr Liakh
 */
@Service("df.integration.oracleRhTaxCountryService")
public class OracleRhTaxCountryService implements IOracleRhTaxCountryService {

    private static final String CONNECTION_EXCEPTION_MESSAGE = "Could not connect to the Oracle AP";

    @Autowired
    private RestTemplate restTemplate;

    @Value("$RUP{dist.foreign.rest.oracle.rh_tax_country.url}")
    private String rhTaxCountryUrl;

    @Override
    public boolean isUsTaxCountry(Long accountNumber) {
        try {
            OracleRhTaxCountryRestHandler handler = new OracleRhTaxCountryRestHandler(restTemplate);
            return handler.handleResponse(rhTaxCountryUrl, ImmutableMap.of("accountNumbers", accountNumber));
        } catch (HttpClientErrorException | ResourceAccessException e) {
            throw new IntegrationConnectionException(CONNECTION_EXCEPTION_MESSAGE, e);
        }
    }
}
