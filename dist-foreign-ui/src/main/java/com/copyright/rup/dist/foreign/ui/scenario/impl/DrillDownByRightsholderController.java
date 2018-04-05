package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static java.util.Objects.requireNonNull;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IDrillDownByRightsholderWidget;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Window;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Controller for {@link DrillDownByRightsholderWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/11/17
 *
 * @author Ihar Suvorau
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DrillDownByRightsholderController extends CommonController<IDrillDownByRightsholderWidget> implements
    IDrillDownByRightsholderController {

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
    public IDrillDownByRightsholderWidget instantiateWidget() {
        return new DrillDownByRightsholderWidget();
    }

    @Override
    public void showWidget(Long accountNumber, String rhName, Scenario scenario) {
        requireNonNull(accountNumber);
        requireNonNull(scenario);
        selectedRightsholderAccountNumber = accountNumber;
        selectedScenario = scenario;
        Window drillDownWindow = (Window) initWidget();
        drillDownWindow.setCaption(ForeignUi.getMessage("table.foreign.rightsholder.format",
            StringUtils.defaultString(rhName), accountNumber));
        Windows.showModalWindow(drillDownWindow);
    }
}
