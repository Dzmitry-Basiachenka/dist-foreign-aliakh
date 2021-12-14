package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UdmValueStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.UdmValuePeriodFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link IUdmValueFilterWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmValueFilterWidget extends VerticalLayout implements IUdmValueFilterWidget {

    private ComboBox<UdmValueStatusEnum> statusComboBox;
    private ComboBox<PublicationType> pubTypeComboBox;
    private Button moreFiltersButton;
    private Button applyButton;
    private UdmValueFilter udmValueFilter = new UdmValueFilter();
    private UdmValueFilter appliedUdmValueFilter = new UdmValueFilter();
    private IUdmValueFilterController controller;
    private UdmValuePeriodFilterWidget periodFilterWidget;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IUdmValueFilterController}
     */
    public UdmValueFilterWidget(IUdmValueFilterController controller) {
        this.controller = controller;
    }

    @Override
    public UdmValueFilter getFilter() {
        return udmValueFilter;
    }

    @Override
    public UdmValueFilter getAppliedFilter() {
        return appliedUdmValueFilter;
    }

    @Override
    public void setController(IUdmValueFilterController controller) {
        this.controller = controller;
    }

    @Override
    @SuppressWarnings("unchecked")
    public IUdmValueFilterWidget init() {
        addComponents(initFiltersLayout(), initButtonsLayout());
        VaadinUtils.setMaxComponentsWidth(this);
        VaadinUtils.addComponentStyle(this, "udm-values-filter-widget");
        return this;
    }

    @Override
    public void applyFilter() {
        appliedUdmValueFilter = new UdmValueFilter(udmValueFilter);
        filterChanged();
        fireEvent(new FilterChangedEvent(this));
    }

    @Override
    public void clearFilter() {
        clearFilterValues();
        refreshFilter();
        applyFilter();
    }

    /**
     * Handles filter change event.
     */
    protected void filterChanged() {
        applyButton.setEnabled(!udmValueFilter.equals(appliedUdmValueFilter));
    }

    private void clearFilterValues() {
        periodFilterWidget.reset();
        statusComboBox.clear();
        pubTypeComboBox.clear();
    }

    private void refreshFilter() {
        udmValueFilter = new UdmValueFilter();
    }

    private VerticalLayout initFiltersLayout() {
        initStatusFilter();
        initPubTypeFilter();
        initMoreFiltersButton();
        VerticalLayout verticalLayout =
            new VerticalLayout(buildFiltersHeaderLabel(), buildPeriodFilter(), statusComboBox, pubTypeComboBox,
                moreFiltersButton);
        verticalLayout.setMargin(false);
        VaadinUtils.setButtonsAutoDisabled(moreFiltersButton);
        return verticalLayout;
    }

    private UdmValuePeriodFilterWidget buildPeriodFilter() {
        periodFilterWidget = new UdmValuePeriodFilterWidget(() -> controller.getPeriods());
        periodFilterWidget.addFilterSaveListener((IFilterSaveListener<Integer>) saveEvent -> {
            udmValueFilter.setPeriods(saveEvent.getSelectedItemsIds());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(periodFilterWidget, "udm-value-periods-filter");
        return periodFilterWidget;
    }

    private void initPubTypeFilter() {
        pubTypeComboBox = new ComboBox<>(ForeignUi.getMessage("label.pub_type"));
        List<PublicationType> publicationTypes = controller.getPublicationTypes();
        publicationTypes.add(0, new PublicationType());
        pubTypeComboBox.setItems(publicationTypes);
        pubTypeComboBox.setPageLength(12);
        pubTypeComboBox.setItemCaptionGenerator(value -> Objects.nonNull(value.getName())
            ? value.getNameAndDescription()
            : "NULL");
        pubTypeComboBox.addValueChangeListener(event -> {
            udmValueFilter.setPubType(pubTypeComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.setMaxComponentsWidth(pubTypeComboBox);
        VaadinUtils.addComponentStyle(pubTypeComboBox, "udm-value-pub-type-filter");
    }

    private void initStatusFilter() {
        statusComboBox = new ComboBox<>(ForeignUi.getMessage("label.status"));
        statusComboBox.setPopupWidth("220px");
        statusComboBox.setItems(new LinkedHashSet<>(Arrays.asList(UdmValueStatusEnum.NEW,
            UdmValueStatusEnum.RSCHD_IN_THE_PREV_PERIOD, UdmValueStatusEnum.PRELIM_RESEARCH_COMPLETE,
            UdmValueStatusEnum.NEEDS_FURTHER_REVIEW, UdmValueStatusEnum.RESEARCH_COMPLETE)));
        VaadinUtils.setMaxComponentsWidth(statusComboBox);
        statusComboBox.addValueChangeListener(event -> {
            udmValueFilter.setStatus(statusComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(statusComboBox, "udm-value-status-filter");
    }

    private void initMoreFiltersButton() {
        moreFiltersButton = new Button(ForeignUi.getMessage("label.more_filters"));
        moreFiltersButton.addStyleName(ValoTheme.BUTTON_LINK);
        moreFiltersButton.addClickListener(event -> {
            UdmValueFiltersWindow udmValueFiltersWindow = new UdmValueFiltersWindow(controller, udmValueFilter);
            Windows.showModalWindow(udmValueFiltersWindow);
            udmValueFiltersWindow.addCloseListener(closeEvent -> {
                udmValueFilter = udmValueFiltersWindow.getAppliedValueFilter();
                filterChanged();
            });
        });
    }

    private HorizontalLayout initButtonsLayout() {
        applyButton = Buttons.createButton(ForeignUi.getMessage("button.apply"));
        applyButton.setEnabled(false);
        applyButton.addClickListener(event -> applyFilter());
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFilter());
        HorizontalLayout horizontalLayout = new HorizontalLayout(applyButton, clearButton);
        VaadinUtils.setMaxComponentsWidth(horizontalLayout, applyButton, clearButton);
        VaadinUtils.addComponentStyle(horizontalLayout, "udm-value-filter-buttons");
        return horizontalLayout;
    }

    private Label buildFiltersHeaderLabel() {
        Label filterHeaderLabel = new Label(ForeignUi.getMessage("label.filters"));
        filterHeaderLabel.addStyleName(Cornerstone.LABEL_H2);
        return filterHeaderLabel;
    }
}
