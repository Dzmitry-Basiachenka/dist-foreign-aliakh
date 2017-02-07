package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonFilterWindowController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Controller for filtering usages.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/11/2017
 *
 * @author Mikita Hladkikh
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UsagesFilterController extends CommonController<IUsagesFilterWidget> implements IUsagesFilterController {

    @Autowired
    @Qualifier("usageBatchesFilterController")
    private ICommonFilterWindowController batchesFilterController;

    @Autowired
    @Qualifier("rightsholderFilterWindowController")
    private ICommonFilterWindowController rightsholdersFilterController;

    @Autowired
    private IUsageBatchService usageBatchService;

    @Override
    public void onUsageBatchFilterClick() {
        batchesFilterController.setFilterWidget(getWidget());
        batchesFilterController.showFilterWindow();
    }

    @Override
    public void onRightsholderFilterClick() {
        rightsholdersFilterController.setFilterWidget(getWidget());
        rightsholdersFilterController.showFilterWindow();
    }

    @Override
    public List<Integer> getFiscalYears() {
        return usageBatchService.getFiscalYears();
    }

    @Override
    protected UsagesFilterWidget instantiateWidget() {
        return new UsagesFilterWidget();
    }
}
