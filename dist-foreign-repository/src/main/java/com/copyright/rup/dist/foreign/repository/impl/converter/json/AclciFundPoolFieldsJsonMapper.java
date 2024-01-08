package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.FundPool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.Objects;

/**
 * Mapper to/from JSON for {@link FundPool.AclciFields}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/28/2022
 *
 * @author Mikita Maistrenka
 */
public class AclciFundPoolFieldsJsonMapper {

    private static final ObjectMapper OBJECT_MAPPER = buildObjectMapper();

    private static ObjectMapper buildObjectMapper() {
        var simpleModule = new SimpleModule();
        simpleModule.addSerializer(FundPool.AclciFields.class, new AclciFundPoolFieldsSerializer());
        simpleModule.addDeserializer(FundPool.AclciFields.class, new AclciFundPoolFieldsDeserializer());
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    /**
     * Serializes an instance of {@link FundPool.AclciFields} into a string JSON.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param aclciFields the instance of {@link FundPool.AclciFields}
     * @return the string JSON
     * @throws JsonProcessingException if JSON processing failed
     */
    public String serialize(FundPool.AclciFields aclciFields) throws JsonProcessingException {
        return Objects.nonNull(aclciFields) ? OBJECT_MAPPER.writeValueAsString(aclciFields) : null;
    }

    /**
     * Deserializes a string JSON into an instance of {@link FundPool.AclciFields}.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param json the string JSON
     * @return the instance of {@link FundPool.AclciFields}
     * @throws IOException if JSON processing failed
     */
    public FundPool.AclciFields deserialize(String json) throws IOException {
        return Objects.nonNull(json) ? OBJECT_MAPPER.readValue(json, FundPool.AclciFields.class) : null;
    }
}
