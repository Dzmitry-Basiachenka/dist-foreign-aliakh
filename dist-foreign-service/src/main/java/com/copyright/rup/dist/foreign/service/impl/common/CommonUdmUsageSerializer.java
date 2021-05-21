package com.copyright.rup.dist.foreign.service.impl.common;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.foreign.domain.UdmUsage;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * Implementation for {@link StdSerializer} to build JSON from list of {@link UdmUsage}s.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/20/2021
 *
 * @author Uladzislau Shalamitski
 */
@Component("df.service.commonUdmUsageSerializer")
public class CommonUdmUsageSerializer extends StdSerializer<List<UdmUsage>> {

    /**
     * Constructor.
     */
    public CommonUdmUsageSerializer() {
        super(CollectionLikeType.upgradeFrom(TypeFactory.defaultInstance().constructType(List.class),
            TypeFactory.defaultInstance().constructType(UdmUsage.class)));
    }

    @Override
    public void serialize(List<UdmUsage> usages, JsonGenerator jsonGenerator, SerializerProvider provider)
        throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeArrayFieldStart("udmUsages");
        if (Objects.nonNull(usages)) {
            for (UdmUsage usage : usages) {
                serialize(usage, jsonGenerator);
            }
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }

    private void serialize(UdmUsage udmUsage, JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeStartObject();
        if (Objects.nonNull(udmUsage)) {
            writeNotNullField(jsonGenerator, "id", udmUsage.getId());
            writeNotNullField(jsonGenerator, "wr_wrk_inst", udmUsage.getWrWrkInst());
            writeNotNullField(jsonGenerator, "type_of_use", udmUsage.getTypeOfUse());
            writeNotNullField(jsonGenerator, "reported_standard_number", udmUsage.getReportedStandardNumber());
            writeNotNullField(jsonGenerator, "reported_title", udmUsage.getReportedTitle());
            writeNotNullField(jsonGenerator, "period_end_date", udmUsage.getPeriodEndDate());
            writeNotNullField(jsonGenerator, "status", udmUsage.getStatus().name());
            jsonGenerator.writeNumberField("record_version", udmUsage.getVersion());
        }
        jsonGenerator.writeEndObject();
    }

    private void writeNotNullField(JsonGenerator jsonGenerator, String fieldName, String fieldValue)
        throws IOException {
        if (Objects.nonNull(fieldValue)) {
            jsonGenerator.writeStringField(fieldName, fieldValue);
        }
    }

    private void writeNotNullField(JsonGenerator jsonGenerator, String fieldName, Long fieldValue)
        throws IOException {
        if (Objects.nonNull(fieldValue)) {
            jsonGenerator.writeNumberField(fieldName, fieldValue);
        }
    }

    private void writeNotNullField(JsonGenerator jsonGenerator, String fieldName, LocalDate fieldValue)
        throws IOException {
        if (Objects.nonNull(fieldValue)) {
            jsonGenerator.writeStringField(fieldName,
                fieldValue.format(DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT)));
        }
    }
}
