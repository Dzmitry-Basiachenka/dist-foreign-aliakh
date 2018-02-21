package com.copyright.rup.dist.foreign.ui.common;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.widget.BaseItemsFilterWidget;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Widget provides functionality for configuring items filter widget for product families.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 2/19/18
 *
 * @author Uladzislau Shalamitski
 */
public class ProductFamilyFilterWidget extends BaseItemsFilterWidget<String, String>
    implements IFilterWindowController<String, String> {

    private Set<String> selectedItemsIds;
    private final Supplier<List<String>> supplier;

    /**
     * Controller.
     *
     * @param supplier product families supplier
     */
    public ProductFamilyFilterWidget(Supplier<List<String>> supplier) {
        super(ForeignUi.getMessage("label.product_families"));
        this.supplier = supplier;
    }

    @Override
    public void reset() {
        selectedItemsIds = null;
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
    public String getBeanItemCaption(String productFamily) {
        return productFamily;
    }

    @Override
    public void onSave(FilterSaveEvent<String> event) {
        selectedItemsIds = event.getSelectedItemsIds();
    }

    @Override
    public String getIdForBean(String productFamily) {
        return productFamily;
    }

    @Override
    public FilterWindow<String, String> showFilterWindow() {
        FilterWindow<String, String> filterWindow =
            Windows.showFilterWindow(ForeignUi.getMessage("window.caption.product_family_filter"), this);
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        filterWindow.setWidth(350, Unit.PIXELS);
        VaadinUtils.addComponentStyle(filterWindow, "product-families-filter-window");
        return filterWindow;
    }
}
