package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterController;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.widget.BaseItemsFilterWidget;

import java.util.List;
import java.util.Set;

/**
 * Widget provides functionality for configuring items filter widget for usage batches.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 2/15/17
 *
 * @author Mikalai Bezmen
 */
public class UsageBatchFilterWidget extends BaseItemsFilterWidget<String, UsageBatch>
    implements IFilterWindowController<String, UsageBatch> {

    private Set<String> selectedItemsIds;
    private IUsagesFilterController controller;

    /**
     * Controller.
     *
     * @param controller controller of UsagesFilterController
     */
    public UsageBatchFilterWidget(IUsagesFilterController controller) {
        super(ForeignUi.getMessage("label.batches"));
        this.controller = controller;
    }

    @Override
    public void reset() {
        selectedItemsIds = null;
        super.reset();
    }

    @Override
    public List<UsageBatch> loadBeans() {
        return controller.getUsageBatchesNotIncludedIntoScenario();
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
    public void onSave(FilterSaveEvent<String> event) {
        selectedItemsIds = event.getSelectedItemsIds();
    }

    @Override
    public String getIdForBean(UsageBatch usageBatch) {
        return usageBatch.getId();
    }

    @Override
    public FilterWindow<String, UsageBatch> showFilterWindow() {
        FilterWindow<String, UsageBatch> filterWindow =
            Windows.showFilterWindow(ForeignUi.getMessage("window.batches_filter"), this, "name");
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        filterWindow.setSearchPromptString("Enter Usage Batch name");
        VaadinUtils.addComponentStyle(filterWindow, "batches-filter-window");
        return filterWindow;
    }
}
