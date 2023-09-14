package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclciReportRepository;
import com.copyright.rup.dist.foreign.repository.impl.csv.aclci.AclciUsageCsvReportHandler;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.io.PipedOutputStream;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of {@link IAclciReportRepository}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 04/13/2023
 *
 * @author Aliaksandr Liakh
 */
@Repository
public class AclciReportRepository extends CommonReportRepository implements IAclciReportRepository {

    @Override
    public void writeAclciUsagesCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(1);
        parameters.put("filter", Objects.requireNonNull(filter));
        writeCsvReportByParts("IAclciReportMapper.findAclciUsagesCountByFilter",
            "IAclciReportMapper.findAclciUsageReportDtos", parameters, !filter.isEmpty(),
            () -> new AclciUsageCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }
}
