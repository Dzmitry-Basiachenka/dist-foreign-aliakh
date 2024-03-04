package com.copyright.rup.dist.foreign.vui.audit.impl.aacl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;
import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditFilterController;
import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditWidget;
import com.copyright.rup.dist.foreign.vui.audit.api.aacl.IAaclAuditController;
import com.copyright.rup.dist.foreign.vui.audit.api.aacl.IAaclAuditFilterController;
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
 * Implementation of {@link IAaclAuditController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/12/2020
 *
 * @author Anton Azarenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AaclAuditController extends CommonAuditController implements IAaclAuditController {

    private static final long serialVersionUID = -2423045440172687438L;

    @Autowired
    private IAaclAuditFilterController filterController;
    @Autowired
    private IAaclUsageService aaclUsageService;

    @Override
    public ICommonAuditFilterController getAuditFilterController() {
        return filterController;
    }

    @Override
    public IStreamSource getCsvStreamSource() {
        return getStreamSourceHandler().getCsvStreamSource(() -> "export_usage_audit_",
            pos -> getReportService().writeAuditAaclCsvReport(getFilter(), pos));
    }

    @Override
    public int getSize() {
        var filter = getFilter();
        return !filter.isEmpty() ? aaclUsageService.getCountForAudit(filter) : 0;
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
            ? aaclUsageService.getForAudit(filter, new Pageable(startIndex, count), sort)
            : List.of();
    }

    @Override
    protected ICommonAuditWidget instantiateWidget() {
        return new AaclAuditWidget(this);
    }
}
