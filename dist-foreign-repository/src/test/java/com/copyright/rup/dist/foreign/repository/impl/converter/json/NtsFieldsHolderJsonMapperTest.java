package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.NtsFieldsHolder;

import com.fasterxml.jackson.core.JsonParseException;

import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Verifies {@link NtsFieldsHolderJsonMapper}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/17/2019
 *
 * @author Aliaksandr Liakh
 */
public class NtsFieldsHolderJsonMapperTest {

    private final NtsFieldsHolderJsonMapper jsonMapper = new NtsFieldsHolderJsonMapper();

    @Test
    public void testDeserialize() throws IOException {
        String json = TestUtils.fileToString(this.getClass(), "nts_fields_holder.json");
        NtsFieldsHolder holder = jsonMapper.deserialize(json);
        assertEquals(new BigDecimal("300.00"), holder.getRhMinimumAmount());
    }

    @Test
    public void testSerialize() throws IOException {
        NtsFieldsHolder holder = new NtsFieldsHolder();
        holder.setRhMinimumAmount(new BigDecimal("300.00"));
        String actualJson = jsonMapper.serialize(holder);
        assertEquals(holder, jsonMapper.deserialize(actualJson));
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
