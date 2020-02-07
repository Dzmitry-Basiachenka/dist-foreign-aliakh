package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.NtsFundPool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

/**
 * Mapper to/from JSON for {@link NtsFundPool}.
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
        simpleModule.addSerializer(NtsFundPool.class, new FundPoolSerializer());
        simpleModule.addDeserializer(NtsFundPool.class, new FundPoolDeserializer());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    /**
     * Serializes an instance of {@link NtsFundPool} into a string JSON.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param ntsFundPool the instance of {@link NtsFundPool}
     * @return the string JSON
     * @throws JsonProcessingException if JSON processing failed
     */
    public String serialize(NtsFundPool ntsFundPool) throws JsonProcessingException {
        return null != ntsFundPool ? OBJECT_MAPPER.writeValueAsString(ntsFundPool) : null;
    }

    /**
     * Deserializes a string JSON into an instance of {@link NtsFundPool}.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param json the string JSON
     * @return the instance of {@link NtsFundPool}
     * @throws IOException if JSON processing failed
     */
    public NtsFundPool deserialize(String json) throws IOException {
        return null != json ? OBJECT_MAPPER.readValue(json, NtsFundPool.class) : null;
    }
}
