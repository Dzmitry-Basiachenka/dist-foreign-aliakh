package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludeUsagesListener;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasScenarioController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;
import com.copyright.rup.vaadin.widget.SearchWidget.ISearchController;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
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
public class FasExcludeSourceRroWindow extends Window implements ISearchController {

    private final IFasScenarioController scenarioController;
    private SearchWidget searchWidget;
    private Grid<Rightsholder> grid;

    /**
     * Constructs window.
     *
     * @param scenarioController instance of {@link IFasScenarioController}
     */
    FasExcludeSourceRroWindow(IFasScenarioController scenarioController) {
        super(ForeignUi.getMessage("label.exclude.rro"));
        this.scenarioController = scenarioController;
        setWidth(880, Unit.PIXELS);
        setHeight(500, Unit.PIXELS);
        setContent(initContent());
    }

    @Override
    public void performSearch() {
        ListDataProvider<Rightsholder> dataProvider = (ListDataProvider<Rightsholder>) grid.getDataProvider();
        dataProvider.clearFilters();
        String searchValue = searchWidget.getSearchValue();
        if (StringUtils.isNotBlank(searchValue)) {
            dataProvider.setFilter(rightsholder ->
                caseInsensitiveContains(rightsholder.getAccountNumber().toString(), searchValue) ||
                    caseInsensitiveContains(rightsholder.getName(), searchValue));
        }
    }

    private Boolean caseInsensitiveContains(String where, String what) {
        return StringUtils.contains(StringUtils.lowerCase(where), StringUtils.lowerCase(what));
    }

    private VerticalLayout initContent() {
        searchWidget = new SearchWidget(this);
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.scenario.search_widget.rro"));
        VaadinUtils.addComponentStyle(this, "exclude-source-rro-window");
        HorizontalLayout buttons = createButtonsToolbar(Buttons.createCancelButton(this));
        initGrid();
        VerticalLayout layout = new VerticalLayout(searchWidget, grid, buttons);
        layout.setMargin(new MarginInfo(false, true, true, true));
        layout.setSizeFull();
        layout.setComponentAlignment(buttons, Alignment.BOTTOM_RIGHT);
        layout.setExpandRatio(grid, 1);
        return layout;
    }

    private HorizontalLayout createButtonsToolbar(Component... components) {
        HorizontalLayout horizontalLayout = new HorizontalLayout(components);
        horizontalLayout.setStyleName("buttons-toolbar");
        horizontalLayout.setMargin(new MarginInfo(false));
        return horizontalLayout;
    }

    private void initGrid() {
        grid = new Grid<>();
        grid.setItems(scenarioController.getSourceRros());
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setSizeFull();
        addColumns();
        VaadinUtils.addComponentStyle(grid, "exclude-details-by-rro-grid");
    }

    private void addColumns() {
        grid.addColumn(Rightsholder::getAccountNumber)
            .setCaption(ForeignUi.getMessage("table.column.source_rro_account_number"))
            .setSortProperty("accountNumber")
            .setExpandRatio(2);
        grid.addColumn(Rightsholder::getName)
            .setCaption(ForeignUi.getMessage("table.column.source_rro_account_name"))
            .setSortProperty("name")
            .setExpandRatio(4);
        grid.addComponentColumn(rightsholder -> {
            Button excludeButton = Buttons.createButton(ForeignUi.getMessage("button.exclude"));
            excludeButton.setId(rightsholder.getId());
            excludeButton.addClickListener(event -> {
                FasExcludeRightsholdersWindow window =
                    new FasExcludeRightsholdersWindow(rightsholder.getAccountNumber(), scenarioController);
                Windows.showModalWindow(window);
                window.addListener((IExcludeUsagesListener) excludeUsagesEvent -> {
                    close();
                    scenarioController.fireWidgetEvent(excludeUsagesEvent);
                });
            });
            VaadinUtils.setButtonsAutoDisabled(excludeButton);
            return excludeButton;
        }).setWidth(95)
            .setSortable(false);
    }
}
