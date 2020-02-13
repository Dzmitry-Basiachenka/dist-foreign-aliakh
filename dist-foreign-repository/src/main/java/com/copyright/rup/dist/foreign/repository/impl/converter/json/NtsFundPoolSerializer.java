package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.NtsFundPool;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Implementation of {@link StdSerializer} for {@link NtsFundPool}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 12/03/2018
 *
 * @author Aliaksandr Liakh
 */
public class NtsFundPoolSerializer extends StdSerializer<NtsFundPool> {

    /**
     * Default constructor.
     */
    NtsFundPoolSerializer() {
        super(NtsFundPool.class);
    }

    @Override
    public void serialize(NtsFundPool ntsFundPool, JsonGenerator jg, SerializerProvider provider) throws IOException {
        jg.writeStartObject();
        writePeriods(jg, ntsFundPool);
        writeAmounts(jg, ntsFundPool);
        writeMarkets(jg, ntsFundPool);
        jg.writeBooleanField("excluding_stm", ntsFundPool.isExcludingStm());
        jg.writeEndObject();
    }

    private void writePeriods(JsonGenerator jg, NtsFundPool ntsFundPool) throws IOException {
        jg.writeNumberField("fund_pool_period_from", ntsFundPool.getFundPoolPeriodFrom());
        jg.writeNumberField("fund_pool_period_to", ntsFundPool.getFundPoolPeriodTo());
    }

    private void writeAmounts(JsonGenerator jg, NtsFundPool ntsFundPool) throws IOException {
        jg.writeNumberField("stm_amount", ntsFundPool.getStmAmount());
        jg.writeNumberField("non_stm_amount", ntsFundPool.getNonStmAmount());
        jg.writeNumberField("stm_minimum_amount", ntsFundPool.getStmMinimumAmount());
        jg.writeNumberField("non_stm_minimum_amount", ntsFundPool.getNonStmMinimumAmount());
    }

    private void writeMarkets(JsonGenerator jg, NtsFundPool ntsFundPool) throws IOException {
        if (null != ntsFundPool.getMarkets()) {
            jg.writeArrayFieldStart("markets");
            for (String market : ntsFundPool.getMarkets()) {
                jg.writeString(market);
            }
            jg.writeEndArray();
        }
    }
}
