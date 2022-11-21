package com.copyright.rup.dist.foreign.ui.main.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.fas.IFasUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.nts.INtsUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageController;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Implementation of {@link com.copyright.rup.dist.foreign.ui.main.api.IControllerProvider} for usage controllers.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/6/19
 *
 * @author Stanislau Rudak
 */
@Component("dist.foreign.usagesControllerProvider")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UsagesControllerProvider extends CommonControllerProvider<ICommonUsageController> {

    @Autowired
    private IFasUsageController fasUsagesController;
    @Autowired
    private INtsUsageController ntsUsagesController;
    @Autowired
    private IAaclUsageController aaclUsagesController;
    @Autowired
    private ISalUsageController salUsagesController;
    @Autowired
    private IAclciUsageController aclciUsageController;

    @Override
    protected Map<String, ICommonUsageController> getProductFamilyToControllerMap() {
        return ImmutableMap.<String, ICommonUsageController>builder()
            .put(FdaConstants.FAS_PRODUCT_FAMILY, fasUsagesController)
            .put(FdaConstants.CLA_FAS_PRODUCT_FAMILY, fasUsagesController)
            .put(FdaConstants.NTS_PRODUCT_FAMILY, ntsUsagesController)
            .put(FdaConstants.AACL_PRODUCT_FAMILY, aaclUsagesController)
            .put(FdaConstants.SAL_PRODUCT_FAMILY, salUsagesController)
            .put(FdaConstants.ACLCI_PRODUCT_FAMILY, aclciUsageController)
            .build();
    }
}
