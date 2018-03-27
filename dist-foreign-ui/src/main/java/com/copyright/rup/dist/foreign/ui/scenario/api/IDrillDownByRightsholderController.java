package com.copyright.rup.dist.foreign.ui.scenario.api;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.vaadin.widget.api.IController;

import com.vaadin.data.provider.QuerySortOrder;

import java.util.List;

/**
 * Controller interface for {@link IDrillDownByRightsholderWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/11/17
 *
 * @author Ihar Suvorau
 */
public interface IDrillDownByRightsholderController
    extends IController<IDrillDownByRightsholderWidget> {

    /**
     * Initializes and shows {@link IDrillDownByRightsholderWidget}.
     * Sets selected {@link Scenario} to the widget.
     *
     * @param accountNumber selected account number
     * @param rhName        rightsholder name
     * @param scenario      selected {@link Scenario}
     */
    void showWidget(Long accountNumber, String rhName, Scenario scenario);

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
