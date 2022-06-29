package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.RightsholderAclTotalsHolder;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioService;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link IAclScenarioController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/23/2022
 *
 * @author Anton Azarenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclScenarioController extends CommonController<IAclScenarioWidget> implements IAclScenarioController {

    @Autowired
    private IAclScenarioService scenarioService;
    private AclScenario aclScenario;

    @Override
    public AclScenario getScenario() {
        return aclScenario;
    }

    @Override
    public void setScenario(AclScenario scenario) {
        this.aclScenario = scenario;
    }

    @Override
    public void performSearch() {
        getWidget().applySearch();
    }

    @Override
    public AclScenarioDto getAclScenarioWithAmountsAndLastAction() {
        return scenarioService.getAclScenarioWithAmountsAndLastAction(aclScenario.getId());
    }

    @Override
    public List<RightsholderAclTotalsHolder> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return scenarioService.getRightsholderAclTotalsHoldersByScenarioId(aclScenario.getId(),
            getWidget().getSearchValue(), new Pageable(startIndex, count), sort);
    }

    @Override
    public int getSize() {
        return scenarioService.getRightsholderAclTotalsHolderCountByScenarioId(aclScenario.getId(),
            getWidget().getSearchValue());
    }

    @Override
    protected IAclScenarioWidget instantiateWidget() {
        return new AclScenarioWidget(this);
    }
}
