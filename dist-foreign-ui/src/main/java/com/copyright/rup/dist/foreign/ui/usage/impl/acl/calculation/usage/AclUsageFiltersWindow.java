package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.CommonAclFiltersWindow;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.AggregateLicenseeClassFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.DetailLicenseeClassFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.PeriodFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.PublicationTypeFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.TypeOfUseFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Window to apply additional filters for {@link AclUsageFilterWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/20/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclUsageFiltersWindow extends CommonAclFiltersWindow {

    private final Binder<AclUsageFilter> filterBinder = new Binder<>();
    private final AclUsageFilter usageFilter;
    private final IAclUsageFilterController controller;

    private PeriodFilterWidget periodFilterWidget;
    private DetailLicenseeClassFilterWidget detailLicenseeClassFilterWidget;
    private AggregateLicenseeClassFilterWidget aggregateLicenseeClassFilterWidget;
    private PublicationTypeFilterWidget pubTypeFilterWidget;
    private TypeOfUseFilterWidget typeOfUseFilterWidget;

    /**
     * Constructor.
     *
     * @param controller     instance of {@link IAclUsageFilterController}
     * @param aclUsageFilter instance of {@link AclUsageFilter} to be displayed on window
     */
    public AclUsageFiltersWindow(IAclUsageFilterController controller, AclUsageFilter aclUsageFilter) {
        this.controller = controller;
        this.usageFilter = aclUsageFilter;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.acl_usages_additional_filters"));
        setResizable(false);
        setWidth(600, Unit.PIXELS);
        setHeight(490, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "acl-usages-additional-filters-window");
    }

    /**
     * @return applied ACL usage filter.
     */
    public AclUsageFilter getAppliedUsageFilter() {
        return usageFilter;
    }

    private ComponentContainer initRootLayout() {
        initTypeOfUseFilterWidget();
        VerticalLayout fieldsLayout = new VerticalLayout();
        fieldsLayout.addComponents(initPeriodDetailLicenseeClassLayout(), initAggregateLicenseeClassPubTypeLayout(),
            typeOfUseFilterWidget);
        filterBinder.readBean(usageFilter);
        filterBinder.validate();
        return buildRootLayout(fieldsLayout);
    }

    private HorizontalLayout initPeriodDetailLicenseeClassLayout() {
        periodFilterWidget = new PeriodFilterWidget(controller::getPeriods, usageFilter.getPeriods());
        //TODO avoid using empty listeners
        periodFilterWidget.addFilterSaveListener((IFilterSaveListener<Integer>) saveEvent -> {});
        detailLicenseeClassFilterWidget = new DetailLicenseeClassFilterWidget(controller::getDetailLicenseeClasses,
            usageFilter.getDetailLicenseeClasses());
        //TODO avoid using empty listeners
        detailLicenseeClassFilterWidget.addFilterSaveListener((
            IFilterSaveListener<DetailLicenseeClass>) saveEvent -> {});
        HorizontalLayout periodDetailLicenseeClassLayout =
            new HorizontalLayout(periodFilterWidget, detailLicenseeClassFilterWidget);
        periodDetailLicenseeClassLayout.setSizeFull();
        periodDetailLicenseeClassLayout.setSpacing(true);
        return periodDetailLicenseeClassLayout;
    }

    private HorizontalLayout initAggregateLicenseeClassPubTypeLayout() {
        aggregateLicenseeClassFilterWidget = new AggregateLicenseeClassFilterWidget(
            controller::getAggregateLicenseeClasses, usageFilter.getAggregateLicenseeClasses());
        //TODO avoid using empty listeners
        aggregateLicenseeClassFilterWidget.addFilterSaveListener((
            IFilterSaveListener<AggregateLicenseeClass>) saveEvent -> {});
        pubTypeFilterWidget = new PublicationTypeFilterWidget(controller::getPublicationTypes,
            usageFilter.getPubTypes());
        //TODO avoid using empty listeners
        pubTypeFilterWidget.addFilterSaveListener((IFilterSaveListener<PublicationType>) saveEvent -> {});
        HorizontalLayout assigneeLicenseeClassLayout =
            new HorizontalLayout(aggregateLicenseeClassFilterWidget, pubTypeFilterWidget);
        assigneeLicenseeClassLayout.setSizeFull();
        assigneeLicenseeClassLayout.setSpacing(true);
        return assigneeLicenseeClassLayout;
    }

    private void initTypeOfUseFilterWidget() {
        typeOfUseFilterWidget = new TypeOfUseFilterWidget(() -> Arrays.asList("PRINT", "DIGITAL"),
            usageFilter.getTypeOfUses());
        //TODO avoid using empty listeners
        typeOfUseFilterWidget.addFilterSaveListener((IFilterSaveListener<String>) saveEvent -> {});
    }

    private VerticalLayout buildRootLayout(VerticalLayout fieldsLayout) {
        VerticalLayout rootLayout = new VerticalLayout();
        Panel panel = new Panel(fieldsLayout);
        panel.setSizeFull();
        fieldsLayout.setMargin(new MarginInfo(true));
        HorizontalLayout buttonsLayout = initButtonsLayout();
        rootLayout.addComponents(panel, buttonsLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        rootLayout.setExpandRatio(panel, 1f);
        rootLayout.setSizeFull();
        panel.setStyleName(Cornerstone.FORMLAYOUT_LIGHT);
        return rootLayout;
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        saveButton.addClickListener(event -> {
            try {
                filterBinder.writeBean(usageFilter);
                usageFilter.setPeriods(periodFilterWidget.getSelectedItemsIds());
                usageFilter.setDetailLicenseeClasses(detailLicenseeClassFilterWidget.getSelectedItemsIds());
                usageFilter.setAggregateLicenseeClasses(aggregateLicenseeClassFilterWidget.getSelectedItemsIds());
                usageFilter.setPubTypes(pubTypeFilterWidget.getSelectedItemsIds());
                usageFilter.setTypeOfUses(typeOfUseFilterWidget.getSelectedItemsIds());
                close();
            } catch (ValidationException e) {
                Windows.showValidationErrorWindow(new ArrayList<>());
            }
        });
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFilters());
        return new HorizontalLayout(saveButton, clearButton, closeButton);
    }

    private void clearFilters() {
        periodFilterWidget.reset();
        detailLicenseeClassFilterWidget.reset();
        aggregateLicenseeClassFilterWidget.reset();
        pubTypeFilterWidget.reset();
        typeOfUseFilterWidget.reset();
        filterBinder.readBean(new AclUsageFilter());
    }
}
