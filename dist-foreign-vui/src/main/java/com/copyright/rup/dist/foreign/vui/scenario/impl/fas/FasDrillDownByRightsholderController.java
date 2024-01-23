package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasDrillDownByRightsholderWidget;
import com.copyright.rup.dist.foreign.vui.scenario.impl.CommonDrillDownByRightsholderController;

import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link IFasDrillDownByRightsholderController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/9/19
 *
 * @author Stanislau Rudak
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FasDrillDownByRightsholderController extends CommonDrillDownByRightsholderController
    implements IFasDrillDownByRightsholderController {

    private static final long serialVersionUID = 3393274374756024792L;

    @Autowired
    private IUsageService usageService;

    @Override
    public int getSize() {
        return usageService.getCountByScenarioAndRhAccountNumber(getSelectedRightsholderAccountNumber(),
            getSelectedScenario(), getWidget().getSearchValue());
    }

    @Override
    public List<UsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return usageService.getByScenarioAndRhAccountNumber(getSelectedRightsholderAccountNumber(),
            getSelectedScenario(), getWidget().getSearchValue(), new Pageable(startIndex, count), sort);
    }

    @Override
    protected IFasDrillDownByRightsholderWidget instantiateWidget() {
        return new FasDrillDownByRightsholderWidget();
    }
}
