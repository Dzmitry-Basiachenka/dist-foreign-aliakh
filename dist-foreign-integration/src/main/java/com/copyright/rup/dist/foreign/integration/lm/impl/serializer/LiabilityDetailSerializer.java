package com.copyright.rup.dist.foreign.integration.lm.impl.serializer;

import com.copyright.rup.dist.foreign.domain.LiabilityDetail;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link StdSerializer} for list of {@link LiabilityDetail}s.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/10/18
 *
 * @author Ihar Suvorau
 */
public class LiabilityDetailSerializer extends StdSerializer<List<LiabilityDetail>> {

    /**
     * Constructor.
     */
    public LiabilityDetailSerializer() {
        super(CollectionLikeType.upgradeFrom(TypeFactory.defaultInstance().constructType(List.class),
            TypeFactory.defaultInstance().constructType(LiabilityDetail.class)));
    }

    @Override
    public void serialize(List<LiabilityDetail> messages, JsonGenerator jsonGenerator, SerializerProvider provider)
        throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeArrayFieldStart("details");
        for (LiabilityDetail liabilityDetail : messages) {
            jsonGenerator.writeStartObject();
            writeNotNullField(jsonGenerator, "rh_account_number",
                Objects.toString(liabilityDetail.getRhAccountNumber()));
            writeNotNullField(jsonGenerator, "product_family", liabilityDetail.getProductFamily());
            jsonGenerator.writeNumberField("royalty_amount",
                Objects.requireNonNull(liabilityDetail.getRoyaltyAmount().setScale(2, RoundingMode.HALF_UP)));
            writeNotNullField(jsonGenerator, "detail_id", Objects.toString(liabilityDetail.getDetailId()));
            writeNotNullField(jsonGenerator, "wr_wrk_inst", Objects.toString(liabilityDetail.getWrWrkInst()));
            writeNotNullField(jsonGenerator, "work_title", liabilityDetail.getWorkTitle());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }

    private void writeNotNullField(JsonGenerator jsonGenerator, String fieldName, String fieldValue)
        throws IOException {
        if (Objects.nonNull(fieldValue)) {
            jsonGenerator.writeStringField(fieldName, fieldValue);
        }
    }
}
