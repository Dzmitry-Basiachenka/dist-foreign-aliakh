package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

/**
 * Verifies {@link AclGrantSetPeriodsJsonMapper}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/26/2022
 *
 * @author Aliaksandr Liakh
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
        Set<Integer> periods = buildPeriods();
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

    private Set<Integer> buildPeriods() {
        return Sets.newHashSet(202106, 202112);
    }
}
