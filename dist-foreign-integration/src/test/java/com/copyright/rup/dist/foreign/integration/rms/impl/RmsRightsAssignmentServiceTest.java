package com.copyright.rup.dist.foreign.integration.rms.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.integration.rms.api.RightsAssignmentResult;
import com.copyright.rup.dist.foreign.integration.rms.api.RightsAssignmentResult.RightsAssignmentResultStatusEnum;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Charsets;
import com.google.common.collect.Sets;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Verifies {@link RmsRightsAssignmentService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/24/18
 *
 * @author Darya Baraukova
 */
public class RmsRightsAssignmentServiceTest {

    private static final String BASE_URL = "http://localhost:9051/rms-rights-rest/";
    private static final String RIGHTS_ASSIGNMENT_URL = BASE_URL + "jobs/wrwrkinst";
    private RestTemplate restTemplate;
    private RmsRightsAssignmentService rightsAssignmentService;

    @Before
    public void setUp() {
        restTemplate = createMock(RestTemplate.class);
        rightsAssignmentService = new RmsRightsAssignmentService();
        Whitebox.setInternalState(rightsAssignmentService, RestTemplate.class, restTemplate);
        Whitebox.setInternalState(rightsAssignmentService, "baseUrl", BASE_URL);
        rightsAssignmentService.initializeUrls();
    }

    @Test
    public void testSendForRightsAssignment() throws IOException {
        expect(restTemplate.postForObject(RIGHTS_ASSIGNMENT_URL, createHttpRightsAssigmentRequest(), String.class))
            .andReturn(TestUtils.fileToString(RmsRightsAssignmentServiceTest.class,
                "rms_rights_assignment_response.json")).once();
        replay(restTemplate);
        assertEquals(buildRightsAssignmentResult(RightsAssignmentResultStatusEnum.SUCCESS,
            "2aa2c93d-6819-446a-a97f-49c32da792b8"),
            rightsAssignmentService.sendForRightsAssignment(Sets.newHashSet(162205190L, 123160519L)));
        verify(restTemplate);
    }

    @Test
    public void testSendForRightsAssignmentWorksCountMismatch() throws IOException {
        expect(restTemplate.postForObject(RIGHTS_ASSIGNMENT_URL, createHttpRightsAssigmentRequest(), String.class))
            .andReturn(TestUtils.fileToString(RmsRightsAssignmentServiceTest.class,
                "rms_rights_assignment_response_works_count_mismatch.json")).once();
        replay(restTemplate);
        assertEquals(buildRightsAssignmentResult(RightsAssignmentResultStatusEnum.RA_ERROR, null),
            rightsAssignmentService.sendForRightsAssignment(Sets.newHashSet(162205190L, 123160519L)));
        verify(restTemplate);
    }

    @Test
    public void testSendForRightsAssignmentNoJobId() throws IOException {
        expect(restTemplate.postForObject(RIGHTS_ASSIGNMENT_URL, createHttpRightsAssigmentRequest(), String.class))
            .andReturn(TestUtils.fileToString(RmsRightsAssignmentServiceTest.class,
                "rms_rights_assignment_response_no_job_id.json")).once();
        replay(restTemplate);
        assertEquals(buildRightsAssignmentResult(RightsAssignmentResultStatusEnum.RA_ERROR, null),
            rightsAssignmentService.sendForRightsAssignment(Sets.newHashSet(162205190L, 123160519L)));
        verify(restTemplate);
    }

    private HttpEntity<String> createHttpRightsAssigmentRequest()
        throws JsonProcessingException {
        return new HttpEntity<>(StringUtils.trim(
            TestUtils.fileToString(RmsRightsAssignmentServiceTest.class, "rms_rights_assignment_request.json")),
            buildRequestHeader());
    }

    private HttpHeaders buildRequestHeader() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charsets.UTF_8));
        return requestHeaders;
    }

    private RightsAssignmentResult buildRightsAssignmentResult(RightsAssignmentResultStatusEnum status, String jobId) {
        RightsAssignmentResult result = new RightsAssignmentResult(status);
        result.setJobId(jobId);
        return result;
    }
}
