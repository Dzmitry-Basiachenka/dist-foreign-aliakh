package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.domain.UsageBatch.NtsFields;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of {@link StdDeserializer} for {@link NtsFields}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 12/03/2018
 *
 * @author Aliaksandr Liakh
 */
public class NtsBatchFieldsDeserializer extends StdDeserializer<NtsFields> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    /**
     * Default constructor.
     */
    NtsBatchFieldsDeserializer() {
        super(NtsFields.class);
    }

    @Override
    public NtsFields deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        NtsFields ntsFields = new NtsFields();
        try {
            JsonNode jsonNode = parser.readValueAsTree();
            ntsFields.setFundPoolPeriodFrom(getIntegerValue(jsonNode.get("fund_pool_period_from")));
            ntsFields.setFundPoolPeriodTo(getIntegerValue(jsonNode.get("fund_pool_period_to")));
            ntsFields.setStmAmount(getBigDecimalValue(jsonNode.get("stm_amount")));
            ntsFields.setNonStmAmount(getBigDecimalValue(jsonNode.get("non_stm_amount")));
            ntsFields.setStmMinimumAmount(getBigDecimalValue(jsonNode.get("stm_minimum_amount")));
            ntsFields.setNonStmMinimumAmount(getBigDecimalValue(jsonNode.get("non_stm_minimum_amount")));
            ntsFields.setMarkets(getMarkets(jsonNode.get("markets")));
            ntsFields.setExcludingStm(getBooleanValue(jsonNode.get("excluding_stm")));
        } catch (JsonParseException e) {
            LOGGER.warn("Deserialize NTS batch fields. Failed", e);
        }
        return ntsFields;
    }

    private static Integer getIntegerValue(JsonNode node) {
        return Objects.nonNull(node) ? node.asInt() : null;
    }

    private static BigDecimal getBigDecimalValue(JsonNode node) {
        return Objects.nonNull(node) && NumberUtils.isNumber(node.asText()) ? new BigDecimal(node.asText()) : null;
    }

    private Set<String> getMarkets(JsonNode node) {
        Set<String> markets = new HashSet<>();
        if (Objects.nonNull(node)) {
            node.elements().forEachRemaining(element -> markets.add(element.textValue()));
        }
        return markets;
    }

    private static boolean getBooleanValue(JsonNode node) {
        boolean result = false;
        if (Objects.nonNull(node)) {
            result = Boolean.parseBoolean(node.asText());
        }
        return result;
    }
}
