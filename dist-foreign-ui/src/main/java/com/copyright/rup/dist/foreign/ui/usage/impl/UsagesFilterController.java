package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
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
    private IUsageBatchService usageBatchService;

    @Autowired
    private IRightsholderService rightsholderService;

    @Override
    public List<UsageBatch> getUsageBatchesNotIncludedIntoScenario() {
        return usageBatchService.getUsageBatchesForFilter();
    }

    @Override
    public List<Rightsholder> getRros() {
        return rightsholderService.getRros();
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
