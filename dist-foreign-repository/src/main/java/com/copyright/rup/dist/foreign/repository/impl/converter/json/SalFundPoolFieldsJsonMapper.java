package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.FundPool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.Objects;

/**
 * Mapper to/from JSON for {@link FundPool.SalFields}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/23/2020
 *
 * @author Uladzislau Shalamitski
 */
public class SalFundPoolFieldsJsonMapper {

    private static final ObjectMapper OBJECT_MAPPER = buildObjectMapper();

    private static ObjectMapper buildObjectMapper() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(FundPool.SalFields.class, new SalFundPoolFieldsSerializer());
        simpleModule.addDeserializer(FundPool.SalFields.class, new SalFundPoolFieldsDeserializer());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    /**
     * Serializes an instance of {@link FundPool.SalFields} into a string JSON.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param salFields the instance of {@link FundPool.SalFields}
     * @return the string JSON
     * @throws JsonProcessingException if JSON processing failed
     */
    public String serialize(FundPool.SalFields salFields) throws JsonProcessingException {
        return Objects.nonNull(salFields) ? OBJECT_MAPPER.writeValueAsString(salFields) : null;
    }

    /**
     * Deserializes a string JSON into an instance of {@link FundPool.SalFields}.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param json the string JSON
     * @return the instance of {@link FundPool.SalFields}
     * @throws IOException if JSON processing failed
     */
    public FundPool.SalFields deserialize(String json) throws IOException {
        return Objects.nonNull(json) ? OBJECT_MAPPER.readValue(json, FundPool.SalFields.class) : null;
    }
}
