package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasExcludePayeeController;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasExcludePayeeFilterController;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasExcludePayeeWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link IFasExcludePayeeController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/19
 *
 * @author Uladzislau Shalamitski
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FasExcludePayeeController extends CommonController<IFasExcludePayeeWidget>
    implements IFasExcludePayeeController {

    private static final long serialVersionUID = 8540434712859534695L;

    @Autowired
    private IFasExcludePayeeFilterController payeesFilterController;

    @Override
    public IFasExcludePayeeFilterController getExcludePayeesFilterController() {
        return payeesFilterController;
    }

    @Override
    protected IFasExcludePayeeWidget instantiateWidget() {
        return new FasExcludePayeeWidget();
    }
}
