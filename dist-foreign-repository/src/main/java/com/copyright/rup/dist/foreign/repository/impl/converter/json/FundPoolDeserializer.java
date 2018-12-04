package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.FundPool;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.List;

/**
 * Implementation of {@link StdDeserializer} for {@link FundPool}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 12/03/2018
 *
 * @author Aliaksandr Liakh
 */
public class FundPoolDeserializer extends StdDeserializer<FundPool> {

    /**
     * Default constructor.
     */
    FundPoolDeserializer() {
        super(FundPool.class);
    }

    @Override
    public FundPool deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        FundPool fundPool = new FundPool();
        JsonToken currentToken;
        while (null != (currentToken = jp.nextValue())) {
            if (JsonToken.VALUE_NUMBER_INT == currentToken) {
                readPeriods(jp, fundPool);
            }
            if (JsonToken.VALUE_NUMBER_INT == currentToken || JsonToken.VALUE_NUMBER_FLOAT == currentToken) {
                readAmounts(jp, fundPool);
            }
            if (JsonToken.START_ARRAY == currentToken) {
                readMarkets(jp, fundPool);
            }
        }
        return fundPool;
    }

    private void readPeriods(JsonParser jp, FundPool fundPool) throws IOException {
        switch (jp.getCurrentName()) {
            case "market_period_from":
                fundPool.setMarketPeriodFrom(jp.getIntValue());
                break;
            case "market_period_to":
                fundPool.setMarketPeriodTo(jp.getIntValue());
                break;
            default:
                break;
        }
    }

    private void readAmounts(JsonParser jp, FundPool fundPool) throws IOException {
        switch (jp.getCurrentName()) {
            case "stm_amount":
                fundPool.setStmAmount(jp.getDecimalValue());
                break;
            case "non_stm_amount":
                fundPool.setNonStmAmount(jp.getDecimalValue());
                break;
            case "stm_minimum_amount":
                fundPool.setStmMinimumAmount(jp.getDecimalValue());
                break;
            case "non_stm_minimum_amount":
                fundPool.setNonStmMinimumAmount(jp.getDecimalValue());
                break;
            default:
                break;
        }
    }

    private void readMarkets(JsonParser jp, FundPool fundPool) throws IOException {
        if ("markets".equals(jp.getCurrentName())) {
            while (jp.nextToken() != JsonToken.END_ARRAY) {
                List<String> markets = fundPool.getMarkets();
                markets.add(jp.getValueAsString());
            }
        }
    }
}
