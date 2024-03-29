package com.copyright.rup.dist.foreign.vui.usage.impl.aacl;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.vui.common.UsageBatchFilterWidget;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.aacl.IAaclUsageFilterController;
import com.copyright.rup.dist.foreign.vui.usage.api.aacl.IAaclUsageFilterWidget;
import com.copyright.rup.dist.foreign.vui.usage.impl.CommonUsageAppliedFilterWidget;
import com.copyright.rup.dist.foreign.vui.usage.impl.CommonUsageFilterWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.CommonFilterWindow.IFilterSaveListener;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;
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

    private static final long serialVersionUID = 3090392499920631454L;
    private static final List<UsageStatusEnum> AACL_STATUSES = List.of(UsageStatusEnum.NEW,
        UsageStatusEnum.WORK_FOUND, UsageStatusEnum.WORK_NOT_FOUND, UsageStatusEnum.WORK_RESEARCH,
        UsageStatusEnum.RH_FOUND, UsageStatusEnum.ELIGIBLE, UsageStatusEnum.SCENARIO_EXCLUDED);

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
        return VaadinUtils.initCommonVerticalLayout(buildFiltersHeaderLabel(), buildUsageBatchFilter(),
            initStatusComboBox(), initUsagePeriodComboBox());
    }

    @Override
    protected CommonUsageAppliedFilterWidget getAppliedFilterWidget() {
        return new AaclUsageAppliedFilterWidget(controller);
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

    private ComboBox<UsageStatusEnum> initStatusComboBox() {
        statusComboBox = new ComboBox<>(ForeignUi.getMessage("label.status"));
        statusComboBox.setWidthFull();
        statusComboBox.addValueChangeListener(event -> {
            getFilter().setUsageStatus(statusComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(statusComboBox, "status-filter");
        return statusComboBox;
    }

    private ComboBox<Integer> initUsagePeriodComboBox() {
        usagePeriodComboBox = new ComboBox<>(ForeignUi.getMessage("label.usage_period"));
        usagePeriodComboBox.setWidthFull();
        usagePeriodComboBox.addValueChangeListener(event -> {
            getFilter().setUsagePeriod(usagePeriodComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(usagePeriodComboBox, "usage-period-filter");
        return usagePeriodComboBox;
    }
}
