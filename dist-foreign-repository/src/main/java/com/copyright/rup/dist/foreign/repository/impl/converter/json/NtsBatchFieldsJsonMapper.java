package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.UsageBatch.NtsFields;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

/**
 * Mapper to/from JSON for {@link NtsFields}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 12/03/2018
 *
 * @author Aliaksandr Liakh
 */
public class NtsBatchFieldsJsonMapper {

    private static final ObjectMapper OBJECT_MAPPER = buildObjectMapper();

    private static ObjectMapper buildObjectMapper() {
        var simpleModule = new SimpleModule();
        simpleModule.addSerializer(NtsFields.class, new NtsBatchFieldsSerializer());
        simpleModule.addDeserializer(NtsFields.class, new NtsBatchFieldsDeserializer());
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    /**
     * Serializes an instance of {@link NtsFields} into a string JSON.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param ntsFields the instance of {@link NtsFields}
     * @return the string JSON
     * @throws JsonProcessingException if JSON processing failed
     */
    public String serialize(NtsFields ntsFields) throws JsonProcessingException {
        return null != ntsFields ? OBJECT_MAPPER.writeValueAsString(ntsFields) : null;
    }

    /**
     * Deserializes a string JSON into an instance of {@link NtsFields}.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param json the string JSON
     * @return the instance of {@link NtsFields}
     * @throws IOException if JSON processing failed
     */
    public NtsFields deserialize(String json) throws IOException {
        return null != json ? OBJECT_MAPPER.readValue(json, NtsFields.class) : null;
    }
}
