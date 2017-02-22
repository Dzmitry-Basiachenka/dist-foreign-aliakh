package com.copyright.rup.dist.foreign.integration.prm.impl;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.integration.IntegrationConnectionException;
import com.copyright.rup.dist.common.test.TestUtils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;

/**
 * Verifies {@link PrmRightsholderService}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/21/2017
 *
 * @author Mikalai Bezmen
 */
public class PrmRightsholderServiceTest {

    private static final String BASE_URL = "http://localhost:8080/rest-api";
    private static final String RIGHTSHOLDERS_URL
        = BASE_URL + "/organization/extorgkeys?extOrgKeys[]={accountNumbers}&fmt=json";
    private static final Long RIGHTSHOLDER_ACCOUNT_NUMBER = 1L;
    private static final String RIGHTSHOLDER_UID = "RightsholderUID";
    private static final String RIGHTSHOLDER_NAME = "RLNK";
    private static final String RIGHTSHOLDER_COLLECTION_JSON = "rightsholders_collection.json";
    private static final String NOT_FOUND_JSON = "not_found.json";
    private static final String RESPONSE_NOT_ARRAY = "response_not_array.json";
    private static final String EXCEPTION_MESSAGE = "exception message";
    private static final String COMMA_SEPARATOR = ",";
    private PrmRightsholderService prmRightsholderService;
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = createStrictMock(RestTemplate.class);
        prmRightsholderService = new PrmRightsholderService();
        prmRightsholderService.setRestTemplate(restTemplate);
        prmRightsholderService.setBaseUrl(BASE_URL);
        prmRightsholderService.initializeUrls();
    }

    @Test
    public void testGetRightsholders() {
        expect(restTemplate.getForObject(RIGHTSHOLDERS_URL, String.class,
            ImmutableMap.of(PrmRightsholderService.URL_VARIABLE_RIGHTSHOLDER_ACCOUNT_NUMBERS,
                StringUtils.join(Lists.newArrayList(RIGHTSHOLDER_ACCOUNT_NUMBER), COMMA_SEPARATOR))))
            .andReturn(TestUtils.fileToString(PrmRightsholderServiceTest.class, RIGHTSHOLDER_COLLECTION_JSON)).once();
        replay(restTemplate);
        List<Rightsholder> rightsholders = Lists.newArrayList(
            prmRightsholderService.getRightsholders(Sets.newHashSet(RIGHTSHOLDER_ACCOUNT_NUMBER)));
        verify(restTemplate);
        assertNotNull(rightsholders);
        assertEquals(1, rightsholders.size());
        Rightsholder rightsholder = rightsholders.get(0);
        verifyRightsholder(rightsholder);
    }

    @Test
    public void testGetNotExistedRightsholders() {
        expect(restTemplate.getForObject(RIGHTSHOLDERS_URL, String.class,
            ImmutableMap.of(PrmRightsholderService.URL_VARIABLE_RIGHTSHOLDER_ACCOUNT_NUMBERS,
                StringUtils.join(Lists.newArrayList(RIGHTSHOLDER_ACCOUNT_NUMBER), COMMA_SEPARATOR))))
            .andReturn(TestUtils.fileToString(PrmRightsholderServiceTest.class, NOT_FOUND_JSON)).once();
        replay(restTemplate);
        Collection<Rightsholder> rightsholders =
            prmRightsholderService.getRightsholders(Sets.newHashSet(RIGHTSHOLDER_ACCOUNT_NUMBER));
        verify(restTemplate);
        assertNotNull(rightsholders);
        assertTrue(rightsholders.isEmpty());
    }

    @Test(expected = IntegrationConnectionException.class)
    public void testGetRightsholdersResourceAccessException() {
        expect(restTemplate.getForObject(RIGHTSHOLDERS_URL, String.class,
            ImmutableMap.of(PrmRightsholderService.URL_VARIABLE_RIGHTSHOLDER_ACCOUNT_NUMBERS,
                StringUtils.join(Lists.newArrayList(RIGHTSHOLDER_ACCOUNT_NUMBER), COMMA_SEPARATOR))))
            .andThrow(new ResourceAccessException(EXCEPTION_MESSAGE)).once();
        replay(restTemplate);
        prmRightsholderService.getRightsholders(Sets.newHashSet(RIGHTSHOLDER_ACCOUNT_NUMBER));
        verify(restTemplate);
    }

    @Test
    public void testGetRightsholdersResponseNotArray() {
        expect(restTemplate.getForObject(RIGHTSHOLDERS_URL, String.class,
            ImmutableMap.of(PrmRightsholderService.URL_VARIABLE_RIGHTSHOLDER_ACCOUNT_NUMBERS,
                StringUtils.join(Lists.newArrayList(RIGHTSHOLDER_ACCOUNT_NUMBER), COMMA_SEPARATOR))))
            .andReturn(TestUtils.fileToString(PrmRightsholderServiceTest.class, RESPONSE_NOT_ARRAY)).once();
        replay(restTemplate);
        Collection<Rightsholder> rightsholders =
            prmRightsholderService.getRightsholders(Sets.newHashSet(RIGHTSHOLDER_ACCOUNT_NUMBER));
        assertNotNull(rightsholders);
        assertTrue(rightsholders.isEmpty());
        verify(restTemplate);
    }

    private void verifyRightsholder(Rightsholder rightsholder) {
        assertNotNull(rightsholder);
        assertEquals(RIGHTSHOLDER_NAME, rightsholder.getName());
        assertEquals(RIGHTSHOLDER_UID, rightsholder.getId());
        assertEquals(RIGHTSHOLDER_ACCOUNT_NUMBER, rightsholder.getAccountNumber());
    }
}
