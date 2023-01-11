package com.copyright.rup.dist.foreign.integration.oracle.impl;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.IntegrationConnectionException;
import com.copyright.rup.dist.common.integration.rest.CommonRestHandler;
import com.copyright.rup.dist.foreign.domain.RhTaxInformation;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleRhTaxInformationService;
import com.copyright.rup.dist.foreign.integration.oracle.api.domain.OracleRhTaxInformationRequest;
import com.copyright.rup.dist.foreign.integration.oracle.impl.handler.OracleRhTaxInformationRestHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * Implementation of {@link IOracleRhTaxInformationService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 6/17/20
 *
 * @author Stanislau Rudak
 */
@Service("df.integration.oracleRhTaxInformationService")
public class OracleRhTaxInformationService implements IOracleRhTaxInformationService {

    private static final int BATCH_SIZE = 128;
    private static final Logger LOGGER = RupLogUtils.getLogger();

    private final ObjectMapper objectMapper;

    @Value("$RUP{dist.foreign.rest.oracle.rh_tax_info.url}")
    private String rhTaxInfoUrl;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Constructor.
     */
    public OracleRhTaxInformationService() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public Map<Long, RhTaxInformation> getRhTaxInformation(
        Collection<OracleRhTaxInformationRequest> oracleRhTaxInformationRequests) {
        checkArgument(CollectionUtils.isNotEmpty(oracleRhTaxInformationRequests));
        try {
            Map<Long, RhTaxInformation> rhTaxInfoResult =
                Maps.newHashMapWithExpectedSize(oracleRhTaxInformationRequests.size());
            CommonRestHandler<Map<Long, RhTaxInformation>> handler =
                new OracleRhTaxInformationRestHandler(restTemplate);
            for (Collection<OracleRhTaxInformationRequest> batchRequests
                : Iterables.partition(oracleRhTaxInformationRequests, BATCH_SIZE)) {
                HttpEntity<String> rhTaxInformationRequest = generateOracleRhTaxInformationRequest(batchRequests);
                rhTaxInfoResult.putAll(handler.handleResponse(rhTaxInfoUrl, rhTaxInformationRequest));
            }
            return rhTaxInfoResult;
        } catch (HttpClientErrorException | ResourceAccessException e) {
            LOGGER.error("Could not connect to the Oracle AP system", e);
            throw new IntegrationConnectionException("Could not connect to the Oracle AP system", e);
        } catch (IOException e) {
            LOGGER.error("Problem with processing (parsing, generating) JSON content", e);
        }
        return Map.of();
    }

    private static HttpHeaders getHttpHeaders() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charsets.UTF_8));
        return requestHeaders;
    }

    private HttpEntity<String> generateOracleRhTaxInformationRequest(
        Collection<OracleRhTaxInformationRequest> rhTaxInformationRequests) throws JsonProcessingException {
        HttpHeaders requestHeaders = getHttpHeaders();
        String rhTaxInformationRequest = objectMapper.writeValueAsString(rhTaxInformationRequests);
        LOGGER.debug("Rightsholder tax information request: {}", rhTaxInformationRequest);
        return new HttpEntity<>(rhTaxInformationRequest, requestHeaders);
    }
}
