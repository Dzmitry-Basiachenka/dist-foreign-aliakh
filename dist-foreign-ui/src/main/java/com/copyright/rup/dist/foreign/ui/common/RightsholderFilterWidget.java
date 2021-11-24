package com.copyright.rup.dist.foreign.ui.common;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.BaseItemsFilterWidget;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.vaadin.data.ValueProvider;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Widget provides functionality for configuring items filter widget for rightsholders.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 2/15/17
 *
 * @author Mikalai Bezmen
 */
public class RightsholderFilterWidget extends BaseItemsFilterWidget<Rightsholder>
    implements IFilterWindowController<Rightsholder> {

    private final String searchPrompt;
    private final String caption;
    private final String rightsholderNotFoundString;
    private final Supplier<List<Rightsholder>> supplier;
    private final Set<Rightsholder> selectedItemsIds = Sets.newHashSet();

    /**
     * Constructor.
     *
     * @param caption                    widget caption
     * @param searchPrompt               search field prompt
     * @param rightsholderNotFoundString a string to be shown as righstholder name if the name is blank
     * @param supplier                   {@link Rightsholder}s supplier
     */
    public RightsholderFilterWidget(String caption, String searchPrompt, String rightsholderNotFoundString,
                                    Supplier<List<Rightsholder>> supplier) {
        super(caption);
        this.caption = caption;
        this.supplier = supplier;
        this.searchPrompt = searchPrompt;
        this.rightsholderNotFoundString = rightsholderNotFoundString;
    }

    @Override
    public void reset() {
        selectedItemsIds.clear();
        super.reset();
    }

    @Override
    public List<Rightsholder> loadBeans() {
        return supplier.get();
    }

    @Override
    public Class<Rightsholder> getBeanClass() {
        return Rightsholder.class;
    }

    @Override
    public String getBeanItemCaption(Rightsholder rightsholder) {
        return String.format("%s - %s", rightsholder.getAccountNumber(),
            StringUtils.defaultIfBlank(rightsholder.getName(), rightsholderNotFoundString));
    }

    @Override
    public void onSave(FilterSaveEvent<Rightsholder> event) {
        Set<Rightsholder> itemsIds = event.getSelectedItemsIds();
        selectedItemsIds.clear();
        if (CollectionUtils.isNotEmpty(itemsIds)) {
            selectedItemsIds.addAll(itemsIds);
        }
    }

    @Override
    public FilterWindow<Rightsholder> showFilterWindow() {
        FilterWindow<Rightsholder> filterWindow =
            Windows.showFilterWindow(ForeignUi.getMessage("window.filter_format", caption), this,
                (ValueProvider<Rightsholder, List<String>>) rightsholder ->
                    Lists.newArrayList(rightsholder.getName(), rightsholder.getAccountNumber().toString()));
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        filterWindow.setSearchPromptString(searchPrompt);
        VaadinUtils.addComponentStyle(filterWindow, "rightsholders-filter-window");
        return filterWindow;
    }
}
