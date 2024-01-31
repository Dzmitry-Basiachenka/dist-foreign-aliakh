package com.copyright.rup.dist.foreign.vui.common;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.FilterWindow;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.IFilterWindowController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.BaseItemsFilterWidget;

import com.vaadin.flow.function.ValueProvider;

import org.apache.commons.collections4.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Widget provides functionality for configuring items filter widget for usage batches.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/15/2017
 *
 * @author Mikalai Bezmen
 */
public class UsageBatchFilterWidget extends BaseItemsFilterWidget<UsageBatch>
    implements IFilterWindowController<UsageBatch> {

    private static final long serialVersionUID = -2321559734957825265L;

    private final Supplier<List<UsageBatch>> supplier;
    private final Set<UsageBatch> selectedItemsIds = new HashSet<>();

    /**
     * Constructor.
     *
     * @param supplier {@link UsageBatch}es supplier
     */
    public UsageBatchFilterWidget(Supplier<List<UsageBatch>> supplier) {
        super(ForeignUi.getMessage("label.batches"));
        this.supplier = supplier;
    }

    @Override
    public void reset() {
        selectedItemsIds.clear();
        super.reset();
    }

    @Override
    public List<UsageBatch> loadBeans() {
        return supplier.get();
    }

    @Override
    public Class<UsageBatch> getBeanClass() {
        return UsageBatch.class;
    }

    @Override
    public String getBeanItemCaption(UsageBatch usageBatch) {
        return usageBatch.getName();
    }

    @Override
    public void onComponentEvent(FilterSaveEvent<UsageBatch> event) {
        Set<UsageBatch> itemsIds = event.getSelectedItemsIds();
        selectedItemsIds.clear();
        if (CollectionUtils.isNotEmpty(itemsIds)) {
            selectedItemsIds.addAll(itemsIds);
        }
    }

    @Override
    public FilterWindow<UsageBatch> showFilterWindow() {
        FilterWindow<UsageBatch> filterWindow =
            Windows.showFilterWindow(ForeignUi.getMessage("window.batches_filter"), this,
                (ValueProvider<UsageBatch, List<String>>) bean -> List.of(bean.getName()));
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        filterWindow.setSearchPromptString(ForeignUi.getMessage("prompt.batch"));
        VaadinUtils.addComponentStyle(filterWindow, "batches-filter-window");
        return filterWindow;
    }
}
