package com.copyright.rup.dist.foreign.vui.audit.impl.fas;

import com.copyright.rup.dist.foreign.vui.audit.impl.CommonAuditFilterWidget;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonStatusFilterWidget;
import com.copyright.rup.dist.foreign.vui.common.LazyRightsholderFilterWidget;
import com.copyright.rup.dist.foreign.vui.common.UsageBatchFilterWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.textfield.TextField;

import org.apache.commons.lang3.StringUtils;

/**
 * Implementation of FAS product family widget.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/16/2020
 *
 * @author Anton Azarenka
 */
public class FasAuditFilterWidget extends CommonAuditFilterWidget {

    private static final long serialVersionUID = 1527033265096718471L;

    private LazyRightsholderFilterWidget rightsholderFilterWidget;
    private UsageBatchFilterWidget usageBatchFilterWidget;
    private CommonStatusFilterWidget commonStatusFilterWidget;
    private TextField cccEventIdField;
    private TextField distributionNameField;

    @Override
    public void initFields() {
        rightsholderFilterWidget = buildRightsholdersFilter();
        usageBatchFilterWidget = buildUsageBatchesFilter();
        cccEventIdField = buildEventIdField();
        distributionNameField = buildDistributionNameField();
        initStatusesFilterWidget();
        add(buildFiltersHeaderLabel(), rightsholderFilterWidget, usageBatchFilterWidget, commonStatusFilterWidget,
            cccEventIdField, distributionNameField);
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

    private void initStatusesFilterWidget() {
        commonStatusFilterWidget = new FasStatusFilterWidget();
        commonStatusFilterWidget.addFilterSaveListener(event -> {
            getFilter().setStatuses(event.getSelectedItemsIds());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(commonStatusFilterWidget, "audit-statuses-filter");
    }
}
