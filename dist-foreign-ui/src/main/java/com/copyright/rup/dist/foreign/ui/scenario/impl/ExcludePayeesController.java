package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludePayeesController;

import com.vaadin.data.provider.QuerySortOrder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link IExcludePayeesController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/19
 *
 * @author Uladzislau Shalamitski
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ExcludePayeesController implements IExcludePayeesController {

    @Override
    public int getBeansCount() {
        //TODO: use service logic here
        return 0;
    }

    @Override
    public List<RightsholderTotalsHolder> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        //TODO: use service logic here
        return Collections.emptyList();
    }
}
