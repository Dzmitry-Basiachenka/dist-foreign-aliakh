package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.report.AclCalculationReportsInfoDto;
import com.copyright.rup.dist.foreign.ui.common.utils.IDateFormatter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioHistoryWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.AclAggregateLicenseeClassMappingViewWindow;
import com.copyright.rup.dist.foreign.ui.usage.impl.AclUsageAgeWeightWindow;
import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget.IParametersSaveListener;
import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget.ParametersSaveEvent;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.AclPublicationTypeWeightsParameterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.AclPublicationTypeWeightsWindow;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link IAclScenariosWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/22/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclScenariosWidget extends VerticalLayout implements IAclScenariosWidget, IDateFormatter {

    private final Label ownerLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label grossTotalPrintLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label grossTotalDigitalLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label numberOfRhsPrintLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label numberOfRhsDigitalLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label numberOfWorksPrintLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label numberOfWorksDigitalLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final VerticalLayout grossTotalLayout = initGrossTotalLayout();
    private final Label serviceFeeTotalPrintLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label serviceFeeTotalDigitalLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final VerticalLayout serviceFeeTotalLayout = initServiceFeeTotalLayout();
    private final Label netTotalPrintLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label netTotalDigitalLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final VerticalLayout netTotalLayout = initNetTotalLayout();
    private final Label descriptionLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label selectionCriteriaLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label copiedFromLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label actionType = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label actionCreatedUser = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label actionCreatedDate = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label actionReason = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final IAclScenarioHistoryController aclScenarioHistoryController;
    private final Button createButton = Buttons.createButton(ForeignUi.getMessage("button.create"));
    private final Button deleteButton = Buttons.createButton(ForeignUi.getMessage("button.delete"));
    private final Button viewButton = Buttons.createButton(ForeignUi.getMessage("button.view"));
    private final Button pubTypeWeights = Buttons.createButton(ForeignUi.getMessage("button.publication_type_weights"));
    private final Button submitButton = Buttons.createButton(ForeignUi.getMessage("button.submit"));
    private final Button rejectButton = Buttons.createButton(ForeignUi.getMessage("button.reject"));
    private final Button approveButton = Buttons.createButton(ForeignUi.getMessage("button.approve"));
    private final Button sendToLmButton = Buttons.createButton(ForeignUi.getMessage("button.send_to_lm"));
    private final String userName = RupContextUtils.getUserName();

    private IAclScenariosController aclScenariosController;
    private HorizontalSplitPanel splitPanel;
    private Grid<AclScenario> scenarioGrid;
    private Panel mainPanel;
    private VerticalLayout mainLayout;
    private ListDataProvider<AclScenario> dataProvider;
    private ScenarioParameterWidget<List<UsageAge>> usageAgeWeightWidget;
    private AclPublicationTypeWeightsParameterWidget publicationTypeWeightWidget;
    private ScenarioParameterWidget<List<DetailLicenseeClass>> licenseeClassMappingWidget;
    private AclScenariosMediator mediator;

    /**
     * Constructor.
     *
     * @param aclScenariosController       instance of {@link IAclScenariosController}
     * @param aclScenarioHistoryController instance of {@link IAclScenarioHistoryController}
     */
    public AclScenariosWidget(IAclScenariosController aclScenariosController,
                              IAclScenarioHistoryController aclScenarioHistoryController) {
        this.aclScenariosController = aclScenariosController;
        this.aclScenarioHistoryController = aclScenarioHistoryController;
    }

    @Override
    public void refresh() {
        List<AclScenario> scenarios = aclScenariosController.getScenarios();
        dataProvider = DataProvider.ofCollection(scenarios);
        scenarioGrid.setDataProvider(dataProvider);
        selectFirstScenario(scenarios);
    }

    @Override
    @SuppressWarnings("unchecked")
    public IAclScenariosWidget init() {
        setSizeFull();
        initSplitPanel();
        initMainPanel();
        HorizontalLayout horizontalLayout = new HorizontalLayout(splitPanel, mainPanel);
        horizontalLayout.setSizeFull();
        horizontalLayout.setExpandRatio(splitPanel, 0.7f);
        horizontalLayout.setExpandRatio(mainPanel, 0.3f);
        addComponents(horizontalLayout);
        setExpandRatio(horizontalLayout, 1);
        setSpacing(false);
        setMargin(false);
        return this;
    }

    @Override
    public IMediator initMediator() {
        mediator = new AclScenariosMediator();
        mediator.setCreateButton(createButton);
        mediator.setViewButton(viewButton);
        mediator.setDeleteButton(deleteButton);
        mediator.setPubTypeWeights(pubTypeWeights);
        mediator.setApproveButton(approveButton);
        mediator.setRejectButton(rejectButton);
        mediator.setSubmitButton(submitButton);
        mediator.setSentToLmButton(sendToLmButton);
        mediator.selectedScenarioChanged(getSelectedScenario());
        return mediator;
    }

    @Override
    public void setController(IAclScenariosController controller) {
        this.aclScenariosController = controller;
    }

    @Override
    public void selectScenario(AclScenario scenario) {
        scenarioGrid.select(scenario);
    }

    @Override
    public AclScenario getSelectedScenario() {
        return scenarioGrid.getSelectedItems().stream().findFirst().orElse(null);
    }

    @Override
    public void refreshSelectedScenario() {
        onItemChanged(getSelectedScenario());
    }

    @Override
    public AclCalculationReportsInfoDto getReportInfo() {
        AclCalculationReportsInfoDto reportInfo = new AclCalculationReportsInfoDto();
        reportInfo.setScenarios(List.of(getSelectedScenario()));
        reportInfo.setUser(userName);
        reportInfo.setReportDateTime(OffsetDateTime.now());
        return reportInfo;
    }

    private void initSplitPanel() {
        splitPanel = new HorizontalSplitPanel();
        splitPanel.addComponents(aclScenariosController.initAclScenariosFilterWidget(), initMainLayout());
        splitPanel.setSplitPosition(180, Unit.PIXELS);
        splitPanel.setLocked(true);
    }

    private VerticalLayout initMainLayout() {
        VerticalLayout layout = new VerticalLayout(initButtonsLayout(), initGrid());
        layout.setSizeFull();
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.setExpandRatio(scenarioGrid, 1);
        VaadinUtils.addComponentStyle(layout, "acl-scenarios-layout");
        return layout;
    }

    private Grid<AclScenario> initGrid() {
        List<AclScenario> scenarios = aclScenariosController.getScenarios();
        dataProvider = DataProvider.ofCollection(scenarios);
        scenarioGrid = new Grid<>(dataProvider);
        addColumns();
        scenarioGrid.setSizeFull();
        scenarioGrid.getColumns().forEach(column -> column.setSortable(true));
        scenarioGrid.addSelectionListener(event -> onItemChanged(event.getFirstSelectedItem().orElse(null)));
        VaadinUtils.addComponentStyle(scenarioGrid, "acl-scenarios-table");
        return scenarioGrid;
    }

    private HorizontalLayout initButtonsLayout() {
        addButtonsListeners();
        HorizontalLayout buttonsLayout =
            new HorizontalLayout(createButton, viewButton, deleteButton, pubTypeWeights, submitButton, rejectButton,
                approveButton, sendToLmButton);
        VaadinUtils.setButtonsAutoDisabled(createButton, viewButton, deleteButton, pubTypeWeights, submitButton,
            rejectButton, approveButton, sendToLmButton);
        buttonsLayout.setMargin(true);
        VaadinUtils.addComponentStyle(buttonsLayout, "acl-scenario-buttons-layout");
        return buttonsLayout;
    }

    private void addButtonsListeners() {
        createButton.addClickListener(event ->
            Windows.showModalWindow(new CreateAclScenarioWindow(aclScenariosController, createEvent -> refresh())));
        viewButton.addClickListener(event -> onClickViewButton());
        deleteButton.addClickListener(event -> aclScenariosController.onDeleteButtonClicked());
        pubTypeWeights.addClickListener(event -> {
            AclPublicationTypeWeightsWindow window = new AclPublicationTypeWeightsWindow(aclScenariosController, true);
            window.setAppliedParameters(aclScenariosController.getAclHistoricalPublicationTypes());
            window.addListener(ParametersSaveEvent.class,
                (IParametersSaveListener<List<AclPublicationType>>)
                    saveEvent -> insertAclPubTypes(saveEvent.getSavedParameters()),
                IParametersSaveListener.SAVE_HANDLER);
            Windows.showModalWindow(window);
        });
        submitButton.addClickListener(event -> aclScenariosController.handleAction(ScenarioActionTypeEnum.SUBMITTED));
        rejectButton.addClickListener(event -> aclScenariosController.handleAction(ScenarioActionTypeEnum.REJECTED));
        approveButton.addClickListener(event -> {
            if (aclScenariosController.canUserApproveAclScenario(getSelectedScenario().getId())) {
                aclScenariosController.handleAction(ScenarioActionTypeEnum.APPROVED);
            } else {
                Windows.showNotificationWindow(ForeignUi.getMessage("message.error.invalid_acl_scenario_approver"));
            }
        });
        sendToLmButton.addClickListener(event -> aclScenariosController.sendToLm());
    }

    private void insertAclPubTypes(List<AclPublicationType> publicationTypes) {
        publicationTypes.removeAll(aclScenariosController.getAclHistoricalPublicationTypes());
        publicationTypes.forEach(
            publicationType -> aclScenariosController.insertAclHistoricalPublicationType(publicationType));
    }

    private void onClickViewButton() {
        IAclScenarioController aclScenarioController = aclScenariosController.getAclScenarioController();
        aclScenarioController.setScenario(getSelectedScenario());
        IAclScenarioWidget aclScenarioWidget = aclScenarioController.initWidget();
        Window aclScenarioWindow = (Window) aclScenarioWidget;
        Windows.showModalWindow(aclScenarioWindow);
        aclScenarioWindow.setPositionY(30);
    }

    private void addColumns() {
        scenarioGrid.addColumn(AclScenario::getName)
            .setCaption(ForeignUi.getMessage("table.column.name"))
            .setComparator((scenario1, scenario2) -> scenario1.getName().compareToIgnoreCase(scenario2.getName()))
            .setExpandRatio(1);
        scenarioGrid.addColumn(AclScenario::getLicenseType)
            .setCaption(ForeignUi.getMessage("table.column.license_type"))
            .setWidth(100);
        scenarioGrid.addColumn(AclScenario::getPeriodEndDate)
            .setCaption(ForeignUi.getMessage("table.column.period"))
            .setWidth(70);
        scenarioGrid.addColumn(scenario -> scenario.isEditableFlag() ? "Y" : "N")
            .setCaption(ForeignUi.getMessage("table.column.editable"))
            .setWidth(90);
        scenarioGrid.addColumn(scenario -> scenario.getStatus().name())
            .setCaption(ForeignUi.getMessage("table.column.status"))
            .setWidth(150);
    }

    private VerticalLayout initGrossTotalLayout() {
        VerticalLayout layout = new VerticalLayout(grossTotalPrintLabel, grossTotalDigitalLabel, numberOfRhsPrintLabel,
            numberOfRhsDigitalLabel, numberOfWorksPrintLabel, numberOfWorksDigitalLabel);
        VaadinUtils.addComponentStyle(layout, "scenario-detailed-amount");
        layout.setCaptionAsHtml(true);
        layout.setSpacing(false);
        layout.setMargin(false);
        VaadinUtils.setMaxComponentsWidth(layout, grossTotalPrintLabel, grossTotalDigitalLabel, numberOfRhsPrintLabel,
            numberOfRhsDigitalLabel, numberOfWorksPrintLabel, numberOfWorksDigitalLabel);
        return layout;
    }

    private VerticalLayout initServiceFeeTotalLayout() {
        VerticalLayout layout = new VerticalLayout(serviceFeeTotalPrintLabel, serviceFeeTotalDigitalLabel);
        VaadinUtils.addComponentStyle(layout, "scenario-detailed-amount");
        layout.setCaptionAsHtml(true);
        layout.setSpacing(false);
        layout.setMargin(false);
        VaadinUtils.setMaxComponentsWidth(layout, serviceFeeTotalPrintLabel, serviceFeeTotalDigitalLabel);
        return layout;
    }

    private VerticalLayout initNetTotalLayout() {
        VerticalLayout layout = new VerticalLayout(netTotalPrintLabel, netTotalDigitalLabel);
        VaadinUtils.addComponentStyle(layout, "scenario-detailed-amount");
        layout.setCaptionAsHtml(true);
        layout.setSpacing(false);
        layout.setMargin(false);
        VaadinUtils.setMaxComponentsWidth(layout, netTotalPrintLabel, netTotalDigitalLabel);
        return layout;
    }

    private void initMainPanel() {
        Panel metadataPanel = initMetadataPanel();
        mainLayout = new VerticalLayout();
        mainLayout.addComponents(metadataPanel, initReportButtonLayout());
        mainLayout.setExpandRatio(metadataPanel, 1);
        mainLayout.setSizeFull();
        mainLayout.setMargin(false);
        mainPanel = new Panel();
        mainPanel.setSizeFull();
        mainPanel.setContent(mainLayout);
        VaadinUtils.addComponentStyle(mainPanel, "scenarios-metadata");
    }

    private Panel initMetadataPanel() {
        Panel metadataPanel = new Panel();
        metadataPanel.setSizeFull();
        VerticalLayout metadataLayout = initMetadataLayout();
        metadataLayout.addComponent(initScenarioActionLayout());
        metadataLayout.setMargin(new MarginInfo(false, true, false, true));
        metadataPanel.setContent(metadataLayout);
        VaadinUtils.setMaxComponentsWidth(metadataLayout);
        return metadataPanel;
    }

    private VerticalLayout initMetadataLayout() {
        descriptionLabel.setStyleName("v-label-white-space-normal");
        selectionCriteriaLabel.setStyleName("v-label-white-space-normal");
        VerticalLayout layout =
            new VerticalLayout(ownerLabel, grossTotalLayout, serviceFeeTotalLayout, netTotalLayout, descriptionLabel,
                selectionCriteriaLabel, initUsageAgeWeightWidget(), initPublicationTypeWeightWidget(),
                initLicenseeClassMappingWidget(), copiedFromLabel);
        layout.setMargin(false);
        VaadinUtils.setMaxComponentsWidth(layout);
        return layout;
    }

    private Component initUsageAgeWeightWidget() {
        usageAgeWeightWidget = new ScenarioParameterWidget<>(
            ForeignUi.getMessage("button.usage_age_weights"),
            aclScenariosController.getUsageAgeWeights(),
            () -> {
                usageAgeWeightWidget.setAppliedParameters(
                    aclScenariosController.getUsageAgeWeightsByScenarioId(getSelectedScenario().getId()));
                return new AclUsageAgeWeightWindow(false);
            });
        return usageAgeWeightWidget;
    }

    private Component initPublicationTypeWeightWidget() {
        publicationTypeWeightWidget = new AclPublicationTypeWeightsParameterWidget(
            ForeignUi.getMessage("button.publication_type_weights"),
            List.of(),
            () -> {
                publicationTypeWeightWidget.setAppliedParameters(
                    aclScenariosController.getAclPublicationTypesByScenarioId(getSelectedScenario().getId()));
                return new AclPublicationTypeWeightsWindow(aclScenariosController, false);
            });
        return publicationTypeWeightWidget;
    }

    private Component initLicenseeClassMappingWidget() {
        licenseeClassMappingWidget = new ScenarioParameterWidget<>(
            ForeignUi.getMessage("button.licensee_class_mapping"),
            aclScenariosController.getDetailLicenseeClasses(),
            () -> {
                licenseeClassMappingWidget.setAppliedParameters(
                    aclScenariosController.getDetailLicenseeClassesByScenarioId(getSelectedScenario().getId()));
                return new AclAggregateLicenseeClassMappingViewWindow();
            });
        return licenseeClassMappingWidget;
    }

    private VerticalLayout initScenarioActionLayout() {
        VerticalLayout layout = new VerticalLayout(actionType, actionCreatedUser, actionCreatedDate, actionReason);
        VaadinUtils.addComponentStyle(layout, "scenario-last-action");
        layout.setCaption(ForeignUi.getMessage("label.scenario.action"));
        Button viewAllActions = new Button(ForeignUi.getMessage("button.caption.view_all_actions"));
        viewAllActions.addStyleName(ValoTheme.BUTTON_LINK);
        viewAllActions.addClickListener(event -> {
            IAclScenarioHistoryWidget scenarioHistoryWidget = aclScenarioHistoryController.initWidget();
            scenarioHistoryWidget.populateHistory(getSelectedScenario());
            Windows.showModalWindow((Window) scenarioHistoryWidget);
        });
        layout.setSpacing(false);
        layout.setMargin(false);
        layout.addComponent(viewAllActions);
        layout.setComponentAlignment(viewAllActions, Alignment.BOTTOM_RIGHT);
        VaadinUtils.setMaxComponentsWidth(layout, actionType, actionCreatedUser, actionCreatedDate, actionReason);
        VaadinUtils.setButtonsAutoDisabled(viewAllActions);
        return layout;
    }

    private VerticalLayout initReportButtonLayout() {
        Button summaryOfWorkSharesButton = buildReportButton("menu.report.summary_of_work_shares_by_agg_lc_report",
            aclScenariosController.getExportAclSummaryOfWorkSharesByAggLcStreamSource());
        Button workSharesButton = buildReportButton("menu.report.work_shares_by_agg_lc_class",
            aclScenariosController.getExportAclWorkSharesByAggLcStreamSource());
        VerticalLayout reportLayout = new VerticalLayout(summaryOfWorkSharesButton, workSharesButton);
        reportLayout.setHeight(60, Unit.PIXELS);
        return reportLayout;
    }

    private Button buildReportButton(String buttonName, IStreamSource streamSource) {
        Button button = Buttons.createButton(ForeignUi.getMessage(buttonName));
        button.addStyleName(ValoTheme.BUTTON_LINK);
        OnDemandFileDownloader downloader = new OnDemandFileDownloader(streamSource.getSource());
        downloader.extend(button);
        VaadinUtils.setButtonsAutoDisabled(button);
        return button;
    }

    private void onItemChanged(AclScenario scenario) {
        if (Objects.nonNull(scenario)) {
            AclScenarioDto scenarioWithAmounts =
                aclScenariosController.getAclScenarioWithAmountsAndLastAction(scenario.getId());
            updateScenarioMetadata(scenarioWithAmounts);
            ScenarioAuditItem lastAction = scenarioWithAmounts.getAuditItem();
            if (Objects.nonNull(lastAction)) {
                actionType.setValue(formatScenarioLabel(ForeignUi.getMessage("label.action_type"),
                    lastAction.getActionType()));
                actionCreatedUser.setValue(formatScenarioLabel(ForeignUi.getMessage("label.action_user"),
                    lastAction.getCreateUser()));
                actionCreatedDate.setValue(formatScenarioLabel(ForeignUi.getMessage("label.action_date"),
                    Objects.nonNull(lastAction.getCreateDate()) ? toLongFormat(lastAction.getCreateDate()) : null));
                actionReason.setValue(formatScenarioLabel(ForeignUi.getMessage("label.action_reason"),
                    lastAction.getActionReason()));
            }
            mainPanel.setContent(mainLayout);
        } else {
            mainPanel.setContent(new Label());
        }
        mediator.selectedScenarioChanged(scenario);
    }

    private void updateScenarioMetadata(AclScenarioDto scenario) {
        ownerLabel.setValue(ForeignUi.getMessage("label.owner", scenario.getCreateUser()));
        grossTotalLayout.setCaption(ForeignUi.getMessage("label.gross_amount_in_usd",
            formatAmount(scenario.getGrossTotal())));
        grossTotalPrintLabel.setValue(ForeignUi.getMessage("label.gross_amount_in_usd_by_print",
            formatAmount(scenario.getGrossTotalPrint())));
        grossTotalDigitalLabel.setValue(ForeignUi.getMessage("label.gross_amount_in_usd_by_digital",
            formatAmount(scenario.getGrossTotalDigital())));
        numberOfRhsPrintLabel.setValue(ForeignUi.getMessage("label.number_of_rhs_print",
            scenario.getNumberOfRhsPrint()));
        numberOfRhsDigitalLabel.setValue(ForeignUi.getMessage("label.number_of_rhs_digital",
            scenario.getNumberOfRhsDigital()));
        numberOfWorksPrintLabel.setValue(ForeignUi.getMessage("label.number_of_works_print",
            scenario.getNumberOfWorksPrint()));
        numberOfWorksDigitalLabel.setValue(ForeignUi.getMessage("label.number_of_works_digital",
            scenario.getNumberOfWorksDigital()));
        serviceFeeTotalLayout.setCaption(ForeignUi.getMessage("label.service_fee_amount_in_usd",
            formatAmount(scenario.getServiceFeeTotal())));
        serviceFeeTotalPrintLabel.setValue(ForeignUi.getMessage("label.service_fee_amount_in_usd_by_print",
            formatAmount(scenario.getServiceFeeTotalPrint())));
        serviceFeeTotalDigitalLabel.setValue(ForeignUi.getMessage("label.service_fee_amount_in_usd_by_digital",
            formatAmount(scenario.getServiceFeeTotalDigital())));
        netTotalLayout.setCaption(ForeignUi.getMessage("label.net_amount_in_usd",
            formatAmount(scenario.getNetTotal())));
        netTotalPrintLabel.setValue(ForeignUi.getMessage("label.net_amount_in_usd_by_print",
            formatAmount(scenario.getNetTotalPrint())));
        netTotalDigitalLabel.setValue(ForeignUi.getMessage("label.net_amount_in_usd_by_digital",
            formatAmount(scenario.getNetTotalDigital())));
        descriptionLabel.setValue(ForeignUi.getMessage("label.description", scenario.getDescription()));
        selectionCriteriaLabel.setValue(aclScenariosController.getCriteriaHtmlRepresentation());
        copiedFromLabel.setValue(ForeignUi.getMessage("label.copied_from",
            Objects.nonNull(scenario.getCopiedFrom()) ? scenario.getCopiedFrom() : StringUtils.EMPTY));
    }

    private String formatScenarioLabel(String caption, Object value) {
        return Objects.nonNull(value)
            ? ForeignUi.getMessage("label.format.label_with_caption", caption, value)
            : StringUtils.EMPTY;
    }

    private String formatAmount(BigDecimal amount) {
        return CurrencyUtils.formatAsHtml(amount.setScale(2, RoundingMode.HALF_UP));
    }

    private void selectFirstScenario(List<AclScenario> scenarios) {
        if (CollectionUtils.isNotEmpty(scenarios)) {
            scenarioGrid.select(scenarios.get(0));
        }
        refreshSelectedScenario();
    }
}
