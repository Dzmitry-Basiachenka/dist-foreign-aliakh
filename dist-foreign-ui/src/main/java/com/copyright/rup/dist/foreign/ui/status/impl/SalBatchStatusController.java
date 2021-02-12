package com.copyright.rup.dist.foreign.ui.status.impl;

import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.ui.status.api.ICommonBatchStatusWidget;
import com.copyright.rup.dist.foreign.ui.status.api.ISalBatchStatusController;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link ISalBatchStatusController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 02/12/2021
 *
 * @author Ihar Suvorau
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SalBatchStatusController extends CommonBatchStatusController implements ISalBatchStatusController {

    @Override
    public List<UsageBatchStatus> getBatchStatuses() {
        return getUsageBatchStatusService().getUsageBatchStatusesSal();
    }

    @Override
    protected ICommonBatchStatusWidget instantiateWidget() {
        return new SalBatchStatusWidget();
    }
}
