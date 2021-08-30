package com.copyright.rup.dist.foreign.ui.main.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmController;

import com.google.common.collect.ImmutableMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Implementation of {@link com.copyright.rup.dist.foreign.ui.main.api.IControllerProvider} for UDM tab controller.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 08/27/2021
 *
 * @author Aliaksandr Liakh
 */
@Component("dist.foreign.udmControllerProvider")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmControllerProvider extends CommonControllerProvider<IUdmController> {

    @Autowired
    private IUdmController udmController;

    @Override
    protected Map<String, IUdmController> getProductFamilyToControllerMap() {
        return ImmutableMap.of(FdaConstants.ACL_PRODUCT_FAMILY, udmController);
    }
}
