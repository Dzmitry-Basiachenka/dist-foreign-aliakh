package com.copyright.rup.dist.foreign.ui.main.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditController;
import com.copyright.rup.dist.foreign.ui.main.api.IMainWidget;
import com.copyright.rup.dist.foreign.ui.main.api.IMainWidgetController;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.dist.foreign.ui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.vaadin.widget.api.TabController;

import com.vaadin.ui.TabSheet;

import org.springframework.beans.factory.annotation.Autowired;
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

    private static final int SCENARIOS_TAB_INDEX = 1;

    @Autowired
    private IUsagesController fasUsagesController;
    @Autowired
    private IUsagesController ntsUsagesController;
    @Autowired
    private IScenariosController fasScenariosController;
    @Autowired
    private IScenariosController ntsScenariosController;
    @Autowired
    private IAuditController fasAuditController;
    @Autowired
    private IAuditController ntsAuditController;
    @Autowired
    private IProductFamilyProvider productFamilyProvider;

    @Override
    public IUsagesController getUsagesController() {
        // TODO {srudak} move logic to switch between product families to a separate component
        return FdaConstants.NTS_PRODUCT_FAMILY.equals(productFamilyProvider.getProductFamily())
            ? ntsUsagesController
            : fasUsagesController;
    }

    @Override
    public IScenariosController getScenariosController() {
        // TODO {srudak} move logic to switch between product families to a separate component
        return FdaConstants.NTS_PRODUCT_FAMILY.equals(productFamilyProvider.getProductFamily())
            ? ntsScenariosController
            : fasScenariosController;
    }

    @Override
    public void onScenarioCreated(ScenarioCreateEvent event) {
        getTabSheet().setSelectedTab(SCENARIOS_TAB_INDEX);
        getScenariosController().getWidget().selectScenario(event.getScenarioId());
    }

    @Override
    public void onProductFamilyChanged() {
        refreshWidget();
    }

    @Override
    public IAuditController getAuditController() {
        // TODO {srudak} move logic to switch between product families to a separate component
        return FdaConstants.NTS_PRODUCT_FAMILY.equals(productFamilyProvider.getProductFamily())
            ? ntsAuditController
            : fasAuditController;
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
