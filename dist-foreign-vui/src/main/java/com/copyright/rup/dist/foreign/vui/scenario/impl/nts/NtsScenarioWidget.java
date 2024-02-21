package com.copyright.rup.dist.foreign.vui.scenario.impl.nts;

import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsScenarioWidget;
import com.copyright.rup.dist.foreign.vui.scenario.impl.CommonScenarioWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IMediator;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 * Implementation of {@link INtsScenarioWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/2019
 *
 * @author Stanislau Rudak
 */
public class NtsScenarioWidget extends CommonScenarioWidget implements INtsScenarioWidget {

    private static final long serialVersionUID = -1182854995445399950L;

    @Override
    public IMediator initMediator() {
        //TODO: {dbasiachenka} implement
        return new NtsScenarioMediator();
    }

    @Override
    protected HorizontalLayout initButtons() {
        //TODO: {dbasiachenka} implement
        return new HorizontalLayout();
    }

    @Override
    public void refresh() {
        //TODO: {dbasiachenka} implement
    }
}
