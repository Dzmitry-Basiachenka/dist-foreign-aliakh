package com.copyright.rup.dist.foreign.service.impl.job;

import com.copyright.rup.dist.foreign.domain.job.JobInfo;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * Implementation of {@link com.fasterxml.jackson.databind.JsonSerializer} for {@link JobInfo}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 05/15/2019
 *
 * @author Uladzislau Shalamitski
 */
public final class JobStatusSerializer extends StdSerializer<JobInfo> {

    /**
     * Constructor.
     */
    public JobStatusSerializer() {
        super(TypeFactory.defaultInstance().constructType(JobInfo.class));
    }

    @Override
    public void serialize(JobInfo info, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", info.getName());
        jsonGenerator.writeStringField("status", info.getStatus().name());
        jsonGenerator.writeStringField("execution_datetime",
            DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(info.getExecutionDatetime()));
        jsonGenerator.writeStringField("result", info.getResult());
        jsonGenerator.writeEndObject();
    }
}
