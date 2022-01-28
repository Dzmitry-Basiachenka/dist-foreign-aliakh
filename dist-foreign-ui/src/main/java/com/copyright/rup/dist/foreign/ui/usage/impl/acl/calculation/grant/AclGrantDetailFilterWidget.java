package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailFilterWidget;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Widget for filtering ACL grant details.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/27/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclGrantDetailFilterWidget extends VerticalLayout implements IAclGrantDetailFilterWidget {

    @Override
    @SuppressWarnings("unchecked")
    public IAclGrantDetailFilterWidget init() {
        addComponents(initFiltersLayout());
        setSizeFull();
        VaadinUtils.setMaxComponentsWidth(this);
        VaadinUtils.addComponentStyle(this, "acl-grant-detail-filter-widget");
        return this;
    }

    @Override
    public void applyFilter() {
    }

    @Override
    public void clearFilter() {
    }

    @Override
    public void setController(IAclGrantDetailFilterController controller) {
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
