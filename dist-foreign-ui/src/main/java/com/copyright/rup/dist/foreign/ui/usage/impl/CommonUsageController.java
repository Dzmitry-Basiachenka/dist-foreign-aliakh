package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IFundPoolService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.ui.common.ForeignCommonController;
import com.copyright.rup.dist.foreign.ui.main.api.ISettableProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterWidget;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

/**
 * Common controller for {@link ICommonUsageWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/5/19
 *
 * @param <C> controller instance
 * @param <W> widget instance
 * @author Uladzislau Shalamitski
 */
public abstract class CommonUsageController<W extends ICommonUsageWidget<W, C>,
    C extends ICommonUsageController<W, C>> extends ForeignCommonController<W>
    implements ICommonUsageController<W, C> {

    @Autowired
    private IFundPoolService fundPoolService;
    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IUsagesFilterController filterController;
    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private ISettableProductFamilyProvider productFamilyProvider;

    @Override
    public IUsagesFilterWidget initUsagesFilterWidget() {
        IUsagesFilterWidget filterWidget = filterController.initWidget();
        filterWidget.addListener(FilterChangedEvent.class, this, ICommonUsageController.ON_FILTER_CHANGED);
        return filterWidget;
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
            sort = new Sort(sortOrder.getSorted(),
                Sort.Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return usageService
            .getUsageDtos(filterController.getWidget().getAppliedFilter(), new Pageable(startIndex, count), sort);
    }

    @Override
    public List<UsageBatch> getUsageBatches(String productFamily) {
        return usageBatchService.getUsageBatches(productFamily);
    }

    @Override
    public void deleteUsageBatch(UsageBatch usageBatch) {
        usageBatchService.deleteUsageBatch(usageBatch);
        filterController.getWidget().clearFilter();
    }

    @Override
    public boolean usageBatchExists(String name) {
        return usageBatchService.usageBatchExists(name);
    }

    @Override
    public boolean scenarioExists(String name) {
        return scenarioService.scenarioExists(name);
    }

    @Override
    public List<String> getScenariosNamesAssociatedWithUsageBatch(String batchId) {
        return ObjectUtils.defaultIfNull(scenarioService.getScenariosNamesByUsageBatchId(batchId),
            Collections.emptyList());
    }

    @Override
    public boolean isValidUsagesState(UsageStatusEnum status) {
        return usageService.isValidUsagesState(filterController.getWidget().getAppliedFilter(), status);
    }

    @Override
    public List<String> getPreServiceFeeFundNamesByUsageBatchId(String batchId) {
        return fundPoolService.getPreServiceFeeFundNamesByUsageBatchId(batchId);
    }

    @Override
    public void clearFilter() {
        filterController.getWidget().clearFilter();
    }

    @Override
    public String getSelectedProductFamily() {
        return productFamilyProvider.getSelectedProductFamily();
    }

    IScenarioService getScenarioService() {
        return scenarioService;
    }

    IUsageBatchService getUsageBatchService() {
        return usageBatchService;
    }

    IUsageService getUsageService() {
        return usageService;
    }

    IUsagesFilterController getFilterController() {
        return filterController;
    }

    IFundPoolService getFundPoolService() {
        return fundPoolService;
    }
}
