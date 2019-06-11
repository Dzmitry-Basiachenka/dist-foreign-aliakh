package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.Scenario.NtsFields;

import com.fasterxml.jackson.core.JsonParseException;

import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Verifies {@link NtsFieldsJsonMapper}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/17/2019
 *
 * @author Aliaksandr Liakh
 */
public class NtsFieldsJsonMapperTest {

    private final NtsFieldsJsonMapper jsonMapper = new NtsFieldsJsonMapper();

    @Test
    public void testDeserialize() throws IOException {
        String json = TestUtils.fileToString(this.getClass(), "nts_fields.json");
        NtsFields ntsFields = jsonMapper.deserialize(json);
        assertEquals(new BigDecimal("300.00"), ntsFields.getRhMinimumAmount());
        assertEquals(new BigDecimal("500.00"), ntsFields.getPreServiceFeeAmount());
        assertEquals(new BigDecimal("1000.00"), ntsFields.getPostServiceFeeAmount());
        assertEquals("3d34ebf4-7b39-4477-be8d-515a53dd59c1", ntsFields.getPreServiceFeeFundId());
        assertEquals(new BigDecimal("0.00"), ntsFields.getPreServiceFeeFundTotal());
        assertNull(ntsFields.getPreServiceFeeFundName());
    }

    @Test
    public void testSerialize() throws IOException {
        NtsFields ntsFields = new NtsFields();
        ntsFields.setRhMinimumAmount(new BigDecimal("300.00"));
        ntsFields.setPreServiceFeeAmount(new BigDecimal("500.00"));
        ntsFields.setPostServiceFeeAmount(new BigDecimal("1000.00"));
        ntsFields.setPreServiceFeeFundId("3d34ebf4-7b39-4477-be8d-515a53dd59c1");
        String actualJson = jsonMapper.serialize(ntsFields);
        assertEquals(ntsFields, jsonMapper.deserialize(actualJson));
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
