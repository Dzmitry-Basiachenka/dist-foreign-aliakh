package com.copyright.rup.dist.foreign.ui.main.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditController;

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
public class AuditControllerProvider extends CommonControllerProvider<IAuditController> {

    // TODO {srudak} replace with specific interfaces once implemented
    @Autowired
    private IAuditController fasAuditController;
    @Autowired
    private IAuditController ntsAuditController;

    @Override
    protected Map<String, IAuditController> getProductFamilyToControllerMap() {
        return ImmutableMap.of(
            FdaConstants.FAS_PRODUCT_FAMILY, fasAuditController,
            FdaConstants.CLA_FAS_PRODUCT_FAMILY, fasAuditController,
            FdaConstants.NTS_PRODUCT_FAMILY, ntsAuditController
        );
    }
}
