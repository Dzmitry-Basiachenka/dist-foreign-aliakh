package com.copyright.rup.dist.foreign.ui.audit.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.BaseItemsFilterWidget;

import com.google.common.collect.Sets;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

/**
 * Statuses filter window.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/12/18
 *
 * @author Aliaksandr Radkevich
 */
class StatusFilterWidget extends BaseItemsFilterWidget<UsageStatusEnum>
    implements IFilterWindowController<UsageStatusEnum> {

    private final EnumSet<UsageStatusEnum> selectedItemsIds = EnumSet.noneOf(UsageStatusEnum.class);
    private final String productFamily;

    private static final Set<UsageStatusEnum> FAS_FAS2_STATUSES =
        Sets.newHashSet(UsageStatusEnum.NEW, UsageStatusEnum.WORK_NOT_FOUND, UsageStatusEnum.WORK_RESEARCH,
            UsageStatusEnum.WORK_FOUND, UsageStatusEnum.RH_NOT_FOUND, UsageStatusEnum.RH_FOUND,
            UsageStatusEnum.SENT_FOR_RA, UsageStatusEnum.ELIGIBLE, UsageStatusEnum.LOCKED, UsageStatusEnum.SENT_TO_LM,
            UsageStatusEnum.PAID, UsageStatusEnum.ARCHIVED);
    private static final Set<UsageStatusEnum> NTS_STATUSES =
        Sets.newHashSet(UsageStatusEnum.NTS_WITHDRAWN, UsageStatusEnum.WORK_FOUND, UsageStatusEnum.RH_FOUND,
            UsageStatusEnum.UNCLASSIFIED, UsageStatusEnum.ELIGIBLE, UsageStatusEnum.TO_BE_DISTRIBUTED,
            UsageStatusEnum.NTS_EXCLUDED, UsageStatusEnum.NON_STM_RH, UsageStatusEnum.US_TAX_COUNTRY,
            UsageStatusEnum.LOCKED, UsageStatusEnum.SENT_TO_LM, UsageStatusEnum.PAID, UsageStatusEnum.ARCHIVED);

    /**
     * Constructor.
     *
     * @param productFamily product family
     */
    StatusFilterWidget(String productFamily) {
        super(ForeignUi.getMessage("label.status"));
        this.productFamily = productFamily;
    }

    @Override
    public Collection<UsageStatusEnum> loadBeans() {
        return FdaConstants.FAS_FAS2_PRODUCT_FAMILY_SET.contains(productFamily)
            ? FAS_FAS2_STATUSES
            : NTS_STATUSES;
    }

    @Override
    public Class<UsageStatusEnum> getBeanClass() {
        return UsageStatusEnum.class;
    }

    @Override
    public String getBeanItemCaption(UsageStatusEnum status) {
        return status.name();
    }

    @Override
    public void onSave(FilterSaveEvent<UsageStatusEnum> event) {
        Set<UsageStatusEnum> itemsIds = event.getSelectedItemsIds();
        selectedItemsIds.clear();
        if (CollectionUtils.isNotEmpty(itemsIds)) {
            selectedItemsIds.addAll(itemsIds);
        }
    }

    @Override
    public FilterWindow<UsageStatusEnum> showFilterWindow() {
        FilterWindow<UsageStatusEnum> filterWindow =
            Windows.showFilterWindow(ForeignUi.getMessage("window.status_filter"), this);
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        VaadinUtils.addComponentStyle(filterWindow, "status-filter-window");
        return filterWindow;
    }

    @Override
    public void reset() {
        selectedItemsIds.clear();
        super.reset();
    }
}
