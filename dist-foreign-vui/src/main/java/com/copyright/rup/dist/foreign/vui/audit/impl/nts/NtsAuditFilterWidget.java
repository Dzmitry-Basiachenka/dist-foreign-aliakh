package com.copyright.rup.dist.foreign.vui.audit.impl.nts;

import com.copyright.rup.dist.foreign.vui.audit.api.nts.INtsAuditFilterController;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonAuditAppliedFilterWidget;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonAuditFilterWidget;
import com.copyright.rup.dist.foreign.vui.common.LazyRightsholderFilterWidget;
import com.copyright.rup.dist.foreign.vui.common.UsageBatchFilterWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.textfield.TextField;

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

    private static final long serialVersionUID = 2374703703458839058L;

    private final INtsAuditFilterController controller;

    private LazyRightsholderFilterWidget rightsholderFilterWidget;
    private UsageBatchFilterWidget usageBatchFilterWidget;
    private NtsStatusFilterWidget ntsStatusFilterWidget;
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
        add(buildFiltersHeaderLabel(), rightsholderFilterWidget, usageBatchFilterWidget, ntsStatusFilterWidget,
            cccEventIdField, distributionNameField);
    }

    @Override
    public void clearFilter() {
        refreshFilter();
        usageBatchFilterWidget.reset();
        rightsholderFilterWidget.reset();
        ntsStatusFilterWidget.reset();
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
        return new NtsAuditAppliedFilterWidget(controller);
    }

    private void initStatusesFilterWidget() {
        ntsStatusFilterWidget = new NtsStatusFilterWidget();
        ntsStatusFilterWidget.addFilterSaveListener(event -> {
            getFilter().setStatuses(event.getSelectedItemsIds());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(ntsStatusFilterWidget, "audit-statuses-filter");
    }
}
