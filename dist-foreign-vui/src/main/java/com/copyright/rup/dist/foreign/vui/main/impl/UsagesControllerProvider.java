package com.copyright.rup.dist.foreign.vui.main.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageController;
import com.copyright.rup.dist.foreign.vui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.vui.usage.api.fas.IFasUsageController;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.INtsUsageController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

import javax.annotation.PostConstruct;

/**
 * Implementation of {@link com.copyright.rup.dist.foreign.vui.main.api.IControllerProvider} for usage controllers.
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

    private Map<String, ICommonUsageController> productFamilyMap;

    @Autowired
    private IAaclUsageController aaclUsagesController;
    @Autowired
    private IFasUsageController fasUsagesController;
    @Autowired
    private INtsUsageController ntsUsagesController;

    /**
     * Initialises product family map.
     */
    @PostConstruct
    public void initProductFamilyMap() {
        productFamilyMap = Map.of(
            FdaConstants.AACL_PRODUCT_FAMILY, aaclUsagesController,
            FdaConstants.FAS_PRODUCT_FAMILY, fasUsagesController,
            FdaConstants.CLA_FAS_PRODUCT_FAMILY, fasUsagesController,
            FdaConstants.NTS_PRODUCT_FAMILY, ntsUsagesController
        );
    }

    @Override
    protected Map<String, ICommonUsageController> getProductFamilyToControllerMap() {
        return productFamilyMap;
    }
}
