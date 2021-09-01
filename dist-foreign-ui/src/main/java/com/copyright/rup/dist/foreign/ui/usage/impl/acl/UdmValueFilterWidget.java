package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterWidget;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

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
        addComponents(initFiltersLayout());
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

    private VerticalLayout initFiltersLayout() {
        VerticalLayout verticalLayout = new VerticalLayout(buildFiltersHeaderLabel());
        verticalLayout.setMargin(false);
        return verticalLayout;
    }

    private Label buildFiltersHeaderLabel() {
        Label filterHeaderLabel = new Label(ForeignUi.getMessage("label.filters"));
        filterHeaderLabel.addStyleName(Cornerstone.LABEL_H2);
        return filterHeaderLabel;
    }
}
