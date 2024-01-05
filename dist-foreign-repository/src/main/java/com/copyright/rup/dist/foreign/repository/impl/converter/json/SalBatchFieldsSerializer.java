package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.UsageBatch.SalFields;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Implementation of {@link StdSerializer} for {@link SalFields}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/30/2020
 *
 * @author Aliaksandr Liakh
 */
public class SalBatchFieldsSerializer extends StdSerializer<SalFields> {

    private static final long serialVersionUID = 326385360807491588L;

    /**
     * Default constructor.
     */
    SalBatchFieldsSerializer() {
        super(SalFields.class);
    }

    @Override
    public void serialize(SalFields salFields, JsonGenerator jg, SerializerProvider provider) throws IOException {
        jg.writeStartObject();
        jg.writeNumberField("licensee_account_number", salFields.getLicenseeAccountNumber());
        jg.writeStringField("licensee_name", salFields.getLicenseeName());
        jg.writeEndObject();
    }
}
