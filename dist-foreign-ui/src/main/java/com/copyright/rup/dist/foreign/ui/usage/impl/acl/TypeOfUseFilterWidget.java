package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.BaseItemsFilterWidget;

import com.vaadin.data.ValueProvider;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Widget provides functionality for configuring items filter widget for Type of Uses.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 06/14/2021
 *
 * @author Ihar Suvorau
 */
public class TypeOfUseFilterWidget extends BaseItemsFilterWidget<String>
    implements IFilterWindowController<String> {

    private final Supplier<List<String>> supplier;
    private final Set<String> selectedItemsIds = new HashSet<>();

    /**
     * Constructor.
     *
     * @param supplier {@link String}s list supplier
     */
    public TypeOfUseFilterWidget(Supplier<List<String>> supplier) {
        super(ForeignUi.getMessage("label.types_of_use"));
        this.supplier = supplier;
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
            Windows.showFilterWindow(ForeignUi.getMessage("window.types_of_use_filter"), this,
                (ValueProvider<String, List<String>>) Arrays::asList);
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        filterWindow.setSearchPromptString(ForeignUi.getMessage("prompt.type_of_use"));
        VaadinUtils.addComponentStyle(filterWindow, "type-of-use-filter-window");
        return filterWindow;
    }
}