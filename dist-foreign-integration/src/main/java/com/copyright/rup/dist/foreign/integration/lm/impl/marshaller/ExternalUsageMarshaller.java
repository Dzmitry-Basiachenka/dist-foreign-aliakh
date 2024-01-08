package com.copyright.rup.dist.foreign.integration.lm.impl.marshaller;

import com.copyright.rup.dist.common.integration.camel.CommonMarshaller;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsage;
import com.copyright.rup.dist.foreign.integration.lm.impl.serializer.ExternalUsageSerializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * External usages marshaller to send {@link ExternalUsage}s to LM.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/11/18
 *
 * @author Ihar Suvorau
 */
@Component("df.integration.externalUsageMarshaller")
public class ExternalUsageMarshaller extends CommonMarshaller {

    /**
     * Usages marshaller {@link TypeReference}.
     */
    static final TypeReference<ExternalUsage> TYPE_REFERENCE = new TypeReference<ExternalUsage>() {
    };

    @Override
    protected ObjectMapper getObjectMapper() {
        var simpleModule = new SimpleModule();
        simpleModule.addSerializer(new ExternalUsageSerializer());
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    @Override
    public Object unmarshal(Exchange exchange, InputStream stream) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected TypeReference<?> getTypeReference() {
        return TYPE_REFERENCE;
    }
}
