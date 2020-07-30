package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.UsageBatch.SalFields;

import com.fasterxml.jackson.core.JsonParseException;

import org.junit.Test;

import java.io.IOException;

/**
 * Verifies {@link SalBatchFieldsJsonMapper}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/30/2020
 *
 * @author Aliaksandr Liakh
 */
public class SalBatchFieldsJsonMapperTest {

    private final SalBatchFieldsJsonMapper jsonMapper = new SalBatchFieldsJsonMapper();

    @Test
    public void testDeserialize() throws IOException {
        String json = TestUtils.fileToString(this.getClass(), "sal_batch_fields.json");
        SalFields salFields = jsonMapper.deserialize(json);
        assertEquals(7001293454L, salFields.getLicenseeAccountNumber().longValue());
        assertEquals("Synergy Publishers", salFields.getLicenseeName());
    }

    @Test
    public void testSerialize() throws IOException {
        SalFields salFields = new SalFields();
        salFields.setLicenseeAccountNumber(7001293454L);
        salFields.setLicenseeName("Synergy Publishers");
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
