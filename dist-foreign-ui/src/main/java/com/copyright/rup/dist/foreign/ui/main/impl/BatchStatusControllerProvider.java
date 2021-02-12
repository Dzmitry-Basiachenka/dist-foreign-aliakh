package com.copyright.rup.dist.foreign.ui.main.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.ui.status.api.IAaclBatchStatusController;
import com.copyright.rup.dist.foreign.ui.status.api.ICommonBatchStatusController;
import com.copyright.rup.dist.foreign.ui.status.api.IFasBatchStatusController;
import com.copyright.rup.dist.foreign.ui.status.api.INtsBatchStatusController;
import com.copyright.rup.dist.foreign.ui.status.api.ISalBatchStatusController;

import com.google.common.collect.ImmutableMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Implementation of {@link com.copyright.rup.dist.foreign.ui.main.api.IControllerProvider} for usage batch status
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
    @Autowired
    private IAaclBatchStatusController aaclBatchStatusController;
    @Autowired
    private ISalBatchStatusController salBatchStatusController;

    @Override
    protected Map<String, ICommonBatchStatusController> getProductFamilyToControllerMap() {
        return ImmutableMap.of(
            FdaConstants.FAS_PRODUCT_FAMILY, fasBatchStatusController,
            FdaConstants.CLA_FAS_PRODUCT_FAMILY, fasBatchStatusController,
            FdaConstants.NTS_PRODUCT_FAMILY, ntsBatchStatusController,
            FdaConstants.AACL_PRODUCT_FAMILY, aaclBatchStatusController,
            FdaConstants.SAL_PRODUCT_FAMILY, salBatchStatusController
        );
    }
}
