package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.repository.impl.converter.json.common.CommonJsonFieldsDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link CommonJsonFieldsDeserializer} for scenario {@link AaclFields}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 03/10/2020
 *
 * @author Aliaksandr Liakh
 */
public class AaclScenarioFieldsDeserializer extends CommonJsonFieldsDeserializer<AaclFields> {

    private static final long serialVersionUID = -8585512689637489777L;
    private static final String FUND_POOL_ID = "fund_pool_uid";
    private static final String PUBLICATION_TYPES = "publicationTypes";
    private static final String USAGE_AGES = "usageAges";
    private static final String ID = "id";
    private static final String PERIOD = "period";
    private static final String WEIGHT = "weight";
    private static final String DETAIL_LICENSEE_CLASSES = "detailLicenseeClasses";
    private static final String DETAIL_LICENSEE_CLASS_ID = "detailLicenseeClassId";
    private static final String AGGREGATE_LICENSEE_CLASS_ID = "aggregateLicenseeClassId";

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
            if (JsonToken.VALUE_STRING == currentToken && FUND_POOL_ID.equals(jp.getCurrentName())) {
                aaclFields.setFundPoolId(jp.getValueAsString());
            } else if (JsonToken.START_ARRAY == currentToken && PUBLICATION_TYPES.equals(jp.getCurrentName())) {
                aaclFields.setPublicationTypes(readPubTypes(jp));
            } else if (JsonToken.START_ARRAY == currentToken && USAGE_AGES.equals(jp.getCurrentName())) {
                aaclFields.setUsageAges(readUsageAges(jp));
            } else if (JsonToken.START_ARRAY == currentToken && DETAIL_LICENSEE_CLASSES.equals(jp.getCurrentName())) {
                aaclFields.setDetailLicenseeClasses(readDetailLicenseeClasses(jp));
            }
        }
        return aaclFields;
    }

    private List<PublicationType> readPubTypes(JsonParser jp) throws IOException {
        PublicationType pubType = new PublicationType();
        List<PublicationType> pubTypes = new ArrayList<>();
        while (jp.nextToken() != JsonToken.END_ARRAY) {
            if (JsonToken.START_OBJECT == jp.currentToken()) {
                pubType = new PublicationType();
                pubTypes.add(pubType);
            } else if (JsonToken.VALUE_STRING == jp.currentToken() && ID.equals(jp.getCurrentName())) {
                pubType.setId(jp.getValueAsString());
            } else if (JsonToken.VALUE_NUMBER_FLOAT == jp.currentToken() && WEIGHT.equals(jp.getCurrentName())) {
                pubType.setWeight(jp.getDecimalValue());
            }
        }
        return pubTypes;
    }

    private List<UsageAge> readUsageAges(JsonParser jp) throws IOException {
        // Workaround for https://sourceforge.net/p/findbugs/bugs/1373
        UsageAge usageAge = null;
        List<UsageAge> usageAges = new ArrayList<>();
        while (jp.nextToken() != JsonToken.END_ARRAY) {
            if (JsonToken.START_OBJECT == jp.currentToken()) {
                usageAge = new UsageAge();
                usageAges.add(usageAge);
            } else if (JsonToken.VALUE_NUMBER_INT == jp.currentToken() && PERIOD.equals(jp.getCurrentName())) {
                usageAge.setPeriod(jp.getValueAsInt());
            } else if (JsonToken.VALUE_NUMBER_FLOAT == jp.currentToken() && WEIGHT.equals(jp.getCurrentName())) {
                usageAge.setWeight(jp.getDecimalValue());
            }
        }
        return usageAges;
    }

    private List<DetailLicenseeClass> readDetailLicenseeClasses(JsonParser jp) throws IOException {
        List<DetailLicenseeClass> detailLicenseeClasses = new ArrayList<>();
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        while (jp.nextToken() != JsonToken.END_ARRAY) {
            if (JsonToken.START_OBJECT == jp.currentToken()) {
                detailLicenseeClass = new DetailLicenseeClass();
                detailLicenseeClasses.add(detailLicenseeClass);
            } else if (JsonToken.VALUE_NUMBER_INT == jp.currentToken()
                && DETAIL_LICENSEE_CLASS_ID.equals(jp.getCurrentName())) {
                detailLicenseeClass.setId(jp.getValueAsInt());
            } else if (JsonToken.VALUE_NUMBER_INT == jp.currentToken()
                && AGGREGATE_LICENSEE_CLASS_ID.equals(jp.getCurrentName())) {
                detailLicenseeClass.getAggregateLicenseeClass().setId(jp.getValueAsInt());
            }
        }
        return detailLicenseeClasses;
    }
}
