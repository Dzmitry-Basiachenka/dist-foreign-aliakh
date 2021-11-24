package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.common.UsageBatchFilterWidget;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageFilterWidget;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.google.common.collect.ImmutableSet;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Widget for filtering AACL usages.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/23/2019
 *
 * @author Aliaksandr Liakh
 */
public class AaclUsageFilterWidget extends CommonUsageFilterWidget implements IAaclUsageFilterWidget {

    private static final Set<UsageStatusEnum> AACL_STATUSES = ImmutableSet.of(UsageStatusEnum.NEW,
        UsageStatusEnum.WORK_FOUND, UsageStatusEnum.RH_FOUND, UsageStatusEnum.WORK_NOT_FOUND,
        UsageStatusEnum.WORK_RESEARCH, UsageStatusEnum.ELIGIBLE, UsageStatusEnum.SCENARIO_EXCLUDED);

    private final IAaclUsageFilterController controller;

    private UsageBatchFilterWidget usageBatchFilterWidget;
    private ComboBox<UsageStatusEnum> statusComboBox;
    private ComboBox<Integer> usagePeriodComboBox;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IAaclUsageFilterController}
     */
    public AaclUsageFilterWidget(IAaclUsageFilterController controller) {
        this.controller = controller;
    }

    @Override
    protected void refreshFilterValues() {
        usagePeriodComboBox.setItems(controller.getUsagePeriods());
        statusComboBox.setItems(AACL_STATUSES);
    }

    @Override
    protected void clearFilterValues() {
        usageBatchFilterWidget.reset();
        statusComboBox.clear();
        usagePeriodComboBox.clear();
    }

    @Override
    protected VerticalLayout initFiltersLayout() {
        initStatusFilter();
        initUsagePeriodFilter();
        VerticalLayout verticalLayout = new VerticalLayout(buildFiltersHeaderLabel(), buildUsageBatchFilter(),
            statusComboBox, usagePeriodComboBox);
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

    private void initStatusFilter() {
        statusComboBox = new ComboBox<>(ForeignUi.getMessage("label.status"));
        VaadinUtils.setMaxComponentsWidth(statusComboBox);
        statusComboBox.addValueChangeListener(event -> {
            getFilter().setUsageStatus(statusComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(statusComboBox, "status-filter");
    }

    private void initUsagePeriodFilter() {
        usagePeriodComboBox = new ComboBox<>(ForeignUi.getMessage("label.usage_period"));
        VaadinUtils.setMaxComponentsWidth(usagePeriodComboBox);
        usagePeriodComboBox.addValueChangeListener(event -> {
            getFilter().setUsagePeriod(usagePeriodComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(usagePeriodComboBox, "usage-period-filter");
    }
}
