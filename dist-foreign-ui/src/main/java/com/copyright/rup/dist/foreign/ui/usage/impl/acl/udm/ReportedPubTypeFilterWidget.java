package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.ValueProvider;

import org.apache.commons.collections4.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Widget provides functionality for configuring items filter widget for reported publication types.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 06/10/2021
 *
 * @author Ihar Suvorau
 */
public class ReportedPubTypeFilterWidget extends BaseUdmItemsFilterWidget<String>
    implements IFilterWindowController<String> {

    private static final long serialVersionUID = -3041200122050151889L;

    private final Supplier<List<String>> supplier;
    private final Set<String> selectedItemsIds = new HashSet<>();

    /**
     * Constructor.
     *
     * @param supplier         {@link String}s list supplier
     * @param selectedItemsIds set of selected items
     */
    public ReportedPubTypeFilterWidget(Supplier<List<String>> supplier, Set<String> selectedItemsIds) {
        super(ForeignUi.getMessage("label.reported_pub_types"));
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
            Windows.showFilterWindow(ForeignUi.getMessage("window.reported_pub_types_filter"), this,
                (ValueProvider<String, List<String>>) List::of);
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        filterWindow.setSelectAllButtonVisible();
        filterWindow.setSearchPromptString(ForeignUi.getMessage("prompt.reported_pub_type"));
        VaadinUtils.addComponentStyle(filterWindow, "reported-pub-type-filter-window");
        return filterWindow;
    }

    public Set<String> getSelectedItemsIds() {
        return selectedItemsIds;
    }
}
