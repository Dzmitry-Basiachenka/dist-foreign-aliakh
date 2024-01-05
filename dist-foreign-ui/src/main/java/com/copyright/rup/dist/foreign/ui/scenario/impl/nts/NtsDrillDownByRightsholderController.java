package com.copyright.rup.dist.foreign.ui.scenario.impl.nts;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsDrillDownByRightsholderWidget;
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
 * Implementation for {@link INtsDrillDownByRightsholderController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/9/19
 *
 * @author Stanislau Rudak
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NtsDrillDownByRightsholderController extends CommonDrillDownByRightsholderController
    implements INtsDrillDownByRightsholderController {

    private static final long serialVersionUID = 152373475503491541L;

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
    protected INtsDrillDownByRightsholderWidget instantiateWidget() {
        return new NtsDrillDownByRightsholderWidget();
    }
}
