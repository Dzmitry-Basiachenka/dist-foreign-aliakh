package com.copyright.rup.dist.foreign.service.impl.common.chunk;

import com.copyright.rup.dist.common.integration.camel.CommonMarshaller;
import com.copyright.rup.dist.foreign.domain.Usage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.apache.camel.Exchange;

import java.io.OutputStream;
import java.util.List;

/**
 * Common JSON unmarshaller to convert Camel messages to list of {@link Usage}s.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/13/2018
 *
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
 */
public class CommonUsageChunkUnmarshaller extends CommonMarshaller {

    private static final TypeReference<List<Usage>> TYPE_REFERENCE = new TypeReference<List<Usage>>() {
    };

    private JsonDeserializer<List<Usage>> deserializer;

    public void setDeserializer(JsonDeserializer<List<Usage>> deserializer) {
        this.deserializer = deserializer;
    }

    @Override
    public void marshal(Exchange exchange, Object graph, OutputStream stream) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected ObjectMapper getObjectMapper() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(List.class, deserializer);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    @Override
    protected TypeReference<List<Usage>> getTypeReference() {
        return TYPE_REFERENCE;
    }
}
