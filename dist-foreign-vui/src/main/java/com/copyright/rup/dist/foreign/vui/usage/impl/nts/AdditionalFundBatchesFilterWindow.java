package com.copyright.rup.dist.foreign.vui.usage.impl.nts;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.IAdditionalFundBatchesFilterWindow;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.INtsUsageController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.FilterWindow;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.IFilterWindowController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.function.ValueProvider;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Window to filter batches to create Additional Fund.
 * <p/>
 * Copyright (C) 2024 copyright.com
 * <p/>
 * Date: 02/07/2024
 *
 * @author Aliaksandr Liakh
 */
public class AdditionalFundBatchesFilterWindow implements IAdditionalFundBatchesFilterWindow,
    IFilterWindowController<UsageBatch> {

    private static final long serialVersionUID = 4182035989051872505L;

    private final INtsUsageController controller;
    private FilterWindow<UsageBatch> filterWindow;

    /**
     * Constructor.
     *
     * @param controller instance of {@link INtsUsageController}
     */
    public AdditionalFundBatchesFilterWindow(INtsUsageController controller) {
        this.controller = controller;
    }

    @Override
    public Collection<UsageBatch> loadBeans() {
        return controller.getUsageBatchesForAdditionalFunds();
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
        List<UsageBatch> usageBatches = event.getSelectedItemsIds()
            .stream()
            .sorted(Comparator.comparing(UsageBatch::getUpdateDate).reversed())
            .collect(Collectors.toList());
        if (!usageBatches.isEmpty()) {
            Windows.showModalWindow(new AdditionalFundFilteredBatchesWindow(controller, usageBatches, this));
        } else {
            Windows.showNotificationWindow(ForeignUi.getMessage("message.usage.batches.empty"));
        }
    }

    @Override
    public void close() {
        filterWindow.close();
    }

    /**
     * @return shows on UI and returns {@link FilterWindow}.
     */
    public FilterWindow<UsageBatch> showFilterWindow() {
        filterWindow = new FilterWindow<>(ForeignUi.getMessage("window.batches_filter"), this, "Continue", null,
            (ValueProvider<UsageBatch, List<String>>) bean -> List.of(bean.getName()));
        filterWindow.setSearchPromptString(ForeignUi.getMessage("prompt.batch"));
        VaadinUtils.addComponentStyle(filterWindow, "batches-filter-window");
        Windows.showModalWindow(filterWindow);
        return filterWindow;
    }
}
