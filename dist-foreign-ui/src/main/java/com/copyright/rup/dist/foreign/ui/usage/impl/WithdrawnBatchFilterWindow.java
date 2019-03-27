package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.google.common.collect.Lists;
import com.vaadin.data.ValueProvider;

import java.util.List;

/**
 * Window to filter batches to create NTS withdrawn fund pool.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 03/27/2019
 *
 * @author Aliaksandr Liakh
 */
public class WithdrawnBatchFilterWindow extends FilterWindow<UsageBatch> {

    /**
     * Constructor.
     *
     * @param controller instance of {@link IFilterWindowController}
     */
    public WithdrawnBatchFilterWindow(IFilterWindowController<UsageBatch> controller) {
        super(ForeignUi.getMessage("window.batches_filter"), controller,
            "Continue", null,
            (ValueProvider<UsageBatch, List<String>>) batch -> Lists.newArrayList(batch.getName()));
        this.setSearchPromptString(ForeignUi.getMessage("prompt.batch"));
        VaadinUtils.addComponentStyle(this, "batches-filter-window");
    }
}
