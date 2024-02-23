package com.copyright.rup.dist.foreign.vui.audit.impl.nts;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditFilterController;
import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditWidget;
import com.copyright.rup.dist.foreign.vui.audit.api.nts.INtsAuditController;
import com.copyright.rup.dist.foreign.vui.audit.api.nts.INtsAuditFilterController;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonAuditController;

import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link INtsAuditController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/17/2019
 *
 * @author Aliaksanr Liakh
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NtsAuditController extends CommonAuditController implements INtsAuditController {

    private static final long serialVersionUID = 5877596419240945865L;

    @Autowired
    private INtsAuditFilterController controller;

    @Override
    public IStreamSource getCsvStreamSource() {
        return getStreamSourceHandler().getCsvStreamSource(() -> "export_usage_audit_",
            pos -> getReportService().writeAuditNtsCsvReport(getFilter(), pos));
    }

    @Override
    public int getSize() {
        var filter = getFilter();
        return !filter.isEmpty() ? getUsageService().getCountForAudit(filter) : 0;
    }

    @Override
    public ICommonAuditFilterController getAuditFilterController() {
        return controller;
    }

    @Override
    public List<UsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        var filter = getFilter();
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return !filter.isEmpty()
            ? getUsageService().getForAudit(filter, new Pageable(startIndex, count), sort)
            : List.of();
    }

    @Override
    protected ICommonAuditWidget instantiateWidget() {
        return new NtsAuditWidget(this);
    }
}
