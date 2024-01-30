package com.copyright.rup.dist.foreign.vui.status.impl;

import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.status.api.ICommonBatchStatusWidget;
import com.copyright.rup.dist.foreign.vui.status.api.IFasBatchStatusController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link IFasBatchStatusController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 02/12/2021
 *
 * @author Ihar Suvorau
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FasBatchStatusController extends CommonBatchStatusController implements IFasBatchStatusController {

    private static final long serialVersionUID = 7208362337278267183L;

    @Autowired
    private IProductFamilyProvider productFamilyProvider;

    @Override
    public List<UsageBatchStatus> getBatchStatuses() {
        return getUsageBatchStatusService().getUsageBatchStatusesFas(productFamilyProvider.getSelectedProductFamily());
    }

    @Override
    protected ICommonBatchStatusWidget instantiateWidget() {
        return new FasBatchStatusWidget();
    }
}
