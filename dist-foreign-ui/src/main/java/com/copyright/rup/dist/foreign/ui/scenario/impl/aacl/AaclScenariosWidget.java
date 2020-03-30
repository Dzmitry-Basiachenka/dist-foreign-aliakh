package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosMediator;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclScenariosWidget;
import com.copyright.rup.dist.foreign.ui.scenario.impl.CommonScenariosWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.aacl.AaclScenarioParameterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.aacl.AggregateLicenseeClassMappingWindow;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link IAaclScenariosWidget}.
 * <p>
 * Copyright (C) 20120 copyright.com
 * <p>
 * Date: 12/11/20
 *
 * @author Stanislau Rudak
 */
public class AaclScenariosWidget extends CommonScenariosWidget implements IAaclScenariosWidget {

    private final IAaclScenariosController controller;
    private AaclScenariosMediator mediator;
    private AaclScenarioParameterWidget<List<DetailLicenseeClass>> licenseeClassMappingWidget;

    /**
     * Controller.
     *
     * @param controller        instance of {@link IAaclScenariosController}
     * @param historyController instance of {@link IScenarioHistoryController}
     */
    AaclScenariosWidget(IAaclScenariosController controller, IScenarioHistoryController historyController) {
        super(historyController);
        setController(controller);
        this.controller = controller;
    }

    @Override
    public IMediator initMediator() {
        mediator = new AaclScenariosMediator();
        mediator.selectedScenarioChanged(getSelectedScenario());
        return mediator;
    }

    @Override
    protected IScenariosMediator getMediator() {
        return mediator;
    }

    @Override
    protected HorizontalLayout initButtonsLayout() {
        //TODO: add buttons to layout in scope of corresponding story
        HorizontalLayout layout = new HorizontalLayout();
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(layout, "scenarios-buttons");
        return layout;
    }

    @Override
    protected VerticalLayout initMetadataLayout() {
        //TODO: add components to scenario metadata in scope of B-57242 story
        licenseeClassMappingWidget = new AaclScenarioParameterWidget<>(
            ForeignUi.getMessage("button.licensee_class_mapping"),
            Collections::emptyList, () -> new AggregateLicenseeClassMappingWindow(false));
        VerticalLayout metadataLayout = new VerticalLayout(licenseeClassMappingWidget);
        metadataLayout.setMargin(new MarginInfo(false, true, false, true));
        VaadinUtils.setMaxComponentsWidth(metadataLayout);
        return metadataLayout;
    }

    @Override
    protected void updateScenarioMetadata(Scenario scenarioWithAmounts) {
        //TODO: implement logic to update scenario metadata in scope of B-57242 story
        licenseeClassMappingWidget.setAppliedParameters(
            controller.getDetailLicenseeClassesByScenarioId(scenarioWithAmounts.getId()));
    }
}
