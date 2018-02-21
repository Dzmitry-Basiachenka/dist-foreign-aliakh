package com.copyright.rup.dist.foreign.ui.audit.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditFilterWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Controller for audit filter.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/12/18
 *
 * @author Aliaksandr Radkevich
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AuditFilterController extends CommonController<IAuditFilterWidget> implements IAuditFilterController {

    @Autowired
    private IUsageService usageService;
    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private IRightsholderService rightsholderService;

    @Override
    public List<Rightsholder> getRightsholders() {
        return rightsholderService.getFromUsages();
    }

    @Override
    public List<UsageBatch> getUsageBatches() {
        return usageBatchService.getUsageBatches();
    }

    @Override
    protected IAuditFilterWidget instantiateWidget() {
        return new AuditFilterWidget();
    }

    @Override
    public List<String> getProductFamilies() {
        return usageService.getProductFamiliesForAudit();
    }
}
