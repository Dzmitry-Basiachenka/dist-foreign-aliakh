package com.copyright.rup.dist.foreign.integration.telesales.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.util.JsonUtils;
import com.copyright.rup.dist.foreign.integration.telesales.api.ITelesalesService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

/**
 * Implementation of {@link ITelesalesService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/27/2020
 *
 * @author Ihar Suvorau
 */
@Service("df.integration.telesalesService")
public class TelesalesService implements ITelesalesService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private RestTemplate restTemplate;
    @Value("$RUP{dist.foreign.rest.crm.sales_info.url}")
    private String salesInfoUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String getLicenseeName(Long licenseeAccountNumber) {
        String result = null;
        Map<String, Long> urlVariables = ImmutableMap.of("licenseeAccountNumber", licenseeAccountNumber);
        LOGGER.debug("Handle company information response. Started. URL={}, UrlVariables={}", salesInfoUrl,
            urlVariables);
        try {
            String response = restTemplate.getForObject(salesInfoUrl, String.class, urlVariables);
            if (StringUtils.isNotBlank(response)) {
                JsonNode responseJson =
                    objectMapper.readTree(response).get("com.copyright.svc.telesales.api.data.SalesData");
                result = JsonUtils.getStringValue(responseJson.get("companyName"));
            }
            LOGGER.debug("Handle company information response. Finished. URL={}, UrlVariables={}, Response={}",
                salesInfoUrl, urlVariables, response);
        } catch (ResourceAccessException | HttpClientErrorException e) {
            LOGGER.warn("Telesales is not responding", e);
        } catch (IOException e) {
            LOGGER.warn("Company information REST call failed. URL={}, Reason={}", salesInfoUrl, e);
        }
        return result;
    }
}
