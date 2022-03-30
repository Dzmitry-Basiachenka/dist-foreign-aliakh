package com.copyright.rup.dist.foreign.repository.impl.converter;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.foreign.repository.impl.converter.json.PeriodsJsonMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.postgresql.util.PGobject;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * Implementation of {@link BaseTypeHandler} for {@link Set} of periods.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/26/2022
 *
 * @author Aliaksandr Liakh
 */
public class PeriodsTypeHandler extends BaseTypeHandler<Set<Integer>> {

    private static final PeriodsJsonMapper JSON_MAPPER = new PeriodsJsonMapper();

    @Override
    public void setNonNullParameter(PreparedStatement statement, int parameterIndex,
                                    Set<Integer> parameter, JdbcType jdbcType)
        throws SQLException {
        PGobject pgobject = new PGobject();
        pgobject.setValue(serialize(parameter));
        pgobject.setType("jsonb");
        statement.setObject(parameterIndex, pgobject);
    }

    @Override
    public Set<Integer> getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return deserialize(resultSet.getString(columnName));
    }

    @Override
    public Set<Integer> getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return deserialize(resultSet.getString(columnIndex));
    }

    @Override
    public Set<Integer> getNullableResult(CallableStatement statement, int columnIndex) throws SQLException {
        return deserialize(statement.getString(columnIndex));
    }

    /**
     * Serializes an instance of {@link Set} of ACL grant set periods into a string JSON.
     *
     * @param periods the instance of {@link Set} of ACL grant set periods
     * @return the string JSON
     */
    String serialize(Set<Integer> periods) {
        try {
            return JSON_MAPPER.serialize(periods);
        } catch (JsonProcessingException e) {
            throw new RupRuntimeException("Unable to serialize ACL grant set periods", e);
        }
    }

    /**
     * Deserializes a string JSON into an instance of {@link Set} of ACL grant set periods.
     *
     * @param json the string JSON
     * @return the instance of {@link Set} of ACL grant set periods
     */
    Set<Integer> deserialize(String json) {
        try {
            return JSON_MAPPER.deserialize(json);
        } catch (IOException e) {
            throw new RupRuntimeException("Unable to deserialize ACL grant set periods", e);
        }
    }
}
