package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IReportRepository;
import com.copyright.rup.dist.foreign.repository.impl.csv.FasBatchSummaryReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.OwnershipAdjustmentReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.ResearchStatusReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.ServiceFeeTrueUpReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.SummaryMarketReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.UndistributedLiabilitiesReportHandler;

import com.google.common.collect.Maps;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
}
