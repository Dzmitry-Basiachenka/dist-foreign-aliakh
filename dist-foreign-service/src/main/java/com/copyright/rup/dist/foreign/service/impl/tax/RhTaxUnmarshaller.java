package com.copyright.rup.dist.foreign.service.impl.tax;

import com.copyright.rup.dist.common.integration.camel.CommonMarshaller;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.io.OutputStream;

/**
 * Json unmarshaller for handling messages to check RH tax country.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/13/2018
 *
 * @author Uladzislau Shalamitski
 */
@Component("df.service.rhTaxUnmarshaller")
public class RhTaxUnmarshaller extends CommonMarshaller {

    private static final TypeReference<Usage> TYPE_REFERENCE =
        new TypeReference<Usage>() {
        };

    @Override
    public void marshal(Exchange exchange, Object graph, OutputStream stream) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected ObjectMapper getObjectMapper() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(Usage.class, new RhTaxDeserializer());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    @Override
    protected TypeReference<Usage> getTypeReference() {
        return TYPE_REFERENCE;
    }
}
