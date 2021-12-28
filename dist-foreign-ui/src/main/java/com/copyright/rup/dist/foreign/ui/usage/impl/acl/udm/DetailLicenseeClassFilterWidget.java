package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm;

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
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
 * Widget provides functionality for configuring items filter widget for detail Licensee Classes.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 06/10/2021
 *
 * @author Ihar Suvorau
 */
public class DetailLicenseeClassFilterWidget extends BaseUdmItemsFilterWidget<DetailLicenseeClass>
    implements IFilterWindowController<DetailLicenseeClass> {

    private final Supplier<List<DetailLicenseeClass>> supplier;
    private final Set<DetailLicenseeClass> selectedItemsIds = new HashSet<>();

    /**
     * Constructor.
     *
     * @param supplier         {@link DetailLicenseeClass}es list supplier
     * @param selectedItemsIds set of selected items
     */
    public DetailLicenseeClassFilterWidget(Supplier<List<DetailLicenseeClass>> supplier,
                                           Set<DetailLicenseeClass> selectedItemsIds) {
        super(ForeignUi.getMessage("label.detail_licensee_classes"));
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
    public List<DetailLicenseeClass> loadBeans() {
        return supplier.get();
    }

    @Override
    public Class<DetailLicenseeClass> getBeanClass() {
        return DetailLicenseeClass.class;
    }

    @Override
    public String getBeanItemCaption(DetailLicenseeClass detailLicenseeClass) {
        return String.format("%s - %s", detailLicenseeClass.getId(), detailLicenseeClass.getDescription());
    }

    @Override
    public void onSave(FilterSaveEvent<DetailLicenseeClass> event) {
        Set<DetailLicenseeClass> itemsIds = event.getSelectedItemsIds();
        selectedItemsIds.clear();
        if (CollectionUtils.isNotEmpty(itemsIds)) {
            selectedItemsIds.addAll(itemsIds);
        }
    }

    @Override
    public FilterWindow<DetailLicenseeClass> showFilterWindow() {
        FilterWindow<DetailLicenseeClass> filterWindow =
            Windows.showFilterWindow(ForeignUi.getMessage("window.detail_licensee_class_filter"), this,
                (ValueProvider<DetailLicenseeClass, List<String>>) licenseeClass ->
                    Arrays.asList(licenseeClass.getId().toString(), licenseeClass.getDescription()));
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        filterWindow.setSelectAllButtonVisible();
        filterWindow.setSearchPromptString(ForeignUi.getMessage("prompt.detail_licensee_class"));
        VaadinUtils.addComponentStyle(filterWindow, "detail-licensee-class-filter-window");
        return filterWindow;
    }
}
