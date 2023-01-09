package com.copyright.rup.dist.foreign.repository.impl.converter;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPool.AclciFields;

import org.junit.Test;
import org.postgresql.util.PGobject;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Verifies {@link AclciFundPoolFieldsTypeHandler}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/28/2022
 *
 * @author Mikita Maistrenka
 */
public class AclciFundPoolFieldsTypeHandlerTest {

    private static final String COLUMN_NAME = "aclci_fields";
    private static final int COLUMN_INDEX = 1;
    private static final int PARAMETER_INDEX = 1;

    private final AclciFundPoolFieldsTypeHandler typeHandler = new AclciFundPoolFieldsTypeHandler();

    @Test
    public void testSetNonNullParameterPreparedStatement() throws SQLException {
        PreparedStatement preparedStatement = createMock(PreparedStatement.class);
        PGobject pgobject = new PGobject();
        FundPool.AclciFields aclciFields = buildAclciFields();
        pgobject.setValue(typeHandler.serialize(aclciFields));
        pgobject.setType("jsonb");
        preparedStatement.setObject(PARAMETER_INDEX, pgobject);
        expectLastCall().once();
        replay(preparedStatement);
        typeHandler.setNonNullParameter(preparedStatement, PARAMETER_INDEX, aclciFields, null);
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
            FundPool.AclciFields aclciFields = buildAclciFields();
            String json = typeHandler.serialize(aclciFields);
            expect(resultSet.getString(COLUMN_INDEX)).andReturn(json).once();
            resultSet.close();
            expectLastCall().once();
            replay(resultSet);
            assertEquals(aclciFields, typeHandler.getNullableResult(resultSet, COLUMN_INDEX));
        } finally {
            resultSet.close();
        }
        verify(resultSet);
    }

    @Test
    public void testGetNullableResultCallableStatement() throws SQLException {
        CallableStatement callableStatement = createMock(CallableStatement.class);
        FundPool.AclciFields aclciFields = buildAclciFields();
        String json = typeHandler.serialize(aclciFields);
        expect(callableStatement.getString(PARAMETER_INDEX)).andReturn(json).once();
        replay(callableStatement);
        assertEquals(aclciFields, typeHandler.getNullableResult(callableStatement, COLUMN_INDEX));
        verify(callableStatement);
    }

    private FundPool.AclciFields buildAclciFields() {
        FundPool.AclciFields aclciFields = new AclciFields();
        aclciFields.setCoverageYears("2022-2023");
        aclciFields.setGradeKto2NumberOfStudents(2);
        aclciFields.setGrade3to5NumberOfStudents(5);
        aclciFields.setGrade6to8NumberOfStudents(8);
        aclciFields.setGrade9to12NumberOfStudents(12);
        aclciFields.setGradeHeNumberOfStudents(20);
        aclciFields.setGrossAmount(new BigDecimal("1000.0"));
        aclciFields.setCurriculumDbGrossAmount(new BigDecimal("200.0"));
        aclciFields.setGradeKto2GrossAmount(new BigDecimal("34.04"));
        aclciFields.setGrade3to5GrossAmount(new BigDecimal("85.11"));
        aclciFields.setGrade6to8GrossAmount(new BigDecimal("136.17"));
        aclciFields.setGrade9to12GrossAmount(new BigDecimal("204.26"));
        aclciFields.setGradeHeGrossAmount(new BigDecimal("340.43"));
        aclciFields.setCurriculumDbSplitPercent(new BigDecimal("0.2"));
        return aclciFields;
    }
}
