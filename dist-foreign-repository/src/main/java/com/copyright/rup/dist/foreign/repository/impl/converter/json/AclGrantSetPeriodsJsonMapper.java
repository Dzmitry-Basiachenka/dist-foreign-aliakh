package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.List;

/**
 * Mapper to/from JSON for {@link List} of ACL grant set periods.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/26/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclGrantSetPeriodsJsonMapper {

    private static final ObjectMapper OBJECT_MAPPER = buildObjectMapper();
    private static final TypeReference<List<Integer>> TYPE_REFERENCE = new TypeReference<List<Integer>>() {
    };

    private static ObjectMapper buildObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new SimpleModule());
        return objectMapper;
    }

    /**
     * Serializes an instance of {@link List} of ACL grant set periods into a string JSON.
     *
     * @param periods the instance of {@link List} of ACL grant set periods
     * @return the string JSON
     * @throws JsonProcessingException if JSON processing failed
     */
    public String serialize(List<Integer> periods) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(periods);
    }

    /**
     * Deserializes a string JSON into an instance of {@link List} of ACL grant set periods.
     *
     * @param json the string JSON
     * @return the instance of {@link List} of ACL grant set periods
     * @throws IOException if JSON processing failed
     */
    public List<Integer> deserialize(String json) throws IOException {
        return OBJECT_MAPPER.readValue(json, TYPE_REFERENCE);
    }
}
