package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline.value;

import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineValueFilter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.PeriodFilterWidget;
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

/**
 * Implementation of {@link IUdmBaselineValueFilterWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Anton Azarenka
 */
public class UdmBaselineValueFilterWidget extends VerticalLayout implements IUdmBaselineValueFilterWidget {

    private UdmBaselineValueFilter udmBaselineValueFilter = new UdmBaselineValueFilter();
    private UdmBaselineValueFilter appliedBaselineValueFilter = new UdmBaselineValueFilter();
    private PeriodFilterWidget periodFilterWidget;
    private Button moreFiltersButton;
    private Button applyButton;
    private ComboBox<PublicationType> pubTypeComboBox;
    private IUdmBaselineValueFilterController controller;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IUdmBaselineValueFilterController}
     */
    public UdmBaselineValueFilterWidget(IUdmBaselineValueFilterController controller) {
        this.controller = controller;
    }

    @Override
    @SuppressWarnings("unchecked")
    public IUdmBaselineValueFilterWidget init() {
        addComponents(initFiltersLayout(), initButtonsLayout());
        VaadinUtils.setMaxComponentsWidth(this);
        VaadinUtils.addComponentStyle(this, "udm-baseline-values-filter-widget");
        return this;
    }

    @Override
    public void applyFilter() {
        appliedBaselineValueFilter = new UdmBaselineValueFilter(udmBaselineValueFilter);
        filterChanged();
        fireEvent(new FilterChangedEvent(this));
    }

    @Override
    public void clearFilter() {
        clearFilterValues();
        refreshFilter();
        applyFilter();
    }

    @Override
    public void setController(IUdmBaselineValueFilterController controller) {
        this.controller = controller;
    }

    private void clearFilterValues() {
        periodFilterWidget.reset();
        pubTypeComboBox.clear();
    }

    private void filterChanged() {
        applyButton.setEnabled(!udmBaselineValueFilter.equals(appliedBaselineValueFilter));
    }

    private void refreshFilter() {
        udmBaselineValueFilter = new UdmBaselineValueFilter();
    }

    private VerticalLayout initFiltersLayout() {
        initPubTypeFilter();
        initMoreFiltersButton();
        VerticalLayout verticalLayout =
            new VerticalLayout(buildFiltersHeaderLabel(), buildPeriodFilter(), pubTypeComboBox, moreFiltersButton);
        verticalLayout.setMargin(false);
        VaadinUtils.setButtonsAutoDisabled(moreFiltersButton);
        return verticalLayout;
    }

    private PeriodFilterWidget buildPeriodFilter() {
        periodFilterWidget = new PeriodFilterWidget(() -> controller.getPeriods());
        periodFilterWidget.addFilterSaveListener((IFilterSaveListener<Integer>) saveEvent -> {
            udmBaselineValueFilter.setPeriods(saveEvent.getSelectedItemsIds());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(periodFilterWidget, "udm-baseline-value-periods-filter");
        return periodFilterWidget;
    }

    private void initPubTypeFilter() {
        pubTypeComboBox = new ComboBox<>(ForeignUi.getMessage("label.pub_type"));
        pubTypeComboBox.setItems(controller.getPublicationTypes());
        pubTypeComboBox.setPageLength(11);
        pubTypeComboBox.setItemCaptionGenerator(PublicationType::getNameAndDescription);
        pubTypeComboBox.addValueChangeListener(event -> {
            udmBaselineValueFilter.setPubType(pubTypeComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.setMaxComponentsWidth(pubTypeComboBox);
        VaadinUtils.addComponentStyle(pubTypeComboBox, "udm-baseline-value-pub-type-filter");
    }

    private void initMoreFiltersButton() {
        moreFiltersButton = new Button(ForeignUi.getMessage("label.more_filters"));
        moreFiltersButton.addStyleName(ValoTheme.BUTTON_LINK);
        moreFiltersButton.addClickListener(event -> {
            UdmBaselineValueFiltersWindow udmValueFiltersWindow =
                new UdmBaselineValueFiltersWindow(udmBaselineValueFilter);
            Windows.showModalWindow(udmValueFiltersWindow);
            udmValueFiltersWindow.addCloseListener(closeEvent -> {
                udmBaselineValueFilter = udmValueFiltersWindow.getBaselineValueFilter();
                filterChanged();
            });
        });
    }

    private Label buildFiltersHeaderLabel() {
        Label filterHeaderLabel = new Label(ForeignUi.getMessage("label.filters"));
        filterHeaderLabel.addStyleName(Cornerstone.LABEL_H2);
        return filterHeaderLabel;
    }

    private HorizontalLayout initButtonsLayout() {
        applyButton = Buttons.createButton(ForeignUi.getMessage("button.apply"));
        applyButton.setEnabled(false);
        applyButton.addClickListener(event -> applyFilter());
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFilter());
        HorizontalLayout horizontalLayout = new HorizontalLayout(applyButton, clearButton);
        VaadinUtils.setMaxComponentsWidth(horizontalLayout, applyButton, clearButton);
        VaadinUtils.addComponentStyle(horizontalLayout, "udm-baseline-value-filter-buttons");
        return horizontalLayout;
    }

    @Override
    public UdmBaselineValueFilter getAppliedFilter() {
        return appliedBaselineValueFilter;
    }
}
