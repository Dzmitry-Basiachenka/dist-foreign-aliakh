package com.copyright.rup.dist.foreign.repository.impl.converter;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.foreign.domain.Scenario.SalFields;

import org.junit.Test;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Verifies {@link SalScenarioFieldsTypeHandler}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 09/24/2020
 *
 * @author Ihar Suvorau
 */
public class SalScenarioFieldsTypeHandlerTest {

    private static final String COLUMN_NAME = "sal_fields";
    private static final int COLUMN_INDEX = 1;
    private static final int PARAMETER_INDEX = 1;

    private final SalScenarioFieldsTypeHandler typeHandler = new SalScenarioFieldsTypeHandler();

    @Test
    public void testSetNonNullParameterPreparedStatement() throws SQLException {
        PreparedStatement ps = createMock(PreparedStatement.class);
        PGobject pgobject = new PGobject();
        SalFields salFields = buildSalFields();
        String json = typeHandler.serialize(salFields);
        pgobject.setValue(json);
        pgobject.setType("jsonb");
        ps.setObject(PARAMETER_INDEX, pgobject);
        expectLastCall().once();
        replay(ps);
        typeHandler.setNonNullParameter(ps, PARAMETER_INDEX, salFields, null);
        verify(ps);
    }

    @Test
    public void testGetNullableResultColumnName() throws SQLException {
        ResultSet rs = createMock(ResultSet.class);
        try {
            expect(rs.getString(COLUMN_NAME)).andReturn(null).once();
            rs.close();
            expectLastCall().once();
            replay(rs);
            assertNull(typeHandler.getNullableResult(rs, COLUMN_NAME));
        } finally {
            rs.close();
        }
        verify(rs);
    }

    @Test
    public void testGetNullableResultColumnIndex() throws SQLException {
        ResultSet rs = createMock(ResultSet.class);
        try {
            SalFields salFields = buildSalFields();
            String json = typeHandler.serialize(salFields);
            expect(rs.getString(COLUMN_INDEX)).andReturn(json).once();
            rs.close();
            expectLastCall().once();
            replay(rs);
            assertEquals(salFields, typeHandler.getNullableResult(rs, COLUMN_INDEX));
        } finally {
            rs.close();
        }
        verify(rs);
    }

    @Test
    public void testGetNullableResultCallableStatement() throws SQLException {
        CallableStatement cs = createMock(CallableStatement.class);
        SalFields salFields = buildSalFields();
        String json = typeHandler.serialize(salFields);
        expect(cs.getString(PARAMETER_INDEX)).andReturn(json).once();
        replay(cs);
        assertEquals(salFields, typeHandler.getNullableResult(cs, COLUMN_INDEX));
        verify(cs);
    }

    private SalFields buildSalFields() {
        SalFields salFields = new SalFields();
        salFields.setFundPoolId("1fcc3b20-11eb-46d8-8361-04d21ffb6233");
        return salFields;
    }
}
