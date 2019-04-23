package com.copyright.rup.dist.foreign.repository.impl.converter;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.foreign.domain.Scenario.NtsFields;
import com.copyright.rup.dist.foreign.repository.impl.converter.json.NtsFieldsJsonMapper;

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
 * Implementation of {@link BaseTypeHandler} for {@link NtsFields}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/17/2019
 *
 * @author Aliaksandr Liakh
 */
public class NtsFieldsTypeHandler extends BaseTypeHandler<NtsFields> {

    private static final NtsFieldsJsonMapper JSON_MAPPER = new NtsFieldsJsonMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, NtsFields parameter, JdbcType jdbcType)
        throws SQLException {
        PGobject pgobject = new PGobject();
        pgobject.setValue(serialize(parameter));
        pgobject.setType("jsonb");
        ps.setObject(i, pgobject);
    }

    @Override
    public NtsFields getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return deserialize(rs.getString(columnName));
    }

    @Override
    public NtsFields getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return deserialize(rs.getString(columnIndex));
    }

    @Override
    public NtsFields getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return deserialize(cs.getString(columnIndex));
    }

    /**
     * Serializes an instance of {@link NtsFields} into a string JSON.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param ntsFields the instance of {@link NtsFields}
     * @return the string JSON
     */
    String serialize(NtsFields ntsFields) {
        try {
            return JSON_MAPPER.serialize(ntsFields);
        } catch (JsonProcessingException e) {
            throw new RupRuntimeException("Unable to serialize NTS fields", e);
        }
    }

    /**
     * Deserializes a string JSON into an instance of {@link NtsFields}.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param json the string JSON
     * @return the instance of {@link NtsFields}
     */
    NtsFields deserialize(String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return JSON_MAPPER.deserialize(json);
        } catch (IOException e) {
            throw new RupRuntimeException("Unable to deserialize NTS fields", e);
        }
    }
}
