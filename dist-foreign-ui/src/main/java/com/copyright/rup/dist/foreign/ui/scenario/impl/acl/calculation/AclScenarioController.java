package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.RightsholderAclTotalsHolder;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.vaadin.data.provider.QuerySortOrder;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
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
        //TODO {dbasiachenka} implement
        return new AclScenarioDto();
    }

    @Override
    public List<RightsholderAclTotalsHolder> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        //TODO {dbasiachenka} implement
        return Collections.emptyList();
    }

    @Override
    public int getSize() {
        //TODO {dbasiachenka} implement
        return 0;
    }

    @Override
    protected IAclScenarioWidget instantiateWidget() {
        return new AclScenarioWidget(this);
    }
}
