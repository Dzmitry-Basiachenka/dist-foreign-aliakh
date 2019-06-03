package com.copyright.rup.dist.foreign.ui.audit.impl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditController;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditWidget;
import com.copyright.rup.dist.foreign.ui.report.api.IStreamSourceHandler;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;

import org.apache.commons.collections4.CollectionUtils;
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
    @Autowired
    private IReportService reportService;
    @Autowired
    private IStreamSourceHandler streamSourceHandler;

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
    public List<UsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        AuditFilter filter = getFilter();
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return !filter.isEmpty()
            ? usageService.getForAudit(filter, new Pageable(startIndex, count), sort)
            : Collections.emptyList();
    }

    @Override
    public void showUsageHistory(String usageId, String detailId) {
        Windows.showModalWindow(new UsageHistoryWindow(detailId, usageAuditService.getUsageAudit(usageId)));
    }

    @Override
    public IStreamSource getCsvStreamSource() {
        return streamSourceHandler.getCsvStreamSource(() -> "export_usage_audit_",
            pos -> reportService.writeAuditCsvReport(getFilter(), pos));
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
