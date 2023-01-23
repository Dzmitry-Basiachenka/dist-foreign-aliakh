package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.domain.FundPool.SalFields;
import com.copyright.rup.dist.foreign.repository.impl.converter.json.common.CommonJsonFieldsDeserializer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import org.slf4j.Logger;

import java.io.IOException;

/**
 * Implementation of {@link CommonJsonFieldsDeserializer} for fund pool {@link SalFields}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/23/2020
 *
 * @author Uladzislau Shalamitski
 */
public class SalFundPoolFieldsDeserializer extends CommonJsonFieldsDeserializer<SalFields> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    /**
     * Default constructor.
     */
    SalFundPoolFieldsDeserializer() {
        super(SalFields.class);
    }

    @Override
    public SalFields deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        SalFields salFields = new SalFields();
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
}
