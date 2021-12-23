package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.UdmCommonFilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;

import com.vaadin.data.ValueProvider;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;
import java.util.Set;

/**
 * Represents filter window for UDM last value periods.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 12/20/21
 *
 * @author Dzmitry Basiachenka
 */
public class UdmLastValuePeriodFilterWindow extends UdmCommonFilterWindow<String> {

    /**
     * Constructor.
     *
     * @param caption    window caption
     * @param controller {@link IFilterWindowController} instance
     * @param values     searchable listDataProvider properties.
     */
    @SafeVarargs
    public UdmLastValuePeriodFilterWindow(String caption, IFilterWindowController<String> controller,
                                          ValueProvider<String, List<String>>... values) {
        super(caption, controller, values);
    }

    @Override
    protected void selectAll() {
        Set<String> items = getSelectedItemsIds();
        if (!items.contains(FilterOperatorEnum.IS_NULL.name())
            && !items.contains(FilterOperatorEnum.IS_NOT_NULL.name())) {
            getFilterItems()
                .stream()
                .filter(NumberUtils::isDigits)
                .forEach(item -> getCheckBoxGroup().select(item));
        }
    }
}
