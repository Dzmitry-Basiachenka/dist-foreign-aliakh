package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.domain.Scenario.NtsFields;
import com.copyright.rup.dist.foreign.repository.impl.converter.json.common.CommonJsonFieldsDeserializer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import org.slf4j.Logger;

import java.io.IOException;

/**
 * Implementation of {@link CommonJsonFieldsDeserializer} for scenario {@link NtsFields}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/17/2019
 *
 * @author Aliaksandr Liakh
 */
public class NtsScenarioFieldsDeserializer extends CommonJsonFieldsDeserializer<NtsFields> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    /**
     * Constructor.
     */
    NtsScenarioFieldsDeserializer() {
        super(NtsFields.class);
    }

    @Override
    public NtsFields deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        NtsFields ntsFields = new NtsFields();
        try {
            JsonNode jsonNode = parser.readValueAsTree();
            ntsFields.setRhMinimumAmount(getBigDecimalValue(jsonNode.get("rh_minimum_amount")));
            ntsFields.setPreServiceFeeAmount(getBigDecimalValue(jsonNode.get("pre_service_fee_amount")));
            ntsFields.setPostServiceFeeAmount(getBigDecimalValue(jsonNode.get("post_service_fee_amount")));
            ntsFields.setPreServiceFeeFundId(getStringValue(jsonNode.get("pre_service_fee_fund_uid")));
        } catch (JsonParseException e) {
            LOGGER.warn("Deserialize NTS scenario fields. Failed", e);
        }
        return ntsFields;
    }
}
