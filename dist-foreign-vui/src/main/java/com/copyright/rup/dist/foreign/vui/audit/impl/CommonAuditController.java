package com.copyright.rup.dist.foreign.vui.audit.impl;

import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditController;
import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.CommonController;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Common implementation for audit controllers.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/11/18
 *
 * @author Aliaksandr Radkevich
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public abstract class CommonAuditController extends CommonController<ICommonAuditWidget>
    implements ICommonAuditController {

    /**
     * Instantiates widget.
     *
     * @return {@link ICommonAuditWidget} instance
     */
    @Override
    protected abstract ICommonAuditWidget instantiateWidget();
}
