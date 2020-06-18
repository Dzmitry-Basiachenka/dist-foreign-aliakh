package com.copyright.rup.dist.foreign.integration.oracle.impl;

import com.copyright.rup.dist.common.integration.IntegrationConnectionException;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleRhTaxCountryChunkService;
import com.copyright.rup.dist.foreign.integration.oracle.impl.handler.OracleRhTaxCountryChunkRestHandler;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Set;

/**
 * Implementation of {@link IOracleRhTaxCountryChunkService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 05/04/2020
 *
 * @author Uladzislau Shalamitski
 */
@Service("df.integration.oracleRhTaxCountryChunkService")
public class OracleRhTaxCountryChunkService implements IOracleRhTaxCountryChunkService {

    private static final String CONNECTION_EXCEPTION_MESSAGE = "Could not connect to the Oracle AP";

    @Autowired
    private RestTemplate restTemplate;

    @Value("$RUP{dist.foreign.rest.oracle.rh_tax_country.url}")
    private String rhTaxCountryUrl;

    @Override
    public Map<Long, Boolean> isUsTaxCountry(Set<Long> accountNumbers) {
        try {
            OracleRhTaxCountryChunkRestHandler handler = new OracleRhTaxCountryChunkRestHandler(restTemplate);
            return handler.handleResponse(rhTaxCountryUrl, ImmutableMap.of("accountNumbers",
                Joiner.on(",").skipNulls().join(accountNumbers)));
        } catch (HttpClientErrorException | ResourceAccessException e) {
            throw new IntegrationConnectionException(CONNECTION_EXCEPTION_MESSAGE, e);
        }
    }
}
