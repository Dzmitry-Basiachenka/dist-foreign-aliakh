package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.NtsFieldsHolder;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * Implementation of {@link StdDeserializer} for {@link NtsFieldsHolder}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/17/2019
 *
 * @author Aliaksandr Liakh
 */
public class NtsFieldsHolderDeserializer extends StdDeserializer<NtsFieldsHolder> {

    private static final String RH_MINIMUM_AMOUNT = "rh_minimum_amount";

    /**
     * Constructor.
     */
    NtsFieldsHolderDeserializer() {
        super(NtsFieldsHolder.class);
    }

    @Override
    public NtsFieldsHolder deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        NtsFieldsHolder holder = new NtsFieldsHolder();
        JsonToken currentToken;
        while (null != (currentToken = jp.nextValue())) {
            if ((JsonToken.VALUE_NUMBER_INT == currentToken || JsonToken.VALUE_NUMBER_FLOAT == currentToken)
                && RH_MINIMUM_AMOUNT.equals(jp.getCurrentName())) {
                holder.setRhMinimumAmount(jp.getDecimalValue());
            }
        }
        return holder;
    }
}
