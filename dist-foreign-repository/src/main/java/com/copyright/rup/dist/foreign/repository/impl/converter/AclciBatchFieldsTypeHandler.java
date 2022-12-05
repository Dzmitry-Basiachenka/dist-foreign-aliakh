package com.copyright.rup.dist.foreign.repository.impl.converter;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.foreign.domain.UsageBatch.AclciFields;
import com.copyright.rup.dist.foreign.repository.impl.converter.json.AclciBatchFieldsJsonMapper;
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
 * Implementation of {@link BaseTypeHandler} for {@link AclciFields}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/08/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclciBatchFieldsTypeHandler extends BaseTypeHandler<AclciFields> {

    private static final AclciBatchFieldsJsonMapper JSON_MAPPER = new AclciBatchFieldsJsonMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, AclciFields parameter, JdbcType jdbcType)
        throws SQLException {
        PGobject pgobject = new PGobject();
        pgobject.setValue(serialize(parameter));
        pgobject.setType("jsonb");
        ps.setObject(i, pgobject);
    }

    @Override
    public AclciFields getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return deserialize(rs.getString(columnName));
    }

    @Override
    public AclciFields getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return deserialize(rs.getString(columnIndex));
    }

    @Override
    public AclciFields getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return deserialize(cs.getString(columnIndex));
    }

    /**
     * Serializes an instance of {@link AclciFields} into a string JSON.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param aclciFields the instance of {@link AclciFields}
     * @return the string JSON
     */
    String serialize(AclciFields aclciFields) {
        try {
            return JSON_MAPPER.serialize(aclciFields);
        } catch (JsonProcessingException e) {
            throw new RupRuntimeException("Unable to serialize ACLCI usage batch fields", e);
        }
    }

    /**
     * Deserializes a string JSON into an instance of {@link AclciFields}.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param json the string JSON
     * @return the instance of {@link AclciFields}
     */
    AclciFields deserialize(String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return JSON_MAPPER.deserialize(json);
        } catch (IOException e) {
            throw new RupRuntimeException("Unable to deserialize ACLCI usage batch fields", e);
        }
    }
}
