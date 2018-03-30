package com.copyright.rup.dist.foreign.integration.crm.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.IntegrationConnectionException;
import com.copyright.rup.dist.common.integration.util.JsonUtils;
import com.copyright.rup.dist.foreign.integration.crm.api.CrmResult;
import com.copyright.rup.dist.foreign.integration.crm.api.CrmResultStatusEnum;
import com.copyright.rup.dist.foreign.integration.crm.api.CrmRightsDistributionRequest;
import com.copyright.rup.dist.foreign.integration.crm.api.CrmRightsDistributionRequestWrapper;
import com.copyright.rup.dist.foreign.integration.crm.api.ICrmService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.base.Charsets;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;

/**
 * Implementation of {@link ICrmService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/29/18
 *
 * @author Darya Baraukova
 */
public class CrmService implements ICrmService {

    private static final Logger LOGGER = RupLogUtils.getLogger();
    private ObjectMapper objectMapper;
    private String crmRightsDistributionRequestsUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Value("$RUP{dist.foreign.integration.rest.crm.url}")
    private String baseUrl;

    @Override
    public CrmResult sendRightsDistributionRequests(List<CrmRightsDistributionRequest> crmRightsDistributionRequests) {
        try {
            return doSendRightsDistributionRequests(Objects.requireNonNull(crmRightsDistributionRequests));
        } catch (HttpClientErrorException | ResourceAccessException e) {
            throw new IntegrationConnectionException("Could not connect to the CRM system", e);
        } catch (IOException e) {
            LOGGER.error("Problem with processing (parsing, generating) JSON content", e);
        }
        return null;
    }

    /**
     * Initializes urls.
     */
    @PostConstruct
    void initializeUrls() {
        objectMapper = new ObjectMapper();
        crmRightsDistributionRequestsUrl = UriComponentsBuilder.fromHttpUrl(baseUrl)
            .pathSegment("insertCCCRightsDistribution")
            .build().toUriString();
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Internal method to send list of {@link CrmRightsDistributionRequest}s to CRM service.
     *
     * @param requests list of {@link CrmRightsDistributionRequest}s
     * @return {@link CrmResult} instance
     * @throws IOException when {@link CrmRightsDistributionRequest} cannot be converted to JSON or request cannot be
     *                     sent to CRM service.
     */
    CrmResult doSendRightsDistributionRequests(List<CrmRightsDistributionRequest> requests) throws IOException {
        CrmResult result = new CrmResult(CrmResultStatusEnum.SUCCESS);
        LOGGER.info("Send liability details to CRM. Started. RequestsCount={}", requests.size());
        for (List<CrmRightsDistributionRequest> batchRequests : Iterables.partition(requests, 128)) {
            HttpEntity<String> crmRequest = new HttpEntity<>(objectMapper.writeValueAsString(
                new CrmRightsDistributionRequestWrapper(batchRequests)), buildRequestHeader());
            LOGGER.debug("RightsDistributionRequest={}", crmRequest);
            result = parseResponse(
                restTemplate.postForObject(crmRightsDistributionRequestsUrl, crmRequest, String.class), result,
                buildRightsDistributionRequestMap(batchRequests));
        }
        return result;
    }

    /**
     * Parses response from CRM service.
     *
     * @param response   the response of the request
     * @param crmResult  the instance or CRM result
     * @param requestMap detail id to request map
     * @return the {@link CrmResult} instances
     * @throws IOException if response cannot be parsed
     */
    CrmResult parseResponse(String response, CrmResult crmResult, Map<Long, String> requestMap)
        throws IOException {
        JsonNode jsonNode = JsonUtils.readJsonTree(objectMapper, response);
        JsonNode list = jsonNode.get("list");
        if (null != list) {
            List<JsonNode> errorNodes = list.findValues("error");
            if (CollectionUtils.isNotEmpty(errorNodes)) {
                crmResult.setCrmResultStatus(CrmResultStatusEnum.CRM_ERROR);
                for (JsonNode errorNode : errorNodes) {
                    JsonNode key = errorNode.findValue("key");
                    List<JsonNode> errorMessages = errorNode.findValues("string");
                    crmResult.addInvalidDetailId(key.asText());
                    LOGGER.warn("Send liability details to CRM. Failed. Key={}, Request={}, ErrorMessage={}", key,
                        requestMap.get(key.asLong()), errorMessages);
                }
            } else {
                LOGGER.debug("CRMResponse={}", crmResult);
            }
        } else {
            LOGGER.warn("Send liability details to CRM. Failed. Couldn't parse. Response={}, JsonNode={}", response,
                jsonNode);
        }
        return crmResult;
    }

    private Map<Long, String> buildRightsDistributionRequestMap(List<CrmRightsDistributionRequest> requests) {
        Map<Long, String> map = Maps.newHashMapWithExpectedSize(requests.size());
        requests.forEach(request -> map.put(request.getOmOrderDetailNumber(), request.toString()));
        return map;
    }

    private HttpHeaders buildRequestHeader() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charsets.UTF_8));
        return requestHeaders;
    }
}
