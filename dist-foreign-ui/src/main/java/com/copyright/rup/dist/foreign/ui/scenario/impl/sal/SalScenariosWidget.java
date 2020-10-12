package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosMediator;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenariosWidget;
import com.copyright.rup.dist.foreign.ui.scenario.impl.CommonScenariosWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * Implementation of {@link ISalScenariosWidget}.
 * <p>
 * Copyright (C) 20120 copyright.com
 * <p>
 * Date: 09/22/2020
 *
 * @author Aliaksandr Liakh
 */
public class SalScenariosWidget extends CommonScenariosWidget implements ISalScenariosWidget {

    private SalScenariosMediator mediator;
    private final Button deleteButton = Buttons.createButton(ForeignUi.getMessage("button.delete"));

    /**
     * Controller.
     *
     * @param controller        instance of {@link ISalScenariosController}
     * @param historyController instance of {@link IScenarioHistoryController}
     */
    SalScenariosWidget(ISalScenariosController controller, IScenarioHistoryController historyController) {
        super(historyController);
        setController(controller);
    }

    @Override
    public IMediator initMediator() {
        mediator = new SalScenariosMediator();
        mediator.setDeleteButton(deleteButton);
        mediator.selectedScenarioChanged(getSelectedScenario());
        return mediator;
    }

    @Override
    protected IScenariosMediator getMediator() {
        return mediator;
    }

    @Override
    protected HorizontalLayout initButtonsLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        addButtonsListeners();
        VaadinUtils.setButtonsAutoDisabled(deleteButton);
        layout.addComponents(deleteButton);
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(layout, "scenarios-buttons");
        return layout;
    }

    @Override
    protected VerticalLayout initMetadataLayout() {
        // TODO implement metadata panel for SAL scenarios
        VerticalLayout metadataLayout = new VerticalLayout();
        metadataLayout.setMargin(new MarginInfo(false, true, false, true));
        VaadinUtils.setMaxComponentsWidth(metadataLayout);
        return metadataLayout;
    }

    @Override
    protected void updateScenarioMetadata(Scenario scenarioWithAmounts) {
        // TODO implement updating metadata panel for SAL scenarios
    }

    private void addButtonsListeners() {
        deleteButton.addClickListener(event -> getController().onDeleteButtonClicked());
    }
}
