package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
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
import java.time.LocalDate;
import java.util.Objects;

/**
 * Implementation of {@link StdDeserializer} for {@link FundPool.SalFields}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/23/2020
 *
 * @author Uladzislau Shalamitski
 */
public class SalFundPoolFieldsDeserializer extends StdDeserializer<FundPool.SalFields> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    /**
     * Default constructor.
     */
    SalFundPoolFieldsDeserializer() {
        super(FundPool.SalFields.class);
    }

    @Override
    public FundPool.SalFields deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        FundPool.SalFields salFields = new FundPool.SalFields();
        try {
            JsonNode jsonNode = parser.readValueAsTree();
            salFields.setDateReceived(getLocalDateValue(jsonNode.get("date_received")));
            salFields.setAssessmentName(getStringValue(jsonNode.get("assessment_name")));
            salFields.setLicenseeAccountNumber(getLongValue(jsonNode.get("licensee_account_number")));
            salFields.setLicenseeName(getStringValue(jsonNode.get("licensee_name")));
            salFields.setGradeKto5NumberOfStudents(getIntegerValue(jsonNode.get("grade_K_5_number_of_students")));
            salFields.setGrade6to8NumberOfStudents(getIntegerValue(jsonNode.get("grade_6_8_number_of_students")));
            salFields.setGrade9to12NumberOfStudents(getIntegerValue(jsonNode.get("grade_9_12_number_of_students")));
            salFields.setGrossAmount(getBigDecimalValue(jsonNode.get("gross_amount")));
            salFields.setItemBankGrossAmount(getBigDecimalValue(jsonNode.get("item_bank_gross_amount")));
            salFields.setGradeKto5GrossAmount(getBigDecimalValue(jsonNode.get("grade_K_5_gross_amount")));
            salFields.setGrade6to8GrossAmount(getBigDecimalValue(jsonNode.get("grade_6_8_gross_amount")));
            salFields.setGrade9to12GrossAmount(getBigDecimalValue(jsonNode.get("grade_9_12_gross_amount")));
            salFields.setItemBankSplitPercent(getBigDecimalValue(jsonNode.get("item_bank_split_percent")));
            salFields.setServiceFee(getBigDecimalValue(jsonNode.get("service_fee")));
        } catch (JsonParseException e) {
            LOGGER.warn("Deserialize SAL fund pool fields. Failed", e);
        }
        return salFields;
    }

    private static LocalDate getLocalDateValue(JsonNode node) {
        return Objects.nonNull(node) && !node.isNumber()
            ? CommonDateUtils.parseLocalDate(node.textValue())
            : CommonDateUtils.getLocalDateFromLong(node.longValue());
    }

    private static String getStringValue(JsonNode node) {
        return Objects.nonNull(node) ? node.textValue() : null;
    }

    private static Long getLongValue(JsonNode node) {
        return Objects.nonNull(node) ? node.asLong() : null;
    }

    private static Integer getIntegerValue(JsonNode node) {
        return Objects.nonNull(node) ? node.asInt() : null;
    }

    private static BigDecimal getBigDecimalValue(JsonNode node) {
        return Objects.nonNull(node) && NumberUtils.isNumber(node.asText()) ? new BigDecimal(node.asText()) : null;
    }
}
