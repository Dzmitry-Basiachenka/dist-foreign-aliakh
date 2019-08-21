package com.copyright.rup.dist.foreign.integration.crm.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.util.JsonUtils;
import com.copyright.rup.dist.foreign.integration.crm.api.GetRightsDistributionResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Parser for 'get rights distribution' response JSON from CRM system.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 08/20/2019
 *
 * @author Aliaksandr Liakh
 */
@Component
class GetRightsDistributionResponseHandler {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Parses 'get rights distribution' response JSON.
     *
     * @param json the JSON response
     * @return list of {@link GetRightsDistributionResponse}
     * @throws IOException if JSON parsing fails
     */
    List<GetRightsDistributionResponse> parseJson(String json) throws IOException {
        LOGGER.trace("Parse 'get rights distribution' response. Response={}", json);
        List<GetRightsDistributionResponse> responses = new ArrayList<>();
        JsonNode root = JsonUtils.readJsonTree(objectMapper, Objects.requireNonNull(json));
        JsonNode list = root.get("list");
        if (Objects.nonNull(list)) {
            if (list.isArray()) {
                JsonNode item = list.get(0);
                if (null != item) {
                    parseNodes(item.get("cccRightsDistribution"), responses);
                }
            }
        } else {
            throw new IOException(String.format("Send usages to CRM. Failed. Reason=Couldn't parse " +
                "'get rights distribution' response. Response=%s, JsonNode=%s", json, root));

        }
        return responses;
    }

    private void parseNodes(JsonNode nodes, List<GetRightsDistributionResponse> responses) {
        if (null != nodes) {
            if (nodes.isArray()) {
                nodes.forEach(node -> responses.add(parseNode(node)));
            } else {
                responses.add(parseNode(nodes));
            }
        }
    }

    private GetRightsDistributionResponse parseNode(JsonNode node) {
        GetRightsDistributionResponse response = new GetRightsDistributionResponse();
        response.setCccEventId(node.findValue("cccEventId").asText());
        response.setOmOrderDetailNumber(JsonUtils.getStringValue(node.findValue("omOrderDetailNumber")));
        return response;
    }
}
