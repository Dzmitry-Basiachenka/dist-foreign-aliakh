package com.copyright.rup.dist.foreign.ui.main.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditController;
import com.copyright.rup.dist.foreign.ui.audit.api.aacl.IAaclAuditController;
import com.copyright.rup.dist.foreign.ui.audit.api.fas.IFasAuditController;
import com.copyright.rup.dist.foreign.ui.audit.api.nts.INtsAuditController;

import com.copyright.rup.dist.foreign.ui.audit.api.sal.ISalAuditController;
import com.google.common.collect.ImmutableMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Implementation of {@link com.copyright.rup.dist.foreign.ui.main.api.IControllerProvider} for audit controllers.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/6/19
 *
 * @author Stanislau Rudak
 */
@Component("dist.foreign.auditControllerProvider")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AuditControllerProvider extends CommonControllerProvider<ICommonAuditController> {

    @Autowired
    private IFasAuditController fasAuditController;
    @Autowired
    private INtsAuditController ntsAuditController;
    @Autowired
    private IAaclAuditController aaclAuditController;
    @Autowired
    private ISalAuditController salAuditController;

    @Override
    protected Map<String, ICommonAuditController> getProductFamilyToControllerMap() {
        return ImmutableMap.of(
            FdaConstants.FAS_PRODUCT_FAMILY, fasAuditController,
            FdaConstants.CLA_FAS_PRODUCT_FAMILY, fasAuditController,
            FdaConstants.NTS_PRODUCT_FAMILY, ntsAuditController,
            FdaConstants.AACL_PRODUCT_FAMILY, aaclAuditController,
            FdaConstants.SAL_PRODUCT_FAMILY, salAuditController
        );
    }
}
