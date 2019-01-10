package com.copyright.rup.dist.foreign.service.impl.eligibility;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.SimpleType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

/**
 * Implementation for {@link StdSerializer} to check whether RH is eligible for distribution or not.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/10/2019
 *
 * @author Uladzislau Shalamitski
 */
@Component("df.service.rhEligibilitySerializer")
public class RhEligibilitySerializer extends StdSerializer<Usage> {

    /**
     * Constructor.
     */
    public RhEligibilitySerializer() {
        super(CollectionLikeType.construct(Usage.class, SimpleType.construct(RhEligibilitySerializer.class)));
    }

    @Override
    public void serialize(Usage usage, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        jsonGenerator.writeStartObject();
        if (Objects.nonNull(usage)) {
            writeNotNullField(jsonGenerator, "id", usage.getId());
            writeNotNullField(jsonGenerator, "rh_id", usage.getRightsholder().getId());
        }
        jsonGenerator.writeEndObject();
    }

    private void writeNotNullField(JsonGenerator jsonGenerator, String fieldName, String fieldValue)
        throws IOException {
        if (Objects.nonNull(fieldValue)) {
            jsonGenerator.writeStringField(fieldName, fieldValue);
        }
    }
}
