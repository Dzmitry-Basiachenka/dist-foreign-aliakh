package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.BaseUdmItemsFilterWidget;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.ValueProvider;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Widget provides functionality for configuring items filter widget for last value periods.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 09/23/2021
 *
 * @author Aliaksandr Liakh
 */
public class LastValuePeriodFilterWidget extends BaseUdmItemsFilterWidget<String>
    implements IFilterWindowController<String> {

    private static final long serialVersionUID = -1563793541835252250L;

    private final Supplier<List<String>> supplier;
    private final Set<String> selectedItemsIds = new HashSet<>();

    /**
     * Constructor.
     *
     * @param supplier         {@link String}s list supplier
     * @param selectedItemsIds set of selected items
     */
    public LastValuePeriodFilterWidget(Supplier<List<String>> supplier, Set<String> selectedItemsIds) {
        super(ForeignUi.getMessage("label.last_value_periods"));
        this.supplier = supplier;
        this.selectedItemsIds.addAll(selectedItemsIds);
        super.setLabelValue(selectedItemsIds.size());
    }

    @Override
    public void reset() {
        super.reset();
        selectedItemsIds.clear();
    }

    @Override
    public List<String> loadBeans() {
        List<String> lastValuePeriods = new ArrayList<>(supplier.get());
        lastValuePeriods.add(FilterOperatorEnum.IS_NULL.name());
        lastValuePeriods.add(FilterOperatorEnum.IS_NOT_NULL.name());
        return lastValuePeriods;
    }

    @Override
    public Class<String> getBeanClass() {
        return String.class;
    }

    @Override
    public String getBeanItemCaption(String lastValuePeriod) {
        return lastValuePeriod;
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
    public CommonFilterWindow<String> showFilterWindow() {
        FilterWindow<String> filterWindow = new FilterWindow<>(ForeignUi.getMessage("window.last_value_periods_filter"),
            this, (ValueProvider<String, List<String>>) List::of);
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        filterWindow.setSelectAllButtonVisible();
        filterWindow.setSearchPromptString(ForeignUi.getMessage("prompt.last_value_period"));
        VaadinUtils.addComponentStyle(filterWindow, "last-value-period-filter-window");
        Windows.showModalWindow(filterWindow);
        return filterWindow;
    }
}
