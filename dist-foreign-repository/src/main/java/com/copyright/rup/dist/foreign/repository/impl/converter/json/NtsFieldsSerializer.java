package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.NtsFields;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Implementation of {@link StdSerializer} for {@link NtsFields}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/17/2019
 *
 * @author Aliaksandr Liakh
 */
public class NtsFieldsSerializer extends StdSerializer<NtsFields> {

    /**
     * Constructor.
     */
    NtsFieldsSerializer() {
        super(NtsFields.class);
    }

    @Override
    public void serialize(NtsFields ntsFields, JsonGenerator jg, SerializerProvider provider) throws IOException {
        jg.writeStartObject();
        jg.writeNumberField("rh_minimum_amount", ntsFields.getRhMinimumAmount());
        jg.writeEndObject();
    }
}
