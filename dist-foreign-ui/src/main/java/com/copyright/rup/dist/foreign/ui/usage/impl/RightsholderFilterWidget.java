package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.widget.BaseItemsFilterWidget;

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
public class RightsholderFilterWidget extends BaseItemsFilterWidget<Long, Rightsholder>
    implements IFilterWindowController<Long, Rightsholder> {

    private Set<Long> selectedItemsIds;
    private final String caption;
    private final Supplier<List<Rightsholder>> supplier;

    /**
     * Controller.
     *
     * @param caption  window caption
     * @param supplier {@link Rightsholder}s supplier
     */
    public RightsholderFilterWidget(String caption, Supplier<List<Rightsholder>> supplier) {
        super(caption);
        this.caption = caption;
        this.supplier = supplier;
    }

    @Override
    public void reset() {
        selectedItemsIds = null;
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
        String rightsholderName = rightsholder.getName();
        return String.format("%s - %s", rightsholder.getAccountNumber(),
            StringUtils.isNotBlank(rightsholderName)
                ? rightsholder.getName()
                : ForeignUi.getMessage("message.error.rro_not_found"));
    }

    @Override
    public void onSave(FilterSaveEvent<Long> event) {
        selectedItemsIds = event.getSelectedItemsIds();
    }

    @Override
    public Long getIdForBean(Rightsholder rightsholder) {
        return rightsholder.getAccountNumber();
    }

    @Override
    public FilterWindow<Long, Rightsholder> showFilterWindow() {
        FilterWindow<Long, Rightsholder> filterWindow =
            Windows.showFilterWindow(ForeignUi.getMessage("window.filter_format", caption), this, "name",
                "accountNumber");
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        filterWindow.setSearchPromptString(ForeignUi.getMessage("prompt.rightsholder"));
        VaadinUtils.addComponentStyle(filterWindow, "rightsholders-filter-window");
        return filterWindow;
    }
}
