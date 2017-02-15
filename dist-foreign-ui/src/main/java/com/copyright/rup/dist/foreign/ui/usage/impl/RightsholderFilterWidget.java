package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterController;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.widget.BaseItemsFilterWidget;

import java.util.List;
import java.util.Set;

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
    private IUsagesFilterController controller;

    /**
     * Controller.
     *
     * @param controller controller of UsagesFilterController
     */
    public RightsholderFilterWidget(IUsagesFilterController controller) {
        super(ForeignUi.getMessage("label.rightsholders"));
        this.controller = controller;
    }

    @Override
    public void reset() {
        selectedItemsIds = null;
        super.reset();
    }

    @Override
    public List<Rightsholder> loadBeans() {
        return controller.getRros();
    }

    @Override
    public Class<Rightsholder> getBeanClass() {
        return Rightsholder.class;
    }

    @Override
    public String getBeanItemCaption(Rightsholder rightsholder) {
        return String.format("%s - %s", rightsholder.getAccountNumber(), rightsholder.getName());
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
            Windows.showFilterWindow(ForeignUi.getMessage("window.rros_filter"), this, "name", "accountNumber");
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        filterWindow.setSearchPromptString("Enter RRO Name/Account #");
        VaadinUtils.addComponentStyle(filterWindow, "rightsholders-filter-window");
        return filterWindow;
    }
}
