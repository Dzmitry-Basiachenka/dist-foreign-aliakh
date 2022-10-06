package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm;

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
 * Widget provides functionality for configuring items filter widget for Reported Type of Uses.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 10/07/2022
 *
 * @author Aliaksandr Liakh
 */
public class ReportedTypeOfUseFilterWidget extends BaseUdmItemsFilterWidget<String>
    implements IFilterWindowController<String> {

    private final Supplier<List<String>> supplier;
    private final Set<String> selectedItemsIds = new HashSet<>();

    /**
     * Constructor.
     *
     * @param supplier         {@link String}s list supplier
     * @param selectedItemsIds set of selected items
     */
    public ReportedTypeOfUseFilterWidget(Supplier<List<String>> supplier, Set<String> selectedItemsIds) {
        super(ForeignUi.getMessage("label.reported_types_of_use"));
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
    public List<String> loadBeans() {
        return supplier.get();
    }

    @Override
    public Class<String> getBeanClass() {
        return String.class;
    }

    @Override
    public String getBeanItemCaption(String reportedPubType) {
        return reportedPubType;
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
    public FilterWindow<String> showFilterWindow() {
        FilterWindow<String> filterWindow =
            Windows.showFilterWindow(ForeignUi.getMessage("window.reported_types_of_use_filter"), this,
                (ValueProvider<String, List<String>>) Arrays::asList);
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        filterWindow.setSelectAllButtonVisible();
        filterWindow.setSearchPromptString(ForeignUi.getMessage("prompt.reported_type_of_use"));
        VaadinUtils.addComponentStyle(filterWindow, "reported-type-of-use-filter-window");
        return filterWindow;
    }

    public Set<String> getSelectedItemsIds() {
        return selectedItemsIds;
    }
}
