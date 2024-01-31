package com.copyright.rup.dist.foreign.vui.usage.impl;

import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.FilterWindow;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.IFilterWindowController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.BaseItemsFilterWidget;

import org.apache.commons.collections4.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Widget provides functionality for configuring items filter widget for markets.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/29/2018
 *
 * @author Ihar Suvorau
 */
public class MarketFilterWidget extends BaseItemsFilterWidget<String>
    implements IFilterWindowController<String> {

    private static final long serialVersionUID = 6057100539027034797L;

    private final Supplier<List<String>> supplier;
    private final Set<String> selectedItemsIds = new HashSet<>();

    /**
     * Constructor.
     *
     * @param supplier markets supplier
     */
    public MarketFilterWidget(Supplier<List<String>> supplier) {
        super(ForeignUi.getMessage("label.markets"));
        this.supplier = supplier;
    }

    @Override
    public void reset() {
        selectedItemsIds.clear();
        super.reset();
    }

    @Override
    public List<String> loadBeans() {
        return supplier.get();
    }

    @Override
    public Class<String> getBeanClass() {
        return String.class;
    }

    @Override
    public String getBeanItemCaption(String market) {
        return market;
    }

    @Override
    public void onComponentEvent(FilterSaveEvent<String> event) {
        Set<String> itemsIds = event.getSelectedItemsIds();
        selectedItemsIds.clear();
        if (CollectionUtils.isNotEmpty(itemsIds)) {
            selectedItemsIds.addAll(itemsIds);
        }
    }

    @Override
    public FilterWindow<String> showFilterWindow() {
        var filterWindow = Windows.showFilterWindow(ForeignUi.getMessage("window.markets_filter"), this);
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        filterWindow.setWidth("350px");
        VaadinUtils.addComponentStyle(filterWindow, "markets-filter-window");
        return filterWindow;
    }
}
