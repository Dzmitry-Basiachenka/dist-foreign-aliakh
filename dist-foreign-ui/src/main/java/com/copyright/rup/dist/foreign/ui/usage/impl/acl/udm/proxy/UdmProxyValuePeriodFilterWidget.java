package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.proxy;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmProxyValueFilterController;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.BaseItemsFilterWidget;

import com.vaadin.data.ValueProvider;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of filter window for UDM proxy value periods.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 11/27/21
 *
 * @author Uladzislau Shalamitski
 */
public class UdmProxyValuePeriodFilterWidget extends BaseItemsFilterWidget<Integer> implements
    CommonFilterWindow.IFilterSaveListener<Integer>, IFilterWindowController<Integer> {

    private final IUdmProxyValueFilterController controller;
    private final Set<Integer> selectedItemsIds = new HashSet<>();

    /**
     * Constructor.
     *
     * @param controller instance of {@link IUdmProxyValueFilterController}
     */
    public UdmProxyValuePeriodFilterWidget(IUdmProxyValueFilterController controller) {
        super(ForeignUi.getMessage("label.periods"));
        this.controller = controller;
    }

    @Override
    public Collection<Integer> loadBeans() {
        return controller.getPeriods();
    }

    @Override
    public Class<Integer> getBeanClass() {
        return Integer.class;
    }

    @Override
    public String getBeanItemCaption(Integer bean) {
        return bean.toString();
    }

    @Override
    public void onSave(FilterSaveEvent<Integer> event) {
        Set<Integer> itemsIds = event.getSelectedItemsIds();
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
    public CommonFilterWindow<Integer> showFilterWindow() {
        UdmCommonProxyValueFilterWindow<Integer> filterWindow =
            new UdmCommonProxyValueFilterWindow<>(ForeignUi.getMessage("window.periods_filter"), this,
                (ValueProvider<Integer, List<String>>) bean -> List.of(String.valueOf(bean)));
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        filterWindow.setSelectAllButtonVisible();
        filterWindow.setSearchPromptString(ForeignUi.getMessage("prompt.period"));
        VaadinUtils.addComponentStyle(filterWindow, "udm-proxy-value-period-filter-window");
        Windows.showModalWindow(filterWindow);
        return filterWindow;
    }
}
