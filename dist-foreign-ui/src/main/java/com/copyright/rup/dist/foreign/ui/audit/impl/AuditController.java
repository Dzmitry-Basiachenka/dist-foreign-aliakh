package com.copyright.rup.dist.foreign.ui.audit.impl;

import com.copyright.rup.dist.foreign.domain.AuditFilter;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditController;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditWidget;
import com.copyright.rup.dist.foreign.ui.common.ExportStreamSource;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Controller for {@link AuditWidget}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/11/18
 *
 * @author Aliaksandr Radkevich
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AuditController extends CommonController<IAuditWidget> implements IAuditController {

    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private IAuditFilterController auditFilterController;
    @Autowired
    private IUsageService usageService;

    @Override
    public IAuditFilterController getAuditFilterController() {
        return auditFilterController;
    }

    @Override
    public void onFilterChanged() {
        getWidget().refresh();
    }

    @Override
    public int getSize() {
        AuditFilter filter = getFilter();
        return !filter.isEmpty() ? usageService.getAuditItemsCount(filter) : 0;
    }

    @Override
    public List<UsageDto> loadBeans(int startIndex, int count, Object[] sortPropertyIds, boolean... sortStates) {
        AuditFilter filter = getFilter();
        return !filter.isEmpty()
            ? usageService.getForAudit(filter, new Pageable(startIndex, count),
            Sort.create(sortPropertyIds, sortStates))
            : Collections.emptyList();
    }

    @Override
    public void showUsageHistory(String usageId, String detailId) {
        Windows.showModalWindow(new UsageHistoryWindow(detailId, usageAuditService.getUsageAudit(usageId)));
    }

    @Override
    public IStreamSource getExportUsagesStreamSource() {
        return new ExportStreamSource("export_usage_audit_",
            pipedStream -> usageService.writeAuditCsvReport(getFilter(), pipedStream));
    }

    @Override
    protected IAuditWidget instantiateWidget() {
        return new AuditWidget();
    }

    /**
     * @return applied {@link AuditFilter}.
     */
    AuditFilter getFilter() {
        AuditFilter filter = getAuditFilterController().getWidget().getAppliedFilter();
        filter.setSearchValue(getWidget().getSearchValue());
        return filter;
    }
}
