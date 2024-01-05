package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.UsageBatch.AclciFields;
import com.copyright.rup.dist.foreign.repository.impl.converter.json.common.CommonJsonFieldsDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;

import java.io.IOException;

/**
 * Implementation of {@link CommonJsonFieldsDeserializer} for usage batch {@link AclciFields}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/08/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclciBatchFieldsDeserializer extends CommonJsonFieldsDeserializer<AclciFields> {

    private static final long serialVersionUID = 340469983819507553L;
    private static final String LICENSEE_ACCOUNT_NUMBER = "licensee_account_number";
    private static final String LICENSEE_NAME = "licensee_name";

    /**
     * Default constructor.
     */
    AclciBatchFieldsDeserializer() {
        super(AclciFields.class);
    }

    @Override
    public AclciFields deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        AclciFields aclciFields = new AclciFields();
        JsonToken currentToken;
        while (null != (currentToken = jp.nextValue())) {
            if (JsonToken.VALUE_NUMBER_INT == currentToken && LICENSEE_ACCOUNT_NUMBER.equals(jp.getCurrentName())) {
                aclciFields.setLicenseeAccountNumber(jp.getValueAsLong());
            } else if (JsonToken.VALUE_STRING == currentToken && LICENSEE_NAME.equals(jp.getCurrentName())) {
                aclciFields.setLicenseeName(jp.getValueAsString());
            }
        }
        return aclciFields;
    }
}
