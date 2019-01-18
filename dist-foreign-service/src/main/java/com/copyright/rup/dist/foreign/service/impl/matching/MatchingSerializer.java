package com.copyright.rup.dist.foreign.service.impl.matching;

import com.copyright.rup.dist.foreign.domain.Usage;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.SimpleType;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Implementation for {@link StdSerializer} to build json message for matching.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/12/2018
 *
 * @author Ihar Suvorau
 */
@Component("df.service.matchingSerializer")
public class MatchingSerializer extends StdSerializer<Usage> {

    /**
     * Constructor.
     */
    public MatchingSerializer() {
        super(CollectionLikeType.construct(Usage.class, SimpleType.construct(MatchingSerializer.class)));
    }

    @Override
    public void serialize(Usage usage, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        jsonGenerator.writeStartObject();
        if (Objects.nonNull(usage)) {
            writeNotNullField(jsonGenerator, "id", usage.getId());
            writeNotNullField(jsonGenerator, "standard_number", usage.getStandardNumber());
            writeNotNullField(jsonGenerator, "work_title", usage.getWorkTitle());
            writeNotNullField(jsonGenerator, "batch_id", usage.getBatchId());
            writeNotNullField(jsonGenerator, "status", usage.getStatus().name());
            writeNotNullField(jsonGenerator, "gross_amount", usage.getGrossAmount());
            writeNotNullField(jsonGenerator, "product_family", usage.getProductFamily());
        }
        jsonGenerator.writeEndObject();
    }

    private void writeNotNullField(JsonGenerator jsonGenerator, String fieldName, String fieldValue)
        throws IOException {
        if (Objects.nonNull(fieldValue)) {
            jsonGenerator.writeStringField(fieldName, fieldValue);
        }
    }

    private void writeNotNullField(JsonGenerator jsonGenerator, String fieldName, BigDecimal fieldValue)
        throws IOException {
        if (Objects.nonNull(fieldValue)) {
            jsonGenerator.writeNumberField(fieldName, fieldValue);
        }
    }
}
