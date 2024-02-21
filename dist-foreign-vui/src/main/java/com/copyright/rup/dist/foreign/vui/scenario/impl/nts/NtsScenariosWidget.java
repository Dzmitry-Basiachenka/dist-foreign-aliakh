package com.copyright.rup.dist.foreign.vui.scenario.impl.nts;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.vui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.vui.scenario.api.IScenariosMediator;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsScenariosWidget;
import com.copyright.rup.dist.foreign.vui.scenario.impl.CommonScenariosWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IMediator;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Implementation of {@link INtsScenariosWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/11/2019
 *
 * @author Stanislau Rudak
 */
public class NtsScenariosWidget extends CommonScenariosWidget implements INtsScenariosWidget {

    private static final long serialVersionUID = -2144279328509538642L;

    private NtsScenariosMediator mediator;

    /**
     * Controller.
     *
     * @param historyController instance of {@link IScenarioHistoryController}
     */
    NtsScenariosWidget(IScenarioHistoryController historyController) {
        super(historyController);
    }

    @Override
    public IMediator initMediator() {
        mediator = new NtsScenariosMediator();
        return mediator;
    }

    @Override
    protected HorizontalLayout initButtonsLayout() {
        //TODO: {dbasiachenka} implement
        return new HorizontalLayout();
    }

    @Override
    protected VerticalLayout initMetadataLayout() {
        //TODO: {dbasiachenka} implement
        return new VerticalLayout();
    }

    @Override
    protected void updateScenarioMetadata(Scenario scenarioWithAmounts) {
        //TODO: {dbasiachenka} implement
    }

    @Override
    protected IScenariosMediator getMediator() {
        return mediator;
    }
}
