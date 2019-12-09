package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.ui.common.ForeignCommonController;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonDrillDownByRightsholderWidget;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Window;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

/**
 * Common controller for {@link ICommonDrillDownByRightsholderWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/9/19
 *
 * @param <W> type of widget
 * @param <C> type of controller
 * @author Stanislau Rudak
 */
public abstract class CommonDrillDownByRightsholderController<W extends ICommonDrillDownByRightsholderWidget<W, C>,
    C extends ICommonDrillDownByRightsholderController<W, C>>
    extends ForeignCommonController<W> implements ICommonDrillDownByRightsholderController<W, C> {

    private Long selectedRightsholderAccountNumber;
    private Scenario selectedScenario;

    @Autowired
    private IUsageService usageService;

    @Override
    public int getSize() {
        return usageService.getCountByScenarioAndRhAccountNumber(selectedRightsholderAccountNumber,
            selectedScenario, getWidget().getSearchValue());
    }

    @Override
    public List<UsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return usageService.getByScenarioAndRhAccountNumber(selectedRightsholderAccountNumber, selectedScenario,
            getWidget().getSearchValue(), new Pageable(startIndex, count), sort);
    }

    @Override
    public void showWidget(Long accountNumber, String rhName, Scenario scenario) {
        selectedRightsholderAccountNumber = Objects.requireNonNull(accountNumber);
        selectedScenario = Objects.requireNonNull(scenario);
        Window drillDownWindow = (Window) initWidget();
        drillDownWindow.setCaption(ForeignUi.getMessage("table.foreign.rightsholder.format",
            StringUtils.defaultString(rhName), accountNumber));
        Windows.showModalWindow(drillDownWindow);
    }
}
