package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.Scenario.SalFields;
import com.copyright.rup.dist.foreign.repository.impl.converter.json.common.CommonJsonFieldsDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;

import java.io.IOException;

/**
 * Implementation of {@link CommonJsonFieldsDeserializer} for scenario {@link SalFields}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 09/24/2020
 *
 * @author Ihar Suvorau
 */
public class SalScenarioFieldsDeserializer extends CommonJsonFieldsDeserializer<SalFields> {

    private static final String FUND_POOL_ID = "fund_pool_uid";

    /**
     * Constructor.
     */
    SalScenarioFieldsDeserializer() {
        super(SalFields.class);
    }

    @Override
    public SalFields deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        SalFields salFields = new SalFields();
        JsonToken currentToken;
        while (null != (currentToken = jp.nextValue())) {
            if (JsonToken.VALUE_STRING == currentToken && FUND_POOL_ID.equals(jp.getCurrentName())) {
                salFields.setFundPoolId(jp.getValueAsString());
            }
        }
        return salFields;
    }
}
