package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.ISalReportRepository;
import com.copyright.rup.dist.foreign.repository.impl.csv.sal.AuditSalCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.sal.SalFundPoolsExportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.sal.SalFundPoolsReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.sal.SalHistoricalItemBankDetailsReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.sal.SalLiabilitiesByRhReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.sal.SalLiabilitiesSummaryByRhAndWorkReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.sal.SalScenarioUsagesCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.sal.SalUndistributedLiabilitiesReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.sal.SalUsageCsvReportHandler;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementation of {@link ISalReportRepository}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 06/25/2019
 *
 * @author Ihar Suvorau
 */
@Repository
public class SalReportRepository extends CommonReportRepository implements ISalReportRepository {

    private static final String FILTER_KEY = "filter";
    private static final String SCENARIO_ID_KEY = "scenarioId";
    private static final String PRODUCT_FAMILY = "productFamily";
    private static final String STATUSES = "statuses";

    @Override
    public void writeSalUsagesCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
        writeCsvReportByParts("ISalReportMapper.findSalUsagesCountByFilter", "ISalReportMapper.findSalUsageReportDtos",
            parameters, !filter.isEmpty(),
            () -> new SalUsageCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeAuditSalCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(1);
        parameters.put(FILTER_KEY, escapeSqlLikePattern(filter));
        writeCsvReportByParts("ISalReportMapper.findSalUsagesCountForAudit", "ISalReportMapper.findAuditSalReportDtos",
            parameters, !Objects.requireNonNull(filter).isEmpty(),
            () -> new AuditSalCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeSalLiabilitiesByRhCsvReport(List<Scenario> scenarios, OutputStream outputStream) {
        try (SalLiabilitiesByRhReportHandler handler =
                 new SalLiabilitiesByRhReportHandler(Objects.requireNonNull(outputStream))) {
            Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(4);
            parameters.put(PRODUCT_FAMILY, FdaConstants.SAL_PRODUCT_FAMILY);
            parameters.put("itemBankType", SalDetailTypeEnum.IB);
            parameters.put("usageDetailType", SalDetailTypeEnum.UD);
            parameters.put("scenarioIds", Objects.requireNonNull(scenarios).stream()
                .map(Scenario::getId)
                .collect(Collectors.toList()));
            getTemplate().select("ISalReportMapper.findSalLiabilitiesByRhReportDtos", parameters, handler);
            handler.writeScenarioNames(scenarios);
        }
    }

    @Override
    public void writeSalLiabilitiesSummaryByRhAndWorkCsvReport(List<Scenario> scenarios,
                                                               OutputStream outputStream) {
        try (SalLiabilitiesSummaryByRhAndWorkReportHandler handler =
                 new SalLiabilitiesSummaryByRhAndWorkReportHandler(Objects.requireNonNull(outputStream))) {
            Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
            parameters.put(PRODUCT_FAMILY, FdaConstants.SAL_PRODUCT_FAMILY);
            parameters.put("scenarioIds", Objects.requireNonNull(scenarios).stream()
                .map(Scenario::getId)
                .collect(Collectors.toList()));
            getTemplate().select("ISalReportMapper.findSalLiabilitiesSummaryByRhAndWorkReportDtos", parameters,
                handler);
            handler.writeScenarioNames(scenarios);
        }
    }

    @Override
    public void writeArchivedSalScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        writeCsvReportByParts("ISalReportMapper.findArchivedScenarioUsageDtosCount",
            "ISalReportMapper.findSalArchivedScenarioUsageReportDtos", parameters,
            () -> new SalScenarioUsagesCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeSalScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        writeCsvReportByParts("ISalReportMapper.findScenarioUsageDtosCount",
            "ISalReportMapper.findSalScenarioUsageReportDtos", parameters,
            () -> new SalScenarioUsagesCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeSalUndistributedLiabilitiesCsvReport(OutputStream outputStream) {
        try (SalUndistributedLiabilitiesReportHandler handler =
                 new SalUndistributedLiabilitiesReportHandler(Objects.requireNonNull(outputStream))) {
            Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
            parameters.put(PRODUCT_FAMILY, FdaConstants.SAL_PRODUCT_FAMILY);
            parameters.put(STATUSES, List.of(ScenarioStatusEnum.IN_PROGRESS, ScenarioStatusEnum.SUBMITTED,
                ScenarioStatusEnum.APPROVED));
            getTemplate().select("ISalReportMapper.findSalUndistributedLiabilitiesReportDtos", parameters, handler);
        }
    }

    @Override
    public void writeSalFundPoolsCsvReport(OutputStream outputStream) {
        try (SalFundPoolsExportHandler handler =
                 new SalFundPoolsExportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("ISalReportMapper.findSalFundPools", FdaConstants.SAL_PRODUCT_FAMILY, handler);
        }
    }

    @Override
    public void writeSalFundPoolsCsvReport(int distributionYear, OutputStream outputStream) {
        try (SalFundPoolsReportHandler handler =
                 new SalFundPoolsReportHandler(Objects.requireNonNull(outputStream))) {
            Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
            parameters.put(PRODUCT_FAMILY, FdaConstants.SAL_PRODUCT_FAMILY);
            parameters.put("distributionYear", Objects.requireNonNull(distributionYear));
            getTemplate().select("ISalReportMapper.findSalFundPoolsByDistYear", parameters, handler);
        }
    }

    @Override
    public void writeSalHistoricalItemBankDetailsReport(Long licenseeAccountNumber, Integer periodEndYearFrom,
                                                        Integer periodEndYearTo, OutputStream outputStream) {
        try (SalHistoricalItemBankDetailsReportHandler handler =
                 new SalHistoricalItemBankDetailsReportHandler(Objects.requireNonNull(outputStream))) {
            Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(5);
            parameters.put(PRODUCT_FAMILY, FdaConstants.SAL_PRODUCT_FAMILY);
            parameters.put("detailType", SalDetailTypeEnum.IB);
            parameters.put("licenseeAccountNumber", Objects.requireNonNull(licenseeAccountNumber));
            parameters.put("periodEndYearFrom", Objects.requireNonNull(periodEndYearFrom));
            parameters.put("periodEndYearTo", Objects.requireNonNull(periodEndYearTo));
            getTemplate().select("ISalReportMapper.findSalHistoricalItemBankDetailsReportDtos", parameters, handler);
        }
    }
}
