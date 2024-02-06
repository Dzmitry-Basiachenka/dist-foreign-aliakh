package com.copyright.rup.dist.foreign.vui.main.impl;

import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditController;
import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditWidget;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.main.api.IMainWidget;
import com.copyright.rup.dist.foreign.vui.main.api.IMainWidgetController;
import com.copyright.rup.dist.foreign.vui.report.impl.ReportWidget;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenariosController;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenariosWidget;
import com.copyright.rup.dist.foreign.vui.status.api.ICommonBatchStatusController;
import com.copyright.rup.dist.foreign.vui.status.api.ICommonBatchStatusWidget;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageController;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageWidget;
import com.copyright.rup.dist.foreign.vui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.dist.foreign.vui.usage.impl.CommonUsageWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.themes.Cornerstone;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IMediator;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IMediatorProvider;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
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
    private SwitchableWidget<ICommonAuditWidget, ICommonAuditController> auditWidget;
    private SwitchableWidget<ICommonBatchStatusWidget, ICommonBatchStatusController> batchStatusWidget;
    private Tab usagesTab;
    private Tab scenariosTab;
    private Tab auditTab;
    private Tab batchStatusTab;

    @Override
    @SuppressWarnings("unchecked")
    public MainWidget init() {
        VaadinUtils.addComponentStyle(this, Cornerstone.MAIN_TABSHEET);
        usagesWidget = new SwitchableWidget<>(controller.getUsagesControllerProvider(), widget ->
            ComponentUtil.addListener((CommonUsageWidget) widget, ScenarioCreateEvent.class,
                event -> controller.onScenarioCreated(event)));
        scenariosWidget = new SwitchableWidget<>(controller.getScenariosControllerProvider(), widget -> {});
        auditWidget = new SwitchableWidget<>(controller.getAuditControllerProvider(), widget -> {});
        batchStatusWidget = new SwitchableWidget<>(controller.getBatchStatusControllerProvider(), widget -> {});
        usagesTab = addTab(ForeignUi.getMessage("tab.usages"), usagesWidget, "main-usages-tab");
        scenariosTab = addTab(ForeignUi.getMessage("tab.scenarios"), scenariosWidget, "main-scenarios-tab");
        auditTab = addTab(ForeignUi.getMessage("tab.audit"), auditWidget, "main-audit-tab");
        batchStatusTab = addTab(ForeignUi.getMessage("tab.batch_status"), batchStatusWidget, "main-batch-statuses-tab");
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
        auditTab.setVisible(auditWidget.updateProductFamily());
        batchStatusTab.setVisible(batchStatusWidget.updateProductFamily());
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
        var suffixComponent = new HorizontalLayout();
        var refreshIcon = Buttons.createRefreshIcon();
        refreshIcon.addClickListener(event -> controller.refreshWidget());
        suffixComponent.add((ReportWidget) controller.getReportController().initWidget(), refreshIcon);
        suffixComponent.add(new HorizontalLayout(initProductFamilySelectLayout(), refreshIcon));
        suffixComponent.setJustifyContentMode(JustifyContentMode.BETWEEN);
        suffixComponent.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        VaadinUtils.addComponentStyle(suffixComponent, "reports-tab-refresh-button-layout");
        return suffixComponent;
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
            controller.getReportController().onProductFamilyChanged();
        });
        productFamilySelect.setWidth("95px");
        VaadinUtils.addComponentStyle(productFamilySelect, "global-product-family-select");
        selectLayout.add(productFamilyLabel, productFamilySelect);
        return selectLayout;
    }
}
