package com.copyright.rup.dist.foreign.vui.usage.impl.nts;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.INtsUsageController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.FilterWindow;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.IFilterWindowController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.function.ValueProvider;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Usage batch selector window for NTS specific logic.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/07/2019
 *
 * @author Ihar Suvorau
 */
public class NtsUsageBatchSelectorWidget implements IFilterWindowController<UsageBatch> {

    private static final long serialVersionUID = 4182035989051872505L;

    private final INtsUsageController usagesController;

    /**
     * Constructor.
     *
     * @param controller instance of {@link INtsUsageController}
     */
    public NtsUsageBatchSelectorWidget(INtsUsageController controller) {
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
    public void onComponentEvent(FilterSaveEvent<UsageBatch> event) {
        Windows.showModalWindow(new WorkClassificationWindow(
            event.getSelectedItemsIds().stream().map(UsageBatch::getId).collect(Collectors.toSet()),
            usagesController.getWorkClassificationController()));
    }

    /**
     * @return shows on UI and returns {@link FilterWindow}.
     */
    public FilterWindow<UsageBatch> showFilterWindow() {
        var filterWindow = new FilterWindow<>(ForeignUi.getMessage("window.batches_filter"), this, "Continue", null,
            (ValueProvider<UsageBatch, List<String>>) bean -> List.of(bean.getName()));
        filterWindow.setSearchPromptString(ForeignUi.getMessage("prompt.batch"));
        VaadinUtils.addComponentStyle(filterWindow, "batches-filter-window");
        Windows.showModalWindow(filterWindow);
        return filterWindow;
    }
}
