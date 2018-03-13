package com.copyright.rup.dist.foreign.ui.audit.impl;

import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.widget.BaseItemsFilterWidget;

import com.google.common.collect.Lists;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
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
class StatusFilterWidget extends BaseItemsFilterWidget<UsageStatusEnum, UsageStatusEnum> implements
    IFilterWindowController<UsageStatusEnum, UsageStatusEnum> {

    private final Set<UsageStatusEnum> selectedItemsIds = new HashSet<>();

    /**
     * Constructor.
     */
    StatusFilterWidget() {
        super(ForeignUi.getMessage("label.status"));
    }

    @Override
    public Collection<UsageStatusEnum> loadBeans() {
        return Lists.newArrayList(UsageStatusEnum.values());
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
    public UsageStatusEnum getIdForBean(UsageStatusEnum status) {
        return status;
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
    public FilterWindow<UsageStatusEnum, UsageStatusEnum> showFilterWindow() {
        FilterWindow<UsageStatusEnum, UsageStatusEnum> filterWindow =
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
