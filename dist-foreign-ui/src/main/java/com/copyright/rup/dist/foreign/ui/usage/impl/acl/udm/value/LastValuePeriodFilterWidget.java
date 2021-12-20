package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.BaseUdmItemsFilterWidget;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.ValueProvider;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Widget provides functionality for configuring items filter widget for last value periods.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 09/23/2021
 *
 * @author Aliaksandr Liakh
 */
public class LastValuePeriodFilterWidget extends BaseUdmItemsFilterWidget<String>
    implements IFilterWindowController<String> {

    private final Supplier<List<String>> supplier;
    private final Set<String> selectedItemsIds = new HashSet<>();

    /**
     * Constructor.
     *
     * @param supplier         {@link String}s list supplier
     * @param selectedItemsIds set of selected items
     */
    public LastValuePeriodFilterWidget(Supplier<List<String>> supplier, Set<String> selectedItemsIds) {
        super(ForeignUi.getMessage("label.last_value_periods"));
        this.supplier = supplier;
        this.selectedItemsIds.addAll(selectedItemsIds);
        setLabelValue(selectedItemsIds.size());
    }

    @Override
    public void reset() {
        super.reset();
        selectedItemsIds.clear();
    }

    @Override
    public List<String> loadBeans() {
        List<String> lastValuePeriods = new ArrayList<>(supplier.get());
        lastValuePeriods.add(FilterOperatorEnum.IS_NULL.name());
        lastValuePeriods.add(FilterOperatorEnum.IS_NOT_NULL.name());
        return lastValuePeriods;
    }

    @Override
    public Class<String> getBeanClass() {
        return String.class;
    }

    @Override
    public String getBeanItemCaption(String lastValuePeriod) {
        return lastValuePeriod;
    }

    @Override
    public void onSave(FilterSaveEvent<String> event) {
        Set<String> itemsIds = event.getSelectedItemsIds();
        selectedItemsIds.clear();
        if (CollectionUtils.isNotEmpty(itemsIds)) {
            selectedItemsIds.addAll(itemsIds);
        }
    }

    @Override
    public CommonFilterWindow<String> showFilterWindow() {
        UdmLastValuePeriodFilterWindow filterWindow =
            new UdmLastValuePeriodFilterWindow(ForeignUi.getMessage("window.last_value_periods_filter"), this,
                (ValueProvider<String, List<String>>) Arrays::asList);
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        filterWindow.setSearchPromptString(ForeignUi.getMessage("prompt.last_value_period"));
        VerticalLayout verticalLayout = (VerticalLayout) filterWindow.getContent();
        Panel panel = (Panel) verticalLayout.getComponent(1);
        CheckBoxGroup<String> checkBoxGroup = (CheckBoxGroup<String>) panel.getContent();
        setItemEnabledProvider(checkBoxGroup, selectedItemsIds);
        checkBoxGroup.addValueChangeListener(event -> setItemEnabledProvider(checkBoxGroup, event.getValue()));
        VaadinUtils.addComponentStyle(filterWindow, "last-value-period-filter-window");
        Windows.showModalWindow(filterWindow);
        return filterWindow;
    }

    /**
     * Enables and disables items in the checkboxes depends on their selected items.
     *
     * @param checkBoxGroup  instance of {@link CheckBoxGroup}
     * @param checkedItemIds checked item ids
     */
    void setItemEnabledProvider(CheckBoxGroup<String> checkBoxGroup, Set<String> checkedItemIds) {
        if (checkedItemIds.isEmpty()) {
            checkBoxGroup.setItemEnabledProvider(item -> true);
        } else if (checkedItemIds.contains(FilterOperatorEnum.IS_NULL.name())) {
            checkBoxGroup.setItemEnabledProvider(item -> item.equals(FilterOperatorEnum.IS_NULL.name()));
        } else if (checkedItemIds.contains(FilterOperatorEnum.IS_NOT_NULL.name())) {
            checkBoxGroup.setItemEnabledProvider(item -> item.equals(FilterOperatorEnum.IS_NOT_NULL.name()));
        } else if (checkedItemIds.stream().allMatch(NumberUtils::isDigits)) {
            checkBoxGroup.setItemEnabledProvider(item ->
                !item.equals(FilterOperatorEnum.IS_NULL.name()) && !item.equals(FilterOperatorEnum.IS_NOT_NULL.name()));
        }
    }
}
