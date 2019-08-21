package com.copyright.rup.dist.foreign.integration.crm.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.integration.crm.api.GetRightsDistributionResponse;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Verifies {@link GetRightsDistributionResponseHandler}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 08/20/2019
 *
 * @author Aliaksandr Liakh
 */
public class GetRightsDistributionResponseHandlerTest {

    private final GetRightsDistributionResponseHandler handler = new GetRightsDistributionResponseHandler();

    @Test
    public void testParseJsonMultipleValues() throws IOException {
        String json = loadFile("crm_get_rights_distribution_response_multiple_values.json");
        List<GetRightsDistributionResponse> responses = handler.parseJson(json);
        assertEquals(2, responses.size());
        GetRightsDistributionResponse response1 = responses.get(0);
        assertEquals("12477", response1.getCccEventId());
        assertEquals("cd48a17b-493a-4be8-8ac7-3ad894b403a5", response1.getOmOrderDetailNumber());
        GetRightsDistributionResponse response2 = responses.get(1);
        assertEquals("13315", response2.getCccEventId());
        assertEquals("169bc60e-7576-4f2d-8f70-8475f2102ebb", response2.getOmOrderDetailNumber());
    }

    @Test
    public void testParseJsonSingleValue() throws IOException {
        String json = loadFile("crm_get_rights_distribution_response_single_value.json");
        List<GetRightsDistributionResponse> responses = handler.parseJson(json);
        assertEquals(1, responses.size());
        GetRightsDistributionResponse response = responses.get(0);
        assertEquals("12477", response.getCccEventId());
        assertEquals("cd48a17b-493a-4be8-8ac7-3ad894b403a5", response.getOmOrderDetailNumber());
    }

    @Test
    public void testParseJsonNoValue() throws IOException {
        String json = loadFile("crm_get_rights_distribution_response_no_value.json");
        List<GetRightsDistributionResponse> responses = handler.parseJson(json);
        assertEquals(0, responses.size());
    }

    @Test
    public void testParseJsonIoException() {
        try {
            handler.parseJson("{\"elements\":{}}");
            fail();
        } catch (IOException e) {
            assertEquals("Send usages to CRM. Failed. Reason=Couldn't parse 'get rights distribution' " +
                "response. Response={\"elements\":{}}, JsonNode={\"elements\":{}}", e.getMessage());
        }
    }

    private String loadFile(String filePath) {
        return TestUtils.fileToString(this.getClass(), filePath);
    }
}
