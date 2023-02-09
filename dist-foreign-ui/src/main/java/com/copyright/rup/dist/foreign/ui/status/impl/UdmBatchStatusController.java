package com.copyright.rup.dist.foreign.ui.status.impl;

import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchStatusService;
import com.copyright.rup.dist.foreign.ui.status.api.IUdmBatchStatusController;
import com.copyright.rup.dist.foreign.ui.status.api.IUdmBatchStatusWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link IUdmBatchStatusController}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/08/2023
 *
 * @author Dzmitry Basiachenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmBatchStatusController extends CommonController<IUdmBatchStatusWidget>
    implements IUdmBatchStatusController {

    @Autowired
    private IUsageBatchStatusService usageBatchStatusService;

    @Override
    public List<UsageBatchStatus> getBatchStatuses() {
        return usageBatchStatusService.getUsageBatchStatusesUdm();
    }

    @Override
    protected IUdmBatchStatusWidget instantiateWidget() {
        return new UdmBatchStatusWidget();
    }
}
