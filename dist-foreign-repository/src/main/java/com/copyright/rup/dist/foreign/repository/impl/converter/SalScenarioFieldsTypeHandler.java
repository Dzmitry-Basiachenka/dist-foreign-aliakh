package com.copyright.rup.dist.foreign.repository.impl.converter;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.foreign.domain.Scenario.SalFields;
import com.copyright.rup.dist.foreign.repository.impl.converter.json.SalScenarioFieldsJsonMapper;

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
 * Implementation of {@link BaseTypeHandler} for {@link SalFields}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 09/24/2020
 *
 * @author Ihar Suvorau
 */
public class SalScenarioFieldsTypeHandler extends BaseTypeHandler<SalFields> {

    private static final SalScenarioFieldsJsonMapper JSON_MAPPER = new SalScenarioFieldsJsonMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, SalFields parameter, JdbcType jdbcType)
        throws SQLException {
        PGobject pgobject = new PGobject();
        pgobject.setValue(serialize(parameter));
        pgobject.setType("jsonb");
        ps.setObject(i, pgobject);
    }

    @Override
    public SalFields getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return deserialize(rs.getString(columnName));
    }

    @Override
    public SalFields getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return deserialize(rs.getString(columnIndex));
    }

    @Override
    public SalFields getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return deserialize(cs.getString(columnIndex));
    }

    /**
     * Serializes an instance of {@link SalFields} into a string JSON.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param salFields the instance of {@link SalFields}
     * @return the string JSON
     */
    String serialize(SalFields salFields) {
        try {
            return JSON_MAPPER.serialize(salFields);
        } catch (JsonProcessingException e) {
            throw new RupRuntimeException("Unable to serialize SAL scenario fields", e);
        }
    }

    /**
     * Deserializes a string JSON into an instance of {@link SalFields}.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param json the string JSON
     * @return the instance of {@link SalFields}
     */
    SalFields deserialize(String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return JSON_MAPPER.deserialize(json);
        } catch (IOException e) {
            throw new RupRuntimeException("Unable to deserialize SAL scenario fields", e);
        }
    }
}
