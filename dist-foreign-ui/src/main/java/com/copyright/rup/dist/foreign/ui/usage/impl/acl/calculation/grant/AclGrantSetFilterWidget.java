package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import com.copyright.rup.dist.foreign.domain.AclGrantSet;
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
 * Widget provides functionality for configuring items filter widget for ACL grant sets.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 02/01/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclGrantSetFilterWidget extends BaseItemsFilterWidget<AclGrantSet>
    implements IFilterWindowController<AclGrantSet> {

    private final Supplier<List<AclGrantSet>> supplier;
    private final Set<AclGrantSet> selectedItemsIds = new HashSet<>();

    /**
     * Constructor.
     *
     * @param supplier {@link AclGrantSet}s supplier
     */
    public AclGrantSetFilterWidget(Supplier<List<AclGrantSet>> supplier) {
        super(ForeignUi.getMessage("label.grant_sets"));
        this.supplier = supplier;
    }

    @Override
    public void reset() {
        selectedItemsIds.clear();
        super.reset();
    }

    @Override
    public List<AclGrantSet> loadBeans() {
        return supplier.get();
    }

    @Override
    public Class<AclGrantSet> getBeanClass() {
        return AclGrantSet.class;
    }

    @Override
    public String getBeanItemCaption(AclGrantSet aclGrantSet) {
        return aclGrantSet.getName();
    }

    @Override
    public void onSave(FilterSaveEvent<AclGrantSet> event) {
        Set<AclGrantSet> itemsIds = event.getSelectedItemsIds();
        selectedItemsIds.clear();
        if (CollectionUtils.isNotEmpty(itemsIds)) {
            selectedItemsIds.addAll(itemsIds);
        }
    }

    @Override
    public FilterWindow<AclGrantSet> showFilterWindow() {
        FilterWindow<AclGrantSet> filterWindow =
            Windows.showFilterWindow(ForeignUi.getMessage("window.grant_sets_filter"), this,
                (ValueProvider<AclGrantSet, List<String>>) bean -> Collections.singletonList(bean.getName()));
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        filterWindow.setSelectAllButtonVisible();
        filterWindow.setSearchPromptString(ForeignUi.getMessage("prompt.grant_set"));
        VaadinUtils.addComponentStyle(filterWindow, "acl-grant-sets-filter-window");
        return filterWindow;
    }
}
