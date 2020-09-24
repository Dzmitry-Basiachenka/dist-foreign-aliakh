package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.Scenario.SalFields;

import com.fasterxml.jackson.core.JsonParseException;

import org.junit.Test;

import java.io.IOException;

/**
 * Verifies {@link SalScenarioFieldsJsonMapper}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 09/24/2020
 *
 * @author Ihar Suvorau
 */
public class SalScenarioFieldsJsonMapperTest {

    private final SalScenarioFieldsJsonMapper jsonMapper = new SalScenarioFieldsJsonMapper();

    @Test
    public void testDeserialize() throws IOException {
        String json = TestUtils.fileToString(this.getClass(), "sal_scenario_fields.json");
        SalFields salFields = jsonMapper.deserialize(json);
        assertEquals("c09ce427-428e-437a-aae9-aed852948683", salFields.getFundPoolId());
    }

    @Test
    public void testSerialize() throws IOException {
        SalFields salFields = new SalFields();
        salFields.setFundPoolId("c09ce427-428e-437a-aae9-aed852948683");
        String actualJson = jsonMapper.serialize(salFields);
        assertEquals(salFields, jsonMapper.deserialize(actualJson));
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
}
