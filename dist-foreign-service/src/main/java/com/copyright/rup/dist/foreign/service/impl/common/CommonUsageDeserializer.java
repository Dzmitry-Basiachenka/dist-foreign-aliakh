package com.copyright.rup.dist.foreign.service.impl.common;

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
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Implementation of {@link JsonDeserializer} to get {@link Usage}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/13/2018
 *
 * @author Ihar Suvorau
 */
@Component("df.service.commonUsageDeserializer")
public class CommonUsageDeserializer extends JsonDeserializer<Usage> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Override
    public Usage deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        Usage usage = null;
        try {
            usage = deserializeUsage(parser.readValueAsTree());
        } catch (JsonParseException e) {
            LOGGER.warn("Deserialize Usage message. Failed", e);
        }
        return usage;
    }

    private Usage deserializeUsage(JsonNode jsonNode) {
        Usage usage = new Usage();
        usage.setId(JsonUtils.getStringValue(jsonNode.get("id")));
        usage.getRightsholder().setId(JsonUtils.getStringValue(jsonNode.get("rh_uid")));
        usage.getRightsholder().setAccountNumber(JsonUtils.getLongValue(jsonNode.get("rh_account_number")));
        usage.setStandardNumber(JsonUtils.getStringValue(jsonNode.get("standard_number")));
        usage.setWorkTitle(JsonUtils.getStringValue(jsonNode.get("work_title")));
        usage.setSystemTitle(JsonUtils.getStringValue(jsonNode.get("system_title")));
        usage.setWrWrkInst(JsonUtils.getLongValue(jsonNode.get("wr_wrk_inst")));
        usage.setBatchId(JsonUtils.getStringValue(jsonNode.get("batch_id")));
        usage.setGrossAmount(
            JsonUtils.getBigDecimalValue(jsonNode.get("gross_amount")).setScale(2, RoundingMode.HALF_UP));
        usage.setStatus(UsageStatusEnum.valueOf(JsonUtils.getStringValue(jsonNode.get("status"))));
        usage.setProductFamily(JsonUtils.getStringValue(jsonNode.get("product_family")));
        return usage;
    }
}
