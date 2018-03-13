package com.copyright.rup.dist.foreign.integration.rms.impl;

import com.copyright.rup.dist.common.integration.rest.CommonRestHandler;
import com.copyright.rup.dist.common.integration.util.JsonUtils;
import com.copyright.rup.dist.foreign.integration.rms.api.IRmsRightsAssignmentService;
import com.copyright.rup.dist.foreign.integration.rms.api.RightsAssignmentRequest;
import com.copyright.rup.dist.foreign.integration.rms.api.RightsAssignmentResult;
import com.copyright.rup.dist.foreign.integration.rms.api.RightsAssignmentResult.RightsAssignmentResultStatusEnum;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;

import javax.annotation.PostConstruct;

/**
 * Implementation of {@link IRmsRightsAssignmentService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/24/18
 *
 * @author Darya Baraukova
 */
@Service("df.integration.rmsRightsAssignmentService")
class RmsRightsAssignmentService implements IRmsRightsAssignmentService {

    private static final String SEND_FOR_RA_INVALID_RESPONSE_MESSAGE_FORMAT =
        "Works count doesn't match or job id is blank. RequestWorksCount=%s, ResponseWorksCount=%s, JobId=%s";

    @Autowired
    private RestTemplate restTemplate;

    @Value("$RUP{dist.foreign.integration.rest.rms.url}")
    private String baseUrl;
    private String rightsAssignmentUrl;
    private ObjectMapper objectMapper;

    @Override
    public RightsAssignmentResult sendForRightsAssignment(Set<Long> wrWrkInsts) {
        try {
            return doRightsAssignmentRequest(Objects.requireNonNull(wrWrkInsts));
        } catch (IOException e) {
            RightsAssignmentResult result = new RightsAssignmentResult(RightsAssignmentResultStatusEnum.RA_ERROR);
            result.setErrorMessage("Problem with processing (parsing, generating) JSON content. Exception=" + e);
            return result;
        }
    }

    /**
     * Initializes URLs.
     */
    @PostConstruct
    void initializeUrls() {
        objectMapper = new ObjectMapper();
        rightsAssignmentUrl = UriComponentsBuilder.fromHttpUrl(baseUrl)
            .pathSegment("jobs/wrwrkinst").build().toUriString();
    }

    private RightsAssignmentResult doRightsAssignmentRequest(Set<Long> wrWrkInsts) throws IOException {
        HttpEntity<String> request =
            new HttpEntity<>(objectMapper.writeValueAsString(new RightsAssignmentRequest(wrWrkInsts)),
                buildRequestHeader());
        RmsRightsAssignmentHandler rightsAssignmentHandler =
            new RmsRightsAssignmentHandler(restTemplate, wrWrkInsts.size());
        return rightsAssignmentHandler.handleResponse(rightsAssignmentUrl, request);
    }

    private HttpHeaders buildRequestHeader() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charsets.UTF_8));
        return requestHeaders;
    }

    private static class RmsRightsAssignmentHandler extends CommonRestHandler<RightsAssignmentResult> {

        private final int wrWrkInstsCount;

        RmsRightsAssignmentHandler(RestTemplate restTemplate, int wrWrkInstsCount) {
            super(restTemplate);
            this.wrWrkInstsCount = wrWrkInstsCount;
        }

        @Override
        protected RightsAssignmentResult doHandleResponse(JsonNode response) {
            return handleRightsAssignmentResponse(response);
        }

        @Override
        protected String getSystemName() {
            return "RMS";
        }

        @Override
        protected RightsAssignmentResult getDefaultValue() {
            return new RightsAssignmentResult(RightsAssignmentResultStatusEnum.RA_ERROR);
        }

        private RightsAssignmentResult handleRightsAssignmentResponse(JsonNode response) {
            RightsAssignmentResult result =
                new RightsAssignmentResult(RightsAssignmentResultStatusEnum.SUCCESS);
            int totalWorks = JsonUtils.getIntegerValue(response.get("totalWorks"));
            String jobId = JsonUtils.getStringValue(response.get("jobId"));
            if (wrWrkInstsCount == totalWorks && StringUtils.isNotBlank(jobId)) {
                result.setJobId(jobId);
            } else {
                result.setStatus(RightsAssignmentResultStatusEnum.RA_ERROR);
                result.setErrorMessage(
                    String.format(SEND_FOR_RA_INVALID_RESPONSE_MESSAGE_FORMAT, wrWrkInstsCount, totalWorks, jobId));
            }
            return result;
        }
    }
}
