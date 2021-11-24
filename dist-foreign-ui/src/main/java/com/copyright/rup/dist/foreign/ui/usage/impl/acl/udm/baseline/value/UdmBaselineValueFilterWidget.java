package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline.value;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
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

    private Button moreFiltersButton;
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
        //TODO will implement later
    }

    @Override
    public void clearFilter() {
        //TODO will implement later
    }

    @Override
    public void setController(IUdmBaselineValueFilterController controller) {
        this.controller = controller;
    }

    private VerticalLayout initFiltersLayout() {
        initMoreFiltersButton();
        VerticalLayout verticalLayout = new VerticalLayout(buildFiltersHeaderLabel(), moreFiltersButton);
        verticalLayout.setMargin(false);
        VaadinUtils.setButtonsAutoDisabled(moreFiltersButton);
        return verticalLayout;
    }

    private void initMoreFiltersButton() {
        moreFiltersButton = new Button(ForeignUi.getMessage("label.more_filters"));
        moreFiltersButton.addStyleName(ValoTheme.BUTTON_LINK);
    }

    private Label buildFiltersHeaderLabel() {
        Label filterHeaderLabel = new Label(ForeignUi.getMessage("label.filters"));
        filterHeaderLabel.addStyleName(Cornerstone.LABEL_H2);
        return filterHeaderLabel;
    }

    private HorizontalLayout initButtonsLayout() {
        Button applyButton = Buttons.createButton(ForeignUi.getMessage("button.apply"));
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
