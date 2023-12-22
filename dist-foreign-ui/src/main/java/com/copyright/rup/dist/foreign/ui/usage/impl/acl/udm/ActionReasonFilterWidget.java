package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm;

import com.copyright.rup.dist.foreign.domain.UdmActionReason;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.ValueProvider;

import org.apache.commons.collections4.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Widget provides functionality for configuring items filter widget for action reasons.
 * <p/>
 * Copyright (C) 2023 copyright.com
 * <p/>
 * Date: 05/24/2023
 *
 * @author Dzmitry Basiachenka
 */
public class ActionReasonFilterWidget extends BaseUdmItemsFilterWidget<UdmActionReason>
    implements IFilterWindowController<UdmActionReason> {

    private static final long serialVersionUID = 3085484342631894428L;

    private final Supplier<List<UdmActionReason>> supplier;
    private final Set<UdmActionReason> selectedItemsIds = new HashSet<>();

    /**
     * Constructor.
     *
     * @param supplier         {@link String}s list supplier
     * @param selectedItemsIds set of selected items
     */
    public ActionReasonFilterWidget(Supplier<List<UdmActionReason>> supplier, Set<UdmActionReason> selectedItemsIds) {
        super(ForeignUi.getMessage("label.action_reasons_udm"));
        this.supplier = supplier;
        this.selectedItemsIds.addAll(selectedItemsIds);
        super.setLabelValue(selectedItemsIds.size());
    }

    @Override
    public void reset() {
        super.reset();
        selectedItemsIds.clear();
    }

    @Override
    public List<UdmActionReason> loadBeans() {
        return supplier.get();
    }

    @Override
    public Class<UdmActionReason> getBeanClass() {
        return UdmActionReason.class;
    }

    @Override
    public String getBeanItemCaption(UdmActionReason actionReason) {
        return actionReason.getReason();
    }

    @Override
    public void onSave(FilterSaveEvent<UdmActionReason> event) {
        Set<UdmActionReason> itemsIds = event.getSelectedItemsIds();
        selectedItemsIds.clear();
        if (CollectionUtils.isNotEmpty(itemsIds)) {
            selectedItemsIds.addAll(itemsIds);
        }
    }

    @Override
    public FilterWindow<UdmActionReason> showFilterWindow() {
        FilterWindow<UdmActionReason> filterWindow =
            Windows.showFilterWindow(ForeignUi.getMessage("window.action_reason_filter"), this,
                (ValueProvider<UdmActionReason, List<String>>) actionReason -> List.of(actionReason.getReason()));
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        filterWindow.setSelectAllButtonVisible();
        filterWindow.setSearchPromptString(ForeignUi.getMessage("prompt.action_reason"));
        VaadinUtils.addComponentStyle(filterWindow, "action-reason-filter-window");
        return filterWindow;
    }

    public Set<UdmActionReason> getSelectedItemsIds() {
        return selectedItemsIds;
    }
}
