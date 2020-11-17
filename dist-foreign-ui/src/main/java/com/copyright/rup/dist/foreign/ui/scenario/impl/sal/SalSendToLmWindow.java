package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

import com.copyright.rup.dist.foreign.domain.Scenario;
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

/**
 * Window to select and send scenarios to LM.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/04/2020
 *
 * @author Uladzislau Shalamitski
 */
public class SalSendToLmWindow extends Window implements SearchWidget.ISearchController {

    private static final SerializableComparator<Scenario> SCENARIOS_COMPARATOR = (scenario1, scenario2) ->
        Comparator.comparing(Scenario::getName, String::compareToIgnoreCase).compare(scenario1, scenario2);

    private final ISalScenariosController controller;
    private SearchWidget searchWidget;
    private Grid<Scenario> grid;
    private ListDataProvider<Scenario> dataProvider;
    private Button sendToLmButton;

    /**
     * Constructor.
     *
     * @param controller instance of {@link ISalScenariosController}
     */
    public SalSendToLmWindow(ISalScenariosController controller) {
        this.controller = controller;
        setHeight(400, Unit.PIXELS);
        setWidth(500, Unit.PIXELS);
        setResizable(true);
        setContent(initAndGetContentLayout());
        setCaption(ForeignUi.getMessage("window.choose_scenarios_to_send_to_lm"));
        VaadinUtils.addComponentStyle(this, "send-to-lm-window");
    }

    @Override
    public void performSearch() {
        dataProvider.clearFilters();
        String searchValue = searchWidget.getSearchValue();
        if (StringUtils.isNotEmpty(searchValue)) {
            grid.getSelectedItems().stream()
                .filter(scenario -> !scenarioMatches(scenario, searchValue))
                .forEach(grid::deselect);
            dataProvider.setFilter(scenario -> scenarioMatches(scenario, searchValue));
        }
    }

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
        dataProvider = new ListDataProvider<>(controller.getApprovedScenarios());
        grid = new Grid<>(dataProvider);
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addColumn(Scenario::getName)
            .setCaption(ForeignUi.getMessage("table.column.scenario_name"))
            .setComparator(SCENARIOS_COMPARATOR);
        grid.addSelectionListener(event ->
            sendToLmButton.setEnabled(CollectionUtils.isNotEmpty(grid.getSelectedItems())));
        VaadinUtils.addComponentStyle(grid, "send-to-lm-grid");
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        sendToLmButton = Buttons.createButton(ForeignUi.getMessage("button.send_to_lm"));
        sendToLmButton.addClickListener(event -> {
            controller.sendToLm(grid.getSelectedItems());
            close();
        });
        sendToLmButton.setEnabled(false);
        HorizontalLayout layout = new HorizontalLayout(sendToLmButton, closeButton);
        layout.setMargin(new MarginInfo(true, false, false, false));
        layout.setSpacing(true);
        return layout;
    }

    private boolean scenarioMatches(Scenario scenario, String searchValue) {
        return StringUtils.containsIgnoreCase(scenario.getName(), searchValue);
    }
}
