package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline.value;

import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineValueFilter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value.UdmValuePeriodFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Button;
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
    private UdmValuePeriodFilterWidget periodFilterWidget;
    private Button moreFiltersButton;
    private Button applyButton;
    @SuppressWarnings("unused")
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
    }

    private void filterChanged() {
        applyButton.setEnabled(!udmBaselineValueFilter.equals(appliedBaselineValueFilter));
    }

    private void refreshFilter() {
        udmBaselineValueFilter = new UdmBaselineValueFilter();
    }

    private VerticalLayout initFiltersLayout() {
        initMoreFiltersButton();
        VerticalLayout verticalLayout =
            new VerticalLayout(buildFiltersHeaderLabel(), buildPeriodFilter(), moreFiltersButton);
        verticalLayout.setMargin(false);
        VaadinUtils.setButtonsAutoDisabled(moreFiltersButton);
        return verticalLayout;
    }

    private UdmValuePeriodFilterWidget buildPeriodFilter() {
        periodFilterWidget = new UdmValuePeriodFilterWidget(() -> controller.getPeriods());
        periodFilterWidget.addFilterSaveListener((IFilterSaveListener<Integer>) saveEvent -> {
            udmBaselineValueFilter.setPeriods(saveEvent.getSelectedItemsIds());
        });
        VaadinUtils.addComponentStyle(periodFilterWidget, "udm-value-periods-filter");
        return periodFilterWidget;
    }

    private void initMoreFiltersButton() {
        moreFiltersButton = new Button(ForeignUi.getMessage("label.more_filters"));
        moreFiltersButton.addStyleName(ValoTheme.BUTTON_LINK);
        moreFiltersButton.addClickListener(event -> {
            UdmBaselineValueFiltersWindow udmValueFiltersWindow =
                new UdmBaselineValueFiltersWindow(udmBaselineValueFilter);
            Windows.showModalWindow(udmValueFiltersWindow);
            udmValueFiltersWindow.addCloseListener(closeEvent -> {
                udmBaselineValueFilter = udmValueFiltersWindow.getAppliedValueFilter();
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
}
