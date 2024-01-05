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

    private static final long serialVersionUID = 4861949738674041432L;

    private final Supplier<List<AclScenario>> supplier;
    private final Set<AclScenario> scenarios = new HashSet<>();
    private final String widgetCaptionLabelName;

    /**
     * Constructor.
     *
     * @param supplier                     period supplier
     * @param widgetButtonCaptionLabelName widget button caption label name
     * @param widgetCaptionLabelName       widget caption label name
     */
    public AclScenarioFilterWidget(Supplier<List<AclScenario>> supplier, String widgetButtonCaptionLabelName,
                                   String widgetCaptionLabelName) {
        super(ForeignUi.getMessage(widgetButtonCaptionLabelName));
        this.supplier = supplier;
        this.widgetCaptionLabelName = widgetCaptionLabelName;
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
            Windows.showFilterWindow(ForeignUi.getMessage(widgetCaptionLabelName), this,
                (ValueProvider<AclScenario, List<String>>) scenario -> List.of(scenario.getName()));
        filterWindow.setSelectedItemsIds(scenarios);
        filterWindow.setSelectAllButtonVisible();
        filterWindow.setSearchPromptString(ForeignUi.getMessage("prompt.scenario"));
        VaadinUtils.addComponentStyle(filterWindow, "acl-scenarios-filter-window");
        return filterWindow;
    }

    public Set<AclScenario> getSelectedItems() {
        return scenarios;
    }
}
