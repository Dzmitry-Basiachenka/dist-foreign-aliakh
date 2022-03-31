package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageFilterWidget;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Widget for filtering ACL usages.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/31/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclUsageFilterWidget extends VerticalLayout implements IAclUsageFilterWidget {

    @Override
    @SuppressWarnings("unchecked")
    public IAclUsageFilterWidget init() {
        addComponents(initFiltersLayout());
        VaadinUtils.setMaxComponentsWidth(this);
        VaadinUtils.addComponentStyle(this, "acl-usage-filter-widget");
        return this;
    }

    @Override
    public void applyFilter() {
    }

    @Override
    public void clearFilter() {
    }

    @Override
    public void setController(IAclUsageFilterController controller) {
    }

    private VerticalLayout initFiltersLayout() {
        VerticalLayout verticalLayout = new VerticalLayout(buildFiltersHeaderLabel());
        verticalLayout.setMargin(true);
        return verticalLayout;
    }

    private Label buildFiltersHeaderLabel() {
        Label filterHeaderLabel = new Label(ForeignUi.getMessage("label.filters"));
        filterHeaderLabel.addStyleName(Cornerstone.LABEL_H2);
        return filterHeaderLabel;
    }
}
