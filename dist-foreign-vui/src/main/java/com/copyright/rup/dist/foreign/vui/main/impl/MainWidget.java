package com.copyright.rup.dist.foreign.vui.main.impl;

import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.main.api.IMainWidget;
import com.copyright.rup.dist.foreign.vui.main.api.IMainWidgetController;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenariosController;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenariosWidget;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageController;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.themes.Cornerstone;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IMediator;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IMediatorProvider;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Label;
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

    private static final long serialVersionUID = 2631811534809187222L;

    private IMainWidgetController controller;
    private Select<String> productFamilySelect;

    private SwitchableWidget<ICommonUsageWidget, ICommonUsageController> usagesWidget;
    private SwitchableWidget<ICommonScenariosWidget, ICommonScenariosController> scenariosWidget;
    private Tab usagesTab;
    private Tab scenariosTab;

    @Override
    @SuppressWarnings("unchecked")
    public MainWidget init() {
        VaadinUtils.addComponentStyle(this, Cornerstone.MAIN_TABSHEET);
        usagesWidget = new SwitchableWidget<>(controller.getUsagesControllerProvider(), widget -> {});
        scenariosWidget = new SwitchableWidget<>(controller.getScenariosControllerProvider(), widget -> {});
        usagesTab = addTab(ForeignUi.getMessage("tab.usages"), usagesWidget, "main-usages-tab");
        scenariosTab = addTab(ForeignUi.getMessage("tab.scenarios"), scenariosWidget, "main-scenarios-tab");
        addSelectedChangeListener(event -> controller.refreshWidget());
        setSuffixComponent(createSuffixComponent());
        setSizeFull();
        addThemeVariants(TabSheetVariant.LUMO_TABS_SMALL);
        controller.refreshWidget();
        return this;
    }

    @Override
    public void updateProductFamily() {
        usagesTab.setVisible(usagesWidget.updateProductFamily());
        scenariosTab.setVisible(scenariosWidget.updateProductFamily());
    }

    @Override
    public void setController(IMainWidgetController controller) {
        this.controller = controller;
    }

    @Override
    public IMediator initMediator() {
        var mediator = new MainWidgetMediator();
        mediator.setController(controller);
        mediator.setProductFamilyProvider(controller.getProductFamilyProvider());
        mediator.setProductFamilySelect(productFamilySelect);
        return mediator;
    }

    private Tab addTab(String caption, Component component, String style) {
        var tab = add(caption, component);
        VaadinUtils.addComponentStyle(tab, style);
        return tab;
    }

    private HorizontalLayout createSuffixComponent() {
        var horizontalLayout = new HorizontalLayout();
        var refreshIcon = Buttons.createRefreshIcon();
        refreshIcon.addClickListener(event -> controller.refreshWidget());
        horizontalLayout.add(initProductFamilySelectLayout(), refreshIcon);
        horizontalLayout.setJustifyContentMode(JustifyContentMode.END);
        horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        VaadinUtils.addComponentStyle(horizontalLayout, "reports-tab-refresh-button-layout");
        return horizontalLayout;
    }

    private HorizontalLayout initProductFamilySelectLayout() {
        var selectLayout = new HorizontalLayout();
        selectLayout.setPadding(false);
        selectLayout.setSpacing(false);
        VaadinUtils.addComponentStyle(selectLayout, "global-product-family-select-layout");
        var productFamilyLabel = new Label(ForeignUi.getMessage("label.product_family"));
        productFamilySelect = new Select<>();
        productFamilySelect.addValueChangeListener(event -> {
            controller.getProductFamilyProvider().setProductFamily(event.getValue());
            controller.onProductFamilyChanged();
        });
        productFamilySelect.setWidth("95px");
        VaadinUtils.addComponentStyle(productFamilySelect, "global-product-family-select");
        selectLayout.add(productFamilyLabel, productFamilySelect);
        return selectLayout;
    }
}
