package com.copyright.rup.dist.foreign.integration.oracle.impl.handler;

import com.copyright.rup.dist.common.integration.rest.CommonRestHandler;
import com.copyright.rup.dist.common.integration.util.JsonUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Maps;

import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

/**
 * Handler for processing rightsholders tax information response.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/27/2018
 *
 * @author Aliaksandr Liakh
 */
public class OracleRhTaxInformationRestHandler extends CommonRestHandler<Map<Long, String>> {

    /**
     * Constructor.
     *
     * @param restTemplate instance of {@link RestTemplate}.
     */
    public OracleRhTaxInformationRestHandler(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    protected String getSystemName() {
        return "Oracle";
    }

    @Override
    protected Map<Long, String> doHandleResponse(JsonNode response) {
        Map<Long, String> result = Maps.newHashMap();
        response.forEach(node -> {
            Long accountNumber = JsonUtils.getLongValue(node.get("tboAccountNumber"));
            String countryCode = JsonUtils.getStringValue(node.get("country"));
            result.put(accountNumber, countryCode);
        });
        return result;
    }

    @Override
    protected Map<Long, String> getDefaultValue() {
        return Collections.emptyMap();
    }
}
