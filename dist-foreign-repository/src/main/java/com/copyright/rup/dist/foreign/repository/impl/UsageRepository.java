package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.repository.impl.csv.AuditCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.BatchSummaryReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.ResearchStatusReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.ScenarioUsagesCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.SendForResearchCsvReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.ServiceFeeTrueUpReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.SummaryMarketReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.UndistributedLiabilitiesReportHandler;
import com.copyright.rup.dist.foreign.repository.impl.csv.UsageCsvReportHandler;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of Usage repository.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/02/17
 *
 * @author Darya Baraukova
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
 */
@Repository
public class UsageRepository extends BaseRepository implements IUsageRepository {

    /**
     * Details ids batch size for finding duplicates. This size was obtained as (32000 / 2 = 16000)
     * where {@code 32000} it's a max value for count of variables in statement and {@code 2} means that statement uses
     * 'in' clause with the same parameters two times.
     */
    private static final String FILTER_KEY = "filter";
    private static final String PAGEABLE_KEY = "pageable";
    private static final String SORT_KEY = "sort";
    private static final String SEARCH_VALUE_KEY = "searchValue";
    private static final String SCENARIO_ID_KEY = "scenarioId";
    private static final String UPDATE_USER_KEY = "updateUser";
    private static final String USAGE_ID_KEY = "usageId";
    private static final String STATUS_KEY = "status";
    private static final String RH_ACCOUNT_NUMBER_KEY = "rhAccountNumber";
    private static final int REPORT_BATCH_SIZE = 100000;

    @Override
    public void insert(Usage usage) {
        insert("IUsageMapper.insert", Objects.requireNonNull(usage));
    }

    @Override
    public List<UsageDto> findDtosByFilter(UsageFilter filter, Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
        parameters.put(PAGEABLE_KEY, pageable);
        parameters.put(SORT_KEY, sort);
        return selectList("IUsageMapper.findDtosByFilter", parameters);
    }

    @Override
    public List<Usage> findByStatusAndProductFamily(UsageStatusEnum status, String productFamily) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(STATUS_KEY, Objects.requireNonNull(status));
        parameters.put("productFamily", Objects.requireNonNull(productFamily));
        return selectList("IUsageMapper.findByStatusAndProductFamily", parameters);
    }

    @Override
    public List<Usage> findByScenarioId(String scenarioId) {
        return selectList("IUsageMapper.findByScenarioId", Objects.requireNonNull(scenarioId));
    }

    @Override
    public List<Usage> findByIds(List<String> usageIds) {
        List<Usage> result = new ArrayList<>();
        Iterables.partition(Objects.requireNonNull(usageIds), 32000)
            .forEach(partition -> result.addAll(selectList("IUsageMapper.findByIds", partition)));
        return result;
    }

    @Override
    public List<Usage> findForReconcile(String scenarioId) {
        return selectList("IUsageMapper.findForReconcile", Objects.requireNonNull(scenarioId));
    }

    @Override
    public Map<Long, Usage> findRightsholdersInformation(String scenarioId) {
        RightsholdersInfoResultHandler handler = new RightsholdersInfoResultHandler();
        getTemplate().select("IUsageMapper.findRightsholdersInformation", Objects.requireNonNull(scenarioId), handler);
        return handler.getRhToUsageMap();
    }

    @Override
    public int findCountByFilter(UsageFilter filter) {
        return selectOne("IUsageMapper.findCountByFilter", ImmutableMap.of(FILTER_KEY, Objects.requireNonNull(filter)));
    }

    @Override
    public void writeScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream) {
        Objects.requireNonNull(pipedOutputStream);
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("scenarioId", Objects.requireNonNull(scenarioId));
        try (ScenarioUsagesCsvReportHandler handler = new ScenarioUsagesCsvReportHandler(pipedOutputStream)) {
            int size = selectOne("IUsageMapper.findCountByScenarioId", parameters);
            for (int offset = 0; offset < size; offset += REPORT_BATCH_SIZE) {
                parameters.put(PAGEABLE_KEY, new Pageable(offset, REPORT_BATCH_SIZE));
                getTemplate().select("IUsageMapper.findDtoByScenarioId", parameters, handler);
            }
        }
    }

    @Override
    public void writeUsagesCsvReport(UsageFilter filter, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
        try (UsageCsvReportHandler handler = new UsageCsvReportHandler(Objects.requireNonNull(pipedOutputStream))) {
            if (!filter.isEmpty()) {
                int size = findCountByFilter(filter);
                for (int offset = 0; offset < size; offset += REPORT_BATCH_SIZE) {
                    parameters.put(PAGEABLE_KEY, new Pageable(offset, REPORT_BATCH_SIZE));
                    getTemplate().select("IUsageMapper.findDtosByFilter", parameters, handler);
                }
            }
        }
    }

    @Override
    public Set<String> writeUsagesForResearchAndFindIds(UsageFilter filter, OutputStream outputStream) {
        Set<String> usageIds = new HashSet<>();
        try (SendForResearchCsvReportHandler handler =
                 new SendForResearchCsvReportHandler(Objects.requireNonNull(outputStream))) {
            if (!Objects.requireNonNull(filter).isEmpty()) {
                getTemplate().select("IUsageMapper.findDtosByFilter", ImmutableMap.of(FILTER_KEY, filter), handler);
                usageIds = handler.getUsagesIds();
            }
        }
        return usageIds;
    }

    @Override
    public BigDecimal getTotalAmountByStandardNumberAndBatchId(String standardNumber, String batchId) {
        Set<UsageStatusEnum> statuses =
            Sets.newHashSet(UsageStatusEnum.NEW, UsageStatusEnum.NTS_WITHDRAWN, UsageStatusEnum.WORK_NOT_FOUND);
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("statuses", Objects.requireNonNull(statuses));
        parameters.put("standardNumber", standardNumber);
        parameters.put("batchId", batchId);
        BigDecimal totalAmount =
            ObjectUtils.defaultIfNull(selectOne("IUsageMapper.getTotalAmountByStandardNumberAndBatchId", parameters),
                BigDecimal.ZERO);
        return totalAmount.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal getTotalAmountByTitleAndBatchId(String title, String batchId) {
        Set<UsageStatusEnum> statuses =
            Sets.newHashSet(UsageStatusEnum.NEW, UsageStatusEnum.NTS_WITHDRAWN, UsageStatusEnum.WORK_NOT_FOUND);
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("statuses", Objects.requireNonNull(statuses));
        parameters.put("title", title);
        parameters.put("batchId", batchId);
        BigDecimal totalAmount =
            ObjectUtils.defaultIfNull(selectOne("IUsageMapper.getTotalAmountByTitleAndBatchId", parameters),
                BigDecimal.ZERO);
        return totalAmount.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public void deleteByBatchId(String batchId) {
        delete("IUsageMapper.deleteByBatchId", Objects.requireNonNull(batchId));
    }

    @Override
    public void deleteById(String usageId) {
        delete("IUsageMapper.deleteByUsageId", Objects.requireNonNull(usageId));
    }

    @Override
    public void deleteByScenarioId(String scenarioId) {
        checkArgument(StringUtils.isNotBlank(scenarioId));
        delete("IUsageMapper.deleteByScenarioId", scenarioId);
    }

    @Override
    public List<Usage> findWithAmountsAndRightsholders(UsageFilter filter) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
        parameters.put(STATUS_KEY, UsageStatusEnum.ELIGIBLE);
        return selectList("IUsageMapper.findWithAmountsAndRightsholders", parameters);
    }

    @Override
    public List<Long> findInvalidRightsholdersByFilter(UsageFilter filter) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(1);
        parameters.put(FILTER_KEY, Objects.requireNonNull(filter));
        return selectList("IUsageMapper.findInvalidRightsholdersByFilter", parameters);
    }

    @Override
    public void addToScenario(List<Usage> usages) {
        Objects.requireNonNull(usages).forEach(usage -> update("IUsageMapper.addToScenario", usage));
    }

    @Override
    public void deleteFromScenario(String scenarioId, String updateUser) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(UPDATE_USER_KEY, Objects.requireNonNull(updateUser));
        parameters.put(STATUS_KEY, UsageStatusEnum.ELIGIBLE);
        update("IUsageMapper.deleteFromScenario", parameters);
    }

    @Override
    public void deleteFromScenario(List<String> usagesIds, String userName) {
        checkArgument(CollectionUtils.isNotEmpty(usagesIds));
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put(STATUS_KEY, UsageStatusEnum.ELIGIBLE);
        params.put(UPDATE_USER_KEY, userName);
        usagesIds.forEach(usageId -> {
            params.put(USAGE_ID_KEY, usageId);
            update("IUsageMapper.deleteFromScenarioByUsageId", params);
        });
    }

    @Override
    public int findCountByUsageIdAndStatus(String usageId, UsageStatusEnum statusEnum) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(USAGE_ID_KEY, Objects.requireNonNull(usageId));
        parameters.put(STATUS_KEY, Objects.requireNonNull(statusEnum));
        return selectOne("IUsageMapper.findCountByUsageIdAndStatus", parameters);
    }

    @Override
    public List<RightsholderTotalsHolder> findRightsholderTotalsHoldersByScenarioId(String scenarioId,
                                                                                    String searchValue,
                                                                                    Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(4);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        parameters.put(PAGEABLE_KEY, pageable);
        parameters.put(SORT_KEY, sort);
        return selectList("IUsageMapper.findRightsholderTotalsHoldersByScenarioId", parameters);
    }

    @Override
    public int findRightsholderTotalsHolderCountByScenarioId(String scenarioId, String searchValue) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        return selectOne("IUsageMapper.findRightsholderTotalsHolderCountByScenarioId", parameters);
    }

    @Override
    public boolean isScenarioEmpty(String scenarioId) {
        return selectOne("IUsageMapper.isScenarioEmpty", Objects.requireNonNull(scenarioId));
    }

    @Override
    public int findCountByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId, String searchValue) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("accountNumber", Objects.requireNonNull(accountNumber));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        return selectOne("IUsageMapper.findCountByScenarioIdAndRhAccountNumber", parameters);
    }

    @Override
    public List<UsageDto> findByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId,
                                                             String searchValue, Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(5);
        parameters.put("accountNumber", Objects.requireNonNull(accountNumber));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        parameters.put(PAGEABLE_KEY, pageable);
        parameters.put(SORT_KEY, sort);
        return selectList("IUsageMapper.findByScenarioIdAndRhAccountNumber", parameters);
    }

    @Override
    public List<String> findIdsByScenarioIdRroAccountNumberRhAccountNumbers(String scenarioId, Long rroAccountNumber,
                                                                            List<Long> accountNumbers) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put("rroAccountNumber", Objects.requireNonNull(rroAccountNumber));
        List<String> result = new ArrayList<>(accountNumbers.size());
        Iterables.partition(accountNumbers, 32000).forEach(partition -> {
            parameters.put("accountNumbers", Objects.requireNonNull(partition));
            result.addAll(selectList("IUsageMapper.findIdsByScenarioIdRroAccountNumberRhAccountNumbers", parameters));
        });
        return result;
    }

    @Override
    public int findCountForAudit(AuditFilter filter) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
        params.put(FILTER_KEY, escapeSqlLikePattern(Objects.requireNonNull(filter)));
        return selectOne("IUsageMapper.findCountForAudit", params);
    }

    @Override
    public List<UsageDto> findForAudit(AuditFilter filter, Pageable pageable, Sort sort) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put(FILTER_KEY, escapeSqlLikePattern(Objects.requireNonNull(filter)));
        params.put("pageable", pageable);
        params.put("sort", sort);
        return selectList("IUsageMapper.findForAudit", params);
    }

    @Override
    public void writeAuditCsvReport(AuditFilter filter, PipedOutputStream pipedOutputStream) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        try (AuditCsvReportHandler handler = new AuditCsvReportHandler(Objects.requireNonNull(pipedOutputStream))) {
            if (!Objects.requireNonNull(filter).isEmpty()) {
                int size = findCountForAudit(filter);
                parameters.put(FILTER_KEY, escapeSqlLikePattern(filter));
                for (int offset = 0; offset < size; offset += REPORT_BATCH_SIZE) {
                    parameters.put(PAGEABLE_KEY, new Pageable(offset, REPORT_BATCH_SIZE));
                    getTemplate().select("IUsageMapper.findForAudit", parameters, handler);
                }
            }
        }
    }

    @Override
    public List<Usage> findByStatuses(UsageStatusEnum... statuses) {
        return selectList("IUsageMapper.findByStatuses", Objects.requireNonNull(statuses));
    }

    @Override
    public void updateStatus(Set<String> usageIds, UsageStatusEnum status) {
        checkArgument(CollectionUtils.isNotEmpty(usageIds));
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(STATUS_KEY, Objects.requireNonNull(status));
        parameters.put(UPDATE_USER_KEY, StoredEntity.DEFAULT_USER);
        usageIds.forEach(usageId -> {
            parameters.put(USAGE_ID_KEY, usageId);
            update("IUsageMapper.updateStatus", parameters);
        });
    }

    @Override
    public void updateStatusAndRhAccountNumber(Set<String> usageIds, UsageStatusEnum status, Long rhAccountNumber) {
        checkArgument(CollectionUtils.isNotEmpty(usageIds));
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(STATUS_KEY, Objects.requireNonNull(status));
        parameters.put(RH_ACCOUNT_NUMBER_KEY, Objects.requireNonNull(rhAccountNumber));
        parameters.put(UPDATE_USER_KEY, StoredEntity.DEFAULT_USER);
        Iterables.partition(usageIds, 32000).forEach(partition -> {
            parameters.put("usageIds", partition);
            update("IUsageMapper.updateStatusAndRhAccountNumber", parameters);
        });
    }

    @Override
    public void update(List<Usage> usages) {
        checkArgument(CollectionUtils.isNotEmpty(usages));
        usages.forEach(usage -> update("IUsageMapper.update", usage));
    }

    @Override
    public String updateProcessedUsage(Usage usage) {
        return selectOne("IUsageMapper.updateProcessedUsage", Objects.requireNonNull(usage));
    }

    @Override
    public void updateResearchedUsages(Collection<ResearchedUsage> researchedUsages) {
        Objects.requireNonNull(researchedUsages);
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(4);
        parameters.put(UPDATE_USER_KEY, StoredEntity.DEFAULT_USER);
        parameters.put(STATUS_KEY, UsageStatusEnum.WORK_FOUND);
        researchedUsages.forEach(researchedUsage -> {
            parameters.put("usage", researchedUsage);
            update("IUsageMapper.updateResearchedUsage", parameters);
        });
    }

    @Override
    public void writeUndistributedLiabilitiesCsvReport(LocalDate paymentDate, OutputStream outputStream) {
        try (UndistributedLiabilitiesReportHandler handler =
                 new UndistributedLiabilitiesReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IUsageMapper.findUndistributedLiabilitiesReportDtos",
                Objects.requireNonNull(paymentDate), handler);
        }
    }

    @Override
    public void writeServiceFeeTrueUpCsvReport(LocalDate fromDate, LocalDate toDate, LocalDate paymentDateTo,
                                               OutputStream outputStream, Long claAccountNumber) {
        try (ServiceFeeTrueUpReportHandler handler =
                 new ServiceFeeTrueUpReportHandler(Objects.requireNonNull(outputStream))) {
            Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(7);
            parameters.put("paymentDateTo", Objects.requireNonNull(paymentDateTo));
            parameters.put("fromDate", Objects.requireNonNull(fromDate));
            parameters.put("toDate", Objects.requireNonNull(toDate));
            parameters.put("productFamilyClaFas", FdaConstants.CLA_FAS_PRODUCT_FAMILY);
            parameters.put("accountNumberClaFas", claAccountNumber);
            parameters.put("action", ScenarioActionTypeEnum.SENT_TO_LM);
            parameters.put(STATUS_KEY, UsageStatusEnum.SENT_TO_LM);
            getTemplate().select("IUsageMapper.findServiceFeeTrueUpReportDtos", parameters, handler);
        }
    }

    @Override
    public void writeSummaryMarketCsvReport(List<String> batchIds, OutputStream outputStream) {
        try (SummaryMarketReportHandler handler = new SummaryMarketReportHandler(
            Objects.requireNonNull(outputStream))) {
            getTemplate().select("IUsageMapper.findSummaryMarketReportDtos", Objects.requireNonNull(batchIds), handler);
        }
    }

    @Override
    public void writeBatchSummaryCsvReport(OutputStream outputStream) {
        try (BatchSummaryReportHandler handler = new BatchSummaryReportHandler(Objects.requireNonNull(outputStream))) {
            getTemplate().select("IUsageMapper.findBatchSummaryReportDtos", handler);
        }
    }

    @Override
    public void writeResearchStatusCsvReport(OutputStream outputStream) {
        try (ResearchStatusReportHandler handler = new ResearchStatusReportHandler(
            Objects.requireNonNull(outputStream))) {
            getTemplate().select("IUsageMapper.findResearchStatusReportDtos", handler);
        }
    }

    @Override
    public boolean isValidUsagesState(UsageFilter filter, UsageStatusEnum status) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put(FILTER_KEY, Objects.requireNonNull(filter));
        params.put(STATUS_KEY, Objects.requireNonNull(status));
        return selectOne("IUsageMapper.isValidUsagesState", params);
    }

    private AuditFilter escapeSqlLikePattern(AuditFilter auditFilter) {
        AuditFilter filterCopy = new AuditFilter(auditFilter);
        filterCopy.setCccEventId(escapeSqlLikePattern(filterCopy.getCccEventId()));
        filterCopy.setDistributionName(escapeSqlLikePattern(filterCopy.getDistributionName()));
        filterCopy.setSearchValue(escapeSqlLikePattern(filterCopy.getSearchValue()));
        return filterCopy;
    }

    @Override
    public List<String> insertNtsUsagesWithAudit(UsageBatch usageBatch, String auditActionReason, String userName) {
        Objects.requireNonNull(usageBatch);
        Objects.requireNonNull(usageBatch.getFundPool());
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(8);
        params.put("batchId", Objects.requireNonNull(usageBatch.getId()));
        params.put("marketPeriodFrom", Objects.requireNonNull(usageBatch.getFundPool().getFundPoolPeriodFrom()));
        params.put("marketPeriodTo", Objects.requireNonNull(usageBatch.getFundPool().getFundPoolPeriodTo()));
        params.put("markets", Objects.requireNonNull(usageBatch.getFundPool().getMarkets()));
        params.put(STATUS_KEY, UsageStatusEnum.ARCHIVED);
        params.put("auditActionReason", Objects.requireNonNull(auditActionReason));
        params.put("createUser", Objects.requireNonNull(userName));
        params.put("updateUser", Objects.requireNonNull(userName));
        return selectList("IUsageMapper.insertNtsUsagesWithAudit", params);
    }
}
