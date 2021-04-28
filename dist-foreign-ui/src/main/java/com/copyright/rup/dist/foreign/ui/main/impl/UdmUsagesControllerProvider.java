package com.copyright.rup.dist.foreign.ui.main.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;

import com.google.common.collect.ImmutableMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Implementation of {@link com.copyright.rup.dist.foreign.ui.main.api.IControllerProvider} for UDM usage controller.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 04/26/2021
 *
 * @author Ihar Suvorau
 */
@Component("dist.foreign.udmUsagesControllerProvider")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmUsagesControllerProvider extends CommonControllerProvider<IUdmUsageController> {

    @Autowired
    private IUdmUsageController udmUsageController;

    @Override
    protected Map<String, IUdmUsageController> getProductFamilyToControllerMap() {
        return ImmutableMap.of(FdaConstants.ACL_PRODUCT_FAMILY, udmUsageController);
    }
}
