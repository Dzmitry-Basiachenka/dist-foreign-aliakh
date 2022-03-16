package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclCalculationReportRepository;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.AclGrantDetailCsvReportHandler;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.io.PipedOutputStream;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Implementation of {@link IAclCalculationReportRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/15/2022
 *
 * @author Dzmitry Basiachenka
 */
@Repository
public class AclCalculationReportRepository extends BaseRepository implements IAclCalculationReportRepository {

    private static final int REPORT_UDM_BATCH_SIZE = 100000;
    private static final String PAGEABLE_KEY = "pageable";

    @Override
    public void writeAclGrantDetailCsvReport(AclGrantDetailFilter filter, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("filter", Objects.requireNonNull(filter));
        writeCsvReportByParts("IAclCalculationReportMapper.findAclGrantDetailsCountByFilter",
            "IAclCalculationReportMapper.findAclGrantDetailDtosByFilter", parameters, !filter.isEmpty(),
            () -> new AclGrantDetailCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
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
