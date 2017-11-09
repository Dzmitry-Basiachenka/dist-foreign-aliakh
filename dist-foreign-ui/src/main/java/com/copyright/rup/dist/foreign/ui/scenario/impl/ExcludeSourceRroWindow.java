package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.LongColumnGenerator;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;
import com.copyright.rup.vaadin.widget.SearchWidget.ISearchController;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

/**
 * Represents window with ability to exclude details by source RRO.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 08/04/17
 *
 * @author Uladzislau Shalamitski
 */
public class ExcludeSourceRroWindow extends Window implements ISearchController {

    private static final String ACCOUNT_NUMBER_PROPERTY = "accountNumber";
    private static final String NAME_PROPERTY = "name";
    private static final String EXCLUDE_PROPERTY = "exclude";

    private IScenarioController scenarioController;
    private BeanContainer<Long, Rightsholder> rightsholderContainer;
    private SearchWidget searchWidget;

    /**
     * Constructs window.
     *
     * @param scenarioController instance of {@link IScenarioController}
     */
    ExcludeSourceRroWindow(IScenarioController scenarioController) {
        super(ForeignUi.getMessage("label.exclude.rro"));
        this.scenarioController = scenarioController;
        setWidth(830, Unit.PIXELS);
        setHeight(500, Unit.PIXELS);
        setContent(initContent());
    }

    @Override
    public void performSearch() {
        rightsholderContainer.removeAllContainerFilters();
        String searchValue = searchWidget.getSearchValue();
        if (StringUtils.isNotEmpty(searchValue)) {
            rightsholderContainer.addContainerFilter(new Or(
                new SimpleStringFilter(NAME_PROPERTY, searchValue, true, false),
                new SimpleStringFilter(ACCOUNT_NUMBER_PROPERTY, searchValue, true, false)));
        }
    }

    private VerticalLayout initContent() {
        searchWidget = new SearchWidget(this);
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.scenario.search_widget.rro"));
        VaadinUtils.addComponentStyle(this, "exclude-source-rro-window");
        HorizontalLayout buttons = createButtonsToolbar(Buttons.createCancelButton(this));
        Table rightsholderTable = initTable();
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(new MarginInfo(false, true, true, true));
        layout.setSpacing(true);
        layout.setSizeFull();
        layout.addComponents(searchWidget, rightsholderTable, buttons);
        layout.setComponentAlignment(buttons, Alignment.BOTTOM_RIGHT);
        layout.setExpandRatio(rightsholderTable, 1);
        return layout;
    }

    private HorizontalLayout createButtonsToolbar(Component... components) {
        HorizontalLayout horizontalLayout = new HorizontalLayout(components);
        horizontalLayout.setStyleName("buttons-toolbar");
        horizontalLayout.setSpacing(true);
        horizontalLayout.setMargin(new MarginInfo(false));
        return horizontalLayout;
    }

    private Table initTable() {
        rightsholderContainer = new BeanContainer<>(Rightsholder.class);
        rightsholderContainer.setBeanIdProperty(ACCOUNT_NUMBER_PROPERTY);
        rightsholderContainer.addAll(scenarioController.getSourceRros());
        Table table = new Table(null, rightsholderContainer);
        VaadinUtils.addComponentStyle(table, "exclude-details-by-rro-table");
        table.setSizeFull();
        table.addGeneratedColumn(ACCOUNT_NUMBER_PROPERTY, new LongColumnGenerator());
        table.addGeneratedColumn(EXCLUDE_PROPERTY, (ColumnGenerator) (source, itemId, columnId) -> {
            Button deleteButton = Buttons.createButton(ForeignUi.getMessage("button.exclude"));
            Rightsholder rightsholder = rightsholderContainer.getItem(itemId).getBean();
            deleteButton.setId(rightsholder.getId());
            deleteButton.addClickListener(event -> Windows.showModalWindow(
                new ExcludeRightsholdersWindow(rightsholder.getAccountNumber(), scenarioController, this)));
            return deleteButton;
        });
        table.setVisibleColumns(
            ACCOUNT_NUMBER_PROPERTY,
            NAME_PROPERTY,
            EXCLUDE_PROPERTY);
        table.setColumnHeaders(
            ForeignUi.getMessage("table.column.source_rro_account_number"),
            ForeignUi.getMessage("table.column.source_rro_account_name"),
            StringUtils.EMPTY);
        table.setColumnExpandRatio(ACCOUNT_NUMBER_PROPERTY, 1);
        table.setColumnExpandRatio(NAME_PROPERTY, 2);
        table.setColumnWidth(EXCLUDE_PROPERTY, 72);
        return table;
    }
}
