package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.ExcludeUsagesEvent;
import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludeUsagesListener;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasExcludePayeeController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasExcludePayeeFilterWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasExcludePayeeWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.CurrencyUtils;
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
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
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
public class FasExcludePayeeWidget extends Window implements IFasExcludePayeeWidget {

    private static final String STYLE_ALIGN_RIGHT = "v-align-right";

    private SearchWidget searchWidget;
    private IFasExcludePayeeController controller;
    private Grid<PayeeTotalHolder> payeesGrid;
    private ListDataProvider<PayeeTotalHolder> dataProvider;

    @Override
    public void refresh() {
        initDataProvider();
        performSearch();
    }

    @Override
    public void setController(IFasExcludePayeeController controller) {
        this.controller = controller;
    }

    @Override
    public String getSearchValue() {
        return searchWidget.getSearchValue();
    }

    @Override
    public void addListener(IExcludeUsagesListener listener) {
        addListener(ExcludeUsagesEvent.class, listener, IExcludeUsagesListener.EXCLUDE_DETAILS_HANDLER);
    }

    @Override
    @SuppressWarnings("unchecked")
    public FasExcludePayeeWidget init() {
        IFasExcludePayeeFilterWidget filterWidget = controller.getExcludePayeesFilterController().initWidget();
        filterWidget.addListener(FilterChangedEvent.class, controller, IFasExcludePayeeController.ON_FILTER_CHANGED);
        setWidth(1200, Unit.PIXELS);
        setHeight(500, Unit.PIXELS);
        setCaption(ForeignUi.getMessage("window.exclude.payee"));
        VaadinUtils.addComponentStyle(this, "exclude-details-by-payee-window");
        initGrid();
        searchWidget = new SearchWidget(this::performSearch);
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.scenario.search_widget.payee"));
        searchWidget.setWidth(75, Unit.PERCENTAGE);
        Button exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        OnDemandFileDownloader fileDownloader = new OnDemandFileDownloader(new CsvStreamSource(controller).getSource());
        fileDownloader.extend(exportButton);
        HorizontalLayout toolbar = new HorizontalLayout(exportButton, searchWidget);
        VaadinUtils.setMaxComponentsWidth(toolbar);
        toolbar.setComponentAlignment(exportButton, Alignment.BOTTOM_LEFT);
        toolbar.setComponentAlignment(searchWidget, Alignment.MIDDLE_CENTER);
        toolbar.setExpandRatio(searchWidget, 1f);
        toolbar.setMargin(new MarginInfo(false, true, false, true));
        HorizontalLayout buttonsLayout = createButtonsLayout();
        VerticalLayout mainLayout = new VerticalLayout(toolbar, payeesGrid, buttonsLayout);
        mainLayout.setMargin(new MarginInfo(true, true));
        mainLayout.setSizeFull();
        mainLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        mainLayout.setExpandRatio(payeesGrid, 1);
        HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
        splitPanel.addComponents(filterWidget, mainLayout);
        splitPanel.setSplitPosition(200, Unit.PIXELS);
        splitPanel.setLocked(true);
        setContent(splitPanel);
        return this;
    }

    @Override
    public Set<Long> getSelectedAccountNumbers() {
        return payeesGrid.getSelectedItems()
            .stream()
            .map(holder -> holder.getPayee().getAccountNumber())
            .collect(Collectors.toSet());
    }

    private void initGrid() {
        payeesGrid = new Grid<>();
        payeesGrid.setSelectionMode(SelectionMode.MULTI);
        payeesGrid.setSizeFull();
        initDataProvider();
        VaadinUtils.addComponentStyle(payeesGrid, "exclude-details-by-payee-grid");
        addColumns();
    }

    private void initDataProvider() {
        dataProvider = DataProvider.ofCollection(controller.getPayeeTotalHolders());
        payeesGrid.setDataProvider(dataProvider);
    }

    private void addColumns() {
        addColumn(holder -> holder.getPayee().getAccountNumber(), "table.column.payee_account_number",
            "payee.accountNumber");
        addColumn(holder -> holder.getPayee().getName(), "table.column.payee_name", "payee.name")
            .setComparator((SerializableComparator<PayeeTotalHolder>) (holder1, holder2) ->
                holder1.getPayee().getName().compareToIgnoreCase(holder2.getPayee().getName()));
        addAmountColumn(PayeeTotalHolder::getGrossTotal, "table.column.gross_amount_in_usd", "grossTotal");
        addAmountColumn(PayeeTotalHolder::getServiceFeeTotal, "table.column.service_fee_amount",
            "serviceFeeTotal");
        addAmountColumn(PayeeTotalHolder::getNetTotal, "table.column.net_amount_in_usd", "netTotal");
        payeesGrid.addColumn(holder -> holder.isPayeeParticipating() ? 'Y' : 'N')
            .setCaption(ForeignUi.getMessage("table.column.participating"))
            .setSortProperty("payeeParticipating");
        payeesGrid.getColumns().forEach(column -> column.setSortable(true));
    }

    private Grid.Column<PayeeTotalHolder, ?> addColumn(ValueProvider<PayeeTotalHolder, ?> provider,
                                                       String captionProperty, String sortProperty) {
        return payeesGrid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sortProperty);
    }

    private void addAmountColumn(Function<PayeeTotalHolder, BigDecimal> function, String captionProperty,
                                 String sortProperty) {
        payeesGrid.addColumn(holder -> CurrencyUtils.format(function.apply(holder), null))
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sortProperty)
            .setComparator((SerializableComparator<PayeeTotalHolder>) (holder1, holder2) ->
                function.apply(holder1).compareTo(function.apply(holder2)))
            .setStyleGenerator(item -> STYLE_ALIGN_RIGHT);
    }

    private HorizontalLayout createButtonsLayout() {
        Button excludeDetails = Buttons.createButton(ForeignUi.getMessage("button.exclude_details"));
        addClickListener(excludeDetails, "message.confirm.exclude.payees",
            (accountNumbers, reason) -> controller.excludeDetails(accountNumbers, reason));
        Button redesignateDetails = Buttons.createButton(ForeignUi.getMessage("button.redesignate_details"));
        addClickListener(redesignateDetails, "message.confirm.redesignate",
            (accountNumbers, reason) -> controller.redesignateDetails(accountNumbers, reason));
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> payeesGrid.getSelectionModel().deselectAll());
        VaadinUtils.setButtonsAutoDisabled(excludeDetails , redesignateDetails);
        return new HorizontalLayout(excludeDetails, redesignateDetails, clearButton, Buttons.createCloseButton(this));
    }

    private void addClickListener(Button button, String dialogMessageProperty,
                                  BiConsumer<Set<Long>, String> actionHandler) {
        button.addClickListener(event -> {
            Set<Long> selectedAccountNumbers = getSelectedAccountNumbers();
            if (CollectionUtils.isNotEmpty(selectedAccountNumbers)) {
                Set<Long> invalidPayees = controller.getAccountNumbersInvalidForExclude(selectedAccountNumbers);
                if (invalidPayees.isEmpty()) {
                    Windows.showConfirmDialogWithReason(
                        ForeignUi.getMessage("window.confirm"),
                        ForeignUi.getMessage(dialogMessageProperty),
                        ForeignUi.getMessage("button.yes"),
                        ForeignUi.getMessage("button.cancel"),
                        reason -> {
                            actionHandler.accept(selectedAccountNumbers, reason);
                            fireEvent(new ExcludeUsagesEvent(this));
                            this.close();
                        }, new StringLengthValidator(ForeignUi.getMessage("field.error.length", 1024), 0, 1024));
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
        String searchValue = searchWidget.getSearchValue();
        if (StringUtils.isNotBlank(searchValue)) {
            dataProvider.setFilter(holder ->
                StringUtils.containsIgnoreCase(holder.getPayee().getAccountNumber().toString(), searchValue)
                    || StringUtils.containsIgnoreCase(holder.getPayee().getName(), searchValue));
        }
    }
}
