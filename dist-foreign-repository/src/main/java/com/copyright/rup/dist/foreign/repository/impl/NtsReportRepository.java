package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.INtsReportRepository;
import com.copyright.rup.dist.foreign.repository.impl.csv.fas.NtsServiceFeeTrueUpReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.nts.AuditNtsCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.nts.NtsFundPoolsCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.nts.NtsScenarioUsagesCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.nts.NtsUndistributedLiabilitiesReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.nts.NtsUsageCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.nts.NtsWithdrawnBatchSummaryReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.nts.WorkClassificationCsvReportHandler;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of {@link INtsReportRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/23/2022
 *
 * @author Dzmitry Basiachenka
 */
@Repository
public class NtsReportRepository extends CommonReportRepository implements INtsReportRepository {

    private static final long serialVersionUID = -2243889661438814036L;
    private static final String FILTER_KEY = "filter";
    private static final String SEARCH_VALUE_KEY = "searchValue";
    private static final String SCENARIO_ID_KEY = "scenarioId";
    private static final String PRODUCT_FAMILY = "productFamily";
    private static final String STATUSES = "statuses";

    @Override
    public void writeNtsServiceFeeTrueUpCsvReport(Scenario scenario, OutputStream outputStream,
                                                  BigDecimal defaultEstimatedServiceFee) {
        try (var handler = new NtsServiceFeeTrueUpReportHandler(Objects.requireNonNull(outputStream))) {
            if (ScenarioStatusEnum.SENT_TO_LM == Objects.requireNonNull(scenario).getStatus()) {
                Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
                parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenario.getId()));
                parameters.put("defaultEstimatedServiceFee", Objects.requireNonNull(defaultEstimatedServiceFee));
                getTemplate().select("INtsReportMapper.findNtsServiceFeeTrueUpReportDtos", parameters, handler);
            }
        }
    }

    @Override
    public void writeNtsScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream) {
        Objects.requireNonNull(pipedOutputStream);
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        writeCsvReportByParts("INtsReportMapper.findScenarioUsageDtosCount",
            "INtsReportMapper.findScenarioUsageReportDtos", parameters,
            () -> new NtsScenarioUsagesCsvReportHandler(pipedOutputStream));
    }

    @Override
    public void writeArchivedNtsScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream) {
        Objects.requireNonNull(pipedOutputStream);
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        writeCsvReportByParts("INtsReportMapper.findArchivedScenarioUsageDtosCount",
            "INtsReportMapper.findArchivedScenarioUsageReportDtos", parameters,
            () -> new NtsScenarioUsagesCsvReportHandler(pipedOutputStream));
    }

    @Override
    public void writeNtsUsageCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
        writeCsvReportByParts("INtsReportMapper.findUsagesCountByFilter", "INtsReportMapper.findUsageReportDtos",
            parameters, !filter.isEmpty(),
            () -> new NtsUsageCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeAuditNtsCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(FILTER_KEY, escapeSqlLikePattern(filter));
        writeCsvReportByParts("INtsReportMapper.findUsagesCountForAudit", "INtsReportMapper.findAuditReportDtos",
            parameters, !Objects.requireNonNull(filter).isEmpty(),
            () -> new AuditNtsCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeWorkClassificationCsvReport(Set<String> batchesIds, String searchValue,
                                                 PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("batchesIds", Objects.requireNonNull(batchesIds));
        parameters.put(SEARCH_VALUE_KEY, searchValue);
        writeCsvReportByParts("INtsReportMapper.findWorkClassificationCountByBatchIds",
            "INtsReportMapper.findWorkClassificationByBatchIds", parameters,
            () -> new WorkClassificationCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeWorkClassificationCsvReport(String searchValue, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SEARCH_VALUE_KEY, searchValue);
        parameters.put("productFamilies", FdaConstants.FAS_FAS2_PRODUCT_FAMILY_SET);
        writeCsvReportByParts("INtsReportMapper.findWorkClassificationCountBySearch",
            "INtsReportMapper.findWorkClassificationBySearch", parameters,
            () -> new WorkClassificationCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeNtsWithdrawnBatchSummaryCsvReport(OutputStream outputStream) {
        try (var handler = new NtsWithdrawnBatchSummaryReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("INtsReportMapper.findNtsWithdrawnBatchSummaryReportDtos", handler);
        }
    }

    @Override
    public void writeNtsUndistributedLiabilitiesCsvReport(BigDecimal estimatedServiceFee,
                                                          OutputStream outputStream) {
        try (var handler = new NtsUndistributedLiabilitiesReportHandler(Objects.requireNonNull(outputStream))) {
            Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
            parameters.put("estimatedServiceFee", estimatedServiceFee);
            parameters.put(PRODUCT_FAMILY, FdaConstants.NTS_PRODUCT_FAMILY);
            parameters.put(STATUSES, List.of(ScenarioStatusEnum.SENT_TO_LM, ScenarioStatusEnum.ARCHIVED));
            getTemplate().select("INtsReportMapper.findNtsUndistributedLiabilitiesReportDtos", parameters, handler);
        }
    }

    @Override
    public void writeNtsFundPoolsCsvReport(OutputStream outputStream) {
        try (var handler = new NtsFundPoolsCsvReportHandler(outputStream)) {
            getTemplate().select("INtsReportMapper.findNtsFundPools", handler);
        }
    }
}
