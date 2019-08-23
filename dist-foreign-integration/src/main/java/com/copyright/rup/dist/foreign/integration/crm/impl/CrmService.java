package com.copyright.rup.dist.foreign.integration.crm.impl;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.IntegrationConnectionException;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.integration.crm.api.GetRightsDistributionResponse;
import com.copyright.rup.dist.foreign.integration.crm.api.ICrmService;
import com.copyright.rup.dist.foreign.integration.crm.api.InsertRightsDistributionRequest;
import com.copyright.rup.dist.foreign.integration.crm.api.InsertRightsDistributionRequestWrapper;
import com.copyright.rup.dist.foreign.integration.crm.api.InsertRightsDistributionResponse;
import com.copyright.rup.dist.foreign.integration.crm.api.InsertRightsDistributionResponseStatusEnum;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;

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

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GetRightsDistributionResponseHandler getRightsDistributionResponseHandler;

    @Autowired
    private InsertRightsDistributionResponseHandler insertRightsDistributionResponseHandler;

    @Value("$RUP{dist.foreign.rest.crm.get_rights_distribution.url}")
    private String getRightsDistributionRequestsUrl;

    @Value("$RUP{dist.foreign.rest.crm.insert_rights_distribution.url}")
    private String insertRightsDistributionRequestsUrl;

    /**
     * Constructor.
     */
    public CrmService() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public List<GetRightsDistributionResponse> getRightsDistribution(Set<String> cccEventIds) {
        try {
            ImmutableMap<String, String> urlVariables = ImmutableMap.of("cccEventIds",
                String.join(",", Objects.requireNonNull(cccEventIds)));
            String json = restTemplate.getForObject(getRightsDistributionRequestsUrl, String.class, urlVariables);
            return getRightsDistributionResponseHandler.parseJson(json);
        } catch (HttpClientErrorException | ResourceAccessException e) {
            throw new IntegrationConnectionException("Could not connect to the CRM system", e);
        } catch (IOException e) {
            throw new RupRuntimeException("Problem with processing (parsing, generating) JSON content", e);
        }
    }

    @Override
    public InsertRightsDistributionResponse insertRightsDistribution(List<InsertRightsDistributionRequest> requests) {
        try {
            InsertRightsDistributionResponse response =
                new InsertRightsDistributionResponse(InsertRightsDistributionResponseStatusEnum.SUCCESS);
            LOGGER.info("Send 'insert rights distribution' requests. Started. RequestsCount={}",
                LogUtils.size(requests));
            for (List<InsertRightsDistributionRequest> batchRequests :
                Iterables.partition(Objects.requireNonNull(requests), 128)) {
                HttpEntity<String> httpEntity = buildHttpEntity(batchRequests);
                LOGGER.trace("Send 'insert rights distribution' request. Request={}", httpEntity);
                String json = restTemplate.postForObject(insertRightsDistributionRequestsUrl, httpEntity, String.class);
                response = insertRightsDistributionResponseHandler.parseJson(json,
                    batchRequests.stream().collect(Collectors.toMap(
                        InsertRightsDistributionRequest::getOmOrderDetailNumber,
                        InsertRightsDistributionRequest::toString)));
            }
            return response;
        } catch (HttpClientErrorException | ResourceAccessException e) {
            throw new IntegrationConnectionException("Could not connect to the CRM system", e);
        } catch (IOException e) {
            throw new RupRuntimeException("Problem with processing (parsing, generating) JSON content", e);
        }
    }

    private HttpEntity<String> buildHttpEntity(List<InsertRightsDistributionRequest> requests)
        throws JsonProcessingException {
        return new HttpEntity<>(objectMapper.writeValueAsString(new InsertRightsDistributionRequestWrapper(requests)),
            buildRequestHeaders());
    }

    private HttpHeaders buildRequestHeaders() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charsets.UTF_8));
        return requestHeaders;
    }
}
