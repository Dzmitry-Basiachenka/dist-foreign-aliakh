package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineFilterWidget;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Implementation of {@link IUdmBaselineFilterWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Uladzislau Shalamitski
 */
public class UdmBaselineFilterWidget extends VerticalLayout implements IUdmBaselineFilterWidget {

    @SuppressWarnings("unused") // TODO remove when the filter is implemented
    private IUdmBaselineFilterController controller;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IUdmBaselineFilterController}
     */
    public UdmBaselineFilterWidget(IUdmBaselineFilterController controller) {
        this.controller = controller;
    }

    @Override
    public void setController(IUdmBaselineFilterController controller) {
        this.controller = controller;
    }

    @Override
    @SuppressWarnings("unchecked")
    public IUdmBaselineFilterWidget init() {
        addComponents(initFiltersLayout());
        VaadinUtils.setMaxComponentsWidth(this);
        VaadinUtils.addComponentStyle(this, "udm-baseline-filter-widget");
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
