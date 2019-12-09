package com.copyright.rup.dist.foreign.ui.main.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;

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
public class UsagesControllerProvider extends CommonControllerProvider<IUsagesController> {

    // TODO {srudak} replace with specific interfaces once implemented
    @Autowired
    private IUsagesController fasUsagesController;
    @Autowired
    private IUsagesController ntsUsagesController;

    @Override
    protected Map<String, IUsagesController> getProductFamilyToControllerMap() {
        return ImmutableMap.of(
            FdaConstants.FAS_PRODUCT_FAMILY, fasUsagesController,
            FdaConstants.CLA_FAS_PRODUCT_FAMILY, fasUsagesController,
            FdaConstants.NTS_PRODUCT_FAMILY, ntsUsagesController
        );
    }
}
