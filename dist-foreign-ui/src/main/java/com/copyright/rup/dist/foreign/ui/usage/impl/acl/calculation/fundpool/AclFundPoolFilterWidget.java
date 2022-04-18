package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolFilterWidget;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Widget for filtering ACL fund pools.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/17/2022
 *
 * @author Anton Azarenka
 */
public class AclFundPoolFilterWidget extends VerticalLayout implements IAclFundPoolFilterWidget {

    @Override
    public void applyFilter() {
        //TODO will be implemented later
    }

    @Override
    public void clearFilter() {
        //TODO will be implemented later
    }

    @Override
    @SuppressWarnings("unchecked")
    public AclFundPoolFilterWidget init() {
        addComponents(initFiltersLayout());
        VaadinUtils.setMaxComponentsWidth(this);
        return this;
    }

    private VerticalLayout initFiltersLayout() {
        VerticalLayout verticalLayout = new VerticalLayout(buildFiltersHeaderLabel());
        verticalLayout.setMargin(false);
        return verticalLayout;
    }

    @Override
    public void setController(IAclFundPoolFilterController controller) {
        //TODO will be implemented later
    }

    private Label buildFiltersHeaderLabel() {
        Label filterHeaderLabel = new Label(ForeignUi.getMessage("label.filters"));
        filterHeaderLabel.addStyleName(Cornerstone.LABEL_H2);
        return filterHeaderLabel;
    }
}
