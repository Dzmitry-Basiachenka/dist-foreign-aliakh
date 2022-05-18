package com.copyright.rup.dist.foreign.service.impl.acl.calculation.fundpool.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.common.integration.camel.ValidationException;
import com.copyright.rup.dist.common.test.TestUtils;

import org.junit.Test;

import java.io.IOException;

/**
 * Verifies {@link LdmtDetailsJsonValidator}.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/14/2022
 *
 * @author Aliaksandr Liakh
 */
public class LdmtDetailsJsonValidatorTest {

    private static final String LDMT_DETAIL_0_IS_NOT_VALID = "LDMT detail #0 is not valid: [";

    private final LdmtDetailsJsonValidator validator = new LdmtDetailsJsonValidator();

    @Test
    public void testValidateSuccess() throws IOException {
        validator.validate(loadJson("ldmt_detail.json"));
    }

    @Test
    public void testValidateFailure() throws IOException {
        try {
            validator.validate(loadJson("ldmt_details_failure.json"));
            fail();
        } catch (ValidationException e) {
            assertEquals(LDMT_DETAIL_0_IS_NOT_VALID +
                "Licensee Class Id should be a valid integer number: not_a_number, " +
                "Type Of Use should be either 'DIGITAL' or 'PHOTOCOPY': error, " +
                "Gross Amount should be a positive number and not exceed 10 digits: not_a_number, " +
                "Net Amount should be a positive number and not exceed 10 digits: not_a_number]; " +
                "LDMT detail #1 is not valid: [" +
                "Licensee Class Id should be a valid integer number: not_a_number, " +
                "Type Of Use should be either 'DIGITAL' or 'PHOTOCOPY': error, " +
                "Gross Amount should be a positive number and not exceed 10 digits: not_a_number, " +
                "Net Amount should be a positive number and not exceed 10 digits: not_a_number]", e.getMessage());
        }
    }

    @Test
    public void testValidateFailureNullFields() throws IOException {
        try {
            validator.validate(loadJson("ldmt_detail_null_fields.json"));
            fail();
        } catch (ValidationException e) {
            assertEquals(LDMT_DETAIL_0_IS_NOT_VALID +
                "Licensee Class Id should not be null or blank, " +
                "License Type should not be null or blank, " +
                "Type Of Use should not be null or blank, " +
                "Gross Amount should not be null or blank, " +
                "Net Amount should not be null or blank]", e.getMessage());
        }
    }

    @Test
    public void testValidateFailureEmptyFields() throws IOException {
        try {
            validator.validate(loadJson("ldmt_detail_empty_fields.json"));
            fail();
        } catch (ValidationException e) {
            assertEquals(LDMT_DETAIL_0_IS_NOT_VALID +
                "Licensee Class Id should not be null or blank, " +
                "License Type should not be null or blank, " +
                "Type Of Use should not be null or blank, " +
                "Gross Amount should not be null or blank, " +
                "Net Amount should not be null or blank]", e.getMessage());
        }
    }

    @Test
    public void testValidateFailureLicenseeClassId() throws IOException {
        try {
            validator.validate(loadJson("ldmt_detail_wrong_licensee_class_id.json"));
            fail();
        } catch (ValidationException e) {
            assertEquals(LDMT_DETAIL_0_IS_NOT_VALID +
                "Licensee Class Id should be a valid integer number: not_a_number]", e.getMessage());
        }
    }

    @Test
    public void testValidateFailureTypeOfUse() throws IOException {
        try {
            validator.validate(loadJson("ldmt_detail_wrong_type_of_use.json"));
            fail();
        } catch (ValidationException e) {
            assertEquals(LDMT_DETAIL_0_IS_NOT_VALID +
                "Type Of Use should be either 'DIGITAL' or 'PHOTOCOPY': error]", e.getMessage());
        }
    }

    @Test
    public void testValidateFailureGrossAmountAndNetAmount() throws IOException {
        try {
            validator.validate(loadJson("ldmt_detail_wrong_amounts.json"));
            fail();
        } catch (ValidationException e) {
            assertEquals(LDMT_DETAIL_0_IS_NOT_VALID +
                "Gross Amount should be a positive number and not exceed 10 digits: -10.02, " +
                "Net Amount should be a positive number and not exceed 10 digits: 12345678901.00]", e.getMessage());
        }
    }

    public String loadJson(String fileName) {
        return TestUtils.fileToString(this.getClass(), fileName);
    }
}
