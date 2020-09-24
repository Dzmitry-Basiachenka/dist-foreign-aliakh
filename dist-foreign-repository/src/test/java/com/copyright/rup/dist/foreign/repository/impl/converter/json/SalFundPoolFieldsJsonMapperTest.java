package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.FundPool;

import com.fasterxml.jackson.core.JsonParseException;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Verifies {@link SalFundPoolFieldsJsonMapper}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/23/2020
 *
 * @author Uladzislau Shalamitski
 */
public class SalFundPoolFieldsJsonMapperTest {

    private final SalFundPoolFieldsJsonMapper jsonMapper = new SalFundPoolFieldsJsonMapper();

    @Test
    public void testDeserialize() throws IOException {
        String json = TestUtils.fileToString(this.getClass(), "sal_fund_pool_fields.json");
        assertEquals(buildSalFields(), jsonMapper.deserialize(json));
    }

    @Test
    public void testSerialize() throws IOException {
        FundPool.SalFields salFields = buildSalFields();
        String actualJson = jsonMapper.serialize(salFields);
        assertEquals(salFields, jsonMapper.deserialize(actualJson));
    }

    @Test
    public void testDeserializeNull() throws IOException {
        assertNull(jsonMapper.deserialize(null));
    }

    @Test
    public void testSerializeNull() throws IOException {
        assertNull(jsonMapper.serialize(null));
    }

    @Test(expected = JsonParseException.class)
    public void testDeserializeException() throws IOException {
        jsonMapper.deserialize("{wrong JSON}");
    }

    private FundPool.SalFields buildSalFields() {
        FundPool.SalFields salFields = new FundPool.SalFields();
        salFields.setDateReceived(LocalDate.of(2020, 12, 24));
        salFields.setAssessmentName("FY2020 COG");
        salFields.setLicenseeAccountNumber(1000008985L);
        salFields.setLicenseeName("FarmField Inc.");
        salFields.setGradeKto5NumberOfStudents(10);
        salFields.setGrade6to8NumberOfStudents(5);
        salFields.setGrossAmount(new BigDecimal("1000.00"));
        salFields.setItemBankAmount(new BigDecimal("15.00"));
        salFields.setItemBankGrossAmount(new BigDecimal("20.00"));
        salFields.setGradeKto5GrossAmount(new BigDecimal("490.00"));
        salFields.setGrade6to8GrossAmount(new BigDecimal("245.00"));
        salFields.setGrade9to12GrossAmount(new BigDecimal("0.00"));
        salFields.setItemBankSplitPercent(new BigDecimal("0.02000"));
        salFields.setServiceFee(new BigDecimal("0.25000"));
        return salFields;
    }
}
