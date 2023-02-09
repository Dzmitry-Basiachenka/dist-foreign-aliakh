package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.common.RightsholderFilterWidget;
import com.copyright.rup.dist.foreign.ui.common.UsageBatchFilterWidget;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageFilterWidget;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.google.common.collect.ImmutableSet;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Widget for filtering SAL usages.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/28/2020
 *
 * @author Uladzislau Shalamitski
 */
public class SalUsageFilterWidget extends CommonUsageFilterWidget implements IAaclUsageFilterWidget {

    private static final Set<UsageStatusEnum> SAL_STATUSES =
        ImmutableSet.of(UsageStatusEnum.NEW, UsageStatusEnum.WORK_FOUND, UsageStatusEnum.WORK_NOT_FOUND,
            UsageStatusEnum.RH_FOUND, UsageStatusEnum.WORK_NOT_GRANTED, UsageStatusEnum.RH_NOT_FOUND,
            UsageStatusEnum.ELIGIBLE);

    private final ISalUsageFilterController controller;

    private UsageBatchFilterWidget usageBatchFilterWidget;
    private RightsholderFilterWidget rightsholderFilterWidget;
    private ComboBox<UsageStatusEnum> statusComboBox;
    private ComboBox<SalDetailTypeEnum> detailTypeComboBox;

    /**
     * Constructor.
     *
     * @param controller instance of {@link ISalUsageFilterController}
     */
    public SalUsageFilterWidget(ISalUsageFilterController controller) {
        this.controller = controller;
    }

    @Override
    protected void refreshFilterValues() {
        statusComboBox.setItems(SAL_STATUSES);
        detailTypeComboBox.setItems(SalDetailTypeEnum.values());
    }

    @Override
    protected void clearFilterValues() {
        usageBatchFilterWidget.reset();
        rightsholderFilterWidget.reset();
        statusComboBox.clear();
        detailTypeComboBox.clear();
    }

    @Override
    protected VerticalLayout initFiltersLayout() {
        initStatusFilter();
        initDetailTypeFilter();
        VerticalLayout verticalLayout = new VerticalLayout(buildFiltersHeaderLabel(), buildUsageBatchFilter(),
            buildRightsholdersFilter(), statusComboBox, detailTypeComboBox);
        verticalLayout.setMargin(false);
        return verticalLayout;
    }

    private HorizontalLayout buildUsageBatchFilter() {
        usageBatchFilterWidget = new UsageBatchFilterWidget(controller::getUsageBatches);
        usageBatchFilterWidget.addFilterSaveListener((FilterWindow.IFilterSaveListener<UsageBatch>) saveEvent -> {
            getFilter().setUsageBatchesIds(saveEvent.getSelectedItemsIds().stream()
                .map(UsageBatch::getId)
                .collect(Collectors.toSet()));
            filterChanged();
        });
        VaadinUtils.addComponentStyle(usageBatchFilterWidget, "batches-filter");
        return usageBatchFilterWidget;
    }

    private HorizontalLayout buildRightsholdersFilter() {
        rightsholderFilterWidget = new RightsholderFilterWidget(
            ForeignUi.getMessage("label.rightsholders"), ForeignUi.getMessage("prompt.rightsholder"),
            ForeignUi.getMessage("message.error.rh_not_found"), controller::getRightsholders);
        rightsholderFilterWidget.addFilterSaveListener((CommonFilterWindow.IFilterSaveListener<Rightsholder>) event -> {
            getFilter().setRhAccountNumbers(event.getSelectedItemsIds()
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
        statusComboBox.addValueChangeListener(event -> {
            getFilter().setUsageStatus(statusComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.setMaxComponentsWidth(statusComboBox);
        VaadinUtils.addComponentStyle(statusComboBox, "status-filter");
    }

    private void initDetailTypeFilter() {
        detailTypeComboBox = new ComboBox<>(ForeignUi.getMessage("label.detail_type"));
        detailTypeComboBox.addValueChangeListener(event -> {
            getFilter().setSalDetailType(detailTypeComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.setMaxComponentsWidth(detailTypeComboBox);
        VaadinUtils.addComponentStyle(detailTypeComboBox, "detail-type-filter");
    }
}
