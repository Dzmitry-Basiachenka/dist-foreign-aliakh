package com.copyright.rup.dist.foreign.ui.main.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclCalculationController;

import com.google.common.collect.ImmutableMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Implementation of {@link com.copyright.rup.dist.foreign.ui.main.api.IControllerProvider} for Calculations tab
 * controller.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/26/2022
 *
 * @author Dzmitry Basiachenka
 */
@Component("dist.foreign.aclCalculationControllerProvider")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclCalculationControllerProvider extends CommonControllerProvider<IAclCalculationController> {

    @Autowired
    private IAclCalculationController aclCalculationController;

    @Override
    protected Map<String, IAclCalculationController> getProductFamilyToControllerMap() {
        return ImmutableMap.of(FdaConstants.ACL_PRODUCT_FAMILY, aclCalculationController);
    }
}
