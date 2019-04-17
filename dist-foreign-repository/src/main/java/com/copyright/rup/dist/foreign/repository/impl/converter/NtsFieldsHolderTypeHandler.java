package com.copyright.rup.dist.foreign.repository.impl.converter;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.foreign.domain.NtsFieldsHolder;
import com.copyright.rup.dist.foreign.repository.impl.converter.json.NtsFieldsHolderJsonMapper;

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
 * Implementation of {@link BaseTypeHandler} for {@link NtsFieldsHolder}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/17/2019
 *
 * @author Aliaksandr Liakh
 */
public class NtsFieldsHolderTypeHandler extends BaseTypeHandler<NtsFieldsHolder> {

    private static final NtsFieldsHolderJsonMapper JSON_MAPPER = new NtsFieldsHolderJsonMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, NtsFieldsHolder parameter, JdbcType jdbcType)
        throws SQLException {
        PGobject pgobject = new PGobject();
        pgobject.setValue(serialize(parameter));
        pgobject.setType("jsonb");
        ps.setObject(i, pgobject);
    }

    @Override
    public NtsFieldsHolder getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return deserialize(rs.getString(columnName));
    }

    @Override
    public NtsFieldsHolder getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return deserialize(rs.getString(columnIndex));
    }

    @Override
    public NtsFieldsHolder getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return deserialize(cs.getString(columnIndex));
    }

    /**
     * Serializes an instance of {@link NtsFieldsHolder} into a string JSON.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param holder the instance of {@link NtsFieldsHolder}
     * @return the string JSON
     */
    String serialize(NtsFieldsHolder holder) {
        try {
            return JSON_MAPPER.serialize(holder);
        } catch (JsonProcessingException e) {
            throw new RupRuntimeException("Unable to serialize NTS fields holder", e);
        }
    }

    /**
     * Deserializes a string JSON into an instance of {@link NtsFieldsHolder}.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param json the string JSON
     * @return the instance of {@link NtsFieldsHolder}
     */
    NtsFieldsHolder deserialize(String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return JSON_MAPPER.deserialize(json);
        } catch (IOException e) {
            throw new RupRuntimeException("Unable to deserialize NTS fields holder", e);
        }
    }
}
