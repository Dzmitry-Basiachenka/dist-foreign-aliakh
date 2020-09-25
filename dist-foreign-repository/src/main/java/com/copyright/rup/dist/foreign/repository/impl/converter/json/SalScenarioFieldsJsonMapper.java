package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.Scenario.SalFields;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

/**
 * Mapper to/from JSON for scenario {@link SalFields}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 09/24/2020
 *
 * @author Ihar Suvorau
 */
public class SalScenarioFieldsJsonMapper {

    private static final ObjectMapper OBJECT_MAPPER = buildObjectMapper();

    private static ObjectMapper buildObjectMapper() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(SalFields.class, new SalScenarioFieldsSerializer());
        simpleModule.addDeserializer(SalFields.class, new SalScenarioFieldsDeserializer());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    /**
     * Serializes an instance of {@link SalFields} into a string JSON.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param salFields the instance of {@link SalFields}
     * @return the string JSON
     * @throws JsonProcessingException if JSON processing failed
     */
    public String serialize(SalFields salFields) throws JsonProcessingException {
        return null != salFields ? OBJECT_MAPPER.writeValueAsString(salFields) : null;
    }

    /**
     * Deserializes a string JSON into an instance of {@link SalFields}.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param json the string JSON
     * @return the instance of {@link SalFields}
     * @throws IOException if JSON processing failed
     */
    public SalFields deserialize(String json) throws IOException {
        return null != json ? OBJECT_MAPPER.readValue(json, SalFields.class) : null;
    }
}
