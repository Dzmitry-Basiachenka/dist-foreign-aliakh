package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IReportRepository;
import com.copyright.rup.dist.foreign.repository.impl.csv.AuditCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.FasBatchSummaryReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.NtsWithdrawnBatchSummaryReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.OwnershipAdjustmentReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.ResearchStatusReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.ScenarioRightsholderTotalsCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.ScenarioUsagesCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.SendForResearchCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.ServiceFeeTrueUpReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.SummaryMarketReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.UndistributedLiabilitiesReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.UsageCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.WorkClassificationCsvReportHandler;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
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

    @Override
    public void writeUndistributedLiabilitiesCsvReport(LocalDate paymentDate, OutputStream outputStream,
                                                       BigDecimal defaultEstimatedServiceFee) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("paymentDate", Objects.requireNonNull(paymentDate));
        parameters.put("withdrawnStatuses",
            Arrays.asList(UsageStatusEnum.NTS_WITHDRAWN, UsageStatusEnum.TO_BE_DISTRIBUTED));
        parameters.put("defaultEstimatedServiceFee", Objects.requireNonNull(defaultEstimatedServiceFee));
        try (UndistributedLiabilitiesReportHandler handler =
                 new UndistributedLiabilitiesReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IReportMapper.findUndistributedLiabilitiesReportDtos", parameters, handler);
        }
    }

    @Override
    public void writeServiceFeeTrueUpCsvReport(LocalDate fromDate, LocalDate toDate, LocalDate paymentDateTo,
                                               OutputStream outputStream, Long claAccountNumber,
                                               BigDecimal defaultEstimatedServiceFee) {
        try (ServiceFeeTrueUpReportHandler handler =
                 new ServiceFeeTrueUpReportHandler(Objects.requireNonNull(outputStream))) {
            Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(8);
            parameters.put("paymentDateTo", Objects.requireNonNull(paymentDateTo));
            parameters.put("fromDate", Objects.requireNonNull(fromDate));
            parameters.put("toDate", Objects.requireNonNull(toDate));
            parameters.put("productFamilyClaFas", FdaConstants.CLA_FAS_PRODUCT_FAMILY);
            parameters.put("accountNumberClaFas", claAccountNumber);
            parameters.put("action", ScenarioActionTypeEnum.SENT_TO_LM);
            parameters.put("status", UsageStatusEnum.SENT_TO_LM);
            parameters.put("defaultEstimatedServiceFee", Objects.requireNonNull(defaultEstimatedServiceFee));
            getTemplate().select("IReportMapper.findServiceFeeTrueUpReportDtos", parameters, handler);
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
        parameters.put("scenarioId", Objects.requireNonNull(scenarioId));
        parameters.put("statuses", statuses);
        try (OwnershipAdjustmentReportHandler handler = new OwnershipAdjustmentReportHandler(
            Objects.requireNonNull(outputStream))) {
            getTemplate().select("IReportMapper.findOwnershipAdjustmentReportDtos", parameters, handler);
        }
    }

    @Override
    public void writeScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream) {
        Objects.requireNonNull(pipedOutputStream);
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("scenarioId", Objects.requireNonNull(scenarioId));
        writeCsvReportByParts("IReportMapper.findScenarioUsageDtosCount", "IReportMapper.findScenarioUsageReportDtos",
            parameters, () -> new ScenarioUsagesCsvReportHandler(pipedOutputStream));
    }

    @Override
    public void writeScenarioRightsholderTotalsCsvReport(String scenarioId, PipedOutputStream pipedOutputStream) {
        Objects.requireNonNull(pipedOutputStream);
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("scenarioId", Objects.requireNonNull(scenarioId));
        parameters.put("sort", new Sort("rightsholder.accountNumber", Direction.ASC));
        try (ScenarioRightsholderTotalsCsvReportHandler handler
                 = new ScenarioRightsholderTotalsCsvReportHandler(pipedOutputStream)) {
            getTemplate().select("IReportMapper.findRightsholderTotalsHoldersReportDtos", parameters, handler);
        }
    }

    @Override
    public void writeUsagesCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
        writeCsvReportByParts("IReportMapper.findUsagesCountByFilter", "IReportMapper.findUsageReportDtos", parameters,
            !filter.isEmpty(), () -> new UsageCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public Set<String> writeUsagesForResearchAndFindIds(UsageFilter filter, OutputStream outputStream) {
        Set<String> usageIds = new HashSet<>();
        try (SendForResearchCsvReportHandler handler =
                 new SendForResearchCsvReportHandler(Objects.requireNonNull(outputStream))) {
            if (!Objects.requireNonNull(filter).isEmpty()) {
                getTemplate().select("IReportMapper.findUsageReportDtos", ImmutableMap.of(FILTER_KEY, filter),
                    handler);
                usageIds = handler.getUsagesIds();
            }
        }
        return usageIds;
    }

    @Override
    public void writeAuditCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(FILTER_KEY, escapeSqlLikePattern(filter));
        writeCsvReportByParts("IReportMapper.findUsagesCountForAudit", "IReportMapper.findAuditReportDtos", parameters,
            !Objects.requireNonNull(filter).isEmpty(),
            () -> new AuditCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
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

    private <T> void writeCsvReportByParts(String countMethodName, String selectMethodName,
                                           Map<String, Object> parameters,
                                           Supplier<? extends BaseCsvReportHandler> handlerSupplier) {
        writeCsvReportByParts(countMethodName, selectMethodName, parameters, true, handlerSupplier);
    }

    private <T> void writeCsvReportByParts(String countMethodName, String selectMethodName,
                                           Map<String, Object> parameters, boolean precondition,
                                           Supplier<? extends BaseCsvReportHandler> handlerSupplier) {
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
