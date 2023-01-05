package com.copyright.rup.dist.foreign.repository.impl.converter;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.foreign.domain.FundPool;

import org.junit.Test;
import org.postgresql.util.PGobject;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Verifies {@link SalFundPoolFieldsTypeHandler}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/23/2020
 *
 * @author Uladzislau Shalamitski
 */
public class SalFundPoolFieldsTypeHandlerTest {

    private static final String COLUMN_NAME = "sal_fields";
    private static final int COLUMN_INDEX = 1;
    private static final int PARAMETER_INDEX = 1;

    private final SalFundPoolFieldsTypeHandler typeHandler = new SalFundPoolFieldsTypeHandler();

    @Test
    public void testSetNonNullParameterPreparedStatement() throws SQLException {
        PreparedStatement preparedStatement = createMock(PreparedStatement.class);
        PGobject pgobject = new PGobject();
        FundPool.SalFields salFields = buildSalFields();
        pgobject.setValue(typeHandler.serialize(salFields));
        pgobject.setType("jsonb");
        preparedStatement.setObject(PARAMETER_INDEX, pgobject);
        expectLastCall().once();
        replay(preparedStatement);
        typeHandler.setNonNullParameter(preparedStatement, PARAMETER_INDEX, salFields, null);
        verify(preparedStatement);
    }

    @Test
    public void testGetNullableResultColumnName() throws SQLException {
        ResultSet resultSet = createMock(ResultSet.class);
        try {
            expect(resultSet.getString(COLUMN_NAME)).andReturn(null).once();
            resultSet.close();
            expectLastCall().once();
            replay(resultSet);
            assertNull(typeHandler.getNullableResult(resultSet, COLUMN_NAME));
        } finally {
            resultSet.close();
        }
        verify(resultSet);
    }

    @Test
    public void testGetNullableResultColumnIndex() throws SQLException {
        ResultSet resultSet = createMock(ResultSet.class);
        try {
            FundPool.SalFields salFields = buildSalFields();
            String json = typeHandler.serialize(salFields);
            expect(resultSet.getString(COLUMN_INDEX)).andReturn(json).once();
            resultSet.close();
            expectLastCall().once();
            replay(resultSet);
            assertEquals(salFields, typeHandler.getNullableResult(resultSet, COLUMN_INDEX));
        } finally {
            resultSet.close();
        }
        verify(resultSet);
    }

    @Test
    public void testGetNullableResultCallableStatement() throws SQLException {
        CallableStatement callableStatement = createMock(CallableStatement.class);
        FundPool.SalFields salFields = buildSalFields();
        String json = typeHandler.serialize(salFields);
        expect(callableStatement.getString(PARAMETER_INDEX)).andReturn(json).once();
        replay(callableStatement);
        assertEquals(salFields, typeHandler.getNullableResult(callableStatement, COLUMN_INDEX));
        verify(callableStatement);
    }

    private FundPool.SalFields buildSalFields() {
        FundPool.SalFields salFields = new FundPool.SalFields();
        salFields.setDateReceived(LocalDate.of(2020, 12, 24));
        salFields.setAssessmentName("FY2020 COG");
        salFields.setLicenseeAccountNumber(1000008985L);
        salFields.setLicenseeName("FarmField Inc.");
        salFields.setGradeKto5NumberOfStudents(10);
        salFields.setGrade6to8NumberOfStudents(5);
        salFields.setGrossAmount(new BigDecimal("1000.0"));
        salFields.setItemBankGrossAmount(new BigDecimal("20.01"));
        salFields.setGradeKto5GrossAmount(new BigDecimal("653.33"));
        salFields.setGrade6to8GrossAmount(new BigDecimal("326.66"));
        salFields.setGrade9to12GrossAmount(new BigDecimal("0.0"));
        salFields.setItemBankSplitPercent(new BigDecimal("0.02"));
        salFields.setServiceFee(new BigDecimal("0.25"));
        return salFields;
    }
}
