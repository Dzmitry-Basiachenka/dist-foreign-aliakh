package com.copyright.rup.dist.foreign.vui.usage.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.vui.common.RightsholderFilterWidget;
import com.copyright.rup.dist.foreign.vui.common.UsageBatchFilterWidget;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.IFasNtsUsageFilterController;
import com.copyright.rup.dist.foreign.vui.usage.api.IFasNtsUsageFilterWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.CommonFilterWindow.IFilterSaveListener;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.LocalDateWidget;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Widget for filtering FAS, FAS2 and NTS usages.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/11/2017
 *
 * @author Mikita Hladkikh
 */
public class FasNtsUsageFilterWidget extends CommonUsageFilterWidget implements IFasNtsUsageFilterWidget {

    private static final List<UsageStatusEnum> FAS_FAS2_STATUSES = List.of(UsageStatusEnum.NEW,
        UsageStatusEnum.WORK_NOT_FOUND, UsageStatusEnum.WORK_RESEARCH, UsageStatusEnum.NTS_WITHDRAWN,
        UsageStatusEnum.TO_BE_DISTRIBUTED, UsageStatusEnum.WORK_FOUND, UsageStatusEnum.RH_NOT_FOUND,
        UsageStatusEnum.RH_FOUND, UsageStatusEnum.SENT_FOR_RA, UsageStatusEnum.ELIGIBLE);
    private static final List<UsageStatusEnum> NTS_STATUSES = List.of(UsageStatusEnum.WORK_FOUND,
        UsageStatusEnum.RH_FOUND, UsageStatusEnum.UNCLASSIFIED, UsageStatusEnum.ELIGIBLE,
        UsageStatusEnum.SCENARIO_EXCLUDED);
    private static final long serialVersionUID = -533018108072874988L;

    private final IFasNtsUsageFilterController controller;
    private LocalDateWidget paymentDateWidget;
    private ComboBox<Integer> fiscalYearComboBox;
    private ComboBox<UsageStatusEnum> statusComboBox;
    private RightsholderFilterWidget rightsholderFilterWidget;
    private UsageBatchFilterWidget usageBatchFilterWidget;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IFasNtsUsageFilterController}
     */
    public FasNtsUsageFilterWidget(IFasNtsUsageFilterController controller) {
        this.controller = controller;
    }

    @Override
    protected void refreshFilterValues() {
        fiscalYearComboBox.setItems(controller.getFiscalYears());
        List<UsageStatusEnum> statuses = getStatuses();
        statusComboBox.setItems(statuses);
    }

    @Override
    protected void clearFilterValues() {
        paymentDateWidget.clear();
        fiscalYearComboBox.clear();
        statusComboBox.clear();
        rightsholderFilterWidget.reset();
        usageBatchFilterWidget.reset();
    }

    @Override
    protected VerticalLayout initFiltersLayout() {
        initPaymentDateFilter();
        initFiscalYearFilter();
        initStatusFilter();
        return VaadinUtils.initCommonVerticalLayout(buildFiltersHeaderLabel(), buildUsageBatchFilter(),
            buildRroAccountNumberFilter(), paymentDateWidget, statusComboBox, fiscalYearComboBox);
    }

    @Override
    protected CommonUsageAppliedFilterWidget getAppliedFilterWidget() {
        return new FasNtsUsageAppliedFilterWidget(controller);
    }

    private HorizontalLayout buildUsageBatchFilter() {
        usageBatchFilterWidget = new UsageBatchFilterWidget(controller::getUsageBatches);
        usageBatchFilterWidget.addFilterSaveListener((IFilterSaveListener<UsageBatch>) saveEvent -> {
            getFilter().setUsageBatchesIds(
                saveEvent.getSelectedItemsIds().stream().map(UsageBatch::getId).collect(Collectors.toSet()));
            filterChanged();
        });
        VaadinUtils.addComponentStyle(usageBatchFilterWidget, "batches-filter");
        return usageBatchFilterWidget;
    }

    private HorizontalLayout buildRroAccountNumberFilter() {
        rightsholderFilterWidget = new RightsholderFilterWidget(
            ForeignUi.getMessage("label.rros"),
            ForeignUi.getMessage("prompt.rro"),
            ForeignUi.getMessage("message.error.rro_not_found"),
            controller::getRros);
        rightsholderFilterWidget.addFilterSaveListener((IFilterSaveListener<Rightsholder>) saveEvent -> {
            getFilter().setRhAccountNumbers(saveEvent.getSelectedItemsIds()
                .stream()
                .map(Rightsholder::getAccountNumber)
                .collect(Collectors.toSet()));
            filterChanged();
        });
        VaadinUtils.addComponentStyle(rightsholderFilterWidget, "rightsholders-filter");
        return rightsholderFilterWidget;
    }

    private void initStatusFilter() {
        statusComboBox = new ComboBox<>(ForeignUi.getMessage("label.status"));
        statusComboBox.setWidthFull();
        statusComboBox.addValueChangeListener(event -> {
            getFilter().setUsageStatus(statusComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(statusComboBox, "status-filter");
    }

    private void initFiscalYearFilter() {
        fiscalYearComboBox = new ComboBox<>(ForeignUi.getMessage("label.fiscal_year_to"));
        fiscalYearComboBox.setWidthFull();
        fiscalYearComboBox.addValueChangeListener(event -> {
            getFilter().setFiscalYear(fiscalYearComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(fiscalYearComboBox, "fiscal-year-filter");
    }

    private void initPaymentDateFilter() {
        paymentDateWidget = new LocalDateWidget(ForeignUi.getMessage("label.payment_date_to"));
        paymentDateWidget.addValueChangeListener(event -> {
            getFilter().setPaymentDate(paymentDateWidget.getValue());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(paymentDateWidget, "payment-date-filter");
    }

    private List<UsageStatusEnum> getStatuses() {
        return FdaConstants.FAS_FAS2_PRODUCT_FAMILY_SET.contains(controller.getSelectedProductFamily())
            ? FAS_FAS2_STATUSES
            : NTS_STATUSES;
    }
}
