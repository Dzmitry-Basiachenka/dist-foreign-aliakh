package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.vaadin.widget.BaseItemsFilterWidget;

import com.vaadin.ui.Label;

/**
 * Abstract widget extends {@link BaseItemsFilterWidget} for UDM product family specific filter widgets.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/16/2021
 *
 * @param <T> bean type
 * @author Ihar Suvorau
 */
public abstract class BaseUdmItemsFilterWidget<T> extends BaseItemsFilterWidget<T> {

    /**
     * Constructor.
     *
     * @param caption button caption
     */
    public BaseUdmItemsFilterWidget(String caption) {
        super(caption);
    }

    /**
     * Sets label value as (selectedItemCount).
     *
     * @param selectedItemsCount selected items count
     */
    public void setLabelValue(int selectedItemsCount) {
        ((Label) getComponent(0)).setValue(String.format("(%s)", selectedItemsCount));
    }
}
