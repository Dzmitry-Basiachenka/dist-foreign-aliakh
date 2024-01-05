package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.domain.UsageBatch.NtsFields;
import com.copyright.rup.dist.foreign.repository.impl.converter.json.common.CommonJsonFieldsDeserializer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import org.slf4j.Logger;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of {@link CommonJsonFieldsDeserializer} for usage batch {@link NtsFields}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 12/03/2018
 *
 * @author Aliaksandr Liakh
 */
public class NtsBatchFieldsDeserializer extends CommonJsonFieldsDeserializer<NtsFields> {

    private static final long serialVersionUID = 5297058521268135670L;
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

    private Set<String> getMarkets(JsonNode node) {
        Set<String> markets = new HashSet<>();
        if (Objects.nonNull(node)) {
            node.elements().forEachRemaining(element -> markets.add(element.textValue()));
        }
        return markets;
    }
}
