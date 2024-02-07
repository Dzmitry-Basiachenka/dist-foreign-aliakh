package com.copyright.rup.dist.foreign.vui.audit.impl;

import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditController;
import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.CommonController;

import com.vaadin.flow.component.dialog.Dialog;

import org.springframework.beans.factory.annotation.Autowired;
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

    private static final long serialVersionUID = -3331965509756762707L;

    @Autowired
    private IUsageService usageService;
    @Autowired
    private IReportService reportService;
    @Autowired
    private IStreamSourceHandler streamSourceHandler;

    @Override
    public AuditFilter getFilter() {
        //TODO: {dbasiachenka} implement
        return new AuditFilter();
    }

    @Override
    public void showUsageHistory(String usageId, String detailId) {
        //TODO: {dbasiachenka} implement
        Windows.showModalWindow(new Dialog());
    }

    /**
     * Instantiates widget.
     *
     * @return {@link ICommonAuditWidget} instance
     */
    @Override
    protected abstract ICommonAuditWidget instantiateWidget();

    protected IUsageService getUsageService() {
        return usageService;
    }

    protected IReportService getReportService() {
        return reportService;
    }

    protected IStreamSourceHandler getStreamSourceHandler() {
        return streamSourceHandler;
    }
}
