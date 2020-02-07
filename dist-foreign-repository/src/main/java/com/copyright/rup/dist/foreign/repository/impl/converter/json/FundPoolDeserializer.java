package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.NtsFundPool;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Set;

/**
 * Implementation of {@link StdDeserializer} for {@link NtsFundPool}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 12/03/2018
 *
 * @author Aliaksandr Liakh
 */
public class FundPoolDeserializer extends StdDeserializer<NtsFundPool> {

    /**
     * Default constructor.
     */
    FundPoolDeserializer() {
        super(NtsFundPool.class);
    }

    @Override
    public NtsFundPool deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        NtsFundPool ntsFundPool = new NtsFundPool();
        JsonToken currentToken;
        while (null != (currentToken = jp.nextValue())) {
            if (JsonToken.VALUE_NUMBER_INT == currentToken) {
                readPeriods(jp, ntsFundPool);
            }
            if (JsonToken.VALUE_NUMBER_INT == currentToken || JsonToken.VALUE_NUMBER_FLOAT == currentToken) {
                readAmounts(jp, ntsFundPool);
            }
            if (JsonToken.START_ARRAY == currentToken) {
                readMarkets(jp, ntsFundPool);
            }
            if (JsonToken.VALUE_TRUE == currentToken || JsonToken.VALUE_FALSE == currentToken) {
                readExcludingStmFlag(jp, ntsFundPool);
            }
        }
        return ntsFundPool;
    }

    private void readPeriods(JsonParser jp, NtsFundPool ntsFundPool) throws IOException {
        switch (jp.getCurrentName()) {
            case "fund_pool_period_from":
                ntsFundPool.setFundPoolPeriodFrom(jp.getIntValue());
                break;
            case "fund_pool_period_to":
                ntsFundPool.setFundPoolPeriodTo(jp.getIntValue());
                break;
            default:
                break;
        }
    }

    private void readAmounts(JsonParser jp, NtsFundPool ntsFundPool) throws IOException {
        switch (jp.getCurrentName()) {
            case "stm_amount":
                ntsFundPool.setStmAmount(jp.getDecimalValue());
                break;
            case "non_stm_amount":
                ntsFundPool.setNonStmAmount(jp.getDecimalValue());
                break;
            case "stm_minimum_amount":
                ntsFundPool.setStmMinimumAmount(jp.getDecimalValue());
                break;
            case "non_stm_minimum_amount":
                ntsFundPool.setNonStmMinimumAmount(jp.getDecimalValue());
                break;
            default:
                break;
        }
    }

    private void readMarkets(JsonParser jp, NtsFundPool ntsFundPool) throws IOException {
        if ("markets".equals(jp.getCurrentName())) {
            while (jp.nextToken() != JsonToken.END_ARRAY) {
                Set<String> markets = ntsFundPool.getMarkets();
                markets.add(jp.getValueAsString());
            }
        }
    }

    private void readExcludingStmFlag(JsonParser jp, NtsFundPool ntsFundPool) throws IOException {
        if ("excluding_stm".equals(jp.getCurrentName())) {
            ntsFundPool.setExcludingStm(jp.getValueAsBoolean());
        }
    }
}
