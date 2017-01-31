package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.UsageBatch;
import com.copyright.rup.dist.foreign.ui.common.domain.FakeDataGenerator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonFilterWindowController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterWidget;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

/**
 * Controller for the usage batches filter window.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 1/19/17
 *
 * @author Aliaksandr Radkevich
 */
@Component("usageBatchesFilterController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UsageBatchesFilterController implements ICommonFilterWindowController<String, UsageBatch> {

    private IUsagesFilterWidget filterWidget;

    @Override
    public Collection<UsageBatch> loadBeans() {
        return FakeDataGenerator.getUsageBatches();
    }

    @Override
    public Class<UsageBatch> getBeanClass() {
        return UsageBatch.class;
    }

    @Override
    public String getBeanItemCaption(UsageBatch usageBatch) {
        return usageBatch.getName();
    }

    @Override
    public void onSave(Set<String> selectedItemsIds) {
        filterWidget.setSelectedUsageBatches(selectedItemsIds);
    }

    @Override
    public String getIdForBean(UsageBatch usageBatch) {
        return usageBatch.getId();
    }

    @Override
    public void setFilterWidget(IUsagesFilterWidget filterWidget) {
        this.filterWidget = filterWidget;
    }

    @Override
    public void showFilterWindow() {
        FilterWindow<String, UsageBatch> filterWindow =
            Windows.showFilterWindow(ForeignUi.getMessage("window.batches_filter"), this,
                "name", "rro.accountNumber");
        filterWindow.setSelectedItemsIds(filterWidget.getFilter().getUsageBatchesIds());
        VaadinUtils.addComponentStyle(filterWindow, "batches-filter-window");
    }
}
