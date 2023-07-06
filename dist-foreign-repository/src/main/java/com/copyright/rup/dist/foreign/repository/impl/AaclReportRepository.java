package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IAaclReportRepository;
import com.copyright.rup.dist.foreign.repository.impl.csv.aacl.AaclBaselineUsagesCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.aacl.AaclExcludeDetailsByPayeeCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.aacl.AaclScenarioUsagesCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.aacl.AaclUndistributedLiabilitiesReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.aacl.AaclUsageCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.aacl.AuditAaclCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.aacl.SendForClassificationCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.aacl.WorkSharesByAggLcClassReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.aacl.WorkSharesByAggLcClassSummaryReportHandler;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import io.micrometer.core.annotation.Timed;

/**
 * Implementation of {@link IAaclReportRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/23/2022
 *
 * @author Dzmitry Basiachenka
 */
@Repository
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class AaclReportRepository extends CommonReportRepository implements IAaclReportRepository {

    private static final String FILTER_KEY = "filter";
    private static final String SCENARIO_ID_KEY = "scenarioId";
    private static final String PRODUCT_FAMILY = "productFamily";
    private static final String STATUSES = "statuses";

    @Override
    public void writeWorkSharesByAggLcClassSummaryCsvReport(String scenarioId, ScenarioStatusEnum status,
                                                            OutputStream outputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put("status", Objects.requireNonNull(status));
        parameters.put("archivedStatuses", List.of(ScenarioStatusEnum.SENT_TO_LM, ScenarioStatusEnum.ARCHIVED));
        try (WorkSharesByAggLcClassSummaryReportHandler handler =
                 new WorkSharesByAggLcClassSummaryReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IAaclReportMapper.findWorkSharesByAggLcClassSummaryReportDtos", parameters, handler);
        }
    }

    @Override
    public void writeWorkSharesByAggLcClassCsvReport(String scenarioId, ScenarioStatusEnum status,
                                                     OutputStream outputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put("status", Objects.requireNonNull(status));
        parameters.put("archivedStatuses", List.of(ScenarioStatusEnum.SENT_TO_LM, ScenarioStatusEnum.ARCHIVED));
        try (WorkSharesByAggLcClassReportHandler handler =
                 new WorkSharesByAggLcClassReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IAaclReportMapper.findWorkSharesByAggLcClassReportDtos", parameters, handler);
        }
    }

    @Override
    public void writeAaclUsagesCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
        writeCsvReportByParts("IAaclReportMapper.findAaclUsagesCountByFilter",
            "IAaclReportMapper.findAaclUsageReportDtos", parameters, !filter.isEmpty(),
            () -> new AaclUsageCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public Set<String> writeUsagesForClassificationAndFindIds(UsageFilter filter, OutputStream outputStream) {
        Set<String> usageIds = new HashSet<>();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
        writeCsvReportByParts("IAaclReportMapper.findAaclUsagesCountByFilter",
            "IAaclReportMapper.findAaclUsageReportDtos", parameters, !Objects.requireNonNull(filter).isEmpty(),
            () -> new SendForClassificationCsvReportHandler(Objects.requireNonNull(outputStream), usageIds));
        return usageIds;
    }

    @Override
    public void writeAuditAaclCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(1);
        parameters.put(FILTER_KEY, escapeSqlLikePattern(filter));
        writeCsvReportByParts("IAaclReportMapper.findAaclUsagesCountForAudit",
            "IAaclReportMapper.findAuditAaclReportDtos", parameters, !Objects.requireNonNull(filter).isEmpty(),
            () -> new AuditAaclCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeArchivedAaclScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        writeCsvReportByParts("IAaclReportMapper.findArchivedScenarioUsageDtosCount",
            "IAaclReportMapper.findAaclArchivedScenarioUsageReportDtos", parameters,
            () -> new AaclScenarioUsagesCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeAaclScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        writeCsvReportByParts("IAaclReportMapper.findScenarioUsageDtosCount",
            "IAaclReportMapper.findAaclScenarioUsageReportDtos", parameters,
            () -> new AaclScenarioUsagesCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeAaclBaselineUsagesCsvReport(int numberOfYears, OutputStream outputStream) {
        try (AaclBaselineUsagesCsvReportHandler handler =
                 new AaclBaselineUsagesCsvReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IAaclReportMapper.findAaclBaselineUsages", numberOfYears, handler);
        }
    }

    @Override
    public void writeAaclExcludeDetailsByPayeeCsvReport(ExcludePayeeFilter filter, Set<Long> selectedAccountNumbers,
                                                        PipedOutputStream pipedOutputStream) {
        try (AaclExcludeDetailsByPayeeCsvReportHandler handler = new AaclExcludeDetailsByPayeeCsvReportHandler(
            Objects.requireNonNull(pipedOutputStream),
            Objects.requireNonNull(selectedAccountNumbers))) {
            if (!Objects.requireNonNull(filter).isEmpty()) {
                getTemplate().select("IAaclUsageMapper.findPayeeTotalHoldersByFilter",
                    ImmutableMap.of(FILTER_KEY, filter), handler);
            }
        }
    }

    @Override
    public void writeAaclUndistributedLiabilitiesCsvReport(OutputStream outputStream) {
        try (AaclUndistributedLiabilitiesReportHandler handler =
                 new AaclUndistributedLiabilitiesReportHandler(Objects.requireNonNull(outputStream))) {
            Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
            parameters.put("serviceFee", 0.25);
            parameters.put(PRODUCT_FAMILY, FdaConstants.AACL_PRODUCT_FAMILY);
            parameters.put(STATUSES, List.of(ScenarioStatusEnum.IN_PROGRESS,
                ScenarioStatusEnum.SUBMITTED, ScenarioStatusEnum.APPROVED));
            getTemplate().select("IAaclReportMapper.findAaclUndistributedLiabilitiesReportFundPools", parameters,
                handler);
        }
    }
}
