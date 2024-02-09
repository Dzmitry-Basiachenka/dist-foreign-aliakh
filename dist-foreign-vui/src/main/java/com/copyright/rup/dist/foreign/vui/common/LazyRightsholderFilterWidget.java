package com.copyright.rup.dist.foreign.vui.common;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditFilterController;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.CommonFilterWindow;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.ILazyFilterWindowController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.LazyFilterWindow;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.BaseItemsFilterWidget;

import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.function.ValueProvider;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Widget provides functionality for configuring items filter widget for rightsholders with lazy loading.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 05/02/18
 *
 * @author Ihar Suvorau
 */
public class LazyRightsholderFilterWidget extends BaseItemsFilterWidget<Rightsholder> implements
    ILazyFilterWindowController<Rightsholder> {

    private static final long serialVersionUID = -5886678815969060171L;

    private final ICommonAuditFilterController controller;
    private final Set<Rightsholder> selectedItemsIds = new HashSet<>();

    /**
     * Constructor.
     *
     * @param caption    for button
     * @param controller instance of {@link ICommonAuditFilterController}
     */
    public LazyRightsholderFilterWidget(String caption, ICommonAuditFilterController controller) {
        super(caption);
        this.controller = controller;
    }

    @Override
    public Collection<Rightsholder> loadBeans(String searchValue, int offset, int limit,
                                              List<QuerySortOrder> sortOrders) {
        return controller.loadBeans(searchValue, offset, limit, sortOrders);
    }

    @Override
    public int getBeansCount(String searchValue) {
        return controller.getBeansCount(searchValue);
    }

    @Override
    public ValueProvider<Rightsholder, String> getGridColumnValueProvider() {
        return rightsholder -> String.format("%s - %s", rightsholder.getAccountNumber(),
            StringUtils.defaultIfBlank(rightsholder.getName(), ForeignUi.getMessage("message.error.rro_not_found")));
    }

    @Override
    public void onComponentEvent(FilterSaveEvent<Rightsholder> event) {
        Set<Rightsholder> itemsIds = event.getSelectedItemsIds();
        selectedItemsIds.clear();
        if (CollectionUtils.isNotEmpty(itemsIds)) {
            selectedItemsIds.addAll(itemsIds);
        }
    }

    @Override
    public void reset() {
        selectedItemsIds.clear();
        super.reset();
    }

    @Override
    public CommonFilterWindow<Rightsholder> showFilterWindow() {
        LazyFilterWindow<Rightsholder> filterWindow =
            new LazyFilterWindow<>(ForeignUi.getMessage("window.filter_format", "Rightsholders"), this);
        Windows.showModalWindow(filterWindow);
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        filterWindow.setSearchPromptString(ForeignUi.getMessage("prompt.rightsholder"));
        VaadinUtils.addComponentStyle(filterWindow, "rightsholders-filter-window");
        return filterWindow;
    }
}
