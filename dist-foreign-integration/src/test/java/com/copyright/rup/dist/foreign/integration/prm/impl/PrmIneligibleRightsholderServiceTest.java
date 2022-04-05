package com.copyright.rup.dist.foreign.integration.prm.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.AclIneligibleRightsholder;

import com.google.common.collect.ImmutableMap;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Verifies {@link PrmIneligibleRightsholderService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/04/2022
 *
 * @author Anton Azarenka
 */
public class PrmIneligibleRightsholderServiceTest {

    private static final String PRINT_TYPE_OF_USE = "PRINT";
    private static final String DIGITAL_TYPE_OF_USE = "DIGITAL";
    private static final String RIGHTSHOLDER_PARENTS_URL =
        "http://localhost:8080/party-rest/orgPreference/allpref?preferenceCode=INELIGIBLEFORSHARES";
    private static final String INELIGIBLE_RIGHTSHOLDERS_URL =
        "http://localhost:8080/party-rest/orgRelationship/drilldownv2?orgIds={rightsholdersIds}&" +
            "relationshipCode=PARENT&productId={productId}";
    private static final String RIGHTSHOLDER_PARENTS_JSON = "rightsholder_parents.json";
    private static final String EMPTY_RESPONSE_JSON = "empty_response.json";
    private static final String ACLPRINT = "ACLPRINT";
    private static final String ACLDIGITAL = "ACLDIGITAL";
    private static final String PRINT_PARENT_RIGHTSHOLDERS_IDS =
        "d145f685-994e-4b47-8748-c1ad375da3f9,cf8125d9-d846-4451-a276-57719a17513f";
    private static final String DIGITAL_PARENT_RIGHTSHOLDER_ID = "63319ddb-4a9d-4e86-aa88-1f046cd80ddf";
    private static final LocalDate PERIOD_END_DATE = LocalDate.of(2016, 4, 29);

    private PrmIneligibleRightsholderService ineligibleRightsholderService;
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = createMock(RestTemplate.class);
        ineligibleRightsholderService = new PrmIneligibleRightsholderService();
        Whitebox.setInternalState(ineligibleRightsholderService, restTemplate);
        Whitebox.setInternalState(ineligibleRightsholderService, "rightsholdersParentUrl", RIGHTSHOLDER_PARENTS_URL);
        Whitebox.setInternalState(ineligibleRightsholderService, "ineligibleRightsholdersUrl",
            INELIGIBLE_RIGHTSHOLDERS_URL);
    }

    @Test
    public void testGetIneligibleRightsHolders() {
        restTemplate.setErrorHandler(anyObject(DefaultResponseErrorHandler.class));
        expectLastCall().times(2);
        expect(restTemplate.getForObject(RIGHTSHOLDER_PARENTS_URL, String.class))
            .andReturn(
                TestUtils.fileToString(PrmIneligibleRightsholderServiceTest.class, RIGHTSHOLDER_PARENTS_JSON))
            .once();
        expect(restTemplate.getForObject(INELIGIBLE_RIGHTSHOLDERS_URL, String.class,
            buildParamsMap(ACLPRINT, PRINT_PARENT_RIGHTSHOLDERS_IDS)))
            .andReturn(TestUtils.fileToString(PrmIneligibleRightsholderServiceTest.class,
                "ineligible_rightsholder_print.json")).once();
        replay(restTemplate);
        Set<AclIneligibleRightsholder> actualRightsholders =
            ineligibleRightsholderService.getIneligibleRightsholders(PERIOD_END_DATE, "MACL");
        assertFalse(actualRightsholders.isEmpty());
        assertEquals(3, actualRightsholders.size());
        verifyRightsholders(actualRightsholders);
        verify(restTemplate);
    }

    @Test
    public void testGetIneligibleRightsholdersEmptyIneligibleRightsholders() {
        restTemplate.setErrorHandler(anyObject(DefaultResponseErrorHandler.class));
        expectLastCall().times(2);
        expect(restTemplate.getForObject(RIGHTSHOLDER_PARENTS_URL, String.class))
            .andReturn(TestUtils.fileToString(PrmIneligibleRightsholderServiceTest.class, RIGHTSHOLDER_PARENTS_JSON))
            .once();
        expect(restTemplate.getForObject(INELIGIBLE_RIGHTSHOLDERS_URL, String.class,
            buildParamsMap(ACLDIGITAL, DIGITAL_PARENT_RIGHTSHOLDER_ID)))
            .andReturn(TestUtils.fileToString(PrmIneligibleRightsholderServiceTest.class, EMPTY_RESPONSE_JSON)).once();
        replay(restTemplate);
        List<AclIneligibleRightsholder> aclIneligibleRightsholders =
            new ArrayList<>(ineligibleRightsholderService.getIneligibleRightsholders(PERIOD_END_DATE, "JACDCL"));
        assertEquals(1, aclIneligibleRightsholders.size());
        verifyRightsholder(aclIneligibleRightsholders.get(0), 2000017000L, DIGITAL_TYPE_OF_USE);
        verify(restTemplate);
    }

    @Test
    public void testGetIneligibleRightsholdersEmptyParents() {
        restTemplate.setErrorHandler(anyObject(DefaultResponseErrorHandler.class));
        expectLastCall().times(2);
        expect(restTemplate.getForObject(RIGHTSHOLDER_PARENTS_URL, String.class))
            .andReturn(TestUtils.fileToString(PrmIneligibleRightsholderServiceTest.class, EMPTY_RESPONSE_JSON)).once();
        replay(restTemplate);
        assertTrue(ineligibleRightsholderService.getIneligibleRightsholders(PERIOD_END_DATE, "MACL").isEmpty());
        verify(restTemplate);
    }

    private void verifyRightsholders(Set<AclIneligibleRightsholder> actualRightsholders) {
        List<AclIneligibleRightsholder> aclIneligibleRightsholders = new ArrayList<>(actualRightsholders);
        verifyRightsholder(aclIneligibleRightsholders.get(0), 7001107060L, PRINT_TYPE_OF_USE);
        verifyRightsholder(aclIneligibleRightsholders.get(1), 2000017000L, PRINT_TYPE_OF_USE);
        verifyRightsholder(aclIneligibleRightsholders.get(0), 7001107060L, PRINT_TYPE_OF_USE);
    }

    private void verifyRightsholder(AclIneligibleRightsholder rightsholder, Long rhAccountNumber,
                                    String typeOfUse) {
        assertNotNull(rightsholder);
        assertEquals(rhAccountNumber, rightsholder.getRhAccountNumber());
        assertEquals(typeOfUse, rightsholder.getTypeOfUse());
    }

    private Map<String, ? extends Serializable> buildParamsMap(String productId, String parentId) {
        return ImmutableMap.of("productId", productId, "rightsholdersIds", parentId);
    }
}
