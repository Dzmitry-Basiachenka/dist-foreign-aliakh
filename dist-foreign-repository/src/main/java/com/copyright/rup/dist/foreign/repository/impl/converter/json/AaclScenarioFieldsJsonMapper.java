package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

/**
 * Mapper to/from JSON for scenario {@link AaclFields}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 03/11/2020
 *
 * @author Aliaksandr Liakh
 */
public class AaclScenarioFieldsJsonMapper {

    private static final ObjectMapper OBJECT_MAPPER = buildObjectMapper();

    private static ObjectMapper buildObjectMapper() {
        var simpleModule = new SimpleModule();
        simpleModule.addSerializer(AaclFields.class, new AaclScenarioFieldsSerializer());
        simpleModule.addDeserializer(AaclFields.class, new AaclScenarioFieldsDeserializer());
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    /**
     * Serializes an instance of {@link AaclFields} into a string JSON.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param aaclFields the instance of {@link AaclFields}
     * @return the string JSON
     * @throws JsonProcessingException if JSON processing failed
     */
    public String serialize(AaclFields aaclFields) throws JsonProcessingException {
        return null != aaclFields ? OBJECT_MAPPER.writeValueAsString(aaclFields) : null;
    }

    /**
     * Deserializes a string JSON into an instance of {@link AaclFields}.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param json the string JSON
     * @return the instance of {@link AaclFields}
     * @throws IOException if JSON processing failed
     */
    public AaclFields deserialize(String json) throws IOException {
        return null != json ? OBJECT_MAPPER.readValue(json, AaclFields.class) : null;
    }
}
