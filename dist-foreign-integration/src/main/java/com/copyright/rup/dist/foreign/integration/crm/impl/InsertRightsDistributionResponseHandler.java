package com.copyright.rup.dist.foreign.integration.crm.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.util.JsonUtils;
import com.copyright.rup.dist.foreign.integration.crm.api.InsertRightsDistributionResponse;
import com.copyright.rup.dist.foreign.integration.crm.api.InsertRightsDistributionResponseStatusEnum;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Parser for 'insert rights distribution' response JSON from CRM system.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 08/20/2019
 *
 * @author Aliaksandr Liakh
 */
@Component
class InsertRightsDistributionResponseHandler {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    private final ObjectMapper objectMapper;

    /**
     * Constructor.
     */
    public InsertRightsDistributionResponseHandler() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Parses 'insert rights distribution' response JSON.
     *
     * @param json       the JSON response
     * @param requestMap detail id to request map
     * @return the {@link InsertRightsDistributionResponse} instances
     * @throws IOException if JSON cannot be parsed
     */
    InsertRightsDistributionResponse parseJson(String json, Map<String, String> requestMap) throws IOException {
        LOGGER.trace("Parse 'insert rights distribution' response. Response={}", json);
        JsonNode root = JsonUtils.readJsonTree(objectMapper, Objects.requireNonNull(json));
        JsonNode list = root.get("list");
        InsertRightsDistributionResponse response =
            new InsertRightsDistributionResponse(InsertRightsDistributionResponseStatusEnum.SUCCESS);
        if (Objects.nonNull(list)) {
            JsonNode errorNode = list.findValue("error");
            if (Objects.nonNull(errorNode)) {
                response.setStatus(InsertRightsDistributionResponseStatusEnum.CRM_ERROR);
                List<JsonNode> errorMessageNodes = new ArrayList<>();
                if (errorNode.isArray()) {
                    errorNode.forEach(errorMessageNodes::add);
                } else {
                    errorMessageNodes.add(errorNode);
                }
                if (CollectionUtils.isNotEmpty(errorMessageNodes)) {
                    for (JsonNode errorMessageNode : errorMessageNodes) {
                        JsonNode key = errorMessageNode.findValue("key");
                        List<JsonNode> errorMessages = errorMessageNode.findValues("string");
                        response.addInvalidUsageId(key.asText());
                        LOGGER.warn("Send usages to CRM. Failed. DetailId={}, Request={}, ErrorMessage={}", key,
                            requestMap.get(key.asText()), errorMessages);
                    }
                }
            }
        } else {
            throw new IOException(String.format("Send usages to CRM. Failed. Reason=Couldn't parse " +
                "'insert rights distribution' response. Response=%s, JsonNode=%s", json, root));
        }
        return response;
    }
}
