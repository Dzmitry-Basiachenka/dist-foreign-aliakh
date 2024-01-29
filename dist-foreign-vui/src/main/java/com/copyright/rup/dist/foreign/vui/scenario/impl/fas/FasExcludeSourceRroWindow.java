package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.scenario.api.ExcludeUsagesEvent;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenarioController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget.ISearchController;

import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.ValueProvider;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * Represents window with ability to exclude details by source RRO.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 08/04/17
 *
 * @author Uladzislau Shalamitski
 */
public class FasExcludeSourceRroWindow extends CommonDialog implements ISearchController {

    private static final long serialVersionUID = 6320399153641040818L;

    private final IFasScenarioController scenarioController;

    private SearchWidget searchWidget;
    private Grid<Rightsholder> grid;

    /**
     * Constructs window.
     *
     * @param scenarioController instance of {@link IFasScenarioController}
     */
    FasExcludeSourceRroWindow(IFasScenarioController scenarioController) {
        this.scenarioController = scenarioController;
        super.setHeaderTitle(ForeignUi.getMessage("label.exclude.rro"));
        super.setWidth("880px");
        super.setHeight("500px");
        super.add(initContent());
        super.setModalWindowProperties("exclude-source-rro-window", true);
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
        initGrid();
        var layout = VaadinUtils.initCommonVerticalLayout(searchWidget, grid);
        VaadinUtils.setPadding(layout, 3, 10, 10, 10);
        getFooter().add(Buttons.createCancelButton(this));
        return layout;
    }

    private void initGrid() {
        grid = new Grid<>();
        grid.setItems(scenarioController.getSourceRros());
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setSizeFull();
        addColumns();
        VaadinUtils.setGridProperties(grid, "exclude-details-by-rro-grid");
    }

    private void addColumns() {
        addColumn(Rightsholder::getAccountNumber, "table.column.source_rro_account_number", "accountNumber");
        addColumn(Rightsholder::getName, "table.column.source_rro_account_name", "name");
        grid.addComponentColumn(rightsholder -> {
            Button excludeButton = Buttons.createButton(ForeignUi.getMessage("button.exclude"));
            excludeButton.setId(Objects.nonNull(rightsholder.getAccountNumber())
                ? rightsholder.getAccountNumber().toString()
                : StringUtils.EMPTY);
            excludeButton.addClickListener(event -> {
                FasExcludeRightsholdersWindow window =
                    new FasExcludeRightsholdersWindow(rightsholder.getAccountNumber(), scenarioController);
                Windows.showModalWindow(window);
                ComponentUtil.addListener(window, ExcludeUsagesEvent.class, excludeUsagesEvent -> {
                    close();
                    scenarioController.onUsagesExcluded(excludeUsagesEvent);
                });
            });
            VaadinUtils.setButtonsAutoDisabled(excludeButton);
            return excludeButton;
        }).setWidth("100px")
            .setFlexGrow(0)
            .setSortable(false)
            .setResizable(true);
    }

    private void addColumn(ValueProvider<Rightsholder, ?> provider, String captionProperty, String sortProperty) {
        grid.addColumn(provider)
            .setHeader(ForeignUi.getMessage(captionProperty))
            .setSortable(true)
            .setSortProperty(sortProperty)
            .setResizable(true);
    }
}
