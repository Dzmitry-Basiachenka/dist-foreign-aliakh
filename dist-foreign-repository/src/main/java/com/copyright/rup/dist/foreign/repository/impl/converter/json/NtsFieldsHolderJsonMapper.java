package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.NtsFieldsHolder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

/**
 * Mapper to/from JSON for {@link NtsFieldsHolder}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/17/2019
 *
 * @author Aliaksandr Liakh
 */
public class NtsFieldsHolderJsonMapper {

    private static final ObjectMapper OBJECT_MAPPER = buildObjectMapper();

    private static ObjectMapper buildObjectMapper() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(NtsFieldsHolder.class, new NtsFieldsHolderSerializer());
        simpleModule.addDeserializer(NtsFieldsHolder.class, new NtsFieldsHolderDeserializer());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    /**
     * Serializes an instance of {@link NtsFieldsHolder} into a string JSON.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param holder the instance of {@link NtsFieldsHolder}
     * @return the string JSON
     * @throws JsonProcessingException if JSON processing failed
     */
    public String serialize(NtsFieldsHolder holder) throws JsonProcessingException {
        return null != holder ? OBJECT_MAPPER.writeValueAsString(holder) : null;
    }

    /**
     * Deserializes a string JSON into an instance of {@link NtsFieldsHolder}.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param json the string JSON
     * @return the instance of {@link NtsFieldsHolder}
     * @throws IOException if JSON processing failed
     */
    public NtsFieldsHolder deserialize(String json) throws IOException {
        return null != json ? OBJECT_MAPPER.readValue(json, NtsFieldsHolder.class) : null;
    }
}
