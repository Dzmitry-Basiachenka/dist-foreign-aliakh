package com.copyright.rup.dist.foreign.vui.main.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.vui.status.api.ICommonBatchStatusController;
import com.copyright.rup.dist.foreign.vui.status.api.IFasBatchStatusController;

import com.copyright.rup.dist.foreign.vui.status.api.INtsBatchStatusController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Implementation of {@link com.copyright.rup.dist.foreign.vui.main.api.IControllerProvider} for usage batch status
 * controllers.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/02/2021
 *
 * @author Ihar Suvorau
 */
@Component("dist.foreign.batchStatusControllerProvider")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BatchStatusControllerProvider extends CommonControllerProvider<ICommonBatchStatusController> {

    @Autowired
    private IFasBatchStatusController fasBatchStatusController;
    @Autowired
    private INtsBatchStatusController ntsBatchStatusController;

    @Override
    protected Map<String, ICommonBatchStatusController> getProductFamilyToControllerMap() {
        return Map.of(
            FdaConstants.FAS_PRODUCT_FAMILY, fasBatchStatusController,
            FdaConstants.CLA_FAS_PRODUCT_FAMILY, fasBatchStatusController,
            FdaConstants.NTS_PRODUCT_FAMILY, ntsBatchStatusController
        );
    }
}
