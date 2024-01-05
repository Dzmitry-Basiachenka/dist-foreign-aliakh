package com.copyright.rup.dist.foreign.ui.audit.impl.fas;

import com.copyright.rup.dist.foreign.ui.audit.api.fas.IFasAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonAuditAppliedFilterWidget;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonAuditFilterWidget;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonStatusFilterWidget;
import com.copyright.rup.dist.foreign.ui.common.LazyRightsholderFilterWidget;
import com.copyright.rup.dist.foreign.ui.common.UsageBatchFilterWidget;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.TextField;

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

    private static final long serialVersionUID = 1826849913053314229L;

    private final IFasAuditFilterController controller;

    private LazyRightsholderFilterWidget rightsholderFilterWidget;
    private UsageBatchFilterWidget usageBatchFilterWidget;
    private CommonStatusFilterWidget commonStatusFilterWidget;
    private TextField cccEventIdField;
    private TextField distributionNameField;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IFasAuditFilterController}
     */
    public FasAuditFilterWidget(IFasAuditFilterController controller) {
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
        return new FasAuditAppliedFilterWidget(controller);
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
