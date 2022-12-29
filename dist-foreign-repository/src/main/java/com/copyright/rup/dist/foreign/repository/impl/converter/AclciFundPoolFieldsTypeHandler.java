package com.copyright.rup.dist.foreign.repository.impl.converter;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.repository.impl.converter.json.AclciFundPoolFieldsJsonMapper;

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
 * Implementation of {@link BaseTypeHandler} for {@link FundPool.AclciFields}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/28/2022
 *
 * @author Mikita Maistrenka
 */
public class AclciFundPoolFieldsTypeHandler extends BaseTypeHandler<FundPool.AclciFields> {

    private static final AclciFundPoolFieldsJsonMapper JSON_MAPPER = new AclciFundPoolFieldsJsonMapper();

    @Override
    public void setNonNullParameter(PreparedStatement statement, int parameterIndex,
                                    FundPool.AclciFields parameter, JdbcType jdbcType)
        throws SQLException {
        PGobject pgobject = new PGobject();
        pgobject.setValue(serialize(parameter));
        pgobject.setType("jsonb");
        statement.setObject(parameterIndex, pgobject);
    }

    @Override
    public FundPool.AclciFields getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return deserialize(resultSet.getString(columnName));
    }

    @Override
    public FundPool.AclciFields getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return deserialize(resultSet.getString(columnIndex));
    }

    @Override
    public FundPool.AclciFields getNullableResult(CallableStatement statement, int columnIndex) throws SQLException {
        return deserialize(statement.getString(columnIndex));
    }

    /**
     * Serializes an instance of {@link FundPool.AclciFields} into a string JSON.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param aclciFields the instance of {@link FundPool.AclciFields}
     * @return the string JSON
     */
    String serialize(FundPool.AclciFields aclciFields) {
        try {
            return JSON_MAPPER.serialize(aclciFields);
        } catch (JsonProcessingException e) {
            throw new RupRuntimeException("Unable to serialize ACLCI fund pool fields", e);
        }
    }

    /**
     * Deserializes a string JSON into an instance of {@link FundPool.AclciFields}.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param json the string JSON
     * @return the instance of {@link FundPool.AclciFields}
     */
    FundPool.AclciFields deserialize(String json) {
        try {
            return StringUtils.isNotEmpty(json) ? JSON_MAPPER.deserialize(json) : null;
        } catch (IOException e) {
            throw new RupRuntimeException("Unable to deserialize ACLCI fund pool fields", e);
        }
    }
}
