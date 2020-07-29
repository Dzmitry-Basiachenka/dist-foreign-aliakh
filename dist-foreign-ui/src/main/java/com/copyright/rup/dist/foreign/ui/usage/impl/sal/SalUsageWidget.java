package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageWidget;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.HorizontalLayout;

/**
 * Usage widget for SAL product families.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/28/2020
 *
 * @author Uladzislau Shalamitski
 */
public class SalUsageWidget extends CommonUsageWidget implements ISalUsageWidget {

    @Override
    public IMediator initMediator() {
        return new SalUsageMediator();
    }

    @Override
    protected void addGridColumns() {
        //TODO: add grid columns
    }

    @Override
    protected HorizontalLayout initButtonsLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(layout, "usages-buttons");
        return layout;
    }
}
