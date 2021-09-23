package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.domain.ValueStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
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
 * Implementation of {@link IUdmValueFilterWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmValueFilterWidget extends VerticalLayout implements IUdmValueFilterWidget {

    private ComboBox<ValueStatusEnum> statusComboBox;
    private ComboBox<Integer> currency;
    private Button moreFiltersButton;
    private UdmValueFilter udmValueFilter = new UdmValueFilter();
    @SuppressWarnings("unused") // TODO remove when the filter is implemented
    private IUdmValueFilterController controller;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IUdmValueFilterController}
     */
    public UdmValueFilterWidget(IUdmValueFilterController controller) {
        this.controller = controller;
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
        //TODO add implementation
    }

    @Override
    public void clearFilter() {
        //TODO add implementation
    }

    /**
     * Handles filter change event.
     */
    protected void filterChanged() {
        //TODO add implementation
    }

    private VerticalLayout initFiltersLayout() {
        initStatusFilter();
        initCurrencyFilter();
        initMoreFiltersButton();
        VerticalLayout verticalLayout =
            new VerticalLayout(buildFiltersHeaderLabel(), statusComboBox, currency, moreFiltersButton);
        verticalLayout.setMargin(false);
        return verticalLayout;
    }

    private void initCurrencyFilter() {
        currency = new ComboBox<>(ForeignUi.getMessage("label.currency"));
        VaadinUtils.setMaxComponentsWidth(currency);
        VaadinUtils.addComponentStyle(currency, "udm-value-currency-filter");
    }

    private void initStatusFilter() {
        statusComboBox = new ComboBox<>(ForeignUi.getMessage("label.status"));
        statusComboBox.setItems(ValueStatusEnum.values());
        VaadinUtils.setMaxComponentsWidth(statusComboBox);
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
        Button applyButton = Buttons.createButton(ForeignUi.getMessage("button.apply"));
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
