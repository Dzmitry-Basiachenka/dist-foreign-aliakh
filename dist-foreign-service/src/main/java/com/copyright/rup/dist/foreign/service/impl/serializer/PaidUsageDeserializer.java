package com.copyright.rup.dist.foreign.service.impl.serializer;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.util.JsonUtils;
import com.copyright.rup.dist.foreign.domain.PaidUsage;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;

import org.slf4j.Logger;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

/**
 * Implementation of {@link JsonDeserializer} for list of {@link PaidUsage}s.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 02/21/18
 *
 * @author Darya Baraukova
 */
public class PaidUsageDeserializer extends JsonDeserializer<List<PaidUsage>> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Override
    public List<PaidUsage> deserialize(JsonParser parser, DeserializationContext context)
        throws IOException {
        List<PaidUsage> usages = Lists.newArrayList();
        try {
            JsonNode tree = parser.readValueAsTree();
            LOGGER.trace("Deserialize LM message. Received JSON={}", tree);
            JsonNode detailsNode = tree.get("details");
            if (detailsNode.isArray()) {
                for (JsonNode detailNode : detailsNode) {
                    usages.add(deserializeLiability(detailNode));
                }
            }
        } catch (JsonParseException e) {
            LOGGER.warn("Deserialize LM message. Failed", e);
        }
        return usages;
    }

    private PaidUsage deserializeLiability(JsonNode jsonNode) {
        PaidUsage usage = new PaidUsage();
        usage.getPayee().setAccountNumber(JsonUtils.getLongValueFromString(jsonNode.get("rollup_account_number")));
        usage.setDetailId(JsonUtils.getLongValueFromString(jsonNode.get("detail_id")));
        usage.setCheckNumber(JsonUtils.getStringValue(jsonNode.get("check_number")));
        usage.setCheckDate(getLocalDateValueFromLong(jsonNode.get("check_date")));
        usage.setCccEventId(JsonUtils.getStringValue(jsonNode.get("ccc_event_id")));
        usage.setDistributionName(JsonUtils.getStringValue(jsonNode.get("distribution_name")));
        usage.setDistributionDate(getLocalDateValueFromLong(jsonNode.get("distribution_date")));
        usage.setPeriodEndDate(getLocalDateValueFromLong(jsonNode.get("period_end_date")));
        return usage;
    }

    private LocalDate getLocalDateValueFromLong(JsonNode node) {
        return null != node ? LocalDate.from(
            Instant.ofEpochMilli(node.asLong()).atZone(ZoneOffset.systemDefault()).toLocalDate()) : null;
    }
}
