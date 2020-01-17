package com.copyright.rup.dist.foreign.service.impl.common;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Implementation for {@link StdSerializer} to build json message from {@link Usage}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/12/2018
 *
 * @author Ihar Suvorau
 */
@Component("df.service.commonUsageSerializer")
public class CommonUsageSerializer extends StdSerializer<Usage> {

    /**
     * Constructor.
     */
    public CommonUsageSerializer() {
        super(CollectionLikeType.upgradeFrom(TypeFactory.defaultInstance().constructType(Usage.class),
            TypeFactory.defaultInstance().constructType(CommonUsageSerializer.class)));
    }

    @Override
    public void serialize(Usage usage, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        jsonGenerator.writeStartObject();
        if (Objects.nonNull(usage)) {
            writeNotNullField(jsonGenerator, "id", usage.getId());
            writeNotNullField(jsonGenerator, "rh_uid", usage.getRightsholder().getId());
            writeNotNullField(jsonGenerator, "rh_account_number", usage.getRightsholder().getAccountNumber());
            writeNotNullField(jsonGenerator, "standard_number", usage.getStandardNumber());
            writeNotNullField(jsonGenerator, "standard_number_type", usage.getStandardNumberType());
            writeNotNullField(jsonGenerator, "wr_wrk_inst", usage.getWrWrkInst());
            writeNotNullField(jsonGenerator, "work_title", usage.getWorkTitle());
            writeNotNullField(jsonGenerator, "system_title", usage.getSystemTitle());
            writeNotNullField(jsonGenerator, "batch_id", usage.getBatchId());
            writeNotNullField(jsonGenerator, "gross_amount", usage.getGrossAmount());
            writeNotNullField(jsonGenerator, "status", usage.getStatus().name());
            writeNotNullField(jsonGenerator, "product_family", usage.getProductFamily());
            if (FdaConstants.AACL_PRODUCT_FAMILY.equals(usage.getProductFamily())) {
                writeNotNullField(jsonGenerator, "batch_period_end_date", usage.getAaclUsage().getBatchPeriodEndDate());
            }
            jsonGenerator.writeNumberField("record_version", usage.getVersion());
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
