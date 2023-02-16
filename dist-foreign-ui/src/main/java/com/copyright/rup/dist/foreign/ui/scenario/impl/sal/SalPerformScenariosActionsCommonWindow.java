package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenariosController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.SerializableComparator;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.Set;

/**
 * Represents common functionality for SAL choose scenarios windows.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/08/2023
 *
 * @author Mikita Maistrenka
 */
public abstract class SalPerformScenariosActionsCommonWindow extends Window implements SearchWidget.ISearchController {

    private static final SerializableComparator<Scenario> SCENARIOS_COMPARATOR = (scenario1, scenario2) ->
        Comparator.comparing(Scenario::getName, String::compareToIgnoreCase).compare(scenario1, scenario2);

    private final ISalScenariosController controller;
    private final String actionButtonCaption;
    private final ScenarioStatusEnum displayedStatus;
    private SearchWidget searchWidget;
    private Grid<Scenario> grid;
    private ListDataProvider<Scenario> dataProvider;
    private Button actionButton;

    /**
     * Constructor.
     *
     * @param controller                   instance of {@link ISalScenariosController}
     * @param actionButtonCaptionLabelName caption of action button
     * @param displayedStatus              status {@link ScenarioStatusEnum} with which will be displayed scenarios
     */
    SalPerformScenariosActionsCommonWindow(ISalScenariosController controller, String actionButtonCaptionLabelName,
                                           ScenarioStatusEnum displayedStatus) {
        this.controller = controller;
        this.actionButtonCaption = ForeignUi.getMessage(actionButtonCaptionLabelName);
        this.displayedStatus = displayedStatus;
        setHeight(400, Unit.PIXELS);
        setWidth(500, Unit.PIXELS);
        setResizable(true);
        setContent(initAndGetContentLayout());
        setCaption(ForeignUi.getMessage("window.choose_scenarios_to", actionButtonCaption));
        VaadinUtils.addComponentStyle(this, "sal-choose-scenarios-window");
    }

    @Override
    public void performSearch() {
        dataProvider.clearFilters();
        String searchValue = searchWidget.getSearchValue();
        if (StringUtils.isNotEmpty(searchValue)) {
            dataProvider.setFilter(scenario -> scenarioMatches(scenario, searchValue));
        }
    }

    /**
     * Performs action via click a button.
     *
     * @param selectedScenarios {@link Set} of selected scenarios
     */
    protected abstract void performAction(Set<Scenario> selectedScenarios);

    private VerticalLayout initAndGetContentLayout() {
        initSearchWidget();
        initGrid();
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout layout = new VerticalLayout(searchWidget, grid, buttonsLayout);
        layout.setSizeFull();
        layout.setExpandRatio(grid, 1);
        layout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        return layout;
    }

    private void initSearchWidget() {
        searchWidget = new SearchWidget(this);
        searchWidget.setPrompt(ForeignUi.getMessage("prompt.scenario"));
        searchWidget.setWidth(100, Unit.PERCENTAGE);
    }

    private void initGrid() {
        dataProvider = new ListDataProvider<>(controller.getScenariosByStatus(displayedStatus));
        grid = new Grid<>(dataProvider);
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addColumn(Scenario::getName)
            .setCaption(ForeignUi.getMessage("table.column.scenario_name"))
            .setComparator(SCENARIOS_COMPARATOR);
        grid.addSelectionListener(event ->
            actionButton.setEnabled(CollectionUtils.isNotEmpty(grid.getSelectedItems())));
        VaadinUtils.addComponentStyle(grid, "sal-choose-scenarios-grid");
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        actionButton = Buttons.createButton(actionButtonCaption);
        actionButton.addClickListener(event -> {
            performAction(grid.getSelectedItems());
            close();
        });
        actionButton.setEnabled(false);
        HorizontalLayout layout = new HorizontalLayout(actionButton, closeButton);
        layout.setMargin(new MarginInfo(true, false, false, false));
        layout.setSpacing(true);
        return layout;
    }

    private boolean scenarioMatches(Scenario scenario, String searchValue) {
        return StringUtils.containsIgnoreCase(scenario.getName(), searchValue);
    }
}
