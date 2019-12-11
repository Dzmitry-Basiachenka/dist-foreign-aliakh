package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.PreServiceFeeFund;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.INtsUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.INtsUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.IWorkClassificationController;

import com.google.common.base.MoreObjects;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Usage controller for NTS product family.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/5/19
 *
 * @author Uladzislau Shalamitski
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NtsUsageController extends CommonUsageController<INtsUsageWidget, INtsUsageController>
    implements INtsUsageController {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IPrmIntegrationService prmIntegrationService;
    @Autowired
    private IReportService reportService;
    @Autowired
    private IStreamSourceHandler streamSourceHandler;
    @Autowired
    private IWorkClassificationController workClassificationController;

    @Override
    public void onFilterChanged(FilterChangedEvent event) {
        getWidget().refresh();
    }

    @Override
    public Rightsholder getRro(Long rroAccountNumber) {
        return MoreObjects.firstNonNull(prmIntegrationService.getRightsholder(rroAccountNumber), new Rightsholder());
    }

    @Override
    public int getUsagesCountForNtsBatch(UsageBatch usageBatch) {
        return getUsageService().getUsagesCountForNtsBatch(usageBatch);
    }

    @Override
    public int loadNtsBatch(UsageBatch usageBatch) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Insert NTS batch. Started. UsageBatchName={}, UserName={}", usageBatch.getName(), userName);
        List<String> ntsUsageIds = getUsageBatchService().insertNtsBatch(usageBatch, userName);
        int insertedUsagesCount = CollectionUtils.size(ntsUsageIds);
        LOGGER.info("Insert NTS batch. Finished. UsageBatchName={}, UserName={}, UsagesCount={}",
            usageBatch.getName(), userName, insertedUsagesCount);
        getUsageBatchService().getAndSendForGettingRights(ntsUsageIds, usageBatch.getName());
        getFilterController().getWidget().clearFilter();
        return insertedUsagesCount;
    }

    @Override
    public List<PreServiceFeeFund> getPreServiceSeeFunds() {
        return getFundPoolService().getPreServiceFeeFunds();
    }

    @Override
    public List<PreServiceFeeFund> getPreServiceFeeFundsNotAttachedToScenario() {
        return getFundPoolService().getPreServiceFeeFundsNotAttachedToScenario();
    }

    @Override
    public void deletePreServiceFeeFund(PreServiceFeeFund fundPool) {
        getFundPoolService().deletePreServiceFeeFund(fundPool);
    }

    @Override
    public Scenario createNtsScenario(String scenarioName, Scenario.NtsFields ntsFields, String description) {
        Scenario scenario = getScenarioService().createNtsScenario(scenarioName, ntsFields, description,
            getFilterController().getWidget().getAppliedFilter());
        getFilterController().getWidget().clearFilter();
        return scenario;
    }

    @Override
    public String getScenarioNameAssociatedWithPreServiceFeeFund(String fundId) {
        return getScenarioService().getScenarioNameByPreServiceFeeFundId(fundId);
    }

    @Override
    public List<UsageBatch> getUsageBatchesForPreServiceFeeFunds() {
        return getUsageBatchService().getUsageBatchesForPreServiceFeeFunds();
    }

    @Override
    public List<String> getMarkets() {
        return getUsageService().getMarkets();
    }

    @Override
    public void deleteUsageBatch(UsageBatch usageBatch) {
        getUsageBatchService().deleteUsageBatch(usageBatch);
        getFilterController().getWidget().clearFilter();
    }

    @Override
    public IStreamSource getExportUsagesStreamSource() {
        return streamSourceHandler.getCsvStreamSource(() -> "export_usage_",
            pos -> reportService.writeUsageCsvReport(getFilterController().getWidget().getAppliedFilter(), pos));
    }

    @Override
    public IWorkClassificationController getWorkClassificationController() {
        return workClassificationController;
    }

    @Override
    public List<Long> getInvalidRightsholders() {
        return getUsageService().getInvalidRightsholdersByFilter(getFilterController().getWidget().getAppliedFilter());
    }

    @Override
    public IStreamSource getPreServiceFeeFundBatchesStreamSource(List<UsageBatch> batches,
                                                                 BigDecimal totalGrossAmount) {
        return streamSourceHandler.getCsvStreamSource(() -> "pre_service_fee_fund_batches_",
            pos -> reportService.writePreServiceFeeFundBatchesCsvReport(batches, totalGrossAmount, pos));
    }

    @Override
    public List<String> getBatchNamesWithUnclassifiedWorks(Set<String> batchIds) {
        return getUsageBatchService().getBatchNamesWithUnclassifiedWorks(batchIds);
    }

    @Override
    public Map<String, List<String>> getBatchNamesWithInvalidStmOrNonStmUsagesState(Set<String> batchIds) {
        return getUsageBatchService().getClassifcationToBatchNamesWithoutUsagesForStmOrNonStm(batchIds);
    }

    @Override
    public List<String> getProcessingBatchesNames(Set<String> batchesIds) {
        return getUsageBatchService().getProcessingBatchesNames(batchesIds);
    }

    @Override
    public Map<String, String> getBatchesNamesToScenariosNames(Set<String> batchesIds) {
        return getUsageBatchService().getBatchesNamesToScenariosNames(batchesIds);
    }

    @Override
    public boolean fundPoolExists(String name) {
        return getFundPoolService().fundPoolNameExists(name);
    }

    @Override
    public void createPreServiceFeeFund(PreServiceFeeFund fundPool, Set<String> batchIds) {
        getFundPoolService().create(fundPool, batchIds);
    }

    @Override
    protected INtsUsageWidget instantiateWidget() {
        return new NtsUsageWidget();
    }
}
