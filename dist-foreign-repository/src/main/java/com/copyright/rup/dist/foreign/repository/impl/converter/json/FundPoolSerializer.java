package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.FundPool;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Implementation of {@link StdSerializer} for {@link FundPool}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 12/03/2018
 *
 * @author Aliaksandr Liakh
 */
public class FundPoolSerializer extends StdSerializer<FundPool> {

    /**
     * Default constructor.
     */
    FundPoolSerializer() {
        super(FundPool.class);
    }

    @Override
    public void serialize(FundPool fundPool, JsonGenerator jg, SerializerProvider provider) throws IOException {
        jg.writeStartObject();
        writePeriods(jg, fundPool);
        writeAmounts(jg, fundPool);
        writeMarkets(jg, fundPool);
        jg.writeEndObject();
    }

    private void writePeriods(JsonGenerator jg, FundPool fundPool) throws IOException {
        jg.writeNumberField("market_period_from", fundPool.getMarketPeriodFrom());
        jg.writeNumberField("market_period_to", fundPool.getMarketPeriodTo());
    }

    private void writeAmounts(JsonGenerator jg, FundPool fundPool) throws IOException {
        jg.writeNumberField("stm_amount", fundPool.getStmAmount());
        jg.writeNumberField("non_stm_amount", fundPool.getNonStmAmount());
        jg.writeNumberField("stm_minimum_amount", fundPool.getStmMinimumAmount());
        jg.writeNumberField("non_stm_minimum_amount", fundPool.getNonStmMinimumAmount());
    }

    private void writeMarkets(JsonGenerator jg, FundPool fundPool) throws IOException {
        if (null != fundPool.getMarkets()) {
            jg.writeArrayFieldStart("markets");
            for (String market : fundPool.getMarkets()) {
                jg.writeString(market);
            }
            jg.writeEndArray();
        }
    }
}
