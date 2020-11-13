package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosMediator;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenariosWidget;
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
 * Implementation of {@link ISalScenariosWidget}.
 * <p>
 * Copyright (C) 20120 copyright.com
 * <p>
 * Date: 09/22/2020
 *
 * @author Aliaksandr Liakh
 */
public class SalScenariosWidget extends CommonScenariosWidget implements ISalScenariosWidget {

    private final ISalScenariosController controller;
    private SalScenariosMediator mediator;
    private final Button viewButton = Buttons.createButton(ForeignUi.getMessage("button.view"));
    private final Button deleteButton = Buttons.createButton(ForeignUi.getMessage("button.delete"));
    private final Button submitButton = Buttons.createButton(ForeignUi.getMessage("button.submit"));
    private final Button rejectButton = Buttons.createButton(ForeignUi.getMessage("button.reject"));
    private final Button approveButton = Buttons.createButton(ForeignUi.getMessage("button.approve"));
    private final Button chooseScenariosButton = Buttons.createButton(ForeignUi.getMessage("button.choose_scenarios"));
    private final Label ownerLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label netTotalLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label grossTotalLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label serviceFeeTotalLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label descriptionLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label fundPoolNameLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label selectionCriteriaLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);

    /**
     * Controller.
     *
     * @param controller        instance of {@link ISalScenariosController}
     * @param historyController instance of {@link IScenarioHistoryController}
     */
    SalScenariosWidget(ISalScenariosController controller, IScenarioHistoryController historyController) {
        super(historyController);
        setController(controller);
        this.controller = controller;
    }

    @Override
    public IMediator initMediator() {
        mediator = new SalScenariosMediator();
        mediator.setViewButton(viewButton);
        mediator.setDeleteButton(deleteButton);
        mediator.setApproveButton(approveButton);
        mediator.setRejectButton(rejectButton);
        mediator.setSubmitButton(submitButton);
        mediator.setChooseScenariosButton(chooseScenariosButton);
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
        VaadinUtils.setButtonsAutoDisabled(viewButton, deleteButton, submitButton, rejectButton, approveButton,
            chooseScenariosButton);
        layout.addComponents(viewButton, deleteButton, submitButton, rejectButton, approveButton,
            chooseScenariosButton);
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(layout, "scenarios-buttons");
        return layout;
    }

    @Override
    protected VerticalLayout initMetadataLayout() {
        descriptionLabel.setStyleName("v-label-white-space-normal");
        selectionCriteriaLabel.setStyleName("v-label-white-space-normal");
        VerticalLayout metadataLayout = new VerticalLayout(ownerLabel, grossTotalLabel, serviceFeeTotalLabel,
            netTotalLabel, descriptionLabel, selectionCriteriaLabel, fundPoolNameLabel);
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
        selectionCriteriaLabel.setValue(controller.getCriteriaHtmlRepresentation());
        fundPoolNameLabel.setValue(ForeignUi.getMessage("label.metadata.fund_pool_name",
            controller.getFundPoolName(scenarioWithAmounts.getSalFields().getFundPoolId())));
    }

    private void addButtonsListeners() {
        viewButton.addClickListener(event -> controller.onViewButtonClicked());
        deleteButton.addClickListener(event -> controller.onDeleteButtonClicked());
        submitButton.addClickListener(event -> controller.handleAction(ScenarioActionTypeEnum.SUBMITTED));
        rejectButton.addClickListener(event -> controller.handleAction(ScenarioActionTypeEnum.REJECTED));
        approveButton.addClickListener(event -> controller.handleAction(ScenarioActionTypeEnum.APPROVED));
        chooseScenariosButton.addClickListener(event -> controller.onSendToLmButtonClicked());
    }
}
