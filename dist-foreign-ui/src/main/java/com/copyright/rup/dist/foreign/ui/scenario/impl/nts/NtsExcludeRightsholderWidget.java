package com.copyright.rup.dist.foreign.ui.scenario.impl.nts;

import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.ExcludeUsagesEvent;
import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludeUsagesListener;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsExcludeRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsExcludeRightsholderWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.SerializableComparator;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * Represents window with ability to exclude details by Rightsholder.
 *
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 08/03/20
 *
 * @author Anton Azarenka
 */
public class NtsExcludeRightsholderWidget extends Window implements INtsExcludeRightsholderWidget {

    private static final long serialVersionUID = -3165943003586100415L;

    private SearchWidget searchWidget;
    private INtsExcludeRightsholderController controller;
    private Grid<RightsholderPayeePair> rightsholderPayeePairGrid;
    private ListDataProvider<RightsholderPayeePair> dataProvider;

    @Override
    public String getSearchValue() {
        return searchWidget.getSearchValue();
    }

    @Override
    public void addListener(IExcludeUsagesListener listener) {
        addListener(ExcludeUsagesEvent.class, listener, IExcludeUsagesListener.EXCLUDE_DETAILS_HANDLER);
    }

    @Override
    public Set<Long> getSelectedAccountNumbers() {
        return rightsholderPayeePairGrid.getSelectedItems()
            .stream()
            .map(holder -> holder.getRightsholder().getAccountNumber())
            .collect(Collectors.toSet());
    }

    @Override
    public void refresh() {
        initDataProvider();
        performSearch();
    }

    @Override
    @SuppressWarnings("unchecked")
    public NtsExcludeRightsholderWidget init() {
        setWidth(800, Unit.PIXELS);
        setHeight(500, Unit.PIXELS);
        setCaption(ForeignUi.getMessage("window.exclude.rightsholder"));
        VaadinUtils.addComponentStyle(this, "exclude-details-by-rightsholder-window");
        initGrid();
        searchWidget = new SearchWidget(this::performSearch);
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.scenario.search_widget.rh_payee"));
        searchWidget.setWidth(75, Unit.PERCENTAGE);
        HorizontalLayout toolbar = new HorizontalLayout(searchWidget);
        VaadinUtils.setMaxComponentsWidth(toolbar);
        toolbar.setComponentAlignment(searchWidget, Alignment.MIDDLE_CENTER);
        toolbar.setExpandRatio(searchWidget, 1f);
        toolbar.setMargin(new MarginInfo(false, true, false, true));
        HorizontalLayout buttonsLayout = createButtonsLayout();
        VerticalLayout mainLayout = new VerticalLayout(toolbar, rightsholderPayeePairGrid, buttonsLayout);
        mainLayout.setMargin(new MarginInfo(true, true));
        mainLayout.setSizeFull();
        mainLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        mainLayout.setExpandRatio(rightsholderPayeePairGrid, 1);
        setContent(mainLayout);
        return this;
    }

    @Override
    public void setController(INtsExcludeRightsholderController controller) {
        this.controller = controller;
    }

    private void initGrid() {
        rightsholderPayeePairGrid = new Grid<>();
        rightsholderPayeePairGrid.setSelectionMode(SelectionMode.MULTI);
        rightsholderPayeePairGrid.setSizeFull();
        initDataProvider();
        VaadinUtils.addComponentStyle(rightsholderPayeePairGrid, "exclude-details-by-rightsholder-grid");
        addColumns();
    }

    private HorizontalLayout createButtonsLayout() {
        Button excludeDetails = Buttons.createButton(ForeignUi.getMessage("button.exclude_details"));
        addClickListener(excludeDetails,
            (accountNumbers, reason) -> controller.excludeDetails(accountNumbers, reason));
        VaadinUtils.setButtonsAutoDisabled(excludeDetails);
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> rightsholderPayeePairGrid.getSelectionModel().deselectAll());
        return new HorizontalLayout(excludeDetails, clearButton, Buttons.createCloseButton(this));
    }

    private void addClickListener(Button button, BiConsumer<Set<Long>, String> actionHandler) {
        button.addClickListener(event -> {
            Set<Long> selectedAccountNumbers = getSelectedAccountNumbers();
            if (CollectionUtils.isNotEmpty(selectedAccountNumbers)) {
                Windows.showConfirmDialogWithReason(
                    ForeignUi.getMessage("window.confirm"),
                    ForeignUi.getMessage("message.confirm.exclude.rightsholders"),
                    ForeignUi.getMessage("button.yes"),
                    ForeignUi.getMessage("button.cancel"),
                    reason -> {
                        actionHandler.accept(selectedAccountNumbers, reason);
                        fireEvent(new ExcludeUsagesEvent(this));
                        this.close();
                    }, new StringLengthValidator(ForeignUi.getMessage("field.error.empty.length", 1024), 1, 1024));
            } else {
                Windows.showNotificationWindow(ForeignUi.getMessage("message.exclude_rro.empty"));
            }
        });
    }

    private void addColumns() {
        addColumn(holder -> holder.getRightsholder().getAccountNumber(), "table.column.rh_account_number",
            "rightsholder.accountNumber");
        addColumn(holder -> holder.getRightsholder().getName(), "table.column.rh_account_name",
            "rightsholder.name")
            .setComparator((SerializableComparator<RightsholderPayeePair>) (holder1, holder2) ->
                holder1.getRightsholder().getName().compareToIgnoreCase(holder2.getRightsholder().getName()));
        addColumn(holder -> holder.getPayee().getAccountNumber(), "table.column.payee_account_number",
            "payee.accountNumber");
        addColumn(holder -> holder.getPayee().getName(), "table.column.payee_name", "payee.name")
            .setComparator((SerializableComparator<RightsholderPayeePair>) (holder1, holder2) ->
                holder1.getPayee().getName().compareToIgnoreCase(holder2.getPayee().getName()));
    }

    private Grid.Column<RightsholderPayeePair, ?> addColumn(ValueProvider<RightsholderPayeePair, ?> provider,
                                                            String captionProperty, String sortProperty) {
        return rightsholderPayeePairGrid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sortProperty);
    }

    private void initDataProvider() {
        dataProvider = DataProvider.ofCollection(controller.getRightsholderPayeePairs());
        rightsholderPayeePairGrid.setDataProvider(dataProvider);
    }

    private void performSearch() {
        dataProvider.clearFilters();
        String searchValue = searchWidget.getSearchValue();
        if (StringUtils.isNotBlank(searchValue)) {
            dataProvider.setFilter(pair ->
                StringUtils.containsIgnoreCase(pair.getPayee().getAccountNumber().toString(), searchValue)
                    || StringUtils.containsIgnoreCase(pair.getPayee().getName(), searchValue)
                    || StringUtils.containsIgnoreCase(pair.getRightsholder().getAccountNumber().toString(), searchValue)
                    || StringUtils.containsIgnoreCase(pair.getRightsholder().getName(), searchValue));
        }
    }
}
