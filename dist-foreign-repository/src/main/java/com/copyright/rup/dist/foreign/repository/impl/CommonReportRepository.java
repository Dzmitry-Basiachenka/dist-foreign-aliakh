package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;

import java.util.Map;
import java.util.function.Supplier;

/**
 * Represents common logic for report generation.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/22/2022
 *
 * @author Anton Azarenka
 */
public class CommonReportRepository extends BaseRepository {

    private static final long serialVersionUID = -4630982319060097908L;
    private static final int REPORT_BATCH_SIZE = 100000;
    private static final String PAGEABLE_KEY = "pageable";

    /**
     * Writes CSV report by parts.
     *
     * @param countMethodName  count method name
     * @param selectMethodName select method name
     * @param parameters       map of parameters
     * @param precondition     precondition
     * @param handlerSupplier  supplier
     */
    protected void writeCsvReportByParts(String countMethodName, String selectMethodName,
                                         Map<String, Object> parameters,
                                         boolean precondition,
                                         Supplier<? extends BaseCsvReportHandler> handlerSupplier) {
        int size = selectOne(countMethodName, parameters);
        try (var handler = handlerSupplier.get()) {
            if (precondition && 0 < size) {
                for (int offset = 0; offset < size; offset += REPORT_BATCH_SIZE) {
                    parameters.put(PAGEABLE_KEY, new Pageable(offset, REPORT_BATCH_SIZE));
                    getTemplate().select(selectMethodName, parameters, handler);
                }
            }
        }
    }

    /**
     * Writes CSV report by parts.
     *
     * @param countMethodName  count method name
     * @param selectMethodName select method name
     * @param parameters       map of parameters
     * @param handlerSupplier  supplier
     */
    protected void writeCsvReportByParts(String countMethodName, String selectMethodName,
                                         Map<String, Object> parameters,
                                         Supplier<? extends BaseCsvReportHandler> handlerSupplier) {
        writeCsvReportByParts(countMethodName, selectMethodName, parameters, true, handlerSupplier);
    }

    /**
     * Escapes audit filter fields that are used in SQL LIKE pattern ('%', '_' and '\').
     *
     * @param auditFilter instance of {@link AuditFilter}
     * @return instance of {@link AuditFilter} with applied sql like pattern
     */
    protected AuditFilter escapeSqlLikePattern(AuditFilter auditFilter) {
        AuditFilter filterCopy = new AuditFilter(auditFilter);
        filterCopy.setCccEventId(escapeSqlLikePattern(filterCopy.getCccEventId()));
        filterCopy.setDistributionName(escapeSqlLikePattern(filterCopy.getDistributionName()));
        filterCopy.setSearchValue(escapeSqlLikePattern(filterCopy.getSearchValue()));
        return filterCopy;
    }
}
