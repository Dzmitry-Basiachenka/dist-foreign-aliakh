package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.Scenario.SalFields;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Implementation of {@link StdSerializer} for scenario {@link SalFields}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 09/24/2020
 *
 * @author Ihar Suvorau
 */
public class SalScenarioFieldsSerializer extends StdSerializer<SalFields> {

    private static final long serialVersionUID = 6345837415696634943L;

    /**
     * Constructor.
     */
    SalScenarioFieldsSerializer() {
        super(SalFields.class);
    }

    @Override
    public void serialize(SalFields salFields, JsonGenerator jg, SerializerProvider provider) throws IOException {
        jg.writeStartObject();
        jg.writeStringField("fund_pool_uid", salFields.getFundPoolId());
        jg.writeEndObject();
    }
}
