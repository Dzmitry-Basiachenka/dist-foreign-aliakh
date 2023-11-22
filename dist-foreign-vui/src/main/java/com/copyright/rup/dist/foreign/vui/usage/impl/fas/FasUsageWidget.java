package com.copyright.rup.dist.foreign.vui.usage.impl.fas;

import com.copyright.rup.dist.foreign.vui.usage.api.fas.IFasUsageWidget;
import com.copyright.rup.dist.foreign.vui.usage.impl.CommonUsageWidget;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 * Usage widget for FAS and FAS2 product families.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/5/19
 *
 * @author Uladzislau Shalamitski
 */
public class FasUsageWidget extends CommonUsageWidget implements IFasUsageWidget {

    @Override
    public FasUsageWidget init() {
        return this;
    }

    @Override
    protected HorizontalLayout initButtonsLayout() {
        //TODO{vaadin23} will implement later
        return new HorizontalLayout();
    }
}
