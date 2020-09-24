package com.copyright.rup.dist.foreign.repository.impl.converter;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.repository.impl.converter.json.SalFundPoolFieldsJsonMapper;

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
 * Implementation of {@link BaseTypeHandler} for {@link FundPool.SalFields}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/23/2020
 *
 * @author Uladzislau Shalamitski
 */
public class SalFundPoolFieldsTypeHandler extends BaseTypeHandler<FundPool.SalFields> {

    private static final SalFundPoolFieldsJsonMapper JSON_MAPPER = new SalFundPoolFieldsJsonMapper();

    @Override
    public void setNonNullParameter(PreparedStatement statement, int parameterIndex,
                                    FundPool.SalFields parameter, JdbcType jdbcType)
        throws SQLException {
        PGobject pgobject = new PGobject();
        pgobject.setValue(serialize(parameter));
        pgobject.setType("jsonb");
        statement.setObject(parameterIndex, pgobject);
    }

    @Override
    public FundPool.SalFields getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return deserialize(resultSet.getString(columnName));
    }

    @Override
    public FundPool.SalFields getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return deserialize(resultSet.getString(columnIndex));
    }

    @Override
    public FundPool.SalFields getNullableResult(CallableStatement statement, int columnIndex) throws SQLException {
        return deserialize(statement.getString(columnIndex));
    }

    /**
     * Serializes an instance of {@link FundPool.SalFields} into a string JSON.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param salFields the instance of {@link FundPool.SalFields}
     * @return the string JSON
     */
    String serialize(FundPool.SalFields salFields) {
        try {
            return JSON_MAPPER.serialize(salFields);
        } catch (JsonProcessingException e) {
            throw new RupRuntimeException("Unable to serialize SAL fund pool fields", e);
        }
    }

    /**
     * Deserializes a string JSON into an instance of {@link FundPool.SalFields}.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param json the string JSON
     * @return the instance of {@link FundPool.SalFields}
     */
    FundPool.SalFields deserialize(String json) {
        try {
            return StringUtils.isNotEmpty(json) ? JSON_MAPPER.deserialize(json) : null;
        } catch (IOException e) {
            throw new RupRuntimeException("Unable to deserialize SAL fund pool fields", e);
        }
    }
}
