package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.vaadin.widget.api.IController;
import com.vaadin.data.provider.QuerySortOrder;

import java.util.List;

/**
 * Interface for UDM value controller.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Aliaksandr Liakh
 */
public interface IUdmValueController extends IController<IUdmValueWidget> {

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
    List<UdmValueDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders);

    /**
     * Initializes {@link IUdmValueFilterWidget}.
     *
     * @return initialized {@link IUdmValueFilterWidget}
     */
    IUdmValueFilterWidget initValuesFilterWidget();
}
