package com.copyright.rup.dist.foreign.ui.scenario.api.acl;

import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.vaadin.widget.api.IController;

import com.vaadin.data.provider.QuerySortOrder;

import java.util.List;

/**
 * Controller interface for {@link IAclDrillDownByRightsholderWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/14/2022
 *
 * @author Dzmitry Basiachenka
 */
public interface IAclDrillDownByRightsholderController extends IController<IAclDrillDownByRightsholderWidget> {

    /**
     * Loads specified number of beans from the storage with given start index.
     *
     * @param startIndex start index
     * @param count      items count to load
     * @param sortOrders sort orders
     * @return list of items to be displayed on UI
     */
    List<AclScenarioDetailDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders);

    /**
     * @return number of items.
     */
    int getSize();

    /**
     * Initializes and shows the widget.
     * Sets selected {@link AclScenario} to the widget.
     *
     * @param accountNumber selected account number
     * @param rhName        rightsholder name
     * @param scenario      selected {@link AclScenario}
     */
    void showWidget(Long accountNumber, String rhName, AclScenario scenario);
}
