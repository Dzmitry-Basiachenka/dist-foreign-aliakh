package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.foreign.domain.FundPool;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * Implementation of {@link StdSerializer} for {@link FundPool.SalFields}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/23/2020
 *
 * @author Uladzislau Shalamitski
 */
public class SalFundPoolFieldsSerializer extends StdSerializer<FundPool.SalFields> {

    /**
     * Default constructor.
     */
    SalFundPoolFieldsSerializer() {
        super(FundPool.SalFields.class);
    }

    @Override
    public void serialize(FundPool.SalFields salFields, JsonGenerator generator, SerializerProvider provider)
        throws IOException {
        generator.writeStartObject();
        generator.writeStringField("date_received",
            salFields.getDateReceived().format(DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT)));
        generator.writeStringField("assessment_name", salFields.getAssessmentName());
        generator.writeNumberField("licensee_account_number", salFields.getLicenseeAccountNumber());
        generator.writeStringField("licensee_name", salFields.getLicenseeName());
        generator.writeNumberField("grade_K_5_number_of_students", salFields.getGradeKto5NumberOfStudents());
        generator.writeNumberField("grade_6_8_number_of_students", salFields.getGrade6to8NumberOfStudents());
        generator.writeNumberField("grade_9_12_number_of_students", salFields.getGrade9to12NumberOfStudents());
        generator.writeNumberField("gross_amount", salFields.getGrossAmount());
        generator.writeNumberField("item_bank_gross_amount", salFields.getItemBankGrossAmount());
        generator.writeNumberField("grade_K_5_gross_amount", salFields.getGradeKto5GrossAmount());
        generator.writeNumberField("grade_6_8_gross_amount", salFields.getGrade6to8GrossAmount());
        generator.writeNumberField("grade_9_12_gross_amount", salFields.getGrade9to12GrossAmount());
        generator.writeNumberField("item_bank_split_percent", salFields.getItemBankSplitPercent());
        generator.writeNumberField("service_fee", salFields.getServiceFee());
        generator.writeEndObject();
    }
}
