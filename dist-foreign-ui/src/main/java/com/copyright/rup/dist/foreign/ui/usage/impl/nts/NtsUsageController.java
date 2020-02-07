package com.copyright.rup.dist.foreign.ui.usage.impl.nts;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.IFasNtsUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.nts.INtsUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.nts.IWorkClassificationController;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageController;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of {@link INtsUsageController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/16/19
 *
 * @author Darya Baraukova
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NtsUsageController extends CommonUsageController implements INtsUsageController {

    @Autowired
    private IStreamSourceHandler streamSourceHandler;
    @Autowired
    private IWorkClassificationController workClassificationController;
    @Autowired
    private IFasNtsUsageFilterController fasNtsUsageFilterController;
    @Autowired
    private IFasUsageService fasUsageService;

    @Override
    public ICommonUsageFilterController getUsageFilterController() {
        return fasNtsUsageFilterController;
    }

    @Override
    public int getBeansCount() {
        return fasUsageService.getUsagesCount(getUsageFilterController().getWidget().getAppliedFilter());
    }

    @Override
    public List<UsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return fasUsageService.getUsageDtos(getUsageFilterController().getWidget().getAppliedFilter(),
            new Pageable(startIndex, count), sort);
    }

    @Override
    public IStreamSource getExportUsagesStreamSource() {
        return streamSourceHandler.getCsvStreamSource(() -> "export_usage_",
            pos -> getReportService().writeNtsUsageCsvReport(
                getUsageFilterController().getWidget().getAppliedFilter(), pos));
    }

    @Override
    public int loadNtsBatch(UsageBatch usageBatch) {
        String userName = RupContextUtils.getUserName();
        List<String> ntsUsageIds = getUsageBatchService().insertNtsBatch(usageBatch, userName);
        getUsageBatchService().getAndSendForGettingRights(ntsUsageIds, usageBatch.getName());
        getUsageFilterController().getWidget().clearFilter();
        return CollectionUtils.size(ntsUsageIds);
    }

    @Override
    public boolean isValidFilteredUsageStatus(UsageStatusEnum status) {
        return getUsageService()
            .isValidFilteredUsageStatus(getUsageFilterController().getWidget().getAppliedFilter(), status);
    }

    @Override
    public void createPreServiceFeeFund(FundPool fundPool, Set<String> batchIds) {
        getFundPoolService().create(fundPool, batchIds);
    }

    @Override
    public boolean fundPoolExists(String name) {
        return getFundPoolService().fundPoolNameExists(name);
    }

    @Override
    public List<FundPool> getPreServiceSeeFunds() {
        return getFundPoolService().getPreServiceFeeFunds(getSelectedProductFamily());
    }

    @Override
    public List<String> getMarkets() {
        return getUsageService().getMarkets();
    }

    @Override
    public List<UsageBatch> getUsageBatchesForPreServiceFeeFunds() {
        return getUsageBatchService().getUsageBatchesForPreServiceFeeFunds();
    }

    @Override
    public List<FundPool> getPreServiceFeeFundsNotAttachedToScenario() {
        return getFundPoolService().getPreServiceFeeFundsNotAttachedToScenario();
    }

    @Override
    public void deletePreServiceFeeFund(FundPool fundPool) {
        getFundPoolService().deletePreServiceFeeFund(fundPool);
    }

    @Override
    public int getUsagesCountForNtsBatch(UsageBatch usageBatch) {
        return getNtsUsageService().getUsagesCountForBatch(usageBatch);
    }

    @Override
    public IWorkClassificationController getWorkClassificationController() {
        return workClassificationController;
    }

    @Override
    public Map<String, List<String>> getBatchNamesWithInvalidStmOrNonStmUsagesState(Set<String> batchIds) {
        return getUsageBatchService().getClassifcationToBatchNamesWithoutUsagesForStmOrNonStm(batchIds);
    }

    @Override
    public List<String> getBatchNamesWithUnclassifiedWorks(Set<String> batchIds) {
        return getUsageBatchService().getBatchNamesWithUnclassifiedWorks(batchIds);
    }

    @Override
    public IStreamSource getPreServiceFeeFundBatchesStreamSource(List<UsageBatch> batches,
                                                                 BigDecimal totalGrossAmount) {
        return streamSourceHandler.getCsvStreamSource(() -> "pre_service_fee_fund_batches_",
            pos -> getReportService().writePreServiceFeeFundBatchesCsvReport(batches, totalGrossAmount, pos));
    }

    @Override
    public List<String> getProcessingBatchesNames(Set<String> batchesIds) {
        return getUsageBatchService().getProcessingBatchesNames(batchesIds);
    }

    @Override
    public String getScenarioNameAssociatedWithPreServiceFeeFund(String fundId) {
        return getScenarioService().getScenarioNameByPreServiceFeeFundId(fundId);
    }

    @Override
    public Scenario createNtsScenario(String scenarioName, Scenario.NtsFields ntsFields, String description) {
        Scenario scenario = getScenarioService().createNtsScenario(scenarioName, ntsFields, description,
            getUsageFilterController().getWidget().getAppliedFilter());
        getUsageFilterController().getWidget().clearFilter();
        return scenario;
    }

    @Override
    public Map<String, String> getBatchesNamesToScenariosNames(Set<String> batchesIds) {
        return getUsageBatchService().getBatchesNamesToScenariosNames(batchesIds);
    }

    @Override
    protected ICommonUsageWidget instantiateWidget() {
        return new NtsUsageWidget(this);
    }
}
