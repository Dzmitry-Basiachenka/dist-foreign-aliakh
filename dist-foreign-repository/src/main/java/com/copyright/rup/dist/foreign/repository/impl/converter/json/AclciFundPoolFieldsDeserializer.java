package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.domain.FundPool.AclciFields;
import com.copyright.rup.dist.foreign.repository.impl.converter.json.common.CommonJsonFieldsDeserializer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import org.slf4j.Logger;

import java.io.IOException;

/**
 * Implementation of {@link CommonJsonFieldsDeserializer} for fund pool {@link AclciFields}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/28/2022
 *
 * @author Mikita Maistrenka
 */
public class AclciFundPoolFieldsDeserializer extends CommonJsonFieldsDeserializer<AclciFields> {

    private static final long serialVersionUID = -8620992041205623349L;
    private static final Logger LOGGER = RupLogUtils.getLogger();

    /**
     * Default constructor.
     */
    AclciFundPoolFieldsDeserializer() {
        super(AclciFields.class);
    }

    @Override
    public AclciFields deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        AclciFields aclciFields = new AclciFields();
        try {
            JsonNode jsonNode = parser.readValueAsTree();
            aclciFields.setCoverageYears(getStringValue(jsonNode.get("coverage_years")));
            aclciFields.setGradeKto2NumberOfStudents(getLongValue(jsonNode.get("grade_k_2_number_of_students")));
            aclciFields.setGrade3to5NumberOfStudents(getLongValue(jsonNode.get("grade_3_5_number_of_students")));
            aclciFields.setGrade6to8NumberOfStudents(getLongValue(jsonNode.get("grade_6_8_number_of_students")));
            aclciFields.setGrade9to12NumberOfStudents(getLongValue(jsonNode.get("grade_9_12_number_of_students")));
            aclciFields.setGradeHeNumberOfStudents(getLongValue(jsonNode.get("grade_he_number_of_students")));
            aclciFields.setGrossAmount(getBigDecimalValue(jsonNode.get("gross_amount")));
            aclciFields.setCurriculumDbGrossAmount(getBigDecimalValue(jsonNode.get("curriculum_db_gross_amount")));
            aclciFields.setGradeKto2GrossAmount(getBigDecimalValue(jsonNode.get("grade_K_2_gross_amount")));
            aclciFields.setGrade3to5GrossAmount(getBigDecimalValue(jsonNode.get("grade_3_5_gross_amount")));
            aclciFields.setGrade6to8GrossAmount(getBigDecimalValue(jsonNode.get("grade_6_8_gross_amount")));
            aclciFields.setGrade9to12GrossAmount(getBigDecimalValue(jsonNode.get("grade_9_12_gross_amount")));
            aclciFields.setGradeHeGrossAmount(getBigDecimalValue(jsonNode.get("grade_he_gross_amount")));
            aclciFields.setCurriculumDbSplitPercent(getBigDecimalValue(jsonNode.get("curriculum_db_split_percent")));
        } catch (JsonParseException e) {
            LOGGER.warn("Deserialize ACLCI fund pool fields. Failed", e);
        }
        return aclciFields;
    }
}
