package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenariosWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosMediator;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;

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
 * Contains common functionality for scenarios widget.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/11/19
 *
 * @author Stanislau Rudak
 */
public abstract class CommonScenariosWidget extends VerticalLayout implements ICommonScenariosWidget {

    private final Label actionType = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label actionCreatedUser = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label actionCreatedDate = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final Label actionReason = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private final IScenarioHistoryController scenarioHistoryController;
    private ICommonScenariosController controller;
    private Panel metadataPanel;
    private VerticalLayout metadataLayout;
    private ListDataProvider<Scenario> dataProvider;
    private Grid<Scenario> scenarioGrid;

    /**
     * Controller.
     *
     * @param historyController instance of {@link IScenarioHistoryController}
     */
    protected CommonScenariosWidget(IScenarioHistoryController historyController) {
        this.scenarioHistoryController = historyController;
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
    public CommonScenariosWidget init() {
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
    public void setController(ICommonScenariosController controller) {
        this.controller = controller;
    }

    /**
     * Formats scenario metadata label.
     *
     * @param caption label caption
     * @param value   label value
     * @return formatted value with caption
     */
    protected String formatScenarioLabel(String caption, Object value) {
        return ForeignUi.getMessage("label.format.label_with_caption", caption, value);
    }

    /**
     * Formats amount to string with round.
     *
     * @param amount amount
     * @return format amount
     */
    protected String formatAmount(BigDecimal amount) {
        return CurrencyUtils.formatAsHtml(amount.setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * Handles click on 'Edit Name' button.
     */
    protected void onEditNameButtonClicked() {
        Windows.showModalWindow(new EditScenarioNameWindow(controller, controller.getWidget().getSelectedScenario()));
    }

    protected ICommonScenariosController getController() {
        return controller;
    }

    /**
     * Inits a {@link HorizontalLayout} with scenario buttons.
     *
     * @return a {@link HorizontalLayout} with scenario buttons
     */
    protected abstract HorizontalLayout initButtonsLayout();

    /**
     * Inits a {@link VerticalLayout} with scenario metadata components.
     *
     * @return a {@link VerticalLayout} with scenario metadata components
     */
    protected abstract VerticalLayout initMetadataLayout();

    /**
     * Update scenario metadata panel info.
     *
     * @param scenarioWithAmounts a {@link Scenario} with amounts and last action
     * @see ICommonScenariosController#getScenarioWithAmountsAndLastAction(Scenario)
     */
    protected abstract void updateScenarioMetadata(Scenario scenarioWithAmounts);

    /**
     * @return an {@link IScenariosMediator} instance.
     */
    protected abstract IScenariosMediator getMediator();

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
            .setComparator((scenario1, scenario2) -> scenario1.getName().compareToIgnoreCase(scenario2.getName()))
            .setExpandRatio(1);
        scenarioGrid.addColumn(scenario -> getStringFromDate(scenario.getCreateDate()))
            .setCaption(ForeignUi.getMessage("table.column.create_date"))
            .setComparator((SerializableComparator<Scenario>) (scenario1, scenario2) ->
                scenario1.getCreateDate().compareTo(scenario2.getCreateDate()))
            .setWidth(100);
        scenarioGrid.addColumn(scenario -> scenario.getStatus().name())
            .setCaption(ForeignUi.getMessage("table.column.status"))
            .setWidth(130);
    }

    private void initMetadataPanel() {
        metadataPanel = new Panel();
        metadataPanel.setSizeFull();
        VaadinUtils.addComponentStyle(metadataPanel, "scenarios-metadata");
        metadataLayout = initMetadataLayout();
        metadataLayout.addComponent(initScenarioActionLayout());
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
        if (Objects.nonNull(scenario)) {
            Scenario scenarioWithAmounts = controller.getScenarioWithAmountsAndLastAction(scenario);
            updateScenarioMetadata(scenarioWithAmounts);
            ScenarioAuditItem lastAction = scenarioWithAmounts.getAuditItem();
            if (Objects.nonNull(lastAction)) {
                actionType.setValue(formatScenarioLabel(ForeignUi.getMessage("label.action_type"),
                    lastAction.getActionType()));
                actionCreatedUser.setValue(formatScenarioLabel(ForeignUi.getMessage("label.action_user"),
                    lastAction.getCreateUser()));
                actionCreatedDate.setValue(formatScenarioLabel(ForeignUi.getMessage("label.action_date"),
                    DateFormatUtils.format(lastAction.getCreateDate(), RupDateUtils.US_DATETIME_FORMAT_PATTERN_LONG)));
                actionReason.setValue(formatScenarioLabel(ForeignUi.getMessage("label.action_reason"),
                    lastAction.getActionReason()));
            }
            metadataPanel.setContent(metadataLayout);
        } else {
            metadataPanel.setContent(new Label());
        }
        getMediator().selectedScenarioChanged(scenario);
    }

    private String getStringFromDate(Date date) {
        return Objects.nonNull(date) ? FastDateFormat.getInstance("MM/dd/yyyy").format(date) : StringUtils.EMPTY;
    }
}
