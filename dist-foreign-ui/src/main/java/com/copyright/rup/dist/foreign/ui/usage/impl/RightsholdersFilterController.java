package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.ui.common.domain.FakeDataGenerator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonFilterWindowController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterWidget;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

/**
 * Controller for the rightsholders filter window.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 1/19/17
 *
 * @author Aliaksandr Radkevich
 */
@Component("rightsholderFilterWindowController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RightsholdersFilterController implements ICommonFilterWindowController<Long, Rightsholder> {

    private IUsagesFilterWidget filterWidget;

    @Override
    public Collection<Rightsholder> loadBeans() {
        return FakeDataGenerator.getRROs();
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
    public void onSave(Set<Long> selectedItemsIds) {
        filterWidget.setSelectedRightsholders(selectedItemsIds);
    }

    @Override
    public Long getIdForBean(Rightsholder rightsholder) {
        return rightsholder.getAccountNumber();
    }

    @Override
    public void setFilterWidget(IUsagesFilterWidget filterWidget) {
        this.filterWidget = filterWidget;
    }

    @Override
    public void showFilterWindow() {
        FilterWindow<Long, Rightsholder> filterWindow =
            Windows.showFilterWindow(ForeignUi.getMessage("window.rightsholders_filter"), this,
                "name", "accountNumber");
        filterWindow.setSelectedItemsIds(filterWidget.getFilter().getRightsholdersAccountNumbers());
    }
}
