package com.copyright.rup.dist.foreign.integration.oracle.impl;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.common.integration.IntegrationConnectionException;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleService;
import com.copyright.rup.dist.foreign.integration.oracle.impl.handler.OracleRhTaxInformationRequest;
import com.copyright.rup.dist.foreign.integration.oracle.impl.handler.OracleRhTaxInformationRestHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.collect.Iterables;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

/**
 * Implementation of {@link IOracleService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/27/2018
 *
 * @author Aliaksandr Liakh
 */
@Service("df.integration.oracleService")
public class OracleService implements IOracleService {

    private static final int BATCH_SIZE = 128;
    private static final String CONNECTION_EXCEPTION_MESSAGE = "Could not connect to the Oracle AP";
    private static final String JSON_PROCESSING_EXCEPTION_MESSAGE = "Exception during JSON request processing";

    @Autowired
    private RestTemplate restTemplate;

    @Value("$RUP{dist.foreign.rest.oracle.rh_tax.url}")
    private String rhTaxUrl;

    private ObjectMapper objectMapper;

    @Override
    public Map<Long, String> getAccountNumbersToCountryCodesMap(List<Long> accountNumbers) {
        try {
            Map<Long, String> result = new HashMap<>(accountNumbers.size());
            OracleRhTaxInformationRestHandler handler = new OracleRhTaxInformationRestHandler(restTemplate);
            for (Collection<Long> partition : Iterables.partition(accountNumbers, BATCH_SIZE)) {
                result.putAll(handler.handleResponse(rhTaxUrl, buildHttpEntity(partition)));
            }
            return result;
        } catch (HttpClientErrorException | ResourceAccessException e) {
            throw new IntegrationConnectionException(CONNECTION_EXCEPTION_MESSAGE, e);
        } catch (JsonProcessingException e) {
            throw new RupRuntimeException(JSON_PROCESSING_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Sets up the service.
     */
    @PostConstruct
    void init() {
        this.objectMapper = new ObjectMapper();
    }

    private HttpEntity<String> buildHttpEntity(Collection<Long> accountNumbers) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charsets.UTF_8));
        return new HttpEntity<>(objectMapper.writeValueAsString(
            accountNumbers
                .stream()
                .map(OracleRhTaxInformationRequest::new)
                .collect(Collectors.toList())),
            headers);
    }
}
