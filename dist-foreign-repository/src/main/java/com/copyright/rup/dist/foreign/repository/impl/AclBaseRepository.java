package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * Represents repository case for ACL product family.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 24/01/2022
 *
 * @author Aliaksandr Liakh
 */
class AclBaseRepository extends BaseRepository {

    private static final long serialVersionUID = 653365730630923022L;

    /**
     * Escapes filter expression that is used inside a MyBatis 'sql' fragment.
     * (MyBatis properties ${} do not replaced ' with '' in comparison with parameters #{})
     *
     * @param filterExpression filter expression
     * @return filter expression with escaped first value
     * @link https://mybatis.org/mybatis-3/sqlmap-xml.html#String_Substitution
     */
    protected FilterExpression<String> escapePropertyForMyBatisSqlFragment(FilterExpression<String> filterExpression) {
        return Objects.nonNull(filterExpression.getOperator())
            ? new FilterExpression<>(filterExpression.getOperator(),
            StringUtils.replaceEach(filterExpression.getFieldFirstValue(),
                new String[]{"\\", "%", "_", "'"}, new String[]{"\\\\", "\\%", "\\_", "''"}),
            filterExpression.getFieldSecondValue())
            : filterExpression;
    }
}
