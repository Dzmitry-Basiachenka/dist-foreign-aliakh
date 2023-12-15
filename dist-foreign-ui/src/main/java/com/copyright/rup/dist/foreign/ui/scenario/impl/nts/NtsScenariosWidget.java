package com.copyright.rup.dist.foreign.ui.scenario.impl.nts;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.NtsFields;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosMediator;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsScenariosWidget;
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

import java.util.Objects;

/**
 * Implementation of {@link INtsScenariosWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/11/19
 *
 * @author Stanislau Rudak
 */
public class NtsScenariosWidget extends CommonScenariosWidget implements INtsScenariosWidget {

    private static final long serialVersionUID = -1899625559383613044L;

    private final Button viewButton = Buttons.createButton(ForeignUi.getMessage("button.view"));
    private final Button editNameButton = Buttons.createButton(ForeignUi.getMessage("button.edit_name"));
    private final Button deleteButton = Buttons.createButton(ForeignUi.getMessage("button.delete"));
    private final Button submitButton = Buttons.createButton(ForeignUi.getMessage("button.submit"));
    private final Button rejectButton = Buttons.createButton(ForeignUi.getMessage("button.reject"));
    private final Button approveButton = Buttons.createButton(ForeignUi.getMessage("button.approve"));
    private final Button sendToLmButton = Buttons.createButton(ForeignUi.getMessage("button.send_to_lm"));
    private final Label ownerLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label netTotalLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label grossTotalLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label serviceFeeTotalLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label rhMinimumAmountLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label preServiceFeeAmountLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label postServiceFeeAmountLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label preServiceFeeFundLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label descriptionLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label selectionCriteriaLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
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
        HorizontalLayout layout = new HorizontalLayout();
        addButtonsListeners();
        VaadinUtils.setButtonsAutoDisabled(viewButton, editNameButton, deleteButton, submitButton, rejectButton,
            approveButton, sendToLmButton);
        layout.addComponents(viewButton, editNameButton, deleteButton, submitButton, rejectButton, approveButton,
            sendToLmButton);
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(layout, "scenarios-buttons");
        return layout;
    }

    @Override
    protected VerticalLayout initMetadataLayout() {
        descriptionLabel.setStyleName("v-label-white-space-normal");
        selectionCriteriaLabel.setStyleName("v-label-white-space-normal");
        VerticalLayout metadataLayout =
            new VerticalLayout(ownerLabel, grossTotalLabel, serviceFeeTotalLabel, netTotalLabel,
                rhMinimumAmountLabel, preServiceFeeAmountLabel, postServiceFeeAmountLabel, preServiceFeeFundLabel,
                descriptionLabel, selectionCriteriaLabel);
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
        NtsFields ntsFields = scenarioWithAmounts.getNtsFields();
        rhMinimumAmountLabel.setValue(ForeignUi.getMessage("label.rh_minimum_amount_in_usd",
            formatAmount(ntsFields.getRhMinimumAmount())));
        preServiceFeeAmountLabel.setValue(ForeignUi.getMessage("label.pre_service_fee_amount",
            formatAmount(ntsFields.getPreServiceFeeAmount())));
        postServiceFeeAmountLabel.setValue(ForeignUi.getMessage("label.post_service_fee_amount",
            formatAmount(ntsFields.getPostServiceFeeAmount())));
        if (Objects.nonNull(ntsFields.getPreServiceFeeFundName())) {
            preServiceFeeFundLabel.setValue(ForeignUi.getMessage("label.pre_service_fee_fund",
                ntsFields.getPreServiceFeeFundName(), formatAmount(ntsFields.getPreServiceFeeFundTotal())));
        } else {
            preServiceFeeFundLabel.setValue(StringUtils.EMPTY);
        }
        descriptionLabel.setValue(ForeignUi.getMessage("label.description", scenarioWithAmounts.getDescription()));
        selectionCriteriaLabel.setValue(controller.getCriteriaHtmlRepresentation());
    }

    @Override
    protected IScenariosMediator getMediator() {
        return mediator;
    }

    private void addButtonsListeners() {
        viewButton.addClickListener(event -> controller.onViewButtonClicked());
        editNameButton.addClickListener(event -> onEditNameButtonClicked());
        deleteButton.addClickListener(event -> controller.onDeleteButtonClicked());
        submitButton.addClickListener(event -> controller.handleAction(ScenarioActionTypeEnum.SUBMITTED));
        rejectButton.addClickListener(event -> controller.handleAction(ScenarioActionTypeEnum.REJECTED));
        approveButton.addClickListener(event -> controller.handleAction(ScenarioActionTypeEnum.APPROVED));
        sendToLmButton.addClickListener(event -> controller.sendToLm());
    }
}
