package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.service.api.IFundPoolService;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.ui.main.api.ISettableProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.google.common.base.MoreObjects;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Common controller for {@link ICommonUsageWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/5/19
 *
 * @author Uladzislau Shalamitski
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public abstract class CommonUsageController extends CommonController<ICommonUsageWidget>
    implements ICommonUsageController {

    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private ICommonUsageFilterController filterController;
    @Autowired
    private IPrmIntegrationService prmIntegrationService;
    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IReportService reportService;
    @Autowired
    private IFundPoolService fundPoolService;
    @Autowired
    private ISettableProductFamilyProvider productFamilyProvider;

    @Override
    public ICommonUsageFilterWidget initUsagesFilterWidget() {
        ICommonUsageFilterWidget result = filterController.initWidget();
        result.addListener(FilterChangedEvent.class, this, ICommonUsageController.ON_FILTER_CHANGED);
        return result;
    }

    @Override
    public void onFilterChanged(FilterChangedEvent event) {
        getWidget().refresh();
    }

    @Override
    public int getBeansCount() {
        return usageService.getUsagesCount(filterController.getWidget().getAppliedFilter());
    }

    @Override
    public List<UsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return usageService
            .getUsageDtos(filterController.getWidget().getAppliedFilter(), new Pageable(startIndex, count), sort);
    }

    @Override
    public Rightsholder getRro(Long rroAccountNumber) {
        return MoreObjects.firstNonNull(prmIntegrationService.getRightsholder(rroAccountNumber), new Rightsholder());
    }

    @Override
    public List<String> getPreServiceFeeFundNamesByUsageBatchId(String batchId) {
        return fundPoolService.getPreServiceFeeFundNamesByUsageBatchId(batchId);
    }

    @Override
    public boolean usageBatchExists(String name) {
        return usageBatchService.usageBatchExists(name);
    }

    @Override
    public Scenario createScenario(String scenarioName, String description) {
        Scenario scenario = scenarioService.createScenario(scenarioName, description,
            filterController.getWidget().getAppliedFilter());
        filterController.getWidget().clearFilter();
        return scenario;
    }

    @Override
    public List<String> getScenariosNamesAssociatedWithUsageBatch(String batchId) {
        return ObjectUtils.defaultIfNull(scenarioService.getScenariosNamesByUsageBatchId(batchId),
            Collections.emptyList());
    }

    @Override
    public List<UsageBatch> getUsageBatches(String productFamily) {
        return usageBatchService.getUsageBatchesByProductFamilies(Collections.singleton(productFamily));
    }

    @Override
    public void deleteUsageBatch(UsageBatch usageBatch) {
        usageBatchService.deleteUsageBatch(usageBatch);
        filterController.getWidget().clearFilter();
    }

    @Override
    public boolean isValidUsagesState(UsageStatusEnum status) {
        return usageService.isValidUsagesState(filterController.getWidget().getAppliedFilter(), status);
    }

    @Override
    public void onScenarioCreated(ScenarioCreateEvent event) {
        getWidget().fireWidgetEvent(event);
    }

    @Override
    public String getSelectedProductFamily() {
        return productFamilyProvider.getSelectedProductFamily();
    }

    @Override
    public List<Long> getInvalidRightsholders() {
        return usageService.getInvalidRightsholdersByFilter(filterController.getWidget().getAppliedFilter());
    }

    @Override
    public void clearFilter() {
        filterController.getWidget().clearFilter();
    }

    @Override
    public boolean scenarioExists(String name) {
        return scenarioService.scenarioExists(name);
    }

    /**
     * @return {@link IScenarioService} instance.
     */
    protected IScenarioService getScenarioService() {
        return scenarioService;
    }


    /**
     * @return {@link IFundPoolService} instance.
     */
    protected IFundPoolService getFundPoolService() {
        return fundPoolService;
    }

    /**
     * @return {@link IUsageBatchService} instance.
     */
    protected IUsageBatchService getUsageBatchService() {
        return usageBatchService;
    }

    /**
     * @return {@link IUsageService} instance.
     */
    protected IUsageService getUsageService() {
        return usageService;
    }

    /**
     * @return {@link IReportService} instance.
     */
    protected IReportService getReportService() {
        return reportService;
    }

    /**
     * @return {@link ICommonUsageFilterController} instance.
     */
    protected ICommonUsageFilterController getUsageFilterController() {
        return filterController;
    }

    /**
     * Instantiates widget.
     *
     * @return {@link ICommonUsageWidget} instance
     */
    protected abstract ICommonUsageWidget instantiateWidget();
}
