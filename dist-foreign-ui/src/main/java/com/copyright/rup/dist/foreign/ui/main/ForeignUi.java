package com.copyright.rup.dist.foreign.ui.main;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.ui.main.api.IMainWidgetController;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.report.api.IReportController;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclReportController;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclReportWidget;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmReportWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.CommonUi;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.RootWidget;
import com.copyright.rup.vaadin.widget.api.IController;
import com.copyright.rup.vaadin.widget.api.IMediator;
import com.copyright.rup.vaadin.widget.api.IMediatorProvider;

import com.google.common.collect.Lists;
import com.vaadin.annotations.Theme;
import com.vaadin.server.ErrorHandler;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Entry point for application.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/16/17
 *
 * @author Nikita Levyankovov
 */
@org.springframework.stereotype.Component("dist.foreignUi")
@EnableVaadin
@Theme("foreign")
@SpringUI
public class ForeignUi extends CommonUi implements IMediatorProvider {

    private static final ResourceBundle MESSAGES =
        ResourceBundle.getBundle("com.copyright.rup.dist.foreign.ui.messages");
    private static final String UDM_TAB = "UDM";
    private static final String CALCULATIONS_TAB = "Calculations";

    private static final String REPORT_MENU_CSS_POSITION = "left: 385px;";
    private static final String UDM_REPORT_MENU_CSS_POSITION_MANAGER = "left: 415px; top: 29px;";
    private static final String UDM_REPORT_MENU_CSS_POSITION_SPECIALIST = "left: 540px; top: 29px;";
    private static final String UDM_REPORT_MENU_CSS_POSITION_VIEW_ONLY = "left: 415px; top: 29px;";
    private static final String UDM_REPORT_MENU_CSS_POSITION_RESEARCHER = "left: 165px; top: 29px;";
    private static final String ACL_REPORT_MENU_CSS_POSITION = "left: 400px; top: 29px;";

    @Autowired
    private IMainWidgetController controller;
    @Autowired
    private IReportController reportController;
    @Autowired
    private IProductFamilyProvider productFamilyProvider;
    @Autowired
    private IUdmReportController udmReportController;
    @Autowired
    private IAclReportController aclReportController;

    private ComboBox<String> productFamilyComboBox;
    private IUdmReportWidget udmReportWidget;
    private IAclReportWidget aclReportWidget;

    /**
     * Gets a message associated with specified {@code key}.
     *
     * @param key        the key to get {@code message}
     * @param parameters arguments referenced by the format specifiers in the format string
     * @return the string message
     */
    public static String getMessage(String key, Object... parameters) {
        return String.format(MESSAGES.getString(key), parameters);
    }

    @Override
    public IMediator initMediator() {
        ForeignUiMediator mediator = new ForeignUiMediator();
        productFamilyComboBox = new ComboBox<>(getMessage("label.product_family"));
        mediator.setProductFamilyComboBox(productFamilyComboBox);
        mediator.setController(controller);
        mediator.setProductFamilyProvider(productFamilyProvider);
        mediator.setReportController(reportController);
        return mediator;
    }

    @Override
    protected void initUi() {
        super.initUi();
        controller.refreshWidget();
        addReportMenu();
    }

    @Override
    protected Component initMainWidget() {
        return controller.initWidget();
    }

    @Override
    protected String getApplicationTitle() {
        return getMessage("application.name");
    }

    @Override
    protected boolean hasAccessPermission() {
        return ForeignSecurityUtils.hasAccessPermission();
    }

    @Override
    protected ErrorHandler initErrorHandler() {
        return new ForeignErrorHandler(this);
    }

    @Override
    protected List<Component> getAdditionalComponents() {
        return Lists.newArrayList(initProductFamilyComboBox(), initRefreshButton());
    }

    /**
     * Sets main widget controller.
     *
     * @param mainWidgetController main widget controller
     */
    void setMainWidgetController(IMainWidgetController mainWidgetController) {
        this.controller = mainWidgetController;
    }

    private ComboBox<String> initProductFamilyComboBox() {
        initMediator().applyPermissions();
        productFamilyComboBox.setEmptySelectionAllowed(false);
        productFamilyComboBox.setTextInputAllowed(false);
        productFamilyComboBox.addValueChangeListener(event -> {
            productFamilyProvider.setProductFamily(event.getValue());
            controller.onProductFamilyChanged();
            reportController.onProductFamilyChanged();
        });
        VaadinUtils.setMaxComponentsWidth(productFamilyComboBox);
        VaadinUtils.addComponentStyle(productFamilyComboBox, "global-product-family-combo-box");
        return productFamilyComboBox;
    }

    private Button initRefreshButton() {
        Button refreshButton = Buttons.createRefreshIcon();
        refreshButton.addListener(Button.ClickEvent.class, controller, IController.REFRESH_WIDGET_HANDLER);
        return refreshButton;
    }

    private void addReportMenu() {
        initAclReportWidgets();
        getAbsoluteLayout().addComponent(reportController.initWidget(), REPORT_MENU_CSS_POSITION);
        initMainWidgetSelectedTabChangeListener();
    }

    private void initAclReportWidgets() {
        initUdmReportWidget();
        initAclReportWidget();
        switchAclReportWidgets();
    }

    private void initUdmReportWidget() {
        udmReportWidget = udmReportController.initWidget();
        VaadinUtils.addComponentStyle(udmReportWidget, "udm-reports-menu");
    }

    private void initAclReportWidget() {
        aclReportWidget = aclReportController.initWidget();
        VaadinUtils.addComponentStyle(aclReportWidget, "acl-reports-menu");
    }

    private void switchAclReportWidgets() {
        String selectedTabName = getSelectedTabName();
        if (Objects.nonNull(selectedTabName)) {
            if (isAclProductFamily()) {
                if (UDM_TAB.equals(selectedTabName)) {
                    addUdmReportWidgetToAbsoluteLayout();
                    getAbsoluteLayout().removeComponent(aclReportWidget);
                } else if (CALCULATIONS_TAB.equals(selectedTabName)) {
                    getAbsoluteLayout().addComponent(aclReportWidget, ACL_REPORT_MENU_CSS_POSITION);
                    getAbsoluteLayout().removeComponent(udmReportWidget);
                }
            } else {
                getAbsoluteLayout().removeComponent(udmReportWidget);
                getAbsoluteLayout().removeComponent(aclReportWidget);
            }
        }
    }

    private String getSelectedTabName() {
        TabSheet mainWidget = (TabSheet) controller.getWidget();
        Component selectedTab = mainWidget.getSelectedTab();
        return Objects.nonNull(selectedTab) ? mainWidget.getTab(selectedTab).getCaption() : null;
    }

    private boolean isAclProductFamily() {
        return FdaConstants.ACL_PRODUCT_FAMILY.equals(productFamilyProvider.getSelectedProductFamily());
    }

    private void addUdmReportWidgetToAbsoluteLayout() {
        if (ForeignSecurityUtils.hasResearcherPermission()) {
            getAbsoluteLayout().addComponent(udmReportWidget, UDM_REPORT_MENU_CSS_POSITION_RESEARCHER);
        } else if (ForeignSecurityUtils.hasSpecialistPermission()) {
            getAbsoluteLayout().addComponent(udmReportWidget, UDM_REPORT_MENU_CSS_POSITION_SPECIALIST);
        } else if (ForeignSecurityUtils.hasManagerPermission()) {
            getAbsoluteLayout().addComponent(udmReportWidget, UDM_REPORT_MENU_CSS_POSITION_MANAGER);
        } else if (ForeignSecurityUtils.hasViewOnlyPermission()) {
            getAbsoluteLayout().addComponent(udmReportWidget, UDM_REPORT_MENU_CSS_POSITION_VIEW_ONLY);
        }
    }

    private AbsoluteLayout getAbsoluteLayout() {
        return ((RootWidget) this.getUI().getContent()).getAbsoluteLayout();
    }

    private void initMainWidgetSelectedTabChangeListener() {
        ((TabSheet) controller.getWidget()).addSelectedTabChangeListener(event -> switchAclReportWidgets());
    }
}
