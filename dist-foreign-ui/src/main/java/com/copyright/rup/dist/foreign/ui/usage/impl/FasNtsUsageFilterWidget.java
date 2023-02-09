package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.common.RightsholderFilterWidget;
import com.copyright.rup.dist.foreign.ui.common.UsageBatchFilterWidget;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.IFasNtsUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.IFasNtsUsageFilterWidget;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.google.common.collect.ImmutableSet;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import java.util.Set;
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

    private static final Set<UsageStatusEnum> FAS_FAS2_STATUSES = ImmutableSet.of(UsageStatusEnum.NEW,
        UsageStatusEnum.WORK_NOT_FOUND, UsageStatusEnum.WORK_RESEARCH, UsageStatusEnum.WORK_FOUND,
        UsageStatusEnum.RH_NOT_FOUND, UsageStatusEnum.RH_FOUND, UsageStatusEnum.SENT_FOR_RA, UsageStatusEnum.ELIGIBLE,
        UsageStatusEnum.NTS_WITHDRAWN, UsageStatusEnum.TO_BE_DISTRIBUTED);
    private static final Set<UsageStatusEnum> NTS_STATUSES = ImmutableSet.of(UsageStatusEnum.WORK_FOUND,
        UsageStatusEnum.RH_FOUND, UsageStatusEnum.UNCLASSIFIED, UsageStatusEnum.ELIGIBLE,
        UsageStatusEnum.SCENARIO_EXCLUDED);

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
        statusComboBox.setItems(getStatuses());
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
        VerticalLayout verticalLayout = new VerticalLayout(buildFiltersHeaderLabel(), buildUsageBatchFilter(),
            buildRroAccountNumberFilter(), paymentDateWidget, statusComboBox, fiscalYearComboBox);
        verticalLayout.setMargin(false);
        return verticalLayout;
    }

    private HorizontalLayout buildUsageBatchFilter() {
        usageBatchFilterWidget = new UsageBatchFilterWidget(() -> controller.getUsageBatches());
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
        VaadinUtils.setMaxComponentsWidth(statusComboBox);
        statusComboBox.addValueChangeListener(event -> {
            getFilter().setUsageStatus(statusComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(statusComboBox, "status-filter");
    }

    private void initFiscalYearFilter() {
        fiscalYearComboBox = new ComboBox<>(ForeignUi.getMessage("label.fiscal_year_to"));
        VaadinUtils.setMaxComponentsWidth(fiscalYearComboBox);
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

    private Set<UsageStatusEnum> getStatuses() {
        return FdaConstants.FAS_FAS2_PRODUCT_FAMILY_SET.contains(controller.getSelectedProductFamily())
            ? FAS_FAS2_STATUSES
            : NTS_STATUSES;
    }
}
