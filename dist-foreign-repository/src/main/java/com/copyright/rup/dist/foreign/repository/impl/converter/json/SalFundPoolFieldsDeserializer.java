package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.FundPool;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

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

    private static final String DATE_RECEIVED = "date_received";
    private static final String ASSESSMENT_NAME = "assessment_name";
    private static final String LICENSEE_ACCOUNT_NUMBER = "licensee_account_number";
    private static final String LICENSEE_NAME = "licensee_name";
    private static final String GRADE_K_5_NUMBER_OF_STUDENTS = "grade_K_5_number_of_students";
    private static final String GRADE_6_8_NUMBER_OF_STUDENTS = "grade_6_8_number_of_students";
    private static final String GRADE_9_12_NUMBER_OF_STUDENTS = "grade_9_12_number_of_students";
    private static final String GROSS_AMOUNT = "gross_amount";
    private static final String ITEM_BANK_GROSS_AMOUNT = "item_bank_gross_amount";
    private static final String GRADE_K_5_GROSS_AMOUNT = "grade_K_5_gross_amount";
    private static final String GRADE_6_8_GROSS_AMOUNT = "grade_6_8_gross_amount";
    private static final String GRADE_9_12_GROSS_AMOUNT = "grade_9_12_gross_amount";
    private static final String ITEM_BANK_SPLIT_PERCENT = "item_bank_split_percent";
    private static final String SERVICE_FEE = "service_fee";

    /**
     * Default constructor.
     */
    SalFundPoolFieldsDeserializer() {
        super(FundPool.SalFields.class);
    }

    @Override
    public FundPool.SalFields deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        FundPool.SalFields salFields = new FundPool.SalFields();
        JsonToken currentToken;
        while (null != (currentToken = parser.nextValue())) {
            if (JsonToken.VALUE_STRING == currentToken) {
                parseAndPopulateSalStringFields(parser, salFields);
            } else if (JsonToken.VALUE_NUMBER_INT == currentToken) {
                parseAndPopulateSalIntFields(parser, salFields);
            } else if (JsonToken.VALUE_NUMBER_FLOAT == currentToken) {
                parseAndPopulateSalDecimalFields(parser, salFields);
            }
        }
        return salFields;
    }

    private void parseAndPopulateSalStringFields(JsonParser parser, FundPool.SalFields salFields) throws IOException {
        switch (parser.getCurrentName()) {
            case DATE_RECEIVED:
                salFields.setDateReceived(CommonDateUtils.parseLocalDate(parser.getValueAsString()));
                break;
            case ASSESSMENT_NAME:
                salFields.setAssessmentName(parser.getValueAsString());
                break;
            case LICENSEE_NAME:
                salFields.setLicenseeName(parser.getValueAsString());
                break;
            default:
                break;
        }
    }

    private void parseAndPopulateSalIntFields(JsonParser parser, FundPool.SalFields salFields) throws IOException {
        switch (parser.getCurrentName()) {
            case LICENSEE_ACCOUNT_NUMBER:
                salFields.setLicenseeAccountNumber(parser.getValueAsLong());
                break;
            case GRADE_K_5_NUMBER_OF_STUDENTS:
                salFields.setGradeKto5NumberOfStudents(parser.getValueAsInt());
                break;
            case GRADE_6_8_NUMBER_OF_STUDENTS:
                salFields.setGrade6to8NumberOfStudents(parser.getValueAsInt());
                break;
            case GRADE_9_12_NUMBER_OF_STUDENTS:
                salFields.setGrade9to12NumberOfStudents(parser.getValueAsInt());
                break;
            default:
                break;
        }
    }

    private void parseAndPopulateSalDecimalFields(JsonParser parser, FundPool.SalFields salFields) throws IOException {
        switch (parser.getCurrentName()) {
            case GROSS_AMOUNT:
                salFields.setGrossAmount(parser.getDecimalValue());
                break;
            case ITEM_BANK_GROSS_AMOUNT:
                salFields.setItemBankGrossAmount(parser.getDecimalValue());
                break;
            case GRADE_K_5_GROSS_AMOUNT:
                salFields.setGradeKto5GrossAmount(parser.getDecimalValue());
                break;
            case GRADE_6_8_GROSS_AMOUNT:
                salFields.setGrade6to8GrossAmount(parser.getDecimalValue());
                break;
            case GRADE_9_12_GROSS_AMOUNT:
                salFields.setGrade9to12GrossAmount(parser.getDecimalValue());
                break;
            case ITEM_BANK_SPLIT_PERCENT:
                salFields.setItemBankSplitPercent(parser.getDecimalValue());
                break;
            case SERVICE_FEE:
                salFields.setServiceFee(parser.getDecimalValue());
                break;
            default:
                break;
        }
    }
}
