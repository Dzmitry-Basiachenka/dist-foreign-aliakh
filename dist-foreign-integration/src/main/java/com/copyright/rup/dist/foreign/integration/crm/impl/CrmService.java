package com.copyright.rup.dist.foreign.integration.crm.impl;

import com.copyright.rup.common.exception.RupRuntimeException;
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

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
@Service
public class CrmService implements ICrmService {

    private static final Logger LOGGER = RupLogUtils.getLogger();
    private ObjectMapper objectMapper;
    private String crmRightsDistributionRequestsUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Value("$RUP{dist.foreign.rest.crm.url}")
    private String baseUrl;

    @Override
    public CrmResult sendRightsDistributionRequests(List<CrmRightsDistributionRequest> crmRightsDistributionRequests) {
        try {
            return doSendRightsDistributionRequests(Objects.requireNonNull(crmRightsDistributionRequests));
        } catch (HttpClientErrorException | ResourceAccessException e) {
            throw new IntegrationConnectionException("Could not connect to the CRM system", e);
        } catch (IOException e) {
            throw new RupRuntimeException("Problem with processing (parsing, generating) JSON content", e);
        }
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
        LOGGER.info("Send rights distribution requests. Started. RequestsCount={}", requests.size());
        for (List<CrmRightsDistributionRequest> batchRequests : Iterables.partition(requests, 128)) {
            HttpEntity<String> crmRequest = new HttpEntity<>(objectMapper.writeValueAsString(
                new CrmRightsDistributionRequestWrapper(batchRequests)), buildRequestHeader());
            LOGGER.trace("Send rights distribution request. Request={}", crmRequest);
            result = parseResponse(
                restTemplate.postForObject(crmRightsDistributionRequestsUrl, crmRequest, String.class),
                batchRequests.stream().collect(Collectors.toMap(
                    CrmRightsDistributionRequest::getOmOrderDetailNumber,
                    CrmRightsDistributionRequest::toString)));
        }
        return result;
    }

    /**
     * Parses response from CRM service.
     *
     * @param response   the response of the request
     * @param requestMap detail id to request map
     * @return the {@link CrmResult} instances
     * @throws IOException if response cannot be parsed
     */
    CrmResult parseResponse(String response, Map<String, String> requestMap)
        throws IOException {
        LOGGER.trace("Parse rights distribution response. Response={}", response);
        JsonNode jsonNode = JsonUtils.readJsonTree(objectMapper, response);
        JsonNode list = jsonNode.get("list");
        CrmResult crmResult = new CrmResult(CrmResultStatusEnum.SUCCESS);
        if (Objects.nonNull(list)) {
            JsonNode errorNode = list.findValue("error");
            if (Objects.nonNull(errorNode)) {
                crmResult.setCrmResultStatus(CrmResultStatusEnum.CRM_ERROR);
                List<JsonNode> errorMessageNodes = new ArrayList<>();
                if (errorNode.isArray()) {
                    errorNode.forEach(errorMessageNodes::add);
                } else {
                    errorMessageNodes.add(errorNode);
                }
                if (CollectionUtils.isNotEmpty(errorMessageNodes)) {
                    for (JsonNode errorMessageNode : errorMessageNodes) {
                        JsonNode key = errorMessageNode.findValue("key");
                        List<JsonNode> errorMessages = errorMessageNode.findValues("string");
                        crmResult.addInvalidUsageId(key.asText());
                        LOGGER.warn("Send usages to CRM. Failed. DetailId={}, Request={}, ErrorMessage={}", key,
                            requestMap.get(key.asText()), errorMessages);
                    }
                }
            }
        } else {
            throw new IOException(
                String.format("Send usages to CRM. Failed. Reason=Couldn't parse response. Response=%s, JsonNode=%s",
                    response, jsonNode));
        }
        return crmResult;
    }

    private HttpHeaders buildRequestHeader() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charsets.UTF_8));
        return requestHeaders;
    }
}
