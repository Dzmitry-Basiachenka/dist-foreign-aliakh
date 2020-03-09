package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;

import com.fasterxml.jackson.core.JsonParseException;

import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Verifies {@link AaclScenarioFieldsJsonMapper}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 03/11/2020
 *
 * @author Aliaksandr Liakh
 */
public class AaclScenarioFieldsJsonMapperTest {

    private final AaclScenarioFieldsJsonMapper jsonMapper = new AaclScenarioFieldsJsonMapper();

    private static final String ONE = "1.00";

    @Test
    public void testDeserialize() throws IOException {
        String json = TestUtils.fileToString(this.getClass(), "aacl_scenario_fields.json");
        AaclFields aaclFields = jsonMapper.deserialize(json);
        List<PublicationType> pubTypes = aaclFields.getPublicationTypes();
        assertNotNull(pubTypes);
        assertEquals(buildPublicationType("Book", ONE), pubTypes.get(0));
        assertEquals(buildPublicationType("Business or Trade Journal", "1.50"), pubTypes.get(1));
        assertEquals(buildPublicationType("Consumer Magazine", ONE), pubTypes.get(2));
        assertEquals(buildPublicationType("News Source", "4.00"), pubTypes.get(3));
        assertEquals(buildPublicationType("STMA Journal", "1.10"), pubTypes.get(4));
    }

    @Test
    public void testSerialize() throws IOException {
        AaclFields aaclFields = new AaclFields();
        List<PublicationType> pubTypes = aaclFields.getPublicationTypes();
        aaclFields.setPublicationTypes(pubTypes);
        pubTypes.add(buildPublicationType("Book", ONE));
        pubTypes.add(buildPublicationType("Business or Trade Journal", "1.50"));
        pubTypes.add(buildPublicationType("Consumer Magazine", ONE));
        pubTypes.add(buildPublicationType("News Source", "4.00"));
        pubTypes.add(buildPublicationType("STMA Journal", "1.10"));
        String actualJson = jsonMapper.serialize(aaclFields);
        assertEquals(aaclFields, jsonMapper.deserialize(actualJson));
    }

    @Test
    public void testDeserializeNull() throws IOException {
        assertNull(jsonMapper.deserialize(null));
    }

    @Test
    public void testSerializeNull() throws IOException {
        assertNull(jsonMapper.serialize(null));
    }

    @Test(expected = JsonParseException.class)
    public void testDeserializeException() throws IOException {
        jsonMapper.deserialize("{wrong JSON}");
    }

    private PublicationType buildPublicationType(String name, String weight) {
        PublicationType pubType = new PublicationType();
        pubType.setName(name);
        pubType.setWeight(new BigDecimal(weight));
        return pubType;
    }
}
