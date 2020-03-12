package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.List;

/**
 * Implementation of {@link StdDeserializer} for scenario {@link AaclFields}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 03/10/2020
 *
 * @author Aliaksandr Liakh
 */
// TODO {aliakh} read Usage Age Weights, Licensee Classes mapping and other fields specific for AACL scenario
public class AaclScenarioFieldsDeserializer extends StdDeserializer<AaclFields> {

    private static final String TITLE_CUTOFF_AMOUNT = "title_cutoff_amount";
    private static final String FUND_POOL_ID = "fund_pool_uid";
    private static final String PUBLICATION_TYPES = "publicationTypes";
    private static final String NAME = "name";
    private static final String WEIGHT = "weight";

    /**
     * Constructor.
     */
    AaclScenarioFieldsDeserializer() {
        super(AaclFields.class);
    }

    @Override
    public AaclFields deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        AaclFields aaclFields = new AaclFields();
        JsonToken currentToken;
        while (null != (currentToken = jp.nextValue())) {
            if ((JsonToken.VALUE_NUMBER_INT == currentToken || JsonToken.VALUE_NUMBER_FLOAT == currentToken)
                && TITLE_CUTOFF_AMOUNT.equals(jp.getCurrentName())) {
                aaclFields.setTitleCutoffAmount(jp.getDecimalValue());
            } else if (JsonToken.VALUE_STRING == currentToken && FUND_POOL_ID.equals(jp.getCurrentName())) {
                aaclFields.setFundPoolId(jp.getValueAsString());
            } else if (JsonToken.START_ARRAY == currentToken && PUBLICATION_TYPES.equals(jp.getCurrentName())) {
                readPubTypes(jp, aaclFields.getPublicationTypes());
            }
        }
        return aaclFields;
    }

    private void readPubTypes(JsonParser jp, List<PublicationType> pubTypes) throws IOException {
        PublicationType pubType = new PublicationType();
        while (jp.nextToken() != JsonToken.END_ARRAY) {
            if (JsonToken.START_OBJECT == jp.currentToken()) {
                pubType = new PublicationType();
                pubTypes.add(pubType);
            } else if (JsonToken.VALUE_STRING == jp.currentToken()
                && NAME.equals(jp.getCurrentName())) {
                pubType.setName(jp.getValueAsString());
            } else if (JsonToken.VALUE_NUMBER_FLOAT == jp.currentToken()
                && WEIGHT.equals(jp.getCurrentName())) {
                pubType.setWeight(jp.getDecimalValue());
            }
        }
    }
}
