package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.UdmValueBaselineDto;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.vaadin.widget.api.IController;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Interface for UDM baseline value controller.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Anton Azarenka
 */
public interface IUdmBaselineValueController extends IController<IUdmBaselineValueWidget> {

    /**
     * {@link #onFilterChanged(FilterChangedEvent)}.
     */
    Method ON_FILTER_CHANGED =
        ReflectTools.findMethod(IUdmBaselineValueController.class, "onFilterChanged", FilterChangedEvent.class);

    /**
     * Handles changes of filter.
     *
     * @param event event
     */
    void onFilterChanged(FilterChangedEvent event);

    /**
     * Loads specified number of beans from the storage with given start index.
     *
     * @param startIndex start index
     * @param count      items count to load
     * @param sortOrders sort orders
     * @return list of items to be displayed on UI
     */
    List<UdmValueBaselineDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders);

    /**
     * @return number of items.
     */
    int getBeansCount();

    /**
     * @return instance of {@link IStreamSource} for export.
     */
    IStreamSource getExportBaselineValuesStreamSource();

    /**
     * Initializes {@link IUdmBaselineValueFilterWidget}.
     *
     * @return initialized {@link IUdmBaselineValueFilterWidget}
     */
    IUdmBaselineValueFilterWidget initBaselineValuesFilterWidget();
}
