package com.copyright.rup.dist.foreign.service.impl.rights;

import com.copyright.rup.dist.common.integration.camel.CommonMarshaller;
import com.copyright.rup.dist.foreign.domain.Usage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * Json marshaller for handling messages for usage matching.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/12/2018
 *
 * @author Ihar Suvorau
 */
@Component("df.service.rightsMarshaller")
public class RightsMarshaller extends CommonMarshaller {

    /**
     * Usages marshaller {@link TypeReference}.
     */
    static final TypeReference<Usage> TYPE_REFERENCE = new TypeReference<Usage>() {
    };

    @Override
    protected ObjectMapper getObjectMapper() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(new RightsSerializer());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    @Override
    public Object unmarshal(Exchange exchange, InputStream stream) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected TypeReference<?> getTypeReference() {
        return TYPE_REFERENCE;
    }
}
