package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.scenario.api.ExcludeUsagesEvent;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasExcludePayeeController;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasExcludePayeeWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.ConfirmWindows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.CurrencyUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.function.ValueProvider;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Represents window with ability to exclude details by Payee.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/2019
 *
 * @author Uladzislau_Shalamitski
 */
public class FasExcludePayeeWidget extends CommonDialog implements IFasExcludePayeeWidget {

    private static final long serialVersionUID = -7110342271534547788L;
    private static final String STYLE_ALIGN_RIGHT = "v-align-right";

    private SearchWidget searchWidget;
    private IFasExcludePayeeController controller;
    private Grid<PayeeTotalHolder> payeesGrid;
    private ListDataProvider<PayeeTotalHolder> dataProvider;

    @Override
    public void setController(IFasExcludePayeeController controller) {
        this.controller = controller;
    }

    @Override
    public void refresh() {
        initDataProvider();
        performSearch();
    }

    @Override
    public String getSearchValue() {
        return searchWidget.getSearchValue();
    }

    @Override
    public Set<Long> getSelectedAccountNumbers() {
        return payeesGrid.getSelectedItems()
            .stream()
            .map(holder -> holder.getPayee().getAccountNumber())
            .collect(Collectors.toSet());
    }

    @Override
    @SuppressWarnings("unchecked")
    public FasExcludePayeeWidget init() {
        var filterWidget = controller.getExcludePayeesFilterController().initWidget();
        super.setWidth("1500px");
        super.setHeight("500px");
        super.setHeaderTitle(ForeignUi.getMessage("window.exclude.payee"));
        super.setModalWindowProperties("exclude-details-by-payee-window", true);
        super.getFooter().add(createButtonsLayout());
        initGrid();
        var toolbar = initToolbar();
        var mainLayout = new VerticalLayout(toolbar, payeesGrid);
        mainLayout.setSpacing(false);
        mainLayout.setPadding(false);
        VaadinUtils.setPadding(this, 0, 10, 0, 10);
        mainLayout.setSizeFull();
        var splitPanel = new SplitLayout();
        splitPanel.setSizeFull();
        splitPanel.addToPrimary((FasExcludePayeeFilterWidget) filterWidget);
        splitPanel.addToSecondary(mainLayout);
        splitPanel.setSplitterPosition(16);
        add(splitPanel);
        return this;
    }

    private HorizontalLayout initToolbar() {
        searchWidget = new SearchWidget(this::performSearch);
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.scenario.search_widget.payee"));
        searchWidget.setWidth("75%");
        var searchWidgetLayout = new HorizontalLayout(searchWidget);
        searchWidgetLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        searchWidgetLayout.setWidth("100%");
        var exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        var fileDownloader = new OnDemandFileDownloader(new CsvStreamSource(controller).getSource());
        fileDownloader.extend(exportButton);
        var toolbar = new HorizontalLayout(fileDownloader, searchWidgetLayout);
        toolbar.setWidth("100%");
        toolbar.setSpacing(false);
        VaadinUtils.setPadding(toolbar, 0, 10, 0, 5);
        return toolbar;
    }

    private void initGrid() {
        payeesGrid = new Grid<>();
        VaadinUtils.setGridProperties(payeesGrid, "exclude-details-by-payee-grid");
        payeesGrid.setSelectionMode(SelectionMode.MULTI);
        payeesGrid.setSizeFull();
        initDataProvider();
        addColumns();
    }

    private void initDataProvider() {
        dataProvider = DataProvider.ofCollection(controller.getPayeeTotalHolders());
        payeesGrid.setItems(dataProvider);
    }

    private void addColumns() {
        addColumn(holder -> holder.getPayee().getAccountNumber(), "table.column.payee_account_number",
            "payee.accountNumber");
        addColumn(holder -> holder.getPayee().getName(), "table.column.payee_name", "payee.name")
            .setComparator((holder1, holder2) ->
                holder1.getPayee().getName().compareToIgnoreCase(holder2.getPayee().getName()));
        addAmountColumn(PayeeTotalHolder::getGrossTotal, "table.column.gross_amount_in_usd", "grossTotal");
        addAmountColumn(PayeeTotalHolder::getServiceFeeTotal, "table.column.service_fee_amount",
            "serviceFeeTotal");
        addAmountColumn(PayeeTotalHolder::getNetTotal, "table.column.net_amount_in_usd", "netTotal");
        payeesGrid.addColumn(holder -> holder.isPayeeParticipating() ? 'Y' : 'N')
            .setHeader(ForeignUi.getMessage("table.column.participating"))
            .setSortProperty("payeeParticipating");
        payeesGrid.getColumns().forEach(column -> column.setSortable(true));
    }

    private Column<PayeeTotalHolder> addColumn(ValueProvider<PayeeTotalHolder, ?> provider,
                                               String captionProperty, String sortProperty) {
        return payeesGrid.addColumn(provider)
            .setHeader(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sortProperty);
    }

    private void addAmountColumn(Function<PayeeTotalHolder, BigDecimal> function, String captionProperty,
                                 String sortProperty) {
        payeesGrid.addColumn(holder -> CurrencyUtils.format(function.apply(holder), null))
            .setHeader(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sortProperty)
            .setComparator(Comparator.comparing(function))
            .setClassNameGenerator(item -> STYLE_ALIGN_RIGHT);
    }

    private HorizontalLayout createButtonsLayout() {
        var excludeDetails = Buttons.createButton(ForeignUi.getMessage("button.exclude_details"));
        addClickListener(excludeDetails, "message.confirm.exclude.payees",
            (accountNumbers, reason) -> controller.excludeDetails(accountNumbers, reason));
        var redesignateDetails = Buttons.createButton(ForeignUi.getMessage("button.redesignate_details"));
        addClickListener(redesignateDetails, "message.confirm.redesignate",
            (accountNumbers, reason) -> controller.redesignateDetails(accountNumbers, reason));
        var clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> payeesGrid.getSelectionModel().deselectAll());
        VaadinUtils.setButtonsAutoDisabled(excludeDetails, redesignateDetails);
        return new HorizontalLayout(excludeDetails, redesignateDetails, clearButton, Buttons.createCloseButton(this));
    }

    private void addClickListener(Button button, String dialogMessageProperty,
                                  BiConsumer<Set<Long>, String> actionHandler) {
        button.addClickListener(event -> {
            Set<Long> selectedAccountNumbers = getSelectedAccountNumbers();
            if (CollectionUtils.isNotEmpty(selectedAccountNumbers)) {
                Set<Long> invalidPayees = controller.getAccountNumbersInvalidForExclude(selectedAccountNumbers);
                if (invalidPayees.isEmpty()) {
                    ConfirmWindows.showConfirmDialogWithReason(
                        ForeignUi.getMessage("window.confirm"),
                        ForeignUi.getMessage(dialogMessageProperty),
                        ForeignUi.getMessage("button.yes"),
                        ForeignUi.getMessage("button.cancel"),
                        reason -> {
                            actionHandler.accept(selectedAccountNumbers, reason);
                            fireEvent(new ExcludeUsagesEvent(this));
                            this.close();
                        },
                        List.of(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 1024), 0, 1024)));
                } else {
                    Windows.showNotificationWindow(
                        ForeignUi.getMessage("message.exclude_payee.invalid_payees", invalidPayees));
                }
            } else {
                Windows.showNotificationWindow(ForeignUi.getMessage("message.exclude_payee.empty"));
            }
        });
    }

    private void performSearch() {
        dataProvider.clearFilters();
        var searchValue = searchWidget.getSearchValue();
        if (StringUtils.isNotBlank(searchValue)) {
            dataProvider.setFilter(holder ->
                StringUtils.containsIgnoreCase(holder.getPayee().getAccountNumber().toString(), searchValue)
                    || StringUtils.containsIgnoreCase(holder.getPayee().getName(), searchValue));
        }
    }
}
