package com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.tabs.TabSheet;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Extend this class for controllers of widgets which contain {@link TabSheet}.
 * <p/>
 * Copyright (C) 2013 copyright.com
 * <p/>
 * Date: 11/19/2013
 *
 * @param <T> widget class
 * @author Aliaksandr Radkevich
 */
public abstract class TabController<T extends IWidget> extends CommonController<T> {

    /**
     * Refreshes currently selected tab if it is an instance of {@link IRefreshable}.
     */
    @Override
    public void refreshWidget() {
        List<Component> components = getTabSheet().getChildren().collect(Collectors.toList());
        components.forEach(component -> {
            if (component instanceof IRefreshable) {
                ((IRefreshable) component).refresh();
            }
        });
    }

    /**
     * @return {@link TabSheet} component of the widget.
     */
    protected abstract TabSheet getTabSheet();
}
