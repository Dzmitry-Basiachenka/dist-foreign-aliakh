package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.UsageBatch.AclciFields;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

/**
 * Mapper to/from JSON for {@link AclciFields}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/08/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclciBatchFieldsJsonMapper {

    private static final ObjectMapper OBJECT_MAPPER = buildObjectMapper();

    private static ObjectMapper buildObjectMapper() {
        var simpleModule = new SimpleModule();
        simpleModule.addSerializer(AclciFields.class, new AclciBatchFieldsSerializer());
        simpleModule.addDeserializer(AclciFields.class, new AclciBatchFieldsDeserializer());
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    /**
     * Serializes an instance of {@link AclciFields} into a string JSON.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param aclciFields the instance of {@link AclciFields}
     * @return the string JSON
     * @throws JsonProcessingException if JSON processing failed
     */
    public String serialize(AclciFields aclciFields) throws JsonProcessingException {
        return null != aclciFields ? OBJECT_MAPPER.writeValueAsString(aclciFields) : null;
    }

    /**
     * Deserializes a string JSON into an instance of {@link AclciFields}.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param json the string JSON
     * @return the instance of {@link AclciFields}
     * @throws IOException if JSON processing failed
     */
    public AclciFields deserialize(String json) throws IOException {
        return null != json ? OBJECT_MAPPER.readValue(json, AclciFields.class) : null;
    }
}
