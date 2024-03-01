package com.copyright.rup.dist.foreign.vui.scenario.impl.nts;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.vui.scenario.api.IScenariosMediator;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsScenariosController;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsScenariosWidget;
import com.copyright.rup.dist.foreign.vui.scenario.impl.CommonScenariosWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IMediator;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.Objects;

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

    private final Button viewButton = Buttons.createButton(ForeignUi.getMessage("button.view"));
    private final Button editNameButton = Buttons.createButton(ForeignUi.getMessage("button.edit_name"));
    private final Button deleteButton = Buttons.createButton(ForeignUi.getMessage("button.delete"));
    private final Button submitButton = Buttons.createButton(ForeignUi.getMessage("button.submit"));
    private final Button rejectButton = Buttons.createButton(ForeignUi.getMessage("button.reject"));
    private final Button approveButton = Buttons.createButton(ForeignUi.getMessage("button.approve"));
    private final Button sendToLmButton = Buttons.createButton(ForeignUi.getMessage("button.send_to_lm"));
    private final Div ownerDiv = new Div();
    private final Div netTotalDiv = new Div();
    private final Div grossTotalDiv = new Div();
    private final Div serviceFeeTotalDiv = new Div();
    private final Div rhMinimumAmountDiv = new Div();
    private final Div preServiceFeeAmountDiv = new Div();
    private final Div postServiceFeeAmountDiv = new Div();
    private final Div preServiceFeeFundDiv = new Div();
    private final Div descriptionDiv = new Div();
    private final Div selectionCriteriaDiv = new Div();
    private final INtsScenariosController controller;
    private NtsScenariosMediator mediator;

    /**
     * Controller.
     *
     * @param controller        instance of {@link INtsScenariosController}
     * @param historyController instance of {@link IScenarioHistoryController}
     */
    NtsScenariosWidget(INtsScenariosController controller, IScenarioHistoryController historyController) {
        super(historyController);
        super.setController(controller);
        this.controller = controller;
    }

    @Override
    public IMediator initMediator() {
        mediator = new NtsScenariosMediator();
        mediator.setViewButton(viewButton);
        mediator.setEditNameButton(editNameButton);
        mediator.setDeleteButton(deleteButton);
        mediator.setApproveButton(approveButton);
        mediator.setRejectButton(rejectButton);
        mediator.setSubmitButton(submitButton);
        mediator.setSendToLmButton(sendToLmButton);
        mediator.selectedScenarioChanged(getSelectedScenario());
        return mediator;
    }

    @Override
    protected HorizontalLayout initButtonsLayout() {
        var layout = new HorizontalLayout(viewButton, editNameButton, deleteButton, submitButton, rejectButton,
            approveButton, sendToLmButton);
        addButtonsListeners();
        layout.setMargin(false);
        VaadinUtils.addComponentStyle(layout, "scenarios-buttons");
        return layout;
    }

    @Override
    protected VerticalLayout initMetadataLayout() {
        var metadataLayout = new VerticalLayout(ownerDiv, grossTotalDiv, serviceFeeTotalDiv, netTotalDiv,
            rhMinimumAmountDiv, preServiceFeeAmountDiv, postServiceFeeAmountDiv, preServiceFeeFundDiv, descriptionDiv,
            selectionCriteriaDiv);
        VaadinUtils.setFullComponentsWidth(ownerDiv, grossTotalDiv, serviceFeeTotalDiv, netTotalDiv,
            rhMinimumAmountDiv, preServiceFeeAmountDiv, postServiceFeeAmountDiv, preServiceFeeFundDiv, descriptionDiv,
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
        var ntsFields = scenarioWithAmounts.getNtsFields();
        updateDivContent(rhMinimumAmountDiv, ForeignUi.getMessage("label.rh_minimum_amount_in_usd",
            formatAmount(ntsFields.getRhMinimumAmount())));
        updateDivContent(preServiceFeeAmountDiv, ForeignUi.getMessage("label.pre_service_fee_amount",
            formatAmount(ntsFields.getPreServiceFeeAmount())));
        updateDivContent(postServiceFeeAmountDiv, ForeignUi.getMessage("label.post_service_fee_amount",
            formatAmount(ntsFields.getPostServiceFeeAmount())));
        if (Objects.nonNull(ntsFields.getPreServiceFeeFundName())) {
            updateDivContent(preServiceFeeFundDiv, ForeignUi.getMessage("label.pre_service_fee_fund",
                ntsFields.getPreServiceFeeFundName(), formatAmount(ntsFields.getPreServiceFeeFundTotal())));
        } else {
            preServiceFeeFundDiv.getElement().removeProperty("innerHTML");
        }
        updateDivContent(
            descriptionDiv, ForeignUi.getMessage("label.description", scenarioWithAmounts.getDescription()));
        updateDivContent(selectionCriteriaDiv, controller.getCriteriaHtmlRepresentation());
    }

    @Override
    protected IScenariosMediator getMediator() {
        return mediator;
    }

    private void addButtonsListeners() {
        viewButton.addClickListener(event -> controller.onViewButtonClicked());
        editNameButton.addClickListener(event -> controller.onEditNameButtonClicked());
    }
}
