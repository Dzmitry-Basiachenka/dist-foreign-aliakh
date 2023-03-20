package com.copyright.rup.dist.foreign.ui.common;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.BaseItemsFilterWidget;

import com.vaadin.data.ValueProvider;

import org.apache.commons.collections4.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Widget provides functionality for configuring items filter widget for scenarios.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 06/16/20
 *
 * @author Ihar Suvorau
 */
public class ScenarioFilterWidget extends BaseItemsFilterWidget<Scenario>
    implements IFilterWindowController<Scenario> {

    private final Supplier<List<Scenario>> supplier;
    private final Set<Scenario> selectedItemsIds = new HashSet<>();

    /**
     * Controller.
     *
     * @param supplier {@link Scenario}s supplier
     */
    public ScenarioFilterWidget(Supplier<List<Scenario>> supplier) {
        super(ForeignUi.getMessage("label.scenarios"));
        this.supplier = supplier;
    }

    @Override
    public void reset() {
        selectedItemsIds.clear();
        super.reset();
    }

    @Override
    public List<Scenario> loadBeans() {
        return supplier.get();
    }

    @Override
    public Class<Scenario> getBeanClass() {
        return Scenario.class;
    }

    @Override
    public String getBeanItemCaption(Scenario scenario) {
        return scenario.getName();
    }

    @Override
    public void onSave(FilterSaveEvent<Scenario> event) {
        Set<Scenario> itemsIds = event.getSelectedItemsIds();
        selectedItemsIds.clear();
        if (CollectionUtils.isNotEmpty(itemsIds)) {
            selectedItemsIds.addAll(itemsIds);
        }
    }

    @Override
    public FilterWindow<Scenario> showFilterWindow() {
        FilterWindow<Scenario> filterWindow =
            Windows.showFilterWindow(ForeignUi.getMessage("window.scenarios_filter"), this,
                (ValueProvider<Scenario, List<String>>) scenario -> List.of(scenario.getName()));
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        filterWindow.setSearchPromptString(ForeignUi.getMessage("prompt.scenario"));
        VaadinUtils.addComponentStyle(filterWindow, "scenarios-filter-window");
        return filterWindow;
    }
}
