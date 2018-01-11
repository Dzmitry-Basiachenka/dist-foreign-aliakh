package com.copyright.rup.dist.foreign.integration.lm.impl.marshaller;

import com.copyright.rup.dist.common.integration.camel.CommonMarshaller;
import com.copyright.rup.dist.foreign.domain.LiabilityDetail;
import com.copyright.rup.dist.foreign.integration.lm.impl.serializer.LiabilityDetailSerializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * Liability detail marshaller to send {@link LiabilityDetail}s to LM.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/11/18
 *
 * @author Ihar Suvorau
 */
@Component("df.integration.liabilityDetailsMarshaller")
public class LiabilityDetailsMarshaller extends CommonMarshaller {

    /**
     * Usages marshaller {@link TypeReference}.
     */
    static final TypeReference<LiabilityDetail> TYPE_REFERENCE = new TypeReference<LiabilityDetail>() {
    };

    @Override
    protected ObjectMapper getObjectMapper() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(new LiabilityDetailSerializer());
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
