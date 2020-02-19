package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.Scenario.NtsFields;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * Implementation of {@link StdDeserializer} for scenario {@link NtsFields}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/17/2019
 *
 * @author Aliaksandr Liakh
 */
public class NtsScenarioFieldsDeserializer extends StdDeserializer<NtsFields> {

    private static final String PRE_SERVICE_FEE_FUND_UID = "pre_service_fee_fund_uid";

    /**
     * Constructor.
     */
    NtsScenarioFieldsDeserializer() {
        super(NtsFields.class);
    }

    @Override
    public NtsFields deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        NtsFields ntsFields = new NtsFields();
        JsonToken currentToken;
        while (null != (currentToken = jp.nextValue())) {
            if (JsonToken.VALUE_NUMBER_INT == currentToken || JsonToken.VALUE_NUMBER_FLOAT == currentToken) {
                readAmounts(jp, ntsFields);
            } else if (JsonToken.VALUE_STRING == currentToken && PRE_SERVICE_FEE_FUND_UID.equals(jp.getCurrentName())) {
                ntsFields.setPreServiceFeeFundId(jp.getValueAsString());
            }
        }
        return ntsFields;
    }

    private void readAmounts(JsonParser jp, NtsFields ntsFields) throws IOException {
        switch (jp.getCurrentName()) {
            case "rh_minimum_amount":
                ntsFields.setRhMinimumAmount(jp.getDecimalValue());
                break;
            case "pre_service_fee_amount":
                ntsFields.setPreServiceFeeAmount(jp.getDecimalValue());
                break;
            case "post_service_fee_amount":
                ntsFields.setPostServiceFeeAmount(jp.getDecimalValue());
                break;
            default:
                break;
        }
    }
}
