package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.vaadin.ui.component.lazytable.CommonBeanQuery;

import org.vaadin.addons.lazyquerycontainer.QueryDefinition;

import java.util.Map;

/**
 * Bean query implementation for {@link RightsholderTotalsHolder}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 04/05/17
 *
 * @author Ihar Suvorau
 */
public class RightsholderTotalsHolderBeanQuery extends CommonBeanQuery<RightsholderTotalsHolder> {

    /**
     * Constructor.
     *
     * @param queryDefinition    query definition
     * @param queryConfiguration query configuration
     * @param sortPropertyIds    sort properties
     * @param sortStates         sort states
     */
    public RightsholderTotalsHolderBeanQuery(QueryDefinition queryDefinition,
                                             Map<String, Object> queryConfiguration, Object[] sortPropertyIds,
                                             boolean... sortStates) {
        super(queryDefinition, queryConfiguration, sortPropertyIds, sortStates);
    }
}
