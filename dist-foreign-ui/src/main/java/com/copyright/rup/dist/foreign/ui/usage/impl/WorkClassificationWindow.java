package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.WorkClassification;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.util.ArrayList;

/**
 * Works classification window.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/07/2019
 *
 * @author Ihar Suvorau
 */
public class WorkClassificationWindow extends Window {

    private Grid<WorkClassification> grid;

    /**
     * Constructor.
     */
    public WorkClassificationWindow() {
        //TODO {isuvorau} IUsageController should be added
        setWidth(1000, Unit.PIXELS);
        setHeight(530, Unit.PIXELS);
        setContent(initContent());
        setCaption(ForeignUi.getMessage("window.works_classification"));
    }

    private Component initContent() {
        initGrid();
        HorizontalLayout buttons = initButtons();
        SearchWidget searchWidget = new SearchWidget(this::close);
        searchWidget.setPrompt("Enter Wr Wrk Inst or Work Title");
        searchWidget.setWidth(60, Unit.PERCENTAGE);
        VerticalLayout content = new VerticalLayout(searchWidget, grid, buttons);
        content.setMargin(true);
        content.setSpacing(true);
        content.setSizeFull();
        content.setExpandRatio(grid, 1.0f);
        content.setComponentAlignment(searchWidget, Alignment.MIDDLE_CENTER);
        content.setComponentAlignment(buttons, Alignment.BOTTOM_RIGHT);
        return content;
    }

    private HorizontalLayout initButtons() {
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> grid.deselectAll());
        buttonsLayout.addComponents(
            Buttons.createButton(ForeignUi.getMessage("button.mark_as_stm")),
            Buttons.createButton(ForeignUi.getMessage("button.mark_as_non_stm")),
            Buttons.createButton(ForeignUi.getMessage("button.mark_as_belletristic")),
            Buttons.createButton(ForeignUi.getMessage("button.remove_classification")),
            clearButton, Buttons.createCloseButton(this));
        buttonsLayout.setSpacing(true);
        return buttonsLayout;
    }

    private void initGrid() {
        //TODO {isuvorau} get data using controller
        DataProvider<WorkClassification, Void> dataProvider = LoadingIndicatorDataProvider.fromCallbacks(
            query -> new ArrayList<WorkClassification>().stream(),
            query -> 0);
        grid = new Grid<>(dataProvider);
        addColumns();
        grid.setSizeFull();
        grid.setSelectionMode(SelectionMode.MULTI);
        grid.getColumns().forEach(column -> column.setSortable(true));
    }

    private void addColumns() {
        addColumn(WorkClassification::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", 100);
        addColumn(WorkClassification::getSystemTitle, "table.column.system_title", "systemTitle", 200);
        addColumn(WorkClassification::getArticle, "table.column.article", "article", 140);
        addColumn(WorkClassification::getAuthor, "table.column.author", "author", 100);
        addColumn(WorkClassification::getPublisher, "table.column.publisher", "publisher", 140);
        addColumn(WorkClassification::getClassification, "table.column.classification", "classification", 110);
        addColumn(WorkClassification::getStandardNumber, "table.column.standard_number", "standardNumber", 140);
        addColumn(WorkClassification::getRhAccountNumber, "table.column.rh_account_number", "rhAccountNumber", 100);
        addColumn(WorkClassification::getRhName, "table.column.rh_account_name", "rhName", 200);
    }

    private void addColumn(ValueProvider<WorkClassification, ?> provider, String captionProperty, String sort,
                           double width) {
        grid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setWidth(width);
    }
}
