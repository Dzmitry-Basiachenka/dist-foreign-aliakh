package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.proxy;

import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.UdmCommonFilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;

import com.vaadin.data.ValueProvider;

import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * Represents filter window for UDM proxy values.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 11/27/21
 *
 * @param <T> type of items
 * @author Uladzislau Shalamitski
 */
public class UdmCommonProxyValueFilterWindow<T> extends UdmCommonFilterWindow<T> {

    /**
     * Constructor.
     *
     * @param caption    window caption
     * @param controller {@link IFilterWindowController} instance
     * @param values     searchable listDataProvider properties.
     */
    @SafeVarargs
    public UdmCommonProxyValueFilterWindow(String caption, IFilterWindowController<T> controller,
                                           ValueProvider<T, List<String>>... values) {
        super(caption, controller, values);
    }

    @Override
    public void setSelectedItemsIds(Set<T> selectedItemsIds) {
        if (CollectionUtils.isNotEmpty(selectedItemsIds)) {
            getCheckBoxGroup().setValue(selectedItemsIds);
        } else {
            selectAll();
        }
    }
}
