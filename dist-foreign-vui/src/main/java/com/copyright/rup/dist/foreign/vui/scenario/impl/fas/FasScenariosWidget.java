package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.vui.scenario.api.IScenariosMediator;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenariosController;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenariosWidget;
import com.copyright.rup.dist.foreign.vui.scenario.impl.CommonScenariosWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IMediator;

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

    private static final long serialVersionUID = 741390963520460309L;

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
    private FasScenariosMediator mediator;

    /**
     * Constructor.
     *
     * @param fasScenariosController instance of {@link IFasScenariosController}
     * @param historyController      instance of {@link IScenarioHistoryController}
     */
    FasScenariosWidget(IFasScenariosController fasScenariosController, IScenarioHistoryController historyController) {
        super(historyController);
        controller = fasScenariosController;
    }

    @Override
    public IMediator initMediator() {
        mediator = new FasScenariosMediator();
        mediator.setViewButton(viewButton);
        mediator.setEditNameButton(editNameButton);
        mediator.setDeleteButton(deleteButton);
        mediator.setExcludePayeesButton(excludePayeesButton);
        mediator.setReconcileRightsholdersButton(reconcileRightsholdersButton);
        mediator.setSubmitButton(submitButton);
        mediator.setRejectButton(rejectButton);
        mediator.setApproveButton(approveButton);
        mediator.setSendToLmButton(sendToLmButton);
        mediator.setRefreshScenarioButton(refreshScenarioButton);
        mediator.selectedScenarioChanged(getSelectedScenario());
        return mediator;
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
        VaadinUtils.setFullComponentsWidth(ownerDiv, grossTotalDiv, serviceFeeTotalDiv, netTotalDiv, descriptionDiv,
            selectionCriteriaDiv);
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

    @Override
    protected IScenariosMediator getMediator() {
        return mediator;
    }

    private void addButtonsListeners() {
        viewButton.addClickListener(event -> controller.onViewButtonClicked());
        editNameButton.addClickListener(event -> controller.onEditNameButtonClicked());
        deleteButton.addClickListener(event -> controller.onDeleteButtonClicked());
        excludePayeesButton.addClickListener(event -> controller.onExcludePayeesButtonClicked());
        reconcileRightsholdersButton.addClickListener(event -> controller.onReconcileRightsholdersButtonClicked());
        submitButton.addClickListener(event -> controller.handleAction(ScenarioActionTypeEnum.SUBMITTED));
        rejectButton.addClickListener(event -> controller.handleAction(ScenarioActionTypeEnum.REJECTED));
        approveButton.addClickListener(event -> controller.handleAction(ScenarioActionTypeEnum.APPROVED));
        sendToLmButton.addClickListener(event -> controller.sendToLm());
        refreshScenarioButton.addClickListener(event -> controller.onRefreshScenarioButtonClicked());
    }
}
