package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.UsageBatch.NtsFields;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Implementation of {@link StdSerializer} for {@link NtsFields}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 12/03/2018
 *
 * @author Aliaksandr Liakh
 */
public class NtsBatchFieldsSerializer extends StdSerializer<NtsFields> {

    private static final long serialVersionUID = -7699625406971570053L;

    /**
     * Default constructor.
     */
    NtsBatchFieldsSerializer() {
        super(NtsFields.class);
    }

    @Override
    public void serialize(NtsFields ntsFields, JsonGenerator jg, SerializerProvider provider) throws IOException {
        jg.writeStartObject();
        writePeriods(jg, ntsFields);
        writeAmounts(jg, ntsFields);
        writeMarkets(jg, ntsFields);
        jg.writeBooleanField("excluding_stm", ntsFields.isExcludingStm());
        jg.writeEndObject();
    }

    private void writePeriods(JsonGenerator jg, NtsFields ntsFields) throws IOException {
        jg.writeNumberField("fund_pool_period_from", ntsFields.getFundPoolPeriodFrom());
        jg.writeNumberField("fund_pool_period_to", ntsFields.getFundPoolPeriodTo());
    }

    private void writeAmounts(JsonGenerator jg, NtsFields ntsFields) throws IOException {
        jg.writeNumberField("stm_amount", ntsFields.getStmAmount());
        jg.writeNumberField("non_stm_amount", ntsFields.getNonStmAmount());
        jg.writeNumberField("stm_minimum_amount", ntsFields.getStmMinimumAmount());
        jg.writeNumberField("non_stm_minimum_amount", ntsFields.getNonStmMinimumAmount());
    }

    private void writeMarkets(JsonGenerator jg, NtsFields ntsFields) throws IOException {
        if (null != ntsFields.getMarkets()) {
            jg.writeArrayFieldStart("markets");
            for (String market : ntsFields.getMarkets()) {
                jg.writeString(market);
            }
            jg.writeEndArray();
        }
    }
}
