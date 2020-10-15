package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonDrillDownByRightsholderWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.impl.CommonDrillDownByRightsholderController;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of  {@link ISalDrillDownByRightsholderController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 10/14/20
 *
 * @author Anton Azarenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SalDrillDownByRightsholderController extends CommonDrillDownByRightsholderController implements
    ISalDrillDownByRightsholderController {
    @Autowired
    private ISalUsageService usageService;

    @Override
    public int getSize() {
        return usageService.getCountByScenarioAndRhAccountNumber(getSelectedScenario(),
            getSelectedRightsholderAccountNumber(), getWidget().getSearchValue());
    }

    @Override
    public List<UsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return usageService.getByScenarioAndRhAccountNumber(getSelectedScenario(),
            getSelectedRightsholderAccountNumber(), getWidget().getSearchValue(), new Pageable(startIndex, count),
            sort);
    }

    @Override
    protected ICommonDrillDownByRightsholderWidget instantiateWidget() {
        return new SalDrillDownByRightsholderWidget();
    }
}
