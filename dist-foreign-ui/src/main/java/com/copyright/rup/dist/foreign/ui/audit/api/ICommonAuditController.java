package com.copyright.rup.dist.foreign.ui.audit.api;

import com.copyright.rup.dist.common.reporting.api.ICsvReportProvider;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.vaadin.widget.api.IController;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Common interface for audit controllers.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/11/18
 *
 * @author Aliaksandr Radkevich
 */
public interface ICommonAuditController extends IController<ICommonAuditWidget>, ICsvReportProvider {

    /**
     * {@link #onFilterChanged()}.
     */
    Method ON_FILTER_CHANGED = ReflectTools.findMethod(ICommonAuditController.class, "onFilterChanged");

    /**
     * @return audit filter controller.
     */
    ICommonAuditFilterController getAuditFilterController();

    /**
     * @return audit filter.
     */
    AuditFilter getFilter();

    /**
     * Handles audit filter change event.
     */
    void onFilterChanged();

    /**
     * Shows modal window with usage history.
     *
     * @param usageId  usage id
     * @param detailId detail id
     */
    void showUsageHistory(String usageId, String detailId);

    /**
     * @return number of items.
     */
    int getSize();

    /**
     * Loads specified number of beans from the storage with given start index.
     *
     * @param startIndex start index
     * @param count      items count to load
     * @param sortOrders sort orders
     * @return list of items to be displayed on UI
     */
    List<UsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders);
}
