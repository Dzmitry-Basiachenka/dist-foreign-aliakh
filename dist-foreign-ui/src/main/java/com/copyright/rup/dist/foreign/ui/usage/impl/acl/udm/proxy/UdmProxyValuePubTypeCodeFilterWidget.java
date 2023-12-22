package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.proxy;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmProxyValueFilterController;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
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
 * Implementation of UDM proxy value pub type filter widget.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 11/27/21
 *
 * @author Uladzislau Shalamitski
 */
public class UdmProxyValuePubTypeCodeFilterWidget extends BaseItemsFilterWidget<String> implements
    CommonFilterWindow.IFilterSaveListener<String>, IFilterWindowController<String> {

    private static final long serialVersionUID = 6849139382509632227L;

    private final IUdmProxyValueFilterController controller;
    private final Set<String> selectedItemsIds = new HashSet<>();

    /**
     * Constructor.
     *
     * @param controller instance of {@link IUdmProxyValueFilterController}
     */
    public UdmProxyValuePubTypeCodeFilterWidget(IUdmProxyValueFilterController controller) {
        super(ForeignUi.getMessage("label.pub_type_codes"));
        this.controller = controller;
    }

    @Override
    public Collection<String> loadBeans() {
        return controller.getPublicationTypeCodes();
    }

    @Override
    public Class<String> getBeanClass() {
        return String.class;
    }

    @Override
    public String getBeanItemCaption(String bean) {
        return bean;
    }

    @Override
    public void onSave(FilterSaveEvent<String> event) {
        Set<String> itemsIds = event.getSelectedItemsIds();
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
    public FilterWindow<String> showFilterWindow() {
        UdmCommonProxyValueFilterWindow<String> filterWindow =
            new UdmCommonProxyValueFilterWindow<>(ForeignUi.getMessage("window.pub_type_codes_filter"), this,
                (ValueProvider<String, List<String>>) bean -> List.of(String.valueOf(bean)));
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        filterWindow.setSelectAllButtonVisible();
        filterWindow.setSearchPromptString(ForeignUi.getMessage("prompt.pub_type_code"));
        VaadinUtils.addComponentStyle(filterWindow, "udm-proxy-value-pub-type-code-filter-window");
        Windows.showModalWindow(filterWindow);
        return filterWindow;
    }
}
