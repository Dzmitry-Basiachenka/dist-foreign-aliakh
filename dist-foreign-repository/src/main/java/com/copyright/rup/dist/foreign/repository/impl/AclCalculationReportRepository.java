package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.filter.AclFundPoolDetailFilter;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.dist.foreign.domain.report.AclCalculationReportsInfoDto;
import com.copyright.rup.dist.foreign.repository.api.IAclCalculationReportRepository;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.AclComparisonByAggLcClassAndTitleCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.AclFundPoolByAggLcCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.AclFundPoolDetailsCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.AclGrantDetailCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.AclLiabilitiesByAggLicClassCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.AclLiabilitiesByRhCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.AclLiabilityDetailsCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.AclScenarioDetailCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.AclScenarioRightsholderTotalsCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.AclSummaryOfWorkSharesByAggLcCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.AclUsageCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.acl.AclWorkSharesByAggLcCsvReportHandler;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
public class AclCalculationReportRepository extends CommonReportRepository implements IAclCalculationReportRepository {

    private static final long serialVersionUID = -8827098935223122903L;
    private static final int REPORT_BATCH_SIZE = 1000;
    private static final String PAGEABLE_KEY = "pageable";
    private static final String FILTER_KEY = "filter";

    @Override
    public void writeAclGrantDetailCsvReport(AclGrantDetailFilter filter, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
        writeCsvReportByParts("IAclCalculationReportMapper.findAclGrantDetailsCountByFilter",
            "IAclCalculationReportMapper.findAclGrantDetailDtosByFilter", parameters, !filter.isEmpty(),
            () -> new AclGrantDetailCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeAclUsageCsvReport(AclUsageFilter filter, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
        writeCsvReportByParts("IAclCalculationReportMapper.findAclUsagesCountByFilter",
            "IAclCalculationReportMapper.findAclUsageDtosByFilter", parameters, !filter.isEmpty(),
            () -> new AclUsageCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeAclFundPoolDetailsCsvReport(AclFundPoolDetailFilter filter, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(1);
        parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
        try (var handler = new AclFundPoolDetailsCsvReportHandler(
            Objects.requireNonNull(pipedOutputStream))) {
            if (!filter.isEmpty()) {
                getTemplate().select("IAclCalculationReportMapper.writeAclFundPoolDetailsCsvReport", parameters,
                    handler);
            }
        }
    }

    @Override
    public void writeAclScenarioDetailsCsvReport(String scenarioId, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("scenarioId", Objects.requireNonNull(scenarioId));
        writeCsvReportByParts("IAclCalculationReportMapper.findAclScenarioDetailDtosCount",
            "IAclCalculationReportMapper.findAclScenarioDetailDtos", parameters,
            () -> new AclScenarioDetailCsvReportHandler(Objects.requireNonNull(pipedOutputStream)));
    }

    @Override
    public void writeAclScenarioRightsholderTotalsCsvReport(String scenarioId, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("scenarioId", Objects.requireNonNull(scenarioId));
        parameters.put("sort", new Sort("rightsholder.accountNumber", Direction.ASC));
        try (var handler =
                 new AclScenarioRightsholderTotalsCsvReportHandler(Objects.requireNonNull(pipedOutputStream))) {
            getTemplate().select("IAclCalculationReportMapper.findAclRightsholderTotalsHoldersReportDtos",
                parameters, handler);
        }
    }

    @Override
    public void writeSummaryOfWorkSharesByAggLcCsvReport(AclCalculationReportsInfoDto reportInfo,
                                                         OutputStream outputStream) {
        String scenarioId = Objects.requireNonNull(reportInfo.getScenarios().get(0).getId());
        try (var handler = new AclSummaryOfWorkSharesByAggLcCsvReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IAclCalculationReportMapper.findSummaryOfWorkSharesByAggLcReportDtos",
                scenarioId, handler);
            handler.writeMetadata(reportInfo);
        }
    }

    @Override
    public void writeWorkSharesByAggLcCsvReport(AclCalculationReportsInfoDto reportInfo, OutputStream outputStream) {
        String scenarioId = Objects.requireNonNull(reportInfo.getScenarios().get(0).getId());
        try (var handler = new AclWorkSharesByAggLcCsvReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IAclCalculationReportMapper.findWorkSharesByAggLcReportDtos", scenarioId, handler);
            handler.writeMetadata(reportInfo);
        }
    }

    @Override
    public void writeAclLiabilityDetailsReport(AclCalculationReportsInfoDto reportInfo, OutputStream outputStream) {
        try (var handler = new AclLiabilityDetailsCsvReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IAclCalculationReportMapper.findAclScenarioDetailsReportDtos", reportInfo, handler);
            handler.writeTotals(
                selectOne("IAclCalculationReportMapper.findTotalAmountsOfAclScenarioDetailsReport", reportInfo));
            handler.writeMetadata(reportInfo);
        }
    }

    @Override
    public void writeAclLiabilitiesByAggLicClassReport(AclCalculationReportsInfoDto reportInfo,
                                                       OutputStream outputStream) {
        try (var handler = new AclLiabilitiesByAggLicClassCsvReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IAclCalculationReportMapper.findAclLiabilitiesByAggLicClassReportDtos",
                reportInfo, handler);
            handler.writeTotals(
                selectOne("IAclCalculationReportMapper.findAclLiabilitiesByAggLicClassReportTotalAmounts", reportInfo));
            handler.writeMetadata(reportInfo);
        }
    }

    @Override
    public void writeAclLiabilitiesByRhReport(AclCalculationReportsInfoDto reportInfo, OutputStream outputStream) {
        try (var handler = new AclLiabilitiesByRhCsvReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IAclCalculationReportMapper.findAclLiabilitiesByRhReportDtos", reportInfo, handler);
            handler.writeTotals(
                selectOne("IAclCalculationReportMapper.findAclLiabilitiesByRhReportTotalAmounts", reportInfo));
            handler.writeMetadata(reportInfo);
        }
    }

    @Override
    public void writeAclComparisonByAggLcClassAndTitleReport(AclCalculationReportsInfoDto reportInfo,
                                                             OutputStream outputStream) {
        try (var handler =
                 new AclComparisonByAggLcClassAndTitleCsvReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IAclCalculationReportMapper.findAclComparisonByAggLcClassAndTitleReportDtos",
                reportInfo, handler);
            handler.writeMetadata(reportInfo);
        }
    }

    @Override
    public void writeAclFundPoolByAggLcReport(Set<String> fundPoolIds, OutputStream outputStream) {
        try (var handler = new AclFundPoolByAggLcCsvReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IAclCalculationReportMapper.writeAclFundPoolByAggLcReport", fundPoolIds, handler);
        }
    }

    @Override
    protected void writeCsvReportByParts(String countMethodName, String selectMethodName,
                                         Map<String, Object> parameters,
                                         boolean precondition,
                                         Supplier<? extends BaseCsvReportHandler> handlerSupplier) {
        int size = selectOne(countMethodName, parameters);
        try (var handler = handlerSupplier.get()) {
            if (precondition && 0 < size) {
                for (int offset = 0; offset < size; offset += REPORT_BATCH_SIZE) {
                    parameters.put(PAGEABLE_KEY, new Pageable(offset, REPORT_BATCH_SIZE));
                    getTemplate().select(selectMethodName, parameters, handler);
                }
            }
        }
    }
}
