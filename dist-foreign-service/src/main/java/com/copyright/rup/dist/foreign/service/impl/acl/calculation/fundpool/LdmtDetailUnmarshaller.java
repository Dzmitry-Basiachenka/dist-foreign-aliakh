package com.copyright.rup.dist.foreign.service.impl.acl.calculation.fundpool;

import com.copyright.rup.dist.common.integration.camel.CommonMarshaller;
import com.copyright.rup.dist.foreign.domain.LdmtDetail;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.util.List;

/**
 * Unmarshaller to consume {@link LdmtDetail}s from Oracle.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/11/2022
 *
 * @author Aliaksandr Liakh
 */
@Component("df.service.ldmtDetailUnmarshaller")
public class LdmtDetailUnmarshaller extends CommonMarshaller {

    private static final TypeReference<List<LdmtDetail>> TYPE_REFERENCE = new TypeReference<List<LdmtDetail>>() {};

    @Override
    public void marshal(Exchange exchange, Object graph, OutputStream stream) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected ObjectMapper getObjectMapper() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(List.class, new LdmtDetailDeserializer());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    @Override
    protected TypeReference<List<LdmtDetail>> getTypeReference() {
        return TYPE_REFERENCE;
    }
}
