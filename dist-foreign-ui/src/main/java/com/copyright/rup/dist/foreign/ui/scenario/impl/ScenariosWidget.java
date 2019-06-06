package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.SerializableComparator;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Widget that represents 'Scenarios' tab.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/14/17
 *
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
 */
public class ScenariosWidget extends VerticalLayout implements IScenariosWidget {

    private final IScenarioHistoryController scenarioHistoryController;
    private final Button deleteButton = Buttons.createButton(ForeignUi.getMessage("button.delete"));
    private final Button viewButton = Buttons.createButton(ForeignUi.getMessage("button.view"));
    private final Button submitButton = Buttons.createButton(ForeignUi.getMessage("button.submit"));
    private final Button rejectButton = Buttons.createButton(ForeignUi.getMessage("button.reject"));
    private final Button approveButton = Buttons.createButton(ForeignUi.getMessage("button.approve"));
    private final Button sendToLmButton = Buttons.createButton(ForeignUi.getMessage("button.send_to_lm"));
    private final Button reconcileRightsholdersButton =
        Buttons.createButton(ForeignUi.getMessage("button.reconcile_rightsholders"));
    private final Button refreshScenarioButton = Buttons.createButton(ForeignUi.getMessage("button.refresh_scenario"));
    private final Label ownerLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label netTotalLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label reportedTotalLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label grossTotalLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label rhMinimumAmountLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label descriptionLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label selectionCriteriaLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label actionType = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label actionCreatedUser = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label actionCreatedDate = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label actionReason = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private IScenariosController controller;
    private Panel metadataPanel;
    private VerticalLayout metadataLayout;
    private ScenariosMediator mediator;
    private ListDataProvider<Scenario> dataProvider;
    private Grid<Scenario> scenarioGrid;

    /**
     * Controller.
     *
     * @param historyController instance of {@link IScenarioHistoryController}
     */
    ScenariosWidget(IScenarioHistoryController historyController) {
        this.scenarioHistoryController = historyController;
    }

    @Override
    public IMediator initMediator() {
        mediator = new ScenariosMediator();
        mediator.setViewButton(viewButton);
        mediator.setDeleteButton(deleteButton);
        mediator.setApproveButton(approveButton);
        mediator.setRejectButton(rejectButton);
        mediator.setSubmitButton(submitButton);
        mediator.setSendToLmButton(sendToLmButton);
        mediator.setReconcileRightsholdersButton(reconcileRightsholdersButton);
        mediator.setRefreshScenarioButton(refreshScenarioButton);
        mediator.setRhMinimumAmountLabel(rhMinimumAmountLabel);
        mediator.selectedScenarioChanged(getSelectedScenario());
        return mediator;
    }

    @Override
    public void refresh() {
        List<Scenario> scenarios = controller.getScenarios();
        dataProvider = DataProvider.ofCollection(scenarios);
        scenarioGrid.setDataProvider(dataProvider);
        if (CollectionUtils.isNotEmpty(scenarios)) {
            selectScenario(scenarios.get(0));
            refreshSelectedScenario();
        }
    }

    @Override
    public void selectScenario(Scenario scenario) {
        scenarioGrid.select(scenario);
    }

    @Override
    public Scenario getSelectedScenario() {
        return scenarioGrid.getSelectedItems().stream().findFirst().orElse(null);
    }

    @Override
    public void refreshSelectedScenario() {
        onItemChanged(getSelectedScenario());
    }

    @Override
    @SuppressWarnings("unchecked")
    public ScenariosWidget init() {
        setSizeFull();
        initGrid();
        initMetadataPanel();
        HorizontalLayout horizontalLayout = new HorizontalLayout(scenarioGrid, metadataPanel);
        horizontalLayout.setSizeFull();
        horizontalLayout.setExpandRatio(scenarioGrid, 0.7f);
        horizontalLayout.setExpandRatio(metadataPanel, 0.3f);
        addComponents(initButtonsLayout(), horizontalLayout);
        setExpandRatio(horizontalLayout, 1);
        setSpacing(false);
        setMargin(false);
        return this;
    }

    @Override
    public void setController(IScenariosController controller) {
        this.controller = controller;
    }

    private HorizontalLayout initButtonsLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        addButtonsListeners();
        VaadinUtils.setButtonsAutoDisabled(viewButton, deleteButton, reconcileRightsholdersButton, submitButton,
            rejectButton, approveButton, sendToLmButton, refreshScenarioButton);
        layout.addComponents(viewButton, deleteButton, reconcileRightsholdersButton, submitButton, rejectButton,
            approveButton, sendToLmButton, refreshScenarioButton);
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(layout, "scenarios-buttons");
        return layout;
    }

    private void addButtonsListeners() {
        deleteButton.addClickListener(event -> controller.onDeleteButtonClicked());
        viewButton.addClickListener(event -> controller.onViewButtonClicked());
        reconcileRightsholdersButton.addClickListener(event -> controller.onReconcileRightsholdersButtonClicked());
        submitButton.addClickListener(event -> controller.handleAction(ScenarioActionTypeEnum.SUBMITTED));
        rejectButton.addClickListener(event -> controller.handleAction(ScenarioActionTypeEnum.REJECTED));
        approveButton.addClickListener(event -> controller.handleAction(ScenarioActionTypeEnum.APPROVED));
        sendToLmButton.addClickListener(event -> controller.sendToLm());
        refreshScenarioButton.addClickListener(event -> controller.onRefreshScenarioButtonClicked());
    }

    private void initGrid() {
        dataProvider = DataProvider.ofCollection(controller.getScenarios());
        scenarioGrid = new Grid<>(dataProvider);
        addColumns();
        scenarioGrid.setSizeFull();
        scenarioGrid.getColumns().forEach(column -> column.setSortable(true));
        scenarioGrid.addSelectionListener(event -> onItemChanged(event.getFirstSelectedItem().orElse(null)));
        VaadinUtils.addComponentStyle(scenarioGrid, "scenarios-table");
    }

    private void addColumns() {
        scenarioGrid.addColumn(Scenario::getName)
            .setCaption(ForeignUi.getMessage("table.column.name"))
            .setSortProperty("name")
            .setExpandRatio(1);
        scenarioGrid.addColumn(scenario -> getStringFromDate(scenario.getCreateDate()))
            .setCaption(ForeignUi.getMessage("table.column.create_date"))
            .setComparator((SerializableComparator<Scenario>) (scenario1, scenario2) ->
                scenario1.getCreateDate().compareTo(scenario2.getCreateDate()))
            .setWidth(100);
        scenarioGrid.addColumn(scenario -> scenario.getStatus().name())
            .setCaption(ForeignUi.getMessage("table.column.status"))
            .setSortProperty("status")
            .setWidth(130);
    }

    private void initMetadataPanel() {
        metadataPanel = new Panel();
        metadataPanel.setSizeFull();
        VaadinUtils.addComponentStyle(metadataPanel, "scenarios-metadata");
        descriptionLabel.setStyleName("v-label-white-space-normal");
        selectionCriteriaLabel.setStyleName("v-label-white-space-normal");
        metadataLayout = new VerticalLayout(ownerLabel, netTotalLabel, grossTotalLabel, reportedTotalLabel,
            rhMinimumAmountLabel, descriptionLabel, selectionCriteriaLabel, initScenarioActionLayout());
        metadataLayout.setMargin(new MarginInfo(false, true, false, true));
        VaadinUtils.setMaxComponentsWidth(metadataLayout);
    }

    private VerticalLayout initScenarioActionLayout() {
        VerticalLayout layout = new VerticalLayout(actionType, actionCreatedUser, actionCreatedDate, actionReason);
        VaadinUtils.addComponentStyle(layout, "scenario-last-action");
        layout.setCaption(ForeignUi.getMessage("label.scenario.action"));
        Button viewAllActions = new Button(ForeignUi.getMessage("button.caption.view_all_actions"));
        viewAllActions.addStyleName(ValoTheme.BUTTON_LINK);
        viewAllActions.addClickListener(event -> {
            IScenarioHistoryWidget scenarioHistoryWidget = scenarioHistoryController.initWidget();
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

    private void onItemChanged(Scenario scenario) {
        if (null != scenario) {
            Scenario scenarioWithAmounts = controller.getScenarioWithAmountsAndLastAction(scenario);
            ownerLabel.setValue(ForeignUi.getMessage("label.owner", scenario.getCreateUser()));
            netTotalLabel.setValue(ForeignUi.getMessage("label.net_amount_in_usd",
                formatAmount(scenarioWithAmounts.getNetTotal())));
            grossTotalLabel.setValue(ForeignUi.getMessage("label.gross_amount_in_usd",
                formatAmount(scenarioWithAmounts.getGrossTotal())));
            reportedTotalLabel.setValue(ForeignUi.getMessage("label.reported_total",
                formatAmount(scenarioWithAmounts.getReportedTotal())));
            if (null != scenario.getNtsFields()) {
                rhMinimumAmountLabel.setValue(ForeignUi.getMessage("label.rh_minimum_amount_in_usd",
                    formatAmount(scenario.getNtsFields().getRhMinimumAmount())));
            } else {
                rhMinimumAmountLabel.setValue(StringUtils.EMPTY);
            }
            descriptionLabel.setValue(ForeignUi.getMessage("label.description", scenario.getDescription()));
            selectionCriteriaLabel.setValue(controller.getCriteriaHtmlRepresentation());
            ScenarioAuditItem lastAction = scenarioWithAmounts.getAuditItem();
            if (Objects.nonNull(lastAction)) {
                actionType.setValue(formatLabel(ForeignUi.getMessage("label.action_type"),
                    lastAction.getActionType()));
                actionCreatedUser.setValue(formatLabel(ForeignUi.getMessage("label.action_user"),
                    lastAction.getCreateUser()));
                actionCreatedDate.setValue(formatLabel(ForeignUi.getMessage("label.action_date"),
                    DateFormatUtils.format(lastAction.getCreateDate(), RupDateUtils.US_DATETIME_FORMAT_PATTERN_LONG)));
                actionReason.setValue(formatLabel(ForeignUi.getMessage("label.action_reason"),
                    lastAction.getActionReason()));
            }
            metadataPanel.setContent(metadataLayout);
        } else {
            metadataPanel.setContent(new Label());
        }
        mediator.selectedScenarioChanged(scenario);
    }

    private String formatAmount(BigDecimal amount) {
        return CurrencyUtils.formatAsHtml(amount.setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    private String formatLabel(String caption, Object value) {
        return ForeignUi.getMessage("label.format.label_with_caption", caption, value);
    }

    private String getStringFromDate(Date date) {
        return Objects.nonNull(date) ? FastDateFormat.getInstance("MM/dd/yyyy").format(date) : StringUtils.EMPTY;
    }
}
