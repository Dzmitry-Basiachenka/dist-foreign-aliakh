package com.copyright.rup.dist.foreign.service.impl.usage.paid;

import com.copyright.rup.dist.common.integration.camel.CommonMarshaller;
import com.copyright.rup.dist.foreign.domain.PaidUsage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.util.List;

/**
 * Paid usages unmarshaller to consume {@link PaidUsage}s from LM.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 02/21/18
 *
 * @author Darya Baraukova
 */
@Component("df.service.paidUsageUnmarshaller")
public class PaidUsageUnmarshaller extends CommonMarshaller {

    private static final TypeReference<List<PaidUsage>> TYPE_REFERENCE =
        new TypeReference<List<PaidUsage>>() {
        };

    @Override
    public void marshal(Exchange exchange, Object graph, OutputStream stream) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected ObjectMapper getObjectMapper() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(List.class, new PaidUsageDeserializer());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    @Override
    protected TypeReference<List<PaidUsage>> getTypeReference() {
        return TYPE_REFERENCE;
    }
}
