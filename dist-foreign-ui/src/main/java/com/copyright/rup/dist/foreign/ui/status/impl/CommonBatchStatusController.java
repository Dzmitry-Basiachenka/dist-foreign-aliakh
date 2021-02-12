package com.copyright.rup.dist.foreign.ui.status.impl;

import com.copyright.rup.dist.foreign.service.api.IUsageBatchStatusService;
import com.copyright.rup.dist.foreign.ui.status.api.ICommonBatchStatusController;
import com.copyright.rup.dist.foreign.ui.status.api.ICommonBatchStatusWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Common implementation for usage batch status controllers.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 02/12/2021
 *
 * @author Ihar Suvorau
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public abstract class CommonBatchStatusController extends CommonController<ICommonBatchStatusWidget>
    implements ICommonBatchStatusController {

    @Autowired
    private IUsageBatchStatusService usageBatchStatusService;

    /**
     * Instantiates widget.
     *
     * @return {@link ICommonBatchStatusWidget} instance
     */
    protected abstract ICommonBatchStatusWidget instantiateWidget();

    protected IUsageBatchStatusService getUsageBatchStatusService() {
        return usageBatchStatusService;
    }
}

