package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.ui.common.utils.IDateFormatter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Modal window that provides functionality for viewing and deleting {@link FundPool}s.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 09/25/2020
 *
 * @author Aliaksandr Liakh
 */
public class ViewSalFundPoolWindow extends Window implements SearchWidget.ISearchController, IDateFormatter {

    private static final int SCALE_1 = 1;

    private final SearchWidget searchWidget;
    private final ISalUsageController controller;
    private Grid<FundPool> grid;
    private Button deleteButton;

    /**
     * Constructor.
     *
     * @param controller {@link ISalUsageController}
     */
    public ViewSalFundPoolWindow(ISalUsageController controller) {
        this.controller = controller;
        setWidth(900, Unit.PIXELS);
        setHeight(550, Unit.PIXELS);
        searchWidget = new SearchWidget(this);
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.view_fund_pool.search"));
        initGrid();
        HorizontalLayout buttonsLayout = initButtons();
        initMediator();
        VerticalLayout layout = new VerticalLayout(searchWidget, grid, buttonsLayout);
        layout.setSizeFull();
        layout.setExpandRatio(grid, 1);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        setContent(layout);
        setCaption(ForeignUi.getMessage("window.view_fund_pool"));
        VaadinUtils.addComponentStyle(this, "view-sal-fund-pool-window");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void performSearch() {
        ListDataProvider<FundPool> dataProvider = (ListDataProvider<FundPool>) grid.getDataProvider();
        dataProvider.clearFilters();
        String search = searchWidget.getSearchValue();
        if (StringUtils.isNotBlank(search)) {
            dataProvider.setFilter(fundPool -> StringUtils.containsIgnoreCase(fundPool.getName(), search));
        }
        // Gets round an issue when Vaadin do not recalculates columns widths once vertical scroll is disappeared
        grid.recalculateColumnWidths();
    }

    private void initMediator() {
        ViewSalFundPoolMediator mediator = new ViewSalFundPoolMediator();
        mediator.setDeleteButton(deleteButton);
        mediator.applyPermissions();
    }

    private HorizontalLayout initButtons() {
        Button closeButton = Buttons.createCloseButton(this);
        deleteButton = Buttons.createButton(ForeignUi.getMessage("button.delete"));
        deleteButton.addClickListener(event -> {
            grid.getSelectedItems().stream().findFirst().ifPresent(selectedFundPool -> {
                String scenarioName = controller.getScenarioNameAssociatedWithFundPool(selectedFundPool.getId());
                if (Objects.isNull(scenarioName)) {
                    Windows.showConfirmDialog(
                        ForeignUi.getMessage("message.confirm.delete_action", selectedFundPool.getName(), "fund pool"),
                        () -> {
                            controller.deleteFundPool(selectedFundPool);
                            grid.setItems(controller.getFundPools());
                        });
                } else {
                    Windows.showNotificationWindow(
                        ForeignUi.getMessage("message.error.delete_action", "Fund pool", "scenario",
                            ' ' + scenarioName));
                }
            });
        });
        deleteButton.setEnabled(false);
        Button exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        OnDemandFileDownloader exportScenarioFileDownloader = new OnDemandFileDownloader(
            controller.getExportFundPoolsStreamSource().getSource());
        exportScenarioFileDownloader.extend(exportButton);
        VaadinUtils.setButtonsAutoDisabled(exportButton, deleteButton, closeButton);
        HorizontalLayout layout = new HorizontalLayout(exportButton, deleteButton, closeButton);
        layout.setSpacing(true);
        VaadinUtils.addComponentStyle(layout, "view-sal-fund-pool-buttons");
        return layout;
    }

    private void initGrid() {
        grid = new Grid<>();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setItems(controller.getFundPools());
        grid.addSelectionListener(event ->
            deleteButton.setEnabled(CollectionUtils.isNotEmpty(event.getAllSelectedItems()))
        );
        grid.setSizeFull();
        addGridColumns();
        VaadinUtils.addComponentStyle(grid, "view-sal-fund-pool-grid");
    }

    private void addGridColumns() {
        grid.addColumn(FundPool::getName)
            .setCaption(ForeignUi.getMessage("table.column.fund_pool_name"))
            .setComparator((fundPool1, fundPool2) -> fundPool1.getName().compareToIgnoreCase(fundPool2.getName()))
            .setExpandRatio(1);
        grid.addColumn(fundPool -> fundPool.getSalFields().getDateReceived()
            .format(DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT)))
            .setCaption(ForeignUi.getMessage("table.column.date_received"))
            .setComparator((fundPool1, fundPool2) -> fundPool1.getSalFields().getDateReceived()
                .compareTo(fundPool2.getSalFields().getDateReceived()))
            .setExpandRatio(0);
        grid.addColumn(fundPool -> fundPool.getSalFields().getAssessmentName())
            .setCaption(ForeignUi.getMessage("table.column.assessment_name"))
            .setComparator((fundPool1, fundPool2) -> fundPool1.getSalFields().getAssessmentName()
                .compareToIgnoreCase(fundPool2.getSalFields().getAssessmentName()))
            .setWidth(180);
        grid.addColumn(fundPool -> fundPool.getSalFields().getLicenseeAccountNumber())
            .setCaption(ForeignUi.getMessage("table.column.licensee_account_number"))
            .setWidth(150);
        grid.addColumn(fundPool -> fundPool.getSalFields().getLicenseeName())
            .setCaption(ForeignUi.getMessage("table.column.licensee_name"))
            .setComparator((fundPool1, fundPool2) -> fundPool1.getSalFields().getLicenseeName()
                .compareToIgnoreCase(fundPool2.getSalFields().getLicenseeName()))
            .setWidth(300);
        grid.addColumn(fundPool -> CurrencyUtils.format(fundPool.getSalFields().getGrossAmount(), null))
            .setCaption(ForeignUi.getMessage("table.column.gross_amount"))
            .setComparator((fundPool1, fundPool2) -> fundPool1.getSalFields().getGrossAmount()
                .compareTo(fundPool2.getSalFields().getGrossAmount()))
            .setWidth(170);
        grid.addColumn(fundPool -> getPercentColumnAsString(fundPool.getSalFields().getServiceFee()))
            .setCaption(ForeignUi.getMessage("table.column.service_fee"))
            .setWidth(115);
        grid.addColumn(fundPool -> getPercentColumnAsString(fundPool.getSalFields().getItemBankSplitPercent()))
            .setCaption(ForeignUi.getMessage("table.column.item_bank_split_percent"))
            .setWidth(150);
        grid.addColumn(fundPool -> fundPool.getSalFields().getGradeKto5NumberOfStudents())
            .setCaption(ForeignUi.getMessage("table.column.grade_K_5_number_of_students"))
            .setWidth(190);
        grid.addColumn(fundPool -> fundPool.getSalFields().getGrade6to8NumberOfStudents())
            .setCaption(ForeignUi.getMessage("table.column.grade_6_8_number_of_students"))
            .setWidth(190);
        grid.addColumn(fundPool -> fundPool.getSalFields().getGrade9to12NumberOfStudents())
            .setCaption(ForeignUi.getMessage("table.column.grade_9_12_number_of_students"))
            .setWidth(190);
        grid.addColumn(fundPool -> CurrencyUtils.format(fundPool.getSalFields().getItemBankGrossAmount(), null))
            .setCaption(ForeignUi.getMessage("table.column.item_bank_gross_amount"))
            .setComparator((fundPool1, fundPool2) -> fundPool1.getSalFields().getItemBankGrossAmount()
                .compareTo(fundPool2.getSalFields().getItemBankGrossAmount()))
            .setWidth(170);
        grid.addColumn(fundPool -> CurrencyUtils.format(fundPool.getSalFields().getGradeKto5GrossAmount(), null))
            .setCaption(ForeignUi.getMessage("table.column.grade_K_5_gross_amount"))
            .setComparator((fundPool1, fundPool2) -> fundPool1.getSalFields().getGradeKto5GrossAmount()
                .compareTo(fundPool2.getSalFields().getGradeKto5GrossAmount()))
            .setWidth(170);
        grid.addColumn(fundPool -> CurrencyUtils.format(fundPool.getSalFields().getGrade6to8GrossAmount(), null))
            .setCaption(ForeignUi.getMessage("table.column.grade_6_8_gross_amount"))
            .setComparator((fundPool1, fundPool2) -> fundPool1.getSalFields().getGrade6to8GrossAmount()
                .compareTo(fundPool2.getSalFields().getGrade6to8GrossAmount()))
            .setWidth(170);
        grid.addColumn(fundPool -> CurrencyUtils.format(fundPool.getSalFields().getGrade9to12GrossAmount(), null))
            .setCaption(ForeignUi.getMessage("table.column.grade_9_12_gross_amount"))
            .setComparator((fundPool1, fundPool2) -> fundPool1.getSalFields().getGrade9to12GrossAmount()
                .compareTo(fundPool2.getSalFields().getGrade9to12GrossAmount()))
            .setWidth(170);
        grid.addColumn(StoredEntity::getCreateUser)
            .setCaption(ForeignUi.getMessage("table.column.created_by"))
            .setComparator((fundPool1, fundPool2) -> fundPool1.getCreateUser().compareTo(fundPool2.getCreateUser()))
            .setWidth(170);
        grid.addColumn(fundPool -> toLongFormat(fundPool.getCreateDate()))
            .setCaption(ForeignUi.getMessage("table.column.created_date"))
            .setComparator((fundPool1, fundPool2) -> fundPool1.getCreateDate().compareTo(fundPool2.getCreateDate()));
    }

    private String getPercentColumnAsString(BigDecimal value) {
        return Objects.nonNull(value)
            ? Objects.toString(value.multiply(new BigDecimal("100")).setScale(SCALE_1, BigDecimal.ROUND_HALF_UP))
            : StringUtils.EMPTY;
    }
}
