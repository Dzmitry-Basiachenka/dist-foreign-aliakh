package com.copyright.rup.dist.foreign.service.impl.common;

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
 * Common JSON unmarshaller to convert Camel messages to list of {@link T}s.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/13/2018
 *
 * @param <T> type of item to unmarshall
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
 */
public class FdaCommonUnmarshaller<T> extends CommonMarshaller {

    private final TypeReference<List<T>> typeReference = new TypeReference<List<T>>() {
    };

    private JsonDeserializer<List<Usage>> deserializer;

    public void setDeserializer(JsonDeserializer<List<Usage>> deserializer) {
        this.deserializer = deserializer;
    }

    @Override
    public void marshal(Exchange exchange, Object graph, OutputStream stream) {
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
    protected TypeReference<List<T>> getTypeReference() {
        return typeReference;
    }
}
