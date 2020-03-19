package com.copyright.rup.dist.foreign.repository.impl.converter;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;

import com.copyright.rup.dist.foreign.domain.UsageAge;
import org.junit.Test;
import org.postgresql.util.PGobject;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Verifies {@link AaclScenarioFieldsTypeHandler}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 03/11/2020
 *
 * @author Aliaksandr Liakh
 */
public class AaclScenarioFieldsTypeHandlerTest {

    private static final String COLUMN_NAME = "aacl_fields";
    private static final int COLUMN_INDEX = 1;
    private static final int PARAMETER_INDEX = 1;

    private final AaclScenarioFieldsTypeHandler typeHandler = new AaclScenarioFieldsTypeHandler();

    @Test
    public void testSetNonNullParameterPreparedStatement() throws SQLException {
        PreparedStatement ps = createMock(PreparedStatement.class);
        PGobject pgobject = new PGobject();
        AaclFields aaclFields = buildAaclFields();
        String json = typeHandler.serialize(aaclFields);
        pgobject.setValue(json);
        pgobject.setType("jsonb");
        ps.setObject(PARAMETER_INDEX, pgobject);
        expectLastCall().once();
        replay(ps);
        typeHandler.setNonNullParameter(ps, PARAMETER_INDEX, aaclFields, null);
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
            AaclFields aaclFields = buildAaclFields();
            String json = typeHandler.serialize(aaclFields);
            expect(rs.getString(COLUMN_INDEX)).andReturn(json).once();
            rs.close();
            expectLastCall().once();
            replay(rs);
            assertEquals(aaclFields, typeHandler.getNullableResult(rs, COLUMN_INDEX));
        } finally {
            rs.close();
        }
        verify(rs);
    }

    @Test
    public void testGetNullableResultCallableStatement() throws SQLException {
        CallableStatement cs = createMock(CallableStatement.class);
        AaclFields aaclFields = buildAaclFields();
        String json = typeHandler.serialize(aaclFields);
        expect(cs.getString(PARAMETER_INDEX)).andReturn(json).once();
        replay(cs);
        assertEquals(aaclFields, typeHandler.getNullableResult(cs, COLUMN_INDEX));
        verify(cs);
    }

    private AaclFields buildAaclFields() {
        AaclFields aaclFields = new AaclFields();
        ArrayList<PublicationType> pubTypes = new ArrayList<>();
        PublicationType pubType = new PublicationType();
        pubType.setId("2fe9c0a0-7672-4b56-bc64-9d4125fecf6e");
        pubType.setWeight(new BigDecimal("1.00"));
        pubTypes.add(pubType);
        aaclFields.setPublicationTypes(pubTypes);
        List<UsageAge> usageAges = new ArrayList<>();
        UsageAge usageAge = new UsageAge();
        usageAge.setPeriod(2020);
        usageAge.setWeight(new BigDecimal("1.00"));
        usageAges.add(usageAge);
        aaclFields.setUsageAges(usageAges);
        return aaclFields;
    }
}
