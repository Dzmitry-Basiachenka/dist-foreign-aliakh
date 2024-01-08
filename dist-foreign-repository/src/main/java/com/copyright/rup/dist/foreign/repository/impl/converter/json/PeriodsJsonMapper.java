package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.Set;

/**
 * Mapper to/from JSON for {@link Set} of periods.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/26/2022
 *
 * @author Aliaksandr Liakh
 */
public class PeriodsJsonMapper {

    private static final ObjectMapper OBJECT_MAPPER = buildObjectMapper();
    private static final TypeReference<Set<Integer>> TYPE_REFERENCE = new TypeReference<Set<Integer>>() {};

    private static ObjectMapper buildObjectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new SimpleModule());
        return objectMapper;
    }

    /**
     * Serializes an instance of {@link Set} of ACL grant set periods into a string JSON.
     *
     * @param periods the instance of {@link Set} of ACL grant set periods
     * @return the string JSON
     * @throws JsonProcessingException if JSON processing failed
     */
    public String serialize(Set<Integer> periods) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(periods);
    }

    /**
     * Deserializes a string JSON into an instance of {@link Set} of ACL grant set periods.
     *
     * @param json the string JSON
     * @return the instance of {@link Set} of ACL grant set periods
     * @throws IOException if JSON processing failed
     */
    public Set<Integer> deserialize(String json) throws IOException {
        return OBJECT_MAPPER.readValue(json, TYPE_REFERENCE);
    }
}
