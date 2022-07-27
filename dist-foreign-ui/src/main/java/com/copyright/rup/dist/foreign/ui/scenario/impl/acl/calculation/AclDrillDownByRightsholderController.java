package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioUsageService;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclDrillDownByRightsholderWidget;
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
import java.util.Objects;

/**
 * Implementation of {@link IAclDrillDownByRightsholderController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/14/2022
 *
 * @author Dzmitry Basiachenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclDrillDownByRightsholderController extends CommonController<IAclDrillDownByRightsholderWidget>
    implements IAclDrillDownByRightsholderController {

    @Autowired
    private IAclScenarioUsageService aclScenarioUsageService;
    private Long selectedRightsholderAccountNumber;
    private AclScenario selectedScenario;

    @Override
    public List<AclScenarioDetailDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return aclScenarioUsageService.getByScenarioIdAndRhAccountNumber(selectedRightsholderAccountNumber,
            selectedScenario.getId(), getWidget().getSearchValue(), new Pageable(startIndex, count), sort);
    }

    @Override
    public int getSize() {
        return aclScenarioUsageService.getCountByScenarioIdAndRhAccountNumber(selectedRightsholderAccountNumber,
            selectedScenario.getId(), getWidget().getSearchValue());
    }

    @Override
    public void showWidget(Long accountNumber, String rhName, AclScenario scenario) {
        selectedRightsholderAccountNumber = Objects.requireNonNull(accountNumber);
        selectedScenario = Objects.requireNonNull(scenario);
        Window drillDownWindow = (Window) initWidget();
        drillDownWindow.setCaption(ForeignUi.getMessage("table.foreign.rightsholder.format",
            StringUtils.defaultString(rhName), accountNumber));
        Windows.showModalWindow(drillDownWindow);
    }

    @Override
    protected IAclDrillDownByRightsholderWidget instantiateWidget() {
        return new AclDrillDownByRightsholderWidget();
    }
}
