package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.domain.FundPool;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Implementation of {@link StdDeserializer} for {@link FundPool.AclciFields}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/28/2022
 *
 * @author Mikita Maistrenka
 */
public class AclciFundPoolFieldsDeserializer extends StdDeserializer<FundPool.AclciFields> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    /**
     * Default constructor.
     */
    AclciFundPoolFieldsDeserializer() {
        super(FundPool.AclciFields.class);
    }

    @Override
    public FundPool.AclciFields deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        FundPool.AclciFields aclciFields = null;
        try {
            aclciFields = deserializeAclciFields(parser.readValueAsTree());
        } catch (JsonParseException e) {
            LOGGER.warn("Deserialize ACLCI fund pool fields. Failed", e);
        }
        return aclciFields;
    }

    private FundPool.AclciFields deserializeAclciFields(JsonNode jsonNode) {
        FundPool.AclciFields aclciFields = new FundPool.AclciFields();
        aclciFields.setCoverageYears(getStringValue(jsonNode.get("coverage_years")));
        aclciFields.setGradeKto2NumberOfStudents(getIntegerValue(jsonNode.get("grade_k_2_number_of_students")));
        aclciFields.setGrade3to5NumberOfStudents(getIntegerValue(jsonNode.get("grade_3_5_number_of_students")));
        aclciFields.setGrade6to8NumberOfStudents(getIntegerValue(jsonNode.get("grade_6_8_number_of_students")));
        aclciFields.setGrade9to12NumberOfStudents(getIntegerValue(jsonNode.get("grade_9_12_number_of_students")));
        aclciFields.setGradeHeNumberOfStudents(getIntegerValue(jsonNode.get("grade_he_number_of_students")));
        aclciFields.setGrossAmount(getBigDecimalValue(jsonNode.get("gross_amount")));
        aclciFields.setCurriculumGrossAmount(getBigDecimalValue(jsonNode.get("curriculum_gross_amount")));
        aclciFields.setGradeKto2GrossAmount(getBigDecimalValue(jsonNode.get("grade_K_2_gross_amount")));
        aclciFields.setGrade3to5GrossAmount(getBigDecimalValue(jsonNode.get("grade_3_5_gross_amount")));
        aclciFields.setGrade6to8GrossAmount(getBigDecimalValue(jsonNode.get("grade_6_8_gross_amount")));
        aclciFields.setGrade9to12GrossAmount(getBigDecimalValue(jsonNode.get("grade_9_12_gross_amount")));
        aclciFields.setGradeHeGrossAmount(getBigDecimalValue(jsonNode.get("grade_he_gross_amount")));
        aclciFields.setCurriculumSplitPercent(getBigDecimalValue(jsonNode.get("curriculum_split_percent")));
        return aclciFields;
    }

    private static String getStringValue(JsonNode node) {
        return Objects.nonNull(node) ? node.textValue() : null;
    }

    private static Integer getIntegerValue(JsonNode node) {
        return Objects.nonNull(node) ? node.asInt() : null;
    }

    private static BigDecimal getBigDecimalValue(JsonNode node) {
        return Objects.nonNull(node) && NumberUtils.isNumber(node.asText()) ? new BigDecimal(node.asText()) : null;
    }
}
