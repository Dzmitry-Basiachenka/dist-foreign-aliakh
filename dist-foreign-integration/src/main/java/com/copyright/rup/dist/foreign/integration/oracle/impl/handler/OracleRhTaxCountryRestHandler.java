package com.copyright.rup.dist.foreign.integration.oracle.impl.handler;

import com.copyright.rup.dist.common.integration.rest.CommonRestHandler;
import com.copyright.rup.dist.common.integration.util.JsonUtils;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Handler for processing rightsholders tax country response.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 05/04/2020
 *
 * @author Uladzislau Shalamitski
 */
public class OracleRhTaxCountryRestHandler extends CommonRestHandler<Map<Long, Boolean>> {

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
    protected Map<Long, Boolean> doHandleResponse(JsonNode response) {
        Map<Long, Boolean> resultMap = new HashMap<>();
        if (Objects.nonNull(response)) {
            response.elements().forEachRemaining(jsonNode -> {
                Long accountNumber = JsonUtils.getLongValue(jsonNode.get("rightsholderAccountNumber"));
                String taxCountryIndicator = JsonUtils.getStringValue(jsonNode.get("domesticInternationalIndicator"));
                resultMap.put(accountNumber, DOMESTIC_INDICATOR.equals(taxCountryIndicator));
            });
        }
        return resultMap;
    }

    @Override
    protected Map<Long, Boolean> getDefaultValue() {
        return new HashMap<>();
    }
}
