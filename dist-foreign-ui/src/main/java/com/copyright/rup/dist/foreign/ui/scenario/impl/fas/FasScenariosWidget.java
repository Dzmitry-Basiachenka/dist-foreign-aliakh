package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosMediator;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasScenariosWidget;
import com.copyright.rup.dist.foreign.ui.scenario.impl.CommonScenariosWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;

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
    private final Label ownerLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label netTotalLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label grossTotalLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label serviceFeeTotalLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label descriptionLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label selectionCriteriaLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final IFasScenariosController controller;
    private FasScenariosMediator mediator;

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
    public IMediator initMediator() {
        mediator = new FasScenariosMediator();
        mediator.setViewButton(viewButton);
        mediator.setEditNameButton(editNameButton);
        mediator.setDeleteButton(deleteButton);
        mediator.setApproveButton(approveButton);
        mediator.setRejectButton(rejectButton);
        mediator.setSubmitButton(submitButton);
        mediator.setSendToLmButton(sendToLmButton);
        mediator.setExcludePayeesButton(excludePayeesButton);
        mediator.setReconcileRightsholdersButton(reconcileRightsholdersButton);
        mediator.setRefreshScenarioButton(refreshScenarioButton);
        mediator.selectedScenarioChanged(getSelectedScenario());
        return mediator;
    }

    @Override
    protected HorizontalLayout initButtonsLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        addButtonsListeners();
        VaadinUtils.setButtonsAutoDisabled(viewButton, editNameButton, deleteButton, excludePayeesButton,
            reconcileRightsholdersButton, submitButton, rejectButton, approveButton, sendToLmButton,
            refreshScenarioButton);
        layout.addComponents(viewButton, editNameButton, deleteButton, excludePayeesButton,
            reconcileRightsholdersButton, submitButton, rejectButton, approveButton, sendToLmButton,
            refreshScenarioButton);
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(layout, "scenarios-buttons");
        return layout;
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

    @Override
    protected VerticalLayout initMetadataLayout() {
        descriptionLabel.setStyleName("v-label-white-space-normal");
        selectionCriteriaLabel.setStyleName("v-label-white-space-normal");
        VerticalLayout metadataLayout =
            new VerticalLayout(ownerLabel, grossTotalLabel, serviceFeeTotalLabel, netTotalLabel, descriptionLabel,
                selectionCriteriaLabel);
        metadataLayout.setMargin(new MarginInfo(false, true, false, true));
        VaadinUtils.setMaxComponentsWidth(metadataLayout);
        return metadataLayout;
    }

    @Override
    protected void updateScenarioMetadata(Scenario scenarioWithAmounts) {
        ownerLabel.setValue(ForeignUi.getMessage("label.owner", scenarioWithAmounts.getCreateUser()));
        netTotalLabel.setValue(ForeignUi.getMessage("label.net_amount_in_usd",
            formatAmount(scenarioWithAmounts.getNetTotal())));
        grossTotalLabel.setValue(ForeignUi.getMessage("label.gross_amount_in_usd",
            formatAmount(scenarioWithAmounts.getGrossTotal())));
        serviceFeeTotalLabel.setValue(ForeignUi.getMessage("label.service_fee_amount_in_usd",
            formatAmount(scenarioWithAmounts.getServiceFeeTotal())));
        descriptionLabel.setValue(ForeignUi.getMessage("label.description", scenarioWithAmounts.getDescription()));
        selectionCriteriaLabel.setValue(getController().getCriteriaHtmlRepresentation());
    }

    @Override
    protected IScenariosMediator getMediator() {
        return mediator;
    }
}
