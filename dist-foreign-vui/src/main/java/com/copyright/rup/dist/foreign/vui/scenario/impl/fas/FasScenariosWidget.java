package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenariosController;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenariosWidget;
import com.copyright.rup.dist.foreign.vui.scenario.impl.CommonScenariosWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Implementation of {@link IFasScenariosWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/11/19
 *
 * @author Stanislau Rudak
 */
public class FasScenariosWidget extends CommonScenariosWidget implements IFasScenariosWidget {

    private final Button viewButton = Buttons.createButton(ForeignUi.getMessage("button.view"));
    private final Button editNameButton = Buttons.createButton(ForeignUi.getMessage("button.edit_name"));
    private final Button deleteButton = Buttons.createButton(ForeignUi.getMessage("button.delete"));
    private final Button submitButton = Buttons.createButton(ForeignUi.getMessage("button.submit"));
    private final Button rejectButton = Buttons.createButton(ForeignUi.getMessage("button.reject"));
    private final Button approveButton = Buttons.createButton(ForeignUi.getMessage("button.approve"));
    private final Button sendToLmButton = Buttons.createButton(ForeignUi.getMessage("button.send_to_lm"));
    private final Button excludePayeesButton = Buttons.createButton(ForeignUi.getMessage("button.exclude_payees"));
    private final Button reconcileRightsholdersButton =
        Buttons.createButton(ForeignUi.getMessage("button.reconcile_rightsholders"));
    private final Button refreshScenarioButton = Buttons.createButton(ForeignUi.getMessage("button.refresh_scenario"));
    private final Div ownerDiv = new Div();
    private final Div netTotalDiv = new Div();
    private final Div grossTotalDiv = new Div();
    private final Div serviceFeeTotalDiv = new Div();
    private final Div descriptionDiv = new Div();
    private final Div selectionCriteriaDiv = new Div();
    private final IFasScenariosController controller;

    /**
     * Controller.
     *
     * @param fasScenariosController instance of {@link IFasScenariosController}
     * @param historyController      instance of {@link IScenarioHistoryController}
     */
    FasScenariosWidget(IFasScenariosController fasScenariosController, IScenarioHistoryController historyController) {
        super(historyController);
        controller = fasScenariosController;
    }

    @Override
    protected HorizontalLayout initButtonsLayout() {
        var buttonsLayout = new HorizontalLayout(viewButton, editNameButton, deleteButton, excludePayeesButton,
            reconcileRightsholdersButton, submitButton, rejectButton, approveButton, sendToLmButton,
            refreshScenarioButton);
        addButtonsListeners();
        buttonsLayout.setMargin(false);
        VaadinUtils.addComponentStyle(buttonsLayout, "scenarios-buttons");
        return buttonsLayout;
    }

    @Override
    protected VerticalLayout initMetadataLayout() {
        var metadataLayout = new VerticalLayout(ownerDiv, grossTotalDiv, serviceFeeTotalDiv, netTotalDiv,
            descriptionDiv, selectionCriteriaDiv);
        VaadinUtils.setMaxComponentsWidth(ownerDiv, grossTotalDiv, serviceFeeTotalDiv, netTotalDiv, descriptionDiv,
            selectionCriteriaDiv);
        metadataLayout.setMargin(false);
        VaadinUtils.setPadding(metadataLayout, 10, 10, 10, 10);
        return metadataLayout;
    }

    @Override
    protected void updateScenarioMetadata(Scenario scenarioWithAmounts) {
        updateDivContent(ownerDiv, ForeignUi.getMessage("label.owner", scenarioWithAmounts.getCreateUser()));
        updateDivContent(netTotalDiv, ForeignUi.getMessage("label.net_amount_in_usd",
            formatAmount(scenarioWithAmounts.getNetTotal())));
        updateDivContent(grossTotalDiv, ForeignUi.getMessage("label.gross_amount_in_usd",
            formatAmount(scenarioWithAmounts.getGrossTotal())));
        updateDivContent(serviceFeeTotalDiv, ForeignUi.getMessage("label.service_fee_amount_in_usd",
            formatAmount(scenarioWithAmounts.getServiceFeeTotal())));
        updateDivContent(descriptionDiv,
            ForeignUi.getMessage("label.description", scenarioWithAmounts.getDescription()));
        updateDivContent(selectionCriteriaDiv, getController().getCriteriaHtmlRepresentation());
    }

    private void addButtonsListeners() {
        //TODO: implement
        excludePayeesButton.addClickListener(event -> controller.onExcludePayeesButtonClicked());
    }
}
