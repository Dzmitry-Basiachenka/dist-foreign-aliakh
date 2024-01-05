package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.UsageBatch.SalFields;
import com.copyright.rup.dist.foreign.repository.impl.converter.json.common.CommonJsonFieldsDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;

import java.io.IOException;

/**
 * Implementation of {@link CommonJsonFieldsDeserializer} for usage batch {@link SalFields}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/30/2020
 *
 * @author Aliaksandr Liakh
 */
public class SalBatchFieldsDeserializer extends CommonJsonFieldsDeserializer<SalFields> {

    private static final long serialVersionUID = -3643565260739263742L;
    private static final String LICENSEE_ACCOUNT_NUMBER = "licensee_account_number";
    private static final String LICENSEE_NAME = "licensee_name";

    /**
     * Default constructor.
     */
    SalBatchFieldsDeserializer() {
        super(SalFields.class);
    }

    @Override
    public SalFields deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        SalFields salFields = new SalFields();
        JsonToken currentToken;
        while (null != (currentToken = jp.nextValue())) {
            if (JsonToken.VALUE_NUMBER_INT == currentToken && LICENSEE_ACCOUNT_NUMBER.equals(jp.getCurrentName())) {
                salFields.setLicenseeAccountNumber(jp.getValueAsLong());
            } else if (JsonToken.VALUE_STRING == currentToken && LICENSEE_NAME.equals(jp.getCurrentName())) {
                salFields.setLicenseeName(jp.getValueAsString());
            }
        }
        return salFields;
    }
}
