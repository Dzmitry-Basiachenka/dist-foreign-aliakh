package com.copyright.rup.dist.foreign.ui.audit.impl.sal;

import com.copyright.rup.dist.foreign.domain.report.SalLicensee;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.BaseItemsFilterWidget;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.vaadin.data.ValueProvider;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Implementation of SAL licensee filter widget.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 12/17/2020
 *
 * @author Aliaksandr Liakh
 */
class SalLicenseeFilterWidget extends BaseItemsFilterWidget<SalLicensee>
    implements IFilterWindowController<SalLicensee> {

    private final String searchPrompt;
    private final String caption;
    private final Supplier<List<SalLicensee>> supplier;
    private final Set<SalLicensee> selectedItemsIds = Sets.newHashSet();

    /**
     * Constructor.
     *
     * @param supplier {@link SalLicensee}s supplier
     */
    SalLicenseeFilterWidget(Supplier<List<SalLicensee>> supplier) {
        super(ForeignUi.getMessage("label.licensees"));
        this.caption = ForeignUi.getMessage("label.licensees");
        this.supplier = supplier;
        this.searchPrompt = ForeignUi.getMessage("prompt.licensee");
    }

    @Override
    public void reset() {
        selectedItemsIds.clear();
        super.reset();
    }

    @Override
    public List<SalLicensee> loadBeans() {
        return supplier.get();
    }

    @Override
    public Class<SalLicensee> getBeanClass() {
        return SalLicensee.class;
    }

    @Override
    public String getBeanItemCaption(SalLicensee licensee) {
        return String.format("%s - %s", licensee.getAccountNumber(), licensee.getName());
    }

    @Override
    public void onSave(FilterWindow.FilterSaveEvent<SalLicensee> event) {
        Set<SalLicensee> itemsIds = event.getSelectedItemsIds();
        selectedItemsIds.clear();
        if (CollectionUtils.isNotEmpty(itemsIds)) {
            selectedItemsIds.addAll(itemsIds);
        }
    }

    @Override
    public FilterWindow<SalLicensee> showFilterWindow() {
        FilterWindow<SalLicensee> filterWindow = Windows.showFilterWindow(
            ForeignUi.getMessage("window.filter_format", caption), this,
            (ValueProvider<SalLicensee, List<String>>) licensee ->
                Lists.newArrayList(licensee.getName(), licensee.getAccountNumber().toString()));
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        filterWindow.setSearchPromptString(searchPrompt);
        VaadinUtils.addComponentStyle(filterWindow, "licensees-filter-window");
        return filterWindow;
    }
}
