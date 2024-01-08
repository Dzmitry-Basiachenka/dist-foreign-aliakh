package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.Scenario.NtsFields;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

/**
 * Mapper to/from JSON for scenario {@link NtsFields}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/17/2019
 *
 * @author Aliaksandr Liakh
 */
public class NtsScenarioFieldsJsonMapper {

    private static final ObjectMapper OBJECT_MAPPER = buildObjectMapper();

    private static ObjectMapper buildObjectMapper() {
        var simpleModule = new SimpleModule();
        simpleModule.addSerializer(NtsFields.class, new NtsScenarioFieldsSerializer());
        simpleModule.addDeserializer(NtsFields.class, new NtsScenarioFieldsDeserializer());
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
