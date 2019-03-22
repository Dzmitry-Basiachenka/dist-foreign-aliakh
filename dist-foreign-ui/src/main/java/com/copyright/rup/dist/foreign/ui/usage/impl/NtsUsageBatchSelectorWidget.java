package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.ValueProvider;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Usage batch selector window for NTS specific logic.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/07/2019
 *
 * @author Ihar Suvorau
 */
public class NtsUsageBatchSelectorWidget implements IFilterWindowController<UsageBatch> {

    private final IUsagesController usagesController;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IUsagesController}
     */
    public NtsUsageBatchSelectorWidget(IUsagesController controller) {
        usagesController = controller;
    }

    @Override
    public Collection<UsageBatch> loadBeans() {
        return usagesController.getUsageBatches(usagesController.getSelectedProductFamily());
    }

    @Override
    public Class<UsageBatch> getBeanClass() {
        return UsageBatch.class;
    }

    @Override
    public String getBeanItemCaption(UsageBatch bean) {
        return bean.getName();
    }

    @Override
    public void onSave(FilterSaveEvent<UsageBatch> event) {
        Windows.showModalWindow(new WorkClassificationWindow(
            event.getSelectedItemsIds().stream().map(UsageBatch::getId).collect(Collectors.toSet()),
            usagesController.getWorkClassificationController()));
    }

    /**
     * @return shows on UI and returns {@link FilterWindow}.
     */
    public FilterWindow<UsageBatch> showFilterWindow() {
        FilterWindow<UsageBatch> filterWindow =
            Windows.showFilterWindow(ForeignUi.getMessage("window.batches_selector"), this,
                (ValueProvider<UsageBatch, List<String>>) bean -> Collections.singletonList(bean.getName()));
        filterWindow.setSearchPromptString(ForeignUi.getMessage("prompt.batch"));
        return filterWindow;
    }
}
