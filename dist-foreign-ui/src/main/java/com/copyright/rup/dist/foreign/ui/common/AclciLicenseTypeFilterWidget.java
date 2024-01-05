package com.copyright.rup.dist.foreign.ui.common;

import com.copyright.rup.dist.foreign.domain.AclciLicenseTypeEnum;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.BaseItemsFilterWidget;

import org.apache.commons.collections4.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Widget provides functionality for configuring items filter widget for ACLCI License Types.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 12/14/2022
 *
 * @author Mikita Maistrenka
 */
public class AclciLicenseTypeFilterWidget extends BaseItemsFilterWidget<AclciLicenseTypeEnum>
    implements IFilterWindowController<AclciLicenseTypeEnum> {

    private static final long serialVersionUID = 4004093543608867699L;

    private final Set<AclciLicenseTypeEnum> licenseTypes;
    private final Set<AclciLicenseTypeEnum> selectedItemsIds = new HashSet<>();

    /**
     * Constructor.
     *
     * @param licenseTypes         {@link AclciLicenseTypeEnum}s set
     * @param selectedItemsIds set of selected items
     */
    public AclciLicenseTypeFilterWidget(Set<AclciLicenseTypeEnum> licenseTypes,
                                        Set<AclciLicenseTypeEnum> selectedItemsIds) {
        super(ForeignUi.getMessage("label.license_types"));
        this.licenseTypes = licenseTypes;
        this.selectedItemsIds.addAll(selectedItemsIds);
    }

    @Override
    public void reset() {
        super.reset();
        selectedItemsIds.clear();
    }

    @Override
    public Set<AclciLicenseTypeEnum> loadBeans() {
        return licenseTypes;
    }

    @Override
    public Class<AclciLicenseTypeEnum> getBeanClass() {
        return AclciLicenseTypeEnum.class;
    }

    @Override
    public String getBeanItemCaption(AclciLicenseTypeEnum licenseType) {
        return licenseType.name();
    }

    @Override
    public void onSave(FilterSaveEvent<AclciLicenseTypeEnum> event) {
        Set<AclciLicenseTypeEnum> itemsIds = event.getSelectedItemsIds();
        selectedItemsIds.clear();
        if (CollectionUtils.isNotEmpty(itemsIds)) {
            selectedItemsIds.addAll(itemsIds);
        }
    }

    @Override
    public FilterWindow<AclciLicenseTypeEnum> showFilterWindow() {
        FilterWindow<AclciLicenseTypeEnum> filterWindow =
            Windows.showFilterWindow(ForeignUi.getMessage("window.license_types_filter"), this);
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        filterWindow.setSelectAllButtonVisible();
        VaadinUtils.addComponentStyle(filterWindow, "aclci-license-type-filter-window");
        return filterWindow;
    }
}
