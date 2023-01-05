package com.copyright.rup.dist.foreign.repository.impl.converter;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.foreign.domain.UsageBatch.NtsFields;

import org.junit.Test;
import org.postgresql.util.PGobject;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * Verifies {@link NtsBatchFieldsTypeHandler}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 12/03/2018
 *
 * @author Aliaksandr Liakh
 */
public class NtsBatchFieldsTypeHandlerTest {

    private static final String COLUMN_NAME = "nts_fields";
    private static final int COLUMN_INDEX = 1;
    private static final int PARAMETER_INDEX = 1;

    private final NtsBatchFieldsTypeHandler typeHandler = new NtsBatchFieldsTypeHandler();

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
        ntsFields.setFundPoolPeriodFrom(2017);
        ntsFields.setFundPoolPeriodTo(2018);
        ntsFields.setStmAmount(new BigDecimal(100));
        ntsFields.setNonStmAmount(new BigDecimal(200));
        ntsFields.setStmMinimumAmount(new BigDecimal(300));
        ntsFields.setNonStmMinimumAmount(new BigDecimal(400));
        ntsFields.setMarkets(Set.of("Edu", "Gov"));
        return ntsFields;
    }
}
