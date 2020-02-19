package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.UsageBatch.NtsFields;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
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

    /**
     * Default constructor.
     */
    NtsBatchFieldsDeserializer() {
        super(NtsFields.class);
    }

    @Override
    public NtsFields deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        NtsFields ntsFields = new NtsFields();
        JsonToken currentToken;
        while (null != (currentToken = jp.nextValue())) {
            if (JsonToken.VALUE_NUMBER_INT == currentToken) {
                readPeriods(jp, ntsFields);
            }
            if (JsonToken.VALUE_NUMBER_INT == currentToken || JsonToken.VALUE_NUMBER_FLOAT == currentToken) {
                readAmounts(jp, ntsFields);
            }
            if (JsonToken.START_ARRAY == currentToken) {
                readMarkets(jp, ntsFields);
            }
            if (JsonToken.VALUE_TRUE == currentToken || JsonToken.VALUE_FALSE == currentToken) {
                readExcludingStmFlag(jp, ntsFields);
            }
        }
        return ntsFields;
    }

    private void readPeriods(JsonParser jp, NtsFields ntsFields) throws IOException {
        switch (jp.getCurrentName()) {
            case "fund_pool_period_from":
                ntsFields.setFundPoolPeriodFrom(jp.getIntValue());
                break;
            case "fund_pool_period_to":
                ntsFields.setFundPoolPeriodTo(jp.getIntValue());
                break;
            default:
                break;
        }
    }

    private void readAmounts(JsonParser jp, NtsFields ntsFields) throws IOException {
        switch (jp.getCurrentName()) {
            case "stm_amount":
                ntsFields.setStmAmount(jp.getDecimalValue());
                break;
            case "non_stm_amount":
                ntsFields.setNonStmAmount(jp.getDecimalValue());
                break;
            case "stm_minimum_amount":
                ntsFields.setStmMinimumAmount(jp.getDecimalValue());
                break;
            case "non_stm_minimum_amount":
                ntsFields.setNonStmMinimumAmount(jp.getDecimalValue());
                break;
            default:
                break;
        }
    }

    private void readMarkets(JsonParser jp, NtsFields ntsFields) throws IOException {
        if ("markets".equals(jp.getCurrentName())) {
            while (jp.nextToken() != JsonToken.END_ARRAY) {
                Set<String> markets = ntsFields.getMarkets();
                markets.add(jp.getValueAsString());
            }
        }
    }

    private void readExcludingStmFlag(JsonParser jp, NtsFields ntsFields) throws IOException {
        if ("excluding_stm".equals(jp.getCurrentName())) {
            ntsFields.setExcludingStm(jp.getValueAsBoolean());
        }
    }
}
