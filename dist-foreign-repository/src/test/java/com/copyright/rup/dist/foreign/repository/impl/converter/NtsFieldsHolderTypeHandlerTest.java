package com.copyright.rup.dist.foreign.repository.impl.converter;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.foreign.domain.NtsFieldsHolder;

import org.junit.Test;
import org.postgresql.util.PGobject;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Verifies {@link NtsFieldsHolderTypeHandler}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/17/2019
 *
 * @author Aliaksandr Liakh
 */
public class NtsFieldsHolderTypeHandlerTest {

    private static final String COLUMN_NAME = "nts_fields_holder";
    private static final int COLUMN_INDEX = 1;
    private static final int PARAMETER_INDEX = 1;

    private final NtsFieldsHolderTypeHandler typeHandler = new NtsFieldsHolderTypeHandler();

    @Test
    public void testSetNonNullParameterPreparedStatement() throws SQLException {
        PreparedStatement ps = createMock(PreparedStatement.class);
        PGobject pgobject = new PGobject();
        NtsFieldsHolder holder = buildNtsFieldsHolder();
        String json = typeHandler.serialize(holder);
        pgobject.setValue(json);
        pgobject.setType("jsonb");
        ps.setObject(PARAMETER_INDEX, pgobject);
        expectLastCall().once();
        replay(ps);
        typeHandler.setNonNullParameter(ps, PARAMETER_INDEX, holder, null);
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
            NtsFieldsHolder holder = buildNtsFieldsHolder();
            String json = typeHandler.serialize(holder);
            expect(rs.getString(COLUMN_INDEX)).andReturn(json).once();
            rs.close();
            expectLastCall().once();
            replay(rs);
            assertEquals(holder, typeHandler.getNullableResult(rs, COLUMN_INDEX));
        } finally {
            rs.close();
        }
        verify(rs);
    }

    @Test
    public void testGetNullableResultCallableStatement() throws SQLException {
        CallableStatement cs = createMock(CallableStatement.class);
        NtsFieldsHolder holder = buildNtsFieldsHolder();
        String json = typeHandler.serialize(holder);
        expect(cs.getString(PARAMETER_INDEX)).andReturn(json).once();
        replay(cs);
        assertEquals(holder, typeHandler.getNullableResult(cs, COLUMN_INDEX));
        verify(cs);
    }

    private NtsFieldsHolder buildNtsFieldsHolder() {
        NtsFieldsHolder holder = new NtsFieldsHolder();
        holder.setRhMinimumAmount(new BigDecimal("300.00"));
        return holder;
    }
}
