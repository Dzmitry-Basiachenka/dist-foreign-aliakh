package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosMediator;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclScenariosWidget;
import com.copyright.rup.dist.foreign.ui.scenario.impl.CommonScenariosWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.ui.usage.impl.aacl.AaclScenarioParameterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.aacl.AggregateLicenseeClassMappingWindow;
import com.copyright.rup.dist.foreign.ui.usage.impl.aacl.PublicationTypeWeightsWindow;
import com.copyright.rup.dist.foreign.ui.usage.impl.aacl.ViewAaclFundPoolDetailsWindow;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private final IAaclUsageController usageController;
    private final Button viewButton = Buttons.createButton(ForeignUi.getMessage("button.view"));
    private final Button deleteButton = Buttons.createButton(ForeignUi.getMessage("button.delete"));
    private final Label ownerLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label netTotalLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label cutoffAmt = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label grossTotalLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label serviceFeeTotalLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label descriptionLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label selectionCriteriaLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private AaclScenariosMediator mediator;
    private FundPool fundPool;
    private List<FundPoolDetail> fundPoolDetails;
    private AaclScenarioParameterWidget<List<DetailLicenseeClass>> licenseeClassMappingWidget;
    private AaclScenarioParameterWidget<List<PublicationType>> publicationTypeWeightWidget;

    /**
     * Controller.
     *
     * @param controller        instance of {@link IAaclScenariosController}
     * @param historyController instance of {@link IScenarioHistoryController}
     * @param usageController   instance of {@link IAaclUsageController}
     */
    AaclScenariosWidget(IAaclScenariosController controller, IScenarioHistoryController historyController,
                        IAaclUsageController usageController) {
        super(historyController);
        setController(controller);
        this.controller = controller;
        this.usageController = usageController;
    }

    @Override
    public IMediator initMediator() {
        mediator = new AaclScenariosMediator();
        mediator.setViewButton(viewButton);
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
        VaadinUtils.setButtonsAutoDisabled(viewButton, deleteButton);
        layout.addComponents(viewButton, deleteButton);
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(layout, "scenarios-buttons");
        return layout;
    }

    @Override
    protected VerticalLayout initMetadataLayout() {
        Button fundPoolButton = Buttons.createButton(ForeignUi.getMessage("label.fund_pool"));
        fundPoolButton.addStyleName(ValoTheme.BUTTON_LINK);
        VaadinUtils.setButtonsAutoDisabled(fundPoolButton);
        fundPoolButton.addClickListener(event ->
            Windows.showModalWindow(new ViewAaclFundPoolDetailsWindow(fundPool, fundPoolDetails)));
        licenseeClassMappingWidget = new AaclScenarioParameterWidget<>(
            ForeignUi.getMessage("button.licensee_class_mapping"),
            Collections::emptyList, () -> new AggregateLicenseeClassMappingWindow(false));
        publicationTypeWeightWidget = new AaclScenarioParameterWidget<>(
            ForeignUi.getMessage("button.publication_type_weights"),
            usageController::getPublicationTypes, () -> new PublicationTypeWeightsWindow(false));
        descriptionLabel.setStyleName("v-label-white-space-normal");
        VerticalLayout metadataLayout =
            new VerticalLayout(ownerLabel, grossTotalLabel, serviceFeeTotalLabel, netTotalLabel,
                cutoffAmt, descriptionLabel, selectionCriteriaLabel, fundPoolButton, licenseeClassMappingWidget,
                publicationTypeWeightWidget);
        metadataLayout.setMargin(new MarginInfo(false, true, false, true));
        VaadinUtils.setMaxComponentsWidth(metadataLayout);
        return metadataLayout;
    }

    @Override
    protected void updateScenarioMetadata(Scenario scenarioWithAmounts) {
        String fundPoolId = scenarioWithAmounts.getAaclFields().getFundPoolId();
        fundPool = usageController.getFundPoolById(fundPoolId);
        fundPoolDetails = usageController.getFundPoolDetails(fundPoolId);
        ownerLabel.setValue(ForeignUi.getMessage("label.owner", scenarioWithAmounts.getCreateUser()));
        grossTotalLabel.setValue(ForeignUi.getMessage("label.gross_amount_in_usd",
            formatAmount(scenarioWithAmounts.getGrossTotal())));
        serviceFeeTotalLabel.setValue(ForeignUi.getMessage("label.service_fee_amount_in_usd",
            formatAmount(scenarioWithAmounts.getServiceFeeTotal())));
        netTotalLabel.setValue(ForeignUi.getMessage("label.net_amount_in_usd",
            formatAmount(scenarioWithAmounts.getNetTotal())));
        cutoffAmt.setValue(ForeignUi.getMessage("label.cutoff_amt_in_usd",
            formatAmount(scenarioWithAmounts.getAaclFields().getTitleCutoffAmount())));
        descriptionLabel.setValue(ForeignUi.getMessage("label.description", scenarioWithAmounts.getDescription()));
        selectionCriteriaLabel.setValue(getController().getCriteriaHtmlRepresentation());
        licenseeClassMappingWidget.setAppliedParameters(
            controller.getDetailLicenseeClassesByScenarioId(scenarioWithAmounts.getId()));
        updatePublicationTypeWeightWidget(scenarioWithAmounts.getAaclFields());
    }

    private void updatePublicationTypeWeightWidget(AaclFields aaclFields) {
        Map<String, String> idsToPublicationTypeNames = usageController.getPublicationTypes()
            .stream()
            .collect(Collectors.toMap(PublicationType::getId, PublicationType::getName));
        publicationTypeWeightWidget.setAppliedParameters(aaclFields.getPublicationTypes()
            .stream()
            .peek(pubType -> pubType.setName(idsToPublicationTypeNames.get(pubType.getId())))
            .collect(Collectors.toList()));
    }

    private void addButtonsListeners() {
        viewButton.addClickListener(event -> getController().onViewButtonClicked());
        deleteButton.addClickListener(event -> getController().onDeleteButtonClicked());
    }
}
