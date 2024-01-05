package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.Scenario.NtsFields;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Implementation of {@link StdSerializer} for scenario {@link NtsFields}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/17/2019
 *
 * @author Aliaksandr Liakh
 */
public class NtsScenarioFieldsSerializer extends StdSerializer<NtsFields> {

    private static final long serialVersionUID = 4839520822338035649L;

    /**
     * Constructor.
     */
    NtsScenarioFieldsSerializer() {
        super(NtsFields.class);
    }

    @Override
    public void serialize(NtsFields ntsFields, JsonGenerator jg, SerializerProvider provider) throws IOException {
        jg.writeStartObject();
        jg.writeNumberField("rh_minimum_amount", ntsFields.getRhMinimumAmount());
        jg.writeNumberField("pre_service_fee_amount", ntsFields.getPreServiceFeeAmount());
        jg.writeNumberField("post_service_fee_amount", ntsFields.getPostServiceFeeAmount());
        jg.writeStringField("pre_service_fee_fund_uid", ntsFields.getPreServiceFeeFundId());
        jg.writeEndObject();
    }
}
