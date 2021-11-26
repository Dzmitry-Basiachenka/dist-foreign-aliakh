package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.proxy;

import com.copyright.rup.dist.foreign.domain.filter.UdmProxyValueFilter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmProxyValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmProxyValueFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Implementation of {@link IUdmProxyValueFilterWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Uladzislau Shalamitski
 */
public class UdmProxyValueFilterWidget extends VerticalLayout implements IUdmProxyValueFilterWidget {

    private Button applyButton;
    private UdmProxyValueFilter udmValueFilter = new UdmProxyValueFilter();
    private UdmProxyValueFilter appliedUdmValueFilter = new UdmProxyValueFilter();

    @Override
    public UdmProxyValueFilter getFilter() {
        return udmValueFilter;
    }

    @Override
    public UdmProxyValueFilter getAppliedFilter() {
        return appliedUdmValueFilter;
    }

    @Override
    public void setController(IUdmProxyValueFilterController controller) {
        //TODO: set controller
    }

    @Override
    @SuppressWarnings("unchecked")
    public IUdmProxyValueFilterWidget init() {
        addComponents(initFiltersLayout(), initButtonsLayout());
        VaadinUtils.setMaxComponentsWidth(this);
        VaadinUtils.addComponentStyle(this, "udm-proxy-value-filter-widget");
        return this;
    }

    @Override
    public void applyFilter() {
        appliedUdmValueFilter = new UdmProxyValueFilter(udmValueFilter);
        filterChanged();
        fireEvent(new FilterChangedEvent(this));
    }

    @Override
    public void clearFilter() {
        // TODO: Clear filter values
        udmValueFilter = new UdmProxyValueFilter();
        applyFilter();
    }

    private void filterChanged() {
        applyButton.setEnabled(!udmValueFilter.equals(appliedUdmValueFilter));
    }

    private VerticalLayout initFiltersLayout() {
        // TODO: Add filters for periods and pub type codes
        VerticalLayout verticalLayout = new VerticalLayout(buildFiltersHeaderLabel());
        verticalLayout.setMargin(false);
        return verticalLayout;
    }

    private HorizontalLayout initButtonsLayout() {
        applyButton = Buttons.createButton(ForeignUi.getMessage("button.apply"));
        applyButton.setEnabled(false);
        applyButton.addClickListener(event -> applyFilter());
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFilter());
        HorizontalLayout horizontalLayout = new HorizontalLayout(applyButton, clearButton);
        VaadinUtils.setMaxComponentsWidth(horizontalLayout, applyButton, clearButton);
        VaadinUtils.addComponentStyle(horizontalLayout, "udm-proxy-value-filter-buttons");
        return horizontalLayout;
    }

    private Label buildFiltersHeaderLabel() {
        Label filterHeaderLabel = new Label(ForeignUi.getMessage("label.filters"));
        filterHeaderLabel.addStyleName(Cornerstone.LABEL_H2);
        return filterHeaderLabel;
    }
}
