package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;
import com.copyright.rup.dist.foreign.domain.UsageAge;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.List;

/**
 * Implementation of {@link StdSerializer} for scenario {@link AaclFields}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 03/11/2020
 *
 * @author Aliaksandr Liakh
 */
public class AaclScenarioFieldsSerializer extends StdSerializer<AaclFields> {

    private static final long serialVersionUID = 2309613724167900666L;

    /**
     * Constructor.
     */
    AaclScenarioFieldsSerializer() {
        super(AaclFields.class);
    }

    @Override
    public void serialize(AaclFields aaclFields, JsonGenerator jg, SerializerProvider provider) throws IOException {
        jg.writeStartObject();
        jg.writeStringField("fund_pool_uid", aaclFields.getFundPoolId());
        jg.writeArrayFieldStart("publicationTypes");
        List<PublicationType> publicationTypes = aaclFields.getPublicationTypes();
        for (PublicationType publicationType : publicationTypes) {
            jg.writeStartObject();
            jg.writeStringField("id", publicationType.getId());
            jg.writeNumberField("weight", publicationType.getWeight());
            jg.writeEndObject();
        }
        jg.writeEndArray();
        jg.writeArrayFieldStart("usageAges");
        List<UsageAge> usageAges = aaclFields.getUsageAges();
        for (UsageAge usageAge : usageAges) {
            jg.writeStartObject();
            jg.writeNumberField("period", usageAge.getPeriod());
            jg.writeNumberField("weight", usageAge.getWeight());
            jg.writeEndObject();
        }
        jg.writeEndArray();
        jg.writeArrayFieldStart("detailLicenseeClasses");
        List<DetailLicenseeClass> detailLicenseeClasses = aaclFields.getDetailLicenseeClasses();
        for (DetailLicenseeClass detailLicenseeClass : detailLicenseeClasses) {
            jg.writeStartObject();
            jg.writeNumberField("detailLicenseeClassId", detailLicenseeClass.getId());
            jg.writeNumberField("aggregateLicenseeClassId", detailLicenseeClass.getAggregateLicenseeClass().getId());
            jg.writeEndObject();
        }
        jg.writeEndArray();
        jg.writeEndObject();
    }
}
