package com.copyright.rup.dist.foreign.vui.main.impl;

import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.main.api.IMainWidget;
import com.copyright.rup.dist.foreign.vui.main.api.IMainWidgetController;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageController;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.themes.Cornerstone;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IMediator;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IMediatorProvider;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;

/**
 * Main widget of the application.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/16/17
 *
 * @author Nikita Levyankov
 */
public class MainWidget extends TabSheet implements IMainWidget, IMediatorProvider {

    private IMainWidgetController controller;
    private Select<String> productFamilySelect = new Select<>(ForeignUi.getMessage("label.product_family"));

    private SwitchableWidget<ICommonUsageWidget, ICommonUsageController> usagesWidget;
    private Tab usagesTab;

    @Override
    @SuppressWarnings("unchecked")
    public MainWidget init() {
        VaadinUtils.addComponentStyle(this, Cornerstone.MAIN_TABSHEET);
        usagesWidget = new SwitchableWidget<>(controller.getUsagesControllerProvider(), widget -> {
        });
        usagesTab = addTab(ForeignUi.getMessage("tab.usages"), usagesWidget, "main-usages-tab");
        updateProductFamily();
        setSuffixComponent(createSuffixComponent());
        setSizeFull();
        addThemeVariants(TabSheetVariant.LUMO_TABS_SMALL);
        controller.refreshWidget();
        return this;
    }

    @Override
    public void updateProductFamily() {
        usagesTab.setVisible(usagesWidget.updateProductFamily());
    }

    @Override
    public void setController(IMainWidgetController controller) {
        this.controller = controller;
    }

    private Tab addTab(String caption, Component component, String style) {
        var tab = add(caption, component);
        VaadinUtils.addComponentStyle(tab, style);
        return tab;
    }

    private HorizontalLayout createSuffixComponent() {
        var horizontalLayout = new HorizontalLayout();
        var refreshIcon = Buttons.createRefreshIcon();
        productFamilySelect = initProductFamilySelect();
        refreshIcon.addClickListener(event -> controller.refreshWidget());
        horizontalLayout.add(productFamilySelect, refreshIcon);
        horizontalLayout.setJustifyContentMode(JustifyContentMode.END);
        horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        VaadinUtils.addComponentStyle(horizontalLayout, "reports-tab-refresh-button-layout");
        return horizontalLayout;
    }

    @Override
    public IMediator initMediator() {
        var mediator = new MainWidgetMediator();
        mediator.setController(controller);
        mediator.setProductFamilyProvider(controller.getProductFamilyProvider());
        mediator.setProductFamilySelect(productFamilySelect);
        return mediator;
    }

    private Select<String> initProductFamilySelect() {
        productFamilySelect.addValueChangeListener(event -> {
            controller.getProductFamilyProvider().setProductFamily(event.getValue());
            controller.onProductFamilyChanged();
        });
        productFamilySelect.setWidth("200px");
        VaadinUtils.addComponentStyle(productFamilySelect, "global-product-family-select");
        return productFamilySelect;
    }
}
