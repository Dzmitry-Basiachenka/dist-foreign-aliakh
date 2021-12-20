package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline;

import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineFilterWidget;
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
 * Implementation of {@link IUdmBaselineFilterWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Uladzislau Shalamitski
 */
public class UdmBaselineFilterWidget extends VerticalLayout implements IUdmBaselineFilterWidget {

    private final UdmBaselineAppliedFilterWidget appliedFilterWidget;
    private IUdmBaselineFilterController controller;
    private Button applyButton;
    private Button moreFiltersButton;
    private ComboBox<Integer> periodComboBox;
    private ComboBox<UdmUsageOriginEnum> usageOriginComboBox;
    private ComboBox<UdmChannelEnum> channelComboBox;
    private UdmBaselineFilter baselineFilter = new UdmBaselineFilter();
    private UdmBaselineFilter appliedBaselineFilter = new UdmBaselineFilter();

    /**
     * Constructor.
     *
     * @param controller instance of {@link IUdmBaselineFilterController}
     */
    public UdmBaselineFilterWidget(IUdmBaselineFilterController controller) {
        this.controller = controller;
        this.appliedFilterWidget = new UdmBaselineAppliedFilterWidget();
    }

    @Override
    public void setController(IUdmBaselineFilterController controller) {
        this.controller = controller;
    }

    @Override
    @SuppressWarnings("unchecked")
    public IUdmBaselineFilterWidget init() {
        addComponents(initFiltersLayout(), initButtonsLayout(), buildAppliedFiltersHeaderLabel(),
            appliedFilterWidget);
        refreshFilter();
        VaadinUtils.setMaxComponentsWidth(this);
        VaadinUtils.addComponentStyle(this, "udm-baseline-filter-widget");
        return this;
    }

    @Override
    public void applyFilter() {
        appliedBaselineFilter = new UdmBaselineFilter(baselineFilter);
        appliedFilterWidget.refreshFilterPanel(appliedBaselineFilter);
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
    public UdmBaselineFilter getFilter() {
        return baselineFilter;
    }

    @Override
    public UdmBaselineFilter getAppliedFilter() {
        return appliedBaselineFilter;
    }

    private void filterChanged() {
        applyButton.setEnabled(!baselineFilter.equals(appliedBaselineFilter));
    }

    private void refreshFilter() {
        refreshFilterValues();
        baselineFilter = new UdmBaselineFilter();
    }

    private void refreshFilterValues() {
        periodComboBox.setItems(controller.getPeriods());
        usageOriginComboBox.setItems(UdmUsageOriginEnum.values());
        channelComboBox.setItems(UdmChannelEnum.values());
    }

    private void clearFilterValues() {
        periodComboBox.clear();
        usageOriginComboBox.clear();
        channelComboBox.clear();
    }

    private VerticalLayout initFiltersLayout() {
        initPeriodFilter();
        initUsageOriginFilter();
        initChannelFilter();
        initMoreFiltersButton();
        VerticalLayout verticalLayout = new VerticalLayout(buildFiltersHeaderLabel(), periodComboBox,
            usageOriginComboBox, channelComboBox, moreFiltersButton);
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
        VaadinUtils.addComponentStyle(horizontalLayout, "udm-baseline-filter-buttons");
        return horizontalLayout;
    }

    private void initPeriodFilter() {
        periodComboBox = new ComboBox<>(ForeignUi.getMessage("label.period"));
        VaadinUtils.setMaxComponentsWidth(periodComboBox);
        periodComboBox.addValueChangeListener(event -> {
            baselineFilter.setPeriod(periodComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(periodComboBox, "udm-baseline-period-filter");
    }

    private void initUsageOriginFilter() {
        usageOriginComboBox = new ComboBox<>(ForeignUi.getMessage("label.usage_origin"));
        VaadinUtils.setMaxComponentsWidth(usageOriginComboBox);
        usageOriginComboBox.addValueChangeListener(event -> {
            baselineFilter.setUdmUsageOrigin(usageOriginComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(usageOriginComboBox, "udm-baseline-usage-origin-filter");
    }

    private void initChannelFilter() {
        channelComboBox = new ComboBox<>(ForeignUi.getMessage("label.channel"));
        VaadinUtils.setMaxComponentsWidth(channelComboBox);
        channelComboBox.addValueChangeListener(event -> {
            baselineFilter.setChannel(channelComboBox.getValue());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(channelComboBox, "udm-baseline-channel-filter");
    }

    private void initMoreFiltersButton() {
        moreFiltersButton = new Button(ForeignUi.getMessage("label.more_filters"));
        moreFiltersButton.addStyleName(ValoTheme.BUTTON_LINK);
        moreFiltersButton.addClickListener(event -> {
            UdmBaselineFiltersWindow udmFiltersWindow = new UdmBaselineFiltersWindow(controller, baselineFilter);
            Windows.showModalWindow(udmFiltersWindow);
            udmFiltersWindow.addCloseListener(closeEvent -> {
                baselineFilter = udmFiltersWindow.getAppliedBaselineFilter();
                filterChanged();
            });
        });
    }

    private Label buildFiltersHeaderLabel() {
        Label filterHeaderLabel = new Label(ForeignUi.getMessage("label.filters"));
        filterHeaderLabel.addStyleName(Cornerstone.LABEL_H2);
        return filterHeaderLabel;
    }

    private Label buildAppliedFiltersHeaderLabel() {
        Label appliedFilterHeaderLabel = new Label("Applied Filters:");
        appliedFilterHeaderLabel.addStyleNames(Cornerstone.LABEL_H2, "udm-applied-filter-header");
        return appliedFilterHeaderLabel;
    }
}
