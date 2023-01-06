package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import com.copyright.rup.dist.foreign.domain.AclFundPool;
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
 * Widget provides functionality for configuring items filter widget for ACL fund pool name.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/11/2022
 *
 * @author Mikita Maistrenka
 */
public class AclFundPoolNameFilterWidget extends BaseItemsFilterWidget<AclFundPool>
    implements IFilterWindowController<AclFundPool> {

    private final Supplier<List<AclFundPool>> supplier;
    private final Set<AclFundPool> selectedItemsIds = new HashSet<>();

    /**
     * Constructor.
     *
     * @param supplier {@link AclFundPool}s supplier
     */
    public AclFundPoolNameFilterWidget(Supplier<List<AclFundPool>> supplier) {
        super(ForeignUi.getMessage("label.fund_pool.names"));
        this.supplier = supplier;
    }

    @Override
    public void reset() {
        selectedItemsIds.clear();
        super.reset();
    }

    @Override
    public List<AclFundPool> loadBeans() {
        return supplier.get();
    }

    @Override
    public Class<AclFundPool> getBeanClass() {
        return AclFundPool.class;
    }

    @Override
    public String getBeanItemCaption(AclFundPool aclFundPool) {
        return aclFundPool.getName();
    }

    @Override
    public void onSave(FilterSaveEvent<AclFundPool> event) {
        Set<AclFundPool> itemsIds = event.getSelectedItemsIds();
        selectedItemsIds.clear();
        if (CollectionUtils.isNotEmpty(itemsIds)) {
            selectedItemsIds.addAll(itemsIds);
        }
    }

    @Override
    public FilterWindow<AclFundPool> showFilterWindow() {
        FilterWindow<AclFundPool> filterWindow =
            Windows.showFilterWindow(ForeignUi.getMessage("window.fund_pool_names_filter"), this,
                (ValueProvider<AclFundPool, List<String>>) bean -> List.of(bean.getName()));
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        filterWindow.setSelectAllButtonVisible();
        filterWindow.setSearchPromptString(ForeignUi.getMessage("prompt.fund_pool_name"));
        VaadinUtils.addComponentStyle(filterWindow, "acl-fund-pool-name-filter-window");
        return filterWindow;
    }
}
