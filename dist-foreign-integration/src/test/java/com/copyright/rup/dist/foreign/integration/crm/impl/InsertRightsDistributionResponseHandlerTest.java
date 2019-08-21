package com.copyright.rup.dist.foreign.integration.crm.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.integration.crm.api.InsertRightsDistributionResponse;
import com.copyright.rup.dist.foreign.integration.crm.api.InsertRightsDistributionResponseStatusEnum;

import com.google.common.collect.ImmutableSet;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

/**
 * Verifies {@link InsertRightsDistributionResponseHandler}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 08/20/2019
 *
 * @author Aliaksandr Liakh
 */
public class InsertRightsDistributionResponseHandlerTest {

    private final InsertRightsDistributionResponseHandler handler = new InsertRightsDistributionResponseHandler();

    @Test
    public void testParseJsonSuccess() throws IOException {
        InsertRightsDistributionResponse response =
            handler.parseJson(loadFile("crm_response.json"), Collections.emptyMap());
        assertEquals(InsertRightsDistributionResponseStatusEnum.SUCCESS, response.getStatus());
        assertTrue(CollectionUtils.isEmpty(response.getInvalidUsageIds()));
    }

    @Test
    public void testParseJsonInvalidResponseOneInvalidUsage() throws IOException {
        assertParseResponseWithInvalidUsages("crm_response_failed.json",
            ImmutableSet.of("2e9747c7-f3e8-4c3c-94cf-4f9ba3d10109"));
    }

    @Test
    public void testParseJsonInvalidResponseTwoInvalidUsages() throws IOException {
        assertParseResponseWithInvalidUsages("crm_response_failed_two_usages.json",
            ImmutableSet.of("ca9763ab-1ce7-486e-8938-272f6c3392a7", "bbdc5eb3-7396-47b8-bc18-5ec6ad0c4ef1"));
    }

    @Test
    public void testParseJsonIoException() {
        try {
            handler.parseJson("{\"elements\":{}}", Collections.emptyMap());
            fail();
        } catch (IOException e) {
            assertEquals("Send usages to CRM. Failed. Reason=Couldn't parse 'insert rights distribution' " +
                "response. Response={\"elements\":{}}, JsonNode={\"elements\":{}}", e.getMessage());
        }
    }

    private void assertParseResponseWithInvalidUsages(String fileName, Set<String> expectedUsagesIds)
        throws IOException {
        InsertRightsDistributionResponse response = handler.parseJson(loadFile(fileName), Collections.emptyMap());
        assertEquals(InsertRightsDistributionResponseStatusEnum.CRM_ERROR, response.getStatus());
        Set<String> actualInvalidUsageIds = response.getInvalidUsageIds();
        assertTrue(CollectionUtils.isNotEmpty(actualInvalidUsageIds));
        assertEquals(CollectionUtils.size(expectedUsagesIds), CollectionUtils.size(actualInvalidUsageIds));
        assertEquals(expectedUsagesIds, actualInvalidUsageIds);
    }

    private String loadFile(String filePath) {
        return TestUtils.fileToString(this.getClass(), filePath);
    }
}
