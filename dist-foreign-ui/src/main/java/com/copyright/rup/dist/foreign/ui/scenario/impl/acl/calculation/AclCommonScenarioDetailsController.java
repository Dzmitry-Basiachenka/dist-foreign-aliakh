package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclCommonScenarioDetailsController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclCommonScenarioDetailsWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.vaadin.data.provider.QuerySortOrder;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link IAclCommonScenarioDetailsController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/14/2022
 *
 * @author Dzmitry Basiachenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public abstract class AclCommonScenarioDetailsController extends CommonController<IAclCommonScenarioDetailsWidget>
    implements IAclCommonScenarioDetailsController {

    private static final long serialVersionUID = 2128943302580500181L;

    @Override
    public abstract List<AclScenarioDetailDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders);

    @Override
    public abstract int getSize();
}
