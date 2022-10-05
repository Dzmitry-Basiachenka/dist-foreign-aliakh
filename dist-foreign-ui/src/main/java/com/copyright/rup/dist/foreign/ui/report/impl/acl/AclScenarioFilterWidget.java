package com.copyright.rup.dist.foreign.ui.report.impl.acl;

import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.BaseItemsFilterWidget;

import com.vaadin.data.ValueProvider;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Widget provides functionality for configuring items filter widget for ACL Scenarios.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/05/2022
 *
 * @author Ihar Suvorau
 */
public class AclScenarioFilterWidget extends BaseItemsFilterWidget<AclScenario>
    implements IFilterWindowController<AclScenario> {

    private final Supplier<List<AclScenario>> supplier;
    private final Set<AclScenario> scenarios = new HashSet<>();

    /**
     * Constructor.
     *
     * @param supplier period supplier
     */
    public AclScenarioFilterWidget(Supplier<List<AclScenario>> supplier) {
        super(ForeignUi.getMessage("label.scenarios"));
        this.supplier = supplier;
    }

    @Override
    public void reset() {
        super.reset();
        scenarios.clear();
    }

    @Override
    public List<AclScenario> loadBeans() {
        return supplier.get();
    }

    @Override
    public Class<AclScenario> getBeanClass() {
        return AclScenario.class;
    }

    @Override
    public String getBeanItemCaption(AclScenario scenario) {
        return scenario.getName();
    }

    @Override
    public void onSave(FilterSaveEvent<AclScenario> event) {
        Set<AclScenario> itemsIds = event.getSelectedItemsIds();
        scenarios.clear();
        if (CollectionUtils.isNotEmpty(itemsIds)) {
            scenarios.addAll(itemsIds);
        }
    }

    @Override
    public FilterWindow<AclScenario> showFilterWindow() {
        FilterWindow<AclScenario> filterWindow =
            Windows.showFilterWindow(ForeignUi.getMessage("window.scenarios_filter"), this,
                (ValueProvider<AclScenario, List<String>>) scenario -> Collections.singletonList(scenario.getName()));
        filterWindow.setSelectedItemsIds(scenarios);
        filterWindow.setSelectAllButtonVisible();
        filterWindow.setSearchPromptString(ForeignUi.getMessage("prompt.scenario"));
        VaadinUtils.addComponentStyle(filterWindow, "acl-scenarios-filter-window");
        return filterWindow;
    }

    public Set<AclScenario> getSelectedItemsIds() {
        return scenarios;
    }
}
