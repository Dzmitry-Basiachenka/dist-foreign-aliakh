package com.copyright.rup.dist.foreign.ui.audit.impl.nts;

import com.copyright.rup.dist.foreign.ui.audit.api.nts.INtsAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonAuditAppliedFilterWidget;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonAuditFilterWidget;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonStatusFilterWidget;
import com.copyright.rup.dist.foreign.ui.common.LazyRightsholderFilterWidget;
import com.copyright.rup.dist.foreign.ui.common.UsageBatchFilterWidget;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.TextField;

import org.apache.commons.lang3.StringUtils;

/**
 * Implementation of NTS product family widget.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/16/2020
 *
 * @author Anton Azarenka
 */
public class NtsAuditFilterWidget extends CommonAuditFilterWidget {

    private final INtsAuditFilterController controller;

    private LazyRightsholderFilterWidget rightsholderFilterWidget;
    private UsageBatchFilterWidget usageBatchFilterWidget;
    private CommonStatusFilterWidget commonStatusFilterWidget;
    private TextField cccEventIdField;
    private TextField distributionNameField;

    /**
     * Constructor.
     *
     * @param controller instance of {@link INtsAuditFilterController}
     */
    public NtsAuditFilterWidget(INtsAuditFilterController controller) {
        this.controller = controller;
    }

    @Override
    public void initFields() {
        rightsholderFilterWidget = buildRightsholdersFilter();
        usageBatchFilterWidget = buildUsageBatchesFilter();
        cccEventIdField = buildEventIdField();
        distributionNameField = buildDistributionNameField();
        initStatusesFilterWidget();
        addComponents(buildFiltersHeaderLabel(), rightsholderFilterWidget, usageBatchFilterWidget,
            commonStatusFilterWidget, cccEventIdField, distributionNameField);
    }

    @Override
    public void clearFilter() {
        refreshFilter();
        usageBatchFilterWidget.reset();
        rightsholderFilterWidget.reset();
        commonStatusFilterWidget.reset();
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
    protected CommonAuditAppliedFilterWidget getAppliedFilterWidget() {
        return new NtsAuditAppliedFilterWidget(controller);
    }

    private void initStatusesFilterWidget() {
        commonStatusFilterWidget = new NtsStatusFilterWidget();
        commonStatusFilterWidget.addFilterSaveListener(event -> {
            getFilter().setStatuses(event.getSelectedItemsIds());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(commonStatusFilterWidget, "audit-statuses-filter");
    }
}
