package com.copyright.rup.dist.foreign.service.impl.eligibility;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.util.JsonUtils;
import com.copyright.rup.dist.foreign.domain.Usage;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Implementation of {@link JsonDeserializer} to check whether RH is eligible or not.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/10/2019
 *
 * @author Uladzislau Shalamitski
 */
@Component("df.service.rhEligibilityDeserializer")
public class RhEligibilityDeserializer extends JsonDeserializer<Usage> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Override
    public Usage deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        Usage usage = null;
        try {
            usage = deserializeUsage(parser.readValueAsTree());
        } catch (JsonParseException e) {
            LOGGER.warn("Deserialize RH eligibility message. Failed", e);
        }
        return usage;
    }

    private Usage deserializeUsage(JsonNode jsonNode) {
        Usage usage = new Usage();
        usage.setId(JsonUtils.getStringValue(jsonNode.get("id")));
        usage.getRightsholder().setId(JsonUtils.getStringValue(jsonNode.get("rh_uid")));
        return usage;
    }
}
