package com.copyright.rup.dist.foreign.repository.impl.converter;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.google.common.collect.Sets;
import org.junit.Test;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * Verifies {@link PeriodsTypeHandler}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/26/2022
 *
 * @author Aliaksandr Liakh
 */
public class PeriodsTypeHandlerTest {

    private static final String COLUMN_NAME = "periods";
    private static final int COLUMN_INDEX = 1;
    private static final int PARAMETER_INDEX = 1;

    private final PeriodsTypeHandler typeHandler = new PeriodsTypeHandler();

    @Test
    public void testSetNonNullParameterPreparedStatement() throws SQLException {
        PreparedStatement preparedStatement = createMock(PreparedStatement.class);
        PGobject pgobject = new PGobject();
        Set<Integer> periods = buildPeriods();
        pgobject.setValue(typeHandler.serialize(periods));
        pgobject.setType("jsonb");
        preparedStatement.setObject(PARAMETER_INDEX, pgobject);
        expectLastCall().once();
        replay(preparedStatement);
        typeHandler.setNonNullParameter(preparedStatement, PARAMETER_INDEX, periods, null);
        verify(preparedStatement);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNullableResultColumnName() throws SQLException {
        ResultSet resultSet = createMock(ResultSet.class);
        try {
            expect(resultSet.getString(COLUMN_NAME)).andReturn(null).once();
            resultSet.close();
            expectLastCall().once();
            replay(resultSet);
            typeHandler.getNullableResult(resultSet, COLUMN_NAME);
            fail();
        } finally {
            resultSet.close();
        }
        verify(resultSet);
    }

    @Test
    public void testGetNullableResultColumnIndex() throws SQLException {
        ResultSet resultSet = createMock(ResultSet.class);
        try {
            Set<Integer> periods = buildPeriods();
            String json = typeHandler.serialize(periods);
            expect(resultSet.getString(COLUMN_INDEX)).andReturn(json).once();
            resultSet.close();
            expectLastCall().once();
            replay(resultSet);
            assertEquals(periods, typeHandler.getNullableResult(resultSet, COLUMN_INDEX));
        } finally {
            resultSet.close();
        }
        verify(resultSet);
    }

    @Test
    public void testGetNullableResultCallableStatement() throws SQLException {
        CallableStatement callableStatement = createMock(CallableStatement.class);
        Set<Integer> periods = buildPeriods();
        String json = typeHandler.serialize(periods);
        expect(callableStatement.getString(PARAMETER_INDEX)).andReturn(json).once();
        replay(callableStatement);
        assertEquals(periods, typeHandler.getNullableResult(callableStatement, COLUMN_INDEX));
        verify(callableStatement);
    }

    private Set<Integer> buildPeriods() {
        return Sets.newHashSet(202106, 202112);
    }
}
