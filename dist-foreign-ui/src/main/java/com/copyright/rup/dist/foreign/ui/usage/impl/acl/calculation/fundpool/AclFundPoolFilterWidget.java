package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.filter.AclFundPoolDetailFilter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.AggregateLicenseeClassFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.DetailLicenseeClassFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.PeriodFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.util.stream.Collectors;

/**
 * Widget for filtering ACL fund pools.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/17/2022
 *
 * @author Anton Azarenka
 */
public class AclFundPoolFilterWidget extends VerticalLayout implements IAclFundPoolFilterWidget {

    private IAclFundPoolFilterController controller;
    private Button applyButton;
    private AclFundPoolDetailFilter aclFundPoolDetailFilter = new AclFundPoolDetailFilter();
    private AclFundPoolDetailFilter appliedAclFundPoolDetailFilter = new AclFundPoolDetailFilter();
    private AclFundPoolNameFilterWidget aclFundPoolNameFilterWidget;
    private PeriodFilterWidget periodFilterWidget;
    private AggregateLicenseeClassFilterWidget aggregateLicenseeClassFilterWidget;
    private DetailLicenseeClassFilterWidget detailLicenseeClassFilterWidget;
    private ComboBox<String> licenseTypesComboBox;
    private ComboBox<String> fundPoolTypeComboBox;

    /**
     * Constructor.
     *
     * @param controller instance of {@link AclFundPoolFilterController}
     */
    public AclFundPoolFilterWidget(IAclFundPoolFilterController controller) {
        this.controller = controller;
    }

    @Override
    public AclFundPoolDetailFilter getFilter() {
        return aclFundPoolDetailFilter;
    }

    @Override
    public AclFundPoolDetailFilter getAppliedFilter() {
        return appliedAclFundPoolDetailFilter;
    }

    @Override
    public void applyFilter() {
        appliedAclFundPoolDetailFilter = new AclFundPoolDetailFilter(aclFundPoolDetailFilter);
        filterChanged();
        fireEvent(new FilterChangedEvent(this));
    }

    @Override
    public void clearFilter() {
        aclFundPoolNameFilterWidget.reset();
        periodFilterWidget.reset();
        aggregateLicenseeClassFilterWidget.reset();
        detailLicenseeClassFilterWidget.reset();
        licenseTypesComboBox.clear();
        fundPoolTypeComboBox.clear();
        aclFundPoolDetailFilter = new AclFundPoolDetailFilter();
        applyFilter();
    }

    @Override
    @SuppressWarnings("unchecked")
    public AclFundPoolFilterWidget init() {
        addComponents(initFiltersLayout(), initButtonsLayout());
        licenseTypesComboBox.setItems("ACL", "VGW", "JACDCL", "MACL");
        fundPoolTypeComboBox.setItems("Print", "Digital");
        VaadinUtils.setMaxComponentsWidth(this);
        VaadinUtils.addComponentStyle(this, "acl-fund-pool-widget");
        return this;
    }

    @Override
    public void setController(IAclFundPoolFilterController controller) {
        this.controller = controller;
    }

    private VerticalLayout initFiltersLayout() {
        initLicenseTypesFilter();
        initFundPoolTypeFilter();
        VerticalLayout verticalLayout = new VerticalLayout(buildFiltersHeaderLabel(), buildFundPoolNamesFilter(),
            buildPeriodFilter(), buildAggregateLicenseeClassFilter(), buildDetailLicenseeClassFilter(),
            licenseTypesComboBox, fundPoolTypeComboBox);
        verticalLayout.setMargin(false);
        return verticalLayout;
    }

    private AclFundPoolNameFilterWidget buildFundPoolNamesFilter() {
        aclFundPoolNameFilterWidget = new AclFundPoolNameFilterWidget(() -> controller.getFundPoolNames());
        aclFundPoolNameFilterWidget.addFilterSaveListener((IFilterSaveListener<AclFundPool>) saveEvent -> {
            aclFundPoolDetailFilter.setFundPoolNames(
                saveEvent.getSelectedItemsIds().stream().map(AclFundPool::getName).collect(Collectors.toSet()));
            filterChanged();
        });
        VaadinUtils.addComponentStyle(aclFundPoolNameFilterWidget, "acl-fund-pool-name-filter");
        return aclFundPoolNameFilterWidget;
    }

    private PeriodFilterWidget buildPeriodFilter() {
        periodFilterWidget = new PeriodFilterWidget(() -> controller.getPeriods());
        periodFilterWidget.addFilterSaveListener((IFilterSaveListener<Integer>) saveEvent -> {
            aclFundPoolDetailFilter.setPeriods(saveEvent.getSelectedItemsIds());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(periodFilterWidget, "udm-periods-filter");
        return periodFilterWidget;
    }

    private AggregateLicenseeClassFilterWidget buildAggregateLicenseeClassFilter() {
        aggregateLicenseeClassFilterWidget = new AggregateLicenseeClassFilterWidget(
            controller::getAggregateLicenseeClasses, aclFundPoolDetailFilter.getAggregateLicenseeClasses());
        aggregateLicenseeClassFilterWidget.addFilterSaveListener((
            IFilterSaveListener<AggregateLicenseeClass>) saveEvent -> {
            aclFundPoolDetailFilter.setAggregateLicenseeClasses(saveEvent.getSelectedItemsIds());
            filterChanged();
        });
        return aggregateLicenseeClassFilterWidget;
    }

    private DetailLicenseeClassFilterWidget buildDetailLicenseeClassFilter() {
        detailLicenseeClassFilterWidget = new DetailLicenseeClassFilterWidget(
            controller::getDetailLicenseeClasses, aclFundPoolDetailFilter.getDetailLicenseeClasses());
        detailLicenseeClassFilterWidget.addFilterSaveListener((
            IFilterSaveListener<DetailLicenseeClass>) saveEvent -> {
            aclFundPoolDetailFilter.setDetailLicenseeClasses(saveEvent.getSelectedItemsIds());
            filterChanged();
        });
        return detailLicenseeClassFilterWidget;
    }

    private void initLicenseTypesFilter() {
        licenseTypesComboBox = new ComboBox<>(ForeignUi.getMessage("label.license_types"));
        VaadinUtils.setMaxComponentsWidth(licenseTypesComboBox);
        licenseTypesComboBox.addValueChangeListener(event -> {
            aclFundPoolDetailFilter.setLicenseType(licenseTypesComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(licenseTypesComboBox, "acl-license-types-filter");
    }

    private void initFundPoolTypeFilter() {
        fundPoolTypeComboBox = new ComboBox<>(ForeignUi.getMessage("label.fund_pool_type"));
        VaadinUtils.setMaxComponentsWidth(fundPoolTypeComboBox);
        fundPoolTypeComboBox.addValueChangeListener(event -> {
            aclFundPoolDetailFilter.setFundPoolType(fundPoolTypeComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(fundPoolTypeComboBox, "acl-fund-pool-type-filter");
    }

    private HorizontalLayout initButtonsLayout() {
        applyButton = Buttons.createButton(ForeignUi.getMessage("button.apply"));
        applyButton.setEnabled(false);
        applyButton.addClickListener(event -> applyFilter());
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFilter());
        HorizontalLayout horizontalLayout = new HorizontalLayout(applyButton, clearButton);
        VaadinUtils.setMaxComponentsWidth(horizontalLayout, applyButton, clearButton);
        VaadinUtils.addComponentStyle(horizontalLayout, "acl-fund-pool-filter-buttons");
        return horizontalLayout;
    }

    private void filterChanged() {
        applyButton.setEnabled(!aclFundPoolDetailFilter.equals(appliedAclFundPoolDetailFilter));
    }

    private Label buildFiltersHeaderLabel() {
        Label filterHeaderLabel = new Label(ForeignUi.getMessage("label.filters"));
        filterHeaderLabel.addStyleName(Cornerstone.LABEL_H2);
        return filterHeaderLabel;
    }
}
