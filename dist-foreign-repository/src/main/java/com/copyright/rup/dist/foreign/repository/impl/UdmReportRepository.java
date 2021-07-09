package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmReportRepository;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.UdmUsageCsvReportHandlerResearcher;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.UdmUsageCsvReportHandlerSpecialistManager;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.UdmUsageCsvReportHandlerView;

import org.springframework.stereotype.Repository;

import java.io.PipedOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Implementation of {@link IUdmReportRepository}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 07/01/2021
 *
 * @author Dzmitry Basiachenka
 */
@Repository
public class UdmReportRepository extends BaseRepository implements IUdmReportRepository {

    private static final int REPORT_UDM_BATCH_SIZE = 100000;
    private static final String PAGEABLE_KEY = "pageable";

    @Override
    public void writeUdmUsageCsvReportSpecialistManager(UdmUsageFilter filter, PipedOutputStream pipedOutputStream) {
        writeUdmUsageCsvReport(filter,
            new UdmUsageCsvReportHandlerSpecialistManager(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeUdmUsageCsvReportResearcher(UdmUsageFilter filter, PipedOutputStream pipedOutputStream) {
        writeUdmUsageCsvReport(filter,
            new UdmUsageCsvReportHandlerResearcher(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeUdmUsageCsvReportView(UdmUsageFilter filter, PipedOutputStream pipedOutputStream) {
        writeUdmUsageCsvReport(filter, new UdmUsageCsvReportHandlerView(Objects.requireNonNull(pipedOutputStream)));
    }

    private void writeUdmUsageCsvReport(UdmUsageFilter filter, BaseCsvReportHandler handler) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("filter", escapeSqlLikePattern(Objects.requireNonNull(filter)));
        writeCsvReportByParts("IUdmReportMapper.findUdmUsagesCountByFilter",
            "IUdmReportMapper.findUdmUsageDtosByFilter", parameters, !filter.isEmpty(), () -> handler);
    }

    private UdmUsageFilter escapeSqlLikePattern(UdmUsageFilter udmUsageFilter) {
        UdmUsageFilter filterCopy = new UdmUsageFilter(udmUsageFilter);
        filterCopy.setSearchValue(escapeSqlLikePattern(filterCopy.getSearchValue()));
        return filterCopy;
    }

    private void writeCsvReportByParts(String countMethodName, String selectMethodName, Map<String, Object> parameters,
                                       boolean precondition, Supplier<? extends BaseCsvReportHandler> handlerSupplier) {
        int size = selectOne(countMethodName, parameters);
        try (BaseCsvReportHandler handler = handlerSupplier.get()) {
            if (precondition && 0 < size) {
                for (int offset = 0; offset < size; offset += REPORT_UDM_BATCH_SIZE) {
                    parameters.put(PAGEABLE_KEY, new Pageable(offset, REPORT_UDM_BATCH_SIZE));
                    getTemplate().select(selectMethodName, parameters, handler);
                }
            }
        }
    }
}
