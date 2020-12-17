package com.copyright.rup.dist.foreign.ui.common;

import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.BaseItemsFilterWidget;
import com.google.common.collect.Sets;
import com.vaadin.data.ValueProvider;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Abstract items filter widget.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 12/17/2020
 *
 * @param <T> item type
 * @author Aliaksandr Liakh
 */
public abstract class CommonBaseItemsFilterWidget<T> extends BaseItemsFilterWidget<T>
    implements IFilterWindowController<T> {

    private final String windowCaption;
    private final String searchPrompt;
    private final String styleName;
    private final Class<T> clazz;
    private final ValueProvider<T, List<String>> valueProvider;
    private final Supplier<List<T>> supplier;
    private final Set<T> selectedItemsIds = Sets.newHashSet();

    /**
     * Constructor.
     *
     * @param widgetCaption window caption
     * @param windowCaption window caption
     * @param searchPrompt  search field prompt
     * @param styleName     style name
     * @param clazz         item class
     * @param valueProvider value provider
     * @param supplier      items supplier
     */
    public CommonBaseItemsFilterWidget(String widgetCaption, String windowCaption, String searchPrompt,
                                       String styleName, Class<T> clazz, ValueProvider<T, List<String>> valueProvider,
                                       Supplier<List<T>> supplier) {
        super(widgetCaption);
        this.windowCaption = windowCaption;
        this.searchPrompt = searchPrompt;
        this.styleName = styleName;
        this.clazz = clazz;
        this.valueProvider = valueProvider;
        this.supplier = supplier;
    }

    @Override
    public void reset() {
        selectedItemsIds.clear();
        super.reset();
    }

    @Override
    public List<T> loadBeans() {
        return supplier.get();
    }

    @Override
    public Class<T> getBeanClass() {
        return clazz;
    }

    @Override
    public void onSave(FilterSaveEvent<T> event) {
        Set<T> itemsIds = event.getSelectedItemsIds();
        selectedItemsIds.clear();
        if (CollectionUtils.isNotEmpty(itemsIds)) {
            selectedItemsIds.addAll(itemsIds);
        }
    }

    @Override
    public FilterWindow<T> showFilterWindow() {
        FilterWindow<T> filterWindow = Windows.showFilterWindow(windowCaption, this, valueProvider);
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        filterWindow.setSearchPromptString(searchPrompt);
        VaadinUtils.addComponentStyle(filterWindow, styleName);
        return filterWindow;
    }
}
