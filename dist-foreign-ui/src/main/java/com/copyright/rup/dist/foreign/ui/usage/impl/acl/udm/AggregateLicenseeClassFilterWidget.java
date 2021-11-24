package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.ValueProvider;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Widget provides functionality for configuring items filter widget for Aggregate Licensee Classes.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 09/01/2021
 *
 * @author Uladzislau Shalamitski
 */
public class AggregateLicenseeClassFilterWidget extends BaseUdmItemsFilterWidget<AggregateLicenseeClass>
    implements IFilterWindowController<AggregateLicenseeClass> {

    private final Supplier<List<AggregateLicenseeClass>> supplier;
    private final Set<AggregateLicenseeClass> selectedItemsIds = new HashSet<>();

    /**
     * Constructor.
     *
     * @param supplier         {@link AggregateLicenseeClass}es list supplier
     * @param selectedItemsIds set of selected items
     */
    public AggregateLicenseeClassFilterWidget(Supplier<List<AggregateLicenseeClass>> supplier,
                                              Set<AggregateLicenseeClass> selectedItemsIds) {
        super(ForeignUi.getMessage("label.aggregate_licensee_classes"));
        this.supplier = supplier;
        this.selectedItemsIds.addAll(selectedItemsIds);
        setLabelValue(selectedItemsIds.size());
    }

    @Override
    public void reset() {
        super.reset();
        selectedItemsIds.clear();
    }

    @Override
    public List<AggregateLicenseeClass> loadBeans() {
        return supplier.get();
    }

    @Override
    public Class<AggregateLicenseeClass> getBeanClass() {
        return AggregateLicenseeClass.class;
    }

    @Override
    public String getBeanItemCaption(AggregateLicenseeClass aggregateLicenseeClass) {
        return String.format("%s - %s", aggregateLicenseeClass.getId(), aggregateLicenseeClass.getDescription());
    }

    @Override
    public void onSave(FilterSaveEvent<AggregateLicenseeClass> event) {
        Set<AggregateLicenseeClass> itemsIds = event.getSelectedItemsIds();
        selectedItemsIds.clear();
        if (CollectionUtils.isNotEmpty(itemsIds)) {
            selectedItemsIds.addAll(itemsIds);
        }
    }

    @Override
    public FilterWindow<AggregateLicenseeClass> showFilterWindow() {
        FilterWindow<AggregateLicenseeClass> filterWindow =
            Windows.showFilterWindow(ForeignUi.getMessage("window.aggregate_licensee_class_filter"), this,
                (ValueProvider<AggregateLicenseeClass, List<String>>) licenseeClass ->
                    Arrays.asList(licenseeClass.getId().toString(), licenseeClass.getDescription()));
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        filterWindow.setSearchPromptString(ForeignUi.getMessage("prompt.aggregate_licensee_class"));
        VaadinUtils.addComponentStyle(filterWindow, "baseline-aggregate-licensee-class-filter-window");
        return filterWindow;
    }
}
