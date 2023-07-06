package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IFasReportRepository;
import com.copyright.rup.dist.foreign.repository.impl.csv.ScenarioRightsholderTotalsCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.fas.AuditFasCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.fas.FasBatchSummaryReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.fas.FasExcludeDetailsByPayeeCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.fas.FasScenarioUsagesCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.fas.FasServiceFeeTrueUpReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.fas.FasUsageCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.fas.OwnershipAdjustmentReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.fas.ResearchStatusReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.fas.SendForResearchCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.fas.SummaryMarketReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.fas.UndistributedLiabilitiesReportHandler;

import com.google.common.collect.Maps;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import io.micrometer.core.annotation.Timed;

/**
 * Implementation of {@link IFasReportRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/23/2022
 *
 * @author Dzmitry Basiachenka
 */
@Repository
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class FasReportRepository extends CommonReportRepository implements IFasReportRepository {

    private static final String FILTER_KEY = "filter";
    private static final String SCENARIO_ID_KEY = "scenarioId";
    private static final String STATUSES = "statuses";
    private static final String FIND_USAGE_REPORT_DTOS_METHOD_NAME = "IFasReportMapper.findUsageReportDtos";
    private static final String FIND_USAGES_COUNT_BY_FILTER_METHOD_NAME = "IFasReportMapper.findUsagesCountByFilter";

    @Override
    public void writeUndistributedLiabilitiesCsvReport(LocalDate paymentDate, OutputStream outputStream,
                                                       BigDecimal defaultEstimatedServiceFee,
                                                       Set<String> productFamilies) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("paymentDate", Objects.requireNonNull(paymentDate));
        parameters.put("withdrawnStatuses", List.of(UsageStatusEnum.NTS_WITHDRAWN, UsageStatusEnum.TO_BE_DISTRIBUTED));
        parameters.put("defaultEstimatedServiceFee", Objects.requireNonNull(defaultEstimatedServiceFee));
        parameters.put("productFamilies", productFamilies);
        try (UndistributedLiabilitiesReportHandler handler =
                 new UndistributedLiabilitiesReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IFasReportMapper.findUndistributedLiabilitiesReportDtos", parameters, handler);
        }
    }

    @Override
    public void writeFasServiceFeeTrueUpCsvReport(LocalDate fromDate, LocalDate toDate, LocalDate paymentDateTo,
                                                  OutputStream outputStream, Long claAccountNumber,
                                                  BigDecimal defaultEstimatedServiceFee) {
        try (FasServiceFeeTrueUpReportHandler handler =
                 new FasServiceFeeTrueUpReportHandler(Objects.requireNonNull(outputStream))) {
            Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(8);
            parameters.put("paymentDateTo", Objects.requireNonNull(paymentDateTo));
            parameters.put("fromDate", Objects.requireNonNull(fromDate));
            parameters.put("toDate", Objects.requireNonNull(toDate));
            parameters.put("productFamilies", FdaConstants.FAS_FAS2_PRODUCT_FAMILY_SET);
            parameters.put("productFamilyClaFas", FdaConstants.CLA_FAS_PRODUCT_FAMILY);
            parameters.put("accountNumberClaFas", claAccountNumber);
            parameters.put("action", ScenarioActionTypeEnum.SENT_TO_LM);
            parameters.put("status", UsageStatusEnum.SENT_TO_LM);
            parameters.put("defaultEstimatedServiceFee", Objects.requireNonNull(defaultEstimatedServiceFee));
            getTemplate().select("IFasReportMapper.findFasServiceFeeTrueUpReportDtos", parameters, handler);
        }
    }

    @Override
    public void writeSummaryMarketCsvReport(List<String> batchIds, OutputStream outputStream) {
        try (SummaryMarketReportHandler handler =
                 new SummaryMarketReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IFasReportMapper.findSummaryMarketReportDtos", Objects.requireNonNull(batchIds),
                handler);
        }
    }

    @Override
    public void writeFasBatchSummaryCsvReport(OutputStream outputStream) {
        try (FasBatchSummaryReportHandler handler =
                 new FasBatchSummaryReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IFasReportMapper.findFasBatchSummaryReportDtos", handler);
        }
    }

    @Override
    public void writeResearchStatusCsvReport(OutputStream outputStream) {
        try (ResearchStatusReportHandler handler =
                 new ResearchStatusReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IFasReportMapper.findResearchStatusReportDtos", handler);
        }
    }

    @Override
    public void writeOwnershipAdjustmentCsvReport(String scenarioId, Set<RightsholderDiscrepancyStatusEnum> statuses,
                                                  OutputStream outputStream) {
        checkArgument(CollectionUtils.isNotEmpty(statuses));
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(STATUSES, statuses);
        try (OwnershipAdjustmentReportHandler handler =
                 new OwnershipAdjustmentReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IFasReportMapper.findOwnershipAdjustmentReportDtos", parameters, handler);
        }
    }

    @Override
    public void writeFasScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream) {
        Objects.requireNonNull(pipedOutputStream);
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        writeCsvReportByParts("IFasReportMapper.findScenarioUsageDtosCount",
            "IFasReportMapper.findScenarioUsageReportDtos", parameters,
            () -> new FasScenarioUsagesCsvReportHandler(pipedOutputStream));
    }

    @Override
    public void writeArchivedFasScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream) {
        Objects.requireNonNull(pipedOutputStream);
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        writeCsvReportByParts("IFasReportMapper.findArchivedScenarioUsageDtosCount",
            "IFasReportMapper.findArchivedScenarioUsageReportDtos", parameters,
            () -> new FasScenarioUsagesCsvReportHandler(pipedOutputStream));
    }

    @Override
    public void writeScenarioRightsholderTotalsCsvReport(String scenarioId, PipedOutputStream pipedOutputStream) {
        Objects.requireNonNull(pipedOutputStream);
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put("sort", new Sort("rightsholder.accountNumber", Direction.ASC));
        try (ScenarioRightsholderTotalsCsvReportHandler handler =
                 new ScenarioRightsholderTotalsCsvReportHandler(pipedOutputStream)) {
            getTemplate().select("IFasReportMapper.findRightsholderTotalsHoldersReportDtos", parameters, handler);
        }
    }

    @Override
    public void writeArchivedScenarioRightsholderTotalsCsvReport(String scenarioId,
                                                                 PipedOutputStream pipedOutputStream) {
        Objects.requireNonNull(pipedOutputStream);
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put("sort", new Sort("rightsholder.accountNumber", Direction.ASC));
        try (ScenarioRightsholderTotalsCsvReportHandler handler =
                 new ScenarioRightsholderTotalsCsvReportHandler(pipedOutputStream)) {
            getTemplate().select("IFasReportMapper.findArchivedRightsholderTotalsHoldersReportDtos", parameters,
                handler);
        }
    }

    @Override
    public void writeFasUsageCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
        writeCsvReportByParts(FIND_USAGES_COUNT_BY_FILTER_METHOD_NAME, FIND_USAGE_REPORT_DTOS_METHOD_NAME, parameters,
            !filter.isEmpty(), () -> new FasUsageCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public Set<String> writeUsagesForResearchAndFindIds(UsageFilter filter, OutputStream outputStream) {
        Set<String> usageIds = new HashSet<>();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
        writeCsvReportByParts(FIND_USAGES_COUNT_BY_FILTER_METHOD_NAME, FIND_USAGE_REPORT_DTOS_METHOD_NAME, parameters,
            !Objects.requireNonNull(filter).isEmpty(),
            () -> new SendForResearchCsvReportHandler(Objects.requireNonNull(outputStream), usageIds));
        return usageIds;
    }

    @Override
    public void writeAuditFasCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(FILTER_KEY, escapeSqlLikePattern(filter));
        writeCsvReportByParts("IFasReportMapper.findUsagesCountForAudit", "IFasReportMapper.findAuditReportDtos",
            parameters, !Objects.requireNonNull(filter).isEmpty(),
            () -> new AuditFasCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeFasExcludeDetailsByPayeeCsvReport(ExcludePayeeFilter filter, Set<Long> selectedAccountNumbers,
                                                       PipedOutputStream pipedOutputStream) {
        try (FasExcludeDetailsByPayeeCsvReportHandler handler = new FasExcludeDetailsByPayeeCsvReportHandler(
            Objects.requireNonNull(pipedOutputStream),
            Objects.requireNonNull(selectedAccountNumbers))) {
            if (!Objects.requireNonNull(filter).isEmpty()) {
                Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
                parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
                parameters.put("scenarioStatus", ScenarioStatusEnum.IN_PROGRESS);
                getTemplate().select("IUsageMapper.findPayeeTotalHoldersByFilter", parameters, handler);
            }
        }
    }
}
