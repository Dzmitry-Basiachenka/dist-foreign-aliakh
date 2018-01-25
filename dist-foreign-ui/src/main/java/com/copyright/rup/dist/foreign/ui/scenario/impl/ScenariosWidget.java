package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.domain.BaseEntity;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.DateColumnGenerator;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.math.BigDecimal;
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

    private IScenarioHistoryController scenarioHistoryController;
    private IScenariosController controller;
    private Button deleteButton = Buttons.createButton(ForeignUi.getMessage("button.delete"));
    private Button viewButton = Buttons.createButton(ForeignUi.getMessage("button.view"));
    private Button submitButton = Buttons.createButton(ForeignUi.getMessage("button.submit"));
    private Button rejectButton = Buttons.createButton(ForeignUi.getMessage("button.reject"));
    private Button approveButton = Buttons.createButton(ForeignUi.getMessage("button.approve"));
    private Button sendToLmButton = Buttons.createButton(ForeignUi.getMessage("button.send_to_lm"));
    private BeanContainer<String, Scenario> container;
    private Label ownerLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private Label netTotalLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private Label reportedTotalLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private Label grossTotalLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private Label descriptionLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private Label actionType = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private Label actionCreatedUser = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private Label actionCreatedDate = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private Label actionReason = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private Table table;
    private Panel metadataPanel;
    private VerticalLayout metadataLayout;
    private ScenariosMediator mediator;

    /**
     * Controller.
     *
     * @param historyController instance of {@link IScenarioHistoryController}
     */
    public ScenariosWidget(IScenarioHistoryController historyController) {
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
        mediator.selectedScenarioChanged(getSelectedScenario());
        return mediator;
    }

    @Override
    public void refresh() {
        container.removeAllItems();
        container.addAll(controller.getScenarios());
        selectScenario(table.firstItemId());
        refreshSelectedScenario();
    }

    @Override
    public void selectScenario(Object scenarioId) {
        table.select(scenarioId);
    }

    @Override
    public Scenario getSelectedScenario() {
        String itemId = Objects.toString(table.getValue(), null);
        return null != itemId ? container.getItem(itemId).getBean() : null;
    }

    @Override
    public void refreshSelectedScenario() {
        onItemChanged(getSelectedScenario());
    }

    @Override
    @SuppressWarnings("unchecked")
    public ScenariosWidget init() {
        setSizeFull();
        initTable();
        initMetadataPanel();
        HorizontalLayout horizontalLayout = new HorizontalLayout(table, metadataPanel);
        horizontalLayout.setSizeFull();
        horizontalLayout.setExpandRatio(table, 0.7f);
        horizontalLayout.setExpandRatio(metadataPanel, 0.3f);
        horizontalLayout.setSpacing(true);
        addComponents(initButtonsLayout(), horizontalLayout);
        setExpandRatio(horizontalLayout, 1);
        return this;
    }

    @Override
    public void setController(IScenariosController controller) {
        this.controller = controller;
    }

    private HorizontalLayout initButtonsLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        addButtonsListeners();
        VaadinUtils.setButtonsAutoDisabled(viewButton, deleteButton, submitButton, rejectButton, approveButton,
            sendToLmButton);
        layout.addComponents(viewButton, deleteButton, submitButton, rejectButton, approveButton, sendToLmButton);
        layout.setSpacing(true);
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(layout, "scenarios-buttons");
        return layout;
    }

    private void addButtonsListeners() {
        deleteButton.addClickListener(event -> controller.onDeleteButtonClicked());
        viewButton.addClickListener(event -> controller.onViewButtonClicked());
        submitButton.addClickListener(event -> controller.handleAction(ScenarioActionTypeEnum.SUBMITTED));
        rejectButton.addClickListener(event -> controller.handleAction(ScenarioActionTypeEnum.REJECTED));
        approveButton.addClickListener(event -> controller.handleAction(ScenarioActionTypeEnum.APPROVED));
        sendToLmButton.addClickListener(event -> controller.sendToLm());
    }

    private void initTable() {
        container = new BeanContainer<>(Scenario.class);
        container.setBeanIdResolver(BaseEntity::getId);
        container.addAll(controller.getScenarios());
        table = new Table(null, container);
        table.setSizeFull();
        table.setVisibleColumns("name", "createDate", "status");
        table.setColumnHeaders(
            ForeignUi.getMessage("table.column.name"),
            ForeignUi.getMessage("table.column.create_date"),
            ForeignUi.getMessage("table.column.status"));
        table.setColumnWidth("createDate", 100);
        table.setColumnWidth("status", 130);
        table.setColumnExpandRatio("name", 1);
        table.addGeneratedColumn("createDate", new DateColumnGenerator());
        table.addValueChangeListener(event -> {
            BeanItem<Scenario> item = container.getItem(event.getProperty().getValue());
            onItemChanged(null != item ? item.getBean() : null);
        });
        VaadinUtils.addComponentStyle(table, "scenarios-table");
    }

    private void initMetadataPanel() {
        metadataPanel = new Panel();
        metadataPanel.setSizeFull();
        VaadinUtils.addComponentStyle(metadataPanel, "scenarios-metadata");
        metadataLayout = new VerticalLayout(ownerLabel, netTotalLabel, grossTotalLabel, reportedTotalLabel,
            descriptionLabel, initScenarioActionLayout());
        metadataLayout.setSpacing(true);
        metadataLayout.setMargin(new MarginInfo(false, true, false, true));
        VaadinUtils.setMaxComponentsWidth(metadataLayout);
    }

    private VerticalLayout initScenarioActionLayout() {
        VerticalLayout layout = new VerticalLayout(actionType, actionCreatedUser, actionCreatedDate, actionReason);
        VaadinUtils.addComponentStyle(layout, "scenario-last-action");
        layout.setCaption(ForeignUi.getMessage("label.scenario.action"));
        Button viewAllActions = new Button(ForeignUi.getMessage("button.caption.view_all_actions"));
        viewAllActions.addStyleName(BaseTheme.BUTTON_LINK);
        viewAllActions.addClickListener(event -> {
            IScenarioHistoryWidget scenarioHistoryWidget = scenarioHistoryController.initWidget();
            scenarioHistoryWidget.populateHistory(getSelectedScenario());
            Windows.showModalWindow((Window) scenarioHistoryWidget);
        });
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
            descriptionLabel.setValue(ForeignUi.getMessage("label.description", scenario.getDescription()));
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
}
