package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.UdmValueStatusEnum;
import com.copyright.rup.dist.foreign.ui.common.utils.BigDecimalUtils;
import com.copyright.rup.dist.foreign.ui.common.utils.BooleanUtils;
import com.copyright.rup.dist.foreign.ui.common.utils.IDateFormatter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.google.common.collect.ImmutableSet;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.components.grid.MultiSelectionModel.SelectAllCheckBoxVisibility;
import com.vaadin.ui.components.grid.MultiSelectionModelImpl;
import com.vaadin.ui.themes.ValoTheme;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Implementation of {@link IUdmValueWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 08/30/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmValueWidget extends HorizontalSplitPanel implements IUdmValueWidget, IDateFormatter {

    private static final long serialVersionUID = 1886776610579809855L;
    private static final String EMPTY_STYLE_NAME = "empty-values-grid";
    private static final String FOOTER_LABEL = "Values Count: %s";
    private static final Set<UdmValueStatusEnum> VALUE_STATUSES_ASSIGNEE_ALLOWED_FOR_RESEARCHER =
        ImmutableSet.of(UdmValueStatusEnum.NEW, UdmValueStatusEnum.RSCHD_IN_THE_PREV_PERIOD);

    private final String userName = RupContextUtils.getUserName();
    private final boolean hasResearcherPermission = ForeignSecurityUtils.hasResearcherPermission();
    private final boolean hasManagerPermission = ForeignSecurityUtils.hasManagerPermission();
    private final boolean hasSpecialistPermission = ForeignSecurityUtils.hasSpecialistPermission();

    private IUdmValueController controller;
    private Grid<UdmValueDto> udmValuesGrid;
    private Button populateButton;
    private MenuBar assignmentMenuBar;
    private MenuBar.MenuItem assignItem;
    private MenuBar.MenuItem unassignItem;
    private Button calculateProxyValuesButton;
    private Button publishButton;
    private DataProvider<UdmValueDto, Void> dataProvider;
    private MultiSelectionModelImpl<UdmValueDto> gridSelectionModel;
    private Set<UdmValueDto> selectedUdmValues;
    private boolean isAllSelected;

    @Override
    @SuppressWarnings("unchecked")
    public IUdmValueWidget init() {
        setSplitPosition(270, Unit.PIXELS);
        setFirstComponent(controller.initValuesFilterWidget());
        setSecondComponent(initValuesLayout());
        setLocked(true);
        super.setSizeFull();
        return this;
    }

    @Override
    public void setController(IUdmValueController controller) {
        this.controller = controller;
    }

    @Override
    public IMediator initMediator() {
        UdmValueMediator mediator = new UdmValueMediator();
        mediator.setPopulateButton(populateButton);
        mediator.setAssignmentMenuBar(assignmentMenuBar);
        mediator.setCalculateProxyValuesButton(calculateProxyValuesButton);
        mediator.setPublishButton(publishButton);
        return mediator;
    }

    @Override
    public void refresh() {
        udmValuesGrid.deselectAll();
        dataProvider.refreshAll();
    }

    private VerticalLayout initValuesLayout() {
        initValuesGrid();
        initAssignmentMenuBar();
        VerticalLayout layout = new VerticalLayout(initButtonsLayout(), udmValuesGrid);
        layout.setSizeFull();
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.setExpandRatio(udmValuesGrid, 1);
        VaadinUtils.addComponentStyle(layout, "udm-value-layout");
        return layout;
    }

    private void initAssignmentMenuBar() {
        assignmentMenuBar = new MenuBar();
        MenuBar.MenuItem item = assignmentMenuBar.addItem(ForeignUi.getMessage("menu.caption.assignment"), null, null);
        initAssignMenuItem(item);
        initUnassignMenuItem(item);
        VaadinUtils.addComponentStyle(assignmentMenuBar, "v-menubar-df");
    }

    private void initAssignMenuItem(MenuBar.MenuItem item) {
        assignItem = item.addItem(ForeignUi.getMessage("menu.item.assign"), null,
            selectedItem -> {
                if (hasResearcherPermission && !isAssignmentAllowedForResearcher(udmValuesGrid.getSelectedItems())) {
                    Windows.showNotificationWindow(
                        ForeignUi.getMessage("message.error.value.assignment_forbidden_for_researcher",
                            VALUE_STATUSES_ASSIGNEE_ALLOWED_FOR_RESEARCHER
                                .stream()
                                .map(UdmValueStatusEnum::name)
                                .collect(Collectors.joining(", "))));
                } else {
                    Set<UdmValueDto> udmValues = udmValuesGrid.getSelectedItems();
                    int valuesCount = udmValues.size();
                    Windows.showConfirmDialog(ForeignUi.getMessage("message.confirm.value.assign", valuesCount),
                        () -> {
                            controller.assignValues(udmValues);
                            refresh();
                            Windows.showNotificationWindow(
                                ForeignUi.getMessage("message.notification.value.assignment_completed", valuesCount));
                        });
                }
            });
        assignItem.setEnabled(false);
    }

    private void initUnassignMenuItem(MenuBar.MenuItem item) {
        unassignItem = item.addItem(ForeignUi.getMessage("menu.item.unassign"), null,
            selectedItem -> {
                boolean isUnassignmentAllowed = udmValuesGrid.getSelectedItems()
                    .stream()
                    .allMatch(udmValueDto -> userName.equals(udmValueDto.getAssignee()));
                if (isUnassignmentAllowed) {
                    Set<UdmValueDto> udmValues = udmValuesGrid.getSelectedItems();
                    int valuesCount = udmValues.size();
                    Windows.showConfirmDialog(ForeignUi.getMessage("message.confirm.value.unassign", valuesCount),
                        () -> {
                            controller.unassignValues(udmValues);
                            refresh();
                            Windows.showNotificationWindow(
                                ForeignUi.getMessage("message.notification.value.unassignment_completed", valuesCount));
                        });
                } else {
                    Windows.showNotificationWindow(ForeignUi.getMessage("message.error.value.unassign"));
                }
            });
        unassignItem.setEnabled(false);
    }

    private HorizontalLayout initButtonsLayout() {
        Button exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        OnDemandFileDownloader fileDownloader =
            new OnDemandFileDownloader(controller.getExportValuesStreamSource().getSource());
        fileDownloader.extend(exportButton);
        populateButton = Buttons.createButton(ForeignUi.getMessage("button.populate_value_batch"));
        populateButton.addClickListener(event -> Windows.showModalWindow(new UdmPopulateValueBatchWindow(controller)));
        calculateProxyValuesButton = Buttons.createButton(ForeignUi.getMessage("button.calculate_proxies"));
        calculateProxyValuesButton.addClickListener(event ->
            Windows.showModalWindow(new UdmCalculateProxyValuesWindow(controller, saveEvent -> refresh())));
        publishButton = Buttons.createButton(ForeignUi.getMessage("button.publish"));
        publishButton.addClickListener(event -> Windows.showModalWindow(new UdmPublishToBaselineWindow(controller,
            publishEvent -> refresh())));
        VaadinUtils.setButtonsAutoDisabled(populateButton, calculateProxyValuesButton, publishButton);
        HorizontalLayout layout = new HorizontalLayout(populateButton, assignmentMenuBar, calculateProxyValuesButton,
            publishButton, exportButton);
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(layout, "udm-value-buttons");
        return layout;
    }

    private boolean checkHasValuesAssignee(Set<UdmValueDto> values) {
        return values.stream().allMatch(value -> userName.equals(value.getAssignee()));
    }

    private void initValuesGrid() {
        dataProvider = LoadingIndicatorDataProvider.fromCallbacks(
            query -> controller.loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
            query -> {
                int size = controller.getBeansCount();
                if (isNotViewOnlyPermission()) {
                    switchSelectAllCheckBoxVisibility(size);
                }
                if (0 < size) {
                    udmValuesGrid.removeStyleName(EMPTY_STYLE_NAME);
                } else {
                    udmValuesGrid.addStyleName(EMPTY_STYLE_NAME);
                }
                udmValuesGrid.getFooterRow(0).getCell("valueId").setText(String.format(FOOTER_LABEL, size));
                return size;
            }, UdmValueDto::getId);
        udmValuesGrid = new Grid<>(dataProvider);
        addColumns();
        udmValuesGrid.setSizeFull();
        initSelectionMode();
        initValueWindowByDoubleClick();
        VaadinUtils.addComponentStyle(udmValuesGrid, "udm-value-grid");
    }

    private void addColumns() {
        FooterRow footer = udmValuesGrid.appendFooterRow();
        udmValuesGrid.setFooterVisible(true);
        Column<UdmValueDto, ?> column = udmValuesGrid.addComponentColumn(udmValue -> {
                String udmValueId = udmValue.getId();
                var button = Buttons.createButton(udmValueId);
                button.addStyleName(ValoTheme.BUTTON_LINK);
                button.addClickListener(event -> {
                    controller.showUdmValueHistory(udmValueId, closeEvent ->
                        restoreSelection(selectedUdmValues, isAllSelected));
                    highlightSelectedValue(udmValue);
                });
                return button;
            })
            .setCaption(ForeignUi.getMessage("table.column.value_id"))
            .setId("valueId")
            .setSortProperty("valueId")
            .setWidth(250);
        footer.getCell(column).setText(String.format(FOOTER_LABEL, 0));
        footer.join(
            addColumn(UdmValueDto::getValuePeriod, "table.column.value_period", "valuePeriod", 150),
            addColumn(UdmValueDto::getStatus, "table.column.status", "status", 100),
            addColumn(UdmValueDto::getAssignee, "table.column.assignee", "assignee", 100),
            addColumn(UdmValueDto::getRhAccountNumber, "table.column.rh_account_number", "rhAccountNumber", 150),
            addColumn(UdmValueDto::getRhName, "table.column.rh_account_name", "rhName", 300),
            addColumn(UdmValueDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", 100),
            addColumn(UdmValueDto::getSystemTitle, "table.column.system_title", "systemTitle", 300),
            addColumn(UdmValueDto::getSystemStandardNumber, "table.column.system_standard_number",
                "systemStandardNumber", 190),
            addColumn(UdmValueDto::getLastValuePeriod, "table.column.last_value_period", "lastValuePeriod", 150),
            addColumn(UdmValueDto::getLastPubType, "table.column.last_pub_type", "lastPubType", 150),
            addColumn(value -> Objects.nonNull(value.getPublicationType())
                ? value.getPublicationType().getName()
                : StringUtils.EMPTY, "table.column.publication_type", "publicationType", 150),
            addBigDecimalColumn(UdmValueDto::getLastPriceInUsd, "table.column.last_price_in_usd", "lastPriceInUsd",
                120),
            addBooleanColumn(UdmValueDto::isLastPriceFlag, "table.column.last_price_flag", "lastPriceFlag", 120),
            addColumn(UdmValueDto::getLastPriceSource, "table.column.last_price_source", "lastPriceSource", 150),
            addColumn(UdmValueDto::getPriceSource, "table.column.price_source", "priceSource", 150),
            addColumn(UdmValueDto::getLastPriceComment, "table.column.last_price_comment", "lastPriceComment", 150),
            addBigDecimalColumn(UdmValueDto::getPrice, "table.column.price", "price", 100),
            addColumn(UdmValueDto::getCurrency, "table.column.currency", "currency", 100),
            addColumn(UdmValueDto::getPriceType, "table.column.price_type", "priceType", 100),
            addColumn(UdmValueDto::getPriceAccessType, "table.column.price_access_type", "priceAccessType", 150),
            addColumn(UdmValueDto::getPriceYear, "table.column.price_year", "priceYear", 100),
            addColumn(UdmValueDto::getPriceComment, "table.column.price_comment", "priceComment", 120),
            addBigDecimalColumn(UdmValueDto::getPriceInUsd, "table.column.price_in_usd", "priceInUsd", 120),
            addBooleanColumn(UdmValueDto::isPriceFlag, "table.column.price_flag", "priceFlag", 100),
            addBigDecimalColumn(UdmValueDto::getCurrencyExchangeRate, "table.column.currency_exchange_rate",
                "currencyExchangeRate", 200),
            addColumn(value -> toShortFormat(value.getCurrencyExchangeRateDate()),
                "table.column.currency_exchange_rate_date", "currencyExchangeRateDate", 200),
            addBigDecimalColumn(UdmValueDto::getLastContent, "table.column.last_content", "lastContent", 100),
            addBooleanColumn(UdmValueDto::isLastContentFlag, "table.column.last_content_flag", "lastContentFlag", 130),
            addColumn(UdmValueDto::getLastContentSource, "table.column.last_content_source", "lastContentSource", 150),
            addColumn(UdmValueDto::getContentSource, "table.column.content_source", "contentSource", 150),
            addColumn(UdmValueDto::getLastContentComment, "table.column.last_content_comment", "lastContentComment",
                200),
            addBigDecimalColumn(UdmValueDto::getContent, "table.column.content", "content", 100),
            addColumn(UdmValueDto::getContentComment, "table.column.content_comment", "contentComment", 200),
            addBooleanColumn(UdmValueDto::isContentFlag, "table.column.content_flag", "contentFlag", 100),
            addBigDecimalColumn(UdmValueDto::getContentUnitPrice, "table.column.content_unit_price", "contentUnitPrice",
                200),
            addBooleanColumn(UdmValueDto::isContentUnitPriceFlag, "table.column.content_unit_price_flag",
                "contentUnitPriceFlag", 90),
            addColumn(UdmValueDto::getLastComment, "table.column.last_comment", "lastComment", 200),
            addColumn(UdmValueDto::getComment, "table.column.comment", "comment", 200),
            addColumn(UdmValueDto::getUpdateUser, "table.column.updated_by", "updateUser", 150),
            addColumn(value -> toShortFormat(value.getUpdateDate()), "table.column.updated_date", "updateDate",
                110));
    }

    private Column<UdmValueDto, ?> addColumn(ValueProvider<UdmValueDto, ?> valueProvider, String captionProperty,
                                             String columnId, double width) {
        return udmValuesGrid.addColumn(valueProvider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true)
            .setSortProperty(columnId)
            .setHidable(true)
            .setWidth(width);
    }

    private Column<UdmValueDto, ?> addBigDecimalColumn(Function<UdmValueDto, BigDecimal> function,
                                                       String captionProperty, String columnId, double width) {
        return udmValuesGrid.addColumn(value -> BigDecimalUtils.formatCurrencyForGrid(function.apply(value)))
            .setStyleGenerator(item -> "v-align-right")
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true)
            .setSortProperty(columnId)
            .setHidable(true)
            .setWidth(width);
    }

    private Column<UdmValueDto, ?> addBooleanColumn(ValueProvider<UdmValueDto, Boolean> valueProvider,
                                                    String captionProperty, String columnId, double width) {
        return udmValuesGrid.addColumn(value -> BooleanUtils.toYNString(valueProvider.apply(value)))
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true)
            .setSortProperty(columnId)
            .setHidable(true)
            .setWidth(width);
    }

    private void initSelectionMode() {
        if (isNotViewOnlyPermission()) {
            gridSelectionModel =
                (MultiSelectionModelImpl<UdmValueDto>) udmValuesGrid.setSelectionMode(Grid.SelectionMode.MULTI);
            udmValuesGrid.addSelectionListener(event -> {
                Set<UdmValueDto> valueDtos = event.getAllSelectedItems();
                boolean isSelected = CollectionUtils.isNotEmpty(valueDtos);
                assignItem.setEnabled(isSelected);
                unassignItem.setEnabled(isSelected);
            });
        } else {
            udmValuesGrid.setSelectionMode(SelectionMode.SINGLE);
        }
    }

    private boolean isNotViewOnlyPermission() {
        return hasSpecialistPermission || hasManagerPermission || hasResearcherPermission;
    }

    private void switchSelectAllCheckBoxVisibility(int beansCount) {
        gridSelectionModel.setSelectAllCheckBoxVisibility(
            0 == beansCount || beansCount > controller.getUdmRecordThreshold()
                ? SelectAllCheckBoxVisibility.HIDDEN
                : SelectAllCheckBoxVisibility.VISIBLE);
        gridSelectionModel.beforeClientResponse(false);
    }

    private void initValueWindowByDoubleClick() {
        udmValuesGrid.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                UdmValueDto udmValueDto = event.getItem();
                if (isNotViewOnlyPermission()) {
                    initValueWindow(udmValueDto);
                } else {
                    UdmViewValueWindow viewWindow = new UdmViewValueWindow(controller, udmValueDto);
                    viewWindow.addCloseListener(closeEvent -> udmValuesGrid.deselect(udmValueDto));
                    Windows.showModalWindow(viewWindow);
                }
                highlightSelectedValue(udmValueDto);
            }
        });
    }

    private void initValueWindow(UdmValueDto selectedValue) {
        if (hasResearcherPermission) {
            initValueWindowForResearcher(selectedValue);
        } else {
            showEditWindow(selectedValue);
        }
    }

    private void initValueWindowForResearcher(UdmValueDto selectedValue) {
        if (checkHasValuesAssignee(Set.of(selectedValue))) {
            showEditWindow(selectedValue);
        } else {
            UdmViewValueWindow viewWindow = new UdmViewValueWindow(controller, selectedValue);
            viewWindow.addCloseListener(closeEvent -> restoreSelection(selectedUdmValues, isAllSelected));
            Windows.showModalWindow(viewWindow);
        }
    }

    private void showEditWindow(UdmValueDto selectedValue) {
        UdmEditValueWindow editWindow =
            new UdmEditValueWindow(controller, selectedValue, saveEvent -> refresh(), hasSpecialistPermission);
        editWindow.addCloseListener(closeEvent -> restoreSelection(selectedUdmValues, isAllSelected));
        Windows.showModalWindow(editWindow);
    }

    private boolean isAssignmentAllowedForResearcher(Set<UdmValueDto> udmValueDtos) {
        return udmValueDtos.stream()
            .allMatch(udmValueDto -> VALUE_STATUSES_ASSIGNEE_ALLOWED_FOR_RESEARCHER.contains(udmValueDto.getStatus()));
    }

    /**
     * Hides current value selection and selects value for which view window was opened.
     *
     * @param selectedValue value to select
     */
    private void highlightSelectedValue(UdmValueDto selectedValue) {
        selectedUdmValues = udmValuesGrid.getSelectedItems();
        isAllSelected = Objects.nonNull(gridSelectionModel) && gridSelectionModel.isAllSelected();
        udmValuesGrid.deselectAll();
        udmValuesGrid.select(selectedValue);
    }

    /**
     * Restores previous value selection. Removes selection of value for which view window was opened.
     *
     * @param valuesToSelect      set of values to select
     * @param isAllValuesSelected {@code true} if all values are selected, {@code false} otherwise
     */
    private void restoreSelection(Set<UdmValueDto> valuesToSelect, boolean isAllValuesSelected) {
        selectedUdmValues = null;
        isAllSelected = false;
        if (Objects.nonNull(gridSelectionModel) && isAllValuesSelected) {
            gridSelectionModel.selectAll();
        } else {
            udmValuesGrid.deselectAll();
            valuesToSelect.forEach(udmValueDto -> udmValuesGrid.select(udmValueDto));
        }
    }
}
