package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.util.stream.Collectors;

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

    private IAclGrantDetailFilterController controller;
    private AclGrantDetailFilter grantDetailFilter = new AclGrantDetailFilter();
    private AclGrantDetailFilter appliedGrantDetailFilter = new AclGrantDetailFilter();
    private AclGrantSetFilterWidget aclGrantSetFilterWidget;
    private Button applyButton;

    @Override
    public AclGrantDetailFilter getFilter() {
        return grantDetailFilter;
    }

    @Override
    public AclGrantDetailFilter getAppliedFilter() {
        return appliedGrantDetailFilter;
    }

    @Override
    public void setController(IAclGrantDetailFilterController controller) {
        this.controller = controller;
    }

    @Override
    @SuppressWarnings("unchecked")
    public IAclGrantDetailFilterWidget init() {
        addComponents(initFiltersLayout(), initButtonsLayout());
        VaadinUtils.setMaxComponentsWidth(this);
        VaadinUtils.addComponentStyle(this, "acl-grant-detail-filter-widget");
        return this;
    }

    @Override
    public void applyFilter() {
        appliedGrantDetailFilter = new AclGrantDetailFilter(grantDetailFilter);
        filterChanged();
        fireEvent(new FilterChangedEvent(this));
    }

    @Override
    public void clearFilter() {
        aclGrantSetFilterWidget.reset();
        grantDetailFilter = new AclGrantDetailFilter();
        applyFilter();
    }

    private void filterChanged() {
        applyButton.setEnabled(!grantDetailFilter.equals(appliedGrantDetailFilter));
    }

    private VerticalLayout initFiltersLayout() {
        VerticalLayout verticalLayout = new VerticalLayout(buildFiltersHeaderLabel(), buildGrandSetFilter());
        verticalLayout.setMargin(false);
        return verticalLayout;
    }

    private AclGrantSetFilterWidget buildGrandSetFilter() {
        aclGrantSetFilterWidget = new AclGrantSetFilterWidget(() -> controller.getAllAclGrantSets());
        aclGrantSetFilterWidget.addFilterSaveListener((IFilterSaveListener<AclGrantSet>) saveEvent -> {
            grantDetailFilter.setGrantSetNames(
                saveEvent.getSelectedItemsIds().stream().map(AclGrantSet::getName).collect(Collectors.toSet()));
            filterChanged();
        });
        VaadinUtils.addComponentStyle(aclGrantSetFilterWidget, "acl-grant-sets-filter");
        return aclGrantSetFilterWidget;
    }

    private HorizontalLayout initButtonsLayout() {
        applyButton = Buttons.createButton(ForeignUi.getMessage("button.apply"));
        applyButton.setEnabled(false);
        applyButton.addClickListener(event -> applyFilter());
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFilter());
        HorizontalLayout horizontalLayout = new HorizontalLayout(applyButton, clearButton);
        VaadinUtils.setMaxComponentsWidth(horizontalLayout, applyButton, clearButton);
        VaadinUtils.addComponentStyle(horizontalLayout, "acl-grant-detail-filter-buttons");
        return horizontalLayout;
    }

    private Label buildFiltersHeaderLabel() {
        Label filterHeaderLabel = new Label(ForeignUi.getMessage("label.filters"));
        filterHeaderLabel.addStyleName(Cornerstone.LABEL_H2);
        return filterHeaderLabel;
    }
}
