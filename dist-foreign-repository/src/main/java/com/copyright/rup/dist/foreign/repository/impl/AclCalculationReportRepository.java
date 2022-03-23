package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclCalculationReportRepository;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.AclGrantDetailCsvReportHandler;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.io.PipedOutputStream;
import java.util.Map;
import java.util.Objects;

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
public class AclCalculationReportRepository extends CommonReportRepository implements IAclCalculationReportRepository {

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
}
