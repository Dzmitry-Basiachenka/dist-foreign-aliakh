package com.copyright.rup.dist.foreign.ui.audit.impl;

import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditController;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditWidget;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.api.CommonController;

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

    private static final long serialVersionUID = -1134914079609850050L;

    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IReportService reportService;
    @Autowired
    private IStreamSourceHandler streamSourceHandler;

    @Override
    public abstract ICommonAuditFilterController getAuditFilterController();

    @Override
    public AuditFilter getFilter() {
        AuditFilter filter = getAuditFilterController().getWidget().getAppliedFilter();
        filter.setSearchValue(getWidget().getSearchValue());
        return filter;
    }

    @Override
    public void onFilterChanged() {
        getWidget().refresh();
    }

    @Override
    public void showUsageHistory(String usageId, String detailId) {
        Windows.showModalWindow(new UsageHistoryWindow(detailId, usageAuditService.getUsageAudit(usageId)));
    }

    /**
     * Instantiates widget.
     *
     * @return {@link ICommonAuditWidget} instance
     */
    @Override
    protected abstract ICommonAuditWidget instantiateWidget();

    protected IUsageAuditService getUsageAuditService() {
        return usageAuditService;
    }

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
