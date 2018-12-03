package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.FundPool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

/**
 * Mapper to/from JSON for {@link FundPool}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 12/03/2018
 *
 * @author Aliaksandr Liakh
 */
public class FundPoolJsonMapper {

    private static final ObjectMapper OBJECT_MAPPER = buildObjectMapper();

    private static ObjectMapper buildObjectMapper() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(FundPool.class, new FundPoolSerializer());
        simpleModule.addDeserializer(FundPool.class, new FundPoolDeserializer());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    /**
     * Serializes an instance of {@link FundPool} into a string JSON.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param fundPool the instance of {@link FundPool}
     * @return the string JSON
     * @throws JsonProcessingException if JSON processing failed
     */
    public String serialize(FundPool fundPool) throws JsonProcessingException {
        return null != fundPool ? OBJECT_MAPPER.writeValueAsString(fundPool) : null;
    }

    /**
     * Deserializes a string JSON into an instance of {@link FundPool}.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param json the string JSON
     * @return the instance of {@link FundPool}
     * @throws IOException if JSON processing failed
     */
    public FundPool deserialize(String json) throws IOException {
        return null != json ? OBJECT_MAPPER.readValue(json, FundPool.class) : null;
    }
}
