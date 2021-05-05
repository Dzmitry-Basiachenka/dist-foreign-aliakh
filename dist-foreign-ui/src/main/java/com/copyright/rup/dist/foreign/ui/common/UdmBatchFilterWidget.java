package com.copyright.rup.dist.foreign.ui.common;

import com.copyright.rup.dist.foreign.domain.UdmBatch;
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
 * Widget provides functionality for configuring items filter widget for UDM batches.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 05/04/21
 *
 * @author Dzmitry Basiachenka
 */
public class UdmBatchFilterWidget extends BaseItemsFilterWidget<UdmBatch>
    implements IFilterWindowController<UdmBatch> {

    private final Supplier<List<UdmBatch>> supplier;
    private final Set<UdmBatch> selectedItemsIds = new HashSet<>();

    /**
     * Constructor.
     *
     * @param supplier {@link UdmBatch}es supplier
     */
    public UdmBatchFilterWidget(Supplier<List<UdmBatch>> supplier) {
        super(ForeignUi.getMessage("label.batches"));
        this.supplier = supplier;
    }

    @Override
    public void reset() {
        selectedItemsIds.clear();
        super.reset();
    }

    @Override
    public List<UdmBatch> loadBeans() {
        return supplier.get();
    }

    @Override
    public Class<UdmBatch> getBeanClass() {
        return UdmBatch.class;
    }

    @Override
    public String getBeanItemCaption(UdmBatch udmBatch) {
        return udmBatch.getName();
    }

    @Override
    public void onSave(FilterSaveEvent<UdmBatch> event) {
        Set<UdmBatch> itemsIds = event.getSelectedItemsIds();
        selectedItemsIds.clear();
        if (CollectionUtils.isNotEmpty(itemsIds)) {
            selectedItemsIds.addAll(itemsIds);
        }
    }

    @Override
    public FilterWindow<UdmBatch> showFilterWindow() {
        FilterWindow<UdmBatch> filterWindow =
            Windows.showFilterWindow(ForeignUi.getMessage("window.batches_filter"), this,
                (ValueProvider<UdmBatch, List<String>>) bean -> Arrays.asList(bean.getName()));
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        filterWindow.setSearchPromptString(ForeignUi.getMessage("prompt.batch"));
        VaadinUtils.addComponentStyle(filterWindow, "udm-batches-filter-window");
        return filterWindow;
    }
}
