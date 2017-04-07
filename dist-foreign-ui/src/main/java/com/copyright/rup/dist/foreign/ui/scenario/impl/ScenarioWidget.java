package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.vaadin.addons.lazyquerycontainer.LazyQueryContainer;

/**
 * Widget for viewing information about rightsholders, payee and their associated amounts grouped by rightsholder.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/31/17
 *
 * @author Ihar Suvorau
 */
public class ScenarioWidget extends Window implements IScenarioWidget {

    private ScenarioController controller;
    private SearchWidget searchWidget;
    private RightsholderTotalsHolderTable table;
    private LazyQueryContainer container;
    private Scenario scenario;

    @Override
    @SuppressWarnings("unchecked")
    public ScenarioWidget init() {
        VaadinUtils.setMaxComponentsWidth(this);
        VaadinUtils.addComponentStyle(this, "view-scenario-widget");
        scenario = controller.getScenario();
        setCaption(scenario.getName());
        setHeight(95, Unit.PERCENTAGE);
        setDraggable(false);
        setResizable(false);
        setContent(initContent());
        return this;
    }

    @Override
    public void setController(ScenarioController controller) {
        this.controller = controller;
    }

    @Override
    public void applySearch() {
        container = table.getContainerDataSource();
        String searchValue = getSearchValue();
        if (StringUtils.isNotBlank(searchValue)) {
            addContainerFilter(searchValue);
        } else {
            if (CollectionUtils.isNotEmpty(container.getContainerFilters())) {
                container.removeAllContainerFilters();
            }
            container.refresh();
        }
    }

    @Override
    public String getSearchValue() {
        return searchWidget.getSearchValue();
    }

    private VerticalLayout initContent() {
        HorizontalLayout searchToolbar = initSearchWidget();
        table = new RightsholderTotalsHolderTable(controller, RightsholderTotalsHolderBeanQuery.class);
        table.setColumnFooter("grossTotal", CurrencyUtils.formatAsHtml(scenario.getGrossTotal()));
        HorizontalLayout buttons = initButtons();
        VerticalLayout layout = new VerticalLayout(new VerticalLayout(searchToolbar), table, buttons);
        layout.setSizeFull();
        layout.setExpandRatio(table, 1);
        layout.setComponentAlignment(buttons, Alignment.BOTTOM_RIGHT);
        layout.setSpacing(true);
        return layout;
    }

    private HorizontalLayout initSearchWidget() {
        searchWidget = new SearchWidget(controller);
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.scenario.search_widget"));
        HorizontalLayout toolbar = new HorizontalLayout(searchWidget);
        VaadinUtils.setMaxComponentsWidth(toolbar);
        searchWidget.setWidth(60, Unit.PERCENTAGE);
        toolbar.setComponentAlignment(searchWidget, Alignment.MIDDLE_CENTER);
        toolbar.setSpacing(true);
        toolbar.setSizeFull();
        toolbar.setExpandRatio(searchWidget, 1);
        return toolbar;
    }

    private HorizontalLayout initButtons() {
        HorizontalLayout buttons = new HorizontalLayout(Buttons.createCloseButton(this));
        buttons.setSpacing(true);
        buttons.setMargin(new MarginInfo(false, true, true, false));
        return buttons;
    }

    private void addContainerFilter(String searchValue) {
        container.removeAllContainerFilters();
        container.addContainerFilter(new Or(new SimpleStringFilter("rightsholderName", searchValue, true, false),
            new SimpleStringFilter("rightsholderAccountNumber", searchValue, true, false)));
    }
}
