package com.copyright.rup.dist.foreign.ui.status.impl;

import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.ui.status.api.IAaclBatchStatusController;
import com.copyright.rup.dist.foreign.ui.status.api.ICommonBatchStatusWidget;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link IAaclBatchStatusController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 02/12/2021
 *
 * @author Ihar Suvorau
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AaclBatchStatusController extends CommonBatchStatusController implements IAaclBatchStatusController {

    private static final long serialVersionUID = -6647817195661634746L;

    @Override
    public List<UsageBatchStatus> getBatchStatuses() {
        return getUsageBatchStatusService().getUsageBatchStatusesAacl();
    }

    @Override
    protected ICommonBatchStatusWidget instantiateWidget() {
        return new AaclBatchStatusWidget();
    }
}
