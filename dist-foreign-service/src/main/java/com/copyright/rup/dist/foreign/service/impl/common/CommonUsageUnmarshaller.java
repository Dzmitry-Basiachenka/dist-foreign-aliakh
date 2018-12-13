package com.copyright.rup.dist.foreign.service.impl.common;

import com.copyright.rup.dist.common.integration.camel.CommonMarshaller;
import com.copyright.rup.dist.foreign.domain.Usage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.camel.Exchange;

import java.io.OutputStream;

/**
 * Common json unmarshaller for handling messages for usages.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/13/2018
 *
 * @author Uladzislau Shalamitski
 */
public class CommonUsageUnmarshaller extends CommonMarshaller {

    private static final TypeReference<Usage> TYPE_REFERENCE = new TypeReference<Usage>() {
    };

    private JsonDeserializer<Usage> deserializer;

    public void setDeserializer(JsonDeserializer<Usage> deserializer) {
        this.deserializer = deserializer;
    }

    @Override
    public void marshal(Exchange exchange, Object graph, OutputStream stream) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected ObjectMapper getObjectMapper() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(Usage.class, deserializer);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    @Override
    protected TypeReference<Usage> getTypeReference() {
        return TYPE_REFERENCE;
    }
}
