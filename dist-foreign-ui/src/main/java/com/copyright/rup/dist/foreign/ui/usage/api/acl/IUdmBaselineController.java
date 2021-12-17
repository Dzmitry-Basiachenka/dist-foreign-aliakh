package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.UdmBaselineDto;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.vaadin.widget.api.IController;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * Interface for UDM baseline controller.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Dzmitry Basiachenka
 */
public interface IUdmBaselineController extends IController<IUdmBaselineWidget> {

    /**
     * {@link #onFilterChanged(FilterChangedEvent)}.
     */
    Method ON_FILTER_CHANGED =
        ReflectTools.findMethod(IUdmBaselineController.class, "onFilterChanged", FilterChangedEvent.class);

    /**
     * Handles changes of filter.
     *
     * @param event event
     */
    void onFilterChanged(FilterChangedEvent event);

    /**
     * @return number of items.
     */
    int getBeansCount();

    /**
     * Loads specified number of beans from the storage with given start index.
     *
     * @param startIndex start index
     * @param count      items count to load
     * @param sortOrders sort orders
     * @return list of items to be displayed on UI
     */
    List<UdmBaselineDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders);

    /**
     * Initializes {@link IUdmBaselineFilterWidget}.
     *
     * @return initialized {@link IUdmBaselineFilterWidget}
     */
    IUdmBaselineFilterWidget initBaselineFilterWidget();

    /**
     * @return instance of {@link IStreamSource} for export.
     */
    IStreamSource getExportUdmBaselineUsagesStreamSource();

    /**
     * Deletes selected usages from baseline.
     *
     * @param usageIds usage ids to delete
     * @param reason reason
     */
    void deleteFromBaseline(Set<String> usageIds, String reason);

    /**
     * @return threshold value for size of UDM records.
     */
    int getUdmRecordThreshold();
}
