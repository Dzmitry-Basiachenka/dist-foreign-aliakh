package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludePayeesFilterController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludePayeesFilterWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Controller for exclude payees filter.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/19
 *
 * @author Uladzislau Shalamitski
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ExcludePayeesFilterController extends CommonController<IExcludePayeesFilterWidget>
    implements IExcludePayeesFilterController {

    private static final Map<String, Boolean> PARTICIPATING_STATUSES = new LinkedHashMap<>();

    static {
        PARTICIPATING_STATUSES.put(ForeignUi.getMessage("label.participating"), Boolean.TRUE);
        PARTICIPATING_STATUSES.put(ForeignUi.getMessage("label.not_participating"), Boolean.FALSE);
    }

    @Override
    public Map<String, Boolean> getParticipatingStatuses() {
        return Collections.unmodifiableMap(PARTICIPATING_STATUSES);
    }

    @Override
    protected IExcludePayeesFilterWidget instantiateWidget() {
        return new ExcludePayeesFilterWidget();
    }
}
