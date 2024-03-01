package com.copyright.rup.dist.foreign.vui.audit.impl.aacl;

import com.copyright.rup.dist.foreign.vui.audit.api.aacl.IAaclAuditFilterController;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonAuditAppliedFilterWidget;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonAuditFilterWidget;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonStatusFilterWidget;
import com.copyright.rup.dist.foreign.vui.common.LazyRightsholderFilterWidget;
import com.copyright.rup.dist.foreign.vui.common.UsageBatchFilterWidget;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;

import org.apache.commons.lang3.StringUtils;

/**
 * Implementation of AACL product family widget.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/16/2020
 *
 * @author Anton Azarenka
 */
public class AaclAuditFilterWidget extends CommonAuditFilterWidget {

    private static final long serialVersionUID = 3421128322830506328L;

    private final IAaclAuditFilterController controller;
    private LazyRightsholderFilterWidget rightsholderFilterWidget;
    private UsageBatchFilterWidget usageBatchFilterWidget;
    private CommonStatusFilterWidget commonStatusFilterWidget;
    private ComboBox<Integer> usagePeriodComboBox;
    private TextField cccEventIdField;
    private TextField distributionNameField;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IAaclAuditFilterController}
     */
    public AaclAuditFilterWidget(IAaclAuditFilterController controller) {
        this.controller = controller;
    }

    @Override
    public void initFields() {
        rightsholderFilterWidget = buildRightsholdersFilter();
        usageBatchFilterWidget = buildUsageBatchesFilter();
        cccEventIdField = buildEventIdField();
        distributionNameField = buildDistributionNameField();
        initUsagePeriodFilter();
        initStatusesFilterWidget();
        add(buildFiltersHeaderLabel(), rightsholderFilterWidget, usageBatchFilterWidget,
            commonStatusFilterWidget, usagePeriodComboBox, cccEventIdField, distributionNameField);
    }

    @Override
    public void clearFilter() {
        refreshFilter();
        usageBatchFilterWidget.reset();
        rightsholderFilterWidget.reset();
        commonStatusFilterWidget.reset();
        usagePeriodComboBox.clear();
        cccEventIdField.setValue(StringUtils.EMPTY);
        distributionNameField.setValue(StringUtils.EMPTY);
        applyFilter();
    }

    @Override
    public void trimFilterValues() {
        cccEventIdField.setValue(StringUtils.trim(cccEventIdField.getValue()));
        distributionNameField.setValue(StringUtils.trim(distributionNameField.getValue()));
        applyFilter();
    }

    @Override
    protected CommonAuditAppliedFilterWidget initAppliedFilterWidget() {
        return new AaclAuditAppliedFilterWidget(controller);
    }

    private void initStatusesFilterWidget() {
        commonStatusFilterWidget = new AaclStatusFilterWidget();
        commonStatusFilterWidget.addFilterSaveListener(event -> {
            getFilter().setStatuses(event.getSelectedItemsIds());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(commonStatusFilterWidget, "audit-statuses-filter");
    }

    private void initUsagePeriodFilter() {
        usagePeriodComboBox = new ComboBox<>(ForeignUi.getMessage("label.usage_period"));
        usagePeriodComboBox.setWidthFull();
        usagePeriodComboBox.addValueChangeListener(event -> {
            getFilter().setUsagePeriod(usagePeriodComboBox.getValue());
            filterChanged();
        });
        usagePeriodComboBox.setItems(controller.getUsagePeriods());
        VaadinUtils.addComponentStyle(usagePeriodComboBox, "usage-period-filter");
    }
}
