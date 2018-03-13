package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.widget.BaseItemsFilterWidget;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

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

    private final Supplier<List<UsageBatch>> supplier;
    private final Set<String> selectedItemsIds = new HashSet<>();

    /**
     * Constructor.
     *
     * @param supplier {@link UsageBatch}es supplier
     */
    public UsageBatchFilterWidget(Supplier<List<UsageBatch>> supplier) {
        super(ForeignUi.getMessage("label.batches"));
        this.supplier = supplier;
    }

    @Override
    public Collection<UsageBatch> loadBeans() {
        return supplier.get();
    }

    @Override
    public void reset() {
        selectedItemsIds.clear();
        super.reset();
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
        Set<String> itemsIds = event.getSelectedItemsIds();
        selectedItemsIds.clear();
        if (CollectionUtils.isNotEmpty(itemsIds)) {
            selectedItemsIds.addAll(itemsIds);
        }
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
        filterWindow.setSearchPromptString(ForeignUi.getMessage("prompt.batch"));
        VaadinUtils.addComponentStyle(filterWindow, "batches-filter-window");
        return filterWindow;
    }
}
