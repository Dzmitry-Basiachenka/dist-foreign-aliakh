package com.copyright.rup.dist.foreign.vui.main.impl;

import com.copyright.rup.dist.foreign.vui.main.api.IMainWidget;
import com.copyright.rup.dist.foreign.vui.main.api.IMainWidgetController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.TabController;

import com.vaadin.flow.component.tabs.TabSheet;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of controller for {@link IMainWidget}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/16/17
 *
 * @author Nikita Levyankov
 */
@Component("MainController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MainWidgetController extends TabController<IMainWidget> implements IMainWidgetController {

    @Override
    public void onProductFamilyChanged() {
        getWidget().updateProductFamily();
        refreshWidget();
    }

    @Override
    protected TabSheet getTabSheet() {
        return (TabSheet) getWidget();
    }

    @Override
    protected IMainWidget instantiateWidget() {
        return new MainWidget();
    }
}
