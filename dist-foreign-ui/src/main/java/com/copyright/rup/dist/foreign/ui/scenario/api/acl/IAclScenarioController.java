package com.copyright.rup.dist.foreign.ui.scenario.api.acl;

import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.vaadin.widget.SearchWidget.ISearchController;
import com.copyright.rup.vaadin.widget.api.IController;

import com.vaadin.data.provider.QuerySortOrder;

import java.util.List;

/**
 * Interface for ACL scenario controller.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/23/2022
 *
 * @author Anton Azarenka
 */
public interface IAclScenarioController extends IController<IAclScenarioWidget>, ISearchController {

    /**
     * @return current {@link AclScenario}.
     */
    AclScenario getScenario();

    /**
     * Sets the {@link AclScenario}.
     *
     * @param scenario a {@link AclScenario} to use
     */
    void setScenario(AclScenario scenario);

    /**
     * @return instance of {@link AclScenarioDto}
     */
    AclScenarioDto getAclScenarioWithAmountsAndLastAction();

    /**
     * Loads specified number of beans from the storage with given start index.
     *
     * @param startIndex start index
     * @param count      items count to load
     * @param sortOrders sort orders
     * @return list of items to be displayed on UI
     */
    List<AclRightsholderTotalsHolder> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders);

    /**
     * @return number of items.
     */
    int getSize();
}
