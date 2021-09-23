package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.BaseItemsFilterWidget;

import com.vaadin.data.ValueProvider;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Widget provides functionality for configuring items filter widget for UDM Periods.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/23/21
 *
 * @author Anton Azarenka
 */
public class UdmValuePeriodFilterWidget extends BaseItemsFilterWidget<Integer>
    implements IFilterWindowController<Integer> {

    private final Supplier<List<Integer>> supplier;
    private final Set<Integer> periods = new HashSet<>();

    /**
     * Constructor.
     *
     * @param supplier period supplier
     */
    public UdmValuePeriodFilterWidget(Supplier<List<Integer>> supplier) {
        super(ForeignUi.getMessage("label.periods"));
        this.supplier = supplier;
    }

    @Override
    public void reset() {
        super.reset();
        periods.clear();
    }

    @Override
    public List<Integer> loadBeans() {
        return supplier.get();
    }

    @Override
    public Class<Integer> getBeanClass() {
        return Integer.class;
    }

    @Override
    public String getBeanItemCaption(Integer period) {
        return String.valueOf(period);
    }

    @Override
    public void onSave(FilterSaveEvent<Integer> event) {
        Set<Integer> itemsIds = event.getSelectedItemsIds();
        periods.clear();
        if (CollectionUtils.isNotEmpty(itemsIds)) {
            periods.addAll(itemsIds);
        }
    }

    @Override
    public FilterWindow<Integer> showFilterWindow() {
        FilterWindow<Integer> filterWindow =
            Windows.showFilterWindow(ForeignUi.getMessage("window.periods_filter"), this,
                (ValueProvider<Integer, List<String>>) bean -> Collections.singletonList(String.valueOf(bean)));
        filterWindow.setSelectedItemsIds(periods);
        filterWindow.setSearchPromptString(ForeignUi.getMessage("prompt.period"));
        VaadinUtils.addComponentStyle(filterWindow, "udm-values-periods-filter-window");
        return filterWindow;
    }
}
