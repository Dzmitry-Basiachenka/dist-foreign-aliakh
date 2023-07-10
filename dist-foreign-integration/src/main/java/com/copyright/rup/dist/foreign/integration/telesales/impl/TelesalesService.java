package com.copyright.rup.dist.foreign.integration.telesales.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.util.JsonUtils;
import com.copyright.rup.dist.foreign.domain.CompanyInformation;
import com.copyright.rup.dist.foreign.integration.telesales.api.ITelesalesService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import io.micrometer.core.annotation.Timed;

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
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class TelesalesService implements ITelesalesService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private RestTemplate restTemplate;
    @Value("$RUP{dist.foreign.rest.crm.sales_info.url}")
    private String salesInfoUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String getLicenseeName(Long licenseeAccountNumber) {
        String result;
        LOGGER.info("Get licensee information. Started. LicneseeAccountNumber={}",
            Objects.requireNonNull(licenseeAccountNumber));
        CompanyInformation companyInformation =
            handleResponse(salesInfoUrl, ImmutableMap.of("companyCode", licenseeAccountNumber));
        result = companyInformation.getName();
        LOGGER.info("Get licensee information. Finished. LicneseeAccountNumber={}, Result={}", licenseeAccountNumber,
            result);
        return result;
    }

    @Override
    public CompanyInformation getCompanyInformation(Long companyId) {
        LOGGER.info("Get company information. Started. CompanyId={}", Objects.requireNonNull(companyId));
        CompanyInformation companyInformation =
            handleResponse(salesInfoUrl, ImmutableMap.of("companyCode", companyId));
        LOGGER.info("Get company information. Finished. CompanyId={}, Result={}", companyId, companyInformation);
        return companyInformation;
    }

    private CompanyInformation handleResponse(final String url, final Map<String, Object> urlVariables) {
        LOGGER.debug("Handle company information response. Started. URL={}, UrlVariables={}", url, urlVariables);
        CompanyInformation result = null;
        try {
            String response = restTemplate.getForObject(url, String.class, urlVariables);
            if (StringUtils.isNotBlank(response)) {
                JsonNode responseJson =
                    Objects.requireNonNull(
                        objectMapper.readTree(response).get("com.copyright.svc.telesales.api.data.SalesData"),
                            "Missing response");
                Long companyId =
                    NumberUtils.createLong(Objects.toString(urlVariables.get("companyCode"), null));
                result = getCompanyInformation(companyId, responseJson);
            }
            LOGGER.debug("Handle company information response. Finished. URL={}, UrlVariables={}, Response={}",
                url, urlVariables, response);
        } catch (ResourceAccessException | HttpClientErrorException e) {
            LOGGER.warn("Telesales is not responding", e);
        } catch (IOException e) {
            LOGGER.warn("Company information REST call failed. URL={}, Reason={}", url, e);
        }
        return result;
    }

    private CompanyInformation getCompanyInformation(Long companyId, JsonNode response) {
        CompanyInformation companyInformation = null;
        if (null != response && response.isObject()) {
            companyInformation = buildCompanyInformation(companyId, response);
        }
        return companyInformation;
    }

    private CompanyInformation buildCompanyInformation(Long companyId, JsonNode companyInformationNode) {
        CompanyInformation companyInformation = new CompanyInformation();
        companyInformation.setId(companyId);
        companyInformation.setName(JsonUtils.getStringValue(companyInformationNode.get("companyName")));
        companyInformation.setDetailLicenseeClassId(
            JsonUtils.getIntegerValue(companyInformationNode.get("validatedICode")));
        return companyInformation;
    }
}
