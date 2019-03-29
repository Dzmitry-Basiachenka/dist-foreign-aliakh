package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.google.common.collect.Lists;
import com.vaadin.data.ValueProvider;
import com.vaadin.ui.Button.ClickListener;

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
class WithdrawnBatchFilterWindow extends FilterWindow<UsageBatch> {

    /**
     * Constructor.
     *
     * @param controller instance of {@link IFilterWindowController}
     */
    WithdrawnBatchFilterWindow(IFilterWindowController<UsageBatch> controller) {
        super(ForeignUi.getMessage("window.batches_filter"), controller,
            "Continue", null,
            (ValueProvider<UsageBatch, List<String>>) batch -> Lists.newArrayList(batch.getName()));
        this.setSearchPromptString(ForeignUi.getMessage("prompt.batch"));
        VaadinUtils.addComponentStyle(this, "batches-filter-window");
    }

    /**
     * Updates {@link ClickListener} for the Save button.
     *
     * @param action instance of {@link Runnable}
     */
    void updateSaveButtonClickListener(Runnable action) {
        getSaveButtonRegistration().remove();
        getSaveButton().addClickListener((ClickListener) event -> {
            fireFilterSaveEvent();
            action.run();
        });
    }
}
