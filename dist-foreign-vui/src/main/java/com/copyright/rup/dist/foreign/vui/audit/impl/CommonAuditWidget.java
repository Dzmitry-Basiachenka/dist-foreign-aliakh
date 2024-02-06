package com.copyright.rup.dist.foreign.vui.audit.impl;

import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditController;
import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditWidget;

import com.vaadin.flow.component.splitlayout.SplitLayout;

import org.apache.commons.lang3.StringUtils;

/**
 * Common implementation for audit widgets.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/11/18
 *
 * @author Aliaksandr Radkevich
 */
public abstract class CommonAuditWidget extends SplitLayout implements ICommonAuditWidget {

    private static final long serialVersionUID = 3616787468284929274L;

    private ICommonAuditController controller;


    @SuppressWarnings("unchecked")
    @Override
    public ICommonAuditWidget init() {
        //TODO: {dbasiachenka} implement
        return this;
    }

    @Override
    public void setController(ICommonAuditController controller) {
        this.controller = controller;
    }

    @Override
    public ICommonAuditController getController() {
        return controller;
    }

    @Override
    public String getSearchValue() {
        //TODO: {dbasiachenka} implement
        return StringUtils.EMPTY;
    }
}
