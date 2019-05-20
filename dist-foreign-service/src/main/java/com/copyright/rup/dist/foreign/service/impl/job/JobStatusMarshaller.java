package com.copyright.rup.dist.foreign.service.impl.job;

import com.copyright.rup.dist.common.integration.camel.CommonMarshaller;
import com.copyright.rup.dist.foreign.domain.job.JobInfo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * Provides functionality for marshalling {@link JobInfo}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 05/14/2019
 *
 * @author Uladzislau Shalamitski
 */
@Component("df.producer.jobStatusMarshaller")
public class JobStatusMarshaller extends CommonMarshaller {

    /**
     * Usages marshaller {@link TypeReference}.
     */
    private static final TypeReference<JobInfo> TYPE_REFERENCE = new TypeReference<JobInfo>() {
    };

    @Override
    protected ObjectMapper getObjectMapper() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(new JobStatusSerializer());
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
