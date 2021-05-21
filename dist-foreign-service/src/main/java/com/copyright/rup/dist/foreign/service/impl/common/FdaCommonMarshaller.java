package com.copyright.rup.dist.foreign.service.impl.common;

import com.copyright.rup.dist.common.integration.camel.CommonMarshaller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.apache.camel.Exchange;

import java.io.InputStream;
import java.util.List;

/**
 * Common JSON marshaller to convert Camel messages from list of {@link T}s.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/12/2018
 *
 * @param <T> type of item to marshall
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
 */
public class FdaCommonMarshaller<T> extends CommonMarshaller {

    private StdSerializer<List<T>> serializer;
    private final TypeReference<List<T>> typeReference = new TypeReference<List<T>>() {
    };

    public void setSerializer(StdSerializer<List<T>> serializer) {
        this.serializer = serializer;
    }

    @Override
    public Object unmarshal(Exchange exchange, InputStream stream) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected ObjectMapper getObjectMapper() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(serializer);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    @Override
    protected TypeReference<?> getTypeReference() {
        return typeReference;
    }
}
