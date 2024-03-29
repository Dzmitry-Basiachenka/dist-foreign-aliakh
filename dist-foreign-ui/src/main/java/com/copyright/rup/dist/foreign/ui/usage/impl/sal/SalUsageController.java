package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.SalGradeGroupEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.integration.telesales.api.ITelesalesService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalScenarioService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.service.impl.csv.SalItemBankCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.SalUsageDataCsvProcessor;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageController;

import com.google.common.io.Files;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of {@link ISalUsageController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/29/20
 *
 * @author Ihar Suvorau
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SalUsageController extends CommonUsageController implements ISalUsageController {

    private static final long serialVersionUID = -8012973134324949006L;

    @Autowired
    private ISalUsageService salUsageService;
    @Autowired
    private ISalUsageFilterController salUsageFilterController;
    @Autowired
    private CsvProcessorFactory csvProcessorFactory;
    @Autowired
    private IStreamSourceHandler streamSourceHandler;
    @Autowired
    @Qualifier("df.integration.telesalesCacheService")
    private ITelesalesService telesalesService;
    @Autowired
    private ISalScenarioService salScenarioService;

    @Override
    public String getLicenseeName(Long licenseeAccountNumber) {
        return telesalesService.getLicenseeName(licenseeAccountNumber);
    }

    @Override
    public void loadItemBank(UsageBatch itemBank, List<Usage> usages) {
        List<String> insertedUsageIds = getUsageBatchService().insertSalBatch(itemBank, usages);
        salUsageService.sendForMatching(insertedUsageIds, itemBank.getName());
        getUsageFilterController().getWidget().clearFilter();
    }

    @Override
    public void loadUsageData(UsageBatch itemBank, List<Usage> usages) {
        getUsageBatchService().addUsageDataToSalBatch(itemBank, usages);
        getUsageFilterController().getWidget().clearFilter();
    }

    @Override
    public int getBeansCount() {
        return salUsageService.getUsagesCount(getUsageFilterController().getWidget().getAppliedFilter());
    }

    @Override
    public List<UsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return salUsageService.getUsageDtos(getUsageFilterController().getWidget().getAppliedFilter(),
            new Pageable(startIndex, count), sort);
    }

    @Override
    public List<FundPool> getFundPoolsNotAttachedToScenario() {
        return getFundPoolService().getSalNotAttachedToScenario();
    }

    @Override
    public List<String> getProcessingBatchesNames(Set<String> batchesIds) {
        return getUsageBatchService().getProcessingSalBatchesNames(batchesIds);
    }

    @Override
    public List<String> getIneligibleBatchesNames(Set<String> batchesIds) {
        return getUsageBatchService().getIneligibleBatchesNames(batchesIds);
    }

    @Override
    public boolean isBatchProcessingCompleted(String batchId) {
        return getUsageBatchStatusService().isBatchProcessingCompleted(batchId,
            Set.of(UsageStatusEnum.NEW, UsageStatusEnum.WORK_FOUND, UsageStatusEnum.RH_FOUND));
    }

    @Override
    public void deleteUsageBatch(UsageBatch usageBatch) {
        getUsageBatchService().deleteSalUsageBatch(usageBatch);
        getUsageFilterController().getWidget().clearFilter();
    }

    @Override
    public Scenario createSalScenario(String scenarioName, String fundPoolId, String description) {
        Scenario scenario = salScenarioService.createScenario(scenarioName, fundPoolId, description,
            getUsageFilterController().getWidget().getAppliedFilter());
        getUsageFilterController().getWidget().clearFilter();
        return scenario;
    }

    @Override
    public IStreamSource getExportUsagesStreamSource() {
        return streamSourceHandler.getCsvStreamSource(() -> "export_usage_",
            pos -> getReportService().writeSalUsageCsvReport(
                getUsageFilterController().getWidget().getAppliedFilter(), pos));
    }

    @Override
    public boolean isValidFilteredUsageStatus(UsageStatusEnum status) {
        return getUsageService().isValidFilteredUsageStatus(
            getUsageFilterController().getWidget().getAppliedFilter(), status);
    }

    @Override
    public boolean isValidStatusFilterApplied() {
        UsageStatusEnum appliedStatus = getUsageFilterController().getWidget().getAppliedFilter().getUsageStatus();
        return Objects.nonNull(appliedStatus)
            && (UsageStatusEnum.RH_NOT_FOUND == appliedStatus || UsageStatusEnum.WORK_NOT_GRANTED == appliedStatus);
    }

    @Override
    public boolean fundPoolExists(String name) {
        return getFundPoolService().fundPoolExists(FdaConstants.SAL_PRODUCT_FAMILY, name);
    }

    @Override
    public List<SalGradeGroupEnum> getUsageDataGradeGroups() {
        return salUsageService.getUsageDataGradeGroups(getUsageFilterController().getWidget().getAppliedFilter());
    }

    @Override
    public SalItemBankCsvProcessor getSalItemBankCsvProcessor() {
        return csvProcessorFactory.getSalItemBankCsvProcessor();
    }

    @Override
    public SalUsageDataCsvProcessor getSalUsageDataCsvProcessor(String itemBankId) {
        return csvProcessorFactory.getSalUsageDataCsvProcessor(itemBankId);
    }

    @Override
    public IStreamSource getErrorResultStreamSource(String fileName, ProcessingResult processingResult) {
        return streamSourceHandler.getCsvStreamSource(
            () -> String.format("Error_for_%s", Files.getNameWithoutExtension(fileName)), null,
            processingResult::writeToFile);
    }

    @Override
    public IStreamSource getExportFundPoolsStreamSource() {
        return streamSourceHandler.getCsvStreamSource(() -> "export_fund_pools_",
            pos -> getReportService().writeSalFundPoolsCsvReport(pos));
    }

    @Override
    public FundPool calculateFundPoolAmounts(FundPool fundPool) {
        return getFundPoolService().calculateSalFundPoolAmounts(fundPool);
    }

    @Override
    public void createFundPool(FundPool fundPool) {
        getFundPoolService().createSalFundPool(fundPool);
    }

    @Override
    public ICommonUsageFilterController getUsageFilterController() {
        return salUsageFilterController;
    }

    @Override
    public UsageBatch getSelectedUsageBatch() {
        return getUsageBatchService().getUsageBatchById(
            getUsageFilterController().getWidget().getAppliedFilter().getUsageBatchesIds().iterator().next());
    }

    @Override
    public List<FundPool> getFundPools() {
        return getFundPoolService().getFundPools(FdaConstants.SAL_PRODUCT_FAMILY);
    }

    @Override
    public String getScenarioNameAssociatedWithFundPool(String fundPoolId) {
        return salScenarioService.getScenarioNameByFundPoolId(fundPoolId);
    }

    @Override
    public void deleteFundPool(FundPool fundPool) {
        getFundPoolService().deleteSalFundPool(fundPool);
    }

    @Override
    public boolean usageDataExists(String batchId) {
        return salUsageService.usageDataExists(batchId);
    }

    @Override
    public boolean usageDataExists(Set<UsageDto> itemBankDetails) {
        return salUsageService.usageDataExists(itemBankDetails);
    }

    @Override
    public void deleteUsageData(UsageBatch usageBatch) {
        salUsageService.deleteUsageData(usageBatch);
        refreshWidget();
    }

    @Override
    public void excludeUsageDetails(Set<UsageDto> usagesToExclude) {
        salUsageService.deleteItemBankUsages(usagesToExclude);
        refreshWidget();
    }

    @Override
    public List<UsageBatch> getBatchesNotAttachedToScenario() {
        return getUsageBatchService().getSalNotAttachedToScenario();
    }

    @Override
    public List<UsageDto> getUsageDtosForRhUpdate() {
        return salUsageService.getUsageDtos(getUsageFilterController().getWidget().getAppliedFilter(), null, null);
    }

    @Override
    public void updateToEligibleWithRhAccountNumber(Set<String> usageIds, Long rhAccountNumber, String reason) {
        salUsageService.updateToEligibleWithRhAccountNumber(usageIds, rhAccountNumber, reason);
    }

    @Override
    public int getGridRecordThreshold() {
        return salUsageService.getRecordsThreshold();
    }

    @Override
    protected ICommonUsageWidget instantiateWidget() {
        return new SalUsageWidget(this);
    }
}
