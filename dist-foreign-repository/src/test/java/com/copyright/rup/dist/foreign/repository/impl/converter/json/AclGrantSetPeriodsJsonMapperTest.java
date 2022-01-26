package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Verifies {@link AclGrantSetPeriodsJsonMapper}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/26/2022
 *
 * @author Aliaksandr Liakhki
 */
public class AclGrantSetPeriodsJsonMapperTest {

    private final AclGrantSetPeriodsJsonMapper jsonMapper = new AclGrantSetPeriodsJsonMapper();

    @Test
    public void testDeserialize() throws IOException {
        String json = "[202106,202112]";
        assertEquals(buildPeriods(), jsonMapper.deserialize(json));
    }

    @Test
    public void testSerialize() throws IOException {
        List<Integer> periods = buildPeriods();
        String actualJson = jsonMapper.serialize(periods);
        assertEquals(periods, jsonMapper.deserialize(actualJson));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeserializeNull() throws IOException {
        assertNull(jsonMapper.deserialize(null));
    }

    @Test(expected = AssertionError.class)
    public void testSerializeNull() throws IOException {
        assertNull(jsonMapper.serialize(null));
    }

    @Test(expected = JsonProcessingException.class)
    public void testDeserializeException() throws IOException {
        jsonMapper.deserialize("{wrong JSON}");
    }

    private List<Integer> buildPeriods() {
        return Arrays.asList(202106, 202112);
    }
}
