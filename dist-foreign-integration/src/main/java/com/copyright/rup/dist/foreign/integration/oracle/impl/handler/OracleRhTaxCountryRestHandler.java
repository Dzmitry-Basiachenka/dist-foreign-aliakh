package com.copyright.rup.dist.foreign.integration.oracle.impl.handler;

import com.copyright.rup.dist.common.integration.rest.CommonRestHandler;
import com.copyright.rup.dist.common.integration.util.JsonUtils;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/**
 * Handler for processing rightsholders tax country response.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/27/2018
 *
 * @author Aliaksandr Liakh
 */
public class OracleRhTaxCountryRestHandler extends CommonRestHandler<Boolean> {

    private static final String DOMESTIC_INDICATOR = "D";

    /**
     * Constructor.
     *
     * @param restTemplate instance of {@link RestTemplate}.
     */
    public OracleRhTaxCountryRestHandler(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    protected String getSystemName() {
        return "Oracle";
    }

    @Override
    protected Boolean doHandleResponse(JsonNode response) {
        Boolean result = Boolean.FALSE;
        if (Objects.nonNull(response) && response.elements().hasNext()) {
            JsonNode node = response.elements().next();
            result = DOMESTIC_INDICATOR.equals(JsonUtils.getStringValue(node.get("domesticInternationalIndicator")));
        }
        return result;
    }

    @Override
    protected Boolean getDefaultValue() {
        return Boolean.FALSE;
    }
}
