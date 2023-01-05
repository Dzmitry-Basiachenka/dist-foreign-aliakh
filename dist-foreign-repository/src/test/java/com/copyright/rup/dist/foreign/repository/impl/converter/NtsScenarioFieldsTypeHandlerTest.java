package com.copyright.rup.dist.foreign.repository.impl.converter;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.foreign.domain.Scenario.NtsFields;

import org.junit.Test;
import org.postgresql.util.PGobject;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Verifies {@link NtsScenarioFieldsTypeHandler}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/17/2019
 *
 * @author Aliaksandr Liakh
 */
public class NtsScenarioFieldsTypeHandlerTest {

    private static final String COLUMN_NAME = "nts_fields";
    private static final int COLUMN_INDEX = 1;
    private static final int PARAMETER_INDEX = 1;

    private final NtsScenarioFieldsTypeHandler typeHandler = new NtsScenarioFieldsTypeHandler();

    @Test
    public void testSetNonNullParameterPreparedStatement() throws SQLException {
        PreparedStatement ps = createMock(PreparedStatement.class);
        PGobject pgobject = new PGobject();
        NtsFields ntsFields = buildNtsFields();
        String json = typeHandler.serialize(ntsFields);
        pgobject.setValue(json);
        pgobject.setType("jsonb");
        ps.setObject(PARAMETER_INDEX, pgobject);
        expectLastCall().once();
        replay(ps);
        typeHandler.setNonNullParameter(ps, PARAMETER_INDEX, ntsFields, null);
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
            NtsFields ntsFields = buildNtsFields();
            String json = typeHandler.serialize(ntsFields);
            expect(rs.getString(COLUMN_INDEX)).andReturn(json).once();
            rs.close();
            expectLastCall().once();
            replay(rs);
            assertEquals(ntsFields, typeHandler.getNullableResult(rs, COLUMN_INDEX));
        } finally {
            rs.close();
        }
        verify(rs);
    }

    @Test
    public void testGetNullableResultCallableStatement() throws SQLException {
        CallableStatement cs = createMock(CallableStatement.class);
        NtsFields ntsFields = buildNtsFields();
        String json = typeHandler.serialize(ntsFields);
        expect(cs.getString(PARAMETER_INDEX)).andReturn(json).once();
        replay(cs);
        assertEquals(ntsFields, typeHandler.getNullableResult(cs, COLUMN_INDEX));
        verify(cs);
    }

    private NtsFields buildNtsFields() {
        NtsFields ntsFields = new NtsFields();
        ntsFields.setRhMinimumAmount(new BigDecimal("300.0"));
        ntsFields.setPreServiceFeeAmount(new BigDecimal("500.0"));
        ntsFields.setPostServiceFeeAmount(new BigDecimal("1000.0"));
        return ntsFields;
    }
}
