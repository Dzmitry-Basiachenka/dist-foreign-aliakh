package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IReportRepository;
import com.copyright.rup.dist.foreign.repository.impl.csv.ScenarioRightsholderTotalsCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.aacl.AaclBaselineUsagesCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.aacl.AaclScenarioUsagesCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.aacl.AaclUndistributedLiabilitiesReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.aacl.AaclUsageCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.aacl.AuditAaclCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.aacl.SendForClassificationCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.aacl.WorkSharesByAggLcClassReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.aacl.WorkSharesByAggLcClassSummaryReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.fas.AuditFasCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.fas.ExcludeDetailsByPayeeCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.fas.FasBatchSummaryReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.fas.FasScenarioUsagesCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.fas.FasServiceFeeTrueUpReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.fas.FasUsageCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.fas.NtsServiceFeeTrueUpReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.fas.OwnershipAdjustmentReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.fas.ResearchStatusReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.fas.SendForResearchCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.fas.SummaryMarketReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.fas.UndistributedLiabilitiesReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.nts.AuditNtsCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.nts.NtsScenarioUsagesCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.nts.NtsUndistributedLiabilitiesReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.nts.NtsUsageCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.nts.NtsWithdrawnBatchSummaryReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.nts.WorkClassificationCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.sal.SalScenarioUsagesCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.sal.SalUsageCsvReportHandler;

import com.google.common.collect.Maps;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Implementation of {@link IReportRepository}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 06/25/2019
 *
 * @author Ihar Suvorau
 */
@Repository
public class ReportRepository extends BaseRepository implements IReportRepository {

    private static final int REPORT_BATCH_SIZE = 100000;
    private static final String FILTER_KEY = "filter";
    private static final String PAGEABLE_KEY = "pageable";
    private static final String SEARCH_VALUE_KEY = "searchValue";
    private static final String SCENARIO_ID_KEY = "scenarioId";
    private static final String FIND_USAGE_REPORT_DTOS_METHOD_NAME = "IReportMapper.findUsageReportDtos";
    private static final String FIND_USAGES_COUNT_BY_FILTER_METHOD_NAME = "IReportMapper.findUsagesCountByFilter";
    private static final String FIND_SCENARIO_USAGE_DTOS_COUNT_METHOD_NAME = "IReportMapper.findScenarioUsageDtosCount";
    private static final String FIND_ARCHIVED_SCENARIO_USAGE_DTOS_COUNT_METHOD_NAME =
        "IReportMapper.findArchivedScenarioUsageDtosCount";

    @Override
    public void writeUndistributedLiabilitiesCsvReport(LocalDate paymentDate, OutputStream outputStream,
                                                       BigDecimal defaultEstimatedServiceFee,
                                                       Set<String> productFamilies) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("paymentDate", Objects.requireNonNull(paymentDate));
        parameters.put("withdrawnStatuses",
            Arrays.asList(UsageStatusEnum.NTS_WITHDRAWN, UsageStatusEnum.TO_BE_DISTRIBUTED));
        parameters.put("defaultEstimatedServiceFee", Objects.requireNonNull(defaultEstimatedServiceFee));
        parameters.put("productFamilies", productFamilies);
        try (UndistributedLiabilitiesReportHandler handler =
                 new UndistributedLiabilitiesReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IReportMapper.findUndistributedLiabilitiesReportDtos", parameters, handler);
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
            getTemplate().select("IReportMapper.findFasServiceFeeTrueUpReportDtos", parameters, handler);
        }
    }

    @Override
    public void writeNtsServiceFeeTrueUpCsvReport(Scenario scenario, OutputStream outputStream,
                                                  BigDecimal defaultEstimatedServiceFee) {
        try (NtsServiceFeeTrueUpReportHandler handler =
                 new NtsServiceFeeTrueUpReportHandler(Objects.requireNonNull(outputStream))) {
            if (ScenarioStatusEnum.SENT_TO_LM == Objects.requireNonNull(scenario).getStatus()) {
                Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
                parameters.put("scenarioId", Objects.requireNonNull(scenario.getId()));
                parameters.put("defaultEstimatedServiceFee", Objects.requireNonNull(defaultEstimatedServiceFee));
                getTemplate().select("IReportMapper.findNtsServiceFeeTrueUpReportDtos", parameters, handler);
            }
        }
    }

    @Override
    public void writeSummaryMarketCsvReport(List<String> batchIds, OutputStream outputStream) {
        try (SummaryMarketReportHandler handler = new SummaryMarketReportHandler(
            Objects.requireNonNull(outputStream))) {
            getTemplate().select("IReportMapper.findSummaryMarketReportDtos", Objects.requireNonNull(batchIds),
                handler);
        }
    }

    @Override
    public void writeFasBatchSummaryCsvReport(OutputStream outputStream) {
        try (FasBatchSummaryReportHandler handler = new FasBatchSummaryReportHandler(
            Objects.requireNonNull(outputStream))) {
            getTemplate().select("IReportMapper.findFasBatchSummaryReportDtos", handler);
        }
    }

    @Override
    public void writeResearchStatusCsvReport(OutputStream outputStream) {
        try (ResearchStatusReportHandler handler = new ResearchStatusReportHandler(
            Objects.requireNonNull(outputStream))) {
            getTemplate().select("IReportMapper.findResearchStatusReportDtos", handler);
        }
    }

    @Override
    public void writeOwnershipAdjustmentCsvReport(String scenarioId, Set<RightsholderDiscrepancyStatusEnum> statuses,
                                                  OutputStream outputStream) {
        checkArgument(CollectionUtils.isNotEmpty(statuses));
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put("statuses", statuses);
        try (OwnershipAdjustmentReportHandler handler = new OwnershipAdjustmentReportHandler(
            Objects.requireNonNull(outputStream))) {
            getTemplate().select("IReportMapper.findOwnershipAdjustmentReportDtos", parameters, handler);
        }
    }

    @Override
    public void writeFasScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream) {
        Objects.requireNonNull(pipedOutputStream);
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        writeCsvReportByParts(FIND_SCENARIO_USAGE_DTOS_COUNT_METHOD_NAME, "IReportMapper.findScenarioUsageReportDtos",
            parameters, () -> new FasScenarioUsagesCsvReportHandler(pipedOutputStream));
    }

    @Override
    public void writeArchivedFasScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream) {
        Objects.requireNonNull(pipedOutputStream);
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        writeCsvReportByParts(FIND_ARCHIVED_SCENARIO_USAGE_DTOS_COUNT_METHOD_NAME,
            "IReportMapper.findArchivedScenarioUsageReportDtos", parameters,
            () -> new FasScenarioUsagesCsvReportHandler(pipedOutputStream));
    }

    @Override
    public void writeNtsScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream) {
        Objects.requireNonNull(pipedOutputStream);
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        writeCsvReportByParts(FIND_SCENARIO_USAGE_DTOS_COUNT_METHOD_NAME, "IReportMapper.findScenarioUsageReportDtos",
            parameters, () -> new NtsScenarioUsagesCsvReportHandler(pipedOutputStream));
    }

    @Override
    public void writeArchivedNtsScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream) {
        Objects.requireNonNull(pipedOutputStream);
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        writeCsvReportByParts(FIND_ARCHIVED_SCENARIO_USAGE_DTOS_COUNT_METHOD_NAME,
            "IReportMapper.findArchivedScenarioUsageReportDtos", parameters,
            () -> new NtsScenarioUsagesCsvReportHandler(pipedOutputStream));
    }

    @Override
    public void writeScenarioRightsholderTotalsCsvReport(String scenarioId, PipedOutputStream pipedOutputStream) {
        Objects.requireNonNull(pipedOutputStream);
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put("sort", new Sort("rightsholder.accountNumber", Direction.ASC));
        try (ScenarioRightsholderTotalsCsvReportHandler handler
                 = new ScenarioRightsholderTotalsCsvReportHandler(pipedOutputStream)) {
            getTemplate().select("IReportMapper.findRightsholderTotalsHoldersReportDtos", parameters, handler);
        }
    }

    @Override
    public void writeArchivedScenarioRightsholderTotalsCsvReport(String scenarioId,
                                                                 PipedOutputStream pipedOutputStream) {
        Objects.requireNonNull(pipedOutputStream);
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put("sort", new Sort("rightsholder.accountNumber", Direction.ASC));
        try (ScenarioRightsholderTotalsCsvReportHandler handler
                 = new ScenarioRightsholderTotalsCsvReportHandler(pipedOutputStream)) {
            getTemplate().select("IReportMapper.findArchivedRightsholderTotalsHoldersReportDtos", parameters, handler);
        }
    }

    @Override
    public void writeWorkSharesByAggLcClassSummaryCsvReport(String scenarioId, ScenarioStatusEnum status,
                                                            OutputStream outputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put("status", Objects.requireNonNull(status));
        parameters.put("archivedStatuses", Arrays.asList(ScenarioStatusEnum.SENT_TO_LM, ScenarioStatusEnum.ARCHIVED));
        try (WorkSharesByAggLcClassSummaryReportHandler handler =
                 new WorkSharesByAggLcClassSummaryReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IReportMapper.findWorkSharesByAggLcClassSummaryReportDtos", parameters, handler);
        }
    }

    @Override
    public void writeWorkSharesByAggLcClassCsvReport(String scenarioId, ScenarioStatusEnum status,
                                                     OutputStream outputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put("status", Objects.requireNonNull(status));
        parameters.put("archivedStatuses", Arrays.asList(ScenarioStatusEnum.SENT_TO_LM, ScenarioStatusEnum.ARCHIVED));
        try (WorkSharesByAggLcClassReportHandler handler =
                 new WorkSharesByAggLcClassReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IReportMapper.findWorkSharesByAggLcClassReportDtos", parameters, handler);
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
    public void writeNtsUsageCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
        writeCsvReportByParts(FIND_USAGES_COUNT_BY_FILTER_METHOD_NAME, FIND_USAGE_REPORT_DTOS_METHOD_NAME, parameters,
            !filter.isEmpty(), () -> new NtsUsageCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeAaclUsagesCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
        writeCsvReportByParts("IReportMapper.findAaclUsagesCountByFilter", "IReportMapper.findAaclUsageReportDtos",
            parameters, !filter.isEmpty(),
            () -> new AaclUsageCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeSalUsagesCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
        writeCsvReportByParts("IReportMapper.findSalUsagesCountByFilter", "IReportMapper.findSalUsageReportDtos",
            parameters, !filter.isEmpty(),
            () -> new SalUsageCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
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
    public Set<String> writeUsagesForClassificationAndFindIds(UsageFilter filter, OutputStream outputStream) {
        Set<String> usageIds = new HashSet<>();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
        writeCsvReportByParts("IReportMapper.findAaclUsagesCountByFilter", "IReportMapper.findAaclUsageReportDtos",
            parameters, !Objects.requireNonNull(filter).isEmpty(),
            () -> new SendForClassificationCsvReportHandler(Objects.requireNonNull(outputStream), usageIds));
        return usageIds;
    }

    @Override
    public void writeAuditFasCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(FILTER_KEY, escapeSqlLikePattern(filter));
        writeCsvReportByParts("IReportMapper.findUsagesCountForAudit", "IReportMapper.findAuditReportDtos",
            parameters, !Objects.requireNonNull(filter).isEmpty(),
            () -> new AuditFasCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeAuditNtsCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(FILTER_KEY, escapeSqlLikePattern(filter));
        writeCsvReportByParts("IReportMapper.findUsagesCountForAudit", "IReportMapper.findAuditReportDtos",
            parameters, !Objects.requireNonNull(filter).isEmpty(),
            () -> new AuditNtsCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeAuditAaclCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(1);
        parameters.put(FILTER_KEY, escapeSqlLikePattern(filter));
        writeCsvReportByParts("IReportMapper.findAaclUsagesCountForAudit", "IReportMapper.findAuditAaclReportDtos",
            parameters, !Objects.requireNonNull(filter).isEmpty(),
            () -> new AuditAaclCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeWorkClassificationCsvReport(Set<String> batchesIds, String searchValue,
                                                 PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("batchesIds", Objects.requireNonNull(batchesIds));
        parameters.put(SEARCH_VALUE_KEY, searchValue);
        writeCsvReportByParts("IReportMapper.findWorkClassificationCountByBatchIds",
            "IReportMapper.findWorkClassificationByBatchIds", parameters,
            () -> new WorkClassificationCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeWorkClassificationCsvReport(String searchValue, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SEARCH_VALUE_KEY, searchValue);
        parameters.put("productFamilies", FdaConstants.FAS_FAS2_PRODUCT_FAMILY_SET);
        writeCsvReportByParts("IReportMapper.findWorkClassificationCountBySearch",
            "IReportMapper.findWorkClassificationBySearch", parameters,
            () -> new WorkClassificationCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeNtsWithdrawnBatchSummaryCsvReport(OutputStream outputStream) {
        try (NtsWithdrawnBatchSummaryReportHandler handler = new NtsWithdrawnBatchSummaryReportHandler(
            Objects.requireNonNull(outputStream))) {
            getTemplate().select("IReportMapper.findNtsWithdrawnBatchSummaryReportDtos", handler);
        }
    }

    @Override
    public void writeArchivedAaclScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        writeCsvReportByParts(FIND_ARCHIVED_SCENARIO_USAGE_DTOS_COUNT_METHOD_NAME,
            "IReportMapper.findAaclArchivedScenarioUsageReportDtos", parameters,
            () -> new AaclScenarioUsagesCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeAaclScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        writeCsvReportByParts(FIND_SCENARIO_USAGE_DTOS_COUNT_METHOD_NAME,
            "IReportMapper.findAaclScenarioUsageReportDtos",
            parameters, () -> new AaclScenarioUsagesCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeAaclBaselineUsagesCsvReport(int numberOfYears, OutputStream outputStream) {
        try (AaclBaselineUsagesCsvReportHandler handler =
                 new AaclBaselineUsagesCsvReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IReportMapper.findAaclBaselineUsages", numberOfYears, handler);
        }
    }

    @Override
    public void writeExcludeDetailsByPayeeCsvReport(ExcludePayeeFilter filter, Set<Long> selectedAccountNumbers,
                                                    PipedOutputStream pipedOutputStream) {
        try (ExcludeDetailsByPayeeCsvReportHandler handler = new ExcludeDetailsByPayeeCsvReportHandler(
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

    @Override
    public void writeAaclUndistributedLiabilitiesCsvReport(OutputStream outputStream) {
        try (AaclUndistributedLiabilitiesReportHandler handler =
                 new AaclUndistributedLiabilitiesReportHandler(Objects.requireNonNull(outputStream))) {
            Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
            parameters.put("serviceFee", 0.25);
            parameters.put("productFamily", FdaConstants.AACL_PRODUCT_FAMILY);
            parameters.put("statuses", Arrays.asList(ScenarioStatusEnum.IN_PROGRESS,
                ScenarioStatusEnum.SUBMITTED, ScenarioStatusEnum.APPROVED));
            getTemplate().select("IReportMapper.findAaclUndistributedLiabilitiesReportFundPools", parameters, handler);
        }
    }

    @Override
    public void writeNtsUndistributedLiabilitiesCsvReport(BigDecimal estimatedServiceFee,
                                                          OutputStream outputStream) {
        try (NtsUndistributedLiabilitiesReportHandler handler =
                 new NtsUndistributedLiabilitiesReportHandler(Objects.requireNonNull(outputStream))) {
            Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
            parameters.put("estimatedServiceFee", estimatedServiceFee);
            parameters.put("productFamily", FdaConstants.NTS_PRODUCT_FAMILY);
            parameters.put("statuses", Arrays.asList(ScenarioStatusEnum.SENT_TO_LM, ScenarioStatusEnum.ARCHIVED));
            getTemplate().select("IReportMapper.findNtsUndistributedLiabilitiesReportDtos", parameters, handler);
        }
    }

    @Override
    public void writeArchivedSalScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        writeCsvReportByParts(FIND_ARCHIVED_SCENARIO_USAGE_DTOS_COUNT_METHOD_NAME,
            "IReportMapper.findSalArchivedScenarioUsageReportDtos", parameters,
            () -> new SalScenarioUsagesCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeSalScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        writeCsvReportByParts(FIND_SCENARIO_USAGE_DTOS_COUNT_METHOD_NAME,
            "IReportMapper.findSalScenarioUsageReportDtos", parameters,
            () -> new SalScenarioUsagesCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    private void writeCsvReportByParts(String countMethodName, String selectMethodName, Map<String, Object> parameters,
                                       Supplier<? extends BaseCsvReportHandler> handlerSupplier) {
        writeCsvReportByParts(countMethodName, selectMethodName, parameters, true, handlerSupplier);
    }

    private void writeCsvReportByParts(String countMethodName, String selectMethodName, Map<String, Object> parameters,
                                       boolean precondition, Supplier<? extends BaseCsvReportHandler> handlerSupplier) {
        int size = selectOne(countMethodName, parameters);
        try (BaseCsvReportHandler handler = handlerSupplier.get()) {
            if (precondition && 0 < size) {
                for (int offset = 0; offset < size; offset += REPORT_BATCH_SIZE) {
                    parameters.put(PAGEABLE_KEY, new Pageable(offset, REPORT_BATCH_SIZE));
                    getTemplate().select(selectMethodName, parameters, handler);
                }
            }
        }
    }

    private AuditFilter escapeSqlLikePattern(AuditFilter auditFilter) {
        AuditFilter filterCopy = new AuditFilter(auditFilter);
        filterCopy.setCccEventId(escapeSqlLikePattern(filterCopy.getCccEventId()));
        filterCopy.setDistributionName(escapeSqlLikePattern(filterCopy.getDistributionName()));
        filterCopy.setSearchValue(escapeSqlLikePattern(filterCopy.getSearchValue()));
        return filterCopy;
    }
}
