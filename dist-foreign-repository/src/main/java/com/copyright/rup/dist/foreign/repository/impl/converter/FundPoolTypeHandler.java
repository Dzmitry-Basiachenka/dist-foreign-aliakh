package com.copyright.rup.dist.foreign.repository.impl.converter;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.repository.impl.converter.json.FundPoolJsonMapper;

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
 * Implementation of {@link BaseTypeHandler} for {@link FundPool}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 12/03/2018
 *
 * @author Aliaksandr Liakh
 */
public class FundPoolTypeHandler extends BaseTypeHandler<FundPool> {

    private static final FundPoolJsonMapper JSON_MAPPER = new FundPoolJsonMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, FundPool parameter, JdbcType jdbcType)
        throws SQLException {
        PGobject pgobject = new PGobject();
        pgobject.setValue(serialize(parameter));
        pgobject.setType("jsonb");
        ps.setObject(i, pgobject);
    }

    @Override
    public FundPool getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return deserialize(rs.getString(columnName));
    }

    @Override
    public FundPool getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return deserialize(rs.getString(columnIndex));
    }

    @Override
    public FundPool getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return deserialize(cs.getString(columnIndex));
    }

    /**
     * Serializes an instance of {@link FundPool} into a string JSON.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param fundPool the instance of {@link FundPool}
     * @return the string JSON
     */
    String serialize(FundPool fundPool) {
        try {
            return JSON_MAPPER.serialize(fundPool);
        } catch (JsonProcessingException e) {
            throw new RupRuntimeException("Unable to serialize fund pool", e);
        }
    }

    /**
     * Deserializes a string JSON into an instance of {@link FundPool}.
     * If the argument is {@code null}, the method returns {@code null}.
     *
     * @param json the string JSON
     * @return the instance of {@link FundPool}
     */
    FundPool deserialize(String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return JSON_MAPPER.deserialize(json);
        } catch (IOException e) {
            throw new RupRuntimeException("Unable to deserialize fund pool", e);
        }
    }
}
