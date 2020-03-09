package com.copyright.rup.dist.foreign.repository.impl.converter;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;
import com.copyright.rup.dist.foreign.repository.impl.converter.json.AaclScenarioFieldsJsonMapper;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.postgresql.util.PGobject;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of {@link BaseTypeHandler} for {@link AaclFields}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 03/11/2020
 *
 * @author Aliaksandr Liakh
 */
public class AaclScenarioFieldsTypeHandler extends BaseTypeHandler<AaclFields> {

    private static final AaclScenarioFieldsJsonMapper JSON_MAPPER = new AaclScenarioFieldsJsonMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, AaclFields parameter, JdbcType jdbcType)
        throws SQLException {
        PGobject pgobject = new PGobject();
        pgobject.setValue(serialize(parameter));
        pgobject.setType("jsonb");
        ps.setObject(i, pgobject);
    }

    @Override
    public AaclFields getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return deserialize(rs.getString(columnName));
    }

    @Override
    public AaclFields getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return deserialize(rs.getString(columnIndex));
    }

    @Override
    public AaclFields getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return deserialize(cs.getString(columnIndex));
    }

    /**
     * Serializes an instance of {@link AaclFields} into a string JSON.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param aaclFields the instance of {@link AaclFields}
     * @return the string JSON
     */
    String serialize(AaclFields aaclFields) {
        try {
            return JSON_MAPPER.serialize(aaclFields);
        } catch (JsonProcessingException e) {
            throw new RupRuntimeException("Unable to serialize AACL scenario fields", e);
        }
    }

    /**
     * Deserializes a string JSON into an instance of {@link AaclFields}.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param json the string JSON
     * @return the instance of {@link AaclFields}
     */
    AaclFields deserialize(String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return JSON_MAPPER.deserialize(json);
        } catch (IOException e) {
            throw new RupRuntimeException("Unable to deserialize AACL scenario fields", e);
        }
    }
}
