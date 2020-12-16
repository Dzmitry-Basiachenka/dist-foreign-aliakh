package com.copyright.rup.dist.foreign.ui.audit.impl.sal;

import com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum;
import com.copyright.rup.dist.foreign.domain.report.SalLicensee;
import com.copyright.rup.dist.foreign.ui.audit.api.sal.ISalAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonAuditFilterWidget;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonStatusFilterWidget;
import com.copyright.rup.dist.foreign.ui.common.LazyRightsholderFilterWidget;
import com.copyright.rup.dist.foreign.ui.common.UsageBatchFilterWidget;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Implementation of SAL product family widget.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 12/16/2020
 *
 * @author Aliaksandr Liakh
 */
public class SalAuditFilterWidget extends CommonAuditFilterWidget {

    private final ISalAuditFilterController controller;

    private LazyRightsholderFilterWidget rightsholderFilterWidget;
    private SalLicenseeFilterWidget licenseeFilterWidget;
    private UsageBatchFilterWidget usageBatchFilterWidget;
    private CommonStatusFilterWidget commonStatusFilterWidget;
    private ComboBox<SalDetailTypeEnum> salDetailTypeComboBox;
    private ComboBox<Integer> usagePeriodComboBox;
    private TextField cccEventIdField;
    private TextField distributionNameField;

    /**
     * Constructor.
     *
     * @param controller instance of {@link ISalAuditFilterController}
     */
    public SalAuditFilterWidget(ISalAuditFilterController controller) {
        this.controller = controller;
    }

    @Override
    public void initFields() {
        rightsholderFilterWidget = buildRightsholdersFilter();
        licenseeFilterWidget = buildLicenseesFilter();
        usageBatchFilterWidget = buildUsageBatchesFilter();
        cccEventIdField = buildEventIdField();
        distributionNameField = buildDistributionNameField();
        initSalDetailTypeComboBox();
        initUsagePeriodComboBox();
        initStatusesFilterWidget();
        addComponents(buildFiltersHeaderLabel(), rightsholderFilterWidget, licenseeFilterWidget, usageBatchFilterWidget,
            commonStatusFilterWidget, salDetailTypeComboBox, usagePeriodComboBox, cccEventIdField,
            distributionNameField);
    }

    @Override
    public void clearFilter() {
        refreshFilter();
        rightsholderFilterWidget.reset();
        licenseeFilterWidget.reset();
        usageBatchFilterWidget.reset();
        commonStatusFilterWidget.reset();
        salDetailTypeComboBox.clear();
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

    private SalLicenseeFilterWidget buildLicenseesFilter() {
        licenseeFilterWidget = new SalLicenseeFilterWidget(controller::getSalLicensees);
        licenseeFilterWidget.addFilterSaveListener((FilterWindow.IFilterSaveListener<SalLicensee>) event -> {
            getFilter().setRhAccountNumbers(event.getSelectedItemsIds()
                .stream()
                .map(SalLicensee::getAccountNumber)
                .collect(Collectors.toSet()));
            filterChanged();
        });
        VaadinUtils.addComponentStyle(licenseeFilterWidget, "audit-licensees-filter");
        return licenseeFilterWidget;
    }

    private void initStatusesFilterWidget() {
        commonStatusFilterWidget = new SalStatusFilterWidget();
        commonStatusFilterWidget.addFilterSaveListener(event -> {
            getFilter().setStatuses(event.getSelectedItemsIds());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(commonStatusFilterWidget, "audit-statuses-filter");
    }

    private void initSalDetailTypeComboBox() {
        salDetailTypeComboBox = new ComboBox<>(ForeignUi.getMessage("label.detail_type"));
        VaadinUtils.setMaxComponentsWidth(salDetailTypeComboBox);
        salDetailTypeComboBox.addValueChangeListener(event -> {
            getFilter().setSalDetailType(salDetailTypeComboBox.getValue());
            filterChanged();
        });
        salDetailTypeComboBox.setItems(Arrays.asList(SalDetailTypeEnum.IB, SalDetailTypeEnum.UD));
        VaadinUtils.addComponentStyle(salDetailTypeComboBox, "detail-type-filter");
    }

    private void initUsagePeriodComboBox() {
        usagePeriodComboBox = new ComboBox<>(ForeignUi.getMessage("label.usage_period"));
        VaadinUtils.setMaxComponentsWidth(usagePeriodComboBox);
        usagePeriodComboBox.addValueChangeListener(event -> {
            getFilter().setUsagePeriod(usagePeriodComboBox.getValue());
            filterChanged();
        });
        usagePeriodComboBox.setItems(controller.getUsagePeriods());
        VaadinUtils.addComponentStyle(usagePeriodComboBox, "usage-period-filter");
    }
}
