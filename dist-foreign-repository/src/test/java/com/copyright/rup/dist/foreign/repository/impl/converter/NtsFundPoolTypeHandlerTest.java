package com.copyright.rup.dist.foreign.repository.impl.converter;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.foreign.domain.NtsFundPool;

import com.google.common.collect.ImmutableSet;

import org.junit.Test;
import org.postgresql.util.PGobject;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Verifies {@link FundPoolTypeHandler}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 12/03/2018
 *
 * @author Aliaksandr Liakh
 */
public class NtsFundPoolTypeHandlerTest {

    private static final String COLUMN_NAME = "fund_pool";
    private static final int COLUMN_INDEX = 1;
    private static final int PARAMETER_INDEX = 1;

    private final FundPoolTypeHandler typeHandler = new FundPoolTypeHandler();

    @Test
    public void testSetNonNullParameterPreparedStatement() throws SQLException {
        PreparedStatement ps = createMock(PreparedStatement.class);
        PGobject pgobject = new PGobject();
        NtsFundPool ntsFundPool = buildFundPool();
        String json = typeHandler.serialize(ntsFundPool);
        pgobject.setValue(json);
        pgobject.setType("jsonb");
        ps.setObject(PARAMETER_INDEX, pgobject);
        expectLastCall().once();
        replay(ps);
        typeHandler.setNonNullParameter(ps, PARAMETER_INDEX, ntsFundPool, null);
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
            NtsFundPool ntsFundPool = buildFundPool();
            String json = typeHandler.serialize(ntsFundPool);
            expect(rs.getString(COLUMN_INDEX)).andReturn(json).once();
            rs.close();
            expectLastCall().once();
            replay(rs);
            assertEquals(ntsFundPool, typeHandler.getNullableResult(rs, COLUMN_INDEX));
        } finally {
            rs.close();
        }
        verify(rs);
    }

    @Test
    public void testGetNullableResultCallableStatement() throws SQLException {
        CallableStatement cs = createMock(CallableStatement.class);
        NtsFundPool ntsFundPool = buildFundPool();
        String json = typeHandler.serialize(ntsFundPool);
        expect(cs.getString(PARAMETER_INDEX)).andReturn(json).once();
        replay(cs);
        assertEquals(ntsFundPool, typeHandler.getNullableResult(cs, COLUMN_INDEX));
        verify(cs);
    }

    private NtsFundPool buildFundPool() {
        NtsFundPool ntsFundPool = new NtsFundPool();
        ntsFundPool.setFundPoolPeriodFrom(2017);
        ntsFundPool.setFundPoolPeriodTo(2018);
        ntsFundPool.setStmAmount(new BigDecimal(100));
        ntsFundPool.setNonStmAmount(new BigDecimal(200));
        ntsFundPool.setStmMinimumAmount(new BigDecimal(300));
        ntsFundPool.setNonStmMinimumAmount(new BigDecimal(400));
        ntsFundPool.setMarkets(ImmutableSet.of("Edu", "Gov"));
        return ntsFundPool;
    }
}
