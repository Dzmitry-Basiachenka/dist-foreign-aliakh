package com.copyright.rup.dist.foreign.service.impl.matching;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.util.JsonUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import org.slf4j.Logger;

import java.io.IOException;
import java.math.RoundingMode;

/**
 * Implementation of {@link JsonDeserializer} to get {@link Usage} for matching.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/13/2018
 *
 * @author Ihar Suvorau
 */
public class MatchingDeserializer extends JsonDeserializer<Usage> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Override
    public Usage deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        Usage usage = null;
        try {
            usage = deserializeUsage(parser.readValueAsTree());
        } catch (JsonParseException e) {
            LOGGER.warn("Deserialize LM message. Failed", e);
        }
        return usage;
    }

    private Usage deserializeUsage(JsonNode jsonNode) {
        Usage usage = new Usage();
        usage.setId(JsonUtils.getStringValue(jsonNode.get("id")));
        usage.setStandardNumber(JsonUtils.getStringValue(jsonNode.get("standard_number")));
        usage.setWorkTitle(JsonUtils.getStringValue(jsonNode.get("work_title")));
        usage.setBatchId(JsonUtils.getStringValue(jsonNode.get("batch_id")));
        usage.setProductFamily(JsonUtils.getStringValue(jsonNode.get("product_family")));
        usage.setStatus(UsageStatusEnum.valueOf(JsonUtils.getStringValue(jsonNode.get("status"))));
        usage.setGrossAmount(
            JsonUtils.getBigDecimalValue(jsonNode.get("gross_amount")).setScale(2, RoundingMode.HALF_UP));
        return usage;
    }
}
