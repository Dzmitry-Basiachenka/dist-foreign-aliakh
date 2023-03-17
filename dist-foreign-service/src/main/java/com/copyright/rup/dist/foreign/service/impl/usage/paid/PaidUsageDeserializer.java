package com.copyright.rup.dist.foreign.service.impl.usage.paid;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.util.JsonUtils;
import com.copyright.rup.dist.foreign.domain.PaidUsage;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import org.slf4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
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
        List<PaidUsage> usages = new ArrayList<>();
        try {
            JsonNode tree = parser.readValueAsTree();
            LOGGER.trace("Deserialize LM message. Received JSON={}", tree);
            JsonNode detailsNode = tree.get("details");
            if (detailsNode.isArray()) {
                for (JsonNode detailNode : detailsNode) {
                    usages.add(deserializePaidUsage(detailNode));
                }
            }
        } catch (JsonParseException e) {
            LOGGER.warn("Deserialize LM message. Failed", e);
        }
        return usages;
    }

    private PaidUsage deserializePaidUsage(JsonNode jsonNode) {
        PaidUsage usage = new PaidUsage();
        usage.getRightsholder().setId(JsonUtils.getStringValue(jsonNode.get("rh_uid")));
        usage.getPayee().setId(JsonUtils.getStringValue(jsonNode.get("rollup_uid")));
        usage.setId(JsonUtils.getStringValue(jsonNode.get("detail_id")));
        usage.setCheckNumber(JsonUtils.getStringValue(jsonNode.get("check_number")));
        usage.setCheckDate(JsonUtils.getOffsetDateTime(jsonNode.get("check_date")));
        usage.setCccEventId(JsonUtils.getStringValue(jsonNode.get("ccc_event_id")));
        usage.setDistributionName(JsonUtils.getStringValue(jsonNode.get("distribution_name")));
        usage.setDistributionDate(JsonUtils.getOffsetDateTime(jsonNode.get("distribution_date")));
        usage.setPeriodEndDate(JsonUtils.getOffsetDateTime(jsonNode.get("period_end_date")));
        usage.setLmDetailId(JsonUtils.getStringValue(jsonNode.get("lm_detail_id")));
        usage.setNetAmount(JsonUtils.getBigDecimalValue(jsonNode.get("royalty_amount")));
        usage.setServiceFeeAmount(JsonUtils.getBigDecimalValue(jsonNode.get("service_fee_amount")));
        usage.setGrossAmount(JsonUtils.getBigDecimalValue(jsonNode.get("collected_amount")));
        usage.setProductFamily(JsonUtils.getStringValue(jsonNode.get("product_family")));
        JsonNode splitParentFlag = jsonNode.get("split_parent_flag");
        usage.setSplitParentFlag(null != splitParentFlag ? JsonUtils.getBooleanValue(splitParentFlag) : null);
        usage.setPostDistributionFlag(JsonUtils.getBooleanValue(jsonNode.get("post_distribution_flag")));
        return usage;
    }
}
