package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.FundPool;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Implementation of {@link StdSerializer} for {@link FundPool.AclciFields}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/28/2022
 *
 * @author Mikita Maistrenka
 */
public class AclciFundPoolFieldsSerializer extends StdSerializer<FundPool.AclciFields> {

    private static final long serialVersionUID = 3034436079351355643L;

    /**
     * Default constructor.
     */
    AclciFundPoolFieldsSerializer() {
        super(FundPool.AclciFields.class);
    }

    @Override
    public void serialize(FundPool.AclciFields aclciFields, JsonGenerator generator, SerializerProvider provider)
        throws IOException {
        generator.writeStartObject();
        generator.writeStringField("coverage_years", aclciFields.getCoverageYears());
        generator.writeNumberField("grade_k_2_number_of_students", aclciFields.getGradeKto2NumberOfStudents());
        generator.writeNumberField("grade_3_5_number_of_students", aclciFields.getGrade3to5NumberOfStudents());
        generator.writeNumberField("grade_6_8_number_of_students", aclciFields.getGrade6to8NumberOfStudents());
        generator.writeNumberField("grade_9_12_number_of_students", aclciFields.getGrade9to12NumberOfStudents());
        generator.writeNumberField("grade_he_number_of_students", aclciFields.getGradeHeNumberOfStudents());
        generator.writeNumberField("gross_amount", aclciFields.getGrossAmount());
        generator.writeNumberField("curriculum_db_gross_amount", aclciFields.getCurriculumDbGrossAmount());
        generator.writeNumberField("grade_K_2_gross_amount", aclciFields.getGradeKto2GrossAmount());
        generator.writeNumberField("grade_3_5_gross_amount", aclciFields.getGrade3to5GrossAmount());
        generator.writeNumberField("grade_6_8_gross_amount", aclciFields.getGrade6to8GrossAmount());
        generator.writeNumberField("grade_9_12_gross_amount", aclciFields.getGrade9to12GrossAmount());
        generator.writeNumberField("grade_he_gross_amount", aclciFields.getGradeHeGrossAmount());
        generator.writeNumberField("curriculum_db_split_percent", aclciFields.getCurriculumDbSplitPercent());
        generator.writeEndObject();
    }
}
