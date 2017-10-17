package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IDrillDownByRightsholderWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Represents widget to display drill down report.
 * Shows information about {@link com.copyright.rup.dist.foreign.domain.UsageDto}
 * for selected {@link com.copyright.rup.dist.common.domain.Rightsholder}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/11/17
 *
 * @author Ihar Suvorau
 */
public class DrillDownByRightsholderWidget extends Window implements IDrillDownByRightsholderWidget {

    private IDrillDownByRightsholderController controller;
    private DrillDownByRightsholderTable table;
    private SearchWidget searchWidget;

    @Override
    @SuppressWarnings("unchecked")
    public DrillDownByRightsholderWidget init() {
        setWidth(1280, Unit.PIXELS);
        setHeight(600, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "drill-down-by-rightsholder-widget");
        setContent(initContent());
        return this;
    }

    @Override
    public String getSearchValue() {
        return searchWidget.getSearchValue();
    }

    @Override
    public void setController(IDrillDownByRightsholderController drillDownByRightsholderController) {
        this.controller = drillDownByRightsholderController;
    }

    private VerticalLayout initContent() {
        table = new DrillDownByRightsholderTable(controller, UsageDetailsBeanQuery.class);
        HorizontalLayout buttonsLayout = initButtons();
        VerticalLayout content = new VerticalLayout(new VerticalLayout(initSearchWidget()), table, buttonsLayout);
        content.setSizeFull();
        content.setExpandRatio(table, 1);
        content.setSpacing(true);
        content.setMargin(new MarginInfo(false, true, true, true));
        content.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        return content;
    }

    private HorizontalLayout initSearchWidget() {
        searchWidget = new SearchWidget(() -> table.getContainerDataSource().refresh());
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.usage.search_widget"));
        HorizontalLayout layout = new HorizontalLayout(searchWidget);
        VaadinUtils.setMaxComponentsWidth(layout);
        searchWidget.setWidth(60, Unit.PERCENTAGE);
        layout.setComponentAlignment(searchWidget, Alignment.MIDDLE_CENTER);
        layout.setSpacing(true);
        layout.setSizeFull();
        layout.setExpandRatio(searchWidget, 1);
        return layout;
    }

    private HorizontalLayout initButtons() {
        HorizontalLayout buttons = new HorizontalLayout(Buttons.createCloseButton(this));
        buttons.setSpacing(true);
        return buttons;
    }
}
