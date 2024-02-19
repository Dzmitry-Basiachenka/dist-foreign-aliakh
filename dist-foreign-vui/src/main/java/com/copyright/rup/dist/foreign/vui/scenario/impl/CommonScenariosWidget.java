package com.copyright.rup.dist.foreign.vui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.vui.common.utils.IDateFormatter;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenariosController;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenariosWidget;
import com.copyright.rup.dist.foreign.vui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.vui.scenario.api.IScenarioHistoryWidget;
import com.copyright.rup.dist.foreign.vui.scenario.api.IScenariosMediator;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.CurrencyUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.Scroller.ScrollDirection;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout.Orientation;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.SerializableComparator;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
public abstract class CommonScenariosWidget extends VerticalLayout implements ICommonScenariosWidget, IDateFormatter {

    private static final long serialVersionUID = 6332062477485137753L;

    private final Div actionType = new Div();
    private final Div actionCreatedUser = new Div();
    private final Div actionCreatedDate = new Div();
    private final Div actionReason = new Div();
    private final IScenarioHistoryController scenarioHistoryController;

    private ICommonScenariosController controller;
    private Section metadataPanel;
    private VerticalLayout metadataLayout;
    private ListDataProvider<Scenario> dataProvider;
    private Grid<Scenario> scenarioGrid;

    /**
     * Constructor.
     *
     * @param historyController instance of {@link IScenarioHistoryController}
     */
    protected CommonScenariosWidget(IScenarioHistoryController historyController) {
        this.scenarioHistoryController = historyController;
    }

    @Override
    @SuppressWarnings("unchecked")
    public CommonScenariosWidget init() {
        setSizeFull();
        initGrid();
        initMetadataPanel();
        setAlignItems(Alignment.STRETCH);
        add(initButtonsLayout(), initTablesPanel());
        setMargin(false);
        setSpacing(false);
        VaadinUtils.setPadding(this, 0, 10, 10, 10);
        VaadinUtils.addComponentStyle(this, "scenarios-widget");
        return this;
    }

    @Override
    public void refresh() {
        List<Scenario> scenarios = controller.getScenarios();
        dataProvider = DataProvider.ofCollection(scenarios);
        scenarioGrid.setItems(dataProvider);
        if (CollectionUtils.isNotEmpty(scenarios)) {
            selectScenario(scenarios.get(0));
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
    public void setController(ICommonScenariosController controller) {
        this.controller = controller;
    }

    /**
     * Handles click on 'Edit Name' button.
     */
    protected void onEditNameButtonClicked() {
        Windows.showModalWindow(new EditScenarioNameWindow(controller, controller.getWidget().getSelectedScenario()));
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
        return CurrencyUtils.formatAsHtml(amount.setScale(2, RoundingMode.HALF_UP));
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

    /**
     * Updates div content.
     *
     * @param div   div component
     * @param value value
     */
    protected void updateDivContent(Div div, String value) {
        div.getElement().setProperty("innerHTML", value);
    }

    private void initGrid() {
        dataProvider = DataProvider.ofCollection(controller.getScenarios());
        scenarioGrid = new Grid<>();
        scenarioGrid.setItems(dataProvider);
        addColumns();
        scenarioGrid.setSizeFull();
        scenarioGrid.getColumns().forEach(column -> column.setSortable(true));
        scenarioGrid.addSelectionListener(event -> onItemChanged(event.getFirstSelectedItem().orElse(null)));
        VaadinUtils.setGridProperties(scenarioGrid, "scenarios-table");
    }

    private void addColumns() {
        scenarioGrid.addColumn(Scenario::getName)
            .setHeader(ForeignUi.getMessage("table.column.name"))
            .setComparator((scenario1, scenario2) -> scenario1.getName().compareToIgnoreCase(scenario2.getName()))
            .setResizable(true)
            .setAutoWidth(true);
        scenarioGrid.addColumn(scenario -> getStringFromDate(scenario.getCreateDate()))
            .setHeader(ForeignUi.getMessage("table.column.created_date"))
            .setComparator((SerializableComparator<Scenario>) (scenario1, scenario2) ->
                scenario1.getCreateDate().compareTo(scenario2.getCreateDate()))
            .setResizable(true)
            .setFlexGrow(0)
            .setWidth("150px");
        scenarioGrid.addColumn(scenario -> scenario.getStatus().name())
            .setHeader(ForeignUi.getMessage("table.column.status"))
            .setResizable(true)
            .setFlexGrow(0)
            .setWidth("150px");
    }

    private void initMetadataPanel() {
        metadataPanel = new Section();
        metadataPanel.setSizeFull();
        VaadinUtils.addComponentStyle(metadataPanel, "scenarios-metadata");
        metadataLayout = initMetadataLayout();
        metadataLayout.add(initScenarioActionLayout());
        metadataLayout.setWidthFull();
    }

    private VerticalLayout initScenarioActionLayout() {
        var actionMetadataLayout =
            VaadinUtils.initCommonVerticalLayout(actionType, actionCreatedUser, actionCreatedDate, actionReason);
        VaadinUtils.setPadding(actionMetadataLayout, 0, 0, 0, 20);
        var viewAllActionsButton = Buttons.createLinkButton(ForeignUi.getMessage("button.caption.view_all_actions"));
        viewAllActionsButton.addClickListener(event -> {
            IScenarioHistoryWidget scenarioHistoryWidget = scenarioHistoryController.initWidget();
            scenarioHistoryWidget.populateHistory(getSelectedScenario());
            Windows.showModalWindow((Dialog) scenarioHistoryWidget);
        });
        var lastActionCaption =
            new Html(String.format("<div><b>%s</b></div>", ForeignUi.getMessage("label.scenario.action")));
        var layout =
            VaadinUtils.initCommonVerticalLayout(lastActionCaption, actionMetadataLayout, viewAllActionsButton);
        layout.setHorizontalComponentAlignment(Alignment.END, viewAllActionsButton);
        VaadinUtils.addComponentStyle(layout, "scenario-last-action");
        VaadinUtils.setFullComponentsWidth(layout, actionType, actionCreatedUser, actionCreatedDate, actionReason);
        return layout;
    }

    private SplitLayout initTablesPanel() {
        var scroller = new Scroller(metadataPanel);
        scroller.setScrollDirection(ScrollDirection.VERTICAL);
        scroller.addClassName("scenarios-metadata-panel-scroller");
        var tablesPanel = new SplitLayout(scenarioGrid, scroller);
        tablesPanel.setOrientation(Orientation.HORIZONTAL);
        tablesPanel.setSplitterPosition(70);
        tablesPanel.setSizeFull();
        tablesPanel.addClassName("scenarios-tables-panel-split-layout");
        return tablesPanel;
    }

    private void onItemChanged(Scenario scenario) {
        metadataPanel.removeAll();
        if (Objects.nonNull(scenario)) {
            Scenario scenarioWithAmounts = controller.getScenarioWithAmountsAndLastAction(scenario);
            updateScenarioMetadata(scenarioWithAmounts);
            ScenarioAuditItem lastAction = scenarioWithAmounts.getAuditItem();
            if (Objects.nonNull(lastAction)) {
                updateDivContent(actionType, formatScenarioLabel(ForeignUi.getMessage("label.action_type"),
                    lastAction.getActionType()));
                updateDivContent(actionCreatedUser, formatScenarioLabel(ForeignUi.getMessage("label.action_user"),
                    lastAction.getCreateUser()));
                updateDivContent(actionCreatedDate, formatScenarioLabel(ForeignUi.getMessage("label.action_date"),
                    toLongFormat(lastAction.getCreateDate())));
                updateDivContent(actionReason, formatScenarioLabel(ForeignUi.getMessage("label.action_reason"),
                    lastAction.getActionReason()));
            }
            metadataPanel.add(metadataLayout);
        }
        getMediator().selectedScenarioChanged(scenario);
    }

    private String getStringFromDate(Date date) {
        return Objects.nonNull(date) ? FastDateFormat.getInstance("MM/dd/yyyy").format(date) : StringUtils.EMPTY;
    }
}
